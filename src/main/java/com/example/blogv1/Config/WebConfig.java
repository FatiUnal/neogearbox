package com.example.blogv1.Config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        // /upload/** yolunu /var/www/uploads/ dizinine yönlendir
        registry.addResourceHandler("/api/v1/upload/bake/**")
                .addResourceLocations("file:/var/www/upload/bake/")
                .setCachePeriod(3600) // İsteğe bağlı: Cache süresini ayarlar (saniye cinsinden)
                .resourceChain(true);
    }
}
