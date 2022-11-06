package com.bgs.homeshare;

import android.app.AlertDialog;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;


import androidx.appcompat.app.AppCompatActivity;

import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.Invitation;
import com.bgs.homeshare.Models.Property;
import com.bgs.homeshare.Models.User;
import com.bgs.homeshare.databinding.ActivityCreateInviteBinding;
import com.bgs.homeshare.ui.profile.ProfileFragment;

import java.util.Arrays;
import java.util.List;

public class CreateInvite extends AppCompatActivity {

    private ActivityCreateInviteBinding binding;
    private String usc;
    private Invitation createInvite;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateInviteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());


    }


//    private void calculateMilesBetween(Property property1){
//        calculateTask c = new calculateTask();
//        String origin;
//        origin = "&origins=";
//        origin += (property1.getAddress().replaceAll(" ", "%20"));
//        origin += "%2C%20" + property1.getCity().replaceAll(" ", "%20");
//        origin += "%2C%20" + property1.getState() + "%2C%20" + property1.getCountry();
//        c.execute(origin, usc);
//    }
//
//    class calculateTask extends AsyncTask<String, Void, Boolean> {
//        public View v;
//        private Exception exception;
//
//        protected Boolean doInBackground(String... urls) {
//            try {
//
//            } catch (Exception e) {
//                this.exception = e;
//            }
//            return false;
//        }
//
//        protected void onPostExecute(Boolean result) {
//            if (!result) {
//
//                return;
//            }
//            else{
//                createInvite.property.set
//            }
//        }
//    }

}