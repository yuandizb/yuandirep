package com.micro.fast.mybatis.generator.extend.util;

import freemarker.core.ParseException;
import freemarker.template.*;
import org.apache.commons.lang3.StringUtils;

import java.io.*;
import java.util.Map;

/**
 * 根据freemarker模板生成文件
 */
public class FreemarkerUtil {

  /**
   * 加载模板文件所在的目录
   */
  private static final String TEMPLATES_DIR_URL = "templates";

  private static Configuration cfg = null;

  static {
    if (cfg==null){
      cfg = new Configuration();
    }
    //设置末班文件的加载目录
    setConfiguration(FreemarkerUtil.TEMPLATES_DIR_URL);
  }

  /**
   *　设置freemarker模板引擎配置对象的参数
   * @param templatesDirUrl　相对于mybatis-generate-extend项目的classpath的相对路径
   */
  private static void setConfiguration(String templatesDirUrl) {

    //1.设置编码
    cfg.setDefaultEncoding("utf-8");
    //２.解压所需要的jar中的文件，设置加载模板文件的位置
    JarUtil.unThisJar();
    try {
      cfg.setDirectoryForTemplateLoading(new File(JarUtil.getThisJarPathParent()+"/"+templatesDirUrl));
    } catch (IOException e) {
      e.printStackTrace();
    }
  }

  /**
   * 根据freemarker模板在当前项目的classpath路径下生成.xml文件
   * @param templateName
   * @param data
   * @param targetFileName
   * @return
   */
  public static boolean createFile(String templateName, Map<String, Object> data, String targetFileName,String projectPath,String packageName,String isOverWrite) {
    Template template = null;
    Writer out = null;
    //1.获取当前项目classpath路径
    String resourcesDir = projectPath+"/"+"src/main/resources";
    File file = new File(resourcesDir);
    if (!file.exists()){
      try {
        file.createNewFile();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
    // 判断生成文件的路径
    String targetFileUrl = null;
    if (packageName ==null){
      // 默认生成在resources文件下
      targetFileUrl = resourcesDir + "/" + targetFileName;
    }else{
      String packageRelativeDir = packageName.replaceAll("\\.","/");
      String packageDir = projectPath+"/src/main/java/"+packageRelativeDir;
        File file1 = new File(packageDir);
        // 创建包
        if (!file1.exists()){
            file1.mkdirs();
        }
        targetFileUrl = packageDir+"/"+targetFileName;
    }
    //2.如果目标文件存在就先删除目标文件
      boolean isCreate = true;
      File targetFile = new File(targetFileUrl);
      if (StringUtils.isNotBlank(isOverWrite)&& isOverWrite.equals("true")){
          if (targetFile.exists()){
              targetFile.delete();
          }
          isCreate = true;
      }else{
          if (targetFile.exists()){
              // 有的话就不生成
              isCreate =false;
          }else{
              // 没有的话就生成
              isCreate = true;
          }
      }
   if (isCreate){
       try {
           //３.获取模板文件对象
           template = cfg.getTemplate(templateName);
           //4.获取输出流对象
           out = new BufferedWriter(new OutputStreamWriter(new FileOutputStream(targetFileUrl)));
           //5.向模板对象中填充数据生成文件
           template.process(data,out);
       } catch (FileNotFoundException e) {
           e.printStackTrace();
           return false;
       } catch (MalformedTemplateNameException e) {
           e.printStackTrace();
           return false;
       } catch (ParseException e) {
           e.printStackTrace();
           return false;
       } catch (IOException e) {
           e.printStackTrace();
           return false;
       } catch (TemplateException e) {
           e.printStackTrace();
           return false;
       } finally {
           try {
               out.flush();
               out.close();
           } catch (IOException e) {
               e.printStackTrace();
               return false;
           }
       }
   }
    //创建成功
    return true;
  }

  public static void main(String[] args) {
    //１．获取类的加载根目录,这个路径会是这个方法在哪个项目中被调用，就是哪个项目的类加载根路径
    String path = FreemarkerUtil.class.getResource("/").getPath();
    String parent = new File(path).getParent();
    String parent1 = new File(parent).getParent();
    System.out.println(path);
    System.out.println(parent1);
    //２.获取当前类的加载目录,这个目录会是确定的是这个类真实在哪，这个相对路径就是哪里,这个类在jar包里，路径就是这个类在jar包中的路径
    String path1 = FreemarkerUtil.class.getResource("").getPath();
    System.out.println(path1);
    //3.获取当前项目的路径
    File file = new File("");
    try {
      String path2 = file.getCanonicalPath();
      System.out.println(path2);
    } catch (IOException e) {
      e.printStackTrace();
    }
    //4.获取当前工程路径
    System.out.println(System.getProperty("user.dir"));
    //5.获取所有的类的jar的加载路径
    System.out.println(System.getProperty("java.class.path"));
  }
}
