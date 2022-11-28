package com.bgs.homeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.*;
import android.os.*;
import android.view.*;
import android.widget.*;

import com.bgs.homeshare.Managers.*;
import com.bgs.homeshare.Models.*;
import java.text.*;
import java.util.*;

public class ViewAnInvitationActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_an_invitation);

        if (InvitationManager.clickedInvitation == null) {
            finish();
        }

        Button b = (Button)findViewById(R.id.ViewCreatorButton);
        b.setEnabled(false);

        TextView address = (TextView) findViewById(R.id.AddressText);
        TextView rent = (TextView) findViewById(R.id.RentText);
        TextView maxCapacity = (TextView) findViewById(R.id.MaxCapacityText);
        TextView sqft = (TextView) findViewById(R.id.SquareFeetText);
        TextView bedsText = (TextView) findViewById(R.id.BedsText);
        TextView bathsText = (TextView) findViewById(R.id.BathsText);
        TextView distanceText = (TextView) findViewById(R.id.DistanceToCampusText);
        TextView pool = (TextView) findViewById(R.id.Pool);
        TextView ac = (TextView) findViewById(R.id.ac);
        TextView laundry = (TextView) findViewById(R.id.Laundry);
        TextView dishwasher = (TextView) findViewById(R.id.DishWasher);
        TextView firePlace = (TextView) findViewById(R.id.FirePlace);
        TextView balcony = (TextView) findViewById(R.id.Balcony);
        TextView roomatesNum = (TextView) findViewById(R.id.NumberOfRoomates);
        TextView deadlineText = (TextView) findViewById(R.id.DeadlineText);

        Invitation curr = InvitationManager.clickedInvitation;

        new getUserTask().execute(curr.getUserId());

        address.setText(curr.property.getAddress());
        rent.setText("Rent: $" + curr.property.getRent());
        maxCapacity.setText("Maximum Capacity: " + curr.property.getMaximumCapacity());
        sqft.setText(curr.property.getSquareFeet() + " square feet");
        bedsText.setText(curr.property.getNumOfBedrooms() + " beds");
        bathsText.setText(curr.property.getNumOfBathrooms() + " baths");
        distanceText.setText(curr.property.getDistanceToCampus() + " miles");
        pool.setText("Pool? " + (curr.property.getUtilities().getPool() == true ? "yes" : "no"));
        ac.setText("AC? " + (curr.property.getUtilities().getAC() == true ? "yes" : "no"));
        laundry.setText("Laundry? " + (curr.property.getUtilities().getLaundry() == true ? "yes" : "no"));
        dishwasher.setText("Dish Washer? " + (curr.property.getUtilities().getDishwasher() == true ? "yes" : "no"));
        firePlace.setText("Fire Place? " + (curr.property.getUtilities().getFireplace() == true ? "yes" : "no"));
        balcony.setText("Balcony? " + (curr.property.getUtilities().getPool() == true ? "yes" : "no"));
        roomatesNum.setText(curr.getNumOfRoomates() + " current roommate");
        deadlineText.setText(new SimpleDateFormat("yyyy-MM-dd").format(curr.getDateOfDeadline()));

        LinearLayout questionsList = (LinearLayout) findViewById(R.id.QuestionsList);

        for (int i = 0; i < curr.getQuestions().size(); i++) {
            View view = LayoutInflater.from(this).inflate(R.layout.questions_list, null);
            TextView question = view.findViewById(R.id.QuestionText);
            question.setText(curr.getQuestions().get(i));
            questionsList.addView(view);
        }
    }

    class getUserTask extends AsyncTask<Integer, Void, User> {

        protected User doInBackground(Integer... urls) {
            try {
                User u = UserManager.GetProfile(urls[0]);
                return u;
            } catch (Exception e) {
            }
            return null;
        }

        protected void onPostExecute(User result) {
            try {
                if (result == null) {
                    //finish();
                    new getUserTask().execute(InvitationManager.clickedInvitation.getUserId());
                    return;
                }

                UserManager.ClickedUser = result;
                Button b = (Button) findViewById(R.id.ViewCreatorButton);
                b.setEnabled(true);

                TextView tv = (TextView) findViewById(R.id.CreatedByText);
                tv.setText("Created By: " + result.getUserName());
            }
            catch (Exception e) {
                new getUserTask().execute(InvitationManager.clickedInvitation.getUserId());
            }
        }
    }

    class rejectPostTask extends AsyncTask<String, Void, Boolean> {

        protected Boolean doInBackground(String... urls) {
            try {
                boolean result = InvitationManager.respondToInvitation(InvitationManager.clickedInvitation.getPostId()
                    , UserManager.LoggedInUser.getUserId(), 0, new ArrayList<>());
                return result;
            } catch (Exception e) {
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (result == true) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewAnInvitationActivity.this);
                alert.setTitle("Rejected");
                alert.setMessage("Successfully Rejected Invitation");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        finish();
                    }
                });
                alert.show();
                return;
            }
        }
    }

    class acceptPostTask extends AsyncTask<String, Void, Boolean> {
        List<String> responses;

        protected Boolean doInBackground(String... urls) {
            try {
                boolean result = InvitationManager.respondToInvitation(InvitationManager.clickedInvitation.getPostId()
                        , UserManager.LoggedInUser.getUserId(), 1, responses);
                return result;
            } catch (Exception e) {
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (result == true) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewAnInvitationActivity.this);
                alert.setTitle("Accepted");
                alert.setMessage("Successfully Accepted Invitation");
                alert.setPositiveButton("OK", new DialogInterface.OnClickListener()
                {
                    public void onClick(DialogInterface dialog, int id)
                    {
                        finish();
                    }
                });
                alert.show();
                return;
            }
            else {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewAnInvitationActivity.this);
                alert.setTitle("Failed");
                alert.setMessage("Failed To Accept Invitation");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
        }
    }

    public void onRejectClick(View v) {
        new rejectPostTask().execute();
    }

    public void onAcceptClick(View v) {
        LinearLayout questionsList = (LinearLayout) findViewById(R.id.QuestionsList);

        List<String> responses = new ArrayList<>();
        for (int i = 0; i < InvitationManager.clickedInvitation.getQuestions().size(); i++) {
            View view = questionsList.getChildAt(i);

            EditText e = (EditText) view.findViewById(R.id.ResponseText);
            String text = e.getText().toString();

            if (text.length() < 1) {
                AlertDialog.Builder alert = new AlertDialog.Builder(ViewAnInvitationActivity.this);
                alert.setTitle("Failed");
                alert.setMessage("Not All Questions Were Answered");
                alert.setPositiveButton("OK", null);
                alert.show();
                return;
            }

            responses.add(text);
        }

        acceptPostTask a = new acceptPostTask();
        a.responses = responses;
        a.execute();
    }

    public void onViewProfileClick(View v) {
        Intent intent = new Intent(this, ViewAUser.class);
        intent.addFlags(Intent.FLAG_ACTIVITY_NO_ANIMATION);
        startActivity(intent);
    }

    public void onBackClick(View v) {
        finish();
    }
}