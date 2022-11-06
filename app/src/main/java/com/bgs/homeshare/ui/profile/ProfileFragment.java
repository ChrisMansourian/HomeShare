package com.bgs.homeshare.ui.profile;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.GridView;
import android.widget.ImageView;
import android.widget.Spinner;

import com.bgs.homeshare.HomeActivity;
import com.bgs.homeshare.MainActivity;
import com.bgs.homeshare.Models.User;
import com.bgs.homeshare.R;
import com.bgs.homeshare.Managers.*;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import com.bgs.homeshare.databinding.FragmentProfileBinding;
import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.Arrays;
import java.util.Calendar;
public class ProfileFragment extends Fragment {

    private FragmentProfileBinding binding;

    private User loggedIn;
    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private ImageView IVPreviewImage;
    private int SELECT_PICTURE = 200;

    public View onCreateView(@NonNull LayoutInflater inflater,
                             ViewGroup container, Bundle savedInstanceState) {
        ProfileViewModel profileViewModel =
                new ViewModelProvider(this).get(ProfileViewModel.class);

        binding = FragmentProfileBinding.inflate(inflater, container, false);
        View root = binding.getRoot();


        loggedIn = new User(UserManager.LoggedInUser);
        initDatePicker();
        dateButton = binding.birthDatePickerProfilePage;
        dateButton.setText(loggedIn.getDOB());

        dateButton.setOnClickListener(v -> {
            datePickerDialog.show();
        });

        Spinner spinnerGraduationYear = binding.graduationYearSpinnerProfilePage;
        ArrayAdapter<CharSequence> adapterGraduationYear = ArrayAdapter.createFromResource(getActivity(), R.array.years2023to2032, android.R.layout.simple_spinner_item);
        adapterGraduationYear.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerGraduationYear.setAdapter(adapterGraduationYear);

        Spinner spinnerPersonalityQuestion1 = binding.spinnerPersonalityQuestion1Profile;
        ArrayAdapter<CharSequence> adapterPersonalityQuestion1 = ArrayAdapter.createFromResource(getActivity(), R.array.personalityQuestion1Options, android.R.layout.simple_spinner_item);
        adapterPersonalityQuestion1.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPersonalityQuestion1.setAdapter(adapterPersonalityQuestion1);

        Spinner spinnerPersonalityQuestion2 = binding.spinnerPersonalityQuestion2Profile;
        ArrayAdapter<CharSequence> adapterPersonalityQuestion2 = ArrayAdapter.createFromResource(getActivity(), R.array.personalityQuestion2Options, android.R.layout.simple_spinner_item);
        adapterPersonalityQuestion2.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPersonalityQuestion2.setAdapter(adapterPersonalityQuestion2);

        Spinner spinnerPersonalityQuestion3 = binding.spinnerPersonalityQuestion3Profile;
        ArrayAdapter<CharSequence> adapterPersonalityQuestion3 = ArrayAdapter.createFromResource(getActivity(), R.array.personalityQuestion3Options, android.R.layout.simple_spinner_item);
        adapterPersonalityQuestion3.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerPersonalityQuestion3.setAdapter(adapterPersonalityQuestion3);


        //initializing spinners to correct values from loggedInUser
        initializeSpinner(spinnerGraduationYear, loggedIn.getSchoolYear());
        initializeSpinner(spinnerPersonalityQuestion1, loggedIn.getPersonalityQuestion1());
        initializeSpinner(spinnerPersonalityQuestion2, loggedIn.getPersonalityQuestion2());
        initializeSpinner(spinnerPersonalityQuestion3, loggedIn.getPersonalityQuestion3());

        TextView username = binding.userNameProfilePage;
        username.setText(loggedIn.getUserName());

        EditText email = binding.editTextEmailProfilePage;
        EditText number = binding.editTextNumberProfilePage;
        EditText major = binding.editTextMajorProfilePage;
        EditText personalIntro = binding.editTextTextMultiLine;

        email.setText(loggedIn.getEmail());
        number.setText(loggedIn.getPhoneNumber());
        major.setText(loggedIn.getAcademicFocus());
        personalIntro.setText(loggedIn.getPersonalIntroduction());

        email.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                String insert = s.toString();
                if(insert.equals("")){
                    insert = null;
                }
                loggedIn.setEmail(insert);
            }
        });

        number.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                String insert = s.toString();
                if(insert.equals("")){
                    insert = null;
                }
                loggedIn.setPhoneNumber(insert);
            }
        });

        major.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                String insert = s.toString();
                if(insert.equals("")){
                    insert = null;
                }
                loggedIn.setAcademicFocus(insert);
            }
        });

        personalIntro.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                // Fires right after the text has changed
                String insert = s.toString();
                if(insert.equals("")){
                    insert = null;
                }
                loggedIn.setPersonalIntroduction(insert);
            }
        });

        spinnerGraduationYear.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                loggedIn.setSchoolYear(parentView.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        spinnerPersonalityQuestion1.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                loggedIn.setPersonalityQuestion1(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnerPersonalityQuestion2.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                loggedIn.setPersonalityQuestion2(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        spinnerPersonalityQuestion3.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                loggedIn.setPersonalityQuestion3(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        //edit image code

        IVPreviewImage = binding.userPhotoImageViewProfilePage;
        byte[] imageArray = loggedIn.getProfileImageBytes();
        Bitmap bmp = BitmapFactory.decodeByteArray(imageArray,0, imageArray.length);
        IVPreviewImage.setImageBitmap(Bitmap.createScaledBitmap(bmp, 120, 120, false));
        IVPreviewImage.setOnClickListener(v -> chooseSelfie());

        Button saveButton = binding.saveChangesButtonProfilePage;
        saveButton.setOnClickListener(v -> {
                if(!anyNull(loggedIn)) {
                    if(anyChanges(loggedIn, UserManager.LoggedInUser)){
                        SaveAccountTask c = new SaveAccountTask();
                        c.v = v;
                        c.execute(loggedIn);
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("Saved");
                        alert.setMessage("Your changes have been saved!");
                        alert.setPositiveButton("OK", null);
                        alert.show();
                    }
                    else{
                        AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                        alert.setTitle("No Changes");
                        alert.setMessage("You have no changes to save, your records are up to date");
                        alert.setPositiveButton("OK", null);
                        alert.show();
                    }
                }
                else{//ask for user to fill in all fields
                    AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                    alert.setTitle("Empty Fields");
                    alert.setMessage("Some mandatory fields are still empty");
                    alert.setPositiveButton("OK", null);
                    alert.show();
                }
            });

        Button logOutButton = binding.logOutButtonProfilePage;

        logOutButton.setOnClickListener(v->{
            UserManager.LoggedInUser = null;
            NotificationManager.notifications = null;
            InvitationManager.invitations = null;
            InvitationManager.myInvitation = null;
            startActivity( new Intent(v.getContext(), MainActivity.class));
            getActivity().overridePendingTransition(0, 0);
        });


        return root;
    }

    private void initializeSpinner(Spinner spinner, String val){
        ArrayAdapter myAdap = (ArrayAdapter)spinner.getAdapter();
        spinner.setSelection(myAdap.getPosition(val));
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        binding = null;
    }

    public boolean anyNull(User user){
        if(user.getUserName() == null || user.getAcademicFocus() == null || user.getPersonalIntroduction() == null || user.getEmail() == null || user.getSchoolYear() == null || user.getProfileImage() == null || user.getDOB() == null || user.getPhoneNumber() == null || user.getPersonalityQuestion1() == null || user.getPersonalityQuestion2() == null || user.getPersonalityQuestion3() == null){
            return true;
        }
        return false;
    }

    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = (datePicker, year, month, day) -> {
            month = month + 1;
            String date = makeDateString(day, month, year);
            dateButton.setText(date);
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(getActivity(), style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        String date = year+"-"+ (new DecimalFormat("00")).format(month)  + "-" + (new DecimalFormat("00")).format(day) ;
        loggedIn.setDOB(date);
        return date;
    }

    void chooseSelfie() {

        // create an instance of the
        // intent of the type image
        Intent i = new Intent();
        i.setType("image/*");
        i.setAction(Intent.ACTION_GET_CONTENT);

        // pass the constant to compare it
        // with the returned requestCode
        startActivityForResult(Intent.createChooser(i, "Select Picture"), SELECT_PICTURE);
    }

    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == android.app.Activity.RESULT_OK) {

            // compare the resultCode with the
            // SELECT_PICTURE constant
            if (requestCode == SELECT_PICTURE) {
                // Get the url of the image from data
                Uri selectedImageUri = data.getData();
                if (null != selectedImageUri) {
                    // update the preview image in the layout
                    IVPreviewImage.setImageURI(selectedImageUri);
                    Bitmap bitmap = ((BitmapDrawable) IVPreviewImage.getDrawable()).getBitmap();
                    ByteArrayOutputStream baos = new ByteArrayOutputStream();
                    bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
                    byte[] imageInByte = baos.toByteArray();
                    loggedIn.setProfileImageBytes(imageInByte);
                }
            }
        }
    }



    class SaveAccountTask extends AsyncTask<User, Void, Boolean> {
        public View v;
        private Exception exception;

        protected Boolean doInBackground(User... urls) {
            try {
                boolean updatedAccount = UserManager.UpdateProfile(urls[0]);
                return updatedAccount;
            } catch (Exception e) {
                this.exception = e;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (!result) {
                AlertDialog.Builder alert = new AlertDialog.Builder(getActivity());
                alert.setTitle("Unexcpected Error");
                alert.setMessage("Please try again, we had trouble updating your profile changes");
                alert.setPositiveButton("OK", null);
                alert.show();
                return;
            }
        }
    }

    private boolean anyChanges(User user1, User user2){
        if(!user1.getEmail().equals(user2.getEmail())){
            return true;
        }
        if(!user1.getPhoneNumber().equals(user2.getPhoneNumber())){
            return true;
        }
        if(!user1.getAcademicFocus().equals(user2.getAcademicFocus())){
            return true;
        }
        if(!user1.getDOB().equals(user2.getDOB())){
            return true;
        }
        if(!user1.getPersonalIntroduction().equals(user2.getPersonalIntroduction())){
            return true;
        }
        if(!user1.getPersonalityQuestion1().equals(user2.getPersonalityQuestion1())){
            return true;
        }
        if(!user1.getPersonalityQuestion2().equals(user2.getPersonalityQuestion2())){
            return true;
        }
        if(!user1.getPersonalityQuestion3().equals(user2.getPersonalityQuestion3())){
            return true;
        }
        if(!user1.getSchoolYear().equals(user2.getSchoolYear())){
            return true;
        }
        if(!Arrays.equals(user1.getProfileImageBytes(), user2.getProfileImageBytes())){
            return true;
        }
        return false;
    }
}