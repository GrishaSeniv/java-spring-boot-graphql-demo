package com.example.demo.book;

import com.example.demo.domain.dto.AddBookReq;
import com.example.demo.domain.dto.Author;
import com.example.demo.domain.dto.Book;
import com.example.demo.domain.dto.BookDTOProjection;
import com.example.demo.domain.util.Pagination;
import com.netflix.graphql.dgs.DgsComponent;
import com.netflix.graphql.dgs.DgsData;
import com.netflix.graphql.dgs.DgsDataFetchingEnvironment;
import com.netflix.graphql.dgs.DgsMutation;
import com.netflix.graphql.dgs.DgsQuery;
import com.netflix.graphql.dgs.InputArgument;
import org.dataloader.DataLoader;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.concurrent.CompletableFuture;

import static com.example.demo.book.Converter.toBook;
import static com.example.demo.book.Converter.toBooks;
import static com.example.demo.domain.util.DgsUtil.getRequestedFields;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@DgsComponent
class BookDataFetcher {
    private static final Logger logger = LoggerFactory.getLogger(BookDataFetcher.class);
    private final BookService service;

    BookDataFetcher(BookService service) {
        this.service = service;
    }

    @DgsQuery
    public List<Book> books(@InputArgument Integer limit, @InputArgument Integer offset, DgsDataFetchingEnvironment dfe) {
        logger.info("[Graphql] Query: books triggered");
        Set<String> requestedFields = getRequestedFields(dfe, Book.class);
        List<BookDTOProjection> dtos = service.findAllWithFields(new Pagination(limit, offset), requestedFields);
        return toBooks(dtos);
    }

    // This method lead to an N+1 query problem.
    // Each author will trigger a separate database query to fetch their books,
    // resulting in potentially many queries if multiple authors are requested.
//    @DgsData(parentType = "Author", field = "books")
//    public List<Book> booksByAuthor(DgsDataFetchingEnvironment dfe) {
//        Author author = dfe.getSource();
//        if (author == null) {
//            logger.info("[Graphql] Fetching books for author ID couldn't be triggered, author is null");
//            return Collections.emptyList();
//        }
//        Long authorId = author.id();
//        logger.info("[Graphql] Fetching books for author ID: {}", authorId);
//        List<BookDTO> dtos = service.findAllByAuthorId(authorId);
//        return toBooks(dtos);
//    }

    @DgsData(parentType = "Author", field = "books")
    public CompletableFuture<List<Book>> booksByAuthor(DgsDataFetchingEnvironment dfe) {
        Author author = dfe.getSource();
        if (author == null) {
            logger.info("[Graphql] Fetching books for author ID couldn't be triggered, author is null");
            return CompletableFuture.completedFuture(Collections.emptyList());
        }
        DataLoader<Long, List<Book>> dataLoader = dfe.getDataLoader(BookDataLoader.class);
        Long authorId = author.id();
        Set<String> requestedFields = getRequestedFields(dfe, Book.class);
        logger.info("[Graphql] Fetching books for author ID: {} with requestFields: {}", authorId, requestedFields);
        return dataLoader.load(authorId, requestedFields);
    }

    @DgsQuery
    public Book bookById(@InputArgument Long id, DgsDataFetchingEnvironment dfe) {
        logger.info("[Graphql] Query: bookById triggered");
        Set<String> requestedFields = getRequestedFields(dfe, Book.class);
        BookDTOProjection dto = service.findByIdWithFields(id, requestedFields);
        return toBook(dto);
    }

    @DgsMutation
    public Book addBook(@InputArgument AddBookReq book) {
        logger.info("[Graphql] Mutation: addBook triggered");
        BookDTOProjection dto = service.save(book);
        return toBook(dto);
    }
}
