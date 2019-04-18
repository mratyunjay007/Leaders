package com.spirit.tech.leaders;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import java.sql.SQLException;
import java.util.ArrayList;

public class Vision extends AppCompatActivity implements VisionAdapter.ItemClicked {

    View linearlayout;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;

    ArrayList<LeaderVision> lv;
    String[] Vision;
    int index;
    int visionindex;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_vision);

        actionBar=this.getSupportActionBar(); /*Adding action bar*/
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" Vision");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        ApplicationClass.flag=false;
        linearlayout=findViewById(R.id.linearlayout);
        index=getIntent().getIntExtra("index",0);

        lv=new ArrayList<LeaderVision>(); /*initializing Array List*/

        if(ApplicationClass.leader.get(index).getName().equals("Swami Vivekanand"))/*Adding content to class as per leader selection*/
        {

            Vision= getResources().getStringArray(R.array.VivekanandVision);

            for(int i=0;i<Vision.length;i++)
            {
                lv.add(new LeaderVision(Vision[i],"", "",""));
            }
            linearlayout.setBackgroundResource(R.drawable.sun);
        }
        else if(ApplicationClass.leader.get(index).getName().equals("Mahatma Gandhi"))
        {

            Vision= getResources().getStringArray(R.array.GandhiVision);

            for(int i=0;i<Vision.length;i++)
            {
                lv.add(new LeaderVision(Vision[i],"", "",""));
            }

            linearlayout.setBackgroundResource(R.drawable.tiranga);
        }

        recyclerView=findViewById(R.id.vision); /*setting up Recycler View*/
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(Vision.this,LinearLayoutManager.HORIZONTAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new VisionAdapter(Vision.this,lv,index);
            recyclerView.setAdapter(adapter);

            }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.main1,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {

         switch(item.getItemId())
         {
             case R.id.share:
                 if(ApplicationClass.flag==true) {
                     ApplicationClass.flag=false;
                     Intent intent = new Intent();
                     intent.setAction(Intent.ACTION_SEND);
                     intent.putExtra(Intent.EXTRA_TEXT, lv.get(visionindex).getVision()+" -Leaders App Team");
                     intent.setType("text/plain");
                     startActivity(Intent.createChooser(intent, "Share with Friends.."));
                 }
                 else
                 {
                     Toast.makeText(this, "Please first click on VISION than" +
                             " share it.", Toast.LENGTH_SHORT).show();
                 }
                 break;

             case R.id.fav:
                 if(ApplicationClass.flag==true)
                    {
                        ApplicationClass.flag=false;
                        int f=0;
                        for(int i=0;i<ApplicationClass.favourites.size();i++)
                        {
                            if(ApplicationClass.favourites.get(i).getLeadername()
                                    .equals(ApplicationClass.leader.get(index).getName())
                                    &&(ApplicationClass.favourites.get(i).getSegment().equals("Vision"))
                                    && (ApplicationClass.favourites.get(i).getPosition()==visionindex))
                            {
                                f=1;
                            }
                            else{
                                f=0;
                            }}
                            if(f==0){
                            
                                ApplicationClass.favourites.add(new Favourite("1",
                                        ApplicationClass.leader.get(index).getName(),"Vision",
                                        lv.get(visionindex).getVision(),visionindex,
                                        "",ApplicationClass.leader.get(index).getColor()));


                                    SqlDataFavourite favdata=new SqlDataFavourite(this);
                                    favdata.open();
                                    favdata.createEntry(ApplicationClass.leader.get(index).getName(),"Vision",
                                            lv.get(visionindex).getVision(),visionindex,"",
                                            ApplicationClass.leader.get(index).getColor());
                                    favdata.close();
                                Toast.makeText(this, "Added to your favourite List.", Toast.LENGTH_SHORT).show();

                            }
                            else if(f==1)
                            {
                                Toast.makeText(this, "Already present in your Favourite list.", Toast.LENGTH_SHORT).show();
                            }
                    }
                    else
                 {
                     Toast.makeText(this,"Please select a vision first",Toast.LENGTH_SHORT).show();
                 }
                 break;

             case R.id.bookmark:
                 Toast.makeText(this, "This feature will be available soon", Toast.LENGTH_SHORT).show();
                 break;


         }
         return true;
    }

    @Override
    public void onItemClicked(int index) {
        visionindex=index;
        Toast.makeText(this, "Add to favourite/Share it", Toast.LENGTH_SHORT).show();

    }
}
