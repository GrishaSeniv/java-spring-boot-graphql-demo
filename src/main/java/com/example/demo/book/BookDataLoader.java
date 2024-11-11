package com.example.demo.book;

import com.example.demo.domain.dto.Book;
import com.example.demo.domain.dto.BookDTOProjection;
import com.netflix.graphql.dgs.DgsDataLoader;
import org.dataloader.BatchLoaderEnvironment;
import org.dataloader.MappedBatchLoaderWithContext;

import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.CompletableFuture;
import java.util.concurrent.CompletionStage;
import java.util.stream.Collectors;

import static com.example.demo.book.Converter.toBooks;
import static com.example.demo.domain.Constants.BOOK_AUTHOR_ID_FIELD_NAME;

/**
 * @author Hryhorii Seniv
 * @version 2024-10-31
 */
@DgsDataLoader(name = "book")
class BookDataLoader implements MappedBatchLoaderWithContext<Long, List<Book>> {
    private final BookService service;

    BookDataLoader(BookService service) {
        this.service = service;
    }

    @Override
    public CompletionStage<Map<Long, List<Book>>> load(Set<Long> set, BatchLoaderEnvironment ble) {
        Set<String> requestedFields = (Set<String>) ble.getKeyContextsList().getFirst();
        // we need to fetch author_id field because it will be used later in loader and for grouping
        requestedFields.add(BOOK_AUTHOR_ID_FIELD_NAME);
        List<BookDTOProjection> dtos = service.findAllByAuthorIdsWithFields(set, requestedFields);
        Map<Long, List<BookDTOProjection>> idBookDTOListMap = dtos.stream()
                .collect(Collectors.groupingBy(BookDTOProjection::getAuthorId));
        Map<Long, List<Book>> idBookListMap = idBookDTOListMap.entrySet()
                .stream()
                .collect(Collectors.toMap(Map.Entry::getKey, entry -> toBooks(entry.getValue())));
        return CompletableFuture.supplyAsync(() -> idBookListMap);
    }
}
