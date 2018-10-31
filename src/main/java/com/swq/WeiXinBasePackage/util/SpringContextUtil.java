package com.swq.WeiXinBasePackage.util;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.support.BeanDefinitionBuilder;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * 
 * @description: 从spring容器中获取装载的bean的工具
 * @date: 2016年2月23日 下午4:14:17
 * @author: Xu
 */
@Component("wechat.SpringContextUtil")
public class SpringContextUtil implements ApplicationContextAware, DisposableBean {

	private static ApplicationContext applicationContext = null;

	private static Log log = LogFactory.getLog(SpringContextUtil.class);

	@Resource
	public void setApplicationContext(ApplicationContext applicationContext) {
		log.info("注入ApplicationContext到SpringContextHolder:" + applicationContext);
		SpringContextUtil.applicationContext = applicationContext; // NOSONAR
	}

	@Override
	public void destroy() throws Exception {
		SpringContextUtil.clear();
	}

	public static ApplicationContext getApplicationContext() {
		return applicationContext;
	}

	@SuppressWarnings("unchecked")
	public static <T> T getBean(String name) {
		return (T) applicationContext.getBean(name);

	}

	public static <T> T getBean(Class<T> requiredType) {
		return applicationContext.getBean(requiredType);
	}

	/**
	 * 
	 * @param beanId
	 * @param className
	 * @throws ClassNotFoundException
	 * @description: 手动注册bean
	 * @date: 2016年2月24日 下午5:27:18
	 *
	 */
	public static void registerBean(String beanId, String className) throws ClassNotFoundException {
		Class<?> class1 = getClassByClassName(className);
		BeanDefinitionBuilder beanDefinitionBuilder = BeanDefinitionBuilder.genericBeanDefinition(class1);
		ConfigurableApplicationContext configurableApplicationContext = (ConfigurableApplicationContext) applicationContext;
		BeanDefinitionRegistry beanDefinitonRegistry = (BeanDefinitionRegistry) configurableApplicationContext.getBeanFactory();
		beanDefinitonRegistry.registerBeanDefinition(beanId, beanDefinitionBuilder.getRawBeanDefinition());
	}

	public static boolean containsBean(String name) {
		return applicationContext.containsBean(name);
	}

	public boolean isRedy() {
		return applicationContext != null;
	}

	public static void clear() {
		log.info("清除SpringContextHolder中的ApplicationContext:" + applicationContext);
		applicationContext = null;
	}

	// 根据类名获取类的信息
	private static Class<?> getClassByClassName(String className) throws ClassNotFoundException {
		Class<?> class1 = Thread.currentThread().getContextClassLoader().loadClass(className);
		return class1;
	}

}