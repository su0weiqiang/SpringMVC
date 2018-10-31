package com.swq.WeiXinBasePackage.domain;

import com.swq.WeiXinBasePackage.enums.ResponseMessageEnum;

/**
 * 
 * @description: 回复用户的音频消息
 * @author:
 */
public class ResponseVoiceMessage extends ResponseMessage {
	private String mediaId;

	public ResponseVoiceMessage(RequestMessage entity) {
		this.fromUser = entity.getToUser();
		this.toUser = entity.getFromUser();
		this.messageType = ResponseMessageEnum.VOICE_MESSAGE;
	}

	public ResponseVoiceMessage(String fromUser, String toUser) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.messageType = ResponseMessageEnum.VOICE_MESSAGE;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append(super.toString());
		sb.append("</xml>");
		sb.append("<Voice>");
		sb.append("<MediaId><![CDATA[").append(mediaId).append("]]></MediaId>");
		sb.append("</Voice>");
		return sb.toString();
	}
}
