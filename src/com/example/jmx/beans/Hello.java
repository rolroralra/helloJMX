package com.example.jmx.beans;

import java.text.SimpleDateFormat;

public class Hello implements HelloMBean {

    private String message;
    private Integer count;

    public Hello() {
        this("Hello JMX!", 0);
    }

    public Hello(String message, Integer count) {
        this.message = message;
        this.count = count;
    }

    @Override
    public void setMessage(String message) {
        this.message = message;
    }

    @Override
    public String sayHello() {
        System.out.println("COUNT: " + this.count);
        this.count++;
        return String.format("[%s] JMX Message ::: " + this.message, new SimpleDateFormat( "yyyy-MM-dd HH:mm:ss").format(System.currentTimeMillis()));
    }

    @Override
    public void setCount(Integer count) {
        this.count = count;
    }

    @Override
    public Integer getCount() {
        return this.count;
    }
}
