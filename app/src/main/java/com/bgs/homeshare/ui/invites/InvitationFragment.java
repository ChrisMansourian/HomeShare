package com.bgs.homeshare.ui.invites;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bgs.homeshare.databinding.FragmentInvitesBinding;

public class InvitationFragment extends Fragment {

    private FragmentInvitesBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        InvitationViewModel invitationViewModel =
                new ViewModelProvider(this).get(InvitationViewModel.class);

        binding = FragmentInvitesBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        final TextView textView = binding.textInvites;
        invitationViewModel.getText().observe(getViewLifecycleOwner(), textView::setText);
        return root;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}