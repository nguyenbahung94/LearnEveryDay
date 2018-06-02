package com.example.designpattern.Facade.ex2;

/**
 * Created by nbhung on 3/19/2018.
 */

public class Iphone implements MobileStore {
    @Override
    public void showMobile() {
        System.out.println("Đây là điện thoại Iphone 10");
    }

    @Override
    public void orderMobile() {
        System.out.println("Bạn đã đặt mua chiếc Iphone 10");
    }

    @Override
    public void pay() {
        System.out.println("Bạn vừa thanh toán chiếc Iphone 10");
    }
}
