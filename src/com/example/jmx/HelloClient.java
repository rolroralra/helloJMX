package com.example.jmx;

import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Arrays;

public class HelloClient {
    private String domain;
    private String ip;
    private int port;
    private String contextPath;

    public HelloClient() {
        this.domain = "helloDomain";
        this.ip = "localhost";
        this.port = 7777;
        this.contextPath = "hello";
    }

    public static void main(String[] args) {
        new HelloClient().foo();
    }

    private void foo() {
        try {
            JMXServiceURL jmxServiceURL = new JMXServiceURL(String.format("service:jmx:rmi:///jndi/rmi://%s:%d/%s", this.ip, this.port, this.contextPath));

            JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, null);

            MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();

            String[] domains = mBeanServerConnection.getDomains();
            Arrays.sort(domains);
            for (String domain : domains) {
                System.out.println(domain);
            }

            ObjectName helloMBeanName = new ObjectName(String.format("%s:name=helloMBean", this.domain));

            HelloMBean hello = JMX.newMBeanProxy(mBeanServerConnection, helloMBeanName, HelloMBean.class, true);

            hello.setMessage("Test1234");
            System.out.println(hello.sayHello());

        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
