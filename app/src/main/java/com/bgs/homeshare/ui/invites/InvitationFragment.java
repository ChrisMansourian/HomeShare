package com.bgs.homeshare.ui.invites;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bgs.homeshare.Managers.InvitationManager;
import com.bgs.homeshare.Managers.NotificationManager;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.Invitation;
import com.bgs.homeshare.R;
import com.bgs.homeshare.Util.InvitationAdapter;
import com.bgs.homeshare.databinding.FragmentInvitesBinding;
import com.bgs.homeshare.ui.notifications.NotificationsFragment;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

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

        InvitationsTask c = new InvitationsTask();
        c.execute("distance", "1");

        Spinner sortOptions = (Spinner)root.findViewById(R.id.SortOptionsSpinner);
        Spinner sortOrder = (Spinner)root.findViewById(R.id.SortOrderSpinner);

        sortOptions.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                InvitationsTask i = new InvitationsTask();
                i.execute(sortOptions.getSelectedItem().toString(), sortOrder.getSelectedItem().toString().equals("asc") ? "1" : "0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        sortOrder.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                InvitationsTask i = new InvitationsTask();
                i.execute(sortOptions.getSelectedItem().toString(), sortOrder.getSelectedItem().toString().equals("asc") ? "1" : "0");
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        return root;
    }

    class InvitationsTask extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... urls) {
            try {
                InvitationManager.getInvitations(UserManager.LoggedInUser.getUserId(), urls[0], Integer.parseInt(urls[1]));
                return true;
            } catch (Exception e) {
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            ListView listInvitations = binding.invitationListNotifications;
            TextView textInvites = binding.textInvites;

            if (result == true) {
                if (InvitationManager.invitations.size() == 0) {
                    textInvites.setText("No Invitations Found!");
                    textInvites.setVisibility(View.VISIBLE);
                    listInvitations.setAdapter(null);
                    return;
                }

                textInvites.setVisibility(View.INVISIBLE);

                InvitationAdapter invitationAdapter = new InvitationAdapter(getContext(), InvitationManager.invitations);
                listInvitations.setAdapter(invitationAdapter);

                return;
            }
            else {
                listInvitations.setAdapter(null);
                textInvites.setText("No Invitations Found!");
                textInvites.setVisibility(View.VISIBLE);
            }
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}