package com.example.ticketastic;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHandler extends SQLiteOpenHelper {
    private static final int DATABASE_VERSION = 1;
    private static final String DATABASE_NAME = "ticketastic.db";
    private static final String TABLE_NAME = "user_data";
    private static final String COLUMN_USERNAME = "user";
    private static final String COLUMN_PASSWORD = "pass";


    public DatabaseHandler(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String query = String.format("CREATE TABLE %s (%s TEXT PRIMARY KEY, %s TEXT)", TABLE_NAME, COLUMN_USERNAME, COLUMN_PASSWORD);
        db.execSQL(query);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
        onCreate(db);
    }

    public boolean addData(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_USERNAME, username);
        contentValues.put(COLUMN_PASSWORD, password);

        long result = db.insert(TABLE_NAME, null, contentValues);
        return (result != -1);
    }

    public Cursor getUsers() {
        SQLiteDatabase db = this.getWritableDatabase();
        String query = "SELECT * FROM " + TABLE_NAME;
        return db.rawQuery(query, null);
    }

    public boolean checkAvailableUsername(String username) {
        SQLiteDatabase db = getWritableDatabase();
        Cursor cursor = db.rawQuery("select * from user_data where user=?", new String[]{username});
        return (cursor.getCount() == 0);
    }


    public boolean checkUser(String username, String password) {
        SQLiteDatabase db = getWritableDatabase();

        Cursor cursor = db.rawQuery("select * from user_data where user = ? and pass = ?", new String[]{username, password});
        if (cursor.moveToFirst()) {
            Log.i("DEBUG-PASS", "tengo algo en el cursor");
            return true;
        }
        else{
            Log.i("DEBUG-PASS", "no tiene cosas el cursor");
            cursor.close();
            return false;
        }
    }
}
