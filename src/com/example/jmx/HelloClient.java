package com.example.jmx;

import com.sun.xml.internal.ws.util.StringUtils;

import javax.management.Attribute;
import javax.management.JMX;
import javax.management.MBeanServerConnection;
import javax.management.ObjectName;
import javax.management.remote.JMXConnector;
import javax.management.remote.JMXConnectorFactory;
import javax.management.remote.JMXServiceURL;
import java.util.Arrays;
import java.util.Hashtable;

public class HelloClient {
    private final String domain;
    private final String ip;
    private final int port;
    private final String contextPath;
    private final String jmxServiceUrl;

    public HelloClient() {
        this.domain = "com.example.jmx";
        this.ip = "localhost";
        this.port = 7777;
        this.contextPath = "hello";
        this.jmxServiceUrl = String.format("service:jmx:rmi:///jndi/rmi://%s:%d/%s", this.ip, this.port, this.contextPath);
    }

    public static void main(String[] args) {
        new HelloClient().foo();
    }

    private void foo() {
        try {
            JMXServiceURL jmxServiceURL = new JMXServiceURL(this.jmxServiceUrl);

            JMXConnector jmxConnector = JMXConnectorFactory.connect(jmxServiceURL, null);

            MBeanServerConnection mBeanServerConnection = jmxConnector.getMBeanServerConnection();

            String[] domains = mBeanServerConnection.getDomains();
            Arrays.sort(domains);
            for (String domain : domains) {
                System.out.println(domain);
            }

//            ObjectName helloMBeanName = ObjectName.getInstance(this.domain, "name", "helloMBean");
            ObjectName helloMBeanName = ObjectName.getInstance(this.domain, new Hashtable<String, String>(){{
                put("name", "helloMBean");
                put("type", "basic");
            }});

            HelloMBean hello = JMX.newMBeanProxy(mBeanServerConnection, helloMBeanName, HelloMBean.class, true);

            System.out.println(mBeanServerConnection.getMBeanInfo(helloMBeanName));
            System.out.println(hello.sayHello());

            hello.setMessage("Hello JMX?!?");
            System.out.println(hello.sayHello());

//            mBeanServerConnection.setAttribute(helloMBeanName, new Attribute("Message", "????"));
            mBeanServerConnection.setAttribute(helloMBeanName, new Attribute(StringUtils.capitalize("message"), "????"));
            System.out.println(hello.sayHello());

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
