package com.macha.asap;

import android.provider.BaseColumns;

/**
 * Created by user pc on 28-May-17.
 */

public final class StudContract {

private StudContract(){}

    public static class StudEntry implements BaseColumns{

        public static final String _ID = BaseColumns._ID;
        public static final String STUD_TABLE ="studTable";
        public static final String FNAME = "sfname";
        public static final String LNAME = "slname";
        public static final String AGE = "sage";
        public static final String GENDER = "sgen";
        public static final String ENG_MARKS = "eng";
        public static final String HIN_MARKS = "hin";
    }

}
