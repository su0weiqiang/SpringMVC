package com.swq.WeiXinBasePackage.enums;

/**
 * 
 * @description: 回复消息类型
 * @date: 2014-8-25 下午4:33:40
 * @author:
 */
public enum ResponseMessageEnum {
	TEXT_MESSAGE("text"), 
	IMAGE_MESSAGE("image"), 
	VOICE_MESSAGE("voice"),
	VIDEO_MESSAGE("video"), 
	MUSIC_MESSAGE("music"), 
	NEWS_MESSAGE("news");

	private String type;

	ResponseMessageEnum(String type) {
		this.type = type;
	}

	public String get() {
		return this.type;
	}
}
