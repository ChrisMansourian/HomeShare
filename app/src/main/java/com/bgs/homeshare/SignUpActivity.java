package com.bgs.homeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.app.DatePickerDialog;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.text.*;
import android.view.*;
import android.widget.*;

import com.bgs.homeshare.Models.*;
import com.bgs.homeshare.Managers.*;

import java.io.ByteArrayOutputStream;
import java.text.DecimalFormat;
import java.util.Calendar;

public class SignUpActivity extends AppCompatActivity {

    private DatePickerDialog datePickerDialog;
    private Button dateButton;
    private Button BSelectImage;
    public User user;
    private String password;
    // One Preview Image
    private ImageView IVPreviewImage;
    private int SELECT_PICTURE = 200;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_sign_up);


        //for date picker
        initDatePicker();
        dateButton = findViewById(R.id.birthDatePickerSignUp);
        dateButton.setText(getTodaysDate());

        Spinner spinnerLanguages = findViewById(R.id.graduationYearSpinnerSignUp);
        ArrayAdapter<CharSequence> adapter=ArrayAdapter.createFromResource(this, R.array.years2023to2032, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        spinnerLanguages = findViewById(R.id.personalityQuestion1SpinnerSignUp);
        adapter = ArrayAdapter.createFromResource(this, R.array.personalityQuestion1Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        spinnerLanguages = findViewById(R.id.personalityQuestion2SpinnerSignUp);
        adapter = ArrayAdapter.createFromResource(this, R.array.personalityQuestion2Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        spinnerLanguages = findViewById(R.id.personalityQuestion3SpinnerSignUp);
        adapter = ArrayAdapter.createFromResource(this, R.array.personalityQuestion3Options, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerLanguages.setAdapter(adapter);

        user =  new User(-1, null, "", null, null,null, null, null, null, null, null, null);

        EditText etValue = findViewById(R.id.userNameTextSignUp);
        etValue.addTextChangedListener(new TextWatcher() {
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
                user.setUserName(insert);
            }
        });

        EditText passwordText = findViewById(R.id.passwordTextSignUp);
        passwordText.addTextChangedListener(new TextWatcher() {
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
                password = insert;
            }
        });

        EditText emailText = findViewById(R.id.emailTextSignUp);
        emailText.addTextChangedListener(new TextWatcher() {
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
                user.setEmail(insert);
            }
        });

        EditText numberText = findViewById(R.id.numberTextSignUp);
        numberText.addTextChangedListener(new TextWatcher() {
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
                user.setPhoneNumber(insert);
            }
        });

        EditText majorText = findViewById(R.id.majorTextSignUp);
        majorText.addTextChangedListener(new TextWatcher() {
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
                user.setAcademicFocus(insert);
            }
        });

        EditText personalIntroductionText = findViewById(R.id.personalIntroductionTextSignUp);
        personalIntroductionText.addTextChangedListener(new TextWatcher() {
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
                user.setPersonalIntroduction(insert);
            }
        });


        BSelectImage = findViewById(R.id.profileImageButtonSignUp);
        IVPreviewImage = findViewById(R.id.profileViewImage);

        // handle the Choose Image button to trigger
        // the image chooser function
        BSelectImage.setOnClickListener(v -> chooseSelfie());

        Spinner graduationYearSpinner = findViewById(R.id.graduationYearSpinnerSignUp);
        Spinner personalityQuestion1Spinner = findViewById(R.id.personalityQuestion1SpinnerSignUp);
        Spinner personalityQuestion2Spinner = findViewById(R.id.personalityQuestion2SpinnerSignUp);
        Spinner personalityQuestion3Spinner = findViewById(R.id.personalityQuestion3SpinnerSignUp);
        //defaults
        user.setSchoolYear("2023");
        user.setPersonalityQuestion1("I am up bright and early");
        user.setPersonalityQuestion2("Neat and tidy is the only way");
        user.setPersonalityQuestion3("I love guests and will be bringing many");

        graduationYearSpinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                user.setSchoolYear(parentView.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        personalityQuestion1Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                user.setPersonalityQuestion1(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        personalityQuestion2Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                user.setPersonalityQuestion2(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });

        personalityQuestion3Spinner.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                user.setPersonalityQuestion3(parentView.getItemAtPosition(position).toString());
            }

            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }

        });
    }


    public void onBackClick(View v) {
        this.startActivity(new Intent(v.getContext(), MainActivity.class));
        this.overridePendingTransition(0, 0);
    }

    public void onCreateClick(View v){
        if(!anyNull()) {
            SignUpActivity.CreateAccountTask c = new SignUpActivity.CreateAccountTask();
            c.v = v;
            User user2 = new User(-1, password, null, null, null, null, null, null, null, null, null, null);
            c.execute(user, user2);
        }
    }


    class CreateAccountTask extends AsyncTask<User, Void, Boolean> {
        public View v;
        private Exception exception;


        protected Boolean doInBackground(User... urls) {
            try {
               if(UserManager.CheckUserNameExists(urls[0].getUserName())){//switch activity
                   return false;
               }
                boolean createAccountResult = UserManager.CreateAccount(urls[0], (urls[1].getUserName()));
                return createAccountResult;
            } catch (Exception e) {
                this.exception = e;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (!result) {
                AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
                alert.setTitle("UserName Already Exists");
                alert.setMessage("Enter a new username");
                alert.setPositiveButton("OK", null);
                alert.show();
                return;
            }

            startActivity(new Intent(v.getContext(), HomeActivity.class));
            overridePendingTransition(0, 0);

        }
    }

    public boolean anyNull(){
        if(user.getUserName() == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
            alert.setTitle("Empty Fields");
            alert.setMessage("Please Enter a Valid UserName");
            alert.setPositiveButton("OK", null);
            alert.show();
            return true;
        }
        else if(password == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
            alert.setTitle("Empty Fields");
            alert.setMessage("Please Enter a Password");
            alert.setPositiveButton("OK", null);
            alert.show();
            return true;
        }
        else if(user.getDOB().equals("")){
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
            alert.setTitle("Empty Fields");
            alert.setMessage("Please Enter Your Date of Birth");
            alert.setPositiveButton("OK", null);
            alert.show();
            return true;
        }
        else if(user.getEmail() == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
            alert.setTitle("Empty Fields");
            alert.setMessage("Please Enter a Email");
            alert.setPositiveButton("OK", null);
            alert.show();
            return true;
        }
        else if(user.getPhoneNumber() == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
            alert.setTitle("Empty Fields");
            alert.setMessage("Please Enter Your Phone Number");
            alert.setPositiveButton("OK", null);
            alert.show();
            return true;
        }
        else if(user.getAcademicFocus() == null) {
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
            alert.setTitle("Empty Fields");
            alert.setMessage("Please Enter a Academic Focus");
            alert.setPositiveButton("OK", null);
            alert.show();
            return true;
        }
        else if(user.getPersonalIntroduction() == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
            alert.setTitle("Empty Fields");
            alert.setMessage("Please Enter a Personal Introduction");
            alert.setPositiveButton("OK", null);
            alert.show();
            return true;
        }
        else if(user.getProfileImage() == null){
            AlertDialog.Builder alert = new AlertDialog.Builder(SignUpActivity.this);
            alert.setTitle("Empty Fields");
            alert.setMessage("Please Select an Image");
            alert.setPositiveButton("OK", null);
            alert.show();
            return true;
        }

        return false;
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

    // this function is triggered when user
    // selects the image from the imageChooser
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (resultCode == RESULT_OK) {

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
                    user.setProfileImageBytes(imageInByte);
                }
            }
        }
    }





    private void initDatePicker()
    {
        DatePickerDialog.OnDateSetListener dateSetListener = new DatePickerDialog.OnDateSetListener()
        {
            @Override
            public void onDateSet(android.widget.DatePicker datePicker, int year, int month, int day)
            {
                month = month + 1;
                String date = makeDateString(day, month, year);
                dateButton.setText(date);
            }
        };

        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        int day = cal.get(Calendar.DAY_OF_MONTH);

        int style = AlertDialog.THEME_HOLO_LIGHT;

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
        //datePickerDialog.getDatePicker().setMaxDate(System.currentTimeMillis());

    }

    private String makeDateString(int day, int month, int year)
    {

        String date = year+"-"+ (new DecimalFormat("00")).format(month)  + "-" + (new DecimalFormat("00")).format(day) ;
        if(user != null){
            user.setDOB(date);
        }
        return date;
    }

    private String getTodaysDate()
    {
        Calendar cal = Calendar.getInstance();
        int year = cal.get(Calendar.YEAR);
        int month = cal.get(Calendar.MONTH);
        month = month + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return makeDateString(day, month, year);
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }




}
