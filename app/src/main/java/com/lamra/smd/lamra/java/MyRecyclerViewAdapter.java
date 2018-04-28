package com.lamra.smd.lamra.java;

/**
 * Created by NimraArif on 4/27/2018.
 */
import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.database.DatabaseReference;

import java.util.List;

public class MyRecyclerViewAdapter extends RecyclerView.Adapter<MyViewHolder> {
    private List<Post> items;
    private int itemLayout;

    Context c;
    public MyRecyclerViewAdapter(List<Post> items, int itemLayout,Context c) {
        this.items = items;
        this.itemLayout = itemLayout;
        this.c=c;


    }
    @Override
    public MyViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(itemLayout, parent, false);
        return new MyViewHolder(v);
    }
    @Override public void onBindViewHolder(MyViewHolder holder, int position) {
        if(items != null && holder != null)
        {

            holder.setValues(items.get(position),c);
       /* holder.name.setText(items.get(position).getName());
        holder.rb.setRating(items.get(position).getRating());
        holder.detail.setText(items.get(position).getDetail());
        holder.picture.setImageResource(items.get(position).getPicture());  */
        }
    }

    @Override public int getItemCount() {
        if(items != null)
            return items.size();
        else
            return 0;
    }

    @Override
    public int getItemViewType(int position) {

        return super.getItemViewType(position);
    }
}



