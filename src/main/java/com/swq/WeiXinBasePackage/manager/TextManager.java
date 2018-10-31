package com.swq.WeiXinBasePackage.manager;

import com.swq.WeiXinBasePackage.exception.NoMatchedMsgException;
import com.swq.WeiXinBasePackage.annotation.Text;
import com.swq.WeiXinBasePackage.annotation.TextMapping;
import com.swq.WeiXinBasePackage.domain.RequestMessage;
import com.swq.WeiXinBasePackage.domain.ResponseMessage;
import com.swq.WeiXinBasePackage.util.ClassTypeUtil;
import com.swq.WeiXinBasePackage.util.SpringContextUtil;

import com.swq.WeiXinBasePackage.annotation.Text;
import com.swq.WeiXinBasePackage.annotation.TextMapping;
import com.swq.WeiXinBasePackage.util.SpringContextUtil;
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
 * 注解的解析类， 把在WeChatResponsePackage中设置的回复类解析，方便回复
 * @description: 处理文本响应的管理类
 * @date: 2016年2月22日 下午3:22:30
 * @author: Xu
 */
@Component("wechat.TextManager")
public class TextManager {
	protected Log log = LogFactory.getLog(getClass());
	protected Map<String, String[]> textMap = new HashMap<String, String[]>();// 包含处理的函数名信息
	public String basePackage;

	public ResponseMessage response(RequestMessage requestMessage) throws NoMatchedMsgException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		String content = requestMessage.getContent();
		return execute(content, requestMessage);
	}

	public ResponseMessage execute(String content, RequestMessage requestMessage) throws NoMatchedMsgException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] mapMsg = null;
		for (String key : textMap.keySet()) {
			if (content.indexOf(key) != -1) {
				mapMsg = textMap.get(key);
				break;
			}
		}
//		String[] mapMsg = textMap.get(content);
		if (mapMsg == null) {
//			throw new NoMatchedMsgException("未找到匹配的响应文本");
			log.error("未找到匹配的响应文本消息的类和方法");
			return null;
		}
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
		ClassTypeUtil classTypeUtil = new ClassTypeUtil(new String[] { basePackage }, Text.class);
		Set<Class<?>> set = classTypeUtil.getClassSet();// 获取所有使用了Text注解的类
		Iterator<Class<?>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Class<?> class1 = iterator.next();
			Method[] methods = class1.getMethods();
			for (Method method : methods) {
				TextMapping textMapping = method.getAnnotation(TextMapping.class);
				if (textMapping != null) {
					textMap.put(textMapping.value(), new String[] { getBeanIdByClassName(class1.getName()), method.getName() });
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


	//设置文本回复的内容类的基类包

	@Value("${menu_basePackage:com.swq}")
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

}