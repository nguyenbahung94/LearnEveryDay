package com.example.designpattern.builder.ex1;

import android.os.Build;

import java.util.GregorianCalendar;

/**
 * Created by nbhung on 3/16/2018.
 */

public class Book {
    public enum Genre {FICTION, TECHNOLOGY, SELFHELP, BUSINESS, SPORT}

    private String title;
    private String auther;
    private Genre genre;
    private GregorianCalendar publishDate;
    private String ISBNl;

    public static class Builder {
        //required params
        private String title;
        private String author;

        //optional params
        private Genre genre = Genre.FICTION;
        private GregorianCalendar publishDate = new GregorianCalendar(2018, 3, 16);
        private String ISBN = "000000000000";

        public Builder(String title, String author) {
            this.title = title;
            this.author = author;
        }

        public Builder genre(Genre val) {
            this.genre = val;
            return this;
        }

        public Builder publishDate(GregorianCalendar val) {
            this.publishDate = val;
            return this;
        }

        public Builder ISBN(String val) {
            this.ISBN = val;
            return this;
        }

        public Book build() {
            return new Book(this);
        }
    }

    private Book(Builder builder) {

        title = builder.title;
        auther = builder.author;
        genre = builder.genre;
        publishDate = builder.publishDate;
        ISBNl = builder.ISBN;

    }

    @Override
    public String toString() {
        return "Book{" +
                "title='" + title + '\'' +
                ", auther='" + auther + '\'' +
                ", genre=" + genre +
                ", publishDate=" + publishDate +
                ", ISBNl='" + ISBNl + '\'' +
                '}';
    }
}
