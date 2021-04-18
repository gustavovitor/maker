package com.github.gustavovitor.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@NoArgsConstructor
public class ObjectPageableRequestWithSort<SPO> {
    private SPO object;
    private RequestPageableWithSort pageable;

    public ObjectPageableRequestWithSort(SPO object, RequestPageableWithSort pageable) {
        pageable.prepareSort();
        this.object = object;
        this.pageable = pageable;
    }
}
