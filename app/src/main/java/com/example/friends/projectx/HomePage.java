package com.example.friends.projectx;

import android.content.Intent;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

public class HomePage extends AppCompatActivity {
    EditText usernameEt, passwordEt;
    Button bt;
    TextView signup;
    ImageView img1,img2;
    public int LCheck = 0;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
        usernameEt = (EditText)findViewById(R.id.editText);
        passwordEt = (EditText)findViewById(R.id.editText2);
        img1 = (ImageView)findViewById(R.id.imageView);
        img2 = (ImageView)findViewById(R.id.imageView2);
        bt = (Button)findViewById(R.id.button);
        signup = (TextView)findViewById(R.id.signUpLink);
        signup.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnSignup(v);
                    }
                }
        );
        bt.setOnClickListener(
                new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        OnLogin(v);

                    }
                }
        );
    }

    private void OnSignup(View v) {
        Intent i = new Intent(HomePage.this, SignupActivity.class);
        this.startActivity(i);
    }

    public void OnLogin(View v){

        String username= usernameEt.getText().toString();
        String password = passwordEt.getText().toString();
        if(username.isEmpty()){
                    img1.setVisibility(View.VISIBLE);
                    new Handler().postDelayed(new Runnable() {
                        public void run() {
                        img1.setVisibility(View.GONE);
                        }
                    }, 3000);
        }
        else {
            if(password.isEmpty()){
                img2.setVisibility(View.VISIBLE);
                new Handler().postDelayed(new Runnable() {
                    public void run() {
                        img2.setVisibility(View.GONE);
                    }
                }, 3000);
            }
            else{
            String type = "login";
            BackgroundWorker backgroundWorker = new BackgroundWorker(this);
            backgroundWorker.execute(username, password, type);
            }
        }
    }


}
