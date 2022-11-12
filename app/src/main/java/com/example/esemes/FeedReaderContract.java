package com.example.esemes;

import android.provider.BaseColumns;

public class FeedReaderContract {

    public static class FeedEntry implements BaseColumns {
        public static final String TABLE_NAME = "kontakty";
        public static final String COLUMN_NAME_TITLE = "nazwa";
        public static final String COLUMN_NAME_SUBTITLE = "numer";
        public static final String _ID = "_id";
    }

    static final String SQL_CREATE_ENTRIES =
            "CREATE TABLE " + FeedEntry.TABLE_NAME + " (" +
                    FeedEntry._ID + " INTEGER PRIMARY KEY," +
                    FeedEntry.COLUMN_NAME_TITLE + " TEXT," +
                    FeedEntry.COLUMN_NAME_SUBTITLE + " TEXT)";

    static final String SQL_DELETE_ENTRIES =
            "DROP TABLE IF EXISTS " + FeedEntry.TABLE_NAME;

}
