package com.bgs.homeshare.ui.notifications;

import android.os.AsyncTask;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ListView;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;

import com.bgs.homeshare.Managers.NotificationManager;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.R;
import com.bgs.homeshare.databinding.FragmentNotificationsBinding;

import java.util.ArrayList;
import java.util.List;

public class NotificationsFragment extends Fragment {

    private FragmentNotificationsBinding binding;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        NotificationsViewModel notificationsViewModel =
                new ViewModelProvider(this).get(NotificationsViewModel.class);

        binding = FragmentNotificationsBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        NotificationsFragment.notificationTask c = new notificationTask();
        c.execute(UserManager.LoggedInUser.getUserId());

        return root;
    }

    class notificationTask extends AsyncTask<Integer, Void, Boolean> {

        protected Boolean doInBackground(Integer... urls) {
            try {
                return NotificationManager.GetNotifications(urls[0]);
            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(Boolean result) {
            int newNotifications = 0;
            if (NotificationManager.notifications != null) {
                if (NotificationManager.notifications.size() != 0) {
                    for (int i = 0; i < NotificationManager.notifications.size(); i++) {
                        if (NotificationManager.notifications.get(i).getNotified() != 1) {
                            newNotifications++;
                        } else {
                            break;
                        }
                    }
                }
            }

            TextView notificationTitle = binding.numberOfNotifications;
            if (newNotifications == 1) {
                notificationTitle.setText("You have " + newNotifications + " new notification!");
            }else if(newNotifications == 0){
                notificationTitle.setText("You have no new notifications!");
            }
            else {
                notificationTitle.setText("You have " + newNotifications + " new notifications!");
            }
            notificationTitle.setVisibility(View.VISIBLE);

            ListView listNotifications = binding.notificationListNotifications;
            List<String> arrayList = new ArrayList<>();
            for (int i = 0; i < NotificationManager.notifications.size(); i++) {
                arrayList.add(NotificationManager.notifications.get(i).getText());
            }
            ArrayAdapter<String> arrayAdapter = new ArrayAdapter<String>(getActivity(), R.layout.notifications_list, arrayList);
            listNotifications.setAdapter(arrayAdapter);

            return;
        }
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }
}