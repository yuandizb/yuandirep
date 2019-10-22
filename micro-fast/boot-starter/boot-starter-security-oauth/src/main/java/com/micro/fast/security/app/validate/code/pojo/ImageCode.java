package com.micro.fast.security.app.validate.code.pojo;

import java.awt.image.BufferedImage;
import java.io.Serializable;

/**
 * 图片验证码
 * @author lsy
 */
public class ImageCode extends ValidateCode implements Serializable{


    private static final long serialVersionUID = -1389697804518380230L;
    /**
     * 图片
     */
    private BufferedImage image;



    public ImageCode() {

    }

    /**
     * @param image 验证码图片对象
     * @param code 验证码
     * @param inSeconds 在多长时间内过期
     */
    public ImageCode(BufferedImage image, String code, int inSeconds) {
        super(code,inSeconds);
        this.image = image;
    }

    public BufferedImage getImage() {
        return image;
    }

    public void setImage(BufferedImage image) {
        this.image = image;
    }

}
