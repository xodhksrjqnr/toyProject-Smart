package taewan.Smart.global.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import taewan.Smart.global.config.properties.AddressProperties;
import taewan.Smart.global.config.properties.PathProperties;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final PathProperties pathProperties;
    private final AddressProperties clientProperties;

    @Autowired
    public WebConfig(PathProperties pathProperties, AddressProperties clientProperties) {
        this.pathProperties = pathProperties;
        this.clientProperties = clientProperties;
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        registry.addResourceHandler(pathProperties.getAccess())
                .addResourceLocations(pathProperties.getResource());
    }

    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedOrigins(clientProperties.getClient())
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
