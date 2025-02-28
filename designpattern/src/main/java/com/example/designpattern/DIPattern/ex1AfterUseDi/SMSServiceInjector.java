package com.example.designpattern.DIPattern.ex1AfterUseDi;

/**
 * Created by nbhung on 3/18/2018.
 */

public class SMSServiceInjector implements MessageServiceInjector {
    @Override
    public Consumer getConsumer() {
        return new MyDIApplication(new SMSServiceImpl());
    }
}
