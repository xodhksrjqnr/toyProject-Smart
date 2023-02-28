package taewan.Smart.global.config;

import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import taewan.Smart.global.config.filter.AuthFilter;

import javax.servlet.Filter;

import static taewan.Smart.global.util.PropertyUtils.*;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Bean
    public FilterRegistrationBean<Filter> authFilter() {
        FilterRegistrationBean<Filter> filter = new FilterRegistrationBean<>();

        filter.setFilter(new AuthFilter());
        filter.addUrlPatterns("/members/*", "/orders/*");
        return filter;
    }

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
                .allowCredentials(true).maxAge(-1);
    }
}
