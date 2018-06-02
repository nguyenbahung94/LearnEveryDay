package com.example.myapplication.chap1.UseENumForSingleTon;

public enum SingletonEnum {
    INSTANCE;
    private SingletonEnum(){

    }
    private String name;

    public void saveMyName(final String name){
        //save name here
        this.name=name;
    }

    public String getMyName(){
        return this.name;
    }

}
