package com.example.productservice.infra.config;

import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ResourceBundleMessageSource;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.nio.charset.StandardCharsets;


@Configuration
public class MessageConfig implements WebMvcConfigurer {

    @Bean
    public MessageSource messageSource(){
        ResourceBundleMessageSource msgSource = new ResourceBundleMessageSource();
        msgSource.setBasename("messages");
        msgSource.setDefaultEncoding(StandardCharsets.UTF_8.displayName());
        return msgSource;
    }
}