package com.example.designpattern.Facade.ex2;

/**
 * Created by nbhung on 3/19/2018.
 */

public class XiaoMi implements MobileStore {
    @Override
    public void showMobile() {
        System.out.println("Đây là điện thoại XiaoMi 3s");
    }

    @Override
    public void orderMobile() {
        System.out.println("Bạn đã đặt mua chiếc XiaoMi 3s");
    }

    @Override
    public void pay() {
        System.out.println("Bạn vừa thanh toán chiếc Iphone 10 ");
    }
}
