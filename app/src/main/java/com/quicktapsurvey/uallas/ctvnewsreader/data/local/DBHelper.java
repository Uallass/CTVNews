package com.quicktapsurvey.uallas.ctvnewsreader.data.local;

import android.content.Context;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.quicktapsurvey.uallas.ctvnewsreader.data.local.table.NewsTable;

import java.io.File;

/**
 * Created by Uallas on 28/03/2018.
 */

public class DBHelper extends SQLiteOpenHelper {

    public static final String DATABASE_NAME = "database.db";
    private static final int DATABASE_VERSION = 1;
    private Context context;

    public DBHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        try {
            db.beginTransaction();

            NewsTable.onCreate(db);

            db.setTransactionSuccessful();
        } catch(SQLException e) {
            File dbFile = context.getDatabasePath(DBHelper.DATABASE_NAME);
            if(dbFile.exists()) {
                dbFile.delete();
            }
            e.printStackTrace();
        } finally {
            db.endTransaction();
        }
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        NewsTable.onUpgrade(db, oldVersion, newVersion);
    }
}
