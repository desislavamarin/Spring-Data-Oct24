package org.bookshop.service;

import java.io.IOException;

public interface BookService {

    void seedBook() throws IOException;

    boolean areBooksImported();

    void printAllBooksAfter2000();

    void printAllBooksFromGeorge();
}
