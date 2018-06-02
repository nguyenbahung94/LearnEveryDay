package com.example.designpattern.Facade.ex1;

/**
 * Created by nbhung on 3/19/2018.
 */

public class HardDrive {
    public byte[] read(long lba, int size) {
        return new byte[123];
    }
}
