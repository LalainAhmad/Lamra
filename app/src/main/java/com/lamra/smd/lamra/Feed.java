package com.lamra.smd.lamra;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.DefaultItemAnimator;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.FirebaseError;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageMetadata;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;
import com.lamra.smd.lamra.java.Comment;
import com.lamra.smd.lamra.java.Like;
import com.lamra.smd.lamra.java.MyRecyclerViewAdapter;
import com.lamra.smd.lamra.java.Post;
import com.lamra.smd.lamra.java.User;
import com.lamra.smd.lamra.java.UserF;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.UUID;

public class Feed extends AppCompatActivity {

    private DatabaseReference ref;
    private FirebaseAuth mAuth;
    private RecyclerView recyclerView;
    private ArrayList<Post> posts;
    private MyRecyclerViewAdapter adapter;
    private UserF user=new UserF();
    ArrayList<String> userids;

    FirebaseStorage storage;
    StorageReference storageReference;
    private Button btnChoose, btnUpload;
    private ImageView imageView;
    private Uri filePath;
    private final int PICK_IMAGE_REQUEST = 71;
    private Context c;

    public ImageView getImageView() {
        return imageView;
    }

    public void setImageView(ImageView imageView) {
        this.imageView = imageView;
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feed);
        mAuth=FirebaseAuth.getInstance();
        recyclerView = (RecyclerView) findViewById(R.id.recView);
        adapter = new MyRecyclerViewAdapter(posts, R.layout.activity_row, Feed.this.getBaseContext());
        recyclerView.setAdapter(adapter);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recyclerView.setItemAnimator(new DefaultItemAnimator());
        btnChoose = (Button) findViewById(R.id.btnChoose);
        btnUpload = (Button) findViewById(R.id.btnUpload);
        imageView = (ImageView) findViewById(R.id.imgView);

        storage = FirebaseStorage.getInstance();
        storageReference = storage.getReference();
        c=this;
//        ValueEventListener userAsync= new ValueEventListener() {
//            @Override
//            public void onDataChange(DataSnapshot dataSnapshot) {
//                String temp;
//                if(dataSnapshot.child("name").exists())
//                {
//                    temp = (String) dataSnapshot.child("name").getValue();
//                    user.setName(temp);
//                }
//                if(dataSnapshot.child("profilepic").exists()) {
//                    temp = (String) dataSnapshot.child("profilepic").getValue();
//                    user.setProfilepic(temp);
//                }
//                        user.setUid(mAuth.getCurrentUser().getUid());
//                Toast.makeText(c,"userloaded",Toast.LENGTH_SHORT).show();
//
//            }
//
//            @Override
//            public void onCancelled(DatabaseError databaseError) {
//
//            }
//        };
//
//        FirebaseDatabase.getInstance().getReference().child("users").child(mAuth.getCurrentUser().getUid()).addValueEventListener(userAsync);

        final ValueEventListener followerPostsListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                ArrayList<Post> s = new ArrayList<>();

                if (dataSnapshot.exists()){
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        for (int i = 0; i < userids.size(); i++){
                            if (messageSnapshot.getKey().equals(userids.get(i))){
                                for (DataSnapshot posts: messageSnapshot.child("posts").getChildren()){
                                    Post p = posts.getValue(Post.class);
                                    s.add(new Post(p.getUserName(),p.getProfilePic(),mAuth.getCurrentUser().getUid(),p.getPid(),p.getPicture()));
                                }
                            }
                        }
                    }

                    Collections.shuffle(s);

                    posts = s;
                    adapter = new MyRecyclerViewAdapter(posts, R.layout.activity_row, Feed.this.getBaseContext());
                    recyclerView.setAdapter(adapter);

                }
            }

            @Override
            public void onCancelled(DatabaseError databaseError) {

            }
        };

        ValueEventListener followerListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                // Get Post object and use the values to update the UI
                userids = new ArrayList<>();
                posts = new ArrayList<>();
                if (dataSnapshot.exists()) {
                    for (DataSnapshot messageSnapshot: dataSnapshot.getChildren()) {
                        Post p=messageSnapshot.getValue(Post.class);
                        userids.add(p.getUid());

                    }
                }
                DatabaseReference databaseReference=FirebaseDatabase.getInstance().getReference().child("users");
                databaseReference.addListenerForSingleValueEvent(followerPostsListener);

            }

            @Override
            public void onCancelled(DatabaseError databaseError) {
                // Getting Post failed, log a message
                Log.w("Feed", "loadPost:onCancelled", databaseError.toException());
                // ...
            }
        };

        FirebaseUser f = FirebaseAuth.getInstance().getCurrentUser();
        ref = FirebaseDatabase.getInstance().getReference().child("posts");
        ref.addValueEventListener(followerListener);


        btnChoose.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                chooseImage();
            }
        });

        btnUpload.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                uploadImage();
            }
        });



    }

    private void chooseImage() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(Intent.createChooser(intent, "Select Picture"), PICK_IMAGE_REQUEST);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK
                && data != null && data.getData() != null) {
            filePath = data.getData();
            try {
                Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), filePath);
                imageView.setImageBitmap(bitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }


        }
    }

    private void uploadImage() {

            Bitmap bitmap= ((BitmapDrawable)imageView.getDrawable()).getBitmap();
        if(filePath != null)
        {
            ByteArrayOutputStream stream = new ByteArrayOutputStream();
            bitmap.compress(Bitmap.CompressFormat.JPEG, 70, stream);
            byte[] byteArray = stream.toByteArray();
            StorageReference storage = FirebaseStorage.getInstance().getReference();
            final String id = UUID.randomUUID().toString();
            storage = storage.child("images").child(id+".jpg");
            Toast.makeText(c,"Uploading",Toast.LENGTH_LONG).show();
            StorageMetadata metadata = new StorageMetadata.Builder()
                    .setContentType("image/jpg")
                    .build();

            UploadTask uploadTask = storage.putBytes(byteArray,metadata);

            uploadTask.addOnFailureListener(new OnFailureListener() {
                @Override
                public void onFailure(@NonNull Exception exception) {
                    // Handle unsuccessful uploads
                    Toast.makeText(c,"Failed",Toast.LENGTH_LONG).show();

                }
            });

            uploadTask.addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                // taskSnapshot.getMetadata() contains file metadata such as size, content-type, and download URL.

                Uri downloadUrl = taskSnapshot.getDownloadUrl();
                final Post p = new Post();
                p.setUid(mAuth.getCurrentUser().getUid());
                p.setPicture(downloadUrl.toString());
                p.setPid(id);
                if(user.getProfilepic()!=null)
                p.setProfilePic(user.getProfilepic());
                if(user.getName()!=null)
                p.setUserName(user.getName());
                FirebaseDatabase db = FirebaseDatabase.getInstance();
                DatabaseReference ref = db.getReference().child("posts").child(id);
                ref.setValue(p);
                ref = db.getReference().child("users").child(mAuth.getCurrentUser().getUid()).child("posts").child(id);
                ref.setValue(p);
                Toast.makeText(c,"Upload successful",Toast.LENGTH_LONG).show();
            }
        });
    }
}

    @Override
    protected void onDestroy() {
        Toast.makeText(this,"Destroyed",Toast.LENGTH_LONG).show();
        super.onDestroy();

    }
}
