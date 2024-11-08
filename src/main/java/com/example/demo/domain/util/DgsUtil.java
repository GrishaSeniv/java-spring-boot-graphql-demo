package com.example.demo.domain.util;

import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import graphql.schema.SelectedField;

import java.lang.reflect.Field;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

import static com.example.demo.domain.Constants.AUTHOR_ID_FIELD_NAME;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-08
 */
public class DgsUtil {
    private static final String DGS_FQN_FIELD_DELIMITER = ".";

    public static Set<String> getRequestedFields(DgsDataFetchingEnvironment dgs, Class<?> schemeDtoClazz) {
        String dtoClazzName = schemeDtoClazz.getSimpleName();
        Set<String> fqnFieldsSet = buildFQNSet(schemeDtoClazz, dtoClazzName);
        List<SelectedField> allRequestedFields = dgs.getSelectionSet().getFields();
        Set<String> filteredRequestedFields = allRequestedFields.stream()
                .filter(selectedField -> fqnFieldsSet.contains(selectedField.getFullyQualifiedName()))
                .map(SelectedField::getName)
                .collect(Collectors.toSet());
        if (shouldAddIdField(allRequestedFields, filteredRequestedFields)) {
            filteredRequestedFields.add(AUTHOR_ID_FIELD_NAME);
        }
        return filteredRequestedFields;
    }

    private static Set<String> buildFQNSet(Class<?> schemeDtoClazz, String dtoClazzName) {
        Field[] fields = schemeDtoClazz.getDeclaredFields();
        return Arrays.stream(fields)
                .map(field -> buildFQN(dtoClazzName, field.getName()))
                .collect(Collectors.toSet());
    }

    private static String buildFQN(String dtoClazzName, String fieldName) {
        return dtoClazzName + DGS_FQN_FIELD_DELIMITER + fieldName;
    }

    private static boolean shouldAddIdField(List<SelectedField> allRequestedFields, Set<String> filteredRequestedFields) {
        return allRequestedFields.size() != filteredRequestedFields.size();
    }
}
