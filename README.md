## JMX
> Java Management Extension (MBean)

- Connector, Adaptor
- MBeanServer, Agent Service
- MBean

[https://narup.tistory.com/23](https://narup.tistory.com/23)

[https://www.youtube.com/watch?v=6kjGpSU_aJE](https://www.youtube.com/watch?v=6kjGpSU_aJE)

---
## Run Test Code
```bash
javac HelloAgent.java

java HelloAgent

javac HelloClient.java

java HelloClient
```

---
## JMX Agent
[HelloAgent.java](./src/com/example/jmx/HelloAgent.java)

<details>
  <summary>Details</summary>
  <p>

```java
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
```
  </p>
</details>

---
## JMX Client
[HelloClient.java](./src/com/example/jmx/HelloClient.java)

<details>
  <summary>Details</summary>
  <p>

```java
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

```
  </p>
</details>
    
