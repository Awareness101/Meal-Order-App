package com.example.excerciseiii;


import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

public class CreateItemActivity extends BaseActivity  {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_create_item);

        Button submit = findViewById(R.id.create);
        final EditText name = findViewById(R.id.name);
        final EditText price = findViewById(R.id.price);

        final ItemDataSource datasource = new ItemDataSource(this);
        datasource.open();

        submit.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {
                datasource.createItem(name.getText().toString(), Double.parseDouble(price.getText().toString()));
                Toast toast = Toast.makeText(getApplicationContext(), "Created " + name.getText().toString(), Toast.LENGTH_LONG);
                toast.show();
            }
        });
    }
}
