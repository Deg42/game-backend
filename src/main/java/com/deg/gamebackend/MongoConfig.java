package com.deg.gamebackend;

import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.mongodb.MongoDatabaseFactory;
import org.springframework.data.mongodb.MongoTransactionManager;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoClientDatabaseFactory;
import org.springframework.data.mongodb.core.convert.MappingMongoConverter;
import org.springframework.data.mongodb.core.convert.MongoCustomConversions;
import org.springframework.data.mongodb.core.mapping.MongoMappingContext;
import org.springframework.data.mongodb.repository.config.EnableMongoRepositories;

@Configuration
@EnableMongoRepositories(basePackages = "com.deg.gamebackend.repository")
public class MongoConfig {
    @Value("${spring.data.mongodb.uri}")
    private String mongoUri;

    public MongoTemplate mongoTemplate() {
        return new MongoTemplate(new SimpleMongoClientDatabaseFactory(mongoUri));
    }
}
