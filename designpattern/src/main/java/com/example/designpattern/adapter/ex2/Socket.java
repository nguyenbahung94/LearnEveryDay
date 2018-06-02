package com.example.designpattern.adapter.ex2;

/**
 * Created by nbhung on 3/19/2018.
 */

public class Socket {
    public Volt getVolt() {
        return new Volt(120);
    }
}
