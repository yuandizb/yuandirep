package com.micro.fast.mybatis.generator.extend.mojo;

import com.micro.fast.mybatis.generator.extend.service.GeneratorServiceController;
import com.micro.fast.mybatis.generator.extend.util.PropertiesUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.lang.reflect.Field;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;


@Mojo(name = "generateSC")
public class GeneratorServiceAndControllerMojo extends AbstractMojo {

    @Override
    public void execute() throws MojoExecutionException, MojoFailureException {
        //获取项目的绝对路径
        String projectPath =null;
        Map<String,Object> pluginContext = getPluginContext();
        Set set = pluginContext.entrySet();
        Iterator iterator = set.iterator();
        while (iterator.hasNext()){
            Object next = iterator.next();
            String s = next.toString();
            String[] split = s.split(" ");
            for (String s1 : split) {
                if (s1.endsWith("pom.xml")){
                    int lastIndexOf = s1.lastIndexOf("pom.xml");
                    projectPath = s1.substring(0,lastIndexOf-1);
                }
            }
        }
        System.out.println(projectPath);
        //加载插件所需要的配置文件
        new PropertiesUtil().loadProperties(projectPath);
        //生成service层controller层java类
        GeneratorServiceController.generator(projectPath);
    }
}
