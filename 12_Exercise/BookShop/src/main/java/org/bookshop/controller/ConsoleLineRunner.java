package org.bookshop.controller;

import org.bookshop.service.AuthorService;
import org.bookshop.service.BookService;
import org.bookshop.service.CategoryService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;

@Component
public class ConsoleLineRunner implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    @Autowired
    public ConsoleLineRunner(CategoryService categoryService, AuthorService authorService, BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        //Logic UI
        seedData();
        //this.bookService.printAllBooksAfter2000();
        //this.authorService.printAllAuthorsWithOneBookBefore1990();
        //this.authorService.printAllAuthorsWithDescCount();
        this.bookService.printAllBooksFromGeorge();
    }

    private void seedData() throws IOException {
        if (!this.categoryService.isImported()) {
            this.categoryService.seedCategories();
        }
        if(!this.authorService.areAuthorsImported()) {
            this.authorService.seedAuthors();
        }
        if (!this.bookService.areBooksImported()) {
            this.bookService.seedBook();
        }
    }
}
