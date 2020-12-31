package com.zgp.demo.controller;


import cn.hutool.core.codec.Base64;
import cn.hutool.core.util.RandomUtil;
import com.google.code.kaptcha.Producer;
import com.zgp.demo.common.Constants;
import com.zgp.demo.common.IdUtils;
import com.zgp.demo.common.RedisCache;
import com.zgp.demo.entity.AjaxResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.FastByteArrayOutputStream;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.imageio.ImageIO;
import javax.servlet.http.HttpServletResponse;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

/***
 * 验证码 生成器
 */
@RestController
public class CaptchaController {

    //验证码类型
    @Value("${maill.captchaType}")
    private String captchaType;

    @Resource(name = "captchaProducerMath")
    private Producer captchaProducerMath;

    @Resource(name = "captchaProducer")
    private Producer captchaProducer;


    @Autowired
    private RedisCache redisCache;


    @GetMapping("/captchaImage")
    public AjaxResult getCode(HttpServletResponse response){
        //保存验证码信息
        String uuid = IdUtils.simpleUUID();
        String verifyKey = Constants.CAPTCHA_CODE_KEY + uuid;
        String capStr = null, code = null;
        BufferedImage image = null;

        if ("maths".equals(captchaType)){
            String capText = captchaProducerMath.createText();
            capStr = capText.substring(0, capText.lastIndexOf("@"));
            code = capText.substring(capText.lastIndexOf("@") + 1);
            image = captchaProducerMath.createImage(capStr);
        }else if("char".equals(captchaType)){
            capStr = code = captchaProducer.createText();
            image = captchaProducer.createImage(capStr);
        }

        redisCache.setCacheObject(verifyKey, code, Constants.CAPTCHA_EXPIRATION, TimeUnit.MINUTES);
        // 转换流信息写出
        FastByteArrayOutputStream os = new FastByteArrayOutputStream();
        try
        {
            ImageIO.write(image, "jpg", os);
        }
        catch (IOException e)
        {
            return AjaxResult.error(e.getMessage());
        }

        AjaxResult ajax = AjaxResult.success();
        ajax.put("uuid", uuid);
        ajax.put("img", Base64.encode(os.toByteArray()));
        return ajax;
    }

}















































