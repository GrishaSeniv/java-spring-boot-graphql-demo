package com.example.demo.domain.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-07
 */
public class Mapper {
    private static final Logger logger = LoggerFactory.getLogger(Mapper.class);

    public static <T> List<T> mapToDTOWithReflectionList(List<Object[]> resultList, Set<String> fields, Class<T> dtoClass) {
        return resultList.stream()
                .map(rl -> mapToDTOWithReflection(rl, fields, dtoClass))
                .toList();
    }

    public static <T> T mapToDTOWithReflection(Object[] result, Set<String> fields, Class<T> dtoClass) {
        try {
            T dtoProjection = dtoClass.getDeclaredConstructor().newInstance();
            int index = 0;
            for (String field : fields) {
                Field dtoField = dtoClass.getDeclaredField(field);
                dtoField.setAccessible(true);
                dtoField.set(dtoProjection, result[index++]);
            }
            return dtoProjection;
        } catch (InstantiationException | IllegalAccessException | NoSuchFieldException | InvocationTargetException |
                 NoSuchMethodException e) {
            String msg = String.format("Failed to map fields: %s to: %s using reflection", fields, dtoClass.getName());
            logger.error(msg, e);
            throw new RuntimeException(msg, e);
        }
    }
}
