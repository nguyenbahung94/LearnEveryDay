package com.example.designpattern.ObserverPattern.ex1;

import android.os.Handler;

import java.util.ArrayList;

/**
 * Created by nbhung on 3/19/2018.
 */

public class UserDataRepository implements Subject {
    private String mFullName;
    private int mAge;
    private static UserDataRepository INSTANCE = null;

    //  list to save object observers
    private ArrayList<RepositoryObserver> mObservers;

    public UserDataRepository() {
        mObservers = new ArrayList<>();
        getNewDataFromRemote();
    }

    // Simulate network
    private void getNewDataFromRemote() {
        final Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                setUserData("Chike Mgbemena", 101);
            }
        }, 10000);
    }

    // Creates a Singleton of the class
    public static UserDataRepository getInstance() {
        if (INSTANCE == null) {
            INSTANCE = new UserDataRepository();
        }
        return INSTANCE;
    }

    @Override
    public void registerObserver(RepositoryObserver repositoryObserver) {
        if (!mObservers.contains(repositoryObserver)) {
            mObservers.add(repositoryObserver);
        }
    }

    @Override
    public void removeObserver(RepositoryObserver repositoryObserver) {
        if (mObservers.contains(repositoryObserver)) {
            mObservers.remove(repositoryObserver);
        }
    }

    @Override
    public void notifyObservers() {
        for (RepositoryObserver observer : mObservers) {
            observer.onUserDataChanged(mFullName, mAge);
        }
    }

    public void setUserData(String fullName, int age) {
        mFullName = fullName;
        mAge = age;

        //it will send a notification for all observers (nếu nó đã đăng ký lắng nghe về sự thay đổi của đối tượng này)
        notifyObservers();
    }
}
