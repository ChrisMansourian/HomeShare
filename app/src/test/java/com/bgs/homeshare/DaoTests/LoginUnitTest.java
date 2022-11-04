package com.bgs.homeshare.DaoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;

import com.bgs.homeshare.DAO.UserDAO;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.User;

import org.junit.Test;

public class LoginUnitTest {

    @Test
    public void LoginFail() {
        boolean result = UserManager.Login("test", "1234");
        assertFalse(result);
        assertEquals(UserManager.LoggedInUser, null);
    }

    @Test
    public void LoginSuccess() {
        boolean result = UserManager.Login("adminUser", "password12345");
        assertTrue(result);
        User t = UserManager.LoggedInUser;
        assertNotEquals(t, null);
        assertEquals(t.getUserId(), 1);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");
        assertEquals(t.getPersonalityQuestion1(), "");
    }

    @Test
    public void LoginWrongPassword() {
        boolean result = UserManager.Login("adminUser", "password");
        assertFalse(result);
        User t = UserManager.LoggedInUser;
        assertEquals(t, null);
    }

    @Test
    public void CreateDuplicateUser() {
        boolean result = UserManager.CreateAccount("hello5", "12345",
                "1982-07-21", "hello1@gmail.com", "5555555555", "biology",
                "sophomore", "I am a test", null, "hello", "test1", "personality");
        assertFalse(result);
        User t = UserManager.LoggedInUser;
        assertEquals(t, null);
    }
}
