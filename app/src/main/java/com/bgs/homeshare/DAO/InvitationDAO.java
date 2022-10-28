package com.bgs.homeshare.DAO;


import com.bgs.homeshare.Models.*;
import com.bgs.homeshare.SQL.*;
import com.bgs.homeshare.Util.*;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.util.List;

public class InvitationDAO {
    public static List<Invitation> getInvitations(int userId) {// gets all valid invitations that have not been
        // responded to by user and are not expired
        Connection c = SqlConnection.GetConnection();
        List<Invitation> invitations = new ArrayList<>();
        String dateNow = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());

        try {
            String SQL = "Exec usp_getPosts \'" + dateNow + "\', \'" + userId + "\'";

            PreparedStatement stmt = c.prepareStatement(SQL);

            ResultSet rs = stmt.executeQuery();

            while (rs.next()) {
                invitations.add(getInvationFromRS(rs));
            }

        } catch (SQLException e) {
            System.out.println(e.getMessage());
        } catch (Exception e) {
            System.out.println(e.getMessage());
        }

        return invitations;
    }

    public static Invitation getInvitationForPosterFromID(int userId) {// gets a invitation current user is manager of
        // if they have any non expired ones
        Connection c = SqlConnection.GetConnection();
        Invitation i = null;

        try {
            Invitation newInvite = null;
            String dateNow = (new SimpleDateFormat("yyyy-MM-dd")).format(new Date());
            String SQL = "Exec usp_getPost \'" + dateNow + "\', \'" + userId + "\'";
            PreparedStatement stmt = c.prepareStatement(SQL);

            ResultSet rs = stmt.executeQuery();

            if (rs.next()) {
                Invitation invitation = getInvationFromRS(rs);
                invitation.setResponses(getResponses(invitation.getPostId()));
                invitation.setRoomates(getRoomates(invitation.getPostId()));
                i = invitation;
            }
        } catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }

        return i;
    }

    public static Boolean createNewInvitation(Invitation invitation) {// creates a new invitation and returns an
        // invitation object with correct id values from
        // database.
        Boolean result = false;
        Connection c = SqlConnection.GetConnection();

        try {

            String dateNow = (new SimpleDateFormat("yyyy-MM-dd")).format(invitation.getDateOfDeadline());

            String SQL = "Exec usp_createPost \'" + invitation.getUserId() + "\', \'"
                    + invitation.property.getStreetAddress1() + "\', \'" + invitation.property.getStreetAddress2()
                    + "\', \'" + invitation.property.getCity() + "\', \'" + invitation.property.getState() + "\', \'"
                    + invitation.property.getCountry() + "\', \'"
                    + invitation.property.getSquareFeet()
                    + "\', \'" + invitation.property.getDistanceToCampus() + "\', \'" + dateNow
                    + "\', \'" + invitation.property.getMaximumCapacity() + "\', \'" + invitation.property.getRent()
                    + "\', \'" +
                    invitation.property.utilities.getPool().compareTo(false) + "\', \'"
                    + invitation.property.utilities.getAC().compareTo(false) + "\', \'"
                    + invitation.property.utilities.getLaundry().compareTo(false)
                    + "\', \'" + invitation.property.utilities.getDishwasher().compareTo(false) + "\', \'"
                    + invitation.property.utilities.getBalcony().compareTo(false) + "\', \'"
                    + invitation.property.utilities.getFireplace().compareTo(false) + "\'";

            PreparedStatement stmt = c.prepareStatement(SQL);

            result = stmt.execute();
            if (result) {
                Invitation newInvite = getInvitationForPosterFromID(invitation.getUserId());
                result = addQuestions(newInvite.getPostId(), invitation.getQuestions());
            }
        } catch (Exception e) {
            return result;
        }

        return result;
    }

    public static Boolean manageResponse(int postId, int userId, int posterResponse) { // records responses from
        // invitation manager
        Connection c = SqlConnection.GetConnection();
        Boolean result = false;

        try {
            String SQL = "Exec usp_managePostResponse";
            PreparedStatement stmt = c.prepareStatement(SQL);
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt.setInt(3, posterResponse);
            result = stmt.execute();
        }catch (Exception e) {
            return result;
        }

        return result;
    }

    public static Boolean respondToInvitation(int postId, int userId, int response, List<String> responses) {
        Connection c = SqlConnection.GetConnection();
        Boolean result = false;
        try {
            String SQL = "Exec usp_addResponse ?,?,?";
            PreparedStatement stmt = c.prepareStatement(SQL);
            stmt.setInt(1, postId);
            stmt.setInt(2, userId);
            stmt.setInt(3, response);
            result = stmt.execute();
            if (result) {
                result = addQuestions(postId, responses);
            }
        } catch (Exception e) {
            return result;
        }
        return result;
    }

    public static List<Responses> getResponses(int postId) {
        Connection c = SqlConnection.GetConnection();
        List<Responses> responses = new ArrayList<>();

        try {
            String SQL = "Exec usp_getPost \'" + postId + "\'";

            PreparedStatement stmt = c.prepareStatement(SQL);

            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("USERID");
                List<String> questionResponses = getUserQuestionResponses(userId, postId);
                responses.add(new Responses(UserDAO.GetUser(userId), questionResponses));
            }
        } catch (Exception e) {
        }
        return responses;
    }

    public static List<String> getUserQuestionResponses(int userId, int postId) {
        Connection c = SqlConnection.GetConnection();
        List<String> questionResponses = new ArrayList<>();

        try {
            String sql = "Exec usp_getQuestionResponses \'" + Integer.toString(postId) + "\', \'"
                    + Integer.toString(userId) + "\'";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                String response = rs.getString("QuestionsResponse");
                questionResponses.add(response);
            }
        } catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        return questionResponses;
    }

    public static List<User> getRoomates(int postId) {
        Connection c = SqlConnection.GetConnection();
        List<User> roomates = new ArrayList<>();

        try {
            String sql = "Exec usp_getCurrentRoomates \'" + Integer.toString(postId) + "\'";
            PreparedStatement stmt = c.prepareStatement(sql);
            ResultSet rs = stmt.executeQuery();
            while (rs.next()) {
                int userId = rs.getInt("USERID");
                roomates.add(UserDAO.GetUser(userId));
            }
        } catch (SQLException e) {
            return null;
        } catch (Exception e) {
            return null;
        }
        return roomates;
    }

    public static Boolean addQuestionsResponses(int postId, int userId, List<String> Questions) {
        Connection c = SqlConnection.GetConnection();
        String sql = "Exec usp_addQuestionResponse ?,?,?,?";
        Boolean result = true;

        for (int i = 0; i < Questions.size(); i++) {
            try {
                PreparedStatement stmt = c.prepareStatement(sql);
                stmt.setInt(1, postId);
                stmt.setInt(2, (i + 1));
                stmt.setInt(3, userId);
                stmt.setString(4, Questions.get(i));
                if (result)
                    result = stmt.execute();
            }
            catch(SQLException e){

            }
            catch(Exception e){

            }
        }

        return result;
    }

    public static Boolean addQuestions(int postId, List<String> Questions) {
        Connection c = SqlConnection.GetConnection();
        String sql = "Exec usp_addQuestion ?,?,?";
        Boolean result = true;

        for (int i = 0; i < Questions.size(); i++) {
            try {
                PreparedStatement stmt = c.prepareStatement(sql);
                stmt.setInt(1, postId);
                stmt.setInt(2, (i + 1));
                stmt.setString(3, Questions.get(i));
                if (result)
                    result = stmt.execute();
            }
            catch(SQLException e){

            }
            catch(Exception e){

            }
        }

        return result;
    }

    public static Invitation getInvationFromRS(ResultSet rs) {
        try {
            int postId = rs.getInt("POSTID");
            int propertyID = rs.getInt("PROPERTYID");
            String streetAddress1 = rs.getString("StreetAddress1");
            String streetAddress2 = rs.getString("StreetAddress2");
            String state = rs.getString("State");
            String city = rs.getString("City");
            String country = rs.getString("Country");
            Boolean ac = Util.returnBoolFromInt(rs.getInt("AC"));
            Boolean balcony = Util.returnBoolFromInt(rs.getInt("Balcony"));
            Boolean dishwasher = Util.returnBoolFromInt(rs.getInt("Dishwasher"));
            Boolean fireplace = Util.returnBoolFromInt(rs.getInt("Fireplace"));
            Boolean laundry = Util.returnBoolFromInt(rs.getInt("Laundry"));
            Boolean pool = Util.returnBoolFromInt(rs.getInt("Pool"));
            int rent = rs.getInt("Rent");
            int squareFeet = rs.getInt("SquareFeet");
            int maxCap = rs.getInt("MaximumCapacity");
            int numOfRoomates = rs.getInt("NumberOfRoomates");
            int ownerId = rs.getInt("USERID");
            double distance = rs.getFloat("DistanceToCampus");
            java.util.Date date = rs.getDate("DOD");
            List<String> splitQuestions = Arrays.asList((rs.getString("InvitationQuestions")).split(","));
            PropertyUtilities utilities = new PropertyUtilities(pool, ac, laundry, dishwasher, balcony, fireplace);
            Property property = new Property(propertyID, streetAddress1, streetAddress2, city, state, country, rent, maxCap, squareFeet, utilities, distance);
            Invitation invitation = new Invitation(postId, ownerId, property, date, null, null, numOfRoomates, splitQuestions);
            return invitation;
        }
        catch(Exception e){
            return null;
        }

    }

}
