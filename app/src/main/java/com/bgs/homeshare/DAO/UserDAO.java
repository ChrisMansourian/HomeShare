package com.bgs.homeshare.DAO;

import android.graphics.Bitmap;
import android.os.Build;
import android.util.Base64;

import androidx.annotation.RequiresApi;

import com.bgs.homeshare.Models.User;
import com.bgs.homeshare.SQL.SqlConnection;

import org.json.JSONObject;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import kotlinx.coroutines.Job;
import okhttp3.OkHttpClient;
import okhttp3.*;

public class UserDAO {

    public static User CheckLogin(String userName, String password) {
        OkHttpClient client = new OkHttpClient();

        /*Request request = new Request.Builder()
                .url("http://localhost:5256/Login/CheckLogin?username=" + userName + "&password=" + password)
                .build();*/
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Login/CheckLogin?username=" + userName + "&password=" + password)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();
            JSONObject Jobject = new JSONObject(temp);

            return GetUserFromJsonObject(Jobject);

            // Do something with the response.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User GetUser(int userId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Login/GetUser?userId=" + userId)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();
            JSONObject Jobject = new JSONObject(temp);

            return GetUserFromJsonObject(Jobject);

            // Do something with the response.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static User GetUserByName(String userName) {

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Login/GetUserByName?username=" + userName)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();
            JSONObject Jobject = new JSONObject(temp);

            return GetUserFromJsonObject(Jobject);

            // Do something with the response.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return null;
    }

    public static boolean CheckUserNameExists(String userName) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Login/CheckUserNameExists?username=" + userName)
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

    public static boolean ChangeUserName(int userId, String userName) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Login/ChangeUserName?userId=" + userId + "&username=" + userName)
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

    public static boolean UpdateProfile(String userName, String dob, String email, String number,
                                     String academicFocus, String schoolYear, String personalIntro, byte[] img,
                                        String personalityQuestion1, String personalityQuestion2, String personalityQuestion3) {

        OkHttpClient client = new OkHttpClient();

        boolean result = false;

        try {
            JSONObject jsonObject = new JSONObject();
            String imgString = android.util.Base64.encodeToString(img, Base64.NO_WRAP);

            jsonObject.put("img", imgString );

            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Login/UpdateProfile?username=" + userName
                            + "&dob=" + sqlDate.toString() + "&email=" + email + "&number=" + number + "&academicFocus=" + academicFocus
                            + "&schoolYear=" + schoolYear + "&personalIntro=" + personalIntro + "&personalityQuestion1=" + personalityQuestion1
                            + "&personalityQuestion2=" + personalityQuestion2 + "&personalityQuestion3=" + personalityQuestion3)
                    .post(body)
                    .build();
            /*Request request = new Request.Builder()
                    .url("http://localhost:5256/Login/UpdateProfile?username=" + userName
                            + "&dob=" + sqlDate.toString() + "&email=" + email + "&number=" + number + "&academicFocus=" + academicFocus
                            + "&schoolYear=" + schoolYear + "&personalIntro=" + personalIntro + "&personalityQuestion1=" + personalityQuestion1
                            + "&personalityQuestion2=" + personalityQuestion2 + "&personalityQuestion3=" + personalityQuestion3)
                    .post(body)
                    .build();*/

            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            result = Boolean.parseBoolean(temp);

            // Do something with the response.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    public static boolean CreateAccount(String userName, String password, String dob, String email, String number,
                                        String academicFocus, String schoolYear, String personalIntro, byte[] img,
                                        String personalityQuestion1, String personalityQuestion2, String personalityQuestion3) {

        OkHttpClient client = new OkHttpClient();

        boolean result = false;

        try {
            JSONObject jsonObject = new JSONObject();
            String imgString = android.util.Base64.encodeToString(img, Base64.NO_WRAP);

            jsonObject.put("img", imgString );

            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Login/SignUp?username=" + userName + "&password=" + password
                            + "&dob=" + sqlDate.toString() + "&email=" + email + "&number=" + number + "&academicFocus=" + academicFocus
                            + "&schoolYear=" + schoolYear + "&personalIntro=" + personalIntro + "&personalityQuestion1=" + personalityQuestion1
                            + "&personalityQuestion2=" + personalityQuestion2 + "&personalityQuestion3=" + personalityQuestion3)
                    .post(body)
                    .build();
            /*Request request = new Request.Builder()
                    .url("http://localhost:5256/Login/SignUp?username=" + userName + "&password=" + password
                            + "&dob=" + sqlDate.toString() + "&email=" + email + "&number=" + number + "&academicFocus=" + academicFocus
                            + "&schoolYear=" + schoolYear + "&personalIntro=" + personalIntro + "&personalityQuestion1=" + personalityQuestion1
                            + "&personalityQuestion2=" + personalityQuestion2 + "&personalityQuestion3=" + personalityQuestion3)
                    .post(body)
                    .build();*/

            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            result = Boolean.parseBoolean(temp);

            // Do something with the response.
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }

    private static User GetUserFromJsonObject(JSONObject obj) throws Exception {
        int uId = Integer.parseInt(obj.get("userId").toString());
        String uName = obj.getString("userName");
        String email = obj.getString("email");
        String phoneNumber = obj.getString("phoneNumber");
        String DOB = obj.getString("dob");
        String academicFocus = obj.getString("academicFocus");
        String schoolYear = obj.getString("schoolYear");
        String personalIntroduction = obj.getString("personalIntroduction");
        byte[] imgBytes = Base64.decode(obj.getString("profileImageBytesString"), Base64.NO_WRAP);
        //byte[] imgBytes = null;
        String personalityQuestion1 = obj.getString("personalityQuestion1");
        String personalityQuestion2 = obj.getString("personalityQuestion2");
        String personalityQuestion3 = obj.getString("personalityQuestion3");

        return new User(uId, uName, DOB, email, phoneNumber, academicFocus, schoolYear, personalIntroduction, imgBytes, personalityQuestion1, personalityQuestion2, personalityQuestion3);
    }

}
