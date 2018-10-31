package com.swq.WeiXinBasePackage.util;

import com.swq.WeiXinBasePackage.util.aes.WXBizMsgCrypt;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.Random;

/**
 * 
 * @description:
 * @date: 2016年2月22日 上午11:59:19
 * @author: Xu
 */
@Component("wechat.WechatAesUtil")
public class WechatAesUtil {
	private static final Log log = LogFactory.getLog(WechatAesUtil.class);
	public String encodingAesKey;
	public String token;
	public String appId;

	/**
	 * 
	 * @return
	 * @description: 微信消息加密
	 * @date: 2015-8-12 下午1:31:59
	 *
	 */
	public String encode(String toUser, String replyMsg) {
		try {
			// 需要加密的明文
			String timestamp = getTimeStamp();
			String nonce = getRandomCharacter(32);

			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);
			String mingwen = pc.encryptMsg(replyMsg, timestamp, nonce);
			System.out.println("加密后: " + mingwen);
			mingwen = mingwen.replace("<xml>", String.format("<xml><ToUserName><![CDATA[%1$s]]></ToUserName>", toUser));

			System.out.println(mingwen);
			return mingwen;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return null;

	}

	/**
	 * 
	 * @return
	 * @description: 微信消息解密
	 * @date: 2015-8-12 下午1:32:14
	 *
	 */
	public String decode(String msgSignature, String timestamp, String nonce, String fromXML) {
		try {
			WXBizMsgCrypt pc = new WXBizMsgCrypt(token, encodingAesKey, appId);

			String result2 = pc.decryptMsg(msgSignature, timestamp, nonce, fromXML);

			return result2;
		} catch (Exception e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	/**
	 * 获取随机字符串
	 * 
	 * @param length
	 * @return
	 */
	public static String getRandomCharacter(int length) {
		if (length <= 0)
			return "";
		String str = "abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ0123456789";
		Random random = new Random();
		StringBuffer sb = new StringBuffer();
		for (int i = 0; i < length; i++) {
			int number = random.nextInt(str.length());
			sb.append(str.charAt(number));
		}
		return sb.toString();
	}

	private static String getTimeStamp() {
		return (System.currentTimeMillis() + "").substring(0, 10);
	}

	public String readLine(InputStream is) {
		BufferedReader in = new BufferedReader(new InputStreamReader(is));
		StringBuffer buffer = new StringBuffer();
		String line = "";
		try {
			while ((line = in.readLine()) != null) {
				buffer.append(line);
			}
			return buffer.toString();
		} catch (IOException e) {
			log.error(e.getMessage(), e);
		}
		return null;
	}

	@Value("${EncodingAESKey:null}")
	public void setEncodingAesKey(String encodingAesKey) {
		this.encodingAesKey = encodingAesKey;
	}

	@Value("${token}")
	public void setToken(String token) {
		this.token = token;
	}

	@Value("${appid}")
	public void setAppId(String appId) {
		this.appId = appId;
	}

}
