package com.fastcampus.jpa.bookmanager.repository;

import com.fastcampus.jpa.bookmanager.domain.Book;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
public class BookRepositoryTest {
    @Autowired
    BookRepository bookRepository;

    @Test
    void bookTest(){
        Book book = new Book();
        book.setName("Jpa test");
        book.setAuthor("jyuka");

        bookRepository.save(book);

        System.out.println(bookRepository.findAll());
    }
}
