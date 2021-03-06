package com.johnathanmarksmith.mongodb.example.repository;


import com.johnathanmarksmith.mongodb.example.domain.Person;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Repository;

import java.util.List;


/**
 * Date:   6/26/13 / 1:22 PM
 * Author: Johnathan Mark Smith
 * Email:  john@johnathanmarksmith.com
 * <p/>
 * Comments:
 * <p/>
 * This is my Person Repository
 */


@Repository
public class PersonRepository {

    static final Logger logger = LoggerFactory.getLogger(PersonRepository.class);

    @Autowired
    MongoTemplate mongoTemplate;

    public void countUnderAge() {
        List<Person> results = null;

        Query query = new Query();
        Criteria criteria = new Criteria();
        criteria = criteria.and("age").lte(21);

        query.addCriteria(criteria);
        results = mongoTemplate.find(query, Person.class);

        logger.info("Total number of under age in database: {}", results.size());
    }

    /**
     * This will count how many Person Objects I have
     */
    public void countAllPersons() {
        List<Person> results = mongoTemplate.findAll(Person.class);
        logger.info("Total number in database: {}", results.size());
    }

    /**
     * This will install a new Person object with my
     * name and random age
     */
    public void insertPersonWithNameJohnathanAndRandomAge() {

        double age = Math.ceil(Math.random() * 100);
        Person p = new Person("Johnathan", (int) age);

        mongoTemplate.insert(p);
    }

    /**
     * this will create a {@link Person} collection if the collection does not already exists
     */
    public void createPersonCollection() {
        if (!mongoTemplate.collectionExists(Person.class)) {
            mongoTemplate.createCollection(Person.class);
        }
    }

    /**
     * this will drop the {@link Person} collection if the collection does already exists
     */
    public void dropPersonCollection() {
        if (mongoTemplate.collectionExists(Person.class)) {
            mongoTemplate.dropCollection(Person.class);
        }
    }
}
