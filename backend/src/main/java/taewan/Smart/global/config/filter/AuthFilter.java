package taewan.Smart.global.config.filter;

import io.jsonwebtoken.JwtException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Set;

import static taewan.Smart.global.utils.JwtUtil.parseJwt;

@Slf4j
public class AuthFilter implements Filter {

    private final Set<String> excludeUrl = Set.of(
            "/members/create", "/members/login", "/members/certificate"
    );

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

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
                    parseJwt(httpServletRequest.getHeader(HttpHeaders.AUTHORIZATION));
                }
            }
            chain.doFilter(request, response);
        } catch (JwtException ex) {
            log.error("JwtException : Request Method : {}, URL : {}", httpServletRequest.getMethod(), httpServletRequest.getRequestURI());
            throw ex;
        }
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
