package com.bgs.homeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.bgs.homeshare.SQL.SqlConnection;

public class MainActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        SqlConnection.GetConnection();
    }

    public void onLoginClick(View v) {
        this.startActivity(new Intent(v.getContext(), LoginActivity.class));
        this.overridePendingTransition(0, 0);
    }

    public void onSignUpClick(View v) {
        this.startActivity(new Intent(v.getContext(), SignUpActivity.class));
        this.overridePendingTransition(0, 0);
    }
}