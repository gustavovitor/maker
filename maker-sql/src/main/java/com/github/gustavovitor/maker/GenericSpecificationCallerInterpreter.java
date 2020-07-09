package com.github.gustavovitor.maker;

import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;
import java.util.List;

public abstract class GenericSpecificationCallerInterpreter {
    public void spec(Root<?> root, CriteriaQuery<?> criteriaQuery, CriteriaBuilder criteriaBuilder, Object specificationObject, List<Predicate> predicates) {};

    public static GenericSpecificationCallerInterpreter instance;

    public static void setInstance(GenericSpecificationCallerInterpreter instance) {
        GenericSpecificationCallerInterpreter.instance = instance;
    }

    public static GenericSpecificationCallerInterpreter getInstance() {
        return instance;
    }
}
