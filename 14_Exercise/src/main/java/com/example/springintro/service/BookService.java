package com.example.springintro.service;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookInfo;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;

public interface BookService {
    void seedBooks() throws IOException;

    List<Book> findAllBooksAfterYear(int year);

    List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year);

    List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName);

    List<String> findTitlesByAgeRestriction(String ageRestriction);

    List<String> findGoldenBookTitles();

    List<String> findByPriceOutsideOf(BigDecimal lowerBound, BigDecimal upperBound);

    List<String> findBooksNotReleasedIn(int year);

    List<Book> findBooksReleasedBefore(String input);

    List<String> findBooksSearch(String input);

    List<Book> findByAuthorLastName(String input);

    int findBookCountByTitleLength(int length);

    BookInfo findBookInfo(String title);

    long updateBookCopiesAfterDate(String date, int count);

    int deleteWithCountLessThen(int minCount);
}
