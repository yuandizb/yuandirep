package com.micro.fast.mybatis.generator.extend.mojo;


import com.micro.fast.mybatis.generator.extend.service.MybatisGeneratorExtend;
import com.micro.fast.mybatis.generator.extend.util.PropertiesUtil;
import org.apache.maven.plugin.AbstractMojo;
import org.apache.maven.plugin.MojoExecutionException;
import org.apache.maven.plugin.MojoFailureException;
import org.apache.maven.plugins.annotations.Mojo;

import java.util.Iterator;
import java.util.Map;
import java.util.Set;

@Mojo(name = "generateFiles")
public class MybatisGeneratorExtendMojo extends AbstractMojo {


  @Override
  public void execute() throws MojoExecutionException, MojoFailureException {
    String projectPath =null;
    Map pluginContext = getPluginContext();
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
    //加载插件所需要的配置文件
    new PropertiesUtil().loadProperties(projectPath);
    MybatisGeneratorExtend.generateMybatisGeneratorNeedFilesByTemplates(projectPath);
  }

}
