package com.bgs.homeshare.DaoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.bgs.homeshare.DAO.UserDAO;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.User;

import org.junit.Test;

public class UserUnitTest {
    @Test
    public void GetUserFail() {
        User t = UserDAO.GetUser(-120123453);
        assertEquals(t, null);

        User t1 = UserDAO.GetUser(100000353);
        assertEquals(t1, null);
    }

    @Test
    public void GetUserSuccess() {
        User t = UserManager.GetProfile(1);
        assertNotEquals(t, null);
        assertEquals(t.getUserId(), 1);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");
        assertEquals(t.getPersonalityQuestion1(), "");

        User t1 = UserManager.GetProfile(2);
        assertNotEquals(t1, null);
        assertEquals(t1.getUserId(), 2);
        assertEquals(t1.getUserName(), "adminUser2");
        assertEquals(t1.getAcademicFocus(), "computer science");
        assertEquals(t1.getSchoolYear(), "senior");
        assertEquals(t1.getEmail(), "hello@gmail.com");
        assertEquals(t.getPersonalityQuestion1(), "");
    }

    @Test
    public void UpdateProfile() {
        boolean loginResult = UserManager.Login("adminUser", "password12345");
        assertTrue(loginResult);

        boolean result = UserManager.UpdateProfile("2022-10-26", "test@gmail.com",
                "8888888888", "comp sci",
                "junior", "hello world", null, "abcd", "efg", "hijk");

        assertTrue(result);

        User t = UserManager.GetProfile(1);
        assertNotEquals(t, null);
        assertEquals(t.getUserId(), 1);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "comp sci");
        assertEquals(t.getSchoolYear(), "junior");
        assertEquals(t.getEmail(), "test@gmail.com");
        assertEquals(t.getPersonalityQuestion1(), "abcd");
        assertEquals(t.getPersonalityQuestion2(), "efg");
        assertEquals(t.getPersonalityQuestion3(), "hijk");

        t = UserManager.LoggedInUser;
        assertNotEquals(t, null);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "comp sci");
        assertEquals(t.getSchoolYear(), "junior");
        assertEquals(t.getEmail(), "test@gmail.com");
        assertEquals(t.getPersonalityQuestion1(), "abcd");
        assertEquals(t.getPersonalityQuestion2(), "efg");
        assertEquals(t.getPersonalityQuestion3(), "hijk");

        boolean result2 = UserManager.UpdateProfile("1999-03-23", "hello@gmail.com",
                "8888888888", "computer science",
                "senior", "hello world", null, "", "", "");

        assertTrue(result2);

        t = UserManager.GetProfile(1);
        assertNotEquals(t, null);
        assertEquals(t.getUserId(), 1);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");
        assertEquals(t.getPersonalityQuestion1(), "");
        assertEquals(t.getPersonalityQuestion2(), "");
        assertEquals(t.getPersonalityQuestion3(), "");

        t = UserManager.LoggedInUser;
        assertNotEquals(t, null);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");
        assertEquals(t.getPersonalityQuestion1(), "");
        assertEquals(t.getPersonalityQuestion2(), "");
        assertEquals(t.getPersonalityQuestion3(), "");

    }

    @Test
    public void UserNameDoesNotExist() {
        boolean result = UserManager.CheckUserNameExists("adsf1asd2312uhsfda");

        assertFalse(result);
    }

    @Test
    public void UserNameExists() {
        boolean result = UserManager.CheckUserNameExists("adminUser");

        assertTrue(result);
    }

    @Test
    public void ChangeUserName() {
        boolean loginResult = UserManager.Login("adminUser", "password12345");
        assertTrue(loginResult);

        boolean result = UserManager.ChangeUserName("abcdefghijklmno");

        assertTrue(result);

        User t = UserManager.GetProfile(1);
        assertNotEquals(t, null);
        assertEquals(t.getUserId(), 1);
        assertEquals(t.getUserName(), "abcdefghijklmno");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");

        t = UserManager.LoggedInUser;
        assertNotEquals(t, null);
        assertEquals(t.getUserName(), "abcdefghijklmno");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");

        boolean result2 = UserManager.ChangeUserName("adminUser");

        assertTrue(result2);

        t = UserManager.GetProfile(1);
        assertNotEquals(t, null);
        assertEquals(t.getUserId(), 1);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");

        t = UserManager.LoggedInUser;
        assertNotEquals(t, null);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");

    }
}
