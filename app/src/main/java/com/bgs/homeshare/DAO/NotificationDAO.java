package com.bgs.homeshare.DAO;

import com.bgs.homeshare.Models.Notification;
import com.bgs.homeshare.Models.User;
import com.bgs.homeshare.SQL.SqlConnection;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class NotificationDAO {

    public static List<Notification> GetUserNotifications(int userId) {
        Connection c = SqlConnection.GetConnection();
        List<Notification> list = new ArrayList<>();
        try {
            String SQL = "Exec usp_getNotifications " + userId;

            PreparedStatement stmt = c.prepareStatement(SQL);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                int uId = rs.getInt("USERID");
                int pId = rs.getInt("POSTID");
                int notified = rs.getInt("Notified");
                if (rs.wasNull()) {
                    notified = -1;
                }
                String text = rs.getString("Notification");

                list.add(new Notification(uId, pId, text, notified));
            }

            rs.close();
        }
        catch (SQLException e) {
            return null;
        }
        catch (Exception e) {
            return null;
        }

        return list;
    }

    public static boolean CreateNewNotification(Notification n) {
        Connection c = SqlConnection.GetConnection();
        try {
            String SQL = "Exec usp_createNotification " + n.getUserId() + ", " + n.getPostId() + ", \'" + n.getText() + "\'";

            PreparedStatement stmt = c.prepareStatement(SQL);

            stmt.execute();
        }
        catch (SQLException e) {
            return false;
        }
        catch (Exception e) {
            return false;
        }

        return true;
    }

}
