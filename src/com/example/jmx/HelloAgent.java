package com.example.jmx;

import com.example.jmx.beans.Hello;
import com.example.jmx.beans.HelloMBean;

import javax.management.*;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;

public class HelloAgent {
    private static HelloMBean helloMBean;
    private static ObjectName helloMBeanObjectName;
    private final MBeanServer mbs;
    private final String domain;
    private final String ip;
    private final int port;
    private final String contextPath;
    private final String jmxServiceUrl;

    public HelloAgent() {
        this.domain = "com.example.jmx.beans";
        this.ip = "localhost";
        this.port = 7777;
        this.contextPath = "hello";
        this.mbs = MBeanServerFactory.createMBeanServer(this.domain);
        this.jmxServiceUrl = String.format("service:jmx:rmi:///jndi/rmi://%s:%d/%s", this.ip, this.port, this.contextPath);

        // JMXServiceURL = "service:jmx:rmi://[ip:port]/jndi/rmi://${ip}:${port}/${contextPath}"
        // rmi : Remote Method Invocation
        // jndi : Java Naming and Directory Interface

        try {
            LocateRegistry.createRegistry(this.port);

            JMXServiceURL serviceUrl = new JMXServiceURL(jmxServiceUrl);

            helloMBeanObjectName= new ObjectName(String.format("%s:name=%s", this.domain, "helloMBean"));
            helloMBean = new Hello();

            mbs.registerMBean(helloMBean, helloMBeanObjectName);

            JMXConnectorServer connector = JMXConnectorServerFactory.newJMXConnectorServer(serviceUrl, null, mbs);

            NotificationListener notificationListener = new NotificationListenerImpl();
            NotificationFilter notificationFilter = (NotificationFilter) notificationListener;
            connector.addNotificationListener(notificationListener, notificationFilter, new NotificationCallback());
            connector.start();

            System.out.printf("JMX Service URL: %s%n", jmxServiceUrl);

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void waitForEnterPressed() {
        try {
            System.out.println("Press to continue...");
            System.in.read();
            System.out.println(helloMBean.sayHello());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public static void main(String[] args) {
        new HelloAgent();
        System.out.println("HelloAgent is running...");
        HelloAgent.waitForEnterPressed();
    }
}
