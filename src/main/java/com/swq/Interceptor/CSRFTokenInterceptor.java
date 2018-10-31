package com.swq.Interceptor;

import com.swq.CSRF.CSRFTokenManager;
import com.swq.CSRF.CSRFTokenManager;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 *
 * @ClassName CSRFTokenInterceptor
 * @Description  校验token拦截器
 * @CreateDate:2018年8月3日
 */
public class CSRFTokenInterceptor extends HandlerInterceptorAdapter {


    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        if(isValidCsrfFormToken(request) || isValidCsrfHeaderToken(request)){
            return true;
        }else{
            request.getRequestDispatcher("/WEB-INF/pages/404.jsp").forward(request, response);
            return false;
        }
    }

    /**
     *
     * @Description 校验form表单是否携带token
     * @date 2018年8月3日
     * @param request
     * @return boolean
     */
    private boolean isValidCsrfFormToken(HttpServletRequest request) {
        String csrfToken = CSRFTokenManager.getTokenFromRequest(request);
        HttpSession session = request.getSession();
        if(csrfToken == null ||
                !csrfToken.equals(session.getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME).toString())){
            return false;
        }
        return true;
    }


    /**
     *
     * @Description 校验请求头是否携带token
     * @Author huachuan.qin
     * @date 2018年8月3日
     * @param request
     * @return boolean
     */
    private boolean isValidCsrfHeaderToken(HttpServletRequest request) {
        HttpSession session = request.getSession();
        if (request.getHeader("_RequestCSRFToken") == null
                || session.getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME) == null
                || !request .getHeader("_RequestCSRFToken").equals(session
                .getAttribute(CSRFTokenManager.CSRF_TOKEN_FOR_SESSION_ATTR_NAME).toString())) {
            return false;
        }
        return true;
    }
}
