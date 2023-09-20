package com.example.bookly;

public class NovelModel {
    private String book,writer,image;

    public NovelModel() {
    }

    public NovelModel(String book, String writer,String image) {
        this.book = book;
        this.writer = writer;
        this.image=image;
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

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
