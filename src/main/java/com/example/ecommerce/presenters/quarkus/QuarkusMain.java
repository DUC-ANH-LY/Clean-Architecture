package com.example.ecommerce.presenters.quarkus;

import io.quarkus.runtime.Quarkus;
import io.quarkus.runtime.QuarkusApplication;

@io.quarkus.runtime.annotations.QuarkusMain
public class QuarkusMain implements QuarkusApplication {

    public static void main(String... args) {
        Quarkus.run(QuarkusMain.class, args);
    }

    @Override
    public int run(String... args) throws Exception {
        System.out.println("Quarkus Order Service is running...");
        Quarkus.waitForExit();
        return 0;
    }
}
