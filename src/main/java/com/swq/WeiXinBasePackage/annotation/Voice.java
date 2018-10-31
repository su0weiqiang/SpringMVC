package com.swq.WeiXinBasePackage.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 
 * @description: 音频消息类注解
 * @author CX
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Voice {
}