package com.swq.WeiXinBasePackage.domain;

import java.util.HashMap;
import java.util.Map;

/**
 * @description: 模板消息
 * @author:
 */
public class ResponseTemplateMessage {
	private String touser; // 用户OpenID
	private String template_id; // 模板消息ID
	private String url; // URL置空，则在发送后，点击模板消息会进入一个空白页面（ios），或无法点击（android）。
	private Map<String, Object> data = new HashMap<String, Object>(); // 模板数据

	/**
	 * @description: 各种颜色常亮，方便选择（RED，BLACK，BLUE等）
	 * @date: 2016年2月26日 下午4:58:13
	 * @author:
	 */
	public class Color {
		public static final String BLACK = "#000000";
		// TODO 其他颜色
	}

	/**
	 * @param name 变量名
	 * @param value 变量值
	 * @param color 文字颜色
	 * @description: 设置模板消息的值
	 * @date: 2016年2月25日 上午11:26:12
	 *
	 */
	public void setAttribute(String name, String value, String color) {
		Map<String, String> map = new HashMap<String, String>();
		map.put("color", color);
		map.put("value", value);
		data.put(name, map);
	}

	/**
	 * @param name 变量名
	 * @param value 变量值
	 * @description: 设置模板消息的值，默认黑色
	 * @date: 2016年2月26日 下午4:57:24
	 *
	 */
	public void setAttribute(String name, String value) {
		setAttribute(name, value, Color.BLACK);
	}

	public String getTouser() {
		return touser;
	}

	public void setTouser(String touser) {
		this.touser = touser;
	}

	public String getTemplate_id() {
		return template_id;
	}

	public void setTemplate_id(String template_id) {
		this.template_id = template_id;
	}

	public String getUrl() {
		return url;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	public Map<String, Object> getData() {
		return data;
	}

	public void setData(Map<String, Object> data) {
		this.data = data;
	}

//	public String getTopcolor() {
//		return topcolor;
//	}
//
//	public void setTopcolor(String topcolor) {
//		this.topcolor = topcolor;
//	}
}
