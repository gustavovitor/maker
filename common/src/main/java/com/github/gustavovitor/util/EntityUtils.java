package com.github.gustavovitor.util;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.util.ReflectionUtils;

import java.lang.reflect.Field;
import java.util.Map;

public class EntityUtils {
    public static void merge(Map<String, Object> origin, Object destinityObject, Class<?> clazz) {
        ObjectMapper objectMapper = new ObjectMapper();
        Object originObject = objectMapper.convertValue(origin, clazz);
        origin.forEach((property, value) -> {
            Field field = ReflectionUtils.findField(clazz, property);
            field.setAccessible(true);
            Object novoValor = ReflectionUtils.getField(field, originObject);
            ReflectionUtils.setField(field, destinityObject, novoValor);
        });
    }

}
