package com.lamra.smd.lamra;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.media.Image;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.View;
import android.widget.ImageView;

import java.io.IOException;

import static android.widget.ImageView.*;

public class PhotoImport extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        {



            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_photo_import);
            Intent i = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
            startActivityForResult(i, 1);


        }


    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == 1 && resultCode == RESULT_OK && data != null) {
            Uri selectedImage = data.getData();
//            try {
                String[] filePathColumn = { MediaStore.Images.Media.DATA };

                Cursor cursor = getContentResolver().query(selectedImage,
                        filePathColumn, null, null, null);
                cursor.moveToFirst();

                int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
                String picturePath = cursor.getString(columnIndex);
                cursor.close();

                    ImageView UploadPhoto1 = (ImageView) findViewById(R.id.UploadPhoto1);
                    UploadPhoto1.setImageBitmap(BitmapFactory.decodeFile(picturePath));

//            } catch (IOException e) {
//                e.printStackTrace();
//            }

        }
    }

}