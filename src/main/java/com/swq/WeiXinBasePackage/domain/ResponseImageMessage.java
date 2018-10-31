package com.swq.WeiXinBasePackage.domain;

import com.swq.WeiXinBasePackage.enums.ResponseMessageEnum;

/**
 * 
 * @description: 回复用户的图片消息
 * @author:
 */
public class ResponseImageMessage extends ResponseMessage {
	private String mediaId;

	public ResponseImageMessage(RequestMessage entity) {
		fromUser = entity.getToUser();
		toUser = entity.getFromUser();
		this.messageType = ResponseMessageEnum.IMAGE_MESSAGE;
	}

	public ResponseImageMessage(String fromUser, String toUser) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.messageType = ResponseMessageEnum.IMAGE_MESSAGE;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append(super.toString());
		sb.append("<Image>");
		sb.append("<MediaId><![CDATA[").append(mediaId).append("]]></MediaId>");
		sb.append("</Image>");
		sb.append("</xml>");
		return sb.toString();
	}
}