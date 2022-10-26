package com.bgs.homeshare.Models;

import android.graphics.Bitmap;

import com.bgs.homeshare.Util.Util;

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

    private Bitmap ProfileImage = null;

    public User(int userId, String userName, String dob, String email, String phoneNumber, String academicFocus, String schoolYear, String personalIntroduction, byte[] profileImageBytes) {
        UserId = userId;
        UserName = userName;
        DOB = dob;
        Email = email;
        PhoneNumber = phoneNumber;
        AcademicFocus = academicFocus;
        SchoolYear = schoolYear;

        PersonalIntroduction = personalIntroduction;
        ProfileImageBytes = profileImageBytes;

        ProfileImage = Util.ConvertBytesToImage(profileImageBytes);
    }

    public int getUserId() {
        return UserId;
    }

    public String getUserName() {
        return UserName;
    }

    public String getDOB() {
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
}
