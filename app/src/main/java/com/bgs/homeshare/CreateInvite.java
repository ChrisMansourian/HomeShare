package com.bgs.homeshare;

import android.app.*;
import android.content.*;
import android.icu.text.*;
import android.os.*;
import android.text.*;
import android.view.*;
import android.widget.*;
import androidx.annotation.*;
import androidx.appcompat.app.AppCompatActivity;
import com.bgs.homeshare.Managers.*;
import com.bgs.homeshare.Managers.UserManager;
import com.bgs.homeshare.Models.*;
import java.util.*;

public class CreateInvite extends AppCompatActivity {

    private Invitation createInvite;
    private Button dateButton;
    private DatePickerDialog datePickerDialog;
    private EditText QuestionInput;
    private ImageView QuestionAdd;
    private ListView DisplayQuestions;
    private ArrayList<String> Questions;
    private ListViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_invite);

        //for date picker
        initDatePicker();
        dateButton = findViewById(R.id.deadlinePicker);
        dateButton.setText(getTodaysDate());

        Questions = new ArrayList<>();
        PropertyUtilities utils = new PropertyUtilities(false,false,false,false,false,false);
        Property property = new Property(-1,null,null,null,null,"USA",0,0,0,utils,0,0,0);
        createInvite = new Invitation(-1, UserManager.LoggedInUser.getUserId(), property,null,null,null,0,Questions);

        EditText streetAddress1 = findViewById(R.id.editTextTextPostalAddress);
        streetAddress1.addTextChangedListener(new TextWatcher() {
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
                createInvite.property.setStreetAddress1(insert);
            }
        });

        EditText streetAddress2 = findViewById(R.id.editTextTextPostalAddress2);
        streetAddress2.addTextChangedListener(new TextWatcher() {
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
                createInvite.property.setStreetAddress2(insert);
            }
        });

        EditText city = findViewById(R.id.editTextCity);
        city.addTextChangedListener(new TextWatcher() {
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
                createInvite.property.setCity(insert);
            }
        });

        //initialize State Spinner
        Spinner spinnerStates = findViewById(R.id.spinnerStateCreateInvite);
        ArrayAdapter<CharSequence> statesAdapter = ArrayAdapter.createFromResource(this, R.array.statesArray, android.R.layout.simple_spinner_item);
        statesAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerStates.setAdapter(statesAdapter);

        //initialize country spinner

        Spinner spinnerCountry = findViewById(R.id.spinnerCountryCreateInvite);
        ArrayAdapter<CharSequence> countryAdapter = ArrayAdapter.createFromResource(this, R.array.countriesArray, android.R.layout.simple_spinner_item);
        countryAdapter.setDropDownViewResource(android.R.layout.simple_spinner_item);
        spinnerCountry.setAdapter(countryAdapter);

        //spinner state listener
        spinnerStates.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                createInvite.property.setState(parentView.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        //spinner country
        spinnerCountry.setOnItemSelectedListener(new AdapterView.OnItemSelectedListener() {
            @Override
            public void onItemSelected(AdapterView<?> parentView, View selectedItemView, int position, long id) {
                // your code here
                createInvite.property.setCountry(parentView.getItemAtPosition(position).toString());
            }
            @Override
            public void onNothingSelected(AdapterView<?> parentView) {
                // your code here
            }
        });

        EditText rent = findViewById(R.id.editTextRentCreateInvite);
        rent.addTextChangedListener(new TextWatcher() {
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
                    insert = "0";
                }
                createInvite.property.setRent(Integer.parseInt(insert));
            }
        });

        EditText roomateNumber = findViewById(R.id.editTextNumberOfRoomatesCreateInvite);
        roomateNumber.addTextChangedListener(new TextWatcher() {
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
                    insert = "0";
                }
                createInvite.property.setMaximumCapacity(Integer.parseInt(insert));
            }
        });

        EditText squarefeet = findViewById(R.id.editTextSquareFeetCreateInvite);
        squarefeet.addTextChangedListener(new TextWatcher() {
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
                    insert = "0";
                }
                createInvite.property.setSquareFeet(Integer.parseInt(insert));
            }
        });

        EditText bathrooms = findViewById(R.id.editTextBathrroms);
        bathrooms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String insert = s.toString();
                if(insert.equals("")){
                    insert = "0";
                }
                createInvite.property.setNumOfBathrooms(Double.parseDouble(insert));
            }
        });


        EditText bedrooms = findViewById(R.id.editTextBedrooms);
        bedrooms.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
            }

            @Override
            public void afterTextChanged(Editable s) {
                String insert = s.toString();
                if(insert.equals("")){
                    insert = "0";
                }
                createInvite.property.setNumOfBedrooms(Integer.parseInt(insert));
            }
        });

        CheckBox poolCheck = findViewById(R.id.poolCheckCreateInvite);
        CheckBox acCheck =  findViewById(R.id.acCheckCreateInvite);
        CheckBox laundryCheck =  findViewById(R.id.laundryCheckCreateInvite);
        CheckBox dishwasherCheck =  findViewById(R.id.dishwasherCheckCreateInvite);
        CheckBox balconyCheck =  findViewById(R.id.balconyCheckCreateInvite);
        CheckBox fireplaceCheck =  findViewById(R.id.fireplaceCheckCreateInvite);

        poolCheck.setOnCheckedChangeListener((buttonView, isChecked) -> createInvite.property.utilities.setPool(isChecked));

        acCheck.setOnCheckedChangeListener((buttonView, isChecked) -> createInvite.property.utilities.setAC(isChecked));

        laundryCheck.setOnCheckedChangeListener((buttonView, isChecked) -> createInvite.property.utilities.setLaundry(isChecked));

        dishwasherCheck.setOnCheckedChangeListener((buttonView, isChecked) -> createInvite.property.utilities.setDishwasher(isChecked));

        balconyCheck.setOnCheckedChangeListener((buttonView, isChecked) -> createInvite.property.utilities.setBalcony(isChecked));

        fireplaceCheck.setOnCheckedChangeListener((buttonView, isChecked) -> createInvite.property.utilities.setFireplace(isChecked));

        //question implementation
        DisplayQuestions = findViewById(R.id.listOfQuestionsCreateInvite);

        DisplayQuestions.setLongClickable(true);


        QuestionInput = findViewById(R.id.inputQuestion);
        QuestionAdd = findViewById(R.id.addQuestionImg);
        adapter = new ListViewAdapter(getApplicationContext(), Questions);
        DisplayQuestions.setAdapter(adapter);

        QuestionAdd.setOnClickListener(view -> {
            String text = QuestionInput.getText().toString();
            if(text == null||text.length() ==0){
                AlertDialog.Builder alert = new AlertDialog.Builder(CreateInvite.this);
                alert.setTitle("Enter Question");
                alert.setMessage("Not a valid question please type a question before adding");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
            else{
                addItem(text);
                QuestionInput.setText("");
            }
        });

        //create Invite implementation
        Button creatingInvite = findViewById(R.id.createInviteButton);
        creatingInvite.setOnClickListener(v->{
            if(!anyNull()) {
                CreateInvite.CreateInviteTask c = new CreateInvite.CreateInviteTask();
                c.v = v;
                c.execute(createInvite);
            }
            else{//ask for user to fill in all fields
                AlertDialog.Builder alert = new AlertDialog.Builder(CreateInvite.this);
                alert.setTitle("Empty Fields");
                alert.setMessage("Some mandatory fields are still empty");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
        });
    }

    public void addItem(String item){
        Questions.add(item);
        DisplayQuestions.setAdapter(adapter);
    }

    class CreateInviteTask extends AsyncTask<Invitation, Void, Boolean> {
        public View v;
        private Exception exception;


        protected Boolean doInBackground(Invitation... urls) {
            try {
                return InvitationManager.createAnInvitation(urls[0]);
            } catch (Exception e) {
                this.exception = e;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (!result) {
                AlertDialog.Builder alert = new AlertDialog.Builder(CreateInvite.this);
                alert.setTitle("Error");
                alert.setMessage("Unable to create invite, please try again");
                alert.setPositiveButton("OK", null);
                alert.show();
            }
            else{
                Intent i = new Intent(v.getContext(), HomeActivity.class);
                i.putExtra("frgToLoad", 2);
                startActivity(i);
                overridePendingTransition(0, 0);
            }
        }
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

        datePickerDialog = new DatePickerDialog(this, style, dateSetListener, year, month, day);
    }

    private String makeDateString(int day, int month, int year)
    {
        String date = year+"-"+ (new DecimalFormat("00")).format(month)  + "-" + (new DecimalFormat("00")).format(day) ;
        SimpleDateFormat formatter = new SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH);
        if(createInvite != null){
            try{
                createInvite.setDateOfDeadline(formatter.parse(date));
            }
            catch (Exception e){
                e.printStackTrace();
            }
        }
        return date;
    }

    public void openDatePicker(View view)
    {
        datePickerDialog.show();
    }


    private boolean anyNull(){
        if(createInvite.property.getStreetAddress1() == null){
            return true;
        }
        if(createInvite.property.getCity() == null){
            return true;
        }
        if(createInvite.property.getState() == null){
            return true;
        }
        if(createInvite.property.getRent() == 0){
            return true;
        }
        if(createInvite.property.getMaximumCapacity() == 0){
            return true;
        }
        if(createInvite.property.getSquareFeet() == 0){
            return true;
        }
        if(createInvite.property.getNumOfBathrooms() == 0){
            return true;
        }
        if(createInvite.property.getNumOfBedrooms() == 0){
            return true;
        }
        if(createInvite.getDateOfDeadline() == null){
            return true;
        }
        return (createInvite.getQuestions().size() < 2);
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

    public void removeItem(int i) {
        Questions.remove(i);
        DisplayQuestions.setAdapter(adapter);
    }

    public void onBackClick(View v) {
        this.startActivity(new Intent(v.getContext(), HomeActivity.class));
        this.overridePendingTransition(0, 0);
    }

    class ListViewAdapter extends ArrayAdapter<String> {
        ArrayList<String> list;
        Context context;

        public ListViewAdapter(Context context, ArrayList<String> items) {
            super(context, R.layout.questions_add_list, items);
            this.context = context;
            list = items;
        }

        @NonNull
        @Override
        public View getView(int position, @Nullable View convertView, @NonNull ViewGroup parent) {
            if (convertView == null) {
                LayoutInflater mInflater = (LayoutInflater) context.getSystemService(Activity.LAYOUT_INFLATER_SERVICE);
                convertView = mInflater.inflate(R.layout.questions_add_list, null);
                TextView name = convertView.findViewById(R.id.name);
                ImageView remove = convertView.findViewById(R.id.remove);
                TextView number = convertView.findViewById(R.id.number);
                number.setText(position + 1 + ".");
                name.setText(list.get(position));
                remove.setOnClickListener(view -> removeItem(position));
            }
            return convertView;
        }
    }
}

