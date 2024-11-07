package com.example.demo.domain.dto;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
public record AddBookReq(String title, String genre, Long authorId) {
}
