package com.bgs.homeshare.Managers;

import com.bgs.homeshare.DAO.InvitationDAO;
import com.bgs.homeshare.Models.Invitation;
import com.bgs.homeshare.Models.User;

import java.sql.SQLException;
import java.util.List;

public class InvitationManager {
    public static List<Invitation> invitations;
    public static Invitation myInvitation;

    public static boolean respondToInvitation(int postId, int userId, int response, List<String> questionResponses){
        return InvitationDAO.respondToInvitation(postId, userId, response, questionResponses);
    }

    public static boolean manageResponse(int postId, int userIdResponseTo, int userIdOwner, int posterResponse){
        if(InvitationDAO.manageResponse(postId, userIdResponseTo, posterResponse, userIdOwner)){
            getMyInvitation(userIdOwner);
            return true;
        }
        return false;
    }

    public static void getMyInvitation(int userId){
        myInvitation = InvitationDAO.getInvitationForPosterFromID(userId);
    }

    /**
     * @param userId
     * @param sortCriteria
     * @param ascending
     * SORT criteria values, 'squarefeet','distance','bedrooms','bathrooms'
     */
    public static void getInvitations(int userId, String sortCriteria, int ascending){//if 1 ascending , 0 descending
        invitations = InvitationDAO.getInvitations(userId, sortCriteria, ascending);
    }

    public static boolean createAnInvitation(Invitation invitation){
        if (InvitationDAO.createNewInvitation(invitation)) {
            getMyInvitation(invitation.getUserId());
            return true;
        }
        return false; //means currently have an active invitation
    }

    public static List<User> getPotentialRoomates(int postId){//roomates before we have finalized a post and reach maximum capacity
        return InvitationDAO.getRoomates(postId);
    }

//    public static List<User> getRoomates(int postId){//to be implemented
//
//    }

    public static boolean deletePost(int postId){
        try{
            if(InvitationDAO.deletePost(postId)){
                myInvitation = null;
                return true;
            }
            else{
                return false;
            }
        } catch(Exception e){//no post to delete
            return false;
        }
    }

    public static boolean deletePostFromUser(int userId){
        Invitation invite = InvitationDAO.getInvitationForPosterFromID(userId);
        try{
            if(invite != null){
                InvitationDAO.deletePost(invite.getPostId());
                return true;
            }
        }catch(Exception e){
            return false;
        }

        return false;

    }

}
