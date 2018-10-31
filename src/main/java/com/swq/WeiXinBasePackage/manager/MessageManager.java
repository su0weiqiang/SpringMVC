package com.swq.WeiXinBasePackage.manager;


import com.swq.WeiXinBasePackage.exception.NoMatchedMsgException;
import com.swq.WeiXinBasePackage.domain.RequestMessage;
import com.swq.WeiXinBasePackage.domain.ResponseMessage;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.lang.reflect.InvocationTargetException;

/**
 * 
 * @description: 处理用户消息的管理类
 * @date: 2016年2月22日 下午3:08:48
 * @author: Xu
 */
@Component("wechat.MessageManager")
public class MessageManager {
	protected Log logger = LogFactory.getLog(getClass());
	@Resource
	protected EventManager eventManager;
	@Resource
	protected TextManager textManager;
//	protected VoiceManager voiceManager;

	/**
	 * 处理链接消息
	 * 
	 * @param requestMessage
	 * @return
	 * @throws NoMatchedMsgException 
	 */
	public ResponseMessage getLinkInfo(RequestMessage requestMessage) throws NoMatchedMsgException {
		throw new NoMatchedMsgException();
	}

	/**
	 * 处理事件响应
	 * 
	 * @param requestMessage
	 * @return
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 */
	public ResponseMessage getEventInfo(RequestMessage requestMessage) throws NoMatchedMsgException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException{
		return eventManager.response(requestMessage);
	}

	/**
	 * 图片消息响应
	 * 
	 * @param requestMessage
	 * @return
	 * @throws NoMatchedMsgException 
	 */
	public ResponseMessage getImageInfo(RequestMessage requestMessage) throws NoMatchedMsgException {
		throw new NoMatchedMsgException();
	}

	/**
	 * 地理位置消息
	 * 
	 * @param requestMessage
	 * @return
	 * @throws NoMatchedMsgException 
	 */
	public ResponseMessage getLocationInfo(RequestMessage requestMessage) throws NoMatchedMsgException {
		throw new NoMatchedMsgException();
	}

	/**
	 * 音频消息响应
	 * 
	 * @param requestMessage
	 * @return
	 * @throws NoMatchedMsgException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public ResponseMessage getVoiceInfo(RequestMessage requestMessage) throws NoMatchedMsgException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
//		throw new NoMatchedMsgException();
//		return voiceManager.response(requestMessage);
		return null;
	}

	/**
	 * 视频消息响应
	 * 
	 * @param requestMessage
	 * @return
	 * @throws NoMatchedMsgException 
	 */
	public ResponseMessage getVideoInfo(RequestMessage requestMessage) throws NoMatchedMsgException {
		throw new NoMatchedMsgException();
	}

	/**
	 * 文本消息响应
	 * 
	 * @param requestMessage
	 * @return
	 * @throws NoMatchedMsgException 
	 * @throws SecurityException 
	 * @throws NoSuchMethodException 
	 * @throws InvocationTargetException 
	 * @throws IllegalArgumentException 
	 * @throws IllegalAccessException 
	 */
	public ResponseMessage getTextInfo(RequestMessage requestMessage) throws NoMatchedMsgException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		//TODO 文本消息也和菜单消息一样用注解来处理
//		throw new NoMatchedMsgException();
		return textManager.response(requestMessage);
//		return null;
	}
	
}