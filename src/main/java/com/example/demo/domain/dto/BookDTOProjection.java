package com.example.demo.domain.dto;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-07
 */
public class BookDTOProjection {
    private Long id;
    private String title;
    private String genre;

    public BookDTOProjection() {
    }

    public BookDTOProjection(Long id, String title, String genre) {
        this.id = id;
        this.title = title;
        this.genre = genre;
    }

    public Long getId() {
        return id;
    }

    public BookDTOProjection setId(Long id) {
        this.id = id;
        return this;
    }

    public String getTitle() {
        return title;
    }

    public BookDTOProjection setTitle(String title) {
        this.title = title;
        return this;
    }

    public String getGenre() {
        return genre;
    }

    public BookDTOProjection setGenre(String genre) {
        this.genre = genre;
        return this;
    }

    @Override
    public String toString() {
        return "BookDTOProjection{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", genre='" + genre + '\'' +
                '}';
    }
}
