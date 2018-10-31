package com.swq.Controller;


import com.swq.WeiXinBasePackage.BaseController.WechatController;
import com.swq.WeiXinBasePackage.BaseController.WechatController;
import org.dom4j.DocumentException;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;

/**
 * 微信总入口  一样的接口 一个get 用于验证，一个post用于接受请求
 * @description: 微信接入控制器

 */
@Controller
@RequestMapping(value = "/enter")
public class WechatEnterController extends WechatController {
    /**
     *
     * @param response
     * @param echostr
     * @param signature
     * @param timestamp
     * @param nonce
     * @description: 处理接入时的校验
     * @date: 2016年2月24日 下午3:50:41
     *
     */
    @RequestMapping(method = RequestMethod.GET)
    public void signature(HttpServletResponse response, String echostr, String signature, String timestamp, String nonce) {
        super.signature(response, echostr, signature, timestamp, nonce);
    }

    /**
     *
     * @param xml
     * @param response
     * @throws IOException
     * @throws IllegalAccessException
     * @throws IllegalArgumentException
     * @throws InvocationTargetException
     * @throws DocumentException
     * @description: 处理微信消息的交互
     * @date: 2016年2月24日 下午3:50:51
     *
     */
    @RequestMapping(method = RequestMethod.POST)
    public void listen(@RequestBody String xml, HttpServletResponse response) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException {
        super.listen(xml, response);
    }
}
