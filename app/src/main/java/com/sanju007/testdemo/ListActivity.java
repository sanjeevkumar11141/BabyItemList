package com.sanju007.testdemo;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.view.View;

import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.sanju007.testdemo.adapter.MyRecyclerViewAdapter;
import com.sanju007.testdemo.data.DatabaseHandler;
import com.sanju007.testdemo.model.Item;

import java.util.ArrayList;
import java.util.List;

public class ListActivity extends AppCompatActivity {
    private static final String TAG = "ListActivity";
    RecyclerView recyclerView;
    DatabaseHandler databaseHandler;
    List<Item> itemList ;
    FloatingActionButton floatingActionButton;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_list);
        recyclerView= findViewById(R.id.recyclerView);
        floatingActionButton= findViewById(R.id.floatingActionButton);
        databaseHandler = new DatabaseHandler(ListActivity.this);
        itemList = new ArrayList<>();

        itemList =  databaseHandler.getAllItem();
        for(Item item : itemList){
            Log.d(TAG, item.getItemName());
        }
        setDataINList();

        floatingActionButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
     public void setDataINList(){

         LinearLayoutManager llm = new LinearLayoutManager(this);
         llm.setOrientation(LinearLayoutManager.VERTICAL);
         recyclerView.setLayoutManager(llm);
         MyRecyclerViewAdapter adapter = new MyRecyclerViewAdapter(ListActivity.this,itemList);
         recyclerView.setAdapter(adapter);
         adapter.notifyDataSetChanged();
     }
}
