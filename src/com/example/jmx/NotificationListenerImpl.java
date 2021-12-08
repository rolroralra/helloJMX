package com.example.jmx;

import javax.management.AttributeChangeNotification;
import javax.management.Notification;
import javax.management.NotificationFilter;
import javax.management.NotificationListener;

public class NotificationListenerImpl implements NotificationListener, NotificationFilter {
    @Override
    public void handleNotification(Notification notification, Object handback) {
        System.out.println(notification);
        System.out.println(handback);

        AttributeChangeNotification attributeChangeNotification = (AttributeChangeNotification) notification;

        System.out.println("Observed Attribute: " + attributeChangeNotification.getAttributeName());
        System.out.println("Old Value: " + attributeChangeNotification.getOldValue());
        System.out.println("New Value: " + attributeChangeNotification.getNewValue());

    }

    @Override
    public boolean isNotificationEnabled(Notification notification) {
        return AttributeChangeNotification.class.isAssignableFrom(notification.getClass());
    }
}
