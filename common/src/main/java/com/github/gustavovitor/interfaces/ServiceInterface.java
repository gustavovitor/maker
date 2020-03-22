package com.github.gustavovitor.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.ReflectionException;

public interface ServiceInterface<T, ID, SP> {
    Page<T> findAllPageable(SP object, Pageable pageable) throws ReflectionException;

    void beforeInsert(T object);
    T insert(T object);

    void beforeUpdate(ID objectId, T object);
    T update(ID objectId, T object);

    void beforePatch(T object);
    T patch(ID objectId, T object, String... ignoreProperties);

    void beforeDelete(ID objectId);
    void delete(ID objectId);

    Iterable<T> findAll(SP object) throws ReflectionException;

    T findById(ID objectId);
}
