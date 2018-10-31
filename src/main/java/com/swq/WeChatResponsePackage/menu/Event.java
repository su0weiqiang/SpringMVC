package com.swq.WeChatResponsePackage.menu;

import com.swq.WeiXinBasePackage.annotation.Menu;
import com.swq.WeiXinBasePackage.annotation.MenuMapping;

import com.swq.WeiXinBasePackage.domain.RequestMessage;
import com.swq.WeiXinBasePackage.domain.ResponseMessage;
import com.swq.WeiXinBasePackage.domain.ResponseTextMessage;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;

@Menu
public class Event {

	private Logger logger = LoggerFactory.getLogger(getClass());

	@Value("${base-url}")
	private String base_url;

	// 关注
	@MenuMapping(key = "subscribe")
	public ResponseMessage subscribe(RequestMessage requestMessage) throws Exception {
		ResponseTextMessage message = new ResponseTextMessage(requestMessage);
		message.setTextContent("欢迎关注苏青天的微信公众号!");
		return message;
	}

	// 取消关注
	@MenuMapping(key = "unsubscribe")
	public ResponseMessage unsubscribe(RequestMessage requestMessage) throws Exception {
		logger.info("openid:" + requestMessage.getFromUser() + "取消关注");
		return null;
	}

	// 扫码事件
	@MenuMapping(key = "scan")
	public ResponseMessage scan(RequestMessage requestMessage) throws Exception {
		String eventKey = requestMessage.getEventKey();
		logger.error("==扫码事件Event==" + requestMessage.getEvent());
		logger.error("==扫码事件Ticket==" + requestMessage.getTicket());
		logger.error("==扫码事件EventKey==" + eventKey);
		// XXX 对事件进行处理 最后返回ResponseMessage

		return null;
	}

}