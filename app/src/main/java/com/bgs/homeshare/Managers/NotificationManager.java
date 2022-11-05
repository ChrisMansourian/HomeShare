package com.bgs.homeshare.Managers;

import com.bgs.homeshare.DAO.NotificationDAO;
import com.bgs.homeshare.Models.Notification;

import java.util.*;

public class NotificationManager {

    public static List<Notification> notifications;

    public static boolean SendNotification(int userId, int postId, String msg) {
        return NotificationDAO.CreateNewNotification(new Notification(userId, postId, msg));
    }

    public static Boolean GetNotifications(int userId) {
        notifications = NotificationDAO.GetUserNotifications(userId);
        Collections.reverse(notifications);
        return (notifications != null);
    }

}
