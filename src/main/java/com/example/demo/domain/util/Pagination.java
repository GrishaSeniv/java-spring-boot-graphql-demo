package com.example.demo.domain.util;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-07
 */
public class Pagination {
    public static final int DEFAULT_LIMIT = 20;
    public static final int DEFAULT_OFFSET = 0;
    private int limit;
    private int offset;

    public Pagination(Integer limit, Integer offset) {
        if (limit == null || limit == 0) {
            this.limit = DEFAULT_LIMIT;
        }
        if (offset == null || offset < 0) {
            this.offset = DEFAULT_OFFSET;
        }
    }

    public int getLimit() {
        return limit;
    }

    public int getOffset() {
        return offset;
    }
}
