package com.example.designpattern.DIPattern.ex1AfterUseDi;

/**
 * Created by nbhung on 3/18/2018.
 */

public class EmailServiceImpl implements MessageService {
    @Override
    public void sendMessage(String msg, String rec) {
        //logic to send email
        System.out.println("Email sent to " + rec + " with Message=" + msg);
    }
}
