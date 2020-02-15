package br.com.gustavomiranda.maker.service;

import br.com.gustavomiranda.maker.repository.RepositoryMaker;
import br.com.gustavomiranda.maker.repository.SpecificationBase;
import br.com.gustavomiranda.maker.util.MessageUtil;
import org.apache.commons.lang3.ArrayUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeanWrapperImpl;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.GenericTypeResolver;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import javax.management.ReflectionException;
import javax.persistence.EntityNotFoundException;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;

@SuppressWarnings({"unchecked", "SpringJavaInjectionPointsAutowiringInspection"})
public class ServiceMaker<R extends RepositoryMaker, T, ID, SPO, SP extends SpecificationBase<T>> implements ServiceInterface<T, ID, SPO> {

    @Autowired
    private R repository;

    private SP specification;

    protected R getRepository() {
        return repository;
    }

    protected SP getSpecification(SPO object) throws ReflectionException {
        try {
            Constructor[] constructors = (Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), ServiceMaker.class))[4]).getDeclaredConstructors();
            if (constructors.length > 0) {
                Constructor<SP> specificationConstructor = (Constructor<SP>) constructors[0];
                this.specification = specificationConstructor.newInstance(object);
                return specification;
            } else {
                throw new ReflectionException(new NoSuchMethodException(), MessageUtil.getMessage("specification.sem.construtor"));
            }
        } catch (InstantiationException e) {
            throw new ReflectionException(e, MessageUtil.getMessage("entity.instance.error", e.getMessage(), object.getClass().getName()));
        } catch (InvocationTargetException e) {
            throw new ReflectionException(e, MessageUtil.getMessage("entity.invoque.error", e.getMessage(), object.getClass().getName()));
        } catch (IllegalAccessException e) {
            throw new ReflectionException(e, MessageUtil.getMessage("entity.illegal.access.error", e.getMessage(), object.getClass().getName()));
        }
    }

    @Override
    public Page<T> findAllPageable(SPO object, Pageable pageable) throws ReflectionException {
        return repository.findAll(getSpecification(object), pageable);
    }

    @Override
    public T insert(T object) {
        return (T) repository.save(object);
    }

    @Override
    public T update(ID objectId, T object) {
        T savedObject = findById(objectId);
        BeanUtils.copyProperties(object, savedObject);
        return (T) repository.save(savedObject);
    }

    @Override
    public T patch(ID objectId, T object, String... ignoreProperties) {
        T savedObject = findById(objectId);
        BeanUtils.copyProperties(object, savedObject, ArrayUtils.addAll(ignoreProperties, getNullPropertyNames(object)));
        return (T) repository.save(savedObject);
    }

    @Override
    public void delete(ID objectId) {
        T object = findById(objectId);
        repository.delete(object);
    }

    @Override
    public List<T> findAll(SPO object) throws ReflectionException {
        return repository.findAll(getSpecification(object));
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

}
