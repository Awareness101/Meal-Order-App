package com.example.excerciseiii;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.Typeface;
import android.os.Bundle;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.style.URLSpan;
import android.util.TypedValue;
import android.view.Gravity;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TableLayout;
import android.widget.TableRow;
import android.widget.TextView;
import android.widget.Toast;

import java.util.LinkedList;
import java.util.ListIterator;

public class ListComboActivity extends BaseActivity {

    LinkedList<TableRow> list;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list_combo);

        final ComboDataSource datasource = new ComboDataSource(this);
        final PurchaseDataSource purchase_source = new PurchaseDataSource(this);

        datasource.open();
        purchase_source.open();

        list = new LinkedList<>();

        LinkedList<String> combo_list = datasource.getCombosId();

        TableLayout table = (TableLayout) findViewById(R.id.tableLayout);

        ListIterator<String> iterator = combo_list.listIterator();

        // Add header
        TableRow header = new TableRow(this);
        header.setGravity(Gravity.CENTER);
        header.setPadding(10, 10, 10, 10);
        header.setBackgroundColor(Color.DKGRAY);
        TableRow.LayoutParams header_lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
        header_lp.setMargins(4, 4, 4, 4);
        header.setLayoutParams(header_lp);

        TextView row_yes = new TextView(this);
        TextView row_no = new TextView(this);
        TextView row_id = new TextView(this);

        row_yes.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        row_no.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);
        row_id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 24);

        row_yes.setTextColor(Color.WHITE);
        row_no.setTextColor(Color.WHITE);
        row_id.setTextColor(Color.WHITE);

        row_yes.setTypeface(null, Typeface.BOLD);
        row_no.setTypeface(null, Typeface.BOLD);
        row_id.setTypeface(null, Typeface.BOLD);

        row_yes.setText("Yes");
        row_no.setText("No");
        row_id.setText("ID");

        header.addView(row_yes);
        header.addView(row_no);
        header.addView(row_id);

        table.addView(header, 0);

        int i = 1;
        while(iterator.hasNext()){
            final String current = iterator.next();

            TableRow row = new TableRow(this);
            row.setGravity(Gravity.CENTER);
            row.setPadding(10, 10, 10, 10);
            row.setBackgroundColor(Color.DKGRAY);
            TableRow.LayoutParams lp = new TableRow.LayoutParams(TableLayout.LayoutParams.MATCH_PARENT, TableRow.LayoutParams.WRAP_CONTENT, 1.0f);
            lp.setMargins(4, 4, 4, 4);
            row.setLayoutParams(lp);

            RadioGroup group = new RadioGroup(this);
            RadioButton yes = new RadioButton(this);
            RadioButton no = new RadioButton(this);
            TextView id = new TextView(this);

            group.setOrientation(LinearLayout.HORIZONTAL);

            id.setTextSize(TypedValue.COMPLEX_UNIT_SP, 18);

            yes.setTextColor(Color.WHITE);
            no.setTextColor(Color.WHITE);
            id.setTextColor(Color.WHITE);

            id.setText(current);

            ListComboActivity.makeTextViewHyperlink(id);
            id.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    Intent intent = new Intent(getBaseContext(), DataActivity.class);
                    intent.putExtra("id", current);
                    intent.putExtra("from", "ListComboActivity");
                    startActivity(intent);
                }
            });

            group.addView(yes);
            group.addView(no);
            row.addView(group);
            row.addView(id);

            list.add(row);

            table.addView(row, i);
            i++;
        }

        Button delete = findViewById(R.id.delete_combo);
        delete.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ListIterator<TableRow> iterator = list.listIterator();
                while(iterator.hasNext()){
                    TableRow current = iterator.next();
                    if(((RadioButton)((RadioGroup) current.getVirtualChildAt(0)).getChildAt(0)).isChecked()){
                        datasource.deleteCombo(Integer.parseInt(((TextView) current.getVirtualChildAt(1)).getText().toString()));
                        Toast toast = Toast.makeText(getApplicationContext(), "Deleted combo "
                                + Integer.parseInt(((TextView) current.getVirtualChildAt(1)).getText().toString()), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });

        Button purchase = findViewById(R.id.purchase_combo);
        purchase.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                ListIterator<TableRow> iterator = list.listIterator();
                while(iterator.hasNext()){
                    TableRow current = iterator.next();
                    if(((RadioButton)((RadioGroup) current.getVirtualChildAt(0)).getChildAt(0)).isChecked()){
                        purchase_source.addPurchase(((TextView) current.getVirtualChildAt(1)).getText().toString());
                        Toast toast = Toast.makeText(getApplicationContext(), "Added purchase "
                                + ((TextView) current.getVirtualChildAt(1)).getText().toString(), Toast.LENGTH_LONG);
                        toast.show();
                    }
                }
            }
        });
    }

    public static void makeTextViewHyperlink(TextView tv) {
        SpannableStringBuilder ssb = new SpannableStringBuilder();
        ssb.append(tv.getText());
        ssb.setSpan(new URLSpan("#"), 0, ssb.length(),
                Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        tv.setText(ssb, TextView.BufferType.SPANNABLE);
    }
}
