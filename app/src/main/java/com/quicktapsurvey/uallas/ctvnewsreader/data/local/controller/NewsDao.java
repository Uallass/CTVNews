package com.quicktapsurvey.uallas.ctvnewsreader.data.local.controller;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.quicktapsurvey.uallas.ctvnewsreader.data.local.DBHelper;
import com.quicktapsurvey.uallas.ctvnewsreader.data.local.table.NewsTable;
import com.quicktapsurvey.uallas.ctvnewsreader.data.model.News;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * Created by Uallas on 28/03/2018.
 */

public class NewsDao {
    private SQLiteDatabase db;
    private DBHelper database;

    public NewsDao(Context context) {
        database = new DBHelper(context);
    }

    public List<News> load() {
        Cursor cursor;
        db = database.getReadableDatabase();
        cursor = db.query(NewsTable.TABLE_NAME, NewsTable.getFieldsArray(), null, null, null, null, NewsTable.NewsColumns.DATE + " DESC", null);

        List<News> newsList = new ArrayList<>();
        if(cursor.moveToFirst()) {
            do {
                News news = new News();
                news.setId(cursor.getDouble(0));
                news.setTitle(cursor.getString(1));
                news.setLink(cursor.getString(2));
                news.setDescription(cursor.getString(3));
                news.setCreditLine(cursor.getString(4));
                news.setDate(new Date(cursor.getLong(5)));
                news.setImage(cursor.getString(6));
                news.setImageDescription(cursor.getString(7));
                news.setRead(cursor.getInt(8) == 1 ? true : false);

                newsList.add(news);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return newsList;
    }

    public News loadById(Double id) {
        Cursor cursor;
        String where = NewsTable.NewsColumns._ID + " = " + id.doubleValue();
        db = database.getReadableDatabase();
        cursor = db.query(NewsTable.TABLE_NAME, NewsTable.getFieldsArray(), where, null, null, null, null, null);

        News news = new News();
        if(cursor.moveToFirst()) {
            do {
                news.setId(cursor.getDouble(0));
                news.setTitle(cursor.getString(1));
                news.setLink(cursor.getString(2));
                news.setDescription(cursor.getString(3));
                news.setCreditLine(cursor.getString(4));
                news.setDate(new Date(cursor.getLong(5)));
                news.setImage(cursor.getString(6));
                news.setImageDescription(cursor.getString(7));
                news.setRead(cursor.getInt(8) == 1 ? true : false);
            } while (cursor.moveToNext());
        }

        cursor.close();
        db.close();

        return news;
    }

    public boolean isInTable(Double id) {
        Cursor cursor;
        String query = "select " + NewsTable.NewsColumns._ID
                + " from " + NewsTable.TABLE_NAME
                + " where " + NewsTable.NewsColumns._ID + " = " + id.doubleValue();

        db = database.getReadableDatabase();
        cursor = db.rawQuery(query, null);

        if(cursor.moveToNext()) {
            cursor.close();
            db.close();
            return true;
        } else {
            cursor.close();
            db.close();
            return false;
        }
    }

    public Long insert(News news) {
        ContentValues values;
        Long result;

        db = database.getWritableDatabase();
        values = new ContentValues();
        values.put(NewsTable.NewsColumns._ID, news.getId());
        values.put(NewsTable.NewsColumns.TITLE, news.getTitle());
        values.put(NewsTable.NewsColumns.LINK, news.getLink());
        values.put(NewsTable.NewsColumns.DESCRIPTION, news.getDescription());
        values.put(NewsTable.NewsColumns.CREDIT_LINE, news.getCreditLine());
        values.put(NewsTable.NewsColumns.DATE, news.getDate().getTime());
        values.put(NewsTable.NewsColumns.IMAGE, news.getImage());
        values.put(NewsTable.NewsColumns.IMAGE_DESCRIPTION, news.getImageDescription());

        result = db.insert(NewsTable.TABLE_NAME, null, values);

        db.close();

        if(result < 1) {
            Log.e(this.getClass().getName(), "It was not possible to insert news id=" + news.getId().doubleValue() + "!");
        }

        return result;
    }

    public int update(News news) {
        ContentValues values;
        int result;

        db = database.getWritableDatabase();
        String where = NewsTable.NewsColumns._ID + " = " + news.getId();
        values = new ContentValues();
        values.put(NewsTable.NewsColumns.TITLE, news.getTitle());
        values.put(NewsTable.NewsColumns.LINK, news.getLink());
        values.put(NewsTable.NewsColumns.DESCRIPTION, news.getDescription());
        values.put(NewsTable.NewsColumns.CREDIT_LINE, news.getCreditLine());
        values.put(NewsTable.NewsColumns.DATE, news.getDate().getTime());
        values.put(NewsTable.NewsColumns.IMAGE, news.getImage());
        values.put(NewsTable.NewsColumns.IMAGE_DESCRIPTION, news.getImageDescription());

        result = db.update(NewsTable.TABLE_NAME, values, where, null);

        db.close();

        if(result < 1) {
            Log.e(this.getClass().getName(), "It was not possible to update news id=" + news.getId().doubleValue() + "!");
        }

        return result;
    }

    public int delete(Double id){
        int result;
        String where = NewsTable.NewsColumns._ID + "=" + id;
        db = database.getReadableDatabase();
        result = db.delete(NewsTable.TABLE_NAME, where, null);
        db.close();

        if(result < 1) {
            Log.e(this.getClass().getName(), "It was not possible to delete news id=" + id.doubleValue() + "!");
        }

        return result;
    }

    // insert just the news that aren't already in table
    public void insertNewNews(List<News> newsList) {

        for(News item : newsList) {
            if(!isInTable(item.getId())) {
                insert(item);
            }
        }

    }

    public void markAsRead(Double id) {
        ContentValues values;
        int result;

        db = database.getWritableDatabase();
        String where = NewsTable.NewsColumns._ID + " = " + id;
        values = new ContentValues();
        values.put(NewsTable.NewsColumns.READ, 1);

        result = db.update(NewsTable.TABLE_NAME, values, where, null);

        db.close();

        if(result < 1) {
            Log.e(this.getClass().getName(), "It was not possible to mark news id=" + id.doubleValue() + " as read!");
        }
    }
}
