package com.micro.fast.util;

import java.io.File;

/**
 * 文件操作工具类
 * @author lishouyu 18332763730@163.com 2017/08/27
 * @version 1.0
 * @since 1.0
 */
public class FileUtil {
  /**
   * 修改文件夹下的所有符合条件的文件的扩展名，递归文件夹下所有文件
   * @param dir              文件夹的路径
   * @param oldExtensionName 旧的文件扩展名 例如：.jpg
   * @param newExtensionName 新的文件扩展名 例如：.png
   */
  public static void modifyFileExtensionNameInDir(File dir, String oldExtensionName, String newExtensionName) {
    //1.判断输入的文件是否是文件夹
    boolean isdirectory = dir.isDirectory();
    if (isdirectory == true) {
      //获取目录下的所有的文件以及目录
      File[] files = dir.listFiles();
      if (files != null && files.length > 0) {
        for (File file : files) {
          if (file.isDirectory()) {
            modifyFileExtensionNameInDir(file, oldExtensionName, newExtensionName);
          } else {
            modifyFileExtensionName(file,oldExtensionName,newExtensionName);
          }
        }
      }
    } else {
      File file = dir;
      modifyFileExtensionName(file,oldExtensionName,newExtensionName);
    }
  }

  /**
   * 修改单个文件的扩展名
   * @param file　文件对象
   * @param oldExtensionName　旧的文件扩展名 例如：.jpg
   * @param newExtensionName　新的文件扩展名 例如：.png
   */
  public static void modifyFileExtensionName(File file, String oldExtensionName, String newExtensionName) {
    String fileName = file.getName();
    if (fileName.endsWith(oldExtensionName)) {
      //去掉扩展名
      int lastIndexOfPoint = fileName.lastIndexOf('.');
      String newFilename = fileName.substring(0, lastIndexOfPoint);
      //重命名文件
      file.renameTo(new File(file.getParent() + "/" + newFilename + newExtensionName));
    }
  }

}