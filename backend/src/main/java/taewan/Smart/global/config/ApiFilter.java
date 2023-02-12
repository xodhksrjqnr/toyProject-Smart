package taewan.Smart.global.config;

import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Arrays;
import java.util.List;

import static taewan.Smart.global.error.ExceptionStatus.PAGE_NOT_FOUND;

public class ApiFilter implements Filter {

    private static List<String> paths = Arrays.asList("/products", "/members", "/orders", "/category", "/testdata", "/testupload");

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {
        Filter.super.init(filterConfig);
    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {
        String url = ((HttpServletRequest) request).getRequestURI();
        boolean flag = false;

        for (String path : paths) {
            if (url.contains(path)) {
                flag = true;
                break;
            }
        }
        if (!flag)
            throw PAGE_NOT_FOUND.exception();
        chain.doFilter(request, response);
    }

    @Override
    public void destroy() {
        Filter.super.destroy();
    }
}
