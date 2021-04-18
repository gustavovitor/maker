package com.github.gustavovitor.util;

import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;

import static java.util.Objects.nonNull;

public class PageableUtils {
    public static Pageable getCustomPageable(RequestPageableWithSort pageable) {
        if (nonNull(pageable.getSort()))
            return PageRequest.of(pageable.getPage(), pageable.getSize(), pageable.getSortObject());
        else
            return PageRequest.of(pageable.getPage(), pageable.getSize());
    }
}
