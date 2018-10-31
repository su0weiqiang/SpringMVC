package com.swq.WeiXinBasePackage.domain;

import com.swq.WeiXinBasePackage.enums.ResponseMessageEnum;

import java.util.Date;

/**
 * 
 * @description: 回复用户的消息基类
 * @author:
 */
public abstract class ResponseMessage {
	protected ResponseMessageEnum messageType;
	protected String toUser;
	protected String fromUser;

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		// 接收方帐号（收到的OpenID）
		sb.append("<ToUserName><![CDATA[").append(toUser).append("]]></ToUserName>");
		// 开发者微信号
		sb.append("<FromUserName><![CDATA[").append(fromUser).append("]]></FromUserName>");
		// 消息创建时间 （整型）
		sb.append("<CreateTime>").append(new Date().getTime()).append("</CreateTime>");
		// 消息类型
		sb.append("<MsgType><![CDATA[").append(messageType.get()).append("]]></MsgType>");
		return sb.toString();
	}
}
