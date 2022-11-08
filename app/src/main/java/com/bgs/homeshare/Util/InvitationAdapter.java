package com.bgs.homeshare.Util;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.bgs.homeshare.Managers.InvitationManager;
import com.bgs.homeshare.Models.Invitation;
import com.bgs.homeshare.R;
import com.bgs.homeshare.ViewAnInvitationActivity;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

public class InvitationAdapter extends BaseAdapter {
    List<Invitation> invitationList = new ArrayList<>();
    Context context;

    public InvitationAdapter(Context context, List<Invitation> list) {
        invitationList = list;
        this.context = context;
    }

    @Override
    public int getCount() {
        return invitationList.size();
    }

    @Override
    public Object getItem(int i) {
        return invitationList.get(i);
    }

    @Override
    public long getItemId(int i) {
        return i;
    }

    @Override
    public View getView(int i, View view, ViewGroup viewGroup) {
        if (view == null) {
            LayoutInflater inflater=(LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
            view = inflater.inflate(R.layout.invitation_list,null);
        }

        Invitation inv = invitationList.get(i);
        view = LayoutInflater.from(context).inflate(R.layout.invitation_list, viewGroup, false);
        TextView address = view.findViewById(R.id.Address);
        TextView deadline = view.findViewById(R.id.DateOfDeadline);
        TextView bedrooms = view.findViewById(R.id.BedroomsText);
        TextView bathrooms = view.findViewById(R.id.BathroomsText);
        TextView distance = view.findViewById(R.id.DistanceText);
        TextView squarefeet = view.findViewById(R.id.SquareFeetText);
        Button viewInvitations = view.findViewById(R.id.ViewInvitationButton);

        address.setText(inv.property.getAddress());
        deadline.setText("Deadline: "+ new SimpleDateFormat("yyyy-MM-dd").format(inv.getDateOfDeadline()));
        bedrooms.setText(inv.property.getNumOfBedrooms() + " beds");
        bathrooms.setText(String.valueOf(inv.property.getNumOfBathrooms()) + " bath");
        distance.setText(String.valueOf(inv.property.getDistanceToCampus()) + " miles");
        squarefeet.setText(inv.property.getSquareFeet() + " square feet");

        viewInvitations.setOnClickListener(new Button.OnClickListener() {
            public void onClick(View v) {
                InvitationManager.clickedInvitation = inv;
                Intent intent = new Intent(context, ViewAnInvitationActivity.class);
                intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
                context.startActivity(intent);
            }
        });


        return view;
    }
}
