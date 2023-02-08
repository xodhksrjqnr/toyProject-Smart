package taewan.Smart.global.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static taewan.Smart.global.error.ExceptionStatus.LOGIN_JWT_EXPIRED;
import static taewan.Smart.global.util.JwtUtils.parseJwt;

@Slf4j
public class AuthInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String loginToken = request.getHeader(HttpHeaders.AUTHORIZATION);

        if (loginToken == null)
            throw LOGIN_JWT_EXPIRED.exception();
        try {
            parseJwt(loginToken);
        } catch (ExpiredJwtException e) {
            throw LOGIN_JWT_EXPIRED.exception();
        }
        return true;
    }
}
