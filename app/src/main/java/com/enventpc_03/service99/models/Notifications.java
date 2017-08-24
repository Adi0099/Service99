package com.enventpc_03.service99.models;

/**
 * Created by eNventPC-03 on 28-Jul-16.
 */

public class Notifications {
    private String notificationId,notification;

    public Notifications() {
    }

    public Notifications(String notificationsId, String notification) {

        this.notificationId = notificationsId;
        this.notification = notification;


    }

    public String getNotificationId() {
        return notificationId;
    }
    public void setNotificationId(String notificationId) {
        this.notificationId = notificationId;
    }

    public String getNotification() {
        return notification;
    }
    public void setNotification(String notification) {
        this.notification = notification;
    }


}
