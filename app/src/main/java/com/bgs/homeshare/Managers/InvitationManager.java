package com.bgs.homeshare.Managers;

import com.bgs.homeshare.DAO.InvitationDAO;
import com.bgs.homeshare.Models.Invitation;

import java.util.List;

public class InvitationManager {
    public static List<Invitation> invitations;
    public static Invitation myInvitation;

    public static boolean respondToInvitation(int postId, int userId, int response, List<String> questionResponses){
        if(InvitationDAO.respondToInvitation(postId, userId, response, questionResponses)){
            getInvitations(userId);
            return true;
        }
        return false;
    }

    public static boolean manageResponse(int postId, int userIdResponseTo, int userIdOwner, int posterResponse){
        if(InvitationDAO.manageResponse(postId, userIdResponseTo, posterResponse)){
            getInvitations(userIdOwner);
            getMyInvitation(userIdOwner);
            if(myInvitation == null) {//do something

            }
            return true;
        }
        return false;
    }

    public static void getMyInvitation(int userId){
        myInvitation = InvitationDAO.getInvitationForPosterFromID(userId);
    }

    public static void getInvitations(int userId){
        invitations = InvitationDAO.getInvitations(userId);
    }

    public static boolean createAnInvitation(Invitation invitation){
        if(InvitationDAO.createNewInvitation(invitation)){
            getMyInvitation(invitation.getUserId());
            return true;
        }
        return false;
    }

}
