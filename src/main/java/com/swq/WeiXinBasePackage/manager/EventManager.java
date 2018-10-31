package com.swq.WeiXinBasePackage.manager;



import com.swq.WeiXinBasePackage.annotation.Menu;
import com.swq.WeiXinBasePackage.annotation.MenuMapping;
import com.swq.WeiXinBasePackage.domain.RequestMessage;
import com.swq.WeiXinBasePackage.domain.ResponseMessage;
import com.swq.WeiXinBasePackage.enums.RequestEventEnum;
import com.swq.WeiXinBasePackage.exception.NoMatchedMsgException;
import com.swq.WeiXinBasePackage.util.ClassTypeUtil;
import com.swq.WeiXinBasePackage.util.SpringContextUtil;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.dom4j.Document;
import org.dom4j.DocumentException;
import org.dom4j.Element;
import org.dom4j.Node;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.io.InputStream;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.*;

/**
 * @description: 处理事件响应的管理类
 * @date: 2016年2月22日 下午3:22:30
 */
@Component("wechat.EventManager")
public class EventManager {
	protected Log log = LogFactory.getLog(getClass());
	protected Map<String, String[]> menuMap = new HashMap<String, String[]>();// 包含处理的函数名信息
	public String basePackage;

	/**
	 * @param requestMessage
	 * @return
	 * @throws NoMatchedMsgException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @description: 处理时间响应的方法
	 * @date: 2016年2月22日 下午3:44:38
	 *
	 */
	public ResponseMessage response(RequestMessage requestMessage) throws NoMatchedMsgException, IllegalAccessException, IllegalArgumentException, InvocationTargetException, NoSuchMethodException, SecurityException {
		RequestEventEnum eventEnum = RequestEventEnum.fromString(requestMessage.getEvent());
		log.info(eventEnum.toString());
		switch (eventEnum) {
		case SUBSCRIBE_EVENT:// 订阅
			log.info("=========订阅==========");
			return execute("subscribe", requestMessage);
		case UNSUBSCRIBE_EVENT:// 取消订阅
			log.info("========取消订阅=========");
			return execute("unsubscribe", requestMessage);
		case CLICK_EVENT:// 点击菜单拉取消息时的事件推送
			log.info("===点击菜单拉取消息时的事件推送===");
			String eventKey = requestMessage.getEventKey();
			return execute(eventKey, requestMessage);
		case SCAN_EVENT:// 扫描带参数二维码事件
			log.info("====扫描带参数二维码事件=====");
			return execute("scan", requestMessage);
		case LOCATION_EVENT:// 上报地理位置事件
			log.info("====上报地理位置事件====");
//			throw new NoMatchedMsgException();
			return null;
		case VIEW:// 点击菜单跳转链接时的事件推送
			log.info("=== 点击菜单跳转链接时的事件推送====");
//			throw new NoMatchedMsgException();
			return null;
		default:
			throw new NoMatchedMsgException();
		}
	}

	/**
	 * @param key
	 * @param requestMessage
	 * @return
	 * @throws NoMatchedMsgException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @throws InvocationTargetException
	 * @throws IllegalArgumentException
	 * @throws IllegalAccessException
	 * @description: 根据特定id将对应的事件分发给不同的处理类来处理
	 * @date: 2016年2月22日 下午3:45:26
	 *
	 */
	public ResponseMessage execute(String key, RequestMessage requestMessage) throws NoMatchedMsgException, NoSuchMethodException, SecurityException, IllegalAccessException, IllegalArgumentException, InvocationTargetException {
		String[] mapMsg = menuMap.get(key);
		if (mapMsg == null)
			throw new NoMatchedMsgException("未找到匹配的响应菜单的key");
		Object object = SpringContextUtil.getBean(mapMsg[0]);
		Method method = object.getClass().getMethod(mapMsg[1], RequestMessage.class);
		return (ResponseMessage) method.invoke(object, requestMessage);
	}

	/**
	 * @throws IOException
	 * @throws ClassNotFoundException
	 * @throws SecurityException
	 * @throws NoSuchMethodException
	 * @description: 
	 *               启动加载，扫描注解，初始化响应对照表（代码感觉有待优化，可以在把spring对controller的实现的源码看懂后参照实现
	 *               ）
	 * @date: 2016年2月22日 下午3:46:33
	 *
	 */
	@PostConstruct
	public void init() throws ClassNotFoundException, IOException {
		initAnnotations();// 先初始化注解
		initMapperFile();// 再初始化配置文件

	}

	// 初始化注解
	private void initAnnotations() throws ClassNotFoundException, IOException {
		@SuppressWarnings("unchecked")
		ClassTypeUtil classTypeUtil = new ClassTypeUtil(new String[] { basePackage }, Menu.class);
		Set<Class<?>> set = classTypeUtil.getClassSet();// 获取所有使用了Menu注解的类
		Iterator<Class<?>> iterator = set.iterator();
		while (iterator.hasNext()) {
			Class<?> class1 = iterator.next();
			Method[] methods = class1.getMethods();
			for (Method method : methods) {
				MenuMapping menuMapping = method.getAnnotation(MenuMapping.class);
				if (menuMapping != null) {
					menuMap.put(menuMapping.key(), new String[] { getBeanIdByClassName(class1.getName()), method.getName() });
				}
			}
		}
	}

	private void initMapperFile() {
		InputStream inputStream = getClass().getClassLoader().getResourceAsStream("/menuMapper.xml");
		if (inputStream == null) {// 允许没有配置文件
			return;
		}
		SAXReader saxReader = new SAXReader();
		try {
			Document document = saxReader.read(inputStream);
			Element rootElement = document.getRootElement();

			List<Node> menuList = rootElement.selectNodes("menu");
			for (Object obj : menuList) {
				Element element = (Element) obj;
				String key = element.attributeValue("key");
				String methodName = element.attributeValue("method");
				String clazz = element.attributeValue("class");
				if (methodName == null) {
					methodName = "execute";
				}
				String beanId = getBeanIdByClassName(clazz);
				try {// 将配置文件的类注册到spring容器中
					if (SpringContextUtil.containsBean(beanId)) {// 如果配置文件的类已经注册，则取消注册
						log.info("bean id为" + beanId + "的bean已经注册进spring容器中，取消本次注册。");
					} else {
						SpringContextUtil.registerBean(beanId, clazz);
					}
					menuMap.put(key, new String[] { beanId, methodName });
				} catch (ClassNotFoundException e) {
					log.error("配置的类信息有误，请检查：", e);
					continue;
				}
			}
		} catch (DocumentException e) {
			log.error(e.getMessage(), e);
		}
	}

	// 通过class信息，获取spring容器中的beand的 id
	private String getBeanIdByClassName(String className) {
		int lastPointIndex = className.lastIndexOf(".");
		String key = className.substring(lastPointIndex + 1, lastPointIndex + 2).toLowerCase() + className.substring(lastPointIndex + 2);
		return key;
	}

	@Value("${menu_basePackage:com.swq")
	public void setBasePackage(String basePackage) {
		this.basePackage = basePackage;
	}

}