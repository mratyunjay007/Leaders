package com.spirit.tech.leaders;

import android.content.Intent;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.CardView;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

public class TwoPart extends AppCompatActivity {

    TextView tvname,tvvision,tvlife;
    ImageView ivvision,ivlife;
    CardView vision,life;
    int i;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_two_part);


        actionBar=this.getSupportActionBar();
        actionBar.setIcon(R.mipmap.logoreal);
        actionBar.setTitle(" Life Segments");
        actionBar.setDisplayUseLogoEnabled(true);
        actionBar.setDisplayShowHomeEnabled(true);

        tvname=findViewById(R.id.tvName);
        tvvision=findViewById(R.id.tvVision);
        tvlife=findViewById(R.id.tvLife);
        ivvision=findViewById(R.id.ivVision);
        ivlife=findViewById(R.id.ivLife);
        vision=findViewById(R.id.vision);
        life=findViewById(R.id.life);

        i=getIntent().getIntExtra("index",0);

        tvname.setText(ApplicationClass.leader.get(i).getName());
        tvvision.setText(ApplicationClass.leader.get(i).getVision());
        if(ApplicationClass.leader.get(i).getName().equals("Swami Vivekanand"))
        {
            ivvision.setImageResource(R.drawable.vivek1);/* setting up image view*/
            ivlife.setImageResource(R.drawable.vivek2);/* setting up image view*/
        }
        else if(ApplicationClass.leader.get(i).getName().equals("Mahatma Gandhi"))
        {
            ivvision.setImageResource(R.drawable.gandhi1);/* setting up image view*/
            ivlife.setImageResource(R.drawable.mg);/* setting up image view*/

        }
        setcolor(i);/* setting up color*/

        vision.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TwoPart.this,Vision.class);
                intent.putExtra("index",i);
                startActivity(intent);
                }
        });

        life.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent=new Intent(TwoPart.this,LifeGlimpses.class);
                intent.putExtra("index",i);
                startActivity(intent);
                }
        });



    }

    void setcolor(int i)
    {
        tvname.setTextColor(ApplicationClass.leader.get(i).getColor());
        tvvision.setTextColor(ApplicationClass.leader.get(i).getColor());
        tvlife.setTextColor(ApplicationClass.leader.get(i).getColor());
    }
}





