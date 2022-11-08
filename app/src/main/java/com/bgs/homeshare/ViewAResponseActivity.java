package com.bgs.homeshare;

import android.app.AlertDialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;

import com.bgs.homeshare.Managers.InvitationManager;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.Responses;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bgs.homeshare.databinding.ActivityViewAresponseBinding;

import java.util.ArrayList;
import java.util.List;

public class ViewAResponseActivity extends AppCompatActivity {

    private ActivityViewAresponseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAresponseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        Responses curr = InvitationManager.clickedResponse;

        if (curr == null) {
            finish();
            return;
        }

        TextView username = (TextView) findViewById(R.id.UserNameTextView);
        ImageView img = (ImageView) findViewById(R.id.userImageView);

        username.setText(curr.user.getUserName());
        img.setImageBitmap(curr.user.getProfileImage());

        LinearLayout responseList = (LinearLayout) findViewById(R.id.ResponseList);

        for (int i = 0; i < curr.questionResponses.size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.responses_list, null);
            TextView question = view.findViewById(R.id.ResponseText);
            question.setText("Response " + Integer.toString(i) + ": " + curr.questionResponses.get(i));
            responseList.addView(view);
        }
    }

    class rejectPostTask extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... urls) {
            try {
                boolean result = InvitationManager.manageResponse(InvitationManager.clickedInvitation.getPostId()
                        , InvitationManager.clickedResponse.user.getUserId(), UserManager.LoggedInUser.getUserId(), 0);
                return result;
            } catch (Exception e) {
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (result == true) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewAResponseActivity.this);
                alert.setTitle("Rejected");
                alert.setMessage("Successfully Rejected Response");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        finish();
                    }
                });
                alert.show();
                return;
            }
        }
    }

    class acceptPostTask extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... urls) {
            try {
                boolean result = InvitationManager.manageResponse(InvitationManager.clickedInvitation.getPostId()
                        , InvitationManager.clickedResponse.user.getUserId(), UserManager.LoggedInUser.getUserId(), 1);
                return result;
            } catch (Exception e) {
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (result == true) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewAResponseActivity.this);
                alert.setTitle("Accepted");
                alert.setMessage("Successfully Accepted Response");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        finish();
                    }
                });
                alert.show();
                return;
            }
            else {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewAResponseActivity.this);
                alert.setTitle("Failed");
                alert.setMessage("Failed To Accept Response");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
        }
    }

    public void onRejectClick(View v) {
        new rejectPostTask().execute();
    }

    public void onAcceptClick(View v) {
       new acceptPostTask().execute();
    }

    public void onViewProfileClick(View v) {
        UserManager.ClickedUser = InvitationManager.clickedResponse.user;
        Intent intent = new Intent(this, ViewAUser.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    @Override
    public void onResume() {
        super.onResume();
    }

    public void onBackClick(View view) {
        finish();
    }
}