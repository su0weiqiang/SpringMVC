package com.swq.WeiXinBasePackage.annotation;

import java.lang.annotation.*;

/**
 * 
 * @description: 响应微信菜单的注解
 * @date: 2016年2月22日 上午11:14:15
 * @author: Xu
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface TextMapping {
	public String value();
}