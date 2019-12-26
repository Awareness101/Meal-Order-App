package com.example.excerciseiii;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;

import java.util.LinkedList;
import java.util.ListIterator;

public class ListPurchaseActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_purchase);

        final TableLayout table = (TableLayout) findViewById(R.id.purchase_table);

        final PurchaseDataSource datasource = new PurchaseDataSource(this);
        datasource.open();

        LinkedList<Purchase> list = datasource.getAllPurchases();

        // Add header
        TableRow header = new TableRow(this);
        header.setGravity(Gravity.CENTER);
        header.setPadding(10, 10, 10, 10);
        header.setBackgroundColor(Color.DKGRAY);
        TableRow.LayoutParams header_lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        header_lp.setMargins(4, 4, 4, 4);
        header.setLayoutParams(header_lp);

        TextView row_id = new TextView(this);
        TextView row_name = new TextView(this);
        TextView row_qty = new TextView(this);

        row_id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        row_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        row_qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

        row_id.setTextColor(Color.WHITE);
        row_name.setTextColor(Color.WHITE);
        row_qty.setTextColor(Color.WHITE);

        row_id.setTypeface(null, Typeface.BOLD);
        row_name.setTypeface(null, Typeface.BOLD);
        row_qty.setTypeface(null, Typeface.BOLD);

        row_id.setText("ID");
        row_name.setText("Food/Combo #");
        row_qty.setText("Quantity");

        header.addView(row_id);
        header.addView(row_name);
        header.addView(row_qty);

        table.addView(header, 0);

        // Add each row
        ListIterator<Purchase> iterator = list.listIterator();

        int i = 1;
        while(iterator.hasNext()){
            final Purchase current = iterator.next();

            TableRow row = new TableRow(this);

            row.setGravity(Gravity.CENTER);
            row.setPadding(10, 10, 10, 10);
            row.setBackgroundColor(Color.DKGRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            lp.setMargins(4, 4, 4, 4);
            row.setLayoutParams(lp);

            TextView id = new TextView(this);
            TextView name = new TextView(this);
            TextView qty = new TextView(this);

            id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            qty.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

            id.setTextColor(Color.WHITE);
            name.setTextColor(Color.WHITE);
            qty.setTextColor(Color.WHITE);

            id.setText(current.getId() + "");
            name.setText(current.getName());
            qty.setText(current.getQuantity() + "");

            if(ListPurchaseActivity.isStringInt(name.getText().toString())){
                ListComboActivity.makeTextViewHyperlink(name);
                name.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(getBaseContext(), DataActivity.class);
                        intent.putExtra("id", current.getName());
                        intent.putExtra("from", "ListPurchaseActivity");
                        startActivity(intent);
                    }
                });
            }

            row.addView(id);
            row.addView(name);
            row.addView(qty);

            table.addView(row, i);
            i++;
        }



    }

    public static boolean isStringInt(String s){
        try {
            Integer.parseInt(s);
            return true;
        } catch (NumberFormatException ex){
            return false;
        }
    }
}
