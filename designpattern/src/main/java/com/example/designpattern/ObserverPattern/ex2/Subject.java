package com.example.designpattern.ObserverPattern.ex2;

/**
 * Created by Billy on 3/20/2018.
 */
//1 class Subject ( cái này để bạn có thể lựa chọn cách phản ứng khi nhận được tin nhắn của viettel, mình lấy ví dụ là : bạn có thể tiếp tục chấp nhận TN từ 199, hoặc từ chối )
//1 class Event ( sự kiện ) sẽ thực thi interface Subject
public interface Subject {
    void registerObserver(Observer observer); // Tiep tuc Nhan tin nhan tu 199 ( default)

    void refuseObserver(Observer observer);   // Tu choi tin nhan tu 199 (option)

    void notifyObserver(); // Cap nhat thong tin den tat ca cac thue bao
}
