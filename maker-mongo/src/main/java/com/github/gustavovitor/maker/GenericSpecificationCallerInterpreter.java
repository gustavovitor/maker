package com.github.gustavovitor.maker;

import com.querydsl.core.types.Predicate;

public abstract class GenericSpecificationCallerInterpreter {
    public void spec(Predicate predicate, Object object) { }

    public static GenericSpecificationCallerInterpreter instance;

    public static void setInstance(GenericSpecificationCallerInterpreter instance) {
        GenericSpecificationCallerInterpreter.instance = instance;
    }

    public static GenericSpecificationCallerInterpreter getInstance() {
        return instance;
    }
}
