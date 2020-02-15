package br.com.gustavomiranda.maker.util;

import lombok.*;
import org.springframework.data.domain.Sort;

@Getter
@Setter
@ToString
@EqualsAndHashCode
@NoArgsConstructor
@AllArgsConstructor
public class RequestPageable {
    private int page;
    private int size;
    private Sort sort;
}
