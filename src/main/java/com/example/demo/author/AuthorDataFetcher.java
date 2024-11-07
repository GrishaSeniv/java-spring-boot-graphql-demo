package com.example.demo.author;

import com.example.demo.domain.dto.AddAuthorReq;
import com.example.demo.domain.dto.Author;
import com.example.demo.domain.dto.AuthorDTO;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;

import static com.example.demo.author.Converter.toAuthor;
import static com.example.demo.author.Converter.toAuthors;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@DgsComponent
class AuthorDataFetcher {
    private static final Logger logger = LoggerFactory.getLogger(AuthorDataFetcher.class);
    private final AuthorService service;

    AuthorDataFetcher(AuthorService service) {
        this.service = service;
    }

    @DgsQuery
    public List<Author> authors(@InputArgument Integer limit, @InputArgument Integer offset) {
        logger.info("[Graphql] Query: authors triggered");
        List<AuthorDTO> list = service.findAll(limit, offset);
        return toAuthors(list);
    }

    @DgsQuery
    public Author authorById(@InputArgument Long id) {
        logger.info("[Graphql] Query: authorById triggered");
        AuthorDTO dto = service.findById(id);
        return toAuthor(dto);
    }

    @DgsMutation
    public Author addAuthor(@InputArgument AddAuthorReq author) {
        logger.info("[Graphql] Mutation: addAuthor triggered");
        AuthorDTO dto = service.save(author);
        return toAuthor(dto);
    }
}
