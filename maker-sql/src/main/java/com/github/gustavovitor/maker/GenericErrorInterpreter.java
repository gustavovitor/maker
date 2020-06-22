package com.github.gustavovitor.maker;

import com.github.gustavovitor.maker.repository.RepositoryMaker;
import com.github.gustavovitor.maker.service.ServiceMaker;

import java.util.Map;

public interface GenericErrorInterpreter {
    void onInsertError(ServiceMaker service, RepositoryMaker repository, Throwable e, Object object);
    void onUpdateError(ServiceMaker service, RepositoryMaker repository, Throwable e, Object objectId, Object object);
    void onPatchError(ServiceMaker service, RepositoryMaker repository, Throwable e, Object objectId, Map<String, Object> object);
    void onDeleteError(ServiceMaker service, RepositoryMaker repository, Throwable e, Object object);
}
