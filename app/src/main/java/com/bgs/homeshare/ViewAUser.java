package com.bgs.homeshare;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.bgs.homeshare.databinding.ActivityViewAuserBinding;

public class ViewAUser extends AppCompatActivity {

    private ActivityViewAuserBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAuserBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}