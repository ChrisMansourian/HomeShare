package com.bgs.homeshare.DAO;

import android.graphics.Bitmap;

import com.bgs.homeshare.Models.User;
import com.bgs.homeshare.SQL.SqlConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.text.SimpleDateFormat;
import java.sql.Date;

import kotlin.jvm.Throws;

public class UserDAO {

    public static User CheckLogin(String userName, String password) {
        Connection c = SqlConnection.GetConnection();
        User u = null;
        try {
            String SQL = "Exec usp_Login \'" + userName + "\', \'" + password + "\'";
            //String SQL = "{call dbo.usp_Login(?,?)}";

            PreparedStatement stmt = c.prepareStatement(SQL);

            //stmt.setString(1, userName);
            //stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            u = GetUserFromResultSet(rs);
            rs.close();
        }
        catch (SQLException e) {
        }
        catch (Exception e) {
        }
        return u;
    }

    public static User GetUser(int userId) {
        Connection c = SqlConnection.GetConnection();
        User u = null;
        try {
            String SQL = "Exec usp_getUser " + userId + "";
            PreparedStatement stmt = c.prepareStatement(SQL);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            u = GetUserFromResultSet(rs);
            rs.close();
        }
        catch (SQLException e) {
        }
        catch (Exception e) {
        }
        return u;
    }

    public static User GetUserByName(String userName) {
        Connection c = SqlConnection.GetConnection();
        User u = null;
        try {
            String SQL = "Exec usp_getUserByName \'" + userName + "\'";
            PreparedStatement stmt = c.prepareStatement(SQL);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            u = GetUserFromResultSet(rs);
            rs.close();
        }
        catch (SQLException e) {
        }
        catch (Exception e) {
        }
        return u;
    }

    public static boolean CheckUserNameExists(String userName) {
        Connection c = SqlConnection.GetConnection();
        boolean result = false;
        try {
            String SQL = "Exec usp_checkUserNameExists \'" + userName + "\'";
            PreparedStatement stmt = c.prepareStatement(SQL);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getInt("result") == 1;
            rs.close();
        }
        catch (SQLException e) {
        }
        catch (Exception e) {
        }
        return result;
    }

    public static boolean ChangeUserName(int userId, String userName) {
        Connection c = SqlConnection.GetConnection();
        boolean result = false;
        try {
            String SQL = "Exec usp_changeUserName " + userId + ", \'" + userName + "\'";
            PreparedStatement stmt = c.prepareStatement(SQL);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            result = rs.getInt("result") == 1;
            rs.close();
        }
        catch (SQLException e) {
        }
        catch (Exception e) {
        }
        return result;
    }

    public static boolean UpdateProfile(String userName, String dob, String email, String number,
                                     String academicFocus, String schoolYear, String personalIntro, byte[] img,
                                        String personalityQuestion1, String personalityQuestion2, String personalityQuestion3) {
        Connection c = SqlConnection.GetConnection();
        boolean result = false;
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            String SQL = "Exec usp_updateProfile \'" + userName + "\',"
                    + " ?,"
                    + " \'" + email + "\',"
                    + " \'" + number + "\',"
                    + " \'" + academicFocus + "\',"
                    + " \'" + schoolYear + "\',"
                    + " \'" + personalIntro + "\',"
                    + " ?,"
                    + " \'" + personalityQuestion1 + "\',"
                    + " \'" + personalityQuestion2 + "\',"
                    + " \'" + personalityQuestion3 + "\'";
            PreparedStatement stmt = c.prepareStatement(SQL);
            stmt.setDate(1, sqlDate);
            stmt.setBytes(2, img);

            stmt.execute();

            result = true;
        }
        catch (SQLException e) {
            result = false;
        }
        catch (Exception e) {
            result = false;
        }
        return result;
    }

    public static boolean CreateAccount(String userName, String password, String dob, String email, String number,
                                        String academicFocus, String schoolYear, String personalIntro, byte[] img,
                                        String personalityQuestion1, String personalityQuestion2, String personalityQuestion3) {
        Connection c = SqlConnection.GetConnection();
        boolean result = false;
        try {
            java.util.Date date = new SimpleDateFormat("yyyy-MM-dd").parse(dob);
            java.sql.Date sqlDate = new java.sql.Date(date.getTime());
            String SQL = "Exec usp_signUp \'" + userName + "\',"
                    + " \'" + password + "\',"
                    + " ?,"
                    + " \'" + email + "\',"
                    + " \'" + number + "\',"
                    + " \'" + academicFocus + "\',"
                    + " \'" + schoolYear + "\',"
                    + " \'" + personalIntro + "\',"
                    + " ?,"
                    + " \'" + personalityQuestion1 + "\',"
                    + " \'" + personalityQuestion2 + "\',"
                    + " \'" + personalityQuestion3 + "\'";
            PreparedStatement stmt = c.prepareStatement(SQL);
            stmt.setDate(1, sqlDate);
            stmt.setBytes(2, img);

            stmt.execute();

            result = true;
        }
        catch (SQLException e) {
            result = false;
        }
        catch (Exception e) {
            result = false;
        }
        return result;
    }

    private static User GetUserFromResultSet(ResultSet rs) throws SQLException, Exception {
        int uId = rs.getInt("UserId");
        String uName = rs.getString("Username");
        String email = rs.getString("Email");
        String phoneNumber = rs.getString("PhoneNumber");
        String DOB = rs.getString("DOB");
        String academicFocus = rs.getString("AcademicFocus");
        String schoolYear = rs.getString("SchoolYear");
        String personalIntroduction = rs.getString("PersonalIntroduction");
        byte[] imgBytes = rs.getBytes("ProfilePicture");
        String personalityQuestion1 = rs.getString("PersonalityQuestion1");
        String personalityQuestion2 = rs.getString("PersonalityQuestion2");
        String personalityQuestion3 = rs.getString("PersonalityQuestion3");

        return new User(uId, uName, DOB, email, phoneNumber, academicFocus, schoolYear, personalIntroduction, imgBytes, personalityQuestion1, personalityQuestion2, personalityQuestion3);
    }

}
