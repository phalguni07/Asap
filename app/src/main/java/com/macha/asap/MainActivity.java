package com.macha.asap;

import android.content.DialogInterface;
import android.content.Intent;
import android.database.Cursor;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import android.widget.Toast;

import static com.macha.asap.StudContract.StudEntry.AGE;
import static com.macha.asap.StudContract.StudEntry.ENG_MARKS;
import static com.macha.asap.StudContract.StudEntry.FNAME;
import static com.macha.asap.StudContract.StudEntry.GENDER;
import static com.macha.asap.StudContract.StudEntry.HIN_MARKS;
import static com.macha.asap.StudContract.StudEntry.LNAME;
import static com.macha.asap.StudContract.StudEntry._ID;

public class MainActivity extends AppCompatActivity {

    private SimpleCursorAdapter dataAdapter;

    DbHelper mydb;
    int flag;
    Cursor list;
    String[] columns,colDA;
    int i;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        mydb = new DbHelper(this);
        ListView listView = (ListView) findViewById(R.id.listView1);

        i = 0;

        FloatingActionButton FAB = (FloatingActionButton) findViewById(R.id.fab);
        FAB.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent toStudent = new Intent(MainActivity.this, Student.class);
                startActivity(toStudent);
            }
        });

         dbToList();
         registerForContextMenu(listView);
    }

    @Override
    protected void onResume() {
        super.onResume();
        dbToList();
        ListView listView = (ListView) findViewById(R.id.listView1);
        registerForContextMenu(listView);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v, ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.setHeaderTitle("Select The Action");
        menu.add(0, v.getId(), 0, "Delete");
        menu.add(0, v.getId(), 0, "Update Eng Marks");
        menu.add(0, v.getId(), 0, "Update Hindi Marks");
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        if (item.getTitle() == "Delete") {
            AdapterView.AdapterContextMenuInfo info=
                    (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();
            boolean x = mydb.deleteRow(info.id);
            if (x){
                onResume();
                Toast.makeText(getApplicationContext(), "Details Deleted!", Toast.LENGTH_LONG).show();
            }
            else {
                Toast.makeText(getApplicationContext(), "Error!", Toast.LENGTH_LONG).show();
            }
        }

        else if (item.getTitle() == "Update Eng Marks") {
           final AdapterView.AdapterContextMenuInfo info=
                    (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.promt_layout, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {

                                    if(((Integer.parseInt (userInput.getText().toString()))<=100) &&
                                            (0<=(Integer.parseInt (userInput.getText().toString()))))
                                    {
                                        mydb.updateMarks("eng",userInput.getText().toString(),info.id);
                                        onResume();
                                        Toast.makeText(getApplicationContext(), "Marks Updated!", Toast.LENGTH_LONG).show();
                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Enter valid marks!!", Toast.LENGTH_LONG).show();
                                    }

                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });

            // create alert dialog
            AlertDialog alertDialog = alertDialogBuilder.create();

            // show it
            alertDialog.show();
            }

        else if (item.getTitle() == "Update Hindi Marks") {

            final AdapterView.AdapterContextMenuInfo info=
                    (AdapterView.AdapterContextMenuInfo)item.getMenuInfo();

            LayoutInflater li = LayoutInflater.from(this);
            View promptsView = li.inflate(R.layout.promt_layout, null);

            AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(
                    this);

            alertDialogBuilder.setView(promptsView);

            final EditText userInput = (EditText) promptsView
                    .findViewById(R.id.editTextDialogUserInput);

            alertDialogBuilder
                    .setCancelable(false)
                    .setPositiveButton("OK",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {

                                    if(((Integer.parseInt (userInput.getText().toString()))<=100) &&
                                            (0<=(Integer.parseInt (userInput.getText().toString()))))
                                    {
                                        mydb.updateMarks("hin",userInput.getText().toString(),info.id);
                                        onResume();
                                        Toast.makeText(getApplicationContext(), "Marks Updated!", Toast.LENGTH_LONG).show();

                                    }
                                    else {
                                        Toast.makeText(getApplicationContext(), "Enter valid marks!!", Toast.LENGTH_LONG).show();
                                    }
                                }
                            })
                    .setNegativeButton("Cancel",
                            new DialogInterface.OnClickListener() {
                                public void onClick(DialogInterface dialog,int id) {
                                    dialog.cancel();
                                }
                            });
            AlertDialog alertDialog = alertDialogBuilder.create();
            alertDialog.show();
        }


        else {
            return false;
        }
        return true;
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.option_menu, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        switch(id)
        {
            case R.id.SummaryId:
                Intent DetailsIntent = new Intent(this, Details.class);
                startActivity(DetailsIntent);
                return true;

            case R.id.EngAscId:
                flag=1;
                onResume();
                return true;

            case R.id.EngDescId:
                flag=2;
                onResume();
                return true;

            case R.id.HinAscId:
                flag=3;
                onResume();
                return true;

            case R.id.HinDescId:
                flag=4;
                onResume();
                return true;

            case R.id.GenFId:
                flag=5;
                onResume();
                return true;

            case R.id.GenMId:
                flag=6;
                onResume();
                return true;
        }
        return true;
    }

    //Function to extract data from DB and display in listView
    void dbToList() {

        ListView listView = (ListView) findViewById(R.id.listView1);

        columns = new String[]{_ID,FNAME,LNAME, AGE, GENDER, ENG_MARKS, HIN_MARKS};
        colDA = new String[]{FNAME,LNAME, AGE, GENDER, ENG_MARKS, HIN_MARKS, "AVG"};

        //Decides the order of appearance of data as selected by user from overflow menu
        switch (flag)
        {
            case 1:
                list = mydb.getData("eng","asc");
                break;
            case 2:
                list = mydb.getData("eng","desc");
                break;
            case 3:
                list = mydb.getData("hin","asc");
                break;
            case 4:
                list = mydb.getData("hin","desc");
                break;
            case 5:
              list = mydb.getData("Girl");
                break;
            case 6:
                list = mydb.getData("Boy");
                break;

            default:
                list = mydb.getData();

        }

        int[] to = new int[] {
                R.id.name_id,
                R.id.lname_id,
                R.id.age_id,
                R.id.gen_id,
                R.id.eng_id,
                R.id.hin_id,
                R.id.avg_id
        };


        dataAdapter = new SimpleCursorAdapter(
                this, R.layout.listlayout,
                list,
                colDA,
                to,
                0);

        listView.setAdapter(dataAdapter);

    }
}