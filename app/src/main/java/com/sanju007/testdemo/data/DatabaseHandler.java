package com.sanju007.testdemo.data;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;
import android.util.Log;

import androidx.annotation.Nullable;

import com.sanju007.testdemo.model.Item;
import com.sanju007.testdemo.util.Constants;

import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

public class DatabaseHandler extends SQLiteOpenHelper {

    Context context;
    public DatabaseHandler(@Nullable Context context) {
        super(context, Constants.DB_NAME, null, Constants.DB_VERSION);
        this.context = context;
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        String CREATE_ITEM_TABLE = "CREATE TABLE " + Constants.TABLE_NAME + "("
                + Constants.KEY_ID + " INTEGER PRIMARY KEY,"
                + Constants.KEY_ITEM_NAME + " TEXT,"
                + Constants.KEY_COLOR + " TEXT,"
                + Constants.KEY_SIZE + " INTEGER,"
                + Constants.KEY_QUANTITY + " INTEGER,"
                + Constants.KEY_ADDED_DATE + " LONG);";

        db.execSQL(CREATE_ITEM_TABLE);
        Log.d("Query", "onCreate: "+CREATE_ITEM_TABLE);
        Log.d("MyTag", "onCreate: TAble created success");
    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int oldVersion, int newVersion) {

        db.execSQL("DROP TABLE IF EXISTS " + Constants.TABLE_NAME);
        onCreate(db);
    }


    public void addItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();;
        //values.put(Constants.KEY_ID,item.getItemName());
        values.put(Constants.KEY_ITEM_NAME,item.getItemName());
        values.put(Constants.KEY_COLOR,item.getItemColor());
        values.put(Constants.KEY_SIZE,item.getItemSize());
        values.put(Constants.KEY_QUANTITY,item.getItemQuantity());
        values.put(Constants.KEY_ADDED_DATE,java.lang.System.currentTimeMillis());
        //insert the row in database
      long i =  db.insert(Constants.TABLE_NAME,null,values);
        Log.d("MyTag", "addItem: "+i);
    }

    public Item getItem(int id){
        SQLiteDatabase db = this.getReadableDatabase();

        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
                Constants.KEY_ITEM_NAME,
                Constants.KEY_QUANTITY,
                Constants.KEY_SIZE,
                Constants.KEY_COLOR,
                Constants.KEY_ADDED_DATE,
        },Constants.KEY_ID + "=?", new String []{String.valueOf(id)},  null, null, null);

        if(cursor != null){
            cursor.moveToFirst();
        }

        Item item = new Item();
        item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
        item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));
        item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));
        item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
        item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QUANTITY)));
        item.setDateItemAdded(String.valueOf(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ADDED_DATE))));

        //convert time stamp
        DateFormat dateFormat = DateFormat.getDateInstance();
        String formattedDate = dateFormat.format(new Date(item.getDateItemAdded()).getTime());
        item.setDateItemAdded(formattedDate);

        return  item;
    }

    public List<Item> getAllItem(){
        SQLiteDatabase db = this.getReadableDatabase();

        List<Item> itemList = new ArrayList<>();
        Cursor cursor = db.query(Constants.TABLE_NAME, new String[]{Constants.KEY_ID,
                Constants.KEY_ITEM_NAME,
                Constants.KEY_QUANTITY,
                Constants.KEY_SIZE,
                Constants.KEY_COLOR,
                Constants.KEY_ADDED_DATE,
        },null,null,  null, null, Constants.KEY_ADDED_DATE+" DESC ");


        if(cursor != null){
            cursor.moveToFirst();
            do{
                Item item = new Item();
                item.setId(Integer.parseInt(cursor.getString(cursor.getColumnIndex(Constants.KEY_ID))));
                item.setItemName(cursor.getString(cursor.getColumnIndex(Constants.KEY_ITEM_NAME)));
                item.setItemSize(cursor.getInt(cursor.getColumnIndex(Constants.KEY_SIZE)));
                item.setItemColor(cursor.getString(cursor.getColumnIndex(Constants.KEY_COLOR)));
                item.setItemQuantity(cursor.getInt(cursor.getColumnIndex(Constants.KEY_QUANTITY)));
                item.setDateItemAdded(String.valueOf(cursor.getLong(cursor.getColumnIndex(Constants.KEY_ADDED_DATE))));

                //convert time stamp
               /* DateFormat dateFormat = DateFormat.getDateInstance();
                String formattedDate = dateFormat.format(new Date(item.getDateItemAdded()).getTime());
                item.setDateItemAdded(formattedDate);*/

                Timestamp ts=new Timestamp(Long.parseLong(item.getDateItemAdded()));
                Date date=new Date(ts.getTime());
                SimpleDateFormat formatter = new SimpleDateFormat("dd/MM/yyyy");
                String strDate= formatter.format(date);
                item.setDateItemAdded(strDate);
                //add to arrayList
                itemList.add(item);
            }while (cursor.moveToNext());
        }
        return itemList;
    }


    public int  updateItem(Item item){
        SQLiteDatabase db = this.getWritableDatabase();

        ContentValues values = new ContentValues();;
        values.put(Constants.KEY_ITEM_NAME,item.getItemName());
        values.put(Constants.KEY_COLOR,item.getItemColor());
        values.put(Constants.KEY_SIZE,item.getItemSize());
        values.put(Constants.KEY_QUANTITY,item.getItemQuantity());
        values.put(Constants.KEY_ADDED_DATE,java.lang.System.currentTimeMillis());

        //update Row
        return db.update(Constants.TABLE_NAME,values,Constants.KEY_ID+ "=?",new String[]{String.valueOf(item.getId())});
     //   return 0;
    }

    public void deleteItem(int id){
        SQLiteDatabase db = this.getWritableDatabase();
        db.delete(Constants.TABLE_NAME,Constants.KEY_ID+"=?",new String[]{String.valueOf(id)});
    }

    public int getCount(){
        String query = "Select * from " + Constants.TABLE_NAME;
        SQLiteDatabase sqLiteDatabase = this.getReadableDatabase();
        Cursor cursor = sqLiteDatabase.rawQuery(query, null);
        return cursor.getCount();
    }
}
