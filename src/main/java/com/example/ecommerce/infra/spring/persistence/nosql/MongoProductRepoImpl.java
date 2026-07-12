package com.example.ecommerce.infra.spring.persistence.nosql;

import com.example.ecommerce.core.entity.Product;
import com.example.ecommerce.core.usecase.ProductRepoI;
import com.example.ecommerce.infra.spring.persistence.nosql.document.ProductDocument;
import com.example.ecommerce.infra.spring.persistence.nosql.repository.SpringDataProductMongoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
@Profile("nosql")
public class MongoProductRepoImpl implements ProductRepoI {

    private final SpringDataProductMongoRepository mongoRepository;

    public MongoProductRepoImpl(SpringDataProductMongoRepository mongoRepository) {
        this.mongoRepository = mongoRepository;
    }

    @Override
    public Optional<Product> findById(String productId) {
        return mongoRepository.findById(productId).map(this::toDomain);
    }

    private Product toDomain(ProductDocument doc) {
        return new Product(doc.getId(), doc.getName(), doc.getPrice(), doc.getStockQuantity());
    }
}
