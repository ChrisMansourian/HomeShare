package com.bgs.homeshare.Models;

import java.util.List;
import java.util.Date;

public class Invitation {
    private int postId = -1;
    private int userId = -1;
    private Date dateOfDeadline;
    private List<Responses> responses; //only users who responded with a value of 1
    private List<User> roomates;
    private int numOfRoomates;
    private List<String> Questions;

    public Property property;

    public Invitation(int postId, int userId,Property property, Date dateOfDeadline, List<Responses> responses, List<User> roomates, int numOfRoomates, List<String> Questions){
        this.postId = postId;
        this.userId = userId;
        this.property = property;
        this.dateOfDeadline = dateOfDeadline;
        this.responses = responses;
        this.roomates = roomates;
        this.numOfRoomates = this.roomates.size();
        this.Questions = Questions;
        this.numOfRoomates = numOfRoomates;
    }

    public int getPostId(){
        return postId;
    }

    public int getUserId(){
        return userId;
    }

    public Property getProperty(){
        return property;
    }

    public List<Responses> getResponse(){
        return responses;
    }

    public List<User> getRoomates(){
        return roomates;
    }

    public List<String> getQuestions(){
        return Questions;
    }

    public java.util.Date getDateOfDeadline(){
        return dateOfDeadline;
    }

    public int getNumOfRoomates(){
        return numOfRoomates;
    }

    public void setProperty(Property val){
        this.property = val;
    }

    public void setDateOfDeadline(java.util.Date val){
        this.dateOfDeadline = val;
    }

    public void setResponses(List<Responses> val){
        this.responses = val;
    }

    public void setRoomates(List<User> val){
        this.roomates = val;
    }
}
