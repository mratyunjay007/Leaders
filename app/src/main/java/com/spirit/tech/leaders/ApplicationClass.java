package com.spirit.tech.leaders;

import android.app.Application;
import android.widget.Toast;

import com.backendless.Backendless;
import com.backendless.BackendlessUser;

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

public class ApplicationClass extends Application {

   public static BackendlessUser user;

   public static  ArrayList<Leader> leader;         /* all leaders collection*/
   public static ArrayList<LeaderVision> lv;        /* each leader vision will be catched in this acoord. to selection*/
   public static ArrayList<Favourite> favourites;   /*user favorite list of vision and story*/
   public static List<Request> requests;            /* list of user request for new stroy,list do not need intialization*/
   public static int index;                         /* In life glimpses to set image over card in fragment*/
   public static boolean flag=false;                /* falg is used to know whether user has clicked a vision or not to perform share*/
    public static boolean logout=false;

    @Override
    public void onCreate() {
        super.onCreate();

        Backendless.setUrl("https://api.backendless.com");
        Backendless.initApp(getApplicationContext(),"49417A6A-506B-40A7-FF57-3021FE4CCC00",
                "A42BBB35-EAB6-9593-FFC6-BFB1A5B29200");

        leader=new ArrayList<Leader>();
        lv=new ArrayList<LeaderVision>();
        favourites=new ArrayList<Favourite>();

        leader.add(new Leader("Swami Vivekanand","Birth: 12 January,1863","Death: 4 July,1902",
                "Birth Place: Kolkata (West Bengal)", "Contribution: Acoomplished Indian Spritual values in World.",
                "Vivekanad Vision of Life",0xFFFF9800));

        leader.add(new Leader("Mahatma Gandhi","Birth: 2 October,1869","Assassinated By Nathuram Godse: 30 January,1948",
                "Place: Porbandar (Gujrat)","Contribution: Plays Lead role in Freedom of Country and spread teaching of " +
                "Truth and Non-voilence.","Gandhi Vision of Life",0xFFA9A9A9));

            SqlDataFavourite favdata=new SqlDataFavourite(this);
            favdata.open();
            favourites=favdata.getData();
            favdata.close();

            }
}
