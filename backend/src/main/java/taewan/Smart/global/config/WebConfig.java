package taewan.Smart.global.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Value("${resource.path}")
    private String resourcePath;

    @Value("${access.path}")
    private String accessPath;

    @Value("${client.address}")
    private String clientAddress;

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(accessPath)
                .addResourceLocations(resourcePath);
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(clientAddress)
                .allowedMethods("GET", "POST", "OPTIONS")
                .allowCredentials(true).maxAge(5);
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new AuthRefreshInterceptor())
                .order(1)
                .addPathPatterns("/members/**")
                .excludePathPatterns("/members/create", "/members/login");

        registry.addInterceptor(new AuthInterceptor())
                .order(2)
                .addPathPatterns("/members/**")
                .excludePathPatterns("/members/create", "/members/refresh", "/members/login");
    }
}
