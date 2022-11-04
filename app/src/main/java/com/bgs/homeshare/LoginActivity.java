package com.bgs.homeshare;

import androidx.appcompat.app.AppCompatActivity;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;

import com.bgs.homeshare.Managers.UserManager;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
    }

    public void onBackClick(View v) {
        this.startActivity(new Intent(v.getContext(), MainActivity.class));
        this.overridePendingTransition(0, 0);
    }

    public void onLoginClick(View v) {
        EditText usernameTextBox = (EditText) findViewById(R.id.UserNameTextBox);
        String username = usernameTextBox.getText().toString();
        EditText passwordTextBox = (EditText) findViewById(R.id.PasswordTextBox);
        String password = passwordTextBox.getText().toString();

        if (username.length() == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setTitle("Invalid UserName");
            alert.setMessage("You must enter a username");
            alert.setPositiveButton("OK", null);
            alert.show();
            return;
        }
        if (password.length() == 0) {
            AlertDialog.Builder alert = new AlertDialog.Builder(LoginActivity.this);
            alert.setTitle("Invalid Password");
            alert.setMessage("You must enter a password");
            alert.setPositiveButton("OK", null);
            alert.show();
            return;
        }

        /*boolean loginResult = UserManager.Login(username, password);

        if (!loginResult) {
            AlertDialog.Builder alert3 = new AlertDialog.Builder(LoginActivity.this);
            alert3.setTitle("Invalid Credentials");
            alert3.setMessage("Incorrect Username/Password Entered");
            alert3.setPositiveButton("OK", null);
            alert3.show();
            return;
        }

        this.startActivity(new Intent(v.getContext(), TempActivity.class));
        this.overridePendingTransition(0, 0);*/
        CheckLoginTask c = new CheckLoginTask();
        c.v = v;
        c.execute(username, password);
    }

    class CheckLoginTask extends AsyncTask<String, Void, Boolean> {
        public View v;
        private Exception exception;

        protected Boolean doInBackground(String... urls) {
            try {
                boolean loginResult = UserManager.Login(urls[0], urls[1]);
                return loginResult;
            } catch (Exception e) {
                this.exception = e;
            }
            return false;
        }

        protected void onPostExecute(Boolean result) {
            if (!result) {
                AlertDialog.Builder alert3 = new AlertDialog.Builder(LoginActivity.this);
                alert3.setTitle("Invalid Credentials");
                alert3.setMessage("Incorrect Username/Password Entered");
                alert3.setPositiveButton("OK", null);
                alert3.show();
                return;
            }

            startActivity(new Intent(v.getContext(), FeedActivity.class));
            overridePendingTransition(0, 0);
        }
    }
}