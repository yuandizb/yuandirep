package com.micro.fast.eureka.server.controller;

import com.micro.fast.boot.starter.common.response.ServerResponse;
import com.netflix.appinfo.AmazonInfo;
import com.netflix.appinfo.DataCenterInfo;
import com.netflix.appinfo.InstanceInfo;
import com.netflix.appinfo.InstanceInfo.InstanceStatus;
import com.netflix.discovery.shared.Application;
import com.netflix.discovery.shared.Pair;
import com.netflix.eureka.EurekaServerContext;
import com.netflix.eureka.EurekaServerContextHolder;
import com.netflix.eureka.registry.PeerAwareInstanceRegistry;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.commons.lang.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * restful操作eureka的接口
 * 服务的注册 ：
 * 当eureka的客户端向eureka服务端注册的时候会提供自身的ip port info health等信息
 * 服务的续约 ：
 * Eureka客户会每隔30秒发送一次心跳来续约。 通过续约来告知Eureka Server该Eureka客户仍然存在，没有出现问题。
 * 正常情况下，如果Eureka Server在90秒没有收到Eureka客户的续约，它会将实例从其注册表中删除。 建议不要更改续约间隔。
 * 获取注册列表信息 ：
 * Eureka客户端从服务器获取注册表信息，并将其缓存在本地。客户端会使用该信息查找其他服务，从而进行远程调用。
 * 该注册列表信息定期（每30秒钟）更新一次。
 * 服务下线 :
 * Eureka客户端在程序关闭时向Eureka服务器发送取消请求。
 * 服务剔除 :
 * 在默认的情况下，当Eureka客户端连续90秒没有向Eureka服务器发送服务续约，即心跳，Eureka服务器会将该服务实例从服务注册列表删除.
 * Eureka默认不会二次注册 ：
 * 当有服务的注册信息传递到Eureka的时候，eureka会判断isReplication是否是true.当isReplication为true时候eureka就不会将服务信息传递
 * 给其他注册中心
 * 如何安全热发布：
 * 将服务状态调整为OUT_OF_SERVICE,经过几次心跳之后zuul会感知服务的状态，不在有新的请求进入下线的服务
 * 灰度发布：
 * 通过动态修改服务的matedate信息来实现灰度发布，可以将新发布的机器设置为灰度机器。灰度机器是指接收少量指定请求的机器
 * @author lsy
 */
@RestController
@RequestMapping({"${eureka.restful.path:/restful/eureka}"})
@Api(description = "注册中心的restful接口")
public class RestfulEurekaController {

    @GetMapping("/apps")
    @ApiOperation("获取所有的服务实例的简单信息")
    public ServerResponse apps() {
        Map<String, Object> map = new HashMap<>();
        //获取所有服务的信息
        populateApps(map);
        return ServerResponse.successMsgData("获取所有实例成功", map);
    }

    @GetMapping("/appsInfo")
    @ApiOperation("获取所有的服务实例的详细信息")
    public ServerResponse appsInfos() {
        Set<InstanceInfo> instanceInfos = new HashSet<>(10);
        List<Application> sortedApplications = getRegistry().getSortedApplications();
        for (Application sortedApplication : sortedApplications) {
            List<InstanceInfo> instances = sortedApplication.getInstances();
            for (InstanceInfo instance : instances) {
                instanceInfos.add(instance);
            }
        }
        return ServerResponse.successMsgData("获取所有实例成功", instanceInfos);
    }

    @PostMapping("/apps")
    @ApiOperation("根据id获取实例的信息，模糊查询")
    public ServerResponse getInstance(@RequestParam("id") String instanceId) {
        Set<InstanceInfo> instanceInfos = new HashSet<>(10);
        if (StringUtils.isNotBlank(instanceId)){
            List<Application> sortedApplications = getRegistry().getSortedApplications();
            boolean isBreak = false;
            for (Application sortedApplication : sortedApplications) {
                List<InstanceInfo> instances = sortedApplication.getInstances();
                for (InstanceInfo instance : instances) {
                    if (instance.getId().contains(instanceId)){
                        instanceInfos.add(instance);
                    }
                }
            }
        }else {
            return ServerResponse.errorMsg("instanceId不能为空");
        }
        return ServerResponse.successMsgData("根据主机名获取实例信息成功", instanceInfos);
    }

