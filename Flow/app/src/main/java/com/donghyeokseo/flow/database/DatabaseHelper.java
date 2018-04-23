package com.donghyeokseo.flow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flow_database";
    private static final int DATABASE_VERSION = 1;
    private DatabaseTableHelper databaseTableHelper = new DatabaseTableHelper();

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(databaseTableHelper.TokenTable);
        db.execSQL(databaseTableHelper.MealTable);
        db.execSQL(databaseTableHelper.OutTable);
        Log.e("database", "table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.e("database", "updated");
    }

    public String getToken() {
        String result;
        String query = "SELECT Token FROM Token ORDER BY idx DESC LIMIT 1;";
        SQLiteDatabase db = getReadableDatabase();
        Cursor cursor = db.rawQuery(query, null);
        cursor.moveToNext();
        try {
            result = cursor.getString(0);
            Log.e("token", result);
        } catch (CursorIndexOutOfBoundsException exception) {
            result = "";
            Log.e("database error", exception.getMessage());
        }
        cursor.close();
        db.close();
        return result;
    }

    public void insertToken(final String token) {
        new Thread(() -> {
            final SQLiteDatabase db = this.getWritableDatabase();
            final ContentValues contentValues = new ContentValues();
            contentValues.put("token", token);
            db.insert("token", null, contentValues);
            Log.e("token data inserted", token);
        }).run();
    }
}
