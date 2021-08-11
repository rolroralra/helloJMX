package com.example.jmx;

import javax.management.MBeanServer;
import javax.management.MBeanServerFactory;
import javax.management.ObjectName;
import javax.management.remote.JMXConnectorServer;
import javax.management.remote.JMXConnectorServerFactory;
import javax.management.remote.JMXServiceURL;
import java.io.IOException;
import java.rmi.registry.LocateRegistry;

public class HelloAgent {
    private MBeanServer mbs;
    private String domain;
    private String ip;
    private int port;
    private String contextPath;

    public HelloAgent() {
        this.domain = "helloDomain";
        this.ip = "localhost";
        this.port = 7777;
        this.contextPath = "hello";
        this.mbs = MBeanServerFactory.createMBeanServer(this.domain);

        try {
            LocateRegistry.createRegistry(this.port);
            // rmi : Remote Method Invocation
            // jndi : Java Naming and Directory Interface
            JMXServiceURL serviceUrl = new JMXServiceURL(String.format("service:jmx:rmi:///jndi/rmi://%s:%d/%s", this.ip, this.port, this.contextPath));

            ObjectName helloMBeanName = new ObjectName(String.format("%s:name=helloMBean", this.domain));

            Hello helloMBean = new Hello();
            mbs.registerMBean(helloMBean, helloMBeanName);

            JMXConnectorServer connector = JMXConnectorServerFactory.newJMXConnectorServer(serviceUrl, null, mbs);

            connector.start();

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private static void waitForEnterPressed() {
        try {
            System.out.println("Press to continue...");
            System.in.read();
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
