package com.example.excerciseiii;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

public class MySQLiteHelper extends SQLiteOpenHelper {

    public static final String TABLE_ITEM = "item";
    public static final String TABLE_COMBO = "combo";
    public static final String TABLE_JOIN_TABLE = "join_table";
    public static final String TABLE_PURCHASE = "purchase";

    public static final String COLUMN_ID = "id";
    public static final String COLUMN_NAME = "name";
    public static final String COLUMN_PRICE = "price";
    public static final String COLUMN_I_ID = "i_id";
    public static final String COLUMN_C_ID = "c_id";
    public static final String COLUMN_QUANTITY = "quantity";

    private static final String DATABASE_NAME  = "applicationdata";
    private static final int DATABASE_VERSION  = 1;

    private static final String CREATE_ITEM = "CREATE TABLE " + TABLE_ITEM + " (" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_PRICE + " DOUBLE);";

    private static final String CREATE_COMBO = "CREATE TABLE " + TABLE_COMBO + " (" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_PRICE + " DOUBLE);";

    private static final String CREATE_JOIN_TABLE = "CREATE TABLE " + TABLE_JOIN_TABLE + " (" + COLUMN_C_ID
            + " INTEGER, " + COLUMN_I_ID + " INTEGER, FOREIGN KEY (" + COLUMN_C_ID + ") REFERENCES "
            + TABLE_COMBO + "(" + COLUMN_ID + "), FOREIGN KEY (" + COLUMN_I_ID + ") REFERENCES "
            + TABLE_ITEM + "(" + COLUMN_ID + "));";

    private static final String CREATE_PURCHASE = "CREATE TABLE " + TABLE_PURCHASE + " (" + COLUMN_ID
            + " INTEGER PRIMARY KEY AUTOINCREMENT, " + COLUMN_NAME + " TEXT, " + COLUMN_QUANTITY + " INTEGER);";

    public MySQLiteHelper(Context context) {
        super(context, DATABASE_NAME, null, DATABASE_VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase database) {
        database.execSQL(CREATE_ITEM);
        database.execSQL(CREATE_COMBO);
        database.execSQL(CREATE_JOIN_TABLE);
        database.execSQL(CREATE_PURCHASE);
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {
        Log.w(MySQLiteHelper.class.getName(), "Upgrading database from version "
                + oldVersion + " to " + newVersion + ", which will destroy all old data");
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_ITEM);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_COMBO);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_JOIN_TABLE);
        db.execSQL("DROP TABLE IF EXISTS " + TABLE_PURCHASE);
        onCreate(db);
    }
}
