package com.swq.WeiXinBasePackage.domain;

import org.apache.commons.lang3.StringUtils;

/**
 * 
 * @description: 回复用户的图文消息item
 * @author:
 */
public class ResponseNewsItem {
	private String title;// 图文消息标题
	private String description;// 图文消息描述
	private String url;// 点击图文消息跳转链接
	private String picUrl;// 图片链接
	
	public ResponseNewsItem() {
		// TODO 自动生成的构造函数存根
	}
	public ResponseNewsItem(String title,String description,String url,String picUrl) {
		this.title = title;
		this.description = description;
		this.url = url;
		this.picUrl = picUrl;
	}

	public void setDescription(String description) {
		this.description = description;
	}

	public void setPicUrl(String picUrl) {
		this.picUrl = picUrl;
	}

	public void setTitle(String title) {
		this.title = title;
	}

	public void setUrl(String url) {
		this.url = url;
	}

	private String subString(String desc, String info) {
		info = StringUtils.trimToNull(info);
		StringBuffer buffer = new StringBuffer();
		if (info == null) {
			buffer.append("<").append(desc).append("><![CDATA[]]></").append(desc).append(">");
		} else {
			buffer.append("<").append(desc).append("><![CDATA[").append(info).append("]]></").append(desc).append(">");
		}
		return buffer.toString();
	}

	@Override
	public String toString() {
		title = StringUtils.trimToNull(title);
		if (title == null) { // 没有title返回空串
			return "";
		}

		StringBuffer buffer = new StringBuffer();
		buffer.append(subString("Title", title));
		buffer.append(subString("Description", description));
		buffer.append(subString("Url", url));
		buffer.append(subString("PicUrl", picUrl));
		return buffer.toString();
	}
}
