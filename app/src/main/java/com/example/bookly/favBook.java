package com.example.bookly;

public class favBook {

    private String bookName,writer,bookPic,bookId,genre;

    public favBook() {

    }

    public favBook(String bookName, String writer, String bookPic, String bookId,String genre) {
        this.bookName = bookName;
        this.writer = writer;
        this.bookPic = bookPic;
        this.bookId = bookId;
        this.genre=genre;
    }

    public String getGenre() {
        return genre;
    }

    public void setGenre(String genre) {
        this.genre = genre;
    }

    public String getBookName() {
        return bookName;
    }

    public void setBookName(String bookName) {
        this.bookName = bookName;
    }

    public String getBookPic() {
        return bookPic;
    }

    public void setBookPic(String bookPic) {
        this.bookPic = bookPic;
    }

    public String getWriter() {
        return writer;
    }

    public void setWriter(String writer) {
        this.writer = writer;
    }



    public String getBookId() {
        return bookId;
    }

    public void setBookId(String bookId) {
        this.bookId = bookId;
    }
}
