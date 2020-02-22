package com.github.gustavovitor.maker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface MongoRepositoryMaker<T, ID> extends MongoRepository<T, ID>, QuerydslPredicateExecutor<T> { }
