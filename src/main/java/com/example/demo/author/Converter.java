package com.example.demo.author;

import com.example.demo.domain.dto.AddAuthorReq;
import com.example.demo.domain.dto.Author;
import com.example.demo.domain.dto.AuthorDTO;
import com.example.demo.domain.dto.AuthorDTOProjection;
import com.example.demo.domain.entity.AuthorEntity;

import java.util.Collections;
import java.util.List;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
class Converter {
    static AuthorEntity toAuthorEntity(AddAuthorReq req) {
        return new AuthorEntity()
                .setName(req.name())
                .setBio(req.bio());
    }

    static AuthorDTO toAuthorDTO(AuthorEntity entity) {
        return new AuthorDTO(entity.getId(), entity.getName(), entity.getBio());
    }

    static List<Author> toAuthors(List<AuthorDTOProjection> dtos) {
        if (dtos == null) {
            return Collections.emptyList();
        }
        return dtos.stream()
                .map(Converter::toAuthor)
                .toList();
    }

    static Author toAuthor(AuthorDTO dto) {
        return new Author(dto.id(), dto.name(), dto.bio());
    }

    static Author toAuthor(AuthorDTOProjection dto) {
        return new Author(dto.getId(), dto.getName(), dto.getBio());
    }
}
