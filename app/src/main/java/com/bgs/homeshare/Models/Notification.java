package com.bgs.homeshare.Models;

public class Notification {
    private int UserId = -1;
    private int PostId = -1;
    private String Text = "";
    private int Notified = -1;

    public Notification(int userId, int postId, String text) {
        UserId = userId;
        PostId = postId;
        Text = text;
        Notified = -1;
    }

    public Notification(int userId, int postId, String text, int notified) {
        UserId = userId;
        PostId = postId;
        Text = text;
        Notified = notified;
    }

    public int getUserId() {
        return UserId;
    }

    public int getPostId() {
        return PostId;
    }

    public String getText() {
        return Text;
    }

    public int getNotified() {
        return Notified;
    }
}
