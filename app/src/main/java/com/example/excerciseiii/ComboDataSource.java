package com.example.excerciseiii;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.SQLException;
import android.database.sqlite.SQLiteDatabase;

import java.util.LinkedList;
import java.util.ListIterator;

public class ComboDataSource {

    private SQLiteDatabase database;
    private MySQLiteHelper dbHelper;
    private String[] idColumn = {
            MySQLiteHelper.COLUMN_ID,
    };
    private String[] allColumns = {
            MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.COLUMN_PRICE
    };
    private String[] fullCombo = {
            MySQLiteHelper.TABLE_COMBO + "." + MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.TABLE_COMBO + "." + MySQLiteHelper.COLUMN_PRICE,
            MySQLiteHelper.TABLE_ITEM + "." + MySQLiteHelper.COLUMN_NAME,
    };
    private String[] joinAllColumns = {
            MySQLiteHelper.TABLE_COMBO + "." + MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.TABLE_COMBO + "." + MySQLiteHelper.COLUMN_PRICE,
            MySQLiteHelper.TABLE_ITEM + "." + MySQLiteHelper.COLUMN_ID,
            MySQLiteHelper.TABLE_ITEM + "." + MySQLiteHelper.COLUMN_PRICE,
            MySQLiteHelper.TABLE_ITEM + "." + MySQLiteHelper.COLUMN_NAME,
    };
    private String join = MySQLiteHelper.TABLE_ITEM + " JOIN " + MySQLiteHelper.TABLE_JOIN_TABLE + " ON "
            + MySQLiteHelper.TABLE_ITEM + "." + MySQLiteHelper.COLUMN_ID + " = " + MySQLiteHelper.COLUMN_I_ID
            + " JOIN " + MySQLiteHelper.TABLE_COMBO + " ON " + MySQLiteHelper.TABLE_COMBO + "."
            + MySQLiteHelper.COLUMN_ID + " = " + MySQLiteHelper.COLUMN_C_ID;

    public ComboDataSource(Context context) {
        dbHelper = new MySQLiteHelper(context);
    }

    public void open() throws SQLException {
        database = dbHelper.getWritableDatabase();
    }

    public void close() {
        dbHelper.close();
    }

    public void createCombo(LinkedList<String> items, double price) {
        // INSERT PRICE INTO COMBO
        ContentValues combo_values = new ContentValues();
        combo_values.put(MySQLiteHelper.COLUMN_PRICE, price);
        long combo_id = database.insert(MySQLiteHelper.TABLE_COMBO, null, combo_values);
        System.out.println("$$$$$$$$$$$$$$$$" + combo_id);

        // FOR EACH item, INSERT INTO JOIN_TABLE
        int item_id = 0;
        ListIterator <String> iterator = items.listIterator();
        while(iterator.hasNext()){
            String current = iterator.next();

            // GET ITEM ID TO INSERT INTO JOIN_TABLE
            Cursor cursor = database.query(MySQLiteHelper.TABLE_ITEM, idColumn, MySQLiteHelper.COLUMN_NAME + " LIKE '" + current + "'"
                    , null, null, null, null);
            cursor.moveToFirst();
            item_id = cursor.getInt(0);
            cursor.close();

            ContentValues item_values = new ContentValues();
            item_values.put(MySQLiteHelper.COLUMN_C_ID, combo_id);
            item_values.put(MySQLiteHelper.COLUMN_I_ID, item_id);
            database.insert(MySQLiteHelper.TABLE_JOIN_TABLE, null, item_values);
        }
        System.out.println("Combo inserted with price: " + price);
    }

    public Combo getCombo(String id){
        String query = "SELECT COMBO.ID, ITEM.NAME, COMBO.PRICE FROM ITEM JOIN JOIN_TABLE ON ITEM.ID "
                + "= I_ID JOIN COMBO ON COMBO.ID = C_ID WHERE C_ID = " + id + ";";
        System.out.println(query);
        Cursor cursor = database.rawQuery(query, null);
        cursor.moveToFirst();
        int c_id = 0;
        LinkedList<Item> items = new LinkedList<>();
        double price = 0;
        while(!cursor.isAfterLast()) {
            c_id = cursor.getInt(0);
            items.add(new Item(cursor.getString(1)));
            price = cursor.getDouble(2);
            System.out.println(c_id + " " + price);
            cursor.moveToNext();
        }
        cursor.close();
        return new Combo(c_id, items, price);
    }

    public void deleteCombo(int id) {
        database.delete(MySQLiteHelper.TABLE_COMBO, MySQLiteHelper.COLUMN_ID + " = " + id, null);
        database.delete(MySQLiteHelper.TABLE_JOIN_TABLE, MySQLiteHelper.COLUMN_C_ID + " = " + id, null);
        System.out.println("Combo deleted with id: " + id);
    }

    public LinkedList<String> getCombosId(){
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMBO,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        LinkedList<String> list = new LinkedList<>();
        while (!cursor.isAfterLast()) {
            list.add(cursor.getInt(0) + "");
            cursor.moveToNext();
        }
        cursor.close();
        return list;
    }

    public LinkedList<Combo> getAllCombos() {
        Cursor cursor = database.query(MySQLiteHelper.TABLE_COMBO,
                allColumns, null, null, null, null, null);
        cursor.moveToFirst();
        LinkedList<Combo> comboList = new LinkedList<>();
        while (!cursor.isAfterLast()) {
            if(!contains(comboList, cursor.getInt(1), cursor.getInt(2), cursor.getDouble(3), cursor.getString(4))){
                LinkedList<Item> current_combo_items = new LinkedList<>();
                current_combo_items.add(new Item(cursor.getInt(2), cursor.getString(4), cursor.getDouble(3)));
                comboList.add(new Combo(cursor.getInt(0), current_combo_items, cursor.getDouble(1)));
            }
            cursor.moveToNext();
        }
        cursor.close();
        return comboList;
    }

    public void clear(){
        database.delete(MySQLiteHelper.TABLE_COMBO, null, null);
    }

    // True -- added item to combo in list, do not add to list (above)
    // False -- didn't add item to combo in list, add current combo to list (above)
    public Boolean contains(LinkedList<Combo> comboList, int c_id, int i_id, double price, String item){
        ListIterator<Combo> iterator = comboList.listIterator();
        while(iterator.hasNext()){
            Combo combo = iterator.next();
            // If c_id already in list, then add to Item list inside Combo
            if(combo.getId() == c_id){
                combo.getItems().add(new Item(i_id, item, price));
                return true;
            }
        }
        return false;
    }
}
