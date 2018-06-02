package com.example.designpattern.builder.ex2;

import java.util.List;

/**
 * Created by nbhung on 3/16/2018.
 */

public class Person {
    private String name;
    private int age;
    private List<String> languages;

    public static class Builder {
        private String name;
        private int age;
        private List<String> languages;

        public Builder name(String name) {
            this.name = name;
            return this;
        }

        public Builder age(int age) {
            this.age = age;
            return this;
        }

        public Builder languages(List<String> languages) {
            this.languages = languages;
            return this;
        }

        public Person build() {
            return new Person(this);
        }
    }

    private Person(Builder builder) {
        name = builder.name;
        age = builder.age;
        languages = builder.languages;
    }

    @Override
    public String toString() {
        return "Person{" +
                "name='" + name + '\'' +
                ", age=" + age +
                ", languages=" + languages +
                '}';
    }
}
