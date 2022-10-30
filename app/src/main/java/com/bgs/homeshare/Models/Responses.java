package com.bgs.homeshare.Models;
import java.util.List;

public class Responses {
    public List<String> questionResponses;
    public User user;

    public Responses(User user, List<String> responses){
        this.user = user;
        this.questionResponses = responses;
    }
}
