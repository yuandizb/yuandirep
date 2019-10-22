package com.yd.eureka.netflix.discovery;

import static com.netflix.discovery.EurekaClientNames.METRIC_TRANSPORT_PREFIX;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.ConcurrentSkipListSet;
import java.util.concurrent.atomic.AtomicReference;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.netflix.discovery.shared.resolver.ClusterResolver;
import com.netflix.discovery.shared.resolver.EurekaEndpoint;
import com.netflix.discovery.shared.resolver.aws.AwsEndpoint;
import com.netflix.discovery.shared.transport.EurekaHttpClient;
import com.netflix.discovery.shared.transport.EurekaHttpClientFactory;
import com.netflix.discovery.shared.transport.EurekaHttpResponse;
import com.netflix.discovery.shared.transport.EurekaTransportConfig;
import com.netflix.discovery.shared.transport.TransportClientFactory;
import com.netflix.discovery.shared.transport.TransportException;
import com.netflix.discovery.shared.transport.TransportUtils;
import com.netflix.discovery.shared.transport.decorator.EurekaHttpClientDecorator;
import com.netflix.discovery.shared.transport.decorator.ServerStatusEvaluator;
import com.netflix.servo.annotations.DataSourceType;
import com.netflix.servo.annotations.Monitor;
import com.netflix.servo.monitor.Monitors;
import com.yd.eureka.ctlserver.IsolatedZoneConfig;
import com.yd.eureka.utils.SpringUtil;

public class RetryableEurekaHttpClient extends EurekaHttpClientDecorator {
    private static final Logger logger = LoggerFactory.getLogger(RetryableEurekaHttpClient.class);
    public static final int DEFAULT_NUMBER_OF_RETRIES = 3;


    private final String name;
    private final EurekaTransportConfig transportConfig;
    private final ClusterResolver clusterResolver;
    private final TransportClientFactory clientFactory;
    private final ServerStatusEvaluator serverStatusEvaluator;
    private final int numberOfRetries;

    private final AtomicReference<EurekaHttpClient> delegate = new AtomicReference<>();

    private final Set<EurekaEndpoint> quarantineSet = new ConcurrentSkipListSet<>();

    public RetryableEurekaHttpClient(String name,
                                     EurekaTransportConfig transportConfig,
                                     ClusterResolver clusterResolver,
                                     TransportClientFactory clientFactory,
                                     ServerStatusEvaluator serverStatusEvaluator,
                                     int numberOfRetries) {
        this.name = name;
        this.transportConfig = transportConfig;
        this.clusterResolver = clusterResolver;
        this.clientFactory = clientFactory;
        this.serverStatusEvaluator = serverStatusEvaluator;
        this.numberOfRetries = numberOfRetries;
        Monitors.registerObject(name, this);
    }

    @Override
    public void shutdown() {
        TransportUtils.shutdown(delegate.get());
        if(Monitors.isObjectRegistered(name, this)) {
            Monitors.unregisterObject(name, this);
        }
    }

    @Override
    protected <R> EurekaHttpResponse<R> execute(RequestExecutor<R> requestExecutor) {
        List<EurekaEndpoint> candidateHosts = null;
        int endpointIdx = 0;
        EurekaHttpResponse response = registIsolateZoneToEureka(requestExecutor);
        if (response != null && response.getStatusCode() == 404) {
        	return response;
        }
        
        for (int retry = 0; retry < numberOfRetries; retry++) {
            EurekaHttpClient currentHttpClient = delegate.get();
            EurekaEndpoint currentEndpoint = null;
            if (currentHttpClient == null) {
                if (candidateHosts == null) {
                    candidateHosts = getHostCandidates();
                    if (candidateHosts.isEmpty()) {
                        throw new TransportException("There is no known eureka server; cluster server list is empty");
                    }
                }
                if (endpointIdx >= candidateHosts.size()) {
                    throw new TransportException("Cannot execute request on any known server");
                }
                currentEndpoint = candidateHosts.get(endpointIdx++);
                currentHttpClient = clientFactory.newClient(currentEndpoint);
            }

            try {
                response = requestExecutor.execute(currentHttpClient);
                if (serverStatusEvaluator.accept(response.getStatusCode(), requestExecutor.getRequestType())) {
                    delegate.set(currentHttpClient);
                    if (retry > 0) {
                        logger.info("Request execution succeeded on retry #{}", retry);
                    }
                    return response;
                }
                logger.warn("Request execution failure with status code {}; retrying on another server if available", response.getStatusCode());
            } catch (Exception e) {
                logger.warn("Request execution failed with message: {}", e.getMessage());  // just log message as the underlying client should log the stacktrace
            }

            // Connection error or 5xx from the server that must be retried on another server
            delegate.compareAndSet(currentHttpClient, null);
            if (currentEndpoint != null) {
                quarantineSet.add(currentEndpoint);
            }
        }
        throw new TransportException("Retry limit reached; giving up on completing the request");
    }

