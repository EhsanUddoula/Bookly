package com.example.bookly;

public class NovelModel {
    private String book,writer,image,description;
    private double price;

    private  boolean isExpanded;

    public NovelModel() {
    }

    public NovelModel(String book, String writer,String image,double price,String description) {
        this.book = book;
        this.writer = writer;
        this.image=image;
        this.price=price;
        this.description=description;
        this.isExpanded=false;
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
        return price+" TK";
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
