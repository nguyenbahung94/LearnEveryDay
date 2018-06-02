package com.example.designpattern.Facade.ex2;

import java.util.Scanner;

/**
 * Created by nbhung on 3/19/2018.
 */

public class FacadePaternClient {
    private int choise;
    Scanner sc = new Scanner(System.in);
    MobileStoreKeeper storeKp = new MobileStoreKeeper();

    public void main(String[] args) throws Exception {
        do {
            System.out.println("Quý khách muốn xem điện thoại nào từ cửa hàng chúng tôi?");
            System.out.println("1. Iphone");
            System.out.println("2. Xiaomi");
            System.out.println("3. Samsung");
            System.out.println("4. Exit");
            System.out.println("Mời quý khách nhập lựa chọn :) ");
            choise = sc.nextInt();
            switch (choise) {
                case 1:
                    storeKp.iphoneSale();
                case 2:
                    storeKp.xiaomiSale();
                case 3:
                    storeKp.samsungSale();
                default:
                    System.out.println("Mời quý khách xem thêm các mẫu trong cửa hàng.");
            }
        } while (choise != 4);
    }
}
