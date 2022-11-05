package com.bgs.homeshare.DAO;


import android.telecom.Call;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.bgs.homeshare.Managers.InvitationManager;
import com.bgs.homeshare.Managers.NotificationManager;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.*;
import com.bgs.homeshare.Util.*;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Date;
import java.text.SimpleDateFormat;
import java.util.Iterator;
import java.util.List;
import java.util.ListIterator;
import java.util.concurrent.Callable;

import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

public class InvitationDAO {
    public static List<Invitation> getInvitations(int userId, String sortCriteria, int ascending) {// gets all valid invitations that have not been
        // responded to by user and are not expired
        List<Invitation> invitations = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Invitation/GetPosts?userId=" + userId + "&sortCriteria=" + sortCriteria + "&ascending" + ascending)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            JSONArray jsonArray = new JSONArray(temp);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                invitations.add(getInvitationFromJsonObject(jsonObject));
            }
        }
        catch (Exception e) {
            return null;
        }

        return invitations;
    }

    public static Invitation getInvitationForPosterFromID(int userId) {// gets a invitation current user is manager of
        // if they have any non expired ones
        Invitation i = null;
        OkHttpClient client = new OkHttpClient();

        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Invitation/GetInvitationForPosterFromID?userId=" + userId)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            JSONObject jsonObject = new JSONObject(temp);
            i = getInvitationFromJsonObject(jsonObject);
        }
        catch (Exception e) {
            return null;
        }

        return i;
    }

    public static Boolean createNewInvitation(Invitation invitation) {// creates a new invitation and returns an
        // invitation object with correct id values from
        // database.
        Boolean result = false;
        OkHttpClient client = new OkHttpClient();

        JSONObject invit = new JSONObject();
        JSONObject prop = new JSONObject();
        JSONObject util = new JSONObject();

        try {
            String dateNow = (new SimpleDateFormat("yyyy-MM-dd")).format(invitation.getDateOfDeadline());

            util.put("pool", invitation.property.utilities.getPool());
            util.put("ac", invitation.property.utilities.getAC());
            util.put("balcony", invitation.property.utilities.getBalcony());
            util.put("dishwasher", invitation.property.utilities.getDishwasher());
            util.put("fireplace", invitation.property.utilities.getFireplace());
            util.put("laundry", invitation.property.utilities.getLaundry());

            prop.put("utilities", util);
            prop.put("propertyId", -1);
            prop.put("streetAddress1", invitation.property.getStreetAddress1());
            prop.put("streetAddress2", invitation.property.getStreetAddress2());
            prop.put("city", invitation.property.getCity());
            prop.put("state", invitation.property.getState());
            prop.put("country", invitation.property.getCountry());
            prop.put("rent", invitation.property.getRent());
            prop.put("maximumCapacity", invitation.property.getMaximumCapacity());
            prop.put("squareFeet", invitation.property.getSquareFeet());
            prop.put("distanceToCampus", invitation.property.getDistanceToCampus());
            prop.put("bathrooms", invitation.property.getNumOfBathrooms());
            prop.put("bedrooms", invitation.property.getNumOfBedrooms());

            invit.put("property", prop);
            invit.put("questions", invitation.getQuestions());
            invit.put("numOfRoomates", invitation.getNumOfRoomates());
            invit.put("dateOfDeadline", dateNow);
            invit.put("userId", invitation.getUserId());

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, invit.toString());
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Invitation/CreateNewInvitation")
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            result = Boolean.parseBoolean(temp);
        } catch (Exception e) {
            return result;
        }

        return result;
    }

    public static Boolean manageResponse(int postId, int userId, int posterResponse, int ownerUserId) { // records responses from
        Boolean result = false;
        OkHttpClient client = new OkHttpClient();

        try {
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Invitation/ManageResponse?postId=" + postId + "&userId=" + userId + "&posterResponse=" + posterResponse)
                    .build();

            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            result = Boolean.parseBoolean(temp);
        } catch (Exception e) {
            return result;
        }

        return result;
    }

    public static Boolean respondToInvitation(int postId, int userId, int response, List<String> responses) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < responses.size(); i++) {
            jsonArray.put(responses.get(i));
        }
        Boolean result = false;
        OkHttpClient client = new OkHttpClient();

        try {

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonArray.toString());
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Invitation/AddResponse?postId=" + postId + "&userId=" + userId + "&response=" + response)
                    .post(body)
                    .build();

            Response callResponse = client.newCall(request).execute();

            String temp = callResponse.body().string();
            callResponse.body().close();

            result = Boolean.parseBoolean(temp);
        } catch (Exception e) {
            return result;
        }

        return result;
    }

    public static List<Responses> getResponses(int postId) {
        List<Responses> responses = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Invitation/GetResponses?postId=" + postId)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            JSONArray jsonArray = new JSONArray(temp);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);

                User u = UserDAO.GetUserFromJsonObject(jsonObject.getJSONObject("user"));
                List<String> qR = new ArrayList<>();
                JSONArray arr = jsonObject.getJSONArray("questionResponses");
                for (int j = 0; j < arr.length(); j++) {
                    qR.add(arr.getString(i));
                }
                responses.add(new Responses(u, qR));
            }
        }
        catch (Exception e) {
            return null;
        }
        return responses;
    }

    public static List<String> GetUserQuestionResponses(int userId, int postId) {
        List<String> questionResponses = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Invitation/GetUserQuestionResponses?userId=" + userId  + "&postId=" + postId)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            JSONArray jsonArray = new JSONArray(temp);

            for (int i = 0; i < jsonArray.length(); i++) {
                questionResponses.add(jsonArray.getString(i));
            }
        }
        catch (Exception e) {
            return null;
        }
        return questionResponses;
    }

    public static List<User> getRoomates(int postId) {
        List<User> roomates = new ArrayList<>();

        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Invitation/GetRoomates?postId=" + postId)
                .build();

        try {
            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            JSONArray jsonArray = new JSONArray(temp);

            for (int i = 0; i < jsonArray.length(); i++) {
                JSONObject jsonObject = jsonArray.getJSONObject(i);
                roomates.add(UserDAO.GetUserFromJsonObject(jsonObject));
            }
        }
        catch (Exception e) {
            return null;
        }

        return roomates;
    }

    public static Boolean addQuestionsResponses(int postId, int userId, List<String> Questions) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Questions.size(); i++) {
            jsonArray.put(Questions.get(i));
        }
        Boolean result = false;
        OkHttpClient client = new OkHttpClient();

        try {

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonArray.toString());
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Invitation/AddQuestionResponses?postId=" + postId + "&userId=" + userId)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            result = Boolean.parseBoolean(temp);
        } catch (Exception e) {
            return result;
        }

        return result;
    }

    public static Boolean addQuestions(int postId, List<String> Questions) {
        JSONArray jsonArray = new JSONArray();
        for (int i = 0; i < Questions.size(); i++) {
            jsonArray.put(Questions.get(i));
        }
        Boolean result = false;
        OkHttpClient client = new OkHttpClient();

        try {

            MediaType JSON = MediaType.parse("application/json; charset=utf-8");
            RequestBody body = RequestBody.create(JSON, jsonArray.toString());
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Invitation/AddQuestions?postId=" + postId)
                    .post(body)
                    .build();

            Response response = client.newCall(request).execute();

            String temp = response.body().string();
            response.body().close();

            result = Boolean.parseBoolean(temp);
        } catch (Exception e) {
            return result;
        }

        return result;
    }

    public static Invitation getInvitationFromJsonObject(JSONObject rs) {
        try {
            int postId = rs.getInt("postId");
            JSONObject propertyObject = rs.getJSONObject("property");
            int propertyID = propertyObject.getInt("propertyID");
            String streetAddress1 = propertyObject.getString("streetAddress1");
            String streetAddress2 = propertyObject.getString("streetAddress2");
            String state = propertyObject.getString("state");
            String city = propertyObject.getString("city");
            String country = propertyObject.getString("country");
            JSONObject utilitiesObject = propertyObject.getJSONObject("utilities");
            Boolean ac = utilitiesObject.getBoolean("ac");
            Boolean balcony = utilitiesObject.getBoolean("balcony");
            Boolean dishwasher = utilitiesObject.getBoolean("dishwasher");
            Boolean fireplace = utilitiesObject.getBoolean(("fireplace"));
            Boolean laundry = utilitiesObject.getBoolean(("laundry"));
            Boolean pool = utilitiesObject.getBoolean(("pool"));
            int rent = propertyObject.getInt("rent");
            int squareFeet = propertyObject.getInt("squareFeet");
            int maxCap = propertyObject.getInt("maximumCapacity");
            int numOfRoomates = rs.getInt("numOfRoomates");
            int ownerId = rs.getInt("userId");
            double distance = propertyObject.getDouble("distanceToCampus");
            double bathrooms = propertyObject.getDouble("bathrooms");
            int bedrooms = propertyObject.getInt("bedrooms");
            java.util.Date date = new SimpleDateFormat("MM/dd/yyyy").parse(rs.getString("dateOfDeadline"));
            List<String> splitQuestions = new ArrayList<>();
            JSONArray questions = rs.getJSONArray("questions");
            for (int i = 0; i < questions.length(); i++) {
                splitQuestions.add(questions.getString(i));
            }
            List<User> roomates = null;
            if (!rs.isNull("roomates")) {
                roomates = new ArrayList<>();
                JSONArray arr = rs.getJSONArray("roomates");

                for (int i = 0; i < arr.length(); i++) {
                    roomates.add(UserDAO.GetUserFromJsonObject(arr.getJSONObject(i)));
                }
            }

            List<Responses> responses = null;
            if (!rs.isNull("responses")) {
                responses = new ArrayList<>();
                JSONArray arr = rs.getJSONArray("responses");

                for (int i = 0; i < arr.length(); i++) {
                    JSONObject resp = arr.getJSONObject(i);

                    User u = UserDAO.GetUserFromJsonObject(resp.getJSONObject("user"));
                    List<String> qR = new ArrayList<>();
                    JSONArray arr2 = resp.getJSONArray("questionResponses");
                    for (int j = 0; j < arr2.length(); j++) {
                        qR.add(arr2.getString(i));
                    }
                    responses.add(new Responses(u, qR));
                }
            }

            PropertyUtilities utilities = new PropertyUtilities(pool, ac, laundry, dishwasher, balcony, fireplace);
            Property property = new Property(propertyID, streetAddress1, streetAddress2, city, state, country, rent, maxCap, squareFeet, utilities, distance, bathrooms, bedrooms);
            Invitation invitation = new Invitation(postId, ownerId, property, date, null, roomates, numOfRoomates, splitQuestions);
            return invitation;
        }
        catch (Exception e){
            return null;
        }
    }


    public static boolean deletePost(int postId) {
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder()
                .url("https://homeshareapi.azurewebsites.net/Invitation/DeletePost?postId=" + postId)
                .build();

        try {
            Response response = client.newCall(request).execute();

            return true;
        }
        catch (Exception e) {
            return false;
        }
    }

    public static boolean deletePostFromUser(int userId){
        Invitation invitation = getInvitationForPosterFromID(userId);
        if(invitation != null){
            try{
                deletePost(invitation.getPostId());
            }
            catch(Exception e){
                return false;
            }
            return true;
        }
        return false; //user has no active post
    }

    public static boolean deleteRoomates(int propertyId){
            OkHttpClient client = new OkHttpClient();
            Request request = new Request.Builder()
                    .url("https://homeshareapi.azurewebsites.net/Invitation/DeleteRoomates?propertyId=" + propertyId)
                    .build();

            try {
                Response response = client.newCall(request).execute();

                return true;
            }
            catch (Exception e) {
                return false;
            }
    }

}
