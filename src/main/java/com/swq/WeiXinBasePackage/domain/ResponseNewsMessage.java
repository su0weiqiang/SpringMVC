package com.swq.WeiXinBasePackage.domain;


import com.swq.WeiXinBasePackage.enums.ResponseMessageEnum;

import java.util.ArrayList;
import java.util.List;

/**
 * 
 * @description: 回复用户的图文消息
 * @author:
 */
public class ResponseNewsMessage extends ResponseMessage {
	private List<ResponseNewsItem> newsItems;

	public ResponseNewsMessage(RequestMessage entity) {
		newsItems = new ArrayList<ResponseNewsItem>();
		fromUser = entity.getToUser();
		toUser = entity.getFromUser();
		this.messageType = ResponseMessageEnum.NEWS_MESSAGE;
	}

	public ResponseNewsMessage(String fromUser, String toUser) {
		newsItems = new ArrayList<ResponseNewsItem>();
		this.fromUser = fromUser;
		this.toUser = toUser;
		this.messageType = ResponseMessageEnum.NEWS_MESSAGE;
	}

	/**
	 * 
	 * @param newsItems
	 * @description: 设置图文消息所有item
	 * @date: 2014-5-7 上午11:51:10
	 *
	 */
	public void setNewsItems(List<ResponseNewsItem> newsItems) {
		this.newsItems = newsItems;
	}

	/**
	 * 
	 * @param item
	 * @description: 添加图文信息item
	 * @date: 2014-5-7 上午11:50:28
	 *
	 */
	public void addNewsItem(ResponseNewsItem item) {
		newsItems.add(item);
	}

	@Override
	public String toString() {
		int length = newsItems.size();
		if (length > 10) {
			length = 10;
		}
		StringBuffer sb = new StringBuffer("");
		sb.append("<xml>");
		sb.append(super.toString());
		sb.append("<ArticleCount>").append(length).append("</ArticleCount>");
		sb.append("<Articles>");
		for (int i = 0; i < length; i++) {
			sb.append("<item>");
			ResponseNewsItem item = newsItems.get(i);
			sb.append(item.toString());
			sb.append("</item>");
		}
		sb.append("</Articles>");
		sb.append("</xml>");
		return sb.toString();
	}
}
