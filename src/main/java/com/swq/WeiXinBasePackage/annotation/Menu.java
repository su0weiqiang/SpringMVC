package com.swq.WeiXinBasePackage.annotation;

import org.springframework.stereotype.Component;

import java.lang.annotation.*;

/**
 * 
 * @description: 菜单类注解
 * @date: 2016年2月23日 下午2:55:43
 * @author: Xu
 */
@Target({ ElementType.TYPE })
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Component
public @interface Menu {
}