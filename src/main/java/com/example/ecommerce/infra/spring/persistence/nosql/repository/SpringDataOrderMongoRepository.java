package com.example.ecommerce.infra.spring.persistence.nosql.repository;

import com.example.ecommerce.infra.spring.persistence.nosql.document.OrderDocument;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface SpringDataOrderMongoRepository extends MongoRepository<OrderDocument, String> {
}
