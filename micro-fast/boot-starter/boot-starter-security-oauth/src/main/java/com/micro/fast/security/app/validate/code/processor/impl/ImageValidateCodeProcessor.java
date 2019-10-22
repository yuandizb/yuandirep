package com.micro.fast.security.app.validate.code.processor.impl;

import com.micro.fast.security.app.validate.code.pojo.ImageCode;
import com.micro.fast.security.app.validate.code.pojo.ValidateCode;
import org.springframework.stereotype.Component;
import org.springframework.web.context.request.ServletWebRequest;

import javax.imageio.ImageIO;
import java.io.IOException;

/**
 * 图片验证码逻辑类
 * @author lsy
 */
@Component("imageValidateCodeProcessor")
public class ImageValidateCodeProcessor extends AbstractValidateCodeProcessor {
    @Override
    public void send(ServletWebRequest servletWebRequest, ValidateCode validateCode) throws IOException {
        ImageCode imageCode = (ImageCode)validateCode;
        //将验证码写入到响应之中
        ImageIO.write(imageCode.getImage(),"JPEG",servletWebRequest.getResponse().getOutputStream());
    }
}
