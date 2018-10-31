package com.swq.WeiXinBasePackage.util;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class WeixinUtil {

    private static Logger logger = LoggerFactory.getLogger(WeixinUtil.class);
    private final static String URL_getAccessToken = "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=${appid}&secret=${secret}";
    private final static String URL_getJsapiTicket = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?access_token=${access_token}&type=jsapi";
    private final static String URL_getIpList = "https://api.weixin.qq.com/cgi-bin/getcallbackip?access_token=${access_token}";
    // 发送模板消息的地址
    private final static String URL_sendTemplateMsg = "https://api.weixin.qq.com/cgi-bin/message/template/send?access_token=${access_token}";

    /**
     * 从腾讯获取AccessToken
     * 
     * @param appid
     * @param appsecret
     * @return
     */
    public static String getAccessToken(String appid, String appsecret) {
        String url = URL_getAccessToken.replace("${appid}", appid).replace("${secret}", appsecret);
        String result = HttpUtil.httpGet(url);
        logger.info("====WeixinUtil====");
        logger.info("从微信平台更新appid:=====" + appid);
        logger.info("从微信平台更新appsecret:=====" + appsecret);
        logger.info("从微信平台更新result:=====" + result);
        logger.info("=====================");
        if (StringUtils.isBlank(result)) {
            logger.error("====获取AccessToken出错,无返回====");
            return "";
        }
        JSONObject json = JSONObject.parseObject(result);
        Integer errcode = json.getInteger("errcode");
        if (errcode != null && errcode != 0) {
            logger.error("====获取AccessToken出错====");
            logger.error(result);
            logger.error("======================");
            return "";
        }
        return json.getString("access_token");
    }

    /**
     * 从腾讯获取JsapiTicket
     * 
     * @param access_token
     * @return
     */
    public static String getJsapiTicket(String access_token) {
        String url = URL_getJsapiTicket.replace("${access_token}", access_token);
        String result = HttpUtil.httpGet(url);
        logger.info("====WeixinUtil====");
        logger.info("获取jsapi_ticket:=====" + result);
        logger.info("=====================");
        if (StringUtils.isBlank(result)) {
            logger.error("====获取jsapi_ticket出错,无返回====");
            return null;
        }
        JSONObject json = JSONObject.parseObject(result);
        Integer errcode = json.getInteger("errcode");
        if (errcode != null && errcode != 0) {
            logger.error("====获取AccessToken出错====");
            logger.error(result);
            logger.error("======================");
            return null;
        }
        return json.getString("ticket");
    }

    /**
     * 从腾讯获取ip白名单
     * 
     * @param accessToken
     * @return
     */
    public static JSONArray getIpList(String accessToken) {
        String url = URL_getIpList.replace("${access_token}", accessToken);
        String result = HttpUtil.httpGet(url);
        JSONObject json = JSONObject.parseObject(result);
        return json.getJSONArray("ip_list");
    }

    /**
     * 发送模板消息
     * 
     * @param accessToken
     * @param postContent
     * @return
     */
    public static JSONObject sendTemplateMsg(String accessToken, String postContent) {
        String url = URL_sendTemplateMsg.replace("${access_token}", accessToken);
        String result = HttpUtil.httpPost(url, postContent);
        if (StringUtils.isBlank(result)) {
            logger.error("====发送模板消息出错,无返回====");
            return null;
        }
        JSONObject json = JSONObject.parseObject(result);
        Integer errcode = json.getInteger("errcode");
        if (errcode != null && errcode != 0) {
            logger.error("====发送模板消息出错====");
            logger.error(result);
            logger.error("===================");
            return null;
        }
        return json;
    }

}
