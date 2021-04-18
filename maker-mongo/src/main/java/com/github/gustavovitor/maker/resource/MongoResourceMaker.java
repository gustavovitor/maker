package com.github.gustavovitor.maker.resource;

import com.github.gustavovitor.interfaces.ResourceInterface;
import com.github.gustavovitor.maker.service.MongoServiceMaker;
import com.github.gustavovitor.util.ObjectPageableRequestWithSort;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.management.ReflectionException;
import javax.validation.Valid;

import java.util.Map;

import static com.github.gustavovitor.util.PageableUtils.getCustomPageable;

@SuppressWarnings({"unchecked"})
public class MongoResourceMaker<S extends MongoServiceMaker, T, ID, SPO> implements ResourceInterface<S, T, ID, SPO> {

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
    public ResponseEntity<Page<T>> findAllPageable(@RequestBody ObjectPageableRequestWithSort<SPO> request) throws ReflectionException {
        return ResponseEntity.ok(service.findAllPageable(request.getObject(), getCustomPageable(request.getPageable())));
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
    public ResponseEntity<T> patch(@PathVariable ID objectId, @RequestBody @Valid Map<String, Object> object) {
        return (ResponseEntity<T>) ResponseEntity.ok(service.patch(objectId, object));
    }

    @Override
    @DeleteMapping("/{objectId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable ID objectId) {
        service.delete(objectId);
    }

}
