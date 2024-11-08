package com.example.demo.author;

import com.example.demo.domain.dto.AddAuthorReq;
import com.example.demo.domain.dto.Author;
import com.example.demo.domain.dto.AuthorDTO;
import com.example.demo.domain.dto.AuthorDTOProjection;
import com.example.demo.domain.util.Pagination;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.Set;

import static com.example.demo.author.Converter.toAuthor;
import static com.example.demo.author.Converter.toAuthors;
import static com.example.demo.domain.util.DgsUtil.getRequestedFields;

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
    public List<Author> authors(@InputArgument Integer limit, @InputArgument Integer offset, DgsDataFetchingEnvironment dgs) {
        logger.info("[Graphql] Query: authors triggered");
        Set<String> requestedFields = getRequestedFields(dgs, Author.class);
        List<AuthorDTOProjection> list = service.findAllWithFields(new Pagination(limit, offset), requestedFields);
        return toAuthors(list);
    }

    @DgsQuery
    public Author authorById(@InputArgument Long id, DgsDataFetchingEnvironment dgs) {
        logger.info("[Graphql] Query: authorById triggered");
        Set<String> requestedFields = getRequestedFields(dgs, Author.class);
        AuthorDTOProjection dto = service.findByIdWithFields(id, requestedFields);
        return toAuthor(dto);
    }

    @DgsMutation
    public Author addAuthor(@InputArgument AddAuthorReq author) {
        logger.info("[Graphql] Mutation: addAuthor triggered");
        AuthorDTO dto = service.save(author);
        return toAuthor(dto);
    }
}
