package com.github.gustavovitor.util;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;

import static java.util.Objects.nonNull;

@Getter
@Setter
@NoArgsConstructor
public class RequestPageableWithSort {
    private int page;
    private int size;
    private SortObject sort;
    private Sort sortObject;

    public RequestPageableWithSort(int page, int size, SortObject sort) {
        this.setPage(page);
        this.setSize(size);
        this.setSort(sort);
        this.setSortObject(Sort.by(sort.getDirection(), sort.getFields()));
    }

    public void setSort(SortObject sort) {
        this.sort = sort;
        this.prepareSort();
    }

    public void prepareSort() {
        if (nonNull(this.sort)) {
            this.setSortObject(Sort.by(this.sort.getDirection(), this.sort.getFields()));
        }
    }

    public static Pageable getCustomPageable(RequestPageableWithSort pageable) {
        if (nonNull(pageable.getSort()))
            return PageRequest.of(pageable.getPage(), pageable.getSize(), pageable.getSortObject());
        else
            return PageRequest.of(pageable.getPage(), pageable.getSize());
    }
}
