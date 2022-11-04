package com.bgs.homeshare.DAO;

import com.bgs.homeshare.Models.Notification;
import com.bgs.homeshare.Models.User;
import com.bgs.homeshare.SQL.SqlConnection;

import org.json.JSONArray;
import org.json.JSONObject;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;

public class NotificationDAO {

    public static List<Notification> GetUserNotifications(int userId) {
        List<Notification> list = new ArrayList<>();
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Notification/GetNotifications?userId=" + userId)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            JSONArray jsonArray = new JSONArray(temp);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                int uId = jsonObject.getInt("userid");
                int pId = jsonObject.getInt("postid");
                int notified = -1;
                if (!jsonObject.isNull("notified")) {
                    notified = jsonObject.getInt("notified");
                }
                String text = jsonObject.getString("notification");

                list.add(new Notification(uId, pId, text, notified));
            }
        }
        catch (Exception e) {
            return null;
        }

        return list;
    }

    public static boolean CreateNewNotification(Notification n) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Notification/CreateNewNotification?userId=" + n.getUserId() + "&postId=" + n.getPostId() + "&text=" + n.getText())
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            return Boolean.parseBoolean(temp);

            // Do something with the response.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return false;
    }

}
