package com.github.gustavovitor.interfaces;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.ReflectionException;
import java.util.Map;

public interface ServiceInterface<T, ID, SP> {
    Page<T> findAllPageable(SP object, Pageable pageable) throws ReflectionException;

    void beforeInsert(T object);
    T insert(T object);
    void afterInsert(T object);
    void onInsertError(Throwable e, T object);

    void beforeUpdate(T savedObject, T object);
    T update(ID objectId, T object);
    void afterUpdate(T savedObject, T object);
    void onUpdateError(Throwable e, ID objectId, T object);

    void beforePatch(T savedObject, Map<String, Object> object);
    T patch(ID objectId, Map<String, Object> object, String... ignoreProperties);
    void afterPatch(T savedObject, Map<String, Object> object);
    void onPatchError(Throwable e, ID objectId, Map<String, Object> object);

    void beforeDelete(T object);
    void delete(ID objectId);
    void afterDelete(T object);
    void onDeleteError(Throwable e, T object);

    void validate(T object);

    Iterable<T> findAll(SP object) throws ReflectionException;

    T findById(ID objectId);
}
