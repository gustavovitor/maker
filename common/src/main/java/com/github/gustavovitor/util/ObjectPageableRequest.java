package com.github.gustavovitor.util;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.*;

@Getter
@Setter
@ToString
@NoArgsConstructor
@AllArgsConstructor
public class ObjectPageableRequest<SPO> {

    @JsonProperty("object")
    SPO object;

    @JsonProperty("pageable")
    RequestPageable pageable;

}
