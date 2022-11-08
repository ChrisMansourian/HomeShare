package com.bgs.homeshare.DAO;

import android.util.*;
import com.bgs.homeshare.Models.*;
import org.json.JSONObject;

import java.net.UnknownHostException;
import java.text.SimpleDateFormat;
import okhttp3.*;

public class UserDAO {

    public static User CheckLogin(String userName, String password) {
        OkHttpClient client = new OkHttpClient();
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
        }
        catch (UnknownHostException e) {
            return CheckLogin(userName, password);
        }
        catch (Exception e) {
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
                            + "&dob=" + sqlDate + "&email=" + email + "&number=" + number + "&academicFocus=" + academicFocus
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


    public static boolean CreateAccount(User user, String password) {

        OkHttpClient client = new OkHttpClient();

        boolean result = false;

        try {
            JSONObject jsonObject = new JSONObject();
            String imgString = android.util.Base64.encodeToString(user.getProfileImageBytes(), Base64.NO_WRAP);

            jsonObject.put("img", imgString );

            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(user.getDOB());
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonObject.toString());
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Login/SignUp?username=" + user.getUserName() + "&password=" + password
                            + "&dob=" + sqlDate.toString() + "&email=" + user.getEmail() + "&number=" + user.getPhoneNumber() + "&academicFocus=" + user.getAcademicFocus()
                            + "&schoolYear=" + user.getSchoolYear() + "&personalIntro=" + user.getPersonalIntroduction() + "&personalityQuestion1=" + user.getPersonalityQuestion1()
                            + "&personalityQuestion2=" + user.getPersonalityQuestion2() + "&personalityQuestion3=" + user.getPersonalityQuestion3())
                    .post(body)
                    .build();

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

    public static User GetUserFromJsonObject(JSONObject obj) throws Exception {
        int uId = Integer.parseInt(obj.get("userId").toString());
        String uName = obj.getString("userName");
        String email = obj.getString("email");
        String phoneNumber = obj.getString("phoneNumber");
        String DOB = obj.getString("dob");
        DOB = DOB.substring(0,10);

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
