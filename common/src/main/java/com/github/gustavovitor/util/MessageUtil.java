package com.github.gustavovitor.util;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.text.MessageFormat;
import java.util.Locale;

@Configuration
public class MessageUtil {

    public static String getMessage(String code, String... parameters) {
        return MessageFormat.format(messageSource().getMessage(code, null, Locale.getDefault()), parameters);
    }

    public static String getMessage(MessageSourceResolvable resolvable) {
        return messageSource().getMessage(resolvable, Locale.getDefault());
    }

    @Bean
    public static MessageSource messageSource() {
        ReloadableResourceBundleMessageSource messageSource = new ReloadableResourceBundleMessageSource();
        messageSource.setBasename("messages");
        messageSource.setDefaultEncoding("UTF-8");
        messageSource.setCacheSeconds(1);
        return messageSource;
    }

}
