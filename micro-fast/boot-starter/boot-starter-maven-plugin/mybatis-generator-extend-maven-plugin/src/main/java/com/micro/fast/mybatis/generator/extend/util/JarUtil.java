package com.micro.fast.mybatis.generator.extend.util;


import java.io.*;
import java.util.jar.JarEntry;
import java.util.jar.JarFile;
import java.util.jar.JarInputStream;

public class JarUtil {

  /**
   * 获取当前jar的绝对路径
   * @return
   */
  public static String getThisJarPath(){
    return JarUtil.class.getProtectionDomain().getCodeSource().getLocation().getPath();
  }

  /**
   * 获取当前jar绝对路径的父路径
   * @return
   */
  public static String getThisJarPathParent(){
    return new File(getThisJarPath()).getParent();
  }

  /**
   * 解压当前jar中所需要的文件
   */
  public static void unThisJar(){
    JarInputStream jarInputStream = null;
    JarFile jarFile = null;
    try {
      //1.获取当前项目jar包的路径
      jarInputStream = new JarInputStream(new FileInputStream(new File(getThisJarPath())));
      jarFile = new JarFile(new File(getThisJarPath()));
      JarEntry jarEntry = null;
      while ((jarEntry = jarInputStream.getNextJarEntry())!=null){
          String fileName = jarEntry.getName();
          //解压.ftl模板
          if (fileName.endsWith(".ftl")){
            File file = new File(getThisJarPathParent() +"/"+fileName);
            if (file.exists()){
              file.delete();
            }
            if (!new File(file.getParent()).exists()){
              new File(file.getParent()).mkdirs();
            }
            InputStream inputStream = null;
            OutputStream outputStream = null;
            try {
              outputStream = new FileOutputStream(new File(getThisJarPathParent()+"/"+fileName));
              inputStream = jarFile.getInputStream(jarEntry);
              int l = 0;
              while ((l= inputStream.read())!=-1){
                outputStream.write(l);
                outputStream.flush();
              }
            }finally {
              outputStream.close();
              inputStream.close();
            }
          }
      }
    } catch (IOException e) {
      e.printStackTrace();
    }finally {
      try {
        jarInputStream.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
      try {
        jarFile.close();
      } catch (IOException e) {
        e.printStackTrace();
      }
    }
  }
}
