package com.example.bookly;

public class NovelModel {
    private String book,writer;

    public NovelModel() {
    }

    public NovelModel(String book, String writer) {
        this.book = book;
        this.writer = writer;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }
}
