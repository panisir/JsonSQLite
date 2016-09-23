package com.example.asus.jsonsqlite;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by ASUS on 6.09.2016.
 */
public class DatabaseHelper extends SQLiteOpenHelper {
    public static final String DATABASE_NAME="coordinates";
    public static final String TABLE_NAME="city_table";
    public static final String COL_1 ="ID";
    public static final String COL_2="CITY";
    public static final String COL_3="LAT";
    public static final String COL_4="LNG";


    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, 1);
        SQLiteDatabase db=this.getWritableDatabase();
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL("create table "+TABLE_NAME+"(ID INTEGER PRIMARY KEY AUTOINCREMENT," +
                "CITY TEXT, LAT FLOAT, LNG FLOAT)");


    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXIST"+ TABLE_NAME);
        onCreate(db);
    }
    public boolean insertData(String city, String lat, String lng){
        SQLiteDatabase db=getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_2,city);
        contentValues.put(COL_3,lat);
        contentValues.put(COL_4,lng);

        long result=db.insert(TABLE_NAME, null, contentValues);
        if(result==-1)
            return false;
        else
            return true;
    }

    public Cursor displayAllData(){
        SQLiteDatabase db=this.getWritableDatabase();
        Cursor res=db.rawQuery("select * from "+TABLE_NAME, null);
        return res;
    }

    public boolean updateData(String id, String city, String lat, String lng){
        SQLiteDatabase db=this.getWritableDatabase();
        ContentValues contentValues=new ContentValues();
        contentValues.put(COL_1,id);
        contentValues.put(COL_2,city);
        contentValues.put(COL_3,lat);
        contentValues.put(COL_4,lng);
        db.update(TABLE_NAME, contentValues, "id = ?", new String[]{id});
        return true;
    }
    public Integer deleteData(String id){
        SQLiteDatabase db=this.getWritableDatabase();
        return db.delete(TABLE_NAME,"ID = ?", new String[]{id});
    }
}
