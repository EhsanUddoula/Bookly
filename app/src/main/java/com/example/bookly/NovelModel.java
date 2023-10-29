package com.example.bookly;


public class NovelModel {
    private String book,writer,image,description,genre,author,language;
    private double price;

    private  boolean isExpanded;
    private String bookId;

    public NovelModel() {

    }

    public NovelModel(String book, String writer, String image, double price, String description, String bookId,String genre,String author,String language) {
        this.book = book;
        this.writer = writer;
        this.image=image;
        this.price=price;
        this.description=description;
        this.bookId=bookId;
        this.isExpanded=false;
        this.genre=genre;
        this.author=author;
        this.language=language;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getAuthor() {
        return author;
    }

    public void setAuthor(String author) {
        this.author = author;
    }

    public String getLanguage() {
        return language;
    }

    public void setLanguage(String language) {
        this.language = language;
    }

    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public boolean isExpanded() {
        return isExpanded;
    }

    public void setExpanded(boolean expanded) {
        isExpanded = expanded;
    }

    public String getPrice() {
        return price+" Tk";
    }

    public void setPrice(double price) {
        this.price = price;
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
