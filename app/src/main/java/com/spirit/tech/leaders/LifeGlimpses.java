package com.spirit.tech.leaders;

import android.content.Intent;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import android.widget.Toast;

public class LifeGlimpses extends AppCompatActivity implements LifeListAdapter.ItemClicked{

    TextView tvTitle,tvstory;
    int index;
    int storyindex;
    String[] title;
    String[] story;
    String [] life;

    FragmentManager manager;
    Fragment fraglife,fragstory;

    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_life_glimpses);

        actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" Life Glipses");
        actionBar.setDisplayShowHomeEnabled(true);
        actionBar.setDisplayUseLogoEnabled(true);

        ApplicationClass.flag=false;

        tvTitle=findViewById(R.id.tvTitle);
        tvstory=findViewById(R.id.tvStory);

        manager=this.getSupportFragmentManager();/*setting up fragment Manager*/
        fraglife=manager.findFragmentById(R.id.fraglist);
        fragstory=manager.findFragmentById(R.id.fragstory);

        index=getIntent().getIntExtra("index",0); /* getting index of leader fromlaunching activity*/
        ApplicationClass.index=index;

        ApplicationClass.lv.clear();

        if(ApplicationClass.leader.get(index).getName().equals("Swami Vivekanand")) {
            title = getResources().getStringArray(R.array.VivekanandTitle);
            life = getResources().getStringArray(R.array.VivekanandLife);
            story = getResources().getStringArray(R.array.VivekanandStory);

            for (int i = 0; i < title.length; i++) {
                ApplicationClass.lv.add(new LeaderVision("", title[i], life[i], story[i]));
            }
        }
        if(ApplicationClass.leader.get(index).getName().equals("Mahatma Gandhi")) {
            title = getResources().getStringArray(R.array.GandhiTitle);
            life = getResources().getStringArray(R.array.GandhiLife);
            story = getResources().getStringArray(R.array.GandhiStory);

            for (int i = 0; i < title.length; i++) {
                ApplicationClass.lv.add(new LeaderVision("", title[i], life[i], story[i]));
            }
        }
        tvTitle.setText(ApplicationClass.lv.get(index).getLife());
        tvstory.setText(ApplicationClass.lv.get(index).getStory());

        if(findViewById(R.id.layout_land)!=null) /*setting up Layout for screen*/
        {
            manager.beginTransaction()
                    .show(fragstory)
                    .show(fraglife)
                    .commit();
        }
        else if(findViewById(R.id.layout_portrait)!=null)
        {
            manager.beginTransaction()
                    .hide(fragstory)
                    .show(fraglife)
                    .commit();
        }

    }

    @Override
    public void onItemClicled(int i) {

        ApplicationClass.flag=true;
        storyindex=i;
        tvTitle.setText(ApplicationClass.lv.get(i).getLife());
        tvstory.setText(ApplicationClass.lv.get(i).getStory());

        if (findViewById(R.id.layout_portrait) != null) {
            manager.beginTransaction()
                    .hide(fraglife)
                    .show(fragstory)
                    .addToBackStack(null)
                    .commit();
            }
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
                intent.putExtra(Intent.EXTRA_TEXT,
                        ApplicationClass.lv.get(storyindex).getLife()+"\n"
                        +ApplicationClass.lv.get(storyindex).getStory()+"\n"+" -Leaders App");
                intent.setType("text/plain");
                startActivity(Intent.createChooser(intent, "Share Story with Friends.."));
            }
            else{
                Toast.makeText(this, "First open up a story then share it", Toast.LENGTH_SHORT).show();
            }
            break;

            case R.id.fav:  if(ApplicationClass.flag==true)
                                {
                                    ApplicationClass.flag=false;
                                    int f=0;

                                    for(int i=0;i<ApplicationClass.favourites.size();i++)
                                    {
                                        if(ApplicationClass.favourites.get(i).getLeadername()
                                                .equals(ApplicationClass.leader.get(index))
                                                && (ApplicationClass.favourites.get(i).getSegment().equals("Story"))
                                                &&(ApplicationClass.favourites.get(i).getPosition()==storyindex))
                                        {
                                            f=1;
                                        }
                                        else{
                                            f=0;
                                        }
                                    }
                                    if(f==0){
                                        ApplicationClass.favourites.add(new Favourite("1",ApplicationClass.leader.get(index).getName(),
                                                "Story",ApplicationClass.lv.get(storyindex).getStory(),storyindex,
                                                        ApplicationClass.lv.get(storyindex).getLife(),
                                                ApplicationClass.leader.get(index).getColor()));

                                        SqlDataFavourite favdata=new SqlDataFavourite(this);
                                        favdata.open();
                                        favdata.createEntry(ApplicationClass.leader.get(index).getName(),
                                                "Story",ApplicationClass.lv.get(storyindex).getStory(),storyindex,
                                                ApplicationClass.lv.get(storyindex).getLife(),
                                                ApplicationClass.leader.get(index).getColor());
                                        favdata.close();
                                        Toast.makeText(this, "Added to Favourite", Toast.LENGTH_SHORT).show();
                                    }
                                    else{
                                        Toast.makeText(this, "Already Present in your favourite list", Toast.LENGTH_SHORT).show();
                                    }
                                }
                                else{
                Toast.makeText(this, "Please first open up story.", Toast.LENGTH_SHORT).show();
                                }
                                break;
            case R.id.bookmark:
                Toast.makeText(this, "This feature will be added soon", Toast.LENGTH_SHORT).show();break;


        }
        return true;
    }
}
