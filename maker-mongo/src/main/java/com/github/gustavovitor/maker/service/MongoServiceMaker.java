package com.github.gustavovitor.maker.service;

import com.github.gustavovitor.exceptions.ValidationException;
import com.github.gustavovitor.interfaces.ServiceInterface;
import com.github.gustavovitor.maker.GenericCallerInterpreter;
import com.github.gustavovitor.maker.GenericErrorInterpreter;
import com.github.gustavovitor.maker.repository.MongoRepositoryMaker;
import com.github.gustavovitor.maker.repository.MongoSpecificationBase;
import com.github.gustavovitor.maker.service.exceptions.DocumentNotFoundException;
import com.github.gustavovitor.util.EntityUtils;
import com.github.gustavovitor.util.MessageUtil;
import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.validation.BeanPropertyBindingResult;
import org.springframework.validation.SmartValidator;

import javax.management.ReflectionException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.Map;
import java.util.Objects;
import java.util.Set;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@SuppressWarnings({"unchecked", "SpringJavaInjectionPointsAutowiringInspection"})
public class MongoServiceMaker<R extends MongoRepositoryMaker, T, ID, SPO, SP extends MongoSpecificationBase<SPO>> implements ServiceInterface<T, ID, SPO> {

    @Autowired
    private R repository;

    @Autowired
    private SmartValidator validator;

    @Autowired(required = false)
    private GenericErrorInterpreter genericErrorInterpreter;

    @Autowired
    private GenericCallerInterpreter genericCallerInterpreter;

    private SP specification;

    protected R getRepository() {
        return repository;
    }

    public SP getSpecification(SPO object) throws ReflectionException {
        try {
            Constructor<SP> specificationConstructor = (Constructor<SP>) (Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), MongoServiceMaker.class))[4]).getConstructor(object.getClass());
            this.specification = specificationConstructor.newInstance(object);
            return specification;
        } catch (InstantiationException e) {
            throw new ReflectionException(e, MessageUtil.getMessage("entity.instance.error", e.getMessage(), object.getClass().getName()));
        } catch (InvocationTargetException e) {
            throw new ReflectionException(e, MessageUtil.getMessage("entity.invoque.error", e.getMessage(), object.getClass().getName()));
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e, MessageUtil.getMessage("entity.illegal.access.error", e.getMessage(), object.getClass().getName()));
        } catch (NoSuchMethodException e) {
            throw new ReflectionException(e, MessageUtil.getMessage("no.such.method.error", e.getMessage(), object.getClass().getName()));
        }
    }

    @Override
    public Page<T> findAllPageable(SPO object, Pageable pageable) throws ReflectionException {
        return repository.findAll(getSpecification(object).toPredicate(), pageable);
    }

    @Override
    public void beforeInsert(T object) {

    }

    @Override
    public T insert(T object) {
        try {
            beforeInsert(object);

            if (nonNull(genericCallerInterpreter))
                genericCallerInterpreter.onInsert(this, repository, object);
            T savedObject = (T) repository.insert(object);
            afterInsert(savedObject);
            return savedObject;
        } catch (Exception e) {
            onInsertError(e, object);

            if (nonNull(genericErrorInterpreter))
                genericErrorInterpreter.onInsertError(this, repository, e, object);

            throw e;
        }
    }

    @Override
    public void afterInsert(T object) {

    }

    @Override
    public void onInsertError(Throwable e, T object) {

    }

    @Override
    public void beforeUpdate(T savedObject, T object) {

    }

    @Override
    public T update(ID objectId, T object) {
        try {
            T savedObject = findById(objectId);
            handleNotFoundException(savedObject);
            beforeUpdate(savedObject, object);

            if (nonNull(genericCallerInterpreter))
                genericCallerInterpreter.onUpdate(this, repository, savedObject, object);
            BeanUtils.copyProperties(object, savedObject);
            T savedObjectNow = (T) repository.save(savedObject);
            afterUpdate(savedObjectNow, object);
            return savedObjectNow;
        } catch (Exception e) {
            onUpdateError(e, objectId, object);

            if (nonNull(genericErrorInterpreter))
                genericErrorInterpreter.onUpdateError(this, repository, e, objectId, object);

            throw e;
        }
    }

    @Override
    public void afterUpdate(T savedObject, T object) {

    }

    @Override
    public void onUpdateError(Throwable e, ID objectId, T object) {

    }

    @Override
    public void beforePatch(T savedObject, Map<String, Object> object) {

    }

    @Override
    public T patch(ID objectId, Map<String, Object> object, String... ignoreProperties) {
        try {
            T savedObject = findById(objectId);
            handleNotFoundException(savedObject);
            beforePatch(savedObject, object);

            if (nonNull(genericCallerInterpreter))
                genericCallerInterpreter.onPatch(this, repository, savedObject, object);
            EntityUtils.merge(object, savedObject, savedObject.getClass());
            T savedObjectNow = (T) repository.save(savedObject);
            afterPatch(savedObjectNow, object);
            return savedObjectNow;
        } catch (Exception e) {
            onPatchError(e, objectId, object);

            if (nonNull(genericErrorInterpreter))
                genericErrorInterpreter.onPatchError(this, repository, e, objectId, object);

            throw e;
        }
    }

    @Override
    public void afterPatch(T savedObject, Map<String, Object> object) {

    }

    @Override
    public void onPatchError(Throwable e, ID objectId, Map<String, Object> object) {

    }

    @Override
    public void beforeDelete(T object) {

    }

    @Override
    public void delete(ID objectId) {
        T object = findById(objectId);
        try {
            handleNotFoundException(object);
            beforeDelete(object);

            if (nonNull(genericCallerInterpreter))
                genericCallerInterpreter.onDelete(this, repository, object);
            repository.delete(object);
            afterDelete(object);
        } catch (Exception e) {
            onDeleteError(e, object);

            if (nonNull(genericErrorInterpreter))
                genericErrorInterpreter.onDeleteError(this, repository, e, object);

            throw e;
        }
    }

    @Override
    public void afterDelete(T object) {

    }

    @Override
    public void onDeleteError(Throwable e, T object) {

    }

    @Override
    public void validate(T object) {
        BeanPropertyBindingResult bindingResult = new BeanPropertyBindingResult(object,
                StringUtils.uncapitalize(object.getClass().getSimpleName()));
        validator.validate(object, bindingResult);

        if (bindingResult.hasErrors()) {
            throw new ValidationException(bindingResult);
        }
    }

    @Override
    public Iterable<T> findAll(SPO object) throws ReflectionException {
        return repository.findAll(getSpecification(object).toPredicate());
    }

    @Override
    public T findById(ID objectId) {
        return (T) repository.findById(objectId).orElse(null);
    }


    private String[] getNullPropertyNames(T source) {
        final BeanWrapper src = new BeanWrapperImpl(source);
        java.beans.PropertyDescriptor[] pds = src.getPropertyDescriptors();

        Set<String> emptyNames = new HashSet<>();
        for (java.beans.PropertyDescriptor pd : pds) {
            Object srcValue = src.getPropertyValue(pd.getName());
            if (srcValue == null) emptyNames.add(pd.getName());
        }
        String[] result = new String[emptyNames.size()];
        return emptyNames.toArray(result);
    }

    private void handleNotFoundException(T savedObject) {
        if (isNull(savedObject)) {
            throw new DocumentNotFoundException(MessageUtil.getMessage("entity.not.found.exception"));
        }
    }

}
