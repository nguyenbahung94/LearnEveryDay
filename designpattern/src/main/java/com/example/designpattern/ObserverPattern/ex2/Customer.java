package com.example.designpattern.ObserverPattern.ex2;

/**
 * Created by Billy on 3/20/2018.
 */
//1 class Customer implements Observer ( cái này xem như là viettel mặc định cho sim của bạn đi- và bạn bị ép phải thực thi nó )
public class Customer implements Observer {
    private String name;

    public Customer(String name) {
        this.name = name;
    }

    @Override
    public void update(String sms) {
        System.out.println("xin chao " + name + sms + "viettel khuyen mai 100%");
    }
}
