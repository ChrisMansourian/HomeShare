package com.bgs.homeshare.DaoTests;

import static org.junit.Assert.*;

import com.bgs.homeshare.Managers.*;
import com.bgs.homeshare.Models.*;
import com.bgs.homeshare.DAO.*;


import org.junit.Test;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class InvitationUnitTests {

    @Test
    public void createPost() {
        User loggedInUser;
        boolean result = UserManager.Login("Captain", "hello123");
        if(result) {
            loggedInUser = UserManager.LoggedInUser;
        }
        else{
           assertFalse(result);
           return;
        }

        String dateString = "2022-12-22";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try{
            date = formatter.parse(dateString);
        }
        catch (Exception e){
            return;
        }
        PropertyUtilities utils = new PropertyUtilities(true,true,true,true,false,false);
        Property property = new Property(-1, "815 Saint Katherine Dr.", "", "La Canada", "CA", "USA", 3500,4,800, utils, 4.4);
        List<String> questions = new ArrayList<>();
        questions.add("Do you love chicken?");
        questions.add("Do you love beef?");
        questions.add("Do you love lahmajune?");
        Invitation invitation = new Invitation(-1, loggedInUser.getUserId(), property, date, null, null, 1, questions);
        if(InvitationManager.createAnInvitation(invitation))
            assertNotEquals(InvitationManager.myInvitation, null);
    }

    @Test
    public void createPostFromUserWithAPost(){
        String dateString = "2022-12-22";
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
        Date date;
        try{
            date = formatter.parse(dateString);
        }
        catch (Exception e){
            return;
        }

        PropertyUtilities utils = new PropertyUtilities(true,true,true,true,false,false);
        Property property = new Property(-1, "815 Saint Katherine Dr.", "", "La Canada", "CA", "USA", 3500,4,800, utils, 4.4);
        List<String> questions = new ArrayList<>();
        questions.add("Do you love chicken?");
        questions.add("Do you love beef?");
        questions.add("Do you love lahmajune?");
        Invitation invitation = new Invitation(-1, 10, property, date, null, null, 1, questions);
        assertFalse(InvitationManager.createAnInvitation(invitation));
    }

    @Test
    public void deletePost(){
        InvitationManager.getMyInvitation(10);
        assertNotEquals(InvitationManager.myInvitation, null);
        InvitationManager.deletePost(InvitationManager.myInvitation.getPostId());
        InvitationManager.getMyInvitation(10);
        assertEquals(InvitationManager.myInvitation, null);
    }

    @Test
    public void createPostAgain(){

    }

    @Test
    public void respondToPost6Times(){

    }

    @Test
    public void reject2Posts(){

    }

    @Test
    public void accept3Posts(){

    }

}
