package taewan.Smart.global.config;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static taewan.Smart.global.error.ExceptionStatus.EXPIRED_REFRESH_JWT;
import static taewan.Smart.global.util.JwtUtils.getJwt;
import static taewan.Smart.global.util.JwtUtils.parseJwt;

@Slf4j
public class AuthRefreshInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String refreshToken = getJwt(request, "refreshToken");

        if (refreshToken == null)
            throw EXPIRED_REFRESH_JWT.exception();
        try {
            parseJwt(refreshToken);
        } catch (ExpiredJwtException e) {
            throw EXPIRED_REFRESH_JWT.exception();
        }
        return true;
    }
}
