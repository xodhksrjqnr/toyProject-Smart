package taewan.Smart.global.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static taewan.Smart.global.error.ExceptionStatus.EXPIRED_LOGIN_JWT;
import static taewan.Smart.global.util.JwtUtils.getJwt;
import static taewan.Smart.global.util.JwtUtils.parseJwt;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }
        String loginToken = getJwt(request, "loginToken");

        if (loginToken == null)
            throw EXPIRED_LOGIN_JWT.exception();
        try {
            parseJwt(loginToken);
        } catch (ExpiredJwtException e) {
            throw EXPIRED_LOGIN_JWT.exception();
        }
        return true;
    }
}
