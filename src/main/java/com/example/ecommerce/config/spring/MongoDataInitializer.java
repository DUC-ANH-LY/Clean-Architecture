package com.example.ecommerce.config.spring;

import com.example.ecommerce.infra.spring.persistence.nosql.document.ProductDocument;
import com.example.ecommerce.infra.spring.persistence.nosql.repository.SpringDataProductMongoRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;
import java.util.List;

@Component
@Profile("nosql")
public class MongoDataInitializer implements CommandLineRunner {

    private final SpringDataProductMongoRepository productRepository;

    public MongoDataInitializer(SpringDataProductMongoRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            System.out.println("NoSQL (MongoDB) detected empty products collection. Seeding initial products...");
            List<ProductDocument> seedProducts = List.of(
                    new ProductDocument("prod-1", "Laptop", new BigDecimal("999.99"), 50),
                    new ProductDocument("prod-2", "Wireless Mouse", new BigDecimal("29.99"), 200),
                    new ProductDocument("prod-3", "Mechanical Keyboard", new BigDecimal("149.99"), 100)
            );
            productRepository.saveAll(seedProducts);
            System.out.println("MongoDB seeded with products: prod-1, prod-2, prod-3.");
        } else {
            System.out.println("MongoDB products collection already has data. Skipping seed.");
        }
    }
}
