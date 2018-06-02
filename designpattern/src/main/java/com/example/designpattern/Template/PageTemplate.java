package com.example.designpattern.Template;

/**
 * Created by nbhung on 3/20/2018.
 */

public abstract class PageTemplate {
    protected void displayHeader() {
        System.out.println("HEADER");
    }

    protected void displayNavigation() {
        System.out.println("NAVIGATION");
    }

    protected void displayFooter() {
        System.out.println("FOOTER");
    }

    /**
     * Let each subclass define it own content
     *
     * @author Edward
     */
    protected abstract void displayBody();

    /**
     * Display all content of web page
     *
     * @author Edward
     */
    public void displayWebPage() {
        displayHeader();
        displayNavigation();
        displayBody();
        displayFooter();
    }
}
