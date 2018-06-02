package com.example.designpattern.ObserverPattern.ex2;

/**
 * Created by Billy on 3/20/2018.
 */

public class Main {
    public static void main(String[] args) {
        Customer c1 = new Customer("Mai Xuan Huu");
        Customer c2 = new Customer("Le Minh Hieu");

        Event ev1 = new Event("Nhan dip 20 - 10");
        Event ev2 = new Event("Nhan dip 20 - 11");

        ev1.registerObserver(c1); // sự kiện 1 đăng ký khách hàng c1
        ev1.registerObserver(c2);//sự kiện  1 đăng ký khách hàng c2
        ev1.notifyObserver(); // gửi thông báo 1 đến c1,c2
        ev2.registerObserver(c1); // sự kiện 2 đăng ký khách hàng c1
        ev2.registerObserver(c2); // sự kiện 2 đăng ký khách hàng c2
        ev2.notifyObserver();//gửi thông báo 2 đến c1,c2
        /*
        * Kết quả :

xin chao :Mai Xuan Huu Nhan dip 20-10Viettel Khuyen Mai 100% gia tri the nap
xin chao :Le Minh Hieu Nhan dip 20-10Viettel Khuyen Mai 100% gia tri the nap
xin chao :Mai Xuan Huu Nhan dip 20-11Viettel Khuyen Mai 100% gia tri the nap
xin chao :Le Minh Hieu Nhan dip 20-11Viettel Khuyen Mai 100% gia tri the nap
        * */
    }
}
