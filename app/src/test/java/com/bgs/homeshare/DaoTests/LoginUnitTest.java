package com.bgs.homeshare.DaoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.bgs.homeshare.DAO.UserDAO;
import com.bgs.homeshare.Models.User;

import org.junit.Test;

public class LoginUnitTest {

    @Test
    public void LoginFail() {
        User t = UserDAO.CheckLogin("test", "1234");
        assertEquals(t, null);
    }
    @Test
    public void LoginSuccess() {
        User t = UserDAO.CheckLogin("adminUser", "password12345!");
        assertNotEquals(t, null);
        assertEquals(t.getUserName(), "adminUser");
    }
}
