package com.example.designpattern.ObserverPattern.ex1;

/**
 * Created by nbhung on 3/19/2018.
 */

public interface Subject {
    void registerObserver(RepositoryObserver repositoryObserver);
    void removeObserver(RepositoryObserver repositoryObserver);
    void notifyObservers();
}
