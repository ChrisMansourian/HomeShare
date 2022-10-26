package com.bgs.homeshare.DaoTests;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;

import com.bgs.homeshare.DAO.UserDAO;
import com.bgs.homeshare.Models.User;

import org.junit.Test;

public class GetUserUnitTest {
    @Test
    public void GetUserFail() {
        User t = UserDAO.GetUser(-1);
        assertEquals(t, null);

        User t1 = UserDAO.GetUser(100000000);
        assertEquals(t1, null);
    }

    @Test
    public void GetUserSuccess() {
        User t = UserDAO.GetUser(1);
        assertNotEquals(t, null);
        assertEquals(t.getUserId(), 1);
        assertEquals(t.getUserName(), "adminUser");
        assertEquals(t.getAcademicFocus(), "computer science");
        assertEquals(t.getSchoolYear(), "senior");
        assertEquals(t.getEmail(), "hello@gmail.com");

        User t1 = UserDAO.GetUser(2);
        assertNotEquals(t1, null);
        assertEquals(t1.getUserId(), 2);
        assertEquals(t1.getUserName(), "adminUser2");
        assertEquals(t1.getAcademicFocus(), "computer science");
        assertEquals(t1.getSchoolYear(), "senior");
        assertEquals(t1.getEmail(), "hello@gmail.com");
    }

}
