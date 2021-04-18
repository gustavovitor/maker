package com.github.gustavovitor.util;

import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Deprecated
public class ObjectPageableRequest<SPO> {
    SPO object;
    RequestPageable pageable;
}
