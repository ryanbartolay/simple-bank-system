package com.ryan.banking;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

@SpringBootApplication(scanBasePackages = "com.ryan.banking")
@EntityScan(basePackages = { "com.ryan.banking" })
@EnableJpaRepositories(basePackages = { "com.ryan.banking.repository" })
@ComponentScan(basePackages = { "com.ryan.banking.*" })
public class SimpleBankSystemWebApplication extends SpringBootServletInitializer {

    public static void main(String[] args) {
        SpringApplication.run(SimpleBankSystemWebApplication.class, args);
    }

    /**
     * In case we need to deploy in an external web container
     */
    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder builder) {
        return builder.sources(SimpleBankSystemWebApplication.class);
    }
}
