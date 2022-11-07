package com.bgs.homeshare;

import android.os.Bundle;

import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.User;
import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.bgs.homeshare.databinding.ActivityViewAuserBinding;

public class ViewAUser extends AppCompatActivity {

    private ActivityViewAuserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAuserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());

        if (UserManager.ClickedUser == null) {
            finish();
        }

        ImageView img = (ImageView) findViewById(R.id.userProfileImage);
        TextView username = (TextView) findViewById(R.id.UserNameView);
        TextView dob = (TextView) findViewById(R.id.DobView);
        TextView major = (TextView) findViewById(R.id.MajorView);
        TextView gradYear = (TextView) findViewById(R.id.GraduationYearView);
        TextView personalIntro = (TextView) findViewById(R.id.PersonalIntroView);
        TextView pQ1 = (TextView) findViewById(R.id.PersonalityQuestion1View);
        TextView pQ2 = (TextView) findViewById(R.id.PersonalityQuestion2View);
        TextView pQ3 = (TextView) findViewById(R.id.PersonalityQuestion3View);

        User u = UserManager.ClickedUser;

        img.setImageBitmap(u.getProfileImage());
        username.setText(u.getUserName());
        dob.setText(u.getDOB());
        major.setText("Major: " + u.getAcademicFocus());
        gradYear.setText("School Year: " + u.getSchoolYear());
        personalIntro.setText(u.getPersonalIntroduction());
        pQ1.setText(u.getPersonalityQuestion1());
        pQ2.setText(u.getPersonalityQuestion2());
        pQ3.setText(u.getPersonalityQuestion3());

    }

    public void onBackClick(View v) {
        finish();
    }
}