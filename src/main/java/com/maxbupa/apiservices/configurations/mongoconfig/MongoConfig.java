package com.maxbupa.apiservices.configurations.mongoconfig;


import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.mongodb.MongoDbFactory;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.SimpleMongoDbFactory;

@Configuration
public class MongoConfig {

    @Bean
    public MongoTemplate mongoTemplate(MongoDbFactory mongoDbFactory)
    {
        return new MongoTemplate(mongoDbFactory);
    }

    @Bean
    public SimpleMongoDbFactory mongoDbFactory()
    {
        MongoClientURI uri = new MongoClientURI("mongodb+srv://m001:m001@cluster0-skjqp.mongodb.net/test?retryWrites=true");
        MongoClient mongoClient = new MongoClient(uri);
        SimpleMongoDbFactory SimpleMongoDbFactoryInstance = new SimpleMongoDbFactory(mongoClient,"maxbupa");
        return SimpleMongoDbFactoryInstance;
    }
}