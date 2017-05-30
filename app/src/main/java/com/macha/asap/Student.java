package com.macha.asap;

import android.content.ContentValues;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.Toast;

import java.text.SimpleDateFormat;
import java.util.Date;

import static com.macha.asap.StudContract.StudEntry.STUD_TABLE;

/**
 * Created by user pc on 28-May-17.
 */

public class Student extends AppCompatActivity {

    private EditText et_fname, et_lname, et_age, et_hm, et_em;
    int id;
    private String rb_gender;
    boolean a = false, b = false, c = false, d = false, e = false;
    private Button et_submit;
    RadioGroup radioGroup;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.student);

        et_fname = (EditText) findViewById(R.id.et_fname_id);
        et_lname = (EditText) findViewById(R.id.et_lname_id);
        et_age = (EditText) findViewById(R.id.et_age_id);
        et_hm = (EditText) findViewById(R.id.et_hm_id);
        et_em = (EditText) findViewById(R.id.et_em_id);
        et_submit = (Button) findViewById(R.id.btn_submit_id);
        radioGroup = (RadioGroup) findViewById(R.id.rg_id) ;

        id = radioGroup.getCheckedRadioButtonId();


        //Data validation is done here
        et_submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                id = radioGroup.getCheckedRadioButtonId();


                if ((et_fname.getText().toString().length() == 0) || (!et_fname.getText().toString().matches("[a-zA-Z]+"))) {
                    et_fname.setError("Enter valid first name");
                    a = et_fname.requestFocus();
                }
                else
                    a = false;


                if ((et_lname.getText().toString().length() == 0) || (!et_lname.getText().toString().matches("[a-zA-Z]+"))) {
                    et_lname.setError("Enter valid last name");
                    b = et_lname.requestFocus();
                }
                else
                    b = false;


                if ((et_age.getText().toString().length() == 0) ||
                        ((Integer.parseInt(et_age.getText().toString()) > 50) || (4 > Integer.parseInt(et_age.getText().toString())))) {
                    et_age.setError("Enter correct age");
                    c = et_age.requestFocus();
                }
                else
                    c = false;

                if ((et_hm.getText().toString().length() == 0)//
                        || ((Integer.parseInt(et_em.getText().toString()) > 100) || (0 > Integer.parseInt(et_em.getText().toString())))) {
                    et_hm.setError("Enter valid marks");
                    d = et_hm.requestFocus();
                }
                else
                    d = false;

                if ((et_em.getText().toString().length() == 0)
                        || ((Integer.parseInt(et_em.getText().toString()) > 100) || (0 > Integer.parseInt(et_em.getText().toString())))) {
                    et_em.setError("Enter valid marks");
                    b = et_hm.requestFocus();
                }
                else
                    e = false;


                if (a == false && b == false && c == false && d == false && e == false) {
                    onSubmitReg();
                }
            }
        });
    }

    public void onSubmitReg() {

        DbHelper mydb = new DbHelper(this);

        switch (id) {
            case R.id.rb_female_id:
                rb_gender = "Girl";
                break;
            case R.id.rb_male_id:
                rb_gender = "Boy";
                break;
        }

        ContentValues contentValues = new ContentValues();
        contentValues.put("sfname", et_fname.getText().toString());
        contentValues.put("slname", et_lname.getText().toString());
        contentValues.put("sage", et_age.getText().toString());
        contentValues.put("sgen", rb_gender);
        contentValues.put("eng", et_em.getText().toString());
        contentValues.put("hin", et_hm.getText().toString());


        //Data sent to DB
        long check = mydb.insertDB(STUD_TABLE, contentValues);

        if (check != -1)
            Toast.makeText(Student.this, "Details Entered successfully! Row: " + check, Toast.LENGTH_SHORT).show();
        else
            Toast.makeText(Student.this, "Something wrong", Toast.LENGTH_SHORT).show();
    }
}
