package com.example.lab10;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

@RestController
@RequestMapping("/books")
public class Controller {
    private List<Book> books = new ArrayList<>();
    @GetMapping
    public List<Book> getBooks() {
        return books;
    }
    @PostMapping
    public ResponseEntity<String> addBook(@RequestBody Book book) {
        if (book.getAuthor() == null || Objects.equals(book.getAuthor(), "")) {
            return new ResponseEntity<>("Title is required", HttpStatus.BAD_REQUEST);
        }
        else if (book.getTitle() == null || Objects.equals(book.getTitle(), "")) {
            return new ResponseEntity<>("Author is required", HttpStatus.BAD_REQUEST);
        }
        else if (book.getYear() <= 0) {
            return new ResponseEntity<>("Year has to be set to a positive value", HttpStatus.BAD_REQUEST);
        }
        books.add(book);
        return new ResponseEntity<>(HttpStatus.OK);
    }
}

class Book {
    private String title;
    private String author;
    private int year;
    public void setTitle(String title) {
        this.title = title;
    }
    public void setAuthor(String author) {
        this.author = author;
    }
    public void setYear(int year) {
        this.year = year;
    }
    public String getTitle() {
        return title;
    }
    public String getAuthor() {
        return author;
    }
    public int getYear() {
        return year;
    }
}
