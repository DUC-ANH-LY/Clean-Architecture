package com.example.ecommerce.infra.persistence.nosql.spring.repository;

import com.example.ecommerce.infra.persistence.nosql.spring.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataOrderMongoRepository extends MongoRepository<OrderDocument, String> {
}
