package com.example.excerciseiii;

import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;

public abstract class BaseActivity extends AppCompatActivity {

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.menu_file, menu);
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        Intent intent;
        switch (item.getItemId()) {
            case R.id.home:
                intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                return true;
            case R.id.createItem:
                intent = new Intent(this, CreateItemActivity.class);
                startActivity(intent);
                return true;
            case R.id.createCombo:
                intent = new Intent(this, CreateComboActivity.class);
                startActivity(intent);
                return true;
            case R.id.listItem:
                intent = new Intent(this, ListItemActivity.class);
                startActivity(intent);
                return true;
            case R.id.listCombo:
                intent = new Intent(this, ListComboActivity.class);
                startActivity(intent);
                return true;
            case R.id.listPurchase:
                intent = new Intent(this, ListPurchaseActivity.class);
                startActivity(intent);
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

}
