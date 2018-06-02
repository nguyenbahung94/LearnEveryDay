package com.example.designpattern.Template;

/**
 * Created by nbhung on 3/20/2018.
 */

public class ContactPage extends PageTemplate {
    @Override
    protected void displayBody() {
        System.out.println("ABOUT CONTENT");
    }

    @Override
    protected void displayNavigation() {
        // We do NOT want to display Navigation here
        // Just do nothing
    }
}
