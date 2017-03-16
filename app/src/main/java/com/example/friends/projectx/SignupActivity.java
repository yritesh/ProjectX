package com.example.friends.projectx;

import android.content.Context;
import android.support.design.widget.TextInputLayout;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class SignupActivity extends AppCompatActivity {
    EditText name, email, mobile, pass, r_pass;
    Button signupButton;
    int temp;
    TextInputLayout tilName, tilEmail, tilMobile, tilPass, tilRpass;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);
        signupButton = (Button)findViewById(R.id.signupButton);
        tilName = (TextInputLayout) findViewById(R.id.tilName);
        tilEmail = (TextInputLayout) findViewById(R.id.tilEmail);
        tilMobile = (TextInputLayout) findViewById(R.id.tilMobile);
        tilPass = (TextInputLayout) findViewById(R.id.tilPassword);
        tilRpass = (TextInputLayout) findViewById(R.id.tilRpass);
        name = (EditText)findViewById(R.id.editText3);
        email = (EditText)findViewById(R.id.editText4);
        mobile = (EditText)findViewById(R.id.editText5);
        pass = (EditText)findViewById(R.id.editText7);
        r_pass = (EditText)findViewById(R.id.editText8);

        name.setOnKeyListener(
                new View.OnKeyListener() {
                    @Override
                    public boolean onKey(View v, int keyCode, KeyEvent event) {
                        checkOtherReq(String.valueOf(v.getId()));
                        return false;
                    }
                }
        );
      name.setOnFocusChangeListener(
              new View.OnFocusChangeListener() {
                  @Override
                  public void onFocusChange(View v, boolean hasFocus) {
                      checkOtherReq(String.valueOf(v.getId()));
                  }
              }
      );
        signupButton.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        onSignupButton(v);
                    }
                }
        );
    }
    public void checkOtherReq(String type){
        type = getResources().getResourceEntryName(Integer.parseInt(type));
        switch (type){
            case "editText3":
                if(name.getText().toString().length()>5)
                    tilName.setError(null);
                else tilName.setError("Name must be atleast 6 characters long");
                break;
            case "editText4":if(email.getText().toString().length()>0)
            {
                if(email.getText().toString().matches("[a-zA-Z0-9._-]+@[a-z]+\\\\.+[a-z]+"))
                    tilEmail.setError(null);
                else tilEmail.setError("Please enter a valid email id");
            }
            else tilEmail.setError("Email field can't be left blank");
                break;

            case "mobile":if(type.matches("[0-9]{10}"))
                tilName.setError(null);
            else tilName.setError("Mobile ");
                break;
            case "pass":if(type.length()<6)
                tilName.setError(null);
            else tilName.setError("Name must be atleast 6 characters long");
                break;
        }
    }

    public void onSignupButton(View v){

        if(name.getText().toString().isEmpty()) {
            tilName.setError("You need to enter a name");
        }

        else if(email.getText().toString().isEmpty()) {
            tilEmail.setError("You need to enter a Email id");
        }
        else if(mobile.getText().toString().isEmpty()) {
            tilMobile.setError("You need to enter your mobile number");

        }
        else if(pass.getText().toString().isEmpty()) {
            tilPass.setError("You need to enter a password");

        }
        else if(r_pass.getText().toString().isEmpty()) {
            tilRpass.setError("You need to re-enter your password");

        }

    }
}