    @GetMapping("/apps/cluster/{appName}")
    @ApiOperation("根据appName,也就是application.name查询实例,模糊查询")
    public ServerResponse getInstancesByAppId(@PathVariable("appName") String appName) {
        List<InstanceInfo> instanceInfos = new LinkedList<>();
        if (StringUtils.isNotBlank(appName)){
            List<Application> sortedApplications = getRegistry().getSortedApplications();
            for (Application sortedApplication : sortedApplications) {
                if (sortedApplication.getName().contains(appName)){
                    List<InstanceInfo> instances = sortedApplication.getInstances();
                    for (InstanceInfo instance : instances) {
                        instanceInfos.add(instance);
                    }
                }
            }
        }else {
            return ServerResponse.errorMsg("appName不能为空");
        }
        return ServerResponse.successMsgData("根据appName获取实例信息成功", instanceInfos);
    }

    @PostMapping("/apps/app")
    @ApiOperation("根据appName和id获取服务的信息")
    public ServerResponse getApplication(@RequestParam("appName") String appName,@RequestParam("id") String id){
        PeerAwareInstanceRegistry registry = getRegistry();
        InstanceInfo instanceByAppAndId = registry.getInstanceByAppAndId(appName, id);
        return ServerResponse.successData(instanceByAppAndId);
    }

    /**
     * 主动下线服务，并传递到其他注册中心
     * 此接口主要用于下线异常终止的服务
     * @param appName
     * @param id
     * @return
     */
    @DeleteMapping
    @ApiOperation("下线服务")
    public ServerResponse unregister(@RequestParam("appName") String appName,@RequestParam("id") String id,@RequestParam("isReplication") Boolean isReplication){
        PeerAwareInstanceRegistry registry = getRegistry();
        // 当isReplication是true的时候表示这个实例的信息是从别的eureka复制过来的，eureka不会进行二次传递
        boolean cancel = registry.cancel(appName, id, isReplication);
        if (cancel){
            return ServerResponse.successMsg("下线服务成功，服务会在下次心跳时注册");
        }
        return ServerResponse.successMsg("下线服务失败");
    }

    /**
     * 调整服务的状态，可以用于在不影响业务的情况下热部署服务
     * @param appName
     * @param id
     * @param status
     * @return
     */
    @PutMapping
    @ApiOperation("调整服务状态")
    public ServerResponse updateStatus(@RequestParam("appName") String appName,@RequestParam("id")String id,@RequestParam("status")String status,@RequestParam("isReplication")Boolean isReplication){
        PeerAwareInstanceRegistry registry = getRegistry();
        switch (status){
            case "OUT_OF_SERVICE":
                boolean b = registry.statusUpdate(appName, id, InstanceStatus.OUT_OF_SERVICE, String.valueOf(System.currentTimeMillis()), isReplication);
                if (b){
                    return ServerResponse.successMsg("下线服务成功");
                }
                break;
            case "DOWN":
                boolean b1 = registry.statusUpdate(appName, id, InstanceStatus.DOWN, String.valueOf(System.currentTimeMillis()), isReplication);
                if (b1){
                    return ServerResponse.successMsg("设置服务为down成功");
                }
                break;
            case "UP" :
                boolean b2 = registry.statusUpdate(appName, id, InstanceStatus.UP, String.valueOf(System.currentTimeMillis()), isReplication);
                if (b2){
                    return ServerResponse.successMsg("上线服务成功");
                }
                break;
            case "UNKNOWN" :
                boolean b3 = registry.statusUpdate(appName, id, InstanceStatus.UNKNOWN, String.valueOf(System.currentTimeMillis()), isReplication);
                if (b3){
                    return ServerResponse.successMsg("设置服务状态为UNKNOWN");
                }
                break;
             case "STARTING" :
                 boolean b4 = registry.statusUpdate(appName, id, InstanceStatus.STARTING, String.valueOf(System.currentTimeMillis()), isReplication);
                 if (b4){
                     return ServerResponse.successMsg("设置服务为正在启动中");
                 }
                 break;
            default:
                return ServerResponse.errorMsg("请传入正确的状态");
        }
        return ServerResponse.errorMsg("修改状态失败");
    }

