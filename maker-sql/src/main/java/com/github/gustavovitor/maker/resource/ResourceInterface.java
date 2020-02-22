package com.github.gustavovitor.maker.resource;

import com.github.gustavovitor.maker.service.ServiceMaker;
import com.github.gustavovitor.util.ObjectPageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.management.ReflectionException;
import java.util.List;

public interface ResourceInterface<S extends ServiceMaker, T, ID> {
    ResponseEntity<T> findById(ID objectId);

    ResponseEntity<List<T>> findAll(T object) throws ReflectionException;

    ResponseEntity<Page<T>> findAllPageable(ObjectPageableRequest<T> request) throws ReflectionException;

    ResponseEntity<T> insert(T object);

    ResponseEntity<T> update(ID objectId, T object);

    ResponseEntity<T> patch(ID objectId, T object);

    void delete(ID objectId);
}
