package com.sanju007.testdemo.adapter;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.sanju007.testdemo.R;
import com.sanju007.testdemo.model.Item;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyRecyclerViewAdapter.MyViewHolder> {

    Context context;
    List<Item> itemList;
    public MyRecyclerViewAdapter(Context context, List<Item> itemList){
        this.context=context;
        this.itemList = itemList;
    }
    @NonNull
    @Override
    public MyRecyclerViewAdapter.MyViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.list_row, parent, false);
        return new MyViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull MyRecyclerViewAdapter.MyViewHolder holder, int position) {

        Item item = itemList.get(position);
        holder.itemName.setText(item.getItemName());
        holder.itemColor.setText(item.getItemColor());
        holder.itemSize.setText(String.valueOf(item.getItemSize()));
        holder.itemQuantity.setText(String.valueOf(item.getItemQuantity()));
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }


    public class MyViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView itemName, itemQuantity, itemColor, itemSize;
        Button editButton,deleteButton;
        public MyViewHolder(@NonNull View itemView) {
            super(itemView);

            itemName = itemView.findViewById(R.id.itemName);
            itemQuantity = itemView.findViewById(R.id.itemQuantity);
            itemColor = itemView.findViewById(R.id.itemColor);
            itemSize = itemView.findViewById(R.id.itemSize);
            editButton = itemView.findViewById(R.id.editButton);
            deleteButton = itemView.findViewById(R.id.deleteButton);
            editButton.setOnClickListener(this);
            deleteButton.setOnClickListener(this);

        }

        @Override
        public void onClick(View v) {
            switch (v.getId()){
                case R.id.deleteButton:
                    break;

                case R.id.editButton:
                    break;
            }
        }
    }

}
