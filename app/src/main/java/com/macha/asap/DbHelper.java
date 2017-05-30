package com.macha.asap;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import static com.macha.asap.StudContract.StudEntry.AGE;
import static com.macha.asap.StudContract.StudEntry.ENG_MARKS;
import static com.macha.asap.StudContract.StudEntry.FNAME;
import static com.macha.asap.StudContract.StudEntry.GENDER;
import static com.macha.asap.StudContract.StudEntry.HIN_MARKS;
import static com.macha.asap.StudContract.StudEntry.LNAME;
import static com.macha.asap.StudContract.StudEntry.STUD_TABLE;
import static com.macha.asap.StudContract.StudEntry._ID;

/**
 * Created by user pc on 28-May-17.
 */

public class DbHelper extends SQLiteOpenHelper{

    public static final int DATABASE_VERSION = 2;
    public static final String DATABASE_NAME = "student.db";



    public DbHelper(Context context)
    {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }



    //Table creation query string
    private static final String SQL_CREATE_ENTRY = "CREATE TABLE IF NOT EXISTS "+STUD_TABLE+" ( "+
            _ID + " INTEGER PRIMARY KEY, "+
            FNAME + " TEXT , "+
            LNAME + " TEXT , "+
            AGE + " INT , "+
            GENDER + " TEXT , "+
            ENG_MARKS + " INT , "+
            HIN_MARKS + " INT ) ";


    //Table deletion query string
    private static final String SQL_DELETE_TABLE =
            "DROP TABLE IF EXISTS " + STUD_TABLE;


    @Override
    public void onCreate(SQLiteDatabase db) {
        //Table & DB created
        db.execSQL(SQL_CREATE_ENTRY);
        Log.i("Database created"," success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        onCreate(db);
    }

    //Deletes the table...not used anywhere
    public void dropTable()
    {
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL(SQL_DELETE_TABLE);
        Log.i("Table deleted", " success");
    }

    //Insert data into DB
    public long insertDB(String tableName, ContentValues contentValues)
    {
        SQLiteDatabase db = this.getWritableDatabase();
        long rowInserted = db.insert(tableName, null, contentValues);
        return rowInserted;
    }

    //Retrieves data with any condition from DB
    public Cursor getData()
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT *, ((eng+hin)/2) as AVG FROM studTable" ;
        return db.rawQuery(query, null);
    }

    //Retrieves sorted data with Ascending/Decending(o) order of sibject(sub) from DB
    public Cursor getData(String sub, String o)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query = "SELECT *, ((eng+hin)/2) as AVG FROM studTable order by "+sub+" "+o ;
        return db.rawQuery(query, null);
    }

    //Retrieves sorted data of gender from DB
    public Cursor getData(String g)
    {
        SQLiteDatabase db = this.getReadableDatabase();
        String query;
        query = "SELECT *, ((eng+hin)/2) as AVG FROM studTable WHERE sgen LIKE '" + g + "'";
        return db.rawQuery(query, null);
    }


    //Delete Record of selected student
    public boolean deleteRow(long rowID){
        SQLiteDatabase db = this.getWritableDatabase();
        String [] where ={String.valueOf(rowID)};
        return db.delete(STUD_TABLE,"_ID=?",where)>0;
    }


    //Update record of selected student
    public void updateMarks (String sub,String marks, long id)
    {
        String ID =String.valueOf(id);
        SQLiteDatabase db = this.getWritableDatabase();
        db.execSQL("UPDATE studTable  SET "+sub+" = " +marks+" WHERE _ID = " + ID);

    }

    //Get sum of marks of given subject (sub)
    public int sum(String sub){
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT SUM("+sub+") FROM studTable" ;
        Cursor c = db.rawQuery(query, null);
        c.moveToFirst();
        int i=c.getInt(0);
        return i;
    }

    //Get average of marks of given subject (sub)
    public float avg(String sub){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT SUM("+sub+") FROM studTable" ;
        String query2 = "SELECT * FROM studTable" ;

        Cursor c1 = db.rawQuery(query1, null);
        Cursor c2 = db.rawQuery(query2, null);

        float n = c2.getCount();
        c1.moveToFirst();
        float i=(c1.getInt(0));
        return i/n;
    }

    //Get highest marks of given subject (sub)
    public String high(String sub){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT sfname, MAX("+sub+") FROM studTable" ;
        Cursor c1 = db.rawQuery(query1, null);
         c1.moveToFirst();
         String str = c1.getString(c1.getColumnIndex("sfname"));
         return str;
    }

    //Get highest Average marks
    public String highAvg(){
        SQLiteDatabase db = this.getWritableDatabase();
        String query1 = "SELECT sfname, MAX((eng+hin)/2) FROM studTable" ;
        Cursor c1 = db.rawQuery(query1, null);
        c1.moveToFirst();
        String str = c1.getString(c1.getColumnIndex("sfname"));
        return str;
    }

}
