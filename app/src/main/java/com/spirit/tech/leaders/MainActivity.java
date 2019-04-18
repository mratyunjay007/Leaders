package com.spirit.tech.leaders;

import android.content.ClipData;
import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.SwitchCompat;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.async.callback.AsyncCallback;
import com.backendless.exceptions.BackendlessFault;

public class MainActivity extends AppCompatActivity implements LeaderAdapter.ItemClicked{

    private DrawerLayout drawerLayout;
    NavigationView navigationView;
    View header;

    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ActionBar actionBar;
    int requestcode=1;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        actionBar=this.getSupportActionBar();
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" Collection");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        drawerLayout=findViewById(R.id.drawerlayout);
        navigationView=findViewById(R.id.navigationview);
        header=navigationView.getHeaderView(0);

        recyclerView=findViewById(R.id.recycler);
        recyclerView.setHasFixedSize(true);

        layoutManager=new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new LeaderAdapter(this,ApplicationClass.leader);
        recyclerView.setAdapter(adapter);

    }

    @Override
    public void onItemClicked(int index) {
        Intent intent=new Intent(MainActivity.this,TwoPart.class);
        intent.putExtra("index",index);
        startActivity(intent);
   
        }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.userinfo,menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch(item.getItemId())
        {
            case R.id.userinfo:
                startActivityForResult(new Intent(MainActivity.this,UserInfo.class),1);
                                break;

        }
        return true;
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        if(requestCode==1) {
            if (resultCode == RESULT_OK) {

                setResult(RESULT_OK);
                MainActivity.this.finish();
            }
        }
    }
}
