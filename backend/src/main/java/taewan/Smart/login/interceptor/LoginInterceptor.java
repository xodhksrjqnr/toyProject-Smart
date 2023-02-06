package taewan.Smart.login.interceptor;

import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static taewan.Smart.util.CookieUtils.findCookie;
import static taewan.Smart.util.JwtUtils.parseJwt;

@Slf4j
public class LoginInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        log.info("[LoginInterceptor] : {}", request.getRequestURI());
        Cookie loginToken = findCookie(request.getCookies(), "loginToken");

        if (loginToken == null)
            throw new AuthException();
        parseJwt(loginToken.getValue());
        return true;
    }
}
