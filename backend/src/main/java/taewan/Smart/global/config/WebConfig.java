package taewan.Smart.global.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import static taewan.Smart.global.utils.PropertyUtil.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(getAccessPath())
                .addResourceLocations(getResourcePath());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(getClientAddress())
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowCredentials(true).maxAge(5);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthInterceptor())
                .addPathPatterns("/members/**", "/orders/**")
                .excludePathPatterns(
                        "/members/create", "/members/login", "/members/certificate/*"
                );
    }
}
