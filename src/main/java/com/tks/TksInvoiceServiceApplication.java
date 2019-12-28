package com.tks;

import com.tks.config.WebConfig;
import com.tks.repository.UsersRepository;
import com.tks.service.InvoiceService;
import com.tks.service.ProductService;
import com.tks.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.web.servlet.WebMvcAutoConfiguration;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import org.springframework.web.servlet.i18n.CookieLocaleResolver;
import org.springframework.web.servlet.i18n.LocaleChangeInterceptor;

@SpringBootApplication
public class TksInvoiceServiceApplication extends WebConfig {

    @Autowired
    ProductService productService;

    @Autowired
    UserService userService;

    @Autowired
    InvoiceService invoiceService;

    public static void main(String[] args) {
        SpringApplication.run(TksInvoiceServiceApplication.class, args);
    }

    @Bean
    public CommandLineRunner initQuery(ApplicationContext applicationContext) {
        return args -> {
            productService.addDummyProductOnStartup();
            userService.addDummyProductOnStartup();
            invoiceService.addDummyInvloiceOnStartup();
        };
    }

    @Bean
    public LocaleResolver localeResolver() {
        return new CookieLocaleResolver();
    }

    @Bean
    public LocaleChangeInterceptor localeChangeInterceptor() {
        LocaleChangeInterceptor lci = new LocaleChangeInterceptor();
        lci.setParamName("lang");
        return lci;
    }


    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(localeChangeInterceptor());
    }
}
