package com.example.designpattern.Facade.ex2;

/**
 * Created by nbhung on 3/19/2018.
 */

public class MobileStoreKeeper {
    private MobileStore iphone;
    private MobileStore xiaomi;
    private MobileStore sumsung;

    public MobileStoreKeeper() {
        this.iphone = new Iphone();
        this.xiaomi = new XiaoMi();
        this.sumsung = new Samsung();
    }

    public void iphoneSale() {
        iphone.showMobile();
        iphone.orderMobile();
        iphone.pay();
    }

    public void xiaomiSale() {
        xiaomi.showMobile();
        xiaomi.orderMobile();
        xiaomi.pay();
    }

    public void samsungSale() {
        sumsung.showMobile();
        sumsung.orderMobile();
        sumsung.pay();
    }
}
