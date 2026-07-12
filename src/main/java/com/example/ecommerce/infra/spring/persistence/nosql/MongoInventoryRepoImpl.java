package com.example.ecommerce.infra.spring.persistence.nosql;

import com.example.ecommerce.core.usecase.InventoryRepoI;
import com.example.ecommerce.infra.spring.persistence.nosql.document.ProductDocument;
import com.example.ecommerce.infra.spring.persistence.nosql.repository.SpringDataProductMongoRepository;
import org.springframework.context.annotation.Profile;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.data.mongodb.core.query.Update;
import org.springframework.stereotype.Repository;

@Repository
@Profile("nosql")
public class MongoInventoryRepoImpl implements InventoryRepoI {

    private final SpringDataProductMongoRepository mongoRepository;
    private final MongoTemplate mongoTemplate;

    public MongoInventoryRepoImpl(SpringDataProductMongoRepository mongoRepository,
                                  MongoTemplate mongoTemplate) {
        this.mongoRepository = mongoRepository;
        this.mongoTemplate = mongoTemplate;
    }

    @Override
    public boolean isStockAvailable(String productId, int quantity) {
        return mongoRepository.findById(productId)
                .map(p -> p.getStockQuantity() >= quantity)
                .orElse(false);
    }

    @Override
    public void reduceStock(String productId, int quantity) {
        Query query = new Query(
                Criteria.where("_id").is(productId)
                        .and("stock_quantity").gte(quantity)
        );
        Update update = new Update().inc("stock_quantity", -quantity);
        var result = mongoTemplate.updateFirst(query, update, ProductDocument.class);

        if (result.getModifiedCount() == 0) {
            throw new IllegalStateException("Failed to reduce stock for product: " + productId);
        }
    }
}
