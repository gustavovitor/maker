package br.com.gustavomiranda.maker.resource;

import br.com.gustavomiranda.maker.service.ServiceMaker;
import br.com.gustavomiranda.maker.util.ObjectPageableRequest;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;

import javax.management.ReflectionException;
import java.util.List;

public interface ResourceInterface<S extends ServiceMaker, T> {
    ResponseEntity<T> findById(Long objectId);

    ResponseEntity<List<T>> findAll(T object) throws ReflectionException;

    ResponseEntity<Page<T>> findAllPageable(ObjectPageableRequest<T> request) throws ReflectionException;

    ResponseEntity<T> insert(T object);

    ResponseEntity<T> update(Long objectId, T object);

    ResponseEntity<T> patch(Long objectId, T object);

    void delete(Long objectId);
}
