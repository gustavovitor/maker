package com.github.gustavovitor.interfaces;

import com.github.gustavovitor.util.ObjectPageableRequestWithSort;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.management.ReflectionException;
import java.io.Serializable;
import java.util.Map;

public interface ResourceInterface<S extends ServiceInterface, T extends Serializable, ID, SPO> {
    ResponseEntity<T> findById(ID objectId);
    ResponseEntity<Iterable<T>> findAll(SPO object) throws ReflectionException;
    ResponseEntity<Page<T>> findAllPageable(ObjectPageableRequestWithSort<SPO> request) throws ReflectionException;
    ResponseEntity<T> insert(T object);
    ResponseEntity<T> update(ID objectId, T object);
    ResponseEntity<T> patch(ID objectId, Map<String, Object> object);
    void delete(ID objectId);
}
