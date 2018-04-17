package com.donghyeokseo.flow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;
import android.widget.Toast;

public class DatabaseHelper extends SQLiteOpenHelper {
    private static final String DATABASE_NAME = "flow_database";
    private static final int DATABASE_VERSION = 1;
    private Context context;
    private DatabaseTableHelper databaseTableHelper = new DatabaseTableHelper();

    public DatabaseHelper(Context context, String name, SQLiteDatabase.CursorFactory factory, int version) {
        super(context, name, factory, version);
        this.context = context;
    }

    public DatabaseHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        db.execSQL(databaseTableHelper.TokenTable);
        db.execSQL(databaseTableHelper.MealTable);
        Toast.makeText(context, "테이블 생성이 완료되었습니다", Toast.LENGTH_SHORT).show();
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Toast.makeText(context, "데이터베이스가 업데이트 되었습니다", Toast.LENGTH_SHORT).show();
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
