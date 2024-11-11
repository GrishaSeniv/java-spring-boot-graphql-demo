package com.example.demo.domain.util;

import static com.example.demo.domain.Constants.ASSOCIATION_FIELD_REGEX;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-11
 */
public class StringUtil {
    public static String transformToCamelCase(String fieldPath) {
        // Split the field path by dots to get individual field names
        String[] parts = fieldPath.split(ASSOCIATION_FIELD_REGEX);
        StringBuilder builder = new StringBuilder();

        // Process each part of the field path
        for (int i = 0; i < parts.length; i++) {
            String word = parts[i];
            if (i == 0) {
                word = word.isEmpty() ? word : word.toLowerCase();
                builder.append(word);
                continue;
            }
            word = word.isEmpty() ? word : Character.toUpperCase(word.charAt(0)) + word.substring(1).toLowerCase();
            builder.append(word);
        }
        return builder.toString();
    }
}
