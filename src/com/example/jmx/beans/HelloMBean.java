package com.example.jmx.beans;

public interface HelloMBean {
    void setMessage(String message);
    String sayHello();
    void setCount(Integer count);
    Integer getCount();
}
