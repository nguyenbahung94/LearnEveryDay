package com.example.designpattern.DIPattern.ex1beforeuseDi;

/**
 * Created by nbhung on 3/16/2018.
 */

public class MyLegacyTest {
    public static void main(String[] args) {
        MyApplication app = new MyApplication();
        app.processMessages("Hi Pankaj", "pankaj@abc.com");
    }
}
