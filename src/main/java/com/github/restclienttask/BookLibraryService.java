package com.github.restclienttask;

import org.springframework.stereotype.Service;
import org.springframework.web.client.RestClient;

@Service
public class BookLibraryService {
    private RestClient restClient = RestClient.builder()
            .baseUrl("https://my-json-server.typicode.com/Flooooooooooorian/BookApi")
            .build();

    public Book getBookById(String id) {
        Book response = restClient.get()
                .uri("/books?id="+id)
                .retrieve()
                .body(Book.class);
        return response;
    }
}