    @SuppressWarnings("rawtypes")
	private EurekaHttpResponse registIsolateZoneToEureka(RequestExecutor requestExecutor) {
    	EurekaHttpClient currentHttpClient = null;
    	IsolatedZoneConfig zc = SpringUtil.getBean(IsolatedZoneConfig.class);
    	if (zc == null) return null;
    	Object[] list = zc.getAwsEndpoints().toArray();
    	for(Object endpoint : list) {
    		AwsEndpoint ae = (AwsEndpoint)endpoint;
    		currentHttpClient = clientFactory.newClient(ae);
    		try {
    			if (logger.isDebugEnabled()) {
    				logger.debug("Regist the app to isolateZone {}", ae.getServiceUrl());
    			}
    			EurekaHttpResponse response = requestExecutor.execute(currentHttpClient);
    			 if (response.getStatusCode() == 404) {
    				 return response;
    			 }
    			if (serverStatusEvaluator.accept(response.getStatusCode(), requestExecutor.getRequestType())) {
    				if (logger.isDebugEnabled()) {
    					logger.debug("Request execution succeeded on {}", ae.getServiceUrl());
    				}
    			}
    			else {
    				logger.warn("Request execution failure with status code {}; retrying on another server if available", response.getStatusCode());
    			}
    		} catch (Exception e) {
    			logger.warn("Request execution failed with message: {}", e.getMessage());  // just log message as the underlying client should log the stacktrace
    		}
    	}
    	return null;
    }

	public static EurekaHttpClientFactory createFactory(final String name,
                                                        final EurekaTransportConfig transportConfig,
                                                        final ClusterResolver<EurekaEndpoint> clusterResolver,
                                                        final TransportClientFactory delegateFactory,
                                                        final ServerStatusEvaluator serverStatusEvaluator) {
        return new EurekaHttpClientFactory() {
            @Override
            public EurekaHttpClient newClient() {
                return new RetryableEurekaHttpClient(name, transportConfig, clusterResolver, delegateFactory,
                        serverStatusEvaluator, DEFAULT_NUMBER_OF_RETRIES);
            }

            @Override
            public void shutdown() {
                delegateFactory.shutdown();
            }
        };
    }

    private List<EurekaEndpoint> getHostCandidates() {
        List<EurekaEndpoint> candidateHosts = clusterResolver.getClusterEndpoints();
        quarantineSet.retainAll(candidateHosts);

        // If enough hosts are bad, we have no choice but start over again
        int threshold = (int) (candidateHosts.size() * transportConfig.getRetryableClientQuarantineRefreshPercentage());
        if (quarantineSet.isEmpty()) {
            // no-op
        } else if (quarantineSet.size() >= threshold) {
            logger.debug("Clearing quarantined list of size {}", quarantineSet.size());
            quarantineSet.clear();
        } else {
            List<EurekaEndpoint> remainingHosts = new ArrayList<>(candidateHosts.size());
            for (EurekaEndpoint endpoint : candidateHosts) {
                if (!quarantineSet.contains(endpoint)) {
                    remainingHosts.add(endpoint);
                }
            }
            candidateHosts = remainingHosts;
        }

        return candidateHosts;
    }

    @Monitor(name = METRIC_TRANSPORT_PREFIX + "quarantineSize",
            description = "number of servers quarantined", type = DataSourceType.GAUGE)
    public long getQuarantineSetSize() {
        return quarantineSet.size();
    }
}