    private PeerAwareInstanceRegistry getRegistry() {
        return getServerContext().getRegistry();
    }

    private EurekaServerContext getServerContext() {
        return EurekaServerContextHolder.getInstance().getServerContext();
    }

    /**
     * 填充所有实例的简单信息
     * @param model
     */
    private void populateApps(Map<String, Object> model) {
        //获取实例的详细信息
        List<Application> sortedApplications = getRegistry().getSortedApplications();
        //封装成简单的信息
        ArrayList<Map<String, Object>> apps = new ArrayList<>();
        for (Application app : sortedApplications) {
            LinkedHashMap<String, Object> appData = new LinkedHashMap<>();
            apps.add(appData);
            appData.put("name", app.getName());
            Map<String, Integer> amiCounts = new HashMap<>();
            Map<InstanceInfo.InstanceStatus, List<Pair<String, String>>> instancesByStatus = new HashMap<>();
            Map<String, Integer> zoneCounts = new HashMap<>();
            for (InstanceInfo info : app.getInstances()) {
                String id = info.getId();
                String url = info.getStatusPageUrl();
                InstanceInfo.InstanceStatus status = info.getStatus();
                String ami = "n/a";
                String zone = "";
                if (info.getDataCenterInfo().getName() == DataCenterInfo.Name.Amazon) {
                    AmazonInfo dcInfo = (AmazonInfo) info.getDataCenterInfo();
                    ami = dcInfo.get(AmazonInfo.MetaDataKey.amiId);
                    zone = dcInfo.get(AmazonInfo.MetaDataKey.availabilityZone);
                }
                Integer count = amiCounts.get(ami);
                if (count != null) {
                    amiCounts.put(ami, count + 1);
                } else {
                    amiCounts.put(ami, 1);
                }
                count = zoneCounts.get(zone);
                if (count != null) {
                    zoneCounts.put(zone, count + 1);
                } else {
                    zoneCounts.put(zone, 1);
                }
                List<Pair<String, String>> list = instancesByStatus.get(status);
                if (list == null) {
                    list = new ArrayList<>();
                    instancesByStatus.put(status, list);
                }
                list.add(new Pair<>(id, url));
            }
            appData.put("amiCounts", amiCounts.entrySet());
            appData.put("zoneCounts", zoneCounts.entrySet());
            ArrayList<Map<String, Object>> instanceInfos = new ArrayList<>();
            appData.put("instanceInfos", instanceInfos);
            for (Iterator<Map.Entry<InstanceInfo.InstanceStatus, List<Pair<String, String>>>> iter = instancesByStatus
                    .entrySet().iterator(); iter.hasNext(); ) {
                Map.Entry<InstanceInfo.InstanceStatus, List<Pair<String, String>>> entry = iter
                        .next();
                List<Pair<String, String>> value = entry.getValue();
                InstanceInfo.InstanceStatus status = entry.getKey();
                LinkedHashMap<String, Object> instanceData = new LinkedHashMap<>();
                instanceInfos.add(instanceData);
                instanceData.put("status", entry.getKey());
                ArrayList<Map<String, Object>> instances = new ArrayList<>();
                instanceData.put("instances", instances);
                instanceData.put("isNotUp", status != InstanceInfo.InstanceStatus.UP);
                for (Pair<String, String> p : value) {
                    LinkedHashMap<String, Object> instance = new LinkedHashMap<>();
                    instances.add(instance);
                    instance.put("id", p.first());
                    String url = p.second();
                    instance.put("url", url);
                    boolean isHref = url != null && url.startsWith("http");
                    instance.put("isHref", isHref);

                }
            }
        }
        model.put("apps", apps);
    }
}

