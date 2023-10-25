package com.example.bookly;

public class CartItem {
    private String bookPic;
    private String bookName;
    private String writer;
    private String price;
    private String amount;

    public CartItem() {
    }

    public CartItem(String bookPic, String bookName, String writer, String price, String amount) {
        this.bookPic = bookPic;
        this.bookName = bookName;
        this.writer = writer;
        this.price = price;
        this.amount = amount;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public String getBookPic() {
        return bookPic;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }

    public String getPrice() {
        return price;
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }
}
