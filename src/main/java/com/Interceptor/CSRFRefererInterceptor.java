package com.Interceptor;

import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 *
 * @ClassName CSRFTokenInterceptor
 * @Description  校验token拦截器

 * @CreateDate:2018年8月3日
 */
public class CSRFRefererInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        boolean flag=true;
        String referer = request.getHeader("referer");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(request.getScheme()).append("://").append(request.getServerName());
        if(referer != null && !referer.equals("") ){
            if(referer.lastIndexOf(String.valueOf(stringBuffer)) != 0){
                flag= false; //验证失败
            }
        }


        if(flag){
            return true;
        }else{
            //request.getRequestDispatcher("/WEB-INF/pages/404.jsp").forward(request, response);
            return false;
        }
    }


}
