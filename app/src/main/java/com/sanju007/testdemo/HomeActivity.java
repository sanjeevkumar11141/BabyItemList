package com.sanju007.testdemo;

import android.app.AlertDialog;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.android.material.snackbar.Snackbar;
import com.sanju007.testdemo.data.DatabaseHandler;
import com.sanju007.testdemo.model.Item;
import com.sanju007.testdemo.util.Constants;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;

import android.os.Handler;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import java.util.List;

public class HomeActivity extends AppCompatActivity implements View.OnClickListener {

    private AlertDialog.Builder builder;
    private AlertDialog dialog;
    Button button;
    EditText enterItem, quantity, color, size;
    DatabaseHandler databaseHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        databaseHandler = new DatabaseHandler(this);
       /*List<Item> list = databaseHandler.getAllItem();
       for(Item item :list){
           Log.d("MAIN", "onCreate: "+item.getDateItemAdded());
       }*/
        FloatingActionButton fab = findViewById(R.id.fab);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createPopupDialog();
            }
        });
    }


    private void createPopupDialog() {
        Context context;
        builder = new AlertDialog.Builder(this);
        View view = getLayoutInflater().inflate(R.layout.popup, null);
        enterItem = view.findViewById(R.id.enterItem);
        size = view.findViewById(R.id.size);
        color = view.findViewById(R.id.color);
        quantity = view.findViewById(R.id.quantity);
        button = view.findViewById(R.id.button);
        button.setOnClickListener(HomeActivity.this);
        builder.setView(view);
        dialog = builder.create();
        dialog.show();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.button:
                if (!enterItem.getText().toString().isEmpty()
                        && !color.getText().toString().isEmpty()
                        && !size.getText().toString().isEmpty()
                        && !quantity.getText().toString().isEmpty()) {
                    saveItem(v);
                }else {
                    Snackbar.make(v, "Empty field not allowed.", Snackbar.LENGTH_LONG)
                            .setAction("Action", null).show();
                }

                break;
        }
    }

    private void saveItem(View view) {
        Item item = new Item();
        String itemName = enterItem.getText().toString();
        String colorStr = color.getText().toString();
        int quantit = Integer.parseInt(quantity.getText().toString());
        int siz = Integer.parseInt(size.getText().toString());

        item.setItemName(itemName);
        item.setItemColor(colorStr);
        item.setItemQuantity(quantit);
        item.setItemSize(siz);

        databaseHandler.addItem(item);

        Snackbar.make(view, "Saved in Database", Snackbar.LENGTH_LONG)
                .setAction("Action", null).show();
       new Handler().postDelayed(new Runnable() {
           @Override
           public void run() {
               dialog.dismiss();
               Intent intent = new Intent(HomeActivity.this,ListActivity.class);
               startActivity(intent);
           }
       },1200);
    }
}
