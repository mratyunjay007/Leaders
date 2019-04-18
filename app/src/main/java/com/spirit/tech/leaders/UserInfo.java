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
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class UserInfo extends AppCompatActivity {

    TextView tvName,tvEmail,tvFavlist,tvBookmark,tvShare,tvRequest,tvKnowabout,tvLogout;
    View progressbar,linearlayout;
    TextView tvLoad;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_user_info);

        actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" Profile");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        progressbar=findViewById(R.id.progressBar);
        linearlayout=findViewById(R.id.linearlayout);
        tvLoad=findViewById(R.id.tvLoad);

        tvName=findViewById(R.id.tvName);
        tvEmail=findViewById(R.id.tvEmail);
        tvFavlist=findViewById(R.id.tvFavlist);
        tvBookmark=findViewById(R.id.tvbookmark);
        tvShare=findViewById(R.id.tvShare);
        tvRequest=findViewById(R.id.tvRequest);
        tvKnowabout=findViewById(R.id.tvKnowabout);
        tvLogout=findViewById(R.id.tvLogout);

        tvName.setText((CharSequence) ApplicationClass.user.getProperty("name"));
        tvEmail.setText(ApplicationClass.user.getEmail());

        tvFavlist.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(UserInfo.this,MyFavourite.class));


            }
        });

        tvBookmark.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Toast.makeText(UserInfo.this, "This feature will be added soon", Toast.LENGTH_SHORT).show();


            }
        });

        tvShare.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent();
                intent.setAction(Intent.ACTION_SEND);
                intent.putExtra(Intent.EXTRA_TEXT,"DownLoad Leaders App which will show you solution to" +
                        " your problem through guidance of great leaders from PlayStore");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent,"Share with Friends.."));

            }
        });

        tvRequest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent request=new Intent(UserInfo.this,actionbar.class);
                request.putExtra("caller","request");
                startActivity(request);

            }
        });

        tvKnowabout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent action=new Intent(UserInfo.this,actionbar.class);
                action.putExtra("caller","purpose");
                startActivity(action);

            }
        });

        tvLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                showprogressbar(true);
                tvLoad.setText("Processing..please wait...");

                Backendless.UserService.logout(new AsyncCallback<Void>() {
                    @Override
                    public void handleResponse(Void response) {

                        ApplicationClass.logout=true;
                        Toast.makeText(UserInfo.this, "Successfullly Logged Out", Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(UserInfo.this, Login.class));
                        showprogressbar(false);
                        setResult(RESULT_OK);
                        UserInfo.this.finish();
                    }

                    @Override
                    public void handleFault(BackendlessFault fault) {
                        Toast.makeText(UserInfo.this, "Error: "+fault.getMessage(), Toast.LENGTH_SHORT).show();
                    }
                });
            }
        });
    }

  @TargetApi(Build.VERSION_CODES.HONEYCOMB_MR2)

    public void showprogressbar(final boolean show)
  {
      if(Build.VERSION.SDK_INT>=Build.VERSION_CODES.HONEYCOMB_MR2)
      {
          int shortAnimetime=getResources().getInteger(android.R.integer.config_shortAnimTime);

          linearlayout.setVisibility(show?View.GONE:View.VISIBLE);
          linearlayout.animate().setDuration(shortAnimetime).alpha(show?0:1).setListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                  linearlayout.setVisibility(show?View.GONE:View.VISIBLE);
              }
          });

          progressbar.setVisibility(show?View.VISIBLE:View.GONE);
          progressbar.animate().setDuration(shortAnimetime).alpha(show?1:0).setListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                  progressbar.setVisibility(show?View.VISIBLE:View.GONE);
              }
          });

          tvLoad.setVisibility(show?View.VISIBLE:View.GONE);
          tvLoad.animate().setDuration(shortAnimetime).alpha(show?1:0).setListener(new AnimatorListenerAdapter() {
              @Override
              public void onAnimationEnd(Animator animation) {
                  tvLoad.setVisibility(show?View.VISIBLE:View.GONE);
              }
          });
      }
      else
      {
          progressbar.setVisibility(show? View.VISIBLE:View.GONE);
          tvLoad.setVisibility(show? View.VISIBLE:View.GONE);
          linearlayout.setVisibility(show? View.GONE:View.VISIBLE);
      }
  }

}
