package com.quicktapsurvey.uallas.ctvnewsreader.data.local.table;

import android.database.sqlite.SQLiteDatabase;
import android.provider.BaseColumns;

/**
 * Created by Uallas on 28/03/2018.
 */

public class NewsTable {
    public static final String TABLE_NAME = "News";

    public static class NewsColumns implements BaseColumns {
        public static final String TITLE = "title";
        public static final String LINK = "link";
        public static final String DESCRIPTION = "description";
        public static final String CREDIT_LINE = "credit_line";
        public static final String DATE = "date";
        public static final String IMAGE = "image";
        public static final String IMAGE_DESCRIPTION = "image_description";
        public static final String READ = "read";
    }

    public static void onCreate(SQLiteDatabase db) {
        String sql = "CREATE TABLE " + NewsTable.TABLE_NAME + "("
                + BaseColumns._ID + " REAL PRIMARY KEY, "
                + NewsColumns.TITLE + " TEXT NOT NULL, "
                + NewsColumns.LINK + " TEXT NOT NULL, "
                + NewsColumns.DESCRIPTION + " TEXT NOT NULL, "
                + NewsColumns.CREDIT_LINE + " TEXT NOT NULL, "
                + NewsColumns.DATE + " NUMERIC NOT NULL, "
                + NewsColumns.IMAGE + " TEXT NOT NULL, "
                + NewsColumns.IMAGE_DESCRIPTION + " TEXT NOT NULL, "
                + NewsColumns.READ + " NUMERIC DEFAULT 0);";

        db.execSQL(sql);
    }

    public static void onUpgrade(SQLiteDatabase db, int oldVersion,
                                 int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + NewsTable.TABLE_NAME);
        NewsTable.onCreate(db);
    }

    public static final String[] getFieldsArray() {
        String[] fields = {BaseColumns._ID,
                NewsColumns.TITLE,
                NewsColumns.LINK,
                NewsColumns.DESCRIPTION,
                NewsColumns.CREDIT_LINE,
                NewsColumns.DATE,
                NewsColumns.IMAGE,
                NewsColumns.IMAGE_DESCRIPTION,
                NewsColumns.READ};

        return fields;
    }
}
