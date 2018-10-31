package com.swq.WeiXinBasePackage.domain;

import com.swq.WeiXinBasePackage.enums.ResponseMessageEnum;

/**
 * 
 * @description: 回复用户的音乐消息
 * @author:
 */
public class ResponseMusicMessage extends ResponseMessage {
	private String musicTitle;
	private String musicDescription;
	private String musicUrl;
	private String hqMusicUrl;
	private String thumbMediaId;

	public ResponseMusicMessage(RequestMessage entity) {
		fromUser = entity.getToUser();
		toUser = entity.getFromUser();
		this.messageType = ResponseMessageEnum.MUSIC_MESSAGE;
	}

	public ResponseMusicMessage(String fromUser, String toUser) {
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.messageType = ResponseMessageEnum.MUSIC_MESSAGE;
	}

	public void setMusicTitle(String musicTitle) {
		this.musicTitle = musicTitle;
	}

	public void setMusicDescription(String musicDescription) {
		this.musicDescription = musicDescription;
	}

	public void setMusicUrl(String musicUrl) {
		this.musicUrl = musicUrl;
	}

	public void setHqMusicUrl(String hqMusicUrl) {
		this.hqMusicUrl = hqMusicUrl;
	}

	public void setThumbMediaId(String thumbMediaId) {
		this.thumbMediaId = thumbMediaId;
	}

	@Override
	public String toString() {
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");
		sb.append(super.toString());
		sb.append("<Music>");
		sb.append("<Title><![CDATA[").append(musicTitle).append("]]></Title>");
		sb.append("<Description><![CDATA[").append(musicDescription).append("]]></Description>");
		sb.append("<MusicUrl><![CDATA[").append(musicUrl).append("]]></MusicUrl>");
		sb.append("<HQMusicUrl><![CDATA[").append(hqMusicUrl).append("]]></HQMusicUrl>");
		sb.append("<ThumbMediaId><![CDATA[").append(thumbMediaId).append("]]></ThumbMediaId>");
		sb.append("</Music>");
		sb.append("</xml>");
		return sb.toString();
	}
}
