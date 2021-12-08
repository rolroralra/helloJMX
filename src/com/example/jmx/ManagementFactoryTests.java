package com.example.jmx;

import javax.management.MBeanServer;
import java.lang.management.*;
import java.util.Arrays;

public class Test {

    public static void main(String[] args) {
        ClassLoadingMXBean classLoadingMXBean = ManagementFactory.getClassLoadingMXBean();
        CompilationMXBean compilationMXBean = ManagementFactory.getCompilationMXBean();
        GarbageCollectorMXBean garbageCollectorMXBean = ManagementFactory.getGarbageCollectorMXBeans().stream().findAny().orElseThrow(RuntimeException::new);
        OperatingSystemMXBean operatingSystemMXBean = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtimeMXBean = ManagementFactory.getRuntimeMXBean();
        ThreadMXBean threadMXBean = ManagementFactory.getThreadMXBean();
        MemoryMXBean memoryMXBean = ManagementFactory.getMemoryMXBean();
        MBeanServer mBeanServer = ManagementFactory.getPlatformMBeanServer();

        MemoryManagerMXBean memoryManagerMXBean = ManagementFactory.getMemoryManagerMXBeans().stream().findAny().orElseThrow(RuntimeException::new);


        System.out.println("LoadedClassCount: " + classLoadingMXBean.getLoadedClassCount());
        System.out.println("MBeanServer Domain Array: " + Arrays.toString(mBeanServer.getDomains()));
        System.out.println("Compilation MXBean Name: " + compilationMXBean.getName());
        System.out.println("GC MXBean MemoryPool Names: " + Arrays.toString(garbageCollectorMXBean.getMemoryPoolNames()));
        System.out.println("OS Name: " + operatingSystemMXBean.getName());
        System.out.println("runtime classpath: " + runtimeMXBean.getClassPath());
        System.out.println("thread ids: " + Arrays.toString(threadMXBean.getAllThreadIds()));
        System.out.println("NonHeapMemoryUsage: " + memoryMXBean.getNonHeapMemoryUsage());
        System.out.println("MemoryManagerMxBean memory pool names: " + Arrays.toString(memoryManagerMXBean.getMemoryPoolNames()));

    }
}
