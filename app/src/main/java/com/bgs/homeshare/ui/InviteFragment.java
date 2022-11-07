package com.bgs.homeshare.ui;

import android.app.Activity;
import android.app.AlertDialog;
import android.app.AsyncNotedAppOp;
import android.content.Context;
import android.content.Intent;
import android.os.*;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.*;
import android.widget.*;


import com.bgs.homeshare.*;
import com.bgs.homeshare.Managers.*;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.*;
import com.bgs.homeshare.databinding.FragmentInviteBinding;

import java.util.*;


public class InviteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentInviteBinding binding;
    private Invitation activeInvitation;
    private ListView Responses;
    private ArrayList<Responses> responsesList;
    private ListViewAdapter adapter;

    public InviteFragment() {
        // Required empty public constructor
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        binding = FragmentInviteBinding.inflate(inflater, container, false);
        View root = binding.getRoot();

        GetInvitation c = new GetInvitation();
        c.execute(UserManager.LoggedInUser);

        return root;
    }

    private void initializeScreen(){
        TextView title = binding.textViewCreateandDeadline;
        Button createButton = binding.createInvitationButton;
        if(activeInvitation == null){//create invite option
            title.setVisibility(View.VISIBLE);
            title.setText("Create a Invite:");
            createButton.setVisibility(View.VISIBLE);
            createButton.setOnClickListener(v->{
                startActivity( new Intent(v.getContext(), CreateInvite.class));
                getActivity().overridePendingTransition(0, 0);
            });
        }
        else{//manage invite option
            createButton.setVisibility(View.INVISIBLE);
            title.setVisibility(View.VISIBLE);
            title.setText("Invitation Deadline: " + activeInvitation.getDateOfDeadline().toString());

            TextView addressTitle = binding.textViewTitleAddress;
            TextView rentTitle = binding.textViewTitleRent;
            TextView roomateTitle = binding.textViewTitleRoomates;
            TextView capacityTitle = binding.textViewTitleCapacity;
            TextView utilitiesTitle = binding.textViewTitleUtilities;
            TextView responsesTitle = binding.textViewResponsesTitle;

            addressTitle.setVisibility(View.VISIBLE);
            rentTitle.setVisibility(View.VISIBLE);
            roomateTitle.setVisibility(View.VISIBLE);
            capacityTitle.setVisibility(View.VISIBLE);
            utilitiesTitle.setVisibility(View.VISIBLE);
            responsesTitle.setVisibility(View.VISIBLE);

            TextView addressContent = binding.textViewAddress;
            TextView rentContent = binding.textViewRentContent;
            TextView roomateContent = binding.textViewRoomateContent;
            TextView capacityContent = binding.textViewCapacityContent;
            TextView squareFeetContent = binding.textViewSquareFeet;
            TextView bedroomContent = binding.textViewBedrooms;
            TextView bathroomContent = binding.textViewBathrooms;
            TextView utilitiesContent = binding.textViewUtilitiesList;

            addressContent.setText(activeInvitation.property.getAddress());
            rentContent.setText(activeInvitation.property.getRent());
            roomateContent.setText(activeInvitation.getNumOfRoomates());
            capacityContent.setText(activeInvitation.property.getMaximumCapacity());
            squareFeetContent.setText(activeInvitation.property.getSquareFeet() + " SqFt");
            bedroomContent.setText(activeInvitation.property.getNumOfBedrooms() + " Bed");
            bathroomContent.setText(activeInvitation.property.getNumOfBathrooms() + " Bath");
            utilitiesContent.setText(activeInvitation.property.utilities.getUtilities());

            //list initialization
            Responses = binding.listOfResponses;
            responsesList = (ArrayList<Responses>) activeInvitation.getResponses();
            adapter = new ListViewAdapter(getActivity().getApplicationContext(), responsesList);
            Responses.setAdapter(adapter);

            //delete button initialization
            Button deleteButton = binding.deleteButton;
            deleteButton.setVisibility(View.VISIBLE);
            deleteButton.setOnClickListener(v->{
                if(activeInvitation.getNumOfRoomates() > 1){
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Error");
                    alert.setMessage("Unable to delete post, already have roomates");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
                else{
                    DeleteInvitation c = new DeleteInvitation();
                    c.execute(activeInvitation.getPostId());
                }
            });

        }
    }

    class ListViewAdapter extends ArrayAdapter<Responses> {
        ArrayList<Responses> list;
        Context context;

        public ListViewAdapter(Context context, ArrayList<Responses> items) {
            super(context, R.layout.responses_list, items);
            this.context = context;
            list = items;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.responses_list, null);
                TextView name = convertView.findViewById(R.id.name);
                ImageView reject = convertView.findViewById(R.id.reject);
                ImageView accept = convertView.findViewById(R.id.approve);
                TextView number = convertView.findViewById(R.id.number);
                number.setText(position + 1);
                name.setText(list.get(position).user.getUserName());
                reject.setOnClickListener(view->{
                    RespondToUser c = new RespondToUser();
                    InvitationManager.ClickedResponse = list.get(position);
                    c.execute(activeInvitation.getPostId(), list.get(position).user.getUserId(), activeInvitation.getUserId(), 0, position);
                });

            }
            return convertView;
        }
    }

    public void removeItem(int i, int accept) {
        responsesList.remove(i);
        Responses.setAdapter(adapter);
        if(accept == 1){
            TextView textview = binding.textViewRoomateContent;
            String text = textview.getText().toString();
            Integer w = Integer.valueOf(text);
            textview.setText(w++);
            if(w == activeInvitation.property.getMaximumCapacity()){
                //refresh page with new invitiation

            }
        }
    }

    class RespondToUser extends AsyncTask<Integer, Void, Boolean>{
        public View v;
        private Exception exception;

        protected Boolean doInBackground(Integer...urls) {
            try {
                if(InvitationManager.manageResponse(urls[0], urls[1], urls[2],urls[3])){
                    removeItem(urls[4], 0);
                    return true;
                }
                return false;
            } catch (Exception e) {
                this.exception = e;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            return;
        }
    }


    class DeleteInvitation extends AsyncTask<Integer, Void, Boolean> {
        public View v;
        private Exception exception;

        protected Boolean doInBackground(Integer... urls) {
            try {
                boolean deleted = InvitationManager.deletePost(urls[0]);
                return deleted;
            } catch (Exception e) {
                this.exception = e;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if(!result){
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Error");
                alert.setMessage("Unable to delete post");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
            else{
                startActivity( new Intent(v.getContext(), HomeActivity.class));
                getActivity().overridePendingTransition(0, 0);
            }
        }
    }

    class GetInvitation extends AsyncTask<User, Void, Boolean> {
        public View v;
        private Exception exception;

        protected Boolean doInBackground(User... urls) {
            try {
                InvitationManager.getMyInvitation(urls[0].getUserId());
                return true;
            } catch (Exception e) {
                this.exception = e;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            activeInvitation = InvitationManager.myInvitation;
            initializeScreen();
        }
    }
}