package com.spirit.tech.leaders;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.Intent;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.local.UserIdStorageFactory;

public class Login extends AppCompatActivity {

    private View progressBar;
    private View linearLayout;
    private TextView tvLoad;


     EditText etEmail,etPassword;
     Button btnLogin,btnRegister;
     TextView tvReset;
     ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        actionBar=this.getSupportActionBar(); /*Adding action bar*/
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" Login/Register");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        progressBar=findViewById(R.id.progressBar);
        tvLoad=findViewById(R.id.tvLoad);
        linearLayout=findViewById(R.id.linearlayout);

        etEmail=findViewById(R.id.etEmail);
        etPassword=findViewById(R.id.etPassword);
        btnLogin=findViewById(R.id.btnLogin);
        btnRegister=findViewById(R.id.btnRegister);
        tvReset=findViewById(R.id.tvReset);


        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etEmail.getText().toString().isEmpty() || etPassword.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, "Please fill all fields..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String Email=etEmail.getText().toString();
                    String Password=etPassword.getText().toString();

                    showProgressBar(true);
                    tvLoad.setText("Processing credentials..");

                    Backendless.UserService.login(Email, Password, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            ApplicationClass.user=response;

                            Toast.makeText(Login.this,  "Successfully Logged In  ", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,MainActivity.class));
                            Login.this.finish();

                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgressBar(false);
                            Toast.makeText(Login.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    },true);
                }

            }
        });

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(Login.this,Register.class));

            }
        });

        tvReset.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if(etEmail.getText().toString().isEmpty())
                {
                    Toast.makeText(Login.this, "Please fill Email field..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    String email=etEmail.getText().toString();
                    showProgressBar(true);
                    tvLoad.setText("Processing Your Request...");

                    Backendless.UserService.restorePassword(email, new AsyncCallback<Void>() {
                        @Override
                        public void handleResponse(Void response) {
                            showProgressBar(false);
                            Toast.makeText(Login.this, "Password Reset insvtructions has been sent to your Email..", Toast.LENGTH_SHORT).show();
                        }

                        @Override
                        public void handleFault(BackendlessFault fault) {
                            showProgressBar(false);
                            Toast.makeText(Login.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();

                        }
                    });
                }

            }
        });

        if(ApplicationClass.logout==false){
        showProgressBar(true);
        tvLoad.setText("Processing ..Please wait...");
        Backendless.UserService.isValidLogin(new AsyncCallback<Boolean>() {
            @Override
            public void handleResponse(Boolean response) {
                if(response)
                { tvLoad.setText("Logging In ...please wait...");
                    String userObjectId=UserIdStorageFactory.instance().getStorage().get();

                    Backendless.Data.of(BackendlessUser.class).findById(userObjectId, new AsyncCallback<BackendlessUser>() {
                        @Override
                        public void handleResponse(BackendlessUser response) {
                            ApplicationClass.user=response;
                            Toast.makeText(Login.this, "Successfully Logged In", Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(Login.this,MainActivity.class));
                            Login.this.finish();
                            }
                        @Override
                        public void handleFault(BackendlessFault fault) {
                            Toast.makeText(Login.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                            showProgressBar(false);
                            }
                    });
                }
                else
                {
                    showProgressBar(false);
                }
            }

            @Override
            public void handleFault(BackendlessFault fault) {
                showProgressBar(false);
                Toast.makeText(Login.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();

            }
        });}
    }


    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgressBar(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimeTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            linearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            linearLayout.animate().setDuration(shortAnimeTime).alpha(show ? 0 : 1)
                    .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    linearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimeTime).alpha(show ? 1: 0)
                    .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            tvLoad.animate().setDuration(shortAnimeTime).alpha(show ? 1 : 0)
                    .setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
                }
            });
        }
        else {
            tvLoad.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            linearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }
}
