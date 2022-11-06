package com.bgs.homeshare.DaoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertNotNull;
import static org.junit.Assert.assertTrue;

import com.bgs.homeshare.DAO.NotificationDAO;
import com.bgs.homeshare.Managers.NotificationManager;
import com.bgs.homeshare.Models.Notification;

import org.junit.Test;

import java.util.List;

public class NotificationUnitTest {

    @Test
    public void NoNotifications() {
        NotificationManager.GetNotifications(-1);
        List<Notification> n = NotificationManager.notifications;

        assertNotNull(n);
        assertTrue(n.size() == 0);
    }

    @Test
    public void CreateNotification() {

        boolean result = NotificationManager.SendNotification(1, 140, "This is a test");

        assertTrue(result);

        NotificationManager.GetNotifications(1);
        List<Notification> notifications = NotificationManager.notifications;

        assertNotNull(notifications);
        assertTrue(notifications.size() > 0);
        assertEquals(notifications.get(notifications.size()-1).getNotified(), -1);


        NotificationManager.GetNotifications(1);
        List<Notification> notifications2 = NotificationManager.notifications;

        assertNotNull(notifications2);
        assertTrue(notifications2.size() > 0);
        assertNotEquals(notifications2.get(notifications.size()-1).getNotified(), -1);
    }

    @Test
    public void GetNotification() {
        NotificationManager.GetNotifications(1);
        List<Notification> n = NotificationManager.notifications;

        assertNotNull(n);
        assertTrue(n.size() > 0);
    }

}
