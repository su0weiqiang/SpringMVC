package com.swq.WeiXinBasePackage.util;

import com.swq.util.PropertyConfigUtil;
import com.swq.util.PropertyConfigUtil;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class AccessTokenUtil {

	private static final Logger logger = LoggerFactory.getLogger(AccessTokenUtil.class);

	public static String access_token = "";// 调用接口的凭据
	public static long expireTime = 0;

	private static final String appid = PropertyConfigUtil.getProperty("appid");
	private static final String appsecret = PropertyConfigUtil.getProperty("appsecret");

	public synchronized static String getAccessToken() {
		long thistime = System.currentTimeMillis();
		if (StringUtils.isBlank(access_token) || thistime > expireTime) {
			access_token = WeixinUtil.getAccessToken(appid, appsecret);
			if (StringUtils.isBlank(access_token)) {
				logger.error("获取access_token异常");
			}
			expireTime = thistime + 7000000;
		}
		return access_token;
	}

}