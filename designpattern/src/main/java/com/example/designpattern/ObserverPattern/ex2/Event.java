package com.example.designpattern.ObserverPattern.ex2;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Billy on 3/20/2018.
 */

public class Event implements Subject {
    private String eventName;
    private List<Observer> obs = new ArrayList<Observer>();

    public Event(String eventName) {
        this.eventName = eventName;
    }

    @Override
    public void registerObserver(Observer observer) {
        obs.add(observer);
    }

    @Override
    public void refuseObserver(Observer observer) {
        obs.remove(observer);
    }

    @Override
    public void notifyObserver() {
        for (Observer observer : obs) {
            observer.update(eventName); // cap nhat thong tin den tat ca khach hang dang ky nhan sms tu 199
        }
    }
}
