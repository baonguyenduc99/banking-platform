package com.banking.common.config;

import java.util.Locale;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import org.springframework.web.server.i18n.AcceptHeaderLocaleContextResolver;

@Configuration
public class MessageSourcesConfig {

    @Bean
    public MessageSource messageSource() {
        ReloadableResourceBundleMessageSource ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename("classpath:messages");
        ms.setDefaultEncoding("UTF-8");
        ms.setFallbackToSystemLocale(false);
        ms.setDefaultLocale(Locale.ENGLISH);
        return ms;
    }

    @Bean
    public AcceptHeaderLocaleContextResolver localResolver() {
        AcceptHeaderLocaleContextResolver resolver = new AcceptHeaderLocaleContextResolver();
        resolver.setDefaultLocale(Locale.ENGLISH);
        return resolver;
    }
}
