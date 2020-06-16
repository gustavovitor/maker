package com.github.gustavovitor.util;

import lombok.*;
import org.springframework.data.domain.Sort;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RequestPageable {
    private int page;
    private int size;
    private Sort sort;
}
