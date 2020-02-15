package br.com.gustavomiranda.maker.repository;

import br.com.gustavomiranda.maker.util.MessageUtil;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.domain.Specification;

import javax.management.ReflectionException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static java.util.Objects.isNull;

@SuppressWarnings("unchecked")
public class SpecificationBase<T> implements Specification<T> {

    T object;

    protected T getObject() { return this.object; }

    public SpecificationBase(T object) throws ReflectionException {
        if (isNull(object)) {
            try {
                object = (T) Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), SpecificationBase.class))[0].getConstructor().newInstance();
            } catch (InstantiationException e) {
                throw new ReflectionException(e, MessageUtil.getMessage("entity.instance.error", e.getMessage(), object.getClass().getName()));
            } catch (InvocationTargetException e) {
                throw new ReflectionException(e, MessageUtil.getMessage("entity.invoque.error", e.getMessage(), object.getClass().getName()));
            } catch (NoSuchMethodException e) {
                throw new ReflectionException(e, MessageUtil.getMessage("no.constructor.found.error", e.getMessage(), object.getClass().getName()));
            } catch (IllegalAccessException e) {
                throw new ReflectionException(e, MessageUtil.getMessage("entity.illegal.access.error", e.getMessage(), object.getClass().getName()));
            }
        }
        this.object = object;
    }

    @Override
    public Predicate toPredicate(Root<T> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) { return null; }
}
