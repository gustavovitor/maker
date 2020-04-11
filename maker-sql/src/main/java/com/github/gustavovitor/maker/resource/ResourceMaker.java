package com.github.gustavovitor.maker.resource;

import com.github.gustavovitor.interfaces.ResourceInterface;
import com.github.gustavovitor.maker.service.ServiceMaker;
import com.github.gustavovitor.util.ObjectPageableRequest;
import com.github.gustavovitor.util.RequestPageable;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ReflectionException;
import javax.validation.Valid;
import java.util.List;

import static java.util.Objects.nonNull;

@SuppressWarnings({"unchecked"})
public class ResourceMaker<S extends ServiceMaker, T, ID, SPO> implements ResourceInterface<S, T, ID, SPO> {

    @Autowired
    private S service;

    protected S getService() {
        return service;
    }

    @Override
    @GetMapping("{objectId}")
    public ResponseEntity<T> findById(@PathVariable("objectId") ID objectId) {
        return (ResponseEntity<T>) ResponseEntity.ok(service.findById(objectId));
    }

    @Override
    @PutMapping("/search")
    public ResponseEntity<Iterable<T>> findAll(@RequestBody SPO object) throws ReflectionException {
        return ResponseEntity.ok(service.findAll(object));
    }

    @Override
    @PutMapping("/search/page")
    public ResponseEntity<Page<T>> findAllPageable(@RequestBody ObjectPageableRequest<SPO> request) throws ReflectionException {
        return ResponseEntity.ok(service.findAllPageable(request.getObject(), getPageable(request.getPageable())));
    }

    @Override
    @PostMapping
    public ResponseEntity<T> insert(@RequestBody @Valid T object) {
        return (ResponseEntity<T>) ResponseEntity.status(HttpStatus.CREATED).body(service.insert(object));
    }

    @Override
    @PutMapping("/{objectId}")
    public ResponseEntity<T> update(@PathVariable ID objectId, @RequestBody @Valid T object) {
        return (ResponseEntity<T>) ResponseEntity.ok(service.update(objectId, object));
    }

    @Override
    @PatchMapping("/{objectId}")
    public ResponseEntity<T> patch(@PathVariable ID objectId, @RequestBody @Valid T object) {
        return (ResponseEntity<T>) ResponseEntity.ok(service.patch(objectId, object));
    }

    @Override
    @DeleteMapping("/{objectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable ID objectId) {
        service.delete(objectId);
    }

    protected Pageable getPageable(RequestPageable pageable) {
        if (nonNull(pageable.getSort()))
            return PageRequest.of(pageable.getPage(), pageable.getSize(), pageable.getSort());
        else
            return PageRequest.of(pageable.getPage(), pageable.getSize());
    }

}
