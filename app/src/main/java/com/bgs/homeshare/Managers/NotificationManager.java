package com.bgs.homeshare.Managers;

import com.bgs.homeshare.DAO.NotificationDAO;
import com.bgs.homeshare.Models.Notification;

import java.util.List;

public class NotificationManager {

    public static boolean SendNotification(int userId, int postId, String msg) {
        return NotificationDAO.CreateNewNotification(new Notification(userId, postId, msg));
    }

    public static List<Notification> GetNotifications(int userId) {
        return NotificationDAO.GetUserNotifications(userId);
    }

}
