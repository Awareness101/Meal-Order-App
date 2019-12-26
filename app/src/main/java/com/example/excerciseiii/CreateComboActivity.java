package com.example.excerciseiii;

import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
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

import java.util.Iterator;
import java.util.LinkedList;
import java.util.ListIterator;

public class CreateComboActivity extends BaseActivity {

    LinkedList<TableRow> rows = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_combo);

        rows = new LinkedList<>();

        final TableLayout table = (TableLayout) findViewById(R.id.tableLayout);
        final Button submit = findViewById(R.id.create);
        final EditText price = findViewById(R.id.price);

        final ComboDataSource combo_source = new ComboDataSource(this);
        final ItemDataSource item_source = new ItemDataSource(this);

        combo_source.open();
        item_source.open();

        LinkedList<Item> items = item_source.getAllItems();
        ListIterator<Item> iterator = items.listIterator();

        // Add header
        TableRow header = new TableRow(this);
        header.setGravity(Gravity.CENTER_HORIZONTAL);
        header.setPadding(10, 10, 10, 10);
        header.setBackgroundColor(Color.DKGRAY);
        TableRow.LayoutParams header_lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        header_lp.setMargins(4, 4, 4, 4);
        header.setLayoutParams(header_lp);

        TextView row_name = new TextView(this);

        row_name.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        row_name.setTextColor(Color.WHITE);
        row_name.setTypeface(null, Typeface.BOLD);
        row_name.setText("Name");

        header.addView(row_name);

        table.addView(header, 0);

        int i = 1;
        while(iterator.hasNext()){
            Item current = iterator.next();

            TableRow row = new TableRow(this);

            row.setGravity(Gravity.CENTER_HORIZONTAL);
            row.setPadding(0, 0, 0, 0);
            row.setBackgroundColor(Color.DKGRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            lp.setMargins(0, 4, 4, 0);
            row.setLayoutParams(lp);

            CheckBox checkbox = new CheckBox(this);

            checkbox.setId(current.getId());
            checkbox.setText(current.getName());
            checkbox.setTextColor(Color.BLACK);
            checkbox.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
            checkbox.setTextColor(Color.WHITE);

            row.addView(checkbox);

            rows.add(row);

            table.addView(row, i);
            i++;
        }

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                LinkedList<String> items = new LinkedList<>();
                Iterator<TableRow> iterator = rows.listIterator();
                while (iterator.hasNext()) {
                    TableRow current = iterator.next();
                    if (((CheckBox) current.getVirtualChildAt(0)).isChecked()) {
                        items.add(((CheckBox) current.getVirtualChildAt(0)).getText().toString());
                    }
                }
                combo_source.createCombo(items, Double.parseDouble(price.getText().toString()));
                Toast toast = Toast.makeText(getApplicationContext(), "Inserted combo with price "  + price.getText().toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
