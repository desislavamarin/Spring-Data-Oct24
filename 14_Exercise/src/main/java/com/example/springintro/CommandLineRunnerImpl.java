package com.example.springintro;

import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookInfo;
import com.example.springintro.service.AuthorService;
import com.example.springintro.service.BookService;
import com.example.springintro.service.CategoryService;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.math.BigDecimal;
import java.util.List;
import java.util.Scanner;

@Component
public class CommandLineRunnerImpl implements CommandLineRunner {

    private final CategoryService categoryService;
    private final AuthorService authorService;
    private final BookService bookService;

    public CommandLineRunnerImpl(
            CategoryService categoryService,
            AuthorService authorService,
            BookService bookService) {
        this.categoryService = categoryService;
        this.authorService = authorService;
        this.bookService = bookService;
    }

    @Override
    public void run(String... args) throws Exception {
        seedData();

        Scanner scanner = new Scanner(System.in);

        //problem_01(scanner);
        //problem_02();
        //problem_03();
        //problem_04(scanner);
        //problem_05(scanner);
        //problem_06(scanner);
        //problem07(scanner);
        //problem08(scanner);
        //problem09(scanner);
        //problem10();
        //problem11(scanner);
        //problem12(scanner);
        //problem13(scanner);


        //printAllBooksAfterYear(2000);
        //printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(1990);
        //printAllAuthorsAndNumberOfTheirBooks();
        //printALlBooksByAuthorNameOrderByReleaseDate("George", "Powell");

    }

    private void problem13(Scanner scanner) {
        int minCount = Integer.parseInt(scanner.nextLine());
        int deletedBooksCount = bookService.deleteWithCountLessThen(minCount);
        System.out.println(deletedBooksCount);
    }

    private void problem12(Scanner scanner) {
        String date = scanner.nextLine();
        int count = Integer.parseInt(scanner.nextLine());
        long total = bookService.updateBookCopiesAfterDate(date, count);
        System.out.println(total);

    }

    private void problem11(Scanner scanner) {
        String title = scanner.nextLine();
        BookInfo bookInfo = bookService.findBookInfo(title);
        System.out.println(bookInfo);
    }

    private void problem10() {
        List<String> result = authorService.getByBooksCount();
        System.out.println(result);
    }

    private void problem09(Scanner scanner) {
        String input = scanner.nextLine();
        int length = Integer.parseInt(input);
        int result = bookService.findBookCountByTitleLength(length);
        System.out.println(result);
    }

    private void problem08(Scanner scanner) {
        String input = scanner.nextLine();
        List<Book> result = bookService.findByAuthorLastName(input);

        result.forEach(b -> System.out.printf("%s (%s %s)%n",
                b.getTitle(), b.getAuthor().getFirstName(), b.getAuthor().getLastName()));
    }

    private void problem07(Scanner scanner) {
        String input = scanner.nextLine();
        List<String> result = bookService.findBooksSearch(input);
        System.out.println(result);
    }

    private void problem_06(Scanner scanner) {
        String input = scanner.nextLine();
        List<String> result = authorService.findAuthorsByString(input);
        System.out.println(result);
    }

    private void problem_05(Scanner scanner) {
        String input = scanner.nextLine();
        List<Book> result = bookService.findBooksReleasedBefore(input);

        for (Book book : result) {
            System.out.println(book.shortInfo());
        }
        //result.forEach(b -> System.out.println(b.shortInfo()));
    }

    private void problem_04(Scanner scanner) {
        String input = scanner.nextLine();
        int year = Integer.parseInt(input);

        List<String> result = bookService.findBooksNotReleasedIn(year);
        System.out.println(result);
    }

    private void problem_03() {
        List<String> result = bookService
                .findByPriceOutsideOf(BigDecimal.valueOf(5), BigDecimal.valueOf(40));
        System.out.println(result);
    }

    private void problem_02() {
        List<String> goldenBookTitles = bookService.findGoldenBookTitles();
        System.out.println(goldenBookTitles);
    }

    private void problem_01(Scanner scanner) {
        String input = scanner.nextLine();
        List<String> titles = bookService.findTitlesByAgeRestriction(input);
        System.out.println(titles);
    }

    private void printALlBooksByAuthorNameOrderByReleaseDate(String firstName, String lastName) {
        bookService
                .findAllBooksByAuthorFirstAndLastNameOrderByReleaseDate(firstName, lastName)
                .forEach(System.out::println);
    }

    private void printAllAuthorsAndNumberOfTheirBooks() {
        authorService
                .getAllAuthorsOrderByCountOfTheirBooks()
                .forEach(System.out::println);
    }

    private void printAllAuthorsNamesWithBooksWithReleaseDateBeforeYear(int year) {
        bookService
                .findAllAuthorsWithBooksWithReleaseDateBeforeYear(year)
                .forEach(System.out::println);
    }

    private void printAllBooksAfterYear(int year) {
        bookService
                .findAllBooksAfterYear(year)
                .stream()
                .map(Book::getTitle)
                .forEach(System.out::println);
    }

    private void seedData() throws IOException {
        categoryService.seedCategories();
        authorService.seedAuthors();
        bookService.seedBooks();
    }
}
