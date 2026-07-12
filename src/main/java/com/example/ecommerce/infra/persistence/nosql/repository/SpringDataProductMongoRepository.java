package com.example.ecommerce.infra.persistence.nosql.repository;

import com.example.ecommerce.infra.persistence.nosql.document.ProductDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataProductMongoRepository extends MongoRepository<ProductDocument, String> {
}
