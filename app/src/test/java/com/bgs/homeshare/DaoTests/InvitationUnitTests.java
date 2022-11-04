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
    public void GetPosts() {
        InvitationManager.getInvitations(1, "bedrooms", 1);

        assertNotNull(InvitationManager.invitations);
        assertTrue(InvitationManager.invitations.size() > 0);
    }

    @Test
    public void GetPostByUserId() {
        InvitationManager.getMyInvitation(3);

        assertNotNull(InvitationManager.myInvitation);
    }

    @Test
    public void createPostAndDelete() {
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

        Property property = new Property(-1, "815 Saint Katherine Dr.", "", "La Canada", "CA", "USA", 3500,4,800, utils, 4.4, 4, 3);

        List<String> questions = new ArrayList<>();
        questions.add("Do you love chicken?");
        questions.add("Do you love beef?");
        questions.add("Do you love lahmajune?");
        Invitation invitation = new Invitation(-1, 1, property, date, null, null, 1, questions);
        if(InvitationManager.createAnInvitation(invitation)) assertNotEquals(InvitationManager.myInvitation, null);
        assertTrue(InvitationManager.deletePostFromUser(1));
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

        Property property = new Property(-1, "815 Saint Katherine Dr.", "", "La Canada", "CA", "USA", 3500,4,800, utils, 4.4, 4, 3);

        List<String> questions = new ArrayList<>();
        questions.add("Do you love chicken?");
        questions.add("Do you love beef?");
        questions.add("Do you love lahmajune?");
        Invitation invitation = new Invitation(-1, 10, property, date, null, null, 1, questions);
        assertTrue(InvitationManager.createAnInvitation(invitation));
        assertFalse(InvitationManager.createAnInvitation(invitation));
        assertTrue(InvitationManager.deletePost(InvitationManager.myInvitation.getPostId()));
    }


    @Test
    public void responseSystem(){
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


        Property property = new Property(-1, "815 Saint Katherine Dr.", "", "La Canada", "CA", "USA", 3500,4,800, utils, 4.4, 4, 5);

        List<String> questions = new ArrayList<>();
        questions.add("Do you love chicken?");
        questions.add("Do you love beef?");
        questions.add("Do you love lahmajune?");
        Invitation invitation = new Invitation(-1, 10, property, date, null, null, 1, questions);
        assertTrue(InvitationManager.createAnInvitation(invitation));
        int propId = InvitationManager.myInvitation.property.getPropertyID();

        List<String> questionsResponses = new ArrayList<>();
        questionsResponses.add("Chicken aint my favorite");
        questionsResponses.add("Only Kosher but yes I love it");
        questionsResponses.add("Armenian Pizza :):):):):)");
        assertTrue(InvitationManager.respondToInvitation(InvitationManager.myInvitation.getPostId(), 3,0,questionsResponses));
        assertTrue(InvitationManager.respondToInvitation(InvitationManager.myInvitation.getPostId(), 2,1,questionsResponses));
        assertTrue(InvitationManager.respondToInvitation(InvitationManager.myInvitation.getPostId(), 1,1,questionsResponses));

        assertTrue(InvitationManager.manageResponse(InvitationManager.myInvitation.getPostId(), 2,10,1)); //user 10 accepting user 2
        assertTrue(InvitationManager.manageResponse(InvitationManager.myInvitation.getPostId(), 1,10,0)); //user 10 rejecting user 1
        assertTrue(InvitationManager.myInvitation.getNumOfRoomates() == 2);

        assertTrue(InvitationManager.respondToInvitation(InvitationManager.myInvitation.getPostId(), 5,1,questionsResponses)); //user 5 accepting invitation
        assertTrue(InvitationManager.respondToInvitation(InvitationManager.myInvitation.getPostId(), 11,1,questionsResponses)); //user 11 accepting invitation
        assertTrue(InvitationManager.manageResponse(InvitationManager.myInvitation.getPostId(), 5,10,1)); //user 10 accepting user 5
        assertTrue(InvitationManager.manageResponse(InvitationManager.myInvitation.getPostId(), 11,10,1)); //user 10 accepting user 11
        assertTrue(InvitationManager.myInvitation == null);
        InvitationDAO.deleteRoomates(propId);

    }

    @Test
    public void getInvitations(){
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

        Property property = new Property(-1, "815 Saint Katherine Dr.", "", "La Canada", "CA", "USA", 3500,4,800, utils, 4.4, 4, 3);

        List<String> questions = new ArrayList<>();
        questions.add("Do you love chicken?");
        questions.add("Do you love beef?");
        questions.add("Do you love lahmajune?");
        Invitation invitation = new Invitation(-1, 1, property, date, null, null, 1, questions);
        assertTrue(InvitationManager.createAnInvitation(invitation));

        dateString = "2022-12-22";
        formatter = new SimpleDateFormat("yyyy-MM-dd");
        try{
            date = formatter.parse(dateString);
        }
        catch (Exception e){
            return;
        }

        utils = new PropertyUtilities(true,true,true,true,false,false);

        property = new Property(-1, "825 Saint Katherine Dr.", "", "La Canada", "CA", "USA", 3500,4,800, utils, 4.4, 4, 3);

        questions = new ArrayList<>();
        questions.add("Do you love bamidor?");
        questions.add("Do you love beef?");

        invitation = new Invitation(-1, 11, property, date, null, null, 1, questions);
        assertTrue(InvitationManager.createAnInvitation(invitation));


        InvitationManager.getInvitations(11, "squarefeet",1);

        assertTrue(InvitationManager.invitations.size() >= 2);
        //assertTrue(InvitationManager.invitations.get(0).getQuestions().size() == 3);
        //assertTrue(InvitationManager.invitations.get(1).getQuestions().size() == 2);
        assertTrue(InvitationManager.deletePostFromUser(1));
        assertTrue(InvitationManager.deletePostFromUser(11));
    }

}
