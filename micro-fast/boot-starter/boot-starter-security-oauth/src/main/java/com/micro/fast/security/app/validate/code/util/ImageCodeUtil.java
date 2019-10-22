package com.micro.fast.security.app.validate.code.util;

import com.micro.fast.security.app.validate.code.config.param.ImageCodeProperties;
import com.micro.fast.security.app.master.SecurityProperties;
import com.micro.fast.security.app.validate.code.pojo.ImageCode;
import org.springframework.web.bind.ServletRequestUtils;
import org.springframework.web.context.request.ServletWebRequest;

import java.awt.*;
import java.awt.image.BufferedImage;
import java.util.Random;

/**
 * 图片验证码工具类
 * @author lsy
 */
public class ImageCodeUtil implements ValidateCodeUtil{

    private SecurityProperties securityProperties;

    /**
     *  证码生成类
     */
    @Override
    public ImageCode createValidateCode(ServletWebRequest request){
        ImageCodeProperties imageCodeProperties = securityProperties.getCode().getImage();
        //图片验证码的宽度
        int width = ServletRequestUtils.getIntParameter(request.getRequest(),"width",imageCodeProperties.getWidth());
        //图片验证码的高度
        int height =ServletRequestUtils.getIntParameter(request.getRequest(),"height",imageCodeProperties.getHeight());
        //生成图片
        BufferedImage image = new BufferedImage(width, height, BufferedImage.TYPE_INT_RGB);
        Graphics g = image.getGraphics();
        Random random = new Random();
        g.setColor(getRandColor(200, 250));
        g.fillRect(0, 0, width, height);
        g.setFont(new Font("Times New Roman", Font.ITALIC, 20));
        g.setColor(getRandColor(160, 200));
        int i1 = 155;
        for (int i = 0; i < i1; i++) {
            int x = random.nextInt(width);
            int y = random.nextInt(height);
            int xl = random.nextInt(12);
            int yl = random.nextInt(12);
            g.drawLine(x, y, x + xl, y + yl);
        }
        //获取验证码数字
        String sRand = "";
        //验证码的位数不能在请求中进行设置
        for (int i = 0; i < imageCodeProperties.getLength(); i++) {
            String rand = String.valueOf(random.nextInt(10));
            sRand += rand;
            g.setColor(new Color(20 + random.nextInt(110), 20 + random.nextInt(110), 20 + random.nextInt(110)));
            g.drawString(rand, 13 * i + 6, (height-20)/2+20-2);
        }
        g.dispose();
        //设置多少时间过期返回图片验证码对象
        return new ImageCode(image, sRand, imageCodeProperties.getExpireIn());
    }

    /**
     * 生成随机背景条纹
     * @param fc
     * @param bc
     * @return
     */
    private Color getRandColor(int fc, int bc) {
        Random random = new Random();
        int i = 255;
        if (fc > i) {
            fc = i;
        }
        if (bc > i) {
            bc = i;
        }
        int r = fc + random.nextInt(bc - fc);
        int g = fc + random.nextInt(bc - fc);
        int b = fc + random.nextInt(bc - fc);
        return new Color(r, g, b);
    }

    public SecurityProperties getSecurityProperties() {
        return securityProperties;
    }

    public void setSecurityProperties(SecurityProperties securityProperties) {
        this.securityProperties = securityProperties;
    }
}
