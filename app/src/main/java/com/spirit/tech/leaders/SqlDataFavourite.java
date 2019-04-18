package com.spirit.tech.leaders;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

import java.util.ArrayList;

public class SqlDataFavourite {

    public final static String KEY_ID="_id";
    public final static String KEY_NAME="_leaername";
    public final static String KEY_SEGMENT="_segment";
    public final static String KEY_DATA="_data";
    public final static String KEY_POSITION="_position";
    public final static String KEY_TITLE="_title";
    public final static String KEY_COLOR="_color";

    public final static String DATABASE_NAME="_userfavourite";
    public final static String TABLE_NAME="_favourite";
    public final static int DATABASE_VERSION=1;

    private final Context ourcontext;
    private DBHelper ourhelper;
    private SQLiteDatabase ourdatabase;
    ArrayList<Favourite> fav;

    public SqlDataFavourite(Context context)
    {
        ourcontext=context;
    }

    private class DBHelper extends SQLiteOpenHelper{

        private DBHelper(Context context)
        {
            super(context,DATABASE_NAME,null,DATABASE_VERSION);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

            db.execSQL("DROP TABLE IF EXISTS "+TABLE_NAME);

            onCreate(db);
            }

        @Override
        public void onCreate(SQLiteDatabase db) {

            String sqlcode="CREATE TABLE "+TABLE_NAME+" ("+KEY_ID+" INTEGER PRIMARY KEY AUTOINCREMENT,"+KEY_NAME+" TEXT NOT NULL,"
                    +KEY_SEGMENT+" TEXT NOT NULL,"+KEY_DATA+" TEXT NOT NULL,"
                    +KEY_POSITION+" TEXT NOT NULL,"+KEY_TITLE+" TEXT NOT NULL,"+KEY_COLOR+" INTEGER NOT NULL);";

            db.execSQL(sqlcode);

        }
    }

    public SqlDataFavourite open()throws SQLException
    {
        ourhelper=new DBHelper(ourcontext);
        ourdatabase=ourhelper.getWritableDatabase();
        return this;
    }

    public  void close()
    {
        ourdatabase.close();
    }

    public long createEntry(String leadername,String segment,String data,int position,String title,int color)
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,leadername);
        cv.put(KEY_SEGMENT,segment);
        cv.put(KEY_DATA,data);
        cv.put(KEY_POSITION,position);
        cv.put(KEY_TITLE,title);
        cv.put(KEY_COLOR,color);

        return ourdatabase.insert(TABLE_NAME,null,cv);
    }

    public ArrayList getData()
    {
        String []column=new String[]{KEY_ID,KEY_NAME,KEY_SEGMENT,KEY_DATA,KEY_POSITION,KEY_TITLE,KEY_COLOR};
        Cursor c=ourdatabase.query(TABLE_NAME,column,null,null,null,null,null);

        fav=new ArrayList<Favourite>();

        int rowId=c.getColumnIndex(KEY_ID);
        int nameId=c.getColumnIndex(KEY_NAME);
        int segmentId=c.getColumnIndex(KEY_SEGMENT);
        int dataId=c.getColumnIndex(KEY_DATA);
        int positionId=c.getColumnIndex(KEY_POSITION);
        int titleId=c.getColumnIndex(KEY_TITLE);
        int colorId=c.getColumnIndex(KEY_COLOR);

        for(c.moveToFirst();!c.isAfterLast();c.moveToNext())
        {
            fav.add(new Favourite(c.getString(rowId),c.getString(nameId),c.getString(segmentId),c.getString(dataId),
                    c.getInt(positionId),c.getString(titleId),c.getInt(colorId)));
        }
        c.close();
        return fav;
    }

    public long update(String rowId,String name,String segment,String data,int position,String title,int color )
    {
        ContentValues cv=new ContentValues();
        cv.put(KEY_NAME,name);
        cv.put(KEY_SEGMENT,segment);
        cv.put(KEY_DATA,data);
        cv.put(KEY_POSITION,position);
        cv.put(KEY_TITLE,title);
        cv.put(KEY_COLOR,color);

        return ourdatabase.update(TABLE_NAME,cv,KEY_ID+"=?",new String[]{rowId});
    }

    public long delete(String rowId)
    {
        return ourdatabase.delete(TABLE_NAME,KEY_ID+"=?",new String[]{rowId});
    }

}
