package com.bgs.homeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onBackClick(View v) {
        this.startActivity(new Intent(v.getContext(), MainActivity.class));
        this.overridePendingTransition(0, 0);
    }
}