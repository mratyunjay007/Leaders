package com.spirit.tech.leaders;

import android.animation.Animator;
import android.animation.AnimatorListenerAdapter;
import android.annotation.TargetApi;
import android.content.DialogInterface;
import android.os.Build;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.ScrollView;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;
import com.backendless.persistence.DataQueryBuilder;

import java.util.ArrayList;
import java.util.List;

import static android.view.View.GONE;

public class actionbar extends AppCompatActivity {

    TextView tvPurpose,tvrequest;
    EditText etname,etreason;
    Button btnsubmit;
    ImageView ivadd,ivedit,ivdelete;
    ScrollView svpurpose,svrequest;
    private View progressbar;
    private View actionlayout;
    private TextView tvLoad;
    boolean flag=false,check=false;

    String purpose, caller;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_actionbar);

        tvPurpose = findViewById(R.id.tvPurpose);
        tvrequest=findViewById(R.id.tvRequest);
        etname=findViewById(R.id.etName);
        etreason=findViewById(R.id.etReason);
        btnsubmit=findViewById(R.id.btnSubmit);
        ivadd=findViewById(R.id.ivadd);
        ivedit=findViewById(R.id.ivedit);
        ivdelete=findViewById(R.id.ivdelete);


        svpurpose=findViewById(R.id.purpose);
        svrequest=findViewById(R.id.request);

        progressbar=findViewById(R.id.progressBar2);
        tvLoad=findViewById(R.id.tvLoad);
        actionlayout=findViewById(R.id.actionlayout);

        caller = getIntent().getStringExtra("caller");



        if (caller.equals("purpose")) {

            svrequest.setVisibility(GONE);
            svpurpose.setVisibility(View.VISIBLE);

            actionBar = this.getSupportActionBar();
            actionBar.setIcon(R.mipmap.logoreal);
            actionBar.setTitle("Purpose");
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

            purpose = "Why this Application was needed ?"+"\n"+" This application is designed to guide the people through different phases of life, when they are in trouble " +
                    "or do not know what to do, and what will happen with them in near future. We are trying to give you guidance" +
                    " through great leaders life and hardships, that they experience in their life. And after all that, how they become" +
                    " Great and successful, as now and always they will rule on heart and mind of people."+"\n"+"-Leaders Team";

            tvPurpose.setText(purpose);
        }
        else if(caller.equals("request")) {
            svpurpose.setVisibility(GONE);
            svrequest.setVisibility(View.VISIBLE);

            actionBar = this.getSupportActionBar();
            actionBar.setIcon(R.mipmap.logoreal);
            actionBar.setTitle(" Request New Story");
            actionBar.setDisplayUseLogoEnabled(true);
            actionBar.setDisplayShowHomeEnabled(true);

            etname.setVisibility(GONE);
            etreason.setVisibility(GONE);
            btnsubmit.setVisibility(GONE);
            ivadd.setVisibility(GONE);
            ivedit.setVisibility(GONE);
            ivdelete.setVisibility(GONE);
            tvrequest.setText("\"Make Request for Story of your Favourite Leader\" \n");
            showprogressbar(true);
            readfromserver();

            ivadd.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    flag = !flag;
                    if (flag) {
                        btnsubmit.setText("SUBMIT");
                        etname.setVisibility(View.VISIBLE);
                        etreason.setVisibility(View.VISIBLE);
                        btnsubmit.setVisibility(View.VISIBLE);

                    } else {
                        etname.setVisibility(GONE);
                        etreason.setVisibility(GONE);
                        btnsubmit.setVisibility(GONE);

                    }
                }
            });

            btnsubmit.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    if (etname.getText().toString().isEmpty() || etreason.getText().toString().isEmpty()) {
                        Toast.makeText(actionbar.this, "Please fill all fields before submition ", Toast.LENGTH_SHORT).show();
                    } else {
                        if(!check){
                        Request request=new Request();
                        request.setUseremail(ApplicationClass.user.getEmail());
                        request.setLeadername(etname.getText().toString());
                        request.setReason(etreason.getText().toString());

                        showprogressbar(true);
                        tvLoad.setText("Submitting Request to server please wait... ");

                        Backendless.Persistence.save(request, new AsyncCallback<Request>() {
                            @Override
                            public void handleResponse(Request response) {
                                Toast.makeText(actionbar.this, "Your Request has been Submitted successfully", Toast.LENGTH_SHORT).show();
                                etname.setVisibility(GONE);
                                etreason.setVisibility(GONE);
                                btnsubmit.setVisibility(GONE);
                                ivadd.setVisibility(GONE);
                               readfromserver();
                                }

                            @Override
                            public void handleFault(BackendlessFault fault) {
                                Toast.makeText(actionbar.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                showprogressbar(false);
                            }
                        });
                    }
                    else if(check)
                        {
                            check=false;
                            ApplicationClass.requests.get(0).setLeadername(etname.getText().toString());
                            ApplicationClass.requests.get(0).setReason(etreason.getText().toString());

                            showprogressbar(true);
                            tvLoad.setText("Updating changes please wait... ");

                            Backendless.Persistence.save(ApplicationClass.requests.get(0), new AsyncCallback<Request>() {
                                @Override
                                public void handleResponse(Request response) {
                                    Toast.makeText(actionbar.this, "Your changes has been updated successfully.", Toast.LENGTH_SHORT).show();
                                    etname.setVisibility(GONE);
                                    etreason.setVisibility(GONE);
                                    btnsubmit.setVisibility(GONE);

                                    showprogressbar(false);
                                    tvrequest.setText("YOUR REQUEST: "+"\n\nLeader Name: "+ApplicationClass.requests.get(0).getLeadername()+
                                            "\n\nWhy this leader ? "+
                                            ApplicationClass.requests.get(0).getReason()+"\n\n");
                                }

                                @Override
                                public void handleFault(BackendlessFault fault) {
                                    Toast.makeText(actionbar.this, "Error: " + fault.getMessage(), Toast.LENGTH_SHORT).show();
                                    showprogressbar(false);
                                }
                            });
                        }
                    }

                }
            });
        }

        ivedit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                flag = !flag;
                if (!flag) {
                    check=true;
                    btnsubmit.setText("EDIT");
                    etname.setVisibility(View.VISIBLE);
                    etreason.setVisibility(View.VISIBLE);
                    btnsubmit.setVisibility(View.VISIBLE);


                } else {
                    etname.setVisibility(GONE);
                    etreason.setVisibility(GONE);
                    btnsubmit.setVisibility(GONE);
                    check=false;
                }

            }
        });


            ivdelete.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                   AlertDialog.Builder builder=new AlertDialog.Builder(actionbar.this);
                   builder.setMessage("Do you want to delete your Request? Are you sure!");
                   builder.setTitle("Delete Request!");
                   builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                           showprogressbar(true);
                           tvLoad.setText("Deleting your Request of new Story from server...");

                           Backendless.Persistence.of(Request.class).remove(ApplicationClass.requests.get(0),
                                   new AsyncCallback<Long>() {
                               @Override
                               public void handleResponse(Long response) {
                                   Toast.makeText(actionbar.this, "Deleted Successfully", Toast.LENGTH_SHORT).show();
                                   ApplicationClass.requests.clear();
                                   check=false;

                                   ivdelete.setVisibility(GONE);
                                   ivedit.setVisibility(View.GONE);
                                   etname.setVisibility(GONE);
                                   etreason.setVisibility(GONE);
                                   btnsubmit.setVisibility(GONE);
                                   etname.setText(null);
                                   etreason.setText(null);
                                   tvrequest.setText("\"Make Request for Story of your Favourite Leader\" \n");
                                   ivadd.setVisibility(View.VISIBLE);
                                   showprogressbar(false);
                               }

                               @Override
                               public void handleFault(BackendlessFault fault) {
                                   Toast.makeText(actionbar.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                                    showprogressbar(false);
                               }
                           });

                       }
                   });
                   builder.setNegativeButton("NO", new DialogInterface.OnClickListener() {
                       @Override
                       public void onClick(DialogInterface dialog, int which) {

                       }
                   });
                   builder.show();

                }
            });



        }


    public void readfromserver()
    {
        String whereclause="useremail='"+ApplicationClass.user.getEmail()+"'";

        DataQueryBuilder queryBuilder=DataQueryBuilder.create();
        queryBuilder.setWhereClause(whereclause);


        tvLoad.setText("Loading data from server...");
        Backendless.Persistence.of(Request.class).find(queryBuilder, new AsyncCallback<List<Request>>() {
            @Override
            public void handleResponse(List<Request> response) {
                ApplicationClass.requests=response;

                if(ApplicationClass.requests.isEmpty())
                {

                    ivadd.setVisibility(View.VISIBLE);
                    showprogressbar(false);
                }
                else
                {
                    tvrequest.setText("YOUR REQUEST: "+"\n\nLeader Name: "+ApplicationClass.requests.get(0).getLeadername()+
                            "\n\nWhy this leader ? "+
                    ApplicationClass.requests.get(0).getReason()+"\n\n");

                    ivedit.setVisibility(View.VISIBLE);
                    ivdelete.setVisibility(View.VISIBLE);
                    etname.setText(ApplicationClass.requests.get(0).getLeadername());
                    etreason.setText(ApplicationClass.requests.get(0).getReason());
                    showprogressbar(false);
                }
                }

            @Override
            public void handleFault(BackendlessFault fault) {
                Toast.makeText(actionbar.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                showprogressbar(false);

            }
        });

    }

    @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)
    public void showprogressbar(final boolean show)
    {
        if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR2)
        {
            int shortAnimtime=getResources().getInteger(android.R.integer.config_shortAnimTime);

            actionlayout.setVisibility(show?View.GONE:View.VISIBLE);
            actionlayout.animate().setDuration(shortAnimtime).alpha(show?0:1).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    actionlayout.setVisibility(show?GONE:View.VISIBLE);
                }
            });

            progressbar.setVisibility(show?View.VISIBLE:View.GONE);
            progressbar.animate().setDuration(shortAnimtime).alpha(show?1:0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {
                    progressbar.setVisibility(show?View.VISIBLE:View.GONE);

                }
            });


            tvLoad.setVisibility(show?View.VISIBLE:View.GONE);
            tvLoad.animate().setDuration(shortAnimtime).alpha(show?1:0).setListener(new AnimatorListenerAdapter() {
                @Override
                public void onAnimationEnd(Animator animation) {

                    tvLoad.setVisibility(show?View.VISIBLE:View.GONE);
                }
            });

        }
        else
        {
            progressbar.setVisibility(show? View.VISIBLE:GONE);
            tvLoad.setVisibility(show?View.VISIBLE:GONE);
            actionlayout.setVisibility(show?GONE:View.VISIBLE);
        }

    }
}