package common.CSRF;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpSession;
import java.util.UUID;



/**
 *
 * 用于CSRF中token的管理，在一定程度上防止csrf攻击
 *
 * */

public class CSRFTokenManager {
    private CSRFTokenManager() {
    }

    //token的名称
    static final String CSRF_PARAM_NAME = "csrfToken";


    public static final  String CSRF_TOKEN_FOR_SESSION_ATTR_NAME = CSRFTokenManager.class
            .getName() + ".tokenval";

    /**
     *
     * @Description 从session中获取token
     * @Author huachuan.qin
     * @date 2018年8月3日
     * @param session
     * @return String
     */
    public static String getTokenForSession(HttpSession session) {
        String token = null;

        synchronized (session) {
            token = (String) session.getAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME);
            if (null == token) {
                token = UUID.randomUUID().toString();
                session.setAttribute(CSRF_TOKEN_FOR_SESSION_ATTR_NAME, token);
            }
        }
        return token;
    }

    /**
     *
     * @Description 获取请求体携带的token
     * @Author huachuan.qin
     * @date 2018年8月3日
     * @param request
     * @return String
     */
    public static String getTokenFromRequest(HttpServletRequest request) {
        return request.getParameter(CSRF_PARAM_NAME);
    }
}
