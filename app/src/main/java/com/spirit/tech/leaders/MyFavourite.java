package com.spirit.tech.leaders;

import android.content.Intent;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

public class MyFavourite extends AppCompatActivity implements FavouriteAdapter.ItemClicked {

    TextView tvIntro;
    RecyclerView recyclerView;
    RecyclerView.Adapter adapter;
    RecyclerView.LayoutManager layoutManager;
    ActionBar actionBar;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_my_favourite);

        actionBar=getSupportActionBar();
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" My Favourites ");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvIntro=findViewById(R.id.tvIntro);
        recyclerView=findViewById(R.id.favlist);
        recyclerView.setHasFixedSize(true);

        if(ApplicationClass.favourites.isEmpty())
        {
            tvIntro.setVisibility(View.VISIBLE);
        }
        layoutManager=new LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false);
        recyclerView.setLayoutManager(layoutManager);

        adapter=new FavouriteAdapter(MyFavourite.this,ApplicationClass.favourites);
        recyclerView.setAdapter(adapter);

        adapter.notifyDataSetChanged();
    }

    @Override
    public void onItemClicked(int index) {
        
        Intent remove=new Intent(MyFavourite.this,DeleteFavourite.class);
        remove.putExtra("favindex",index);
        startActivityForResult(remove,1);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        
        if(requestCode==1)
        {
                adapter.notifyDataSetChanged();
        }

        if(ApplicationClass.favourites.isEmpty())
        {
            tvIntro.setVisibility(View.VISIBLE);
        }
    }
}
