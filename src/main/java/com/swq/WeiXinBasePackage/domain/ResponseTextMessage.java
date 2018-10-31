package com.swq.WeiXinBasePackage.domain;

import com.swq.WeiXinBasePackage.enums.ResponseMessageEnum;

/**
 * 
 * @description: 回复用户的文本消息
 * @author:
 */
public class ResponseTextMessage extends ResponseMessage {
	private String textContent;

	public ResponseTextMessage(RequestMessage entity) {
		fromUser = entity.getToUser();
		toUser = entity.getFromUser();
		this.messageType = ResponseMessageEnum.TEXT_MESSAGE;
	}

	public ResponseTextMessage(String fromUser, String toUser) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.messageType = ResponseMessageEnum.TEXT_MESSAGE;
	}

	public void setTextContent(String textContent) {
		this.textContent = textContent;
	}

	public String toString() {
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");
		sb.append(super.toString());
		sb.append("<Content><![CDATA[").append(textContent).append("]]></Content>");
		sb.append("</xml>");
		return sb.toString();
	}
}
