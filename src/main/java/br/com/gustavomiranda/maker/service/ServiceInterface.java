package br.com.gustavomiranda.maker.service;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.ReflectionException;
import java.util.List;

public interface ServiceInterface<T, ID, SP> {
    Page<T> findAllPageable(SP object, Pageable pageable) throws ReflectionException;

    T insert(T object);

    T update(ID objectId, T object);

    T patch(ID objectId, T object, String... ignoreProperties);

    void delete(ID objectId);

    List<T> findAll(SP object) throws ReflectionException;

    T findById(ID objectId);
}
