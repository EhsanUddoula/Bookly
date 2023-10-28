package com.example.bookly;

public class popBook {
    private String book,bookId,writer,genre,price,image;

    public popBook() {
    }

    public popBook(String book, String bookId, String writer, String genre, String price, String image) {
        this.book = book;
        this.bookId = bookId;
        this.writer = writer;
        this.genre = genre;
        this.price = price;
        this.image = image;
    }

    public String getBook() {
        return book;
    }

    public void setBook(String book) {
        this.book = book;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }
}
