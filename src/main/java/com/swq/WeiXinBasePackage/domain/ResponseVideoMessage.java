package com.swq.WeiXinBasePackage.domain;


import com.swq.WeiXinBasePackage.enums.ResponseMessageEnum;

/**
 * 
 * @description: 回复用户的视频消息
 * @author:
 */
public class ResponseVideoMessage extends ResponseMessage {
	private String mediaId;
	private String thumbMediaId;

	public ResponseVideoMessage(RequestMessage entity) {
		this.fromUser = entity.getToUser();
		this.toUser = entity.getFromUser();
		this.messageType = ResponseMessageEnum.VIDEO_MESSAGE;
	}

	public ResponseVideoMessage(String fromUser, String toUser) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.messageType = ResponseMessageEnum.VIDEO_MESSAGE;
	}

	public void setMediaId(String mediaId) {
		this.mediaId = mediaId;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer();
		sb.append("<xml>");
		sb.append(super.toString());
		sb.append("<Video>");
		sb.append("<MediaId><![CDATA[").append(mediaId).append("]]></MediaId>");
		sb.append("<ThumbMediaId><![CDATA[").append(thumbMediaId).append("]]></ThumbMediaId>");
		sb.append("</Video>");
		sb.append("</xml>");

		return sb.toString();
	}
}
