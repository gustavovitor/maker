package br.com.gustavomiranda.maker.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ObjectPageableRequest<T> {

    @JsonProperty("object")
    T object;

    @JsonProperty("pageable")
    RequestPageable pageable;

}
