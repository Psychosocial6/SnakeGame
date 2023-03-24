package com.example.snakegame;


import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import java.util.ArrayList;
import java.util.List;

public class MyDB {
    private static final int DATABASE_VERSION = 3;
    private static final String DATABASE_NAME = "best_score.db";
    private static final String TABLE_NAME = "score";
    private static final String COLUMN_ID = "id";
    private static final String COLUMN_RESULT = "result";
    private static final int NUM_COLUMN_ID = 0;
    private static final int NUM_COLUMN_RESULT = 1;

    private SQLiteDatabase database;

    public MyDB(Context context) {
        OpenHelper mOpenHelper = new OpenHelper(context);
        database = mOpenHelper.getWritableDatabase();
    }

    public long insert(int record) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESULT, record);
        return database.insert(TABLE_NAME, null, contentValues);
    }

    public int update(ScoreForDB scoreForDB) {
        ContentValues contentValues = new ContentValues();
        contentValues.put(COLUMN_RESULT, scoreForDB.getPoints());
        return database.update(TABLE_NAME, contentValues, COLUMN_ID + " = ?", new String[] { String.valueOf(scoreForDB.getId()) });
    }

    public void deleteAll() {
        database.delete(TABLE_NAME, null, null);
    }

    public ScoreForDB select(long id) {
        Cursor cursor = database.query(TABLE_NAME, null, COLUMN_ID + " = ?", new String[] { String.valueOf(id) }, null, null, null);
        if (cursor.moveToFirst()) {
            int record = cursor.getInt(NUM_COLUMN_RESULT);
            return new ScoreForDB(id, record);
        }
        return null;
    }

    public void delete(long id) {
        database.delete(TABLE_NAME, COLUMN_ID + " = ?", new String[] { String.valueOf(id) });
    }

    public ArrayList<ScoreForDB> selectAll() {
        Cursor cursor = database.query(TABLE_NAME, null, null, null, null, null, null);

        ArrayList<ScoreForDB> arrayList = new ArrayList<ScoreForDB>();
        cursor.moveToFirst();
        if (!cursor.isAfterLast()) {
            do {
                long id = cursor.getLong(NUM_COLUMN_ID);
                int record = cursor.getInt(NUM_COLUMN_RESULT);
                arrayList.add(new ScoreForDB(id, record));
            } while (cursor.moveToNext());
        }
        cursor.close();
        return arrayList;
    }

    private class OpenHelper extends SQLiteOpenHelper {

        public OpenHelper(@Nullable Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String query = "CREATE TABLE " + TABLE_NAME + " (" + COLUMN_ID + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_RESULT + " INT);";
            db.execSQL(query);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_NAME);
            onCreate(db);
        }
    }
}
