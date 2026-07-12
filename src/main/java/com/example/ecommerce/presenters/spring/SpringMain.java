package com.example.ecommerce.presenters.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.domain.EntityScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@SpringBootApplication(scanBasePackages = {
        "com.example.ecommerce.presenters.spring",
        "com.example.ecommerce.config.spring",
        "com.example.ecommerce.infra.spring"
})
@EntityScan(basePackages = "com.example.ecommerce.infra.persistence.sql.entity")
@EnableJpaRepositories(basePackages = "com.example.ecommerce.infra.spring.persistence.sql.repository")
@EnableMongoRepositories(basePackages = "com.example.ecommerce.infra.spring.persistence.nosql.repository")
public class SpringMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);
    }
}
