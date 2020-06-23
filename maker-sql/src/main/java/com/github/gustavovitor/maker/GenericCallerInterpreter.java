package com.github.gustavovitor.maker;

import com.github.gustavovitor.maker.repository.RepositoryMaker;
import com.github.gustavovitor.maker.service.ServiceMaker;

import java.util.Map;

public interface GenericCallerInterpreter {
    void onInsert(ServiceMaker service, RepositoryMaker repository, Object object);
    void onUpdate(ServiceMaker service, RepositoryMaker repository, Object savedObject, Object object);
    void onPatch(ServiceMaker service, RepositoryMaker repository, Object savedObject, Map<String, Object> object);
    void onDelete(ServiceMaker service, RepositoryMaker repository, Object object);
}
