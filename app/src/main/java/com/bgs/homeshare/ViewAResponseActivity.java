package com.bgs.homeshare;

import android.os.Bundle;

import com.google.android.material.appbar.CollapsingToolbarLayout;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.view.View;

import com.bgs.homeshare.databinding.ActivityViewAresponseBinding;

public class ViewAResponseActivity extends AppCompatActivity {

    private ActivityViewAresponseBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityViewAresponseBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}