package com.github.gustavovitor.maker;

import com.github.gustavovitor.maker.repository.MongoRepositoryMaker;
import com.github.gustavovitor.maker.service.MongoServiceMaker;

import java.util.Map;

public interface GenericCallerInterpreter {
    void onInsert(MongoServiceMaker service, MongoRepositoryMaker repository, Object object);
    void onUpdate(MongoServiceMaker service, MongoRepositoryMaker repository, Object savedObject, Object object);
    void onPatch(MongoServiceMaker service, MongoRepositoryMaker repository, Object savedObject, Map<String, Object> object);
    void onDelete(MongoServiceMaker service, MongoRepositoryMaker repository, Object object);
}
