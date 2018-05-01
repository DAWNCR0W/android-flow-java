package com.donghyeokseo.flow.database;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.CursorIndexOutOfBoundsException;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import com.donghyeokseo.flow.model.Out;

import java.util.ArrayList;
import java.util.List;

public final class DatabaseHelper extends SQLiteOpenHelper {

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

        Log.d("database", "table created");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        Log.d("database", "updated");
    }

    public void insertToken(final String token) {

        new Thread(() -> {

            final SQLiteDatabase db = this.getWritableDatabase();
            final ContentValues contentValues = new ContentValues();

            contentValues.put("token", token);

            db.insert("token", null, contentValues);

            Log.d("token data inserted", token);

            db.close();
        }).run();
    }

    public String getToken() {

        String result;

        String query = "SELECT Token FROM Token ORDER BY idx DESC LIMIT 1;";

        try {

            SQLiteDatabase db = getReadableDatabase();
            Cursor cursor = db.rawQuery(query, null);
            cursor.moveToNext();

            try {

                result = cursor.getString(0);

                Log.d("token", result);
            } catch (CursorIndexOutOfBoundsException exception) {

                result = "";

                Log.d("database error", exception.getMessage());
            }

            cursor.close();

            db.close();

            return result;
        } catch (Exception ignored) {

            return null;
        }
    }

    public void insertOut(Out out) {

        new Thread(() -> {

            final SQLiteDatabase db = this.getWritableDatabase();
            final ContentValues contentValues = new ContentValues();

            contentValues.put("start_date", out.getStartTime());
            contentValues.put("end_date", out.getEndTime());
            contentValues.put("reason", out.getReason());
            contentValues.put("status", out.getStatusCode());

            db.insert("Out", null, contentValues);

            Log.d("out data inserted", out.getReason());

            db.close();
        }).run();
    }

    public List<Out> getOuts() {

        List<Out> outList = new ArrayList<>();

        String query = "SELECT * FROM Out ORDER BY idx DESC;";

        SQLiteDatabase db = getReadableDatabase();

        Cursor cursor = db.rawQuery(query, null);

        while (cursor.moveToNext()) {

            Out out = new Out();

            out.setStatusCode(cursor.getInt(1));
            out.setStartTime(cursor.getString(2));
            out.setEndTime(cursor.getString(3));
            out.setReason(cursor.getString(4));

            cursor.moveToNext();

            outList.add(out);
        }

        cursor.close();

        db.close();

        return outList;
    }

    public void modifyOutStatus(Out out) {

        final SQLiteDatabase db = this.getWritableDatabase();

        String query =
                "UPDATE Out SET status = "
                + out.getStatusCode()
                + " WHERE start_date = '"
                + out.getStartTime()
                + "'"
                + " AND end_date = '"
                + out.getEndTime()
                + "'"
                + " AND reason = '"
                + out.getReason()
                + "'";

        db.execSQL(query);

        db.close();
    }
}
