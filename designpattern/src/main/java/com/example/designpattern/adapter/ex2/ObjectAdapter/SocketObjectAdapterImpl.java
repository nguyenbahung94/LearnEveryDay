package com.example.designpattern.adapter.ex2.ObjectAdapter;

import com.example.designpattern.adapter.ex2.Socket;
import com.example.designpattern.adapter.ex2.SocketAdapter;
import com.example.designpattern.adapter.ex2.Volt;

/**
 * Created by nbhung on 3/19/2018.
 */

public class SocketObjectAdapterImpl implements SocketAdapter {
    private Socket socket = new Socket();

    @Override
    public Volt get120Volt() {
        return socket.getVolt();
    }

    @Override
    public Volt get12Volt() {
        Volt v = socket.getVolt();
        return convertVolt(v, 10);
    }

    @Override
    public Volt get3Volt() {
        Volt v = socket.getVolt();
        return convertVolt(v, 40);
    }

    private Volt convertVolt(Volt v, int i) {
        return new Volt(v.getVolts() / i);
    }
}
