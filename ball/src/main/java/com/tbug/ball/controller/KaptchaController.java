package com.tbug.ball.controller;

import java.awt.image.BufferedImage;
import java.io.IOException;
import java.util.Date;
import java.util.Properties;

import javax.imageio.ImageIO;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;

import com.google.code.kaptcha.Constants;
import com.google.code.kaptcha.Producer;
import com.google.code.kaptcha.impl.DefaultKaptcha;
import com.google.code.kaptcha.util.Config;

/**
 * 验证码生成
 *
 * @author fengshuonan
 * @date 2017-05-05 23:10
 */
@Controller
@RequestMapping("/kaptcha")
public class KaptchaController {

	private static final Logger logger = LoggerFactory.getLogger(KaptchaController.class);
	
	private static Producer producer;
	
	static {
        Properties properties = new Properties();
        properties.put(Constants.KAPTCHA_BORDER, "no");
        properties.put(Constants.KAPTCHA_BORDER_COLOR, "105,179,90");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_COLOR, "black");
        properties.put(Constants.KAPTCHA_IMAGE_WIDTH, "102");
        properties.put(Constants.KAPTCHA_IMAGE_HEIGHT, "45");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_SIZE, "30");
        properties.put(Constants.KAPTCHA_SESSION_CONFIG_KEY, "code");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_CHAR_LENGTH, "4");
        properties.put(Constants.KAPTCHA_TEXTPRODUCER_FONT_NAMES, "宋体,楷体,微软雅黑");
        properties.put(Constants.KAPTCHA_NOISE_IMPL, "com.google.code.kaptcha.impl.NoNoise");
        Config config = new Config(properties);
        DefaultKaptcha defaultKaptcha = new DefaultKaptcha();
        defaultKaptcha.setConfig(config);
        
        producer = defaultKaptcha;
	}
	
    /**
     * 生成验证码
     */
    @GetMapping("")
    public void index(HttpServletRequest request, HttpServletResponse response) {
        HttpSession session = request.getSession();

        response.setDateHeader("Expires", 0);

        // Set standard HTTP/1.1 no-cache headers.
        response.setHeader("Cache-Control", "no-store, no-cache, must-revalidate");

        // Set IE extended HTTP/1.1 no-cache headers (use addHeader).
        response.addHeader("Cache-Control", "post-check=0, pre-check=0");

        // Set standard HTTP/1.0 no-cache header.
        response.setHeader("Pragma", "no-cache");

        // return a jpeg
        response.setContentType("image/jpeg");

        // create the text for the image
        String capText = producer.createText();

        // store the text in the session
        session.setAttribute(Constants.KAPTCHA_SESSION_KEY, capText);
        session.setAttribute(Constants.KAPTCHA_SESSION_CONFIG_DATE, new Date().getTime());
        
        // create the image with the text
        BufferedImage bi = producer.createImage(capText);
        ServletOutputStream out = null;
        try {
            out = response.getOutputStream();
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
        }

        // write the data out
        try {
            ImageIO.write(bi, "jpg", out);
        } catch (IOException e) {
        	logger.error(e.getMessage(), e);
        }
        try {
            try {
                out.flush();
            } catch (IOException e) {
            	logger.error(e.getMessage(), e);
            }
        } finally {
            try {
                out.close();
            } catch (IOException e) {
            	logger.error(e.getMessage(), e);
            }
        }
    }

}
