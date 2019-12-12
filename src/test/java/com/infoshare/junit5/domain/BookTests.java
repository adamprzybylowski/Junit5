package com.infoshare.junit5.domain;

import org.junit.jupiter.api.*;

import java.io.File;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.contentOf;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.fail;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class BookTests {

    List<Book> products = new ArrayList<>();

    @BeforeEach
    void setup() {
        products.add(new Book("Extreme Programming", "Kent Beck", 10, LocalDate.of(1999, 1, 1)));
        products.add(new Book("Test Driven Development: By Example", "Kent Beck", 15, LocalDate.of(2001, 1, 1)));
        products.add(new Book("Implementation Patterns", "Kent Beck", 12, LocalDate.of(2003, 1, 1)));
        products.add(new Book("Clean Code", "Robert C. Martin", 13, LocalDate.of(2004, 1, 1)));
        products.add(new Book("Clean Architecture", "Robert C. Martin", 20, LocalDate.of(2018, 1, 1)));
        products.add(new Book("Lord of the Rings", "J.R.R Tolkien", 30, LocalDate.of(1951, 1, 1)));
    }

    @Disabled
    @Test
    @Order(1)
    public void junit_failure() {
        assertEquals("XXX", products.get(0).getAuthor());
    }

    @Disabled
    @Test
    @Order(1)
    public void assertj_failure() {
        assertThat(products.get(0))
                .extracting("author")
                .as("check book's author")
                .isEqualTo("XXX");
    }

    @Test
    public void assertj_filtering() {
        assertThat(products)
                .filteredOn(book -> book.publishedOn.getYear() > 2000)
                .as("check books published after 2000")
                .hasSize(4);
    }

    @Test
    public void assertj_extracting() {
        assertThat(products)
                .filteredOn("publishedOn.year", 1999)
                .hasSize(1)
                .extracting("author").containsOnly("Kent Beck");
    }

    @Test
    public void assertj_multiple_filtering() {
        // TODO find all Kent Beck's books published after 2000
        fail();
    }

    @Test
    public void assertj_file_asserts() {
        File f = new File("README.md");
        assertThat(f).exists().isFile();
        assertThat(contentOf(f)).startsWith("###").endsWith("---");
    }

    @Test
    public void assertj_file_content_asserts() {
        File f = new File("README.md");
        // TODO check if file contains word Mockito
        fail();
    }

}
