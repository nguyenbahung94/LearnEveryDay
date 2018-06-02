package com.example.myapplication.chap1.ConsiderStaticFactoryMethodsInsteadofConstructors;

import static java.lang.Math.cos;
import static java.lang.Math.sin;

public class example {
    public static example fromCartesian(double real, double imag) {
        return new example(real, imag);
    }

    public static example fromPolar(double modulus, double angle) {
        return new example(modulus * cos(angle), modulus * sin(angle));
    }

    private example(double a, double b) {
        //...
    }
    //call :Complex c = Complex.fromPolar(1, pi)
}
