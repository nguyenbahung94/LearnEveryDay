package com.example.designpattern.DIPattern.ex1beforeuseDi;

/**
 * Created by nbhung on 3/16/2018.
 */

public class EmailService {
    public void sendEmail(String message, String receiver) {
        //logic to send email
        System.out.println("Email sent to " + receiver + " with Message=" + message);
    }
}
