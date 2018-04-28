package com.lamra.smd.lamra.java;

/**
 * Created by NimraArif on 4/27/2018.
 */

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.util.JsonReader;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import android.support.v7.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.lamra.smd.lamra.R;

import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.ArrayList;

/**
 * Created by ishrat.fatima on 2/10/2018.
 */

public class MyViewHolder extends RecyclerView.ViewHolder{

    public TextView Name;
    public ImageView profilepic;
    public TextView description;
    public ImageView picture;
    private DatabaseReference mDatabase;
    private FirebaseAuth mAuth;

    public MyViewHolder(View view) {
        super(view);
        mDatabase= FirebaseDatabase.getInstance().getReference();
        mAuth = FirebaseAuth.getInstance();
        Name = (TextView) view.findViewById(R.id.name);
        profilepic = (ImageView) view.findViewById(R.id.profilePic);
        picture = (ImageView) view.findViewById(R.id.imageView);
    }

    public void setValues(Post post, Context c)
    {

        Name.setText(post.getUserName());
        description.setText(post.getDescription());

        new ImageDownloaderAsync(picture,c).execute(post.getPicture());
        new ThumbnailImageAsync(profilepic,c).execute(post.getProfilePic());

    }


}
