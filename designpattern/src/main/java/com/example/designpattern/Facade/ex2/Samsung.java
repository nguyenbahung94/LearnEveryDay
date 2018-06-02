package com.example.designpattern.Facade.ex2;

/**
 * Created by nbhung on 3/19/2018.
 */

public class Samsung implements MobileStore  {
    @Override
    public void showMobile() {
        System.out.println("Đây là điện thoại Samsung J7 Prime");
    }

    @Override
    public void orderMobile() {
        System.out.println("Bạn đã đặt mua chiếc Samsung J7 Prime");
    }

    @Override
    public void pay() {
        System.out.println("Bạn vừa thanh toán chiếc Samsung J7 Prime");
    }
}
