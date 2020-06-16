package com.github.gustavovitor.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.ReflectionException;
import java.util.Map;

public interface ServiceInterface<T, ID, SP> {
    Page<T> findAllPageable(SP object, Pageable pageable) throws ReflectionException;

    void beforeInsert(T object);
    T insert(T object);

    void beforeUpdate(T savedObject, T object);
    T update(ID objectId, T object);

    void beforePatch(T savedObject, Map<String, Object> object);
    T patch(ID objectId, Map<String, Object> object, String... ignoreProperties);

    void beforeDelete(T objectId);
    void delete(ID objectId);

    Iterable<T> findAll(SP object) throws ReflectionException;

    T findById(ID objectId);
}
