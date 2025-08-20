package com.spingwithbushan.journalapp;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class JournalApplication {
    public static void main(String[] args) {
        SpringApplication.run(JournalApplication.class, args);
    }
}

//    @Bean
//    public PlatformTransactionManager transactionManager(MongoDatabaseFactory  dbFactory) {
//        return new MongoTransactionManager(dbFactory);
//    }

//platformTransactionalManager
//MongoTransactionManager