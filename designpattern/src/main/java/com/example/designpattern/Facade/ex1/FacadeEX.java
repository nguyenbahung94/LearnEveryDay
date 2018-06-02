package com.example.designpattern.Facade.ex1;

/**
 * Created by nbhung on 3/19/2018.
 */

public class FacadeEX {
    private CPU processor;
    private Memory ram;
    private HardDrive hd;

    public FacadeEX() {
        this.processor = new CPU();
        this.ram = new Memory();
        this.hd = new HardDrive();
    }

    public void start() {
        processor.freeze();
        ram.load(45545, hd.read(445, 456));
        processor.jump(4564);
        processor.execute();
    }
}
