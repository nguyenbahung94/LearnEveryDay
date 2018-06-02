package com.example.myapplication.chap1.DefaultConstructorForNoninstantiability;
/*
* it's mean
* */
public class example {
    private example() {
        throw new AssertionError("No java.util.Objects instances for you!");
    }
}
