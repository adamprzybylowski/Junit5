package com.infoshare.junit5.domain;

import java.time.LocalDate;

public class Book implements Item {

    final private Integer value;
    final private String name;
    final private String author;
    final LocalDate publishedOn;

    public Book(String name, String author, Integer value, LocalDate publishedOn) {
        this.value = value;
        this.name = name;
        this.author = author;
        this.publishedOn = publishedOn;
    }

    public LocalDate publishedOn() {
        return publishedOn;
    }

    public String getAuthor() {
        return author;
    }

    @Override
    public String name() {
        return name;
    }

    @Override
    public Integer getValue() {
        return value;
    }
}
