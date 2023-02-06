package taewan.Smart.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import taewan.Smart.login.interceptor.LoginInterceptor;
import taewan.Smart.login.interceptor.RefreshInterceptor;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${resource.path}")
    private String resourcePath;

    @Value("${access.path}")
    private String accessPath;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(accessPath)
                .addResourceLocations(resourcePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins("*");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new RefreshInterceptor())
                .order(1)
                .addPathPatterns("/logout", "/members/**")
                .excludePathPatterns("members/create");

        registry.addInterceptor(new LoginInterceptor())
                .order(2)
                .addPathPatterns("/logout", "/members/**")
                .excludePathPatterns("/members/create", "members/refresh");
    }
}
