package com.example.excerciseiii;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.InputType;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.EditText;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import androidx.constraintlayout.widget.ConstraintLayout;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class ListItemActivity extends BaseActivity {

    LinkedList<TableRow> rows = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_item);

        rows = new LinkedList<>();

        final ConstraintLayout layout = findViewById(R.id.layout);
        final TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        final ComboDataSource combo_source = new ComboDataSource(this);
        final ItemDataSource item_source = new ItemDataSource(this);
        final PurchaseDataSource purchase_source = new PurchaseDataSource(this);

        combo_source.open();
        item_source.open();
        purchase_source.open();

        LinkedList<Item> items = item_source.getAllItems();
        ListIterator<Item> iterator = items.listIterator();

        // Add header
        TableRow header = new TableRow(this);
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        header.setPadding(0, 0, 0, 0);
        header.setBackgroundColor(Color.DKGRAY);
        TableRow.LayoutParams header_lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        header_lp.setMargins(4, 0, 4, 0);
        header.setLayoutParams(header_lp);

        TextView row_id = new TextView(this);
        TextView row_name = new TextView(this);
        TextView row_price = new TextView(this);

        row_id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        row_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        row_price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

        row_id.setTextColor(Color.WHITE);
        row_name.setTextColor(Color.WHITE);
        row_price.setTextColor(Color.WHITE);

        row_id.setTypeface(null, Typeface.BOLD);
        row_name.setTypeface(null, Typeface.BOLD);
        row_price.setTypeface(null, Typeface.BOLD);


        row_id.setText("ID");
        row_name.setText("Name");
        row_price.setText("Price");

        header.addView(row_id);
        header.addView(row_name);
        header.addView(row_price);

        table.addView(header, 0);

        // Add each row
        int i = 1;
        while(iterator.hasNext()){
            Item current = iterator.next();

            TableRow row = new TableRow(this);

            row.setGravity(Gravity.CENTER);
            row.setPadding(0, 0, 0, 0);
            row.setBackgroundColor(Color.DKGRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            lp.setMargins(4, 0, 4, 0);
            row.setLayoutParams(lp);

            CheckBox checkbox = new CheckBox(this);
            TextView name = new TextView(this);
            EditText price = new EditText(this);

            price.setInputType(InputType.TYPE_CLASS_NUMBER | InputType.TYPE_NUMBER_FLAG_DECIMAL);

            checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);
            price.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            checkbox.setTextColor(Color.WHITE);
            name.setTextColor(Color.WHITE);
            price.setTextColor(Color.WHITE);

            name.setText(current.getName());
            price.setText(current.getPrice() + "");

            System.out.println("TEST &&&&&&&" + current.getPrice());

            row.addView(checkbox);
            row.addView(name);
            row.addView(price);

            rows.add(row);

            table.addView(row, i);
            i++;
        }

        Button update = findViewById(R.id.update);
        update.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
            Iterator<TableRow> iterator = rows.listIterator();
            while(iterator.hasNext()){
                TableRow current = iterator.next();
                if(((CheckBox) current.getVirtualChildAt(0)).isChecked()){
                    item_source.update(((TextView) current.getVirtualChildAt(1)).getText().toString(), Double.parseDouble(((EditText) current.getVirtualChildAt(2)).getText().toString()));
                    Toast toast = Toast.makeText(getApplicationContext(), "Updated database for " + ((TextView) current.getVirtualChildAt(1)).getText().toString(), Toast.LENGTH_LONG);
                    toast.show();
                }
            }
            }
        });

        Button clear = findViewById(R.id.clear_button);
        clear.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                combo_source.clear();
                item_source.clear();
                purchase_source.clear();
                Toast toast = Toast.makeText(getApplicationContext(), "Cleared database", Toast.LENGTH_LONG);
                toast.show();
            }
        });

        Button purchase = findViewById(R.id.purchase_item);
        purchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                Iterator<TableRow> iterator = rows.listIterator();
                while(iterator.hasNext()){
                    TableRow current = iterator.next();
                    if(((CheckBox) current.getVirtualChildAt(0)).isChecked()){
                        purchase_source.addPurchase(((TextView) current.getVirtualChildAt(1)).getText().toString());
                        Toast toast = Toast.makeText(getApplicationContext(), "Added purchase for " + ((TextView) current.getVirtualChildAt(1)).getText().toString(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
    }
}
