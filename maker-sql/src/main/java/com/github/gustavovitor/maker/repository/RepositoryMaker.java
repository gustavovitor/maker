package com.github.gustavovitor.maker.repository;

import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.NoRepositoryBean;

import java.io.Serializable;

@NoRepositoryBean
public interface RepositoryMaker<T extends Serializable, ID> extends CrudRepository<T, ID>, JpaSpecificationExecutor<T> { }
