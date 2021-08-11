package com.example.jmx;

import java.text.SimpleDateFormat;

public class Hello implements HelloMBean {

    private String message;

    public Hello() {
        this("Hello JMX!");
    }

    public Hello(String message) {
        this.message = message;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String sayHello() {
        return String.format("[%s] JMX Message ::: " + this.message, new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
    }
}
