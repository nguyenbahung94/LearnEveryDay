package com.example.designpattern.builder.ex1;

import java.util.GregorianCalendar;

/**
 * Created by nbhung on 3/16/2018.
 */

public class MainBook {
    Book book = new Book.Builder("android", "billy")
            .publishDate(new GregorianCalendar(789, 78, 7))
            .build();
}
