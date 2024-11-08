package com.example.demo.domain.dto;

import java.util.Objects;

/**
 * @author Hryhorii Seniv
 * @version 2024-11-07
 */
public class AuthorDTOProjection {
    private Long id;
    private String name;
    private String bio;

    public AuthorDTOProjection() {
    }

    public AuthorDTOProjection(Long id, String name, String bio) {
        this.id = id;
        this.name = name;
        this.bio = bio;
    }

    public Long getId() {
        return id;
    }

    public AuthorDTOProjection setId(Long id) {
        this.id = id;
        return this;
    }

    public String getName() {
        return name;
    }

    public AuthorDTOProjection setName(String name) {
        this.name = name;
        return this;
    }

    public String getBio() {
        return bio;
    }

    public AuthorDTOProjection setBio(String bio) {
        this.bio = bio;
        return this;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        AuthorDTOProjection that = (AuthorDTOProjection) o;
        return Objects.equals(id, that.id)
                && Objects.equals(name, that.name)
                && Objects.equals(bio, that.bio);
    }

    @Override
    public int hashCode() {
        return Objects.hash(id, name, bio);
    }

    @Override
    public String toString() {
        return "AuthorDTOProjection{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", bio='" + bio + '\'' +
                '}';
    }
}
