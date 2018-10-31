package com.swq.WeChatResponsePackage.text;

import com.swq.WeiXinBasePackage.annotation.Text;
import com.swq.WeiXinBasePackage.annotation.TextMapping;
import com.swq.WeiXinBasePackage.domain.*;
import com.swq.WeiXinBasePackage.annotation.Text;
import com.swq.WeiXinBasePackage.annotation.TextMapping;


@Text
public class TextDemo {

	@TextMapping(value = "你好swq")
	public ResponseMessage bind(RequestMessage requestMessage) throws Exception {

		// 图文消息List(不超过8个)
		ResponseNewsMessage messages = new ResponseNewsMessage(requestMessage);
		// 图文消息
		ResponseNewsItem item = new ResponseNewsItem();
		item.setTitle("这个是一个图文信息");// 图文消息标题
		item.setDescription("这个是一个图文信息");// 图文消息描述
		item.setUrl("www.baidu.com");// 点击图文消息跳转链接
		item.setPicUrl("");// 图片链接

		messages.addNewsItem(item);
		return messages;
	}


	@TextMapping(value = "哈哈")
	public ResponseTextMessage hehe(RequestMessage requestMessage) throws Exception {


		ResponseTextMessage messages = new ResponseTextMessage(requestMessage);

		messages.setTextContent(requestMessage.getContent()+"----------这是一个自动回复");

		return messages;
	}

}
