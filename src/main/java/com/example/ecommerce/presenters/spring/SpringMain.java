package com.example.ecommerce.presenters.spring;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;


@SpringBootApplication(scanBasePackages = "com.example.ecommerce")
public class SpringMain {

    public static void main(String[] args) {
        SpringApplication.run(SpringMain.class, args);
    }
}
