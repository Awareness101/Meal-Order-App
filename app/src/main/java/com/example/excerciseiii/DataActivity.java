package com.example.excerciseiii;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.LinearLayout;
import android.widget.TextView;

import java.util.ListIterator;

public class DataActivity extends BaseActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_data);

        ComboDataSource datasource = new ComboDataSource(this);
        datasource.open();

        String id = getIntent().getStringExtra("id");
        final String from = getIntent().getStringExtra("from");

        // Only Item's name is populated!
        Combo combo = datasource.getCombo(id);

        LinearLayout layout = (LinearLayout) findViewById(R.id.linearLayout);

        TextView current_id = findViewById(R.id.id_data_label);
        TextView current_price = findViewById(R.id.price_data_label);

        current_id.append(" " + combo.getId());
        current_price.append(" " + combo.getPrice());

        ListIterator<Item> iterator = combo.getItems().listIterator();
        int i = 3;
        while(iterator.hasNext()){
            Item current = iterator.next();
            TextView view = new TextView(this);
            view.setText("      - " + current.getName());
            view.setTextSize(30);
            layout.addView(view, i);
            i++;
        }

        Button back = findViewById(R.id.back);

        back.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                if(from.equals("ListPurchaseActivity")) {
                    Intent intent = new Intent(getBaseContext(), ListPurchaseActivity.class);
                    startActivity(intent);
                } else if(from.equals("ListComboActivity")){
                    Intent intent = new Intent(getBaseContext(), ListComboActivity.class);
                    startActivity(intent);
                }
            }
        });
    }
}
