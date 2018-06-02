package com.example.designpattern.ObserverPattern.ex1;

import java.util.ArrayList;

/**
 * Created by nbhung on 3/19/2018.
 */

public interface RepositoryObserver {
    void onUserDataChanged(String fullname, int age);
}
