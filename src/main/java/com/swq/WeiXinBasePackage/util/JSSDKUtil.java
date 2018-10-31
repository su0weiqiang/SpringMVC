package com.swq.WeiXinBasePackage.util;

import org.apache.commons.codec.binary.Hex;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class JSSDKUtil {

	private static Logger logger = LoggerFactory.getLogger(JSSDKUtil.class);
	private static String jsapi_ticket;// 调用微信JS接口的临时票据
	public static long expireTime = 0;

	public synchronized static String getJsapiTicket() {
		if (StringUtils.isBlank(jsapi_ticket) || System.currentTimeMillis() > expireTime) {
			String access_token = AccessTokenUtil.getAccessToken();
			jsapi_ticket = WeixinUtil.getJsapiTicket(access_token);
			if (StringUtils.isBlank(jsapi_ticket)) {
				logger.error("获取jsapi_ticket异常");
			}
			expireTime = System.currentTimeMillis() + 7000000;
		}
		return jsapi_ticket;
	}

	public static String getSignature(String noncestr, String timestamp, String url) throws Exception {
		String jsapi_ticket = getJsapiTicket();
		String st = "jsapi_ticket=" + jsapi_ticket + "&noncestr=" + noncestr + "&timestamp=" + timestamp + "&url=" + url;
		try {
			MessageDigest digest = MessageDigest.getInstance("SHA1");
			digest.update(st.getBytes());
			byte[] bytes = digest.digest();
			return Hex.encodeHexString(bytes);
		} catch (NoSuchAlgorithmException e) {
			logger.error(e.getMessage(), e);
			return null;
		}
	}

}