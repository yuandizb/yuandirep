package com.micro.fast.util;




import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.UUID;

/**
 * 常用工具类包
 * @author lishouyu 18332763730@163.com 2017/09/05
 * @version 1.0
 * @since 1.0
 */
public class WatermarkerUtil {

  /**
   * 默认的字体
   */
  private  String fontFamily = "微软雅黑";
  /**
   * 默认的字体样式
   */
  private  int fontStyle = Font.BOLD;
  /**
   * 默认的文字大小
   */
  private  int fontSize = 12;
  /**
   * 默认的字体颜色
   */
  private  Color fontColor = Color.WHITE;
  /**
   * 默认的水印左侧距离原图最左边的距离
   */
  private  int x = 5;
  /**
   * 默认的水印的下侧距离原图下侧的距离
   */
  private  int y = 5;
  /**
   * 默认的透明度
   */
  private  float alpha = 0.9F;

  /**
   * 为图片添加文字水印
   * @param text 要添加的水印的文字内容
   * @param inputImage 输入的图片文件
   * @param filePath 生成图片的输出路径
     * @return　file　加上水印之后的图片对象
   */
  public File markerWord(String text,File inputImage,String filePath){
    String imageName = inputImage.getName();
    File file;
    try {
      //1.将输入的图片文件解码为图片格式
      Image image = ImageIO.read(inputImage);
      //2.获取原图的宽度和高度
      int width = image.getWidth(null);
      int height = image.getHeight(null);
      //3.创建BufferImage对象用于存放原图片.图片的高度和宽度等于原图片的高度和宽度,颜色采用rbg模式
      BufferedImage bufferedImage = new BufferedImage(width,height,BufferedImage.TYPE_INT_RGB);
      //4.使用bufferImage对象创建画图工具对象,并将原图绘制到bufferImage中
      Graphics2D graphics = bufferedImage.createGraphics();
      graphics.drawImage(image,0,0,width,height,null);
      //5.设置水印字体的样式
      graphics.setFont(new Font(fontFamily,fontStyle,fontSize));
      graphics.setColor(fontColor);
      //6.设置水印图片的位置
      //6.1获取原图片和水印的高度以及宽度差,调整图片的位置
      int textLength = getTextLength(text);
      int widthDiff = width-textLength;
      int heightDiff = height-getFontSize();
      if (widthDiff<x){
        x=widthDiff;
      }
      if (heightDiff<y){
        y=heightDiff;
      }
      //7.设置水印透明的样式
      graphics.setComposite(AlphaComposite.getInstance(AlphaComposite.SRC_ATOP,alpha));
      //8.将文字画在图片之上,放在右下角.参数x表示是文字的左侧距离图片左侧的距离,文字的右侧表示是文字的下侧距离图片上侧的距离
      graphics.drawString(text,x,height-(y));
      graphics.dispose();
      //9.将bufferImage输出成文件,使用uuid避免文件名重复
      file = new File(filePath + "/" + UUID.randomUUID()+ imageName);
      int lastIndexOf = imageName.lastIndexOf(".");
      String extensionName = imageName.substring(lastIndexOf+1);
      ImageIO.write(bufferedImage,extensionName,new FileOutputStream(file));
      //10.返回加上水印之后的图片文件对象
      return file;
    } catch (IOException e) {
      e.printStackTrace();
    }
    //11.发生异常的时候就返回null
    return null;
  }

  /**
   * 计算文本的长度 返回值是向上取整后的数值
   * 一个英文的宽度等于0.5*fontSize 英文字符的字节码小于1
   * 一个中为跨度等于1*fontSize 中文字符的字节码大于1
   * @param text　要输入的文本
   * @return　　转换成中文的宽度单位px
   */
  public int getTextLength(String text) throws UnsupportedEncodingException {
    float textWidth = 0.0F;
    //1.计算等同于多少个中文字符
    for (int i = 0; i <text.length() ; i++) {
      String s = String.valueOf(text.charAt(i));
      if (s.getBytes("utf-8").length>1){
        textWidth++;
      }else{
        textWidth +=0.5F;
      }
    }

    //2.等同的中文字符向上取整然后乘于单个中文字符的宽度,
    return  Math.round(textWidth)*fontSize;
  }

  public String getFontFamily() {
    return fontFamily;
  }

  public void setFontFamily(String fontFamily) {
    this.fontFamily = fontFamily;
  }

  public int getFontStyle() {
    return fontStyle;
  }

  public void setFontStyle(int fontStyle) {
    this.fontStyle = fontStyle;
  }

  public int getFontSize() {
    return fontSize;
  }

  public void setFontSize(int fontSize) {
    this.fontSize = fontSize;
  }

  public Color getFontColor() {
    return fontColor;
  }

  public void setFontColor(Color fontColor) {
    this.fontColor = fontColor;
  }

  public int getX() {
    return x;
  }

  public void setX(int x) {
    this.x = x;
  }

  public int getY() {
    return y;
  }

  public void setY(int y) {
    this.y = y;
  }

  public float getAlpha() {
    return alpha;
  }

  public void setAlpha(float alpha) {
    this.alpha = alpha;
  }
}
