package com.macha.asap;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;

/* SUMMARY IS CALCULATED HERE */

public class Details extends AppCompatActivity {

    DbHelper mydb;
    int Marks;
    Float FMarks;
    String name;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.details_layout);

        TextView tme_tv = (TextView) findViewById(R.id.tme2_id);
        TextView tmh_tv = (TextView) findViewById(R.id.tmh2_id);
        TextView amh_tv = (TextView) findViewById(R.id.amh2_id);
        TextView ame_tv = (TextView) findViewById(R.id.ame2_id);
        TextView he_tv = (TextView) findViewById(R.id.he2_id);
        TextView hh_tv = (TextView) findViewById(R.id.hh2_id);
        TextView hab_tv = (TextView) findViewById(R.id.hab2_id);

        mydb = new DbHelper(this);

        Marks = mydb.sum("eng");
        tme_tv.setText(String.valueOf(Marks));

        Marks = mydb.sum("hin");
        tmh_tv.setText(String.valueOf(Marks));

        FMarks = mydb.avg("eng");
        ame_tv.setText(String.valueOf(FMarks));

        FMarks = mydb.avg("hin");
        amh_tv.setText(String.valueOf(FMarks));

        name = mydb.high("hin");
        hh_tv.setText(name);

        name = mydb.high("eng");
        he_tv.setText(name);

        name = mydb.highAvg();
        hab_tv.setText(name);

    }
}
