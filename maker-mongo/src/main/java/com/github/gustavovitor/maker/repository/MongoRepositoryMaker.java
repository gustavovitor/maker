package com.github.gustavovitor.maker.repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.querydsl.QuerydslPredicateExecutor;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface MongoRepositoryMaker<T extends Serializable, ID> extends MongoRepository<T, ID>, QuerydslPredicateExecutor<T> { }
