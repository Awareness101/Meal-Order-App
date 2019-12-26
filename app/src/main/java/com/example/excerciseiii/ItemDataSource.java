package com.example.excerciseiii;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;

public class ItemDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] allColumns = {MySQLiteHelper.COLUMN_ID, MySQLiteHelper.COLUMN_NAME, MySQLiteHelper.COLUMN_PRICE};

    public ItemDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createItem(String name, double price) {
        ContentValues values = new ContentValues();
        values.put(MySQLiteHelper.COLUMN_NAME, name);
        values.put(MySQLiteHelper.COLUMN_PRICE, price);
        long insertId = database.insert(MySQLiteHelper.TABLE_ITEM, null, values);
        System.out.println("Item inserted with name: " + name + " and price: " + price);
    }

    public void deleteItem(Item item) {
        long id = item.getId();
        database.delete(MySQLiteHelper.TABLE_ITEM, MySQLiteHelper.COLUMN_ID + " = " + id, null);
        System.out.println("Item deleted with id: " + id);
    }

    public LinkedList<Item> getAllItems() {
        LinkedList<Item> items = new LinkedList<Item>();
        Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEM,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        while (!cursor.isAfterLast()) {
            Item item = cursorToItem(cursor);
            items.add(item);
            cursor.moveToNext();
        }
        cursor.close();
        return items;
    }

    public void update(String name, double price){
        System.out.println("%%%%%%%%%%%%%%%%%%%" + price);
        ContentValues content = new ContentValues();
        content.put(MySQLiteHelper.COLUMN_PRICE, price);
        database.update(MySQLiteHelper.TABLE_ITEM, content, MySQLiteHelper.COLUMN_NAME + " LIKE '" + name + "'", null);
    }

    public void clear(){
        database.delete(MySQLiteHelper.TABLE_ITEM, null, null);
        database.delete(MySQLiteHelper.TABLE_JOIN_TABLE, null, null);
    }

    private Item cursorToItem(Cursor cursor) {
        return new Item(cursor.getInt(0), cursor.getString(1), cursor.getDouble(2));
    }
}
