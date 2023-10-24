package com.example.bookly;

public class CartItem {
    private static String bookPic;
    private String bookName;
    private String writer;
    private String price;
    private int amount;

    public CartItem() {
    }

    public CartItem(String bookPic, String bookName, String writer, String price, int amount) {
        this.bookPic = bookPic;
        this.bookName = bookName;
        this.writer = writer;
        this.price = price;
        this.amount = amount;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public static String getBookPic() {
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
        return price + " Tk";
    }

    public void setPrice(String price) {
        this.price = price;
    }

    public int getAmount() {
        return amount;
    }

    public void setAmount(int amount) {
        this.amount = amount;
    }
}
