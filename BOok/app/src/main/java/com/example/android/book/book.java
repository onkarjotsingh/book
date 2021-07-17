package com.example.android.book;

public class book {
    private String title;
    private String author;
    private String img;
    private String loc;

    public book(String Title,String Author,String Img,String Loc){
        title=Title;
        author=Author;
        img=Img;
        loc=Loc;
    }

    public String getAuthor() {
        return author;
    }

    public String getImg() {
        return img;
    }

    public String getLoc() {
        return loc;
    }

    public String getTitle() {
        return title;
    }
}
