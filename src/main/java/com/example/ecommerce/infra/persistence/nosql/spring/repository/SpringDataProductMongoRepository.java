package com.example.ecommerce.infra.persistence.nosql.spring.repository;

import com.example.ecommerce.infra.persistence.nosql.spring.document.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataProductMongoRepository extends MongoRepository<ProductDocument, String> {
}
