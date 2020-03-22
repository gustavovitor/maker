package com.github.gustavovitor.maker.repository;

import com.github.gustavovitor.util.MessageUtil;
import com.querydsl.core.types.Predicate;
import org.springframework.core.GenericTypeResolver;

import javax.management.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.Objects;

import static java.util.Objects.isNull;

@SuppressWarnings("unchecked")
public class MongoSpecificationBase<SPO> {

    SPO object;

    protected SPO getObject() { return this.object; }

    public MongoSpecificationBase() { }

    public MongoSpecificationBase(SPO object) throws ReflectionException {
        if (isNull(object)) {
            try {
                object = (SPO) Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), MongoSpecificationBase.class))[1].getConstructor().newInstance();
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

    public Predicate toPredicate() { return null; }
}
