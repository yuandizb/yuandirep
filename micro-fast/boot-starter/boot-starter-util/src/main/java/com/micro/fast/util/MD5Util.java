package com.micro.fast.util;

import java.io.UnsupportedEncodingException;
import java.security.MessageDigest;

/**
 * MD5加密类
 * @author lishouyu 18332763730@163.com 2017/08/21
 * @version 1.0
 * @since 1.0
 */
public class MD5Util {
    /**
     * 对字节流进行单向MD5加密
     * @param bytes 字节数组
     * @return String类型返回值
     */
    private static String MD5EncodeUtf8(byte[] bytes) {
        //用于加密的字符
        char md5String[] = {'0', '1', '2', '3', '4', '5', '6', '7', '8', '9',
                'A', 'B', 'C', 'D', 'E', 'F'};
        try {
            //信息摘要是安全的单向哈希函数，它接受任意大小的数据，并输出固定长度的哈希值
            MessageDigest messageDigest = MessageDigest.getInstance("MD5");
            //MessageDigest 对象通过使用update方法处理数据，使用指定的byte数组更新摘要
            messageDigest.update(bytes);
            //摘要更新之后。通过调用digest()计算哈希值
            byte[] digest = messageDigest.digest();
            // 把密文转换成十六进制的字符串形式
            int j = digest.length;
            char str[] = new char[j * 2];
            int k = 0;
            for (byte byte0 : digest) {   //  i = 0
                str[k++] = md5String[byte0 >>> 4 & 0xf];    //    5
                str[k++] = md5String[byte0 & 0xf];   //   F
            }
            return new String(str);
        } catch (Exception e) {
            //发生异常的时候返回为空
            return null;
        }
    }
    /**
     * 对字符串进行Md5加密
     * @param string 传入的字符串参数
     * @return String型返回结果
     */
    public static String MD5EncodeStrUtf8(String string){
        byte[] bytes = new byte[0];
        try {
            bytes = string.getBytes("utf8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return MD5EncodeUtf8(bytes);
    }
}
