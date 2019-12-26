package com.example.excerciseiii;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public class PurchaseDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;

    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_NAME,
            MySQLiteHelper.COLUMN_QUANTITY
    };

    public PurchaseDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void clear(){
        database.delete(MySQLiteHelper.TABLE_PURCHASE, null, null);
    }

    public LinkedList<Purchase> getAllPurchases(){

        Cursor cursor = database.query(MySQLiteHelper.TABLE_PURCHASE, allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        LinkedList<Purchase> purchases = new LinkedList<>();
        while(!cursor.isAfterLast()) {
            purchases.add(new Purchase(cursor.getInt(0), cursor.getString(1), cursor.getInt(2)));
            cursor.moveToNext();
        }
        cursor.close();
        return purchases;
    }

    public void addPurchase(String name){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_PURCHASE, allColumns, MySQLiteHelper.COLUMN_NAME + " = '" + name + "'", null, null, null, null);
        cursor.moveToFirst();
        int qty = 0;
        int i = 0;
        while(!cursor.isAfterLast()) {
            qty = cursor.getInt(2);
            i++;
            cursor.moveToNext();
        }
        // Assume no entries in purchase has the same name
        if(i == 0) {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_NAME, name);
            values.put(MySQLiteHelper.COLUMN_QUANTITY, 1);
            long combo_id = database.insert(MySQLiteHelper.TABLE_PURCHASE, null, values);
        } else {
            ContentValues values = new ContentValues();
            values.put(MySQLiteHelper.COLUMN_QUANTITY, qty + 1);
            database.update(MySQLiteHelper.TABLE_PURCHASE, values, MySQLiteHelper.COLUMN_NAME + " = '" + name + "'", null);
        }
    }
}
