package com.example.springintro.repository;

import com.example.springintro.model.entity.AgeRestriction;
import com.example.springintro.model.entity.Book;
import com.example.springintro.model.entity.BookInfo;
import com.example.springintro.model.entity.EditionType;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.List;

@Repository
public interface BookRepository extends JpaRepository<Book, Long> {

    List<Book> findByAgeRestriction(AgeRestriction ageRestriction);

    List<Book> findAllByReleaseDateAfter(LocalDate releaseDateAfter);

    List<Book> findAllByReleaseDateBefore(LocalDate releaseDateBefore);

    List<Book> findAllByAuthor_FirstNameAndAuthor_LastNameOrderByReleaseDateDescTitle(String author_firstName, String author_lastName);

    List<Book> findByEditionTypeAndCopiesLessThan(EditionType type, int copies);

    List<Book> findByPriceLessThanOrPriceGreaterThan(BigDecimal lower, BigDecimal upper);

    List<Book> findAllByReleaseDateBeforeOrReleaseDateAfter(LocalDate before, LocalDate after);

    List<Book> findByReleaseDateBefore(LocalDate formatedDate);

    List<Book> findByTitleContainingIgnoreCase(String input);

    List<Book> findByAuthorLastNameStartingWith(String input);

    @Query("SELECT COUNT(b) FROM Book b WHERE length(b.title) > :length")
    int countByTitleSizeGreaterThan(int length);

    BookInfo findByTitle(String title);

    @Query("UPDATE Book AS b SET b.copies = b.copies + :count WHERE b.releaseDate > :parsed")
    @Modifying
    @Transactional
    int updateBookCopiesReleasedAfter(LocalDate parsed, int count);

    @Transactional
    int deleteByCopiesLessThan(int count);
}
