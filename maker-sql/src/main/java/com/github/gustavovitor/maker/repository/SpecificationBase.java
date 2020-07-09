package com.github.gustavovitor.maker.repository;

import com.github.gustavovitor.maker.GenericSpecificationCallerInterpreter;
import com.github.gustavovitor.util.MessageUtil;
import org.springframework.core.GenericTypeResolver;
import org.springframework.data.jpa.domain.Specification;

import javax.management.ReflectionException;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import static java.util.Objects.isNull;
import static java.util.Objects.nonNull;

@SuppressWarnings("unchecked")
public class SpecificationBase<SPO> implements Specification<SPO> {

    SPO object;

    private final GenericSpecificationCallerInterpreter genericSpecificationCallerInterpreter = GenericSpecificationCallerInterpreter.getInstance();

    protected SPO getObject() { return this.object; }

    protected void setObject(SPO object) {
        this.object = object;
    }

    public SpecificationBase() { }

    public SpecificationBase(SPO object) throws ReflectionException {
        if (isNull(object)) {
            try {
                object = (SPO) Objects.requireNonNull(GenericTypeResolver.resolveTypeArguments(getClass(), SpecificationBase.class))[1].getConstructor().newInstance();
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
    public Predicate toPredicate(Root<SPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder) {
        List<Predicate> predicates = new ArrayList<>();

        if (nonNull(genericSpecificationCallerInterpreter)) {
            genericSpecificationCallerInterpreter.spec(root, criteriaQuery, criteriaBuilder, getObject(), predicates);
        }

        List<Predicate> userPredicates = predicate(root, criteriaQuery, criteriaBuilder, getObject());
        if (nonNull(userPredicates))
            predicates.addAll(userPredicates);

        return criteriaBuilder.and(predicates.toArray(new Predicate[predicates.size()]));
    }

    // Rewrite here your predicate.
    protected List<Predicate> predicate(Root<SPO> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, SPO object) {
        return null;
    }
}
