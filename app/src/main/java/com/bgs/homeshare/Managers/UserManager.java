package com.bgs.homeshare.Managers;

import android.graphics.Bitmap;

import com.bgs.homeshare.DAO.UserDAO;
import com.bgs.homeshare.Models.User;
import com.bgs.homeshare.Util.Util;

public class UserManager {

    public static User LoggedInUser = null;

    public static User GetProfile(int userId) {
        return UserDAO.GetUser(userId);
    }

    public static User GetProfileByName(String name) {
        return UserDAO.GetUserByName(name);
    }

    public static boolean Login(String userName, String password) {
        LoggedInUser = UserDAO.CheckLogin(userName, password);
        return LoggedInUser != null;
    }

    public static boolean UpdateProfile(String dob, String email, String number,
                                     String academicFocus, String schoolYear,
                                     String personalIntro, Bitmap img,
                                        String personalityQuestion1, String personalityQuestion2,
                                        String personalityQuestion3) {
        if (LoggedInUser == null) {
            return false;
        }

        if (UserDAO.UpdateProfile(LoggedInUser.getUserName(), dob, email, number, academicFocus,
                                  schoolYear, personalIntro, Util.ConvertImageToBytes(img), personalityQuestion1,
                                  personalityQuestion2, personalityQuestion3)) {
            LoggedInUser.setDOB(dob);
            LoggedInUser.setEmail(email);
            LoggedInUser.setPhoneNumber(number);
            LoggedInUser.setAcademicFocus(academicFocus);
            LoggedInUser.setSchoolYear(schoolYear);
            LoggedInUser.setPersonalIntroduction(personalIntro);
            LoggedInUser.setProfileImage(img);
            LoggedInUser.setPersonalityQuestion1(personalityQuestion1);
            LoggedInUser.setPersonalityQuestion2(personalityQuestion2);
            LoggedInUser.setPersonalityQuestion3(personalityQuestion3);
            return true;
        }
        return false;
    }

    public static boolean CreateAccount(String userName, String password,
                                        String dob, String email, String number,
                                        String academicFocus, String schoolYear,
                                        String personalIntro, Bitmap img,
                                        String personalityQuestion1, String personalityQuestion2,
                                        String personalityQuestion3) {

        if (UserDAO.CreateAccount(userName, password, dob, email, number, academicFocus,
                schoolYear, personalIntro, Util.ConvertImageToBytes(img), personalityQuestion1, personalityQuestion2, personalityQuestion3)) {
            LoggedInUser = GetProfileByName(userName);
            return true;
        }
        LoggedInUser = null;
        return false;
    }

    public static boolean CheckUserNameExists(String userName) {
        return UserDAO.CheckUserNameExists(userName);
    }

    public static boolean ChangeUserName(String userName) {
        if (LoggedInUser == null) {
            return false;
        }

        if (UserDAO.ChangeUserName(LoggedInUser.getUserId(), userName)) {
            LoggedInUser.setUserName(userName);
            return true;
        }

        return false;
    }

}
