package com.github.gustavovitor.maker.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

@NoRepositoryBean
public interface RepositoryMaker<T, ID> extends CrudRepository<T, ID>, JpaSpecificationExecutor<T> { }
