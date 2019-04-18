package com.spirit.tech.leaders;

import android.content.DialogInterface;
import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.Toast;

public class DeleteFavourite extends AppCompatActivity {

    ImageView ivdelete;
    private int index;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_favourite);


        actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" Remove Favourite ");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ivdelete=findViewById(R.id.ivdelete);

        index=getIntent().getIntExtra("favindex",0);

        ivdelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                AlertDialog.Builder dialog=new  AlertDialog.Builder(DeleteFavourite.this);
                dialog.setTitle("Remove from Favourites!");
                dialog.setMessage("Do yout want to remove this from favourite?");
                dialog.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {

                        SqlDataFavourite favdata=new SqlDataFavourite(DeleteFavourite.this);
                        favdata.open();
                        favdata.delete(index+1+"");
                        favdata.close();

                        ApplicationClass.favourites.remove(index);
                        Toast.makeText(DeleteFavourite.this, "Removed From Favourites", Toast.LENGTH_SHORT).show();

                        setResult(RESULT_OK);
                        DeleteFavourite.this.finish();
                    }
                });
                dialog.setNegativeButton("No", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        setResult(RESULT_OK);
                        DeleteFavourite.this.finish();
                    }
                });
                dialog.show();
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

        switch (item.getItemId())
        {
            case R.id.share: Intent share=new Intent();
                                    share.setAction(Intent.ACTION_SEND);
                                    if(ApplicationClass.favourites.get(index).getSegment().equals("Vision"))
                                    {
                                        share.putExtra(Intent.EXTRA_TEXT,ApplicationClass.favourites.get(index).getData()+"\n" +
                                                " -Leaders App");
                                    }
                                    else if(ApplicationClass.favourites.get(index).getSegment().equals("Story")) {
                                        share.putExtra(Intent.EXTRA_TEXT, ApplicationClass.favourites.get(index).getTitle() + "\n" +
                                                ApplicationClass.favourites.get(index).getData() + "\n -Leaders App");
                                    }
                                    share.setType("text/plain");
                                    startActivity(Intent.createChooser(share,"Share with Friends/Relatives..."));
                break;

        }
        return true;
    }
}
