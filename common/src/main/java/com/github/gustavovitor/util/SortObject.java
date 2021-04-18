package com.github.gustavovitor.util;

import lombok.Data;
import org.springframework.data.domain.Sort;

@Data
public class SortObject {
    private String[] fields;
    private Sort.Direction direction;
}
