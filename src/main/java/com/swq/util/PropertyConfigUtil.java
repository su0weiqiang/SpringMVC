package com.swq.util;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.config.PropertyPlaceholderConfigurer;

import java.util.Properties;

/**
 * @description: 重写spring的PropertyPlaceholderConfigurer，并改用该类加载配置文件并获取配置信息
 * @date: 2015-6-30 下午6:26:07
 * @author:
 */
public class PropertyConfigUtil extends PropertyPlaceholderConfigurer {

	private final static Logger logger = LoggerFactory.getLogger(PropertyConfigUtil.class);
	public static final String key = "e4ecef27c69bfbd0";// aes加密的key(之前的工具写死了key为长度16的字符串，请不要改动key长度)
	public static final String prefix = "4J.";// 用来标记已经被加密的配置信息的前缀，以数字开头防止冲突

	private static Properties properties = new Properties();
	private AesUtil util = AesUtil.getInstance();

	/**
	 * @param beanFactory
	 * @param props
	 * @throws BeansException
	 * @description: 父类的该方法将配置信息提交给beanFactory，在之前解密，并初始化properties
	 * @date: 2015-6-30 下午7:04:16
	 *
	 */
	@Override
	protected void processProperties(ConfigurableListableBeanFactory beanFactory, Properties props) throws BeansException {
		for (Object propertyName : props.keySet()) {
			String prop = props.getProperty((String) propertyName);
			if (((String) propertyName).startsWith(prefix)) {
				propertyName = ((String) propertyName).substring(prefix.length());// 去掉前缀
				prop = util.decrypt(prop, key);
			}
			properties.put(propertyName, prop);
		}
		super.processProperties(beanFactory, properties);
	}

	/**
	 * @param key
	 * @return
	 * @description: 读取配置文件信息
	 * @date: 2015-6-30 下午7:06:30
	 *
	 */
	public static String getProperty(String key) {
		String property = properties.getProperty(key);
		if (StringUtils.isBlank(property)) {
			property = properties.getProperty(key.substring(prefix.length()));// 防止有人不小心照着配置文件取带前缀的。
			if (StringUtils.isBlank(property)) {
				logger.error("获取配置文件异常:Key==" + key);
			}
		}
		return property;
	}

}