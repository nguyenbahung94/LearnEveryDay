package com.example.myapplication.chap1.UseENumForSingleTon;
/*
*when you call LazySingleton lazy = LazySingleton.INSTANCE; that's time it just create
 *  */

public enum LazySingleton {
    INSTANCE;

    static {
        System.out.println("Static Initializer");
    }
}
/*
* example
* public class LazyEnumTest {
  public static void main(String[] args) throws InterruptedException {
    System.out.println("Sleeping for 5 seconds...");
    Thread.sleep(5000);
    System.out.println("Accessing enum...");
    LazySingleton lazy = LazySingleton.INSTANCE;
    System.out.println("Done.");
  }
}
* */
/*
* result
* $ java LazyEnumTest
Sleeping for 5 seconds...
Accessing enum...
Static Initializer
Done.
* */

