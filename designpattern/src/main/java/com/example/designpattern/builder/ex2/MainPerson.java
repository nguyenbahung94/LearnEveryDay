package com.example.designpattern.builder.ex2;

import java.util.Arrays;

/**
 * Created by nbhung on 3/16/2018.
 */

public class MainPerson {
    Person testPerson = new Person.Builder()
            .name("AnhNV")
            .age(20)
            .languages(Arrays.asList("Vietnam", "English"))
            .build();
}
