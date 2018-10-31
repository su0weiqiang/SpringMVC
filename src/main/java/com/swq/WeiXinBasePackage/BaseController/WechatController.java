package com.swq.WeiXinBasePackage.BaseController;

import com.swq.WeiXinBasePackage.domain.RequestMessage;
import com.swq.WeiXinBasePackage.domain.ResponseMessage;
import com.swq.WeiXinBasePackage.domain.ResponseTextMessage;
import com.swq.WeiXinBasePackage.enums.RequestMessageEnum;
import com.swq.WeiXinBasePackage.exception.NoMatchedMsgException;
import com.swq.WeiXinBasePackage.manager.MessageManager;
import org.dom4j.DocumentException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.apache.commons.codec.binary.Hex;
import org.springframework.beans.factory.annotation.Value;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.Writer;
import java.lang.reflect.InvocationTargetException;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.util.Arrays;

public class WechatController {

    @Value("${token}")
    protected String token;

    @Resource
    protected MessageManager messageManager;


    private Logger logger = LoggerFactory.getLogger(WechatController.class);

    public void signature(HttpServletResponse response, String echostr, String signature, String timestamp, String nonce) {
        logger.info("=========腾讯接入响应GET==========");
        logger.info("echostr:" + echostr);
        logger.info("signature:" + signature);
        logger.info("timestamp:" + timestamp);
        logger.info("nonce:" + nonce);
        logger.info("=============================");
        boolean flag=checkSignature(nonce, timestamp, signature, token);
        if (flag) {
            write(response, echostr);
        } else {
            write(response, "false");
        }
    }

    // 向微信服务器写消息
    private void write(HttpServletResponse response, String content) {
        response.setCharacterEncoding("utf-8");
        try {
            Writer writer = response.getWriter();
            writer.write(content);
            writer.flush();
            writer.close();
        } catch (IOException e) {
            logger.error(e.getMessage(), e);
        }
    }

    // 微信验证算法
    protected boolean checkSignature(String nonce, String timestamp, String signature, String token) {
        if (token == null) {
            logger.error("验证失败，未给token赋值。");
            return false;
        }
        String[] array = { token, nonce, timestamp };
        Arrays.sort(array);

        StringBuffer sb = new StringBuffer("");
        for (String s : array) {
            sb.append(s);
        }
        try {
            MessageDigest digest = MessageDigest.getInstance("SHA1");
            digest.update(new String(sb).getBytes());
            byte[] bytes = digest.digest();
            String result = Hex.encodeHexString(bytes);
            if (result.equals(signature)) {
                return true;
            }
            return false;
        } catch (NoSuchAlgorithmException e) {
            logger.info(e.getMessage(), e);
            return false;
        }
    }


    public void listen(String xml, HttpServletResponse response) throws IOException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, DocumentException {
        logger.info("=========腾讯接入响应POST==========");
        logger.info(xml);
        logger.info("==============================");
        RequestMessage requestMessage = new RequestMessage(xml);
        RequestMessageEnum msgType = requestMessage.getMsgType();
        logger.info("=========消息类型==========");
        logger.info("post get:" + msgType);
        logger.info("========================");
        try {
            ResponseMessage entity;
            switch (msgType) {
                case TEXT_MESSAGE:// 文本消息
                    logger.info("=========文本消息==========");


                    entity = messageManager.getTextInfo(requestMessage);
                    break;
                case EVENT_MESSAGE:// 事件
                    logger.info("=========事件==========");
                    entity = messageManager.getEventInfo(requestMessage);
                    break;
                case LOCATION_MESSAGE:// 地理位置消息
                    logger.info("========= 地理位置消息==========");
                    entity = messageManager.getLocationInfo(requestMessage);
                    break;
                case IMAGE_MESSAGE:// 图片消息
                    logger.info("========= 图片消息==========");
                    entity = messageManager.getImageInfo(requestMessage);
                    break;
                case LINK_MESSAGE:// 链接消息
                    logger.info("========= 链接消息==========");
                    entity = messageManager.getLinkInfo(requestMessage);
                    break;
                case VIDEO_MESSAGE:// 视频消息
                    logger.info("========= 视频消息==========");
                    entity = messageManager.getVideoInfo(requestMessage);
                    break;
                case SHORTVIDEO_MESSAGE:// 小视频消息
                    logger.info("========= 小视频消息==========");
                    entity = messageManager.getVideoInfo(requestMessage);
                    break;
                case VOICE_MESSAGE:// 语音消息
                    logger.info("========= 语音消息==========");
                    entity = messageManager.getVoiceInfo(requestMessage);
                    break;
                default:
                    entity = null;
                    break;
            }
            if (entity == null) {
                // XXX
                logger.error("======发送微信消息失败=======");
//				write(response, failedMessage(requestMessage).toString());
                write(response, "success");
            } else {
                logger.info("==发送微信消息:" + entity.toString());
                write(response, entity.toString());//
            }
        } catch (NoMatchedMsgException e) {
            write(response, failedMessage(requestMessage).toString());
            logger.error(e.getMessage(), e);
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
    }
    // 未定义消息类型
    protected ResponseMessage failedMessage(RequestMessage requestMessage) {
        ResponseTextMessage responseMessage = new ResponseTextMessage(requestMessage);
        responseMessage.setTextContent("此功能尚未启用 ...");
        return responseMessage;
    }
}
