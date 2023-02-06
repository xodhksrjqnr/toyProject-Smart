package taewan.Smart.login.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static taewan.Smart.util.CookieUtils.findCookie;
import static taewan.Smart.util.JwtUtils.parseJwt;

public class RefreshInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        Cookie refreshToken = findCookie(request.getCookies(), "refreshToken");

        if (refreshToken == null)
            throw new AuthException("[DetailErrorMessage:refreshToken이 만료되었습니다.]");
        try {
            parseJwt(refreshToken.getValue());
        } catch (ExpiredJwtException e) {
            throw new AuthException("[DetailErrorMessage:refreshToken이 만료되었습니다.]");
        }
        return true;
    }
}
