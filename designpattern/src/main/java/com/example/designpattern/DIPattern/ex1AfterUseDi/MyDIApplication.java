package com.example.designpattern.DIPattern.ex1AfterUseDi;

/**
 * Created by nbhung on 3/18/2018.
 */

public class MyDIApplication implements Consumer {
    private MessageService service;

    public MyDIApplication(MessageService service) {
        this.service = service;
    }

    @Override
    public void processMessages(String msg, String rec) {
        this.service.sendMessage(msg, rec);
    }
}
