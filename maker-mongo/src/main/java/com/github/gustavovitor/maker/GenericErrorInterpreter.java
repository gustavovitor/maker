package com.github.gustavovitor.maker;

import com.github.gustavovitor.maker.repository.MongoRepositoryMaker;
import com.github.gustavovitor.maker.service.MongoServiceMaker;

import java.util.Map;

public interface GenericErrorInterpreter {
    void onInsertError(MongoServiceMaker service, MongoRepositoryMaker repository, Throwable e, Object object);
    void onUpdateError(MongoServiceMaker service, MongoRepositoryMaker repository, Throwable e, Object objectId, Object object);
    void onPatchError(MongoServiceMaker service, MongoRepositoryMaker repository, Throwable e, Object objectId, Map<String, Object> object);
    void onDeleteError(MongoServiceMaker service, MongoRepositoryMaker repository, Throwable e, Object object);
}
