package com.github.gustavovitor.maker.repository;

import com.github.gustavovitor.maker.GenericSpecificationCallerInterpreter;
import com.github.gustavovitor.util.MessageUtil;
import com.querydsl.core.types.ExpressionUtils;
import com.querydsl.core.types.Predicate;
import com.querydsl.core.types.dsl.Expressions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.repository.NoRepositoryBean;
import org.springframework.stereotype.Component;

import javax.management.ReflectionException;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@SuppressWarnings("unchecked")
public class MongoSpecificationBase<SPO> {

    SPO object;

    private final GenericSpecificationCallerInterpreter genericSpecificationCallerInterpreter = GenericSpecificationCallerInterpreter.getInstance();

    protected SPO getObject() { return this.object; }

    protected void setObject(SPO object) {
        this.object = object;
    }

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

    public Predicate toPredicate() {
        Predicate finalPredicate = null;
        Predicate truePredicate = Expressions.asBoolean(true).isTrue();

        if (nonNull(genericSpecificationCallerInterpreter))
            genericSpecificationCallerInterpreter.spec(finalPredicate, getObject());

        Predicate userPredicate = predicate(getObject());
        if (nonNull(userPredicate)) {
            if (nonNull(finalPredicate))
                finalPredicate = ExpressionUtils.allOf(finalPredicate, userPredicate);
            else
                finalPredicate = userPredicate;
        } else {
            if (isNull(finalPredicate))
                finalPredicate = truePredicate;
        }

        return finalPredicate;
    }

    // Rewrite here your predicate.
    protected Predicate predicate(SPO object) {
        return null;
    }
}
