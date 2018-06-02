package com.tma.stockmarket.ui.navigation.navigationDrawer;


public class NavDrawerItem {
    private String title;
    private int item;


    NavDrawerItem() {

    }

    public NavDrawerItem(String title) {
        this.title = title;
    }

    public int getItem() {
        return item;
    }

    public void setItem(int item) {
        this.item = item;
    }

    String getTitle() {
        return title;
    }

    void setTitle(String title) {
        this.title = title;
    }
}
