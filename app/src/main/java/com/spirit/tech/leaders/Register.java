package com.spirit.tech.leaders;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class Register extends AppCompatActivity {

    private View progressBar;
    private TextView tvLoad;
    private View linearLayout;

    ImageView ivRegistered,ivRegister;
    EditText etName,etPhone,etEmail,etDate,etPassword,etCnfpswd;
    Button  btnRegister;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        actionBar=this.getSupportActionBar(); /*Adding action bar*/
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" Register Yourself");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        progressBar=findViewById(R.id.progressBar);
        tvLoad=findViewById(R.id.tvLoad);
        linearLayout=findViewById(R.id.register);

        ivRegistered=findViewById(R.id.ivRegistered);
        ivRegister=findViewById(R.id.ivRegister);
        etName=findViewById(R.id.etName);
        etPhone=findViewById(R.id.etPhone);
        etEmail=findViewById(R.id.etEmail);
        etDate=findViewById(R.id.etDate);
        etPassword=findViewById(R.id.etPassword);
        etCnfpswd=findViewById(R.id.etCnfpswd);
        btnRegister=findViewById(R.id.btnRegister);

        btnRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(etName.getText().toString().isEmpty() || etPhone.getText().toString().isEmpty()
                        ||etEmail.getText().toString().isEmpty() ||etDate.getText().toString().isEmpty()
                        ||etPassword.getText().toString().isEmpty() ||etCnfpswd.getText().toString().isEmpty() )
                {
                    Toast.makeText(Register.this, "Please Fill all Fields..", Toast.LENGTH_SHORT).show();
                }
                else
                {
                    if(etPassword.getText().toString().equals(etCnfpswd.getText().toString()))
                    {

                        String Name=etName.getText().toString();
                        String Phone=etPhone.getText().toString();
                        String Date=etDate.getText().toString();
                        String Email=etEmail.getText().toString();
                        String Password=etPassword.getText().toString();

                        BackendlessUser user=new BackendlessUser();
                        user.setPassword(Password);
                        user.setEmail(Email);
                        user.setProperty("name",Name);
                        user.setProperty("DOB",Date);
                        user.setProperty("phone",Phone);

                        showProgressBar(true);
                        tvLoad.setText("Registering User ..Please Wait....");

                        Backendless.UserService.register(user, new AsyncCallback<BackendlessUser>() {
                            @Override
                            public void handleResponse(BackendlessUser response) {


                                Toast.makeText(Register.this, "Registered Successfully", Toast.LENGTH_SHORT).show();

                                Register.this.finish();
                            }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(Register.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                                showProgressBar(false);
                            }
                        });


                    }
                    else
                    {
                        Toast.makeText(Register.this, " Passwords do not Match..Please Make sure..", Toast.LENGTH_SHORT).show();
                    }
                }
            }
        });
    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showProgressBar(final boolean show) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB_MR2) {
            int shortAnimeTime = getResources().getInteger(android.R.integer.config_shortAnimTime);

            linearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
            linearLayout.animate().setDuration(shortAnimeTime).alpha(show ? 0 : 1).
                    setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    linearLayout.setVisibility(show ? View.GONE : View.VISIBLE);
                }
            });

            progressBar.setVisibility(show ? View.VISIBLE : View.GONE);
            progressBar.animate().setDuration(shortAnimeTime).alpha(show ? 1 : 0)
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
            tvLoad.setVisibility(show ? View.GONE : View.VISIBLE);
        }

    }
}
