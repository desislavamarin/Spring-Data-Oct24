package com.example.springintro.service.impl;

import com.example.springintro.model.entity.*;
import com.example.springintro.repository.BookRepository;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.math.BigDecimal;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@Service
public class BookServiceImpl implements BookService {

    private static final String BOOKS_FILE_PATH = "src/main/resources/files/books.txt";

    private final BookRepository bookRepository;
    private final AuthorService authorService;
    private final CategoryService categoryService;

    public BookServiceImpl(BookRepository bookRepository, AuthorService authorService, CategoryService categoryService) {
        this.bookRepository = bookRepository;
        this.authorService = authorService;
        this.categoryService = categoryService;
    }

    @Override
    public void seedBooks() throws IOException {
        if (bookRepository.count() > 0) {
            return;
        }

        Files
                .readAllLines(Path.of(BOOKS_FILE_PATH))
                .forEach(row -> {
                    String[] bookInfo = row.split("\\s+");

                    Book book = createBookFromInfo(bookInfo);

                    bookRepository.save(book);
                });
    }

    @Override
    public List<Book> findAllBooksAfterYear(int year) {
        return bookRepository
                .findAllByReleaseDateAfter(LocalDate.of(year, 12, 31));
    }

    @Override
    public List<String> findAllAuthorsWithBooksWithReleaseDateBeforeYear(int year) {
        return bookRepository
                .findAllByReleaseDateBefore(LocalDate.of(year, 1, 1))
                .stream()
                .map(book -> String.format("%s %s", book.getAuthor().getFirstName(),
                        book.getAuthor().getLastName()))
                .distinct()
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(String firstName, String lastName) {
       return bookRepository
                .findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(firstName, lastName)
                .stream()
                .map(book -> String.format("%s %s %d",
                        book.getTitle(),
                        book.getReleaseDate(),
                        book.getCopies()))
                .collect(Collectors.toList());
    }

    @Override
    public List<String> findTitlesByAgeRestriction(String input) {
        AgeRestriction ageRestriction = AgeRestriction.valueOf(input.toUpperCase());

        List<Book> byAgeRestriction = bookRepository.findByAgeRestriction(ageRestriction);

        return mapBooksToTitles(byAgeRestriction);
    }

    @Override
    public List<String> findGoldenBookTitles() {
        List<Book> books = bookRepository
                .findByEditionTypeAndCopiesLessThan(EditionType.GOLD, 5000);

        return mapBooksToTitles(books);
    }

    private static List<String> mapBooksToTitles(List<Book> books) {
        return books.stream()
                .map(Book::getTitle)
                .toList();
    }

    @Override
    public List<String> findByPriceOutsideOf(BigDecimal lowerBound, BigDecimal upperBound) {
        List<Book> books = bookRepository
                .findByPriceLessThanOrPriceGreaterThan(lowerBound, upperBound);

        return mapBooksToTitles(books);
    }

    @Override
    public List<String> findBooksNotReleasedIn(int year) {
        LocalDate yearStart = LocalDate.of(year, 01, 01);
        LocalDate yearEnd = LocalDate.of(year, 12, 31);

        List<Book> result = bookRepository
                .findAllByReleaseDateBeforeOrReleaseDateAfter(yearStart, yearEnd);

        return mapBooksToTitles(result);
    }

    @Override
    public List<Book> findBooksReleasedBefore(String input) {
        LocalDate formatedDate = LocalDate.parse(input, DateTimeFormatter.ofPattern("dd-MM-yyyy"));

        List<Book> books = bookRepository.findByReleaseDateBefore(formatedDate);
        return books;
    }

    @Override
    public List<String> findBooksSearch(String input) {
        List<Book> result = bookRepository.findByTitleContainingIgnoreCase(input);

        return mapBooksToTitles(result);
    }

    @Override
    public List<Book> findByAuthorLastName(String input) {
        List<Book> result = bookRepository.findByAuthorLastNameStartingWith(input);

        return result;
    }

    @Override
    public int findBookCountByTitleLength(int length) {
        return bookRepository.countByTitleSizeGreaterThan(length);
    }

    @Override
    public BookInfo findBookInfo(String title) {
        return bookRepository.findByTitle(title);
    }

    @Override
    public long updateBookCopiesAfterDate(String date, int count) {
        LocalDate parsed = LocalDate.parse(date, DateTimeFormatter.ofPattern("dd MMM yyyy"));

        int updatedBookCount = bookRepository.updateBookCopiesReleasedAfter(parsed, count);
        return updatedBookCount * count;
    }

    @Override
    public int deleteWithCountLessThen(int minCount) {
        return bookRepository.deleteByCopiesLessThan(minCount);
    }

    private Book createBookFromInfo(String[] bookInfo) {
        EditionType editionType = EditionType.values()[Integer.parseInt(bookInfo[0])];
        LocalDate releaseDate = LocalDate
                .parse(bookInfo[1], DateTimeFormatter.ofPattern("d/M/yyyy"));
        Integer copies = Integer.parseInt(bookInfo[2]);
        BigDecimal price = new BigDecimal(bookInfo[3]);
        AgeRestriction ageRestriction = AgeRestriction
                .values()[Integer.parseInt(bookInfo[4])];
        String title = Arrays.stream(bookInfo)
                .skip(5)
                .collect(Collectors.joining(" "));

        Author author = authorService.getRandomAuthor();
        Set<Category> categories = categoryService
                .getRandomCategories();

        return new Book(editionType, releaseDate, copies, price, ageRestriction, title, author, categories);

    }
}
