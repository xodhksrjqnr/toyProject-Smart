package taewan.Smart.global.config.filter;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.util.Set;

import static taewan.Smart.global.util.JwtUtils.parseJwt;

@Slf4j
public class AuthFilter implements Filter {

    private final Set<String> excludeUrl = Set.of(
            "/members/create", "/members/login", "/members/certificate"
    );

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        HttpServletRequest httpServletRequest = (HttpServletRequest) request;

        try {
            if (!(httpServletRequest.getMethod().equals("OPTIONS"))) {
                boolean flag = false;

                for (String exclude : excludeUrl) {
                    flag = httpServletRequest.getRequestURI().contains(exclude) || flag;
                }
                if (!flag) {
                    String token = httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION);
                    Claims claims = parseJwt(token);
                    httpServletRequest.setAttribute("tokenMemberId", claims.get("memberId"));
                    httpServletRequest.setAttribute("type", claims.get("type"));
                    if (claims.get("type") != null) {
                        httpServletRequest.setAttribute("email", claims.get("email"));
                    }
                }
            }
            chain.doFilter(httpServletRequest, response);
        } catch (JwtException ex) {
            log.warn("JwtException : Request Method : {}, URL : {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
            throw ex;
        }
    }
}
