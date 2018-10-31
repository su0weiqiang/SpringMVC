package com.swq.WeiXinBasePackage.manager;


import com.swq.WeiXinBasePackage.exception.NoMatchedMsgException;
import com.swq.WeiXinBasePackage.annotation.Voice;
import com.swq.WeiXinBasePackage.annotation.VoiceMapping;
import com.swq.WeiXinBasePackage.domain.RequestMessage;
import com.swq.WeiXinBasePackage.domain.ResponseMessage;
import com.swq.WeiXinBasePackage.util.ClassTypeUtil;
import com.swq.WeiXinBasePackage.util.SpringContextUtil;
import com.swq.WeiXinBasePackage.annotation.Voice;
import com.swq.WeiXinBasePackage.annotation.VoiceMapping;
import com.swq.WeiXinBasePackage.util.ClassTypeUtil;
import com.swq.WeiXinBasePackage.util.SpringContextUtil;
import org.apache.commons.lang3.StringUtils;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;
import java.util.Set;

/**
 * @description: 处理音频消息响应的管理类
 * @author CX
 */
@Component("wechat.VoiceManager")
public class VoiceManager {
	protected Log log = LogFactory.getLog(getClass());
	protected Map<String, String[]> voiceMap = new HashMap<String, String[]>();// 包含处理的函数名信息
	public String basePackage;

	public ResponseMessage response(RequestMessage requestMessage) throws NoMatchedMsgException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String recognition = requestMessage.getRecognition();//
		return execute(recognition, requestMessage);
	}

	public ResponseMessage execute(String recognition, RequestMessage requestMessage) throws NoMatchedMsgException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		System.out.println("==================");
		System.out.println("音频识别结果:" + recognition);
		System.out.println("==================");

		if (StringUtils.isBlank(recognition)) {
			log.error("未获得语音识别的内容,请确认是否开启该功能模块");
			return null;
		}
		String[] mapMsg = null;
		for (String key : voiceMap.keySet()) {
			if (recognition.indexOf(key) != -1) {
				mapMsg = voiceMap.get(key);
				break;
			}
		}
		if (mapMsg == null) {
			log.error("未找到匹配的响应文本消息的类和方法");
			// 采用默认的方法
//			Object object = springContextUtil.getBean("xnbVoice");
//			Method method = object.getClass().getMethod("execute", RequestMessage.class);
//			return (ResponseMessage) method.invoke(object, requestMessage);
			return null;
		}
		System.out.println(mapMsg[0] + "===" + mapMsg[1]);
		Object object = SpringContextUtil.getBean(mapMsg[0]);
		Method method = object.getClass().getMethod(mapMsg[1], RequestMessage.class);
		return (ResponseMessage) method.invoke(object, requestMessage);
	}

	/**
	 * 
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @description: 
	 *               启动加载，扫描注解，初始化响应对照表（代码感觉有待优化，可以在把spring对controller的实现的源码看懂后参照实现
	 *               ）
	 */
	@PostConstruct
	public void init() throws ClassNotFoundException, IOException {
		@SuppressWarnings("unchecked")
        ClassTypeUtil classTypeUtil = new ClassTypeUtil(new String[] { basePackage }, Voice.class);
		Set<Class<?>> set = classTypeUtil.getClassSet();// 获取所有使用了Voice注解的类
		Iterator<Class<?>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Class<?> class1 = iterator.next();
			Method[] methods = class1.getMethods();
			for (Method method : methods) {
				VoiceMapping voiceMapping = method.getAnnotation(VoiceMapping.class);
				if (voiceMapping != null) {
					voiceMap.put(voiceMapping.value(), new String[] { getBeanIdByClassName(class1.getName()), method.getName() });
				}
			}
		}
	}

	// 通过class信息，获取spring容器中的beand的 id
	private String getBeanIdByClassName(String className) {
		int lastPointIndex = className.lastIndexOf(".");
		String key = className.substring(lastPointIndex + 1, lastPointIndex + 2).toLowerCase() + className.substring(lastPointIndex + 2);
		return key;
	}

	@Value("${menu_basePackage:com.swq}")
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

}