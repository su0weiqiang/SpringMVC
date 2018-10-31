package com.swq.WeiXinBasePackage.enums;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @description: 微信事件集合
 * @date: 2014-8-25 下午4:31:02
 * @author:
 */
public enum RequestEventEnum {
	CLICK_EVENT("CLICK"), // 点击菜单拉取消息时的事件推送
	LOCATION_EVENT("LOCATION"), // 上报地理位置事件
	SCAN_EVENT("SCAN"), // 扫描带参数二维码事件
	SUBSCRIBE_EVENT("subscribe"), // 订阅
	UNSUBSCRIBE_EVENT("unsubscribe"), // 取消订阅
	VIEW("VIEW");// 点击菜单跳转链接时的事件推送

	private String type;

	RequestEventEnum(String type) {
		this.type = type;
	}

	public String get() {
		return this.type;
	}

	public static RequestEventEnum fromString(String type) {
		if (StringUtils.isNotEmpty(type)) {
			for (RequestEventEnum e : RequestEventEnum.values()) {
				if (e.type.equals(type)) {
					return e;
				}
			}
		}
		return null;
	}
}
