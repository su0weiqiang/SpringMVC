package com.swq.WeiXinBasePackage.annotation;

import java.lang.annotation.*;

/**
 * 
 * @description: 响应音频消息的注解
 * @author CX
 */
@Target(ElementType.METHOD)
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface VoiceMapping {
	public String value();
}