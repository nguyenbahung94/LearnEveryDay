package com.example.designpattern.singleton;

/**
 * Created by nbhung on 3/16/2018.
 */

public class Singleton {
    public static class LazyInitializedSingleton {
        /*
        * The above implementation works fine incase of single threaded environment but when it comes to multithreaded systems,
        * it can cause issues if multiple threads are inside the if loop at the same time.
        * It will destroy the singleton pattern and both threads will get the different instances of singleton class.
        * */
        private static LazyInitializedSingleton instance;

        private LazyInitializedSingleton() {
        }

        public static LazyInitializedSingleton getInstance() {
            if (instance == null) {
                instance = new LazyInitializedSingleton();
            }
            return instance;
        }

    }

    public static class ThreadSafeSingleton {
        private static ThreadSafeSingleton instance;

        private ThreadSafeSingleton() {
        }

        public static synchronized ThreadSafeSingleton getInstance() {
            if (instance == null) {
                instance = new ThreadSafeSingleton();
            }
            return instance;
        }

        //double check locking
        public static ThreadSafeSingleton getInstanceUsingDoubleLocking() {
            if (instance == null) {
                synchronized (ThreadSafeSingleton.class) {
                    if (instance == null) {
                        instance = new ThreadSafeSingleton();
                    }
                }
            }
            return instance;
        }
    }


}
