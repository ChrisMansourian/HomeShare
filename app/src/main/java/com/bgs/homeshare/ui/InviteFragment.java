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

import java.text.SimpleDateFormat;
import java.util.*;


public class InviteFragment extends Fragment {

    private static final String ARG_PARAM1 = "param1";
    private static final String ARG_PARAM2 = "param2";
    private FragmentInviteBinding binding;
    private Invitation activeInvitation;
    private LinearLayout Responses;
    private ArrayList<Responses> responsesList;


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
            SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd");
            title.setText("Deadline: " + formatter.format(activeInvitation.getDateOfDeadline()));


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
            rentContent.setText(Integer.toString(activeInvitation.property.getRent()));
            roomateContent.setText(Integer.toString(activeInvitation.getNumOfRoomates()));
            capacityContent.setText(Integer.toString(activeInvitation.property.getMaximumCapacity()));
            squareFeetContent.setText(Integer.toString(activeInvitation.property.getSquareFeet()) + " SqFt");
            bedroomContent.setText(Integer.toString(activeInvitation.property.getNumOfBedrooms())+ " Bed");
            bathroomContent.setText(Double.toString(activeInvitation.property.getNumOfBathrooms()) + " Bath");
            utilitiesContent.setText(activeInvitation.property.utilities.getUtilities());

            addressContent.setVisibility(View.VISIBLE);
            rentContent.setVisibility(View.VISIBLE);
            roomateContent.setVisibility(View.VISIBLE);
            capacityContent.setVisibility(View.VISIBLE);
            squareFeetContent.setVisibility(View.VISIBLE);
            bedroomContent.setVisibility(View.VISIBLE);
            bedroomContent.setVisibility(View.VISIBLE);
            bathroomContent.setVisibility(View.VISIBLE);
            utilitiesContent.setVisibility(View.VISIBLE);

            //list initialization
            createReponsesList();


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


    private void createReponsesList(){
        Responses = binding.listOfResponses;
        Responses.removeAllViewsInLayout();
        Responses.setVisibility(View.VISIBLE);
        responsesList = (ArrayList<Responses>) activeInvitation.getResponses();
        if(responsesList == null){
            responsesList = new ArrayList<Responses>();
        }

        for(int i = 0; i < responsesList.size();i++){
            int position = i;
            View view = LayoutInflater.from(binding.getRoot().getContext()).inflate(R.layout.responses_list, null);
            TextView name = view.findViewById(R.id.name);
            ImageView reject = view.findViewById(R.id.reject);
            ImageView accept = view.findViewById(R.id.approve);
            TextView number = view.findViewById(R.id.number);
            number.setText(i + 1 + ".");
            name.setText(responsesList.get(i).user.getUserName());
            reject.setOnClickListener(v->{
                RespondToUser c = new RespondToUser();
                c.execute(activeInvitation.getPostId(), responsesList.get(position).user.getUserId(), activeInvitation.getUserId(), 0, position);
                removeItem(position, 0);
            });
            accept.setOnClickListener(v->{
                RespondToUser c = new RespondToUser();
                c.execute(activeInvitation.getPostId(), responsesList.get(position).user.getUserId(), activeInvitation.getUserId(), 1, position);
                removeItem(position, 1);
            });
            name.setOnClickListener(v->{
                InvitationManager.clickedResponse = responsesList.get(position);
                Intent intent = new Intent(binding.getRoot().getContext(), ViewAResponseActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                startActivity(intent);
            });
            Responses.addView(view);
        }
    }
//
//    class ListViewAdapter extends ArrayAdapter<Responses> {
//        ArrayList<Responses> list;
//        Context context;
//
//        public ListViewAdapter(Context context, ArrayList<Responses> items) {
//            super(context, R.layout.responses_list, items);
//            this.context = context;
//            list = items;
//        }
//
//        @NonNull
//        @Override
//        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
//            if (convertView == null) {
//                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
//                convertView = mInflater.inflate(R.layout.responses_list, null);
//                TextView name = convertView.findViewById(R.id.name);
//                ImageView reject = convertView.findViewById(R.id.reject);
//                ImageView accept = convertView.findViewById(R.id.approve);
//                TextView number = convertView.findViewById(R.id.number);
//                number.setText(position + 1 + ".");
//                name.setText(list.get(position).user.getUserName());
//                reject.setOnClickListener(view->{
//                    RespondToUser c = new RespondToUser();
//                    c.execute(activeInvitation.getPostId(), list.get(position).user.getUserId(), activeInvitation.getUserId(), 0, position);
//                });
//                accept.setOnClickListener(view->{
//                    RespondToUser c = new RespondToUser();
//                    c.execute(activeInvitation.getPostId(), list.get(position).user.getUserId(), activeInvitation.getUserId(), 1, position);
//                });
//                name.setOnClickListener(view->{
//                    InvitationManager.ClickedResponse = list.get(position);
//
//                });
//            }
//            return convertView;
//        }
//    }

    public void removeItem(int i, int accept) {
        responsesList.remove(i);
        createReponsesList();
        if(accept == 1){
            TextView textview = binding.textViewRoomateContent;
            String text = textview.getText().toString();
            Integer w = Integer.valueOf(text);
            w++;
            textview.setText(Integer.toString(w));
            if(w == activeInvitation.property.getMaximumCapacity()){
                initializeScreen();
            }
        }
    }

    class RespondToUser extends AsyncTask<Integer, Void, Boolean>{
        public View v;
        private Exception exception;

        protected Boolean doInBackground(Integer...urls) {
            try {
                if(InvitationManager.manageResponse(urls[0], urls[1], urls[2],urls[3])){
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
                startActivity( new Intent(getContext(), HomeActivity.class));
                getActivity().overridePendingTransition(0, 0);
            }
        }
    }

    class GetInvitation extends AsyncTask<User, Void, Boolean> {
        public View v;
        private Exception exception;

        User u;

        protected Boolean doInBackground(User... urls) {
            try {
                u = urls[0];
                InvitationManager.getMyInvitation(urls[0].getUserId());
                return true;
            } catch (Exception e) {
                this.exception = e;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            try {
                activeInvitation = InvitationManager.myInvitation;
                initializeScreen();
            }
            catch (Exception e) {
                new GetInvitation().execute(u);
            }
        }
    }

    @Override
    public void onResume() {
        if (InvitationManager.myInvitation != null) {
            activeInvitation = InvitationManager.myInvitation;
            createReponsesList();
        }

        super.onResume();
    }
}