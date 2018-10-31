package com.swq.WeiXinBasePackage.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @description: 接收消息类型集合
 * @date: 2014-8-25 下午4:33:20
 */
public enum RequestMessageEnum {
	EVENT_MESSAGE("event"), // 事件
	IMAGE_MESSAGE("image"), // 图片消息
	LINK_MESSAGE("link"), // 链接消息
	LOCATION_MESSAGE("location"), // 地理位置消息
	TEXT_MESSAGE("text"), // 文本消息
	VIDEO_MESSAGE("video"), // 视频消息
	SHORTVIDEO_MESSAGE("shortvideo"), // 小视频消息
	VOICE_MESSAGE("voice");// 语音消息

	private String type;

	RequestMessageEnum(String type) {
		this.type = type;
	}

	public String get() {
		return this.type;
	}

	public static RequestMessageEnum fromString(String type) {
		if (StringUtils.isNotEmpty(type)) {
			for (RequestMessageEnum e : RequestMessageEnum.values()) {
				if (e.type.equals(type)) {
					return e;
				}
			}
		}
		return null;
	}
}
