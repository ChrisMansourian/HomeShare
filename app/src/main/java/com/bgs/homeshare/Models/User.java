package com.bgs.homeshare.Models;

import android.graphics.Bitmap;
import com.bgs.homeshare.Util.Util;
import java.text.DecimalFormat;

public class User {
    private int UserId = -1;
    private String UserName = "";
    private String DOB = "";
    private String Email = "";
    private String PhoneNumber = "";
    private String AcademicFocus = "";
    private String SchoolYear = "";
    private String PersonalIntroduction = "";
    private byte[] ProfileImageBytes = null;
    private String PersonalityQuestion1 = "";
    private String PersonalityQuestion2 = "";
    private String PersonalityQuestion3 = "";

    private Bitmap ProfileImage = null;

    public User(int userId, String userName, String dob, String email, String phoneNumber, String academicFocus, String schoolYear, String personalIntroduction, byte[] profileImageBytes, String personalityQuestion1, String personalityQuestion2, String personalityQuestion3) {
        UserId = userId;
        UserName = userName;
        DOB = dob;
        Email = email;
        PhoneNumber = phoneNumber;
        AcademicFocus = academicFocus;
        SchoolYear = schoolYear;

        PersonalIntroduction = personalIntroduction;
        ProfileImageBytes = profileImageBytes;

        PersonalityQuestion1 = personalityQuestion1;
        PersonalityQuestion2 = personalityQuestion2;
        PersonalityQuestion3 = personalityQuestion3;

        try{//for junit testing not in android environment
            ProfileImage = Util.ConvertBytesToImage(profileImageBytes);
        }
        catch(Exception e){
            ProfileImage = null;
        }
    }

    public User(User user) {
        UserId = user.getUserId();
        UserName = user.getUserName();
        DOB = user.getDOB();
        Email = user.getEmail();
        PhoneNumber = user.getPhoneNumber();
        AcademicFocus = user.getAcademicFocus();
        SchoolYear = user.getSchoolYear();

        PersonalIntroduction = user.getPersonalIntroduction();
        ProfileImageBytes = user.getProfileImageBytes();

        PersonalityQuestion1 = user.getPersonalityQuestion1();
        PersonalityQuestion2 = user.getPersonalityQuestion2();
        PersonalityQuestion3 = user.getPersonalityQuestion3();

        ProfileImage = Util.ConvertBytesToImage(ProfileImageBytes);
    }

    public int getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public void setUserName(String userName) {
         UserName = userName;
    }

    public String getDOB() {
        if(DOB.charAt(2) == '/'){
            String [] date = DOB.split("/");
            String actualDate =  date[2].substring(0,4) + "-" + (new DecimalFormat("00")).format(Integer.valueOf(date[1])) + "-" + (new DecimalFormat("00")).format(Integer.valueOf(date[0]));
            return actualDate;
        }
        return DOB;
    }

    public void setDOB(String DOB) {
        this.DOB = DOB;
    }

    public String getEmail() {
        return Email;
    }

    public void setEmail(String email) {
        Email = email;
    }

    public String getPhoneNumber() {
        return PhoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        PhoneNumber = phoneNumber;
    }

    public String getAcademicFocus() {
        return AcademicFocus;
    }

    public void setAcademicFocus(String academicFocus) {
        AcademicFocus = academicFocus;
    }

    public String getSchoolYear() {
        return SchoolYear;
    }

    public void setSchoolYear(String schoolYear) {
        SchoolYear = schoolYear;
    }

    public String getPersonalIntroduction() {
        return PersonalIntroduction;
    }

    public void setPersonalIntroduction(String personalIntroduction) {
        PersonalIntroduction = personalIntroduction;
    }

    public Bitmap getProfileImage() {
        return ProfileImage;
    }

    public void setProfileImage(Bitmap profileImage) {
        ProfileImage = profileImage;
        ProfileImageBytes = Util.ConvertImageToBytes(profileImage);
    }

    public byte[] getProfileImageBytes() {
        return ProfileImageBytes;
    }

    public void setProfileImageBytes(byte[] profileImageBytes) {
        ProfileImageBytes = profileImageBytes;
        ProfileImage = Util.ConvertBytesToImage(profileImageBytes);
    }

    public String getPersonalityQuestion1() {
        return PersonalityQuestion1;
    }

    public void setPersonalityQuestion1(String personalityQuestion1) {
        PersonalityQuestion1 = personalityQuestion1;
    }

    public String getPersonalityQuestion2() {
        return PersonalityQuestion2;
    }

    public void setPersonalityQuestion2(String personalityQuestion2) {
        PersonalityQuestion2 = personalityQuestion2;
    }

    public String getPersonalityQuestion3() {
        return PersonalityQuestion3;
    }

    public void setPersonalityQuestion3(String personalityQuestion3) {
        PersonalityQuestion3 = personalityQuestion3;
    }
}
