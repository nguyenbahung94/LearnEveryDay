package com.example.designpattern.adapter.ex2.classAdapter;

import com.example.designpattern.adapter.ex2.Socket;
import com.example.designpattern.adapter.ex2.SocketAdapter;
import com.example.designpattern.adapter.ex2.Volt;

/**
 * Created by nbhung on 3/19/2018.
 */

public class SocketClassAdapterImpl extends Socket implements SocketAdapter {
    @Override
    public Volt get120Volt() {
        return getVolt();
    }

    @Override
    public Volt get12Volt() {
        Volt v = getVolt();
        return ConvertVolt(v, 10);
    }

    @Override
    public Volt get3Volt() {
        Volt v = getVolt();
        return ConvertVolt(v, 40);
    }

    private Volt ConvertVolt(Volt v, int i) {
        return new Volt(v.getVolts() / i);
    }
}
