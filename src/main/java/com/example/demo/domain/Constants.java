package com.example.demo.domain;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
public class Constants {
    public static final String ASSOCIATION_FIELD_DELIMITER = ".";
    public static final String ASSOCIATION_FIELD_REGEX = "\\.";

    // Default ID field name used for queries.
    // Change this value or dynamically set it if your entities
    // use a different field name for the primary key.
    public static final String AUTHOR_ID_FIELD_NAME = "id";

    // Default ID field name used for queries.
    // Change this value or dynamically set it if your entities
    // use a different field name for the primary key.
    public static final String BOOK_ID_FIELD_NAME = "id";
    public static final String BOOK_AUTHOR_ID_FIELD_NAME = "author." + AUTHOR_ID_FIELD_NAME;
}
