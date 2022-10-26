package com.bgs.homeshare.DAO;

import com.bgs.homeshare.Models.User;
import com.bgs.homeshare.SQL.SqlConnection;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class UserDAO {

    public static User CheckLogin(String userName, String password) {
        Connection c = SqlConnection.GetConnection();
        User u = null;
        try {
            String SQL = "Exec usp_Login \'" + userName + "\', \'" + password + "\'";
            //String SQL = "{call dbo.usp_Login(?,?)}";
            //String SQL = "Exec usp_Login \'adminUser\', \'password12345\'";

            PreparedStatement stmt = c.prepareStatement(SQL);

            //stmt.setString(1, userName);
            //stmt.setString(2, password);

            ResultSet rs = stmt.executeQuery();
            rs.next();
            /*
            String row = "";
            int count = 0;
            while (rs.next()) {
                for (int i = 1; i <= 10; i++) {
                    row += rs.getString(i) + ", ";
                    row += rs.getString("Username");
                }
                count++;
            }*/

            int userId = rs.getInt("UserId");
            String uName = rs.getString("Username");
            String email = rs.getString("Email");
            String phoneNumber = rs.getString("PhoneNumber");
            String DOB = rs.getString("DOB");
            String academicFocus = rs.getString("AcademicFocus");
            String schoolYear = rs.getString("SchoolYear");
            String personalIntroduction = rs.getString("PersonalIntroduction");
            byte[] imgBytes = rs.getBytes("ProfilePicture");

            u = new User(userId, uName, DOB, email, phoneNumber, academicFocus, schoolYear, personalIntroduction, imgBytes);

        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {

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

            int uId = rs.getInt("UserId");
            String uName = rs.getString("Username");
            String email = rs.getString("Email");
            String phoneNumber = rs.getString("PhoneNumber");
            String DOB = rs.getString("DOB");
            String academicFocus = rs.getString("AcademicFocus");
            String schoolYear = rs.getString("SchoolYear");
            String personalIntroduction = rs.getString("PersonalIntroduction");
            byte[] imgBytes = rs.getBytes("ProfilePicture");

            u = new User(uId, uName, DOB, email, phoneNumber, academicFocus, schoolYear, personalIntroduction, imgBytes);
        }
        catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        catch (Exception e) {
            System.out.println(e.getMessage());
        }
        finally {

        }
        return u;
    }

}
