package taewan.Smart.login.interceptor;

import io.jsonwebtoken.ExpiredJwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.servlet.HandlerInterceptor;

import javax.security.auth.message.AuthException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import static taewan.Smart.util.JwtUtils.getJwt;
import static taewan.Smart.util.JwtUtils.parseJwt;

@Slf4j
public class RefreshInterceptor implements HandlerInterceptor {

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getMethod().equals("OPTIONS")) {
            return true;
        }

        String refreshToken = getJwt(request, "refreshToken");

        if (refreshToken == null)
            throw new AuthException("[DetailErrorMessage:refreshToken이 만료되었습니다.]");
        try {
            parseJwt(refreshToken);
        } catch (ExpiredJwtException e) {
            throw new AuthException("[DetailErrorMessage:refreshToken이 만료되었습니다.]");
        }
        return true;
    }
}
