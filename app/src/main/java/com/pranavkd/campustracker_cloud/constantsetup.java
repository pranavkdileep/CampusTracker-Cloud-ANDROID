package com.pranavkd.campustracker_cloud;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

public class constantsetup extends SQLiteOpenHelper {

        private static final String DATABASE_NAME = "app_database";
        private static final int DATABASE_VERSION = 1;
        private static final String TABLE_CONST = "consta";
        private static final String COLUMN_URL = "url";
        private static final String COLUMN_KEY = "kry";

        public constantsetup(Context context) {
            super(context, DATABASE_NAME, null, DATABASE_VERSION);
        }

        @Override
        public void onCreate(SQLiteDatabase db) {
            String CREATE_USERS_TABLE = "CREATE TABLE " + TABLE_CONST + "("
                    + COLUMN_URL + " TEXT PRIMARY KEY,"
                    + COLUMN_KEY + " TEXT" + ")";
            db.execSQL(CREATE_USERS_TABLE);
        }

        @Override
        public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
            db.execSQL("DROP TABLE IF EXISTS " + TABLE_CONST);
            onCreate(db);
        }

        public boolean isLoginDetailsExist() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_CONST, new String[] { COLUMN_URL, COLUMN_KEY }, null, null, null, null, null);
            boolean exist = (cursor.getCount() > 0);
            cursor.close();
            db.close();
            return exist;
        }

        public void addUser(String username, String password) {
            if(isLoginDetailsExist()) {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_URL, username);
                values.put(COLUMN_KEY, password);
                db.update(TABLE_CONST, values, null, null);
                db.close();
            } else {
                SQLiteDatabase db = this.getWritableDatabase();
                ContentValues values = new ContentValues();
                values.put(COLUMN_URL, username);
                values.put(COLUMN_KEY, password);
                db.insert(TABLE_CONST, null, values);
                db.close();
            }
        }

        public String getURL() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_CONST, new String[] { COLUMN_URL, COLUMN_KEY }, null, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            String url = cursor.getString(0);
            cursor.close();
            db.close();
            return url;
        }
        public String getKey() {
            SQLiteDatabase db = this.getReadableDatabase();
            Cursor cursor = db.query(TABLE_CONST, new String[] { COLUMN_URL, COLUMN_KEY }, null, null, null, null, null);
            if (cursor != null)
                cursor.moveToFirst();
            String key = cursor.getString(1);
            cursor.close();
            db.close();
            return key;
        }


}


