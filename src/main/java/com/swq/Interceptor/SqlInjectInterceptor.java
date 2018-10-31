package com.swq.Interceptor;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Enumeration;

/**
 * 
 * <p>
 * Description:防SQL注入拦截器
 * </p>
 * <p>
 * Date:2017年7月19日
 * </p>
 * <p>
 * @version 1.0
 */
public class SqlInjectInterceptor implements HandlerInterceptor {

	@Override
	public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
	}

	@Override
	public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
	}

	@Override
	public boolean preHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2) throws Exception {
		Enumeration<String> names = arg0.getParameterNames();
		while (names.hasMoreElements()) {
			String name = names.nextElement();
			String[] values = arg0.getParameterValues(name);
			for (String value : values) {
				if (judgeXSS(value.toLowerCase())) {
					arg1.setContentType("text/html;charset=UTF-8");
					arg1.getWriter().print("参数含有非法SQL攻击字符,已禁止继续访问！");
					return false;
				}
			}
		}
		return true;
	}

	/**
	 * 判断参数是否含有攻击串
	 * 
	 * @param value
	 * @return
	 */
	public boolean judgeXSS(String value) {
		if (value == null || "".equals(value)) {
			return false;
		}
		// String xssStr = "and|or|select|update|delete|drop|truncate|%20|=|-|--|;|'|%|#|+|,|//|/| |\\|!=|(|)";
		String xssStr = " and | or |select |update |delete |drop |truncate ";
		String[] xssArr = xssStr.split("\\|");
		for (int i = 0; i < xssArr.length; i++) {
			if (value.indexOf(xssArr[i]) > -1) {
				return true;
			}
		}
		return false;
	}

}
