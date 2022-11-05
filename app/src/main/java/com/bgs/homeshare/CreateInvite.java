package com.bgs.homeshare;

import android.os.Bundle;



import androidx.appcompat.app.AppCompatActivity;

import com.bgs.homeshare.databinding.ActivityCreateInviteBinding;

public class CreateInvite extends AppCompatActivity {

    private ActivityCreateInviteBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        binding = ActivityCreateInviteBinding.inflate(getLayoutInflater());
        setContentView(binding.getRoot());
    }
}