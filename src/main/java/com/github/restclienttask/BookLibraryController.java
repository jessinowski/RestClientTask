package com.github.restclienttask;

import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/library")
@RequiredArgsConstructor
public class BookLibraryController {

    private final BookLibraryService service;

    @GetMapping("/books/{id}")
    public Book getBookById(@PathVariable String id){
        return service.getBookById(id);
    }
}
