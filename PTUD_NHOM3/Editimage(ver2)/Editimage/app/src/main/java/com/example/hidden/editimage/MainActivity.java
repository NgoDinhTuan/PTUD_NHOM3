package com.example.hidden.editimage;

import android.content.Intent;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

import java.io.IOException;

public class MainActivity extends AppCompatActivity {

    Button btnbright, btnContrast, btnOpen;

    private static int RESULT_LOAD_IMAGE = 1;
    private GoogleApiClient client;
    private static Uri picPath = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);


        if (picPath != null)
        {

            Bundle bl = getIntent().getExtras();
            DataPassing data = (DataPassing)bl.getParcelable("PICTURE_PATH");
            picPath = data.getPathFile();
            Uri picturePath = data.getPathFile();

            try {
                Bitmap bitmapData = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picturePath);
                ImageView view = (ImageView)findViewById(R.id.imageView2);
                view.setImageBitmap(bitmapData);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }



        btnOpen=(Button) findViewById(R.id.btnOpen);
        btnOpen.setOnClickListener(new View.OnClickListener(){
            public void onClick(View arg0) {

                Intent i = new Intent(
                        Intent.ACTION_PICK,
                        android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);

                startActivityForResult(i, RESULT_LOAD_IMAGE);
            }
        });

        btnContrast = (Button) findViewById(R.id.btnContrast);
        btnContrast.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent mh3 = new Intent(MainActivity.this, manhinh3.class);
                if (picPath != null)
                {
                    DataPassing dataPassing = new DataPassing();
                    dataPassing.setPathFile(picPath);
                    mh3.putExtra("PICTURE_PATH", dataPassing);
                }
                startActivity(mh3);
            }
        });


        btnbright = (Button) findViewById(R.id.btnbright);
        btnbright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent mh2 = new Intent(MainActivity.this, ManHinh2.class);
                if (picPath != null)
                {
                    DataPassing dataPassing = new DataPassing();
                    dataPassing.setPathFile(picPath);
                    mh2.putExtra("PICTURE_PATH", dataPassing);
                }
                startActivity(mh2);
            }

            ;
        });


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == RESULT_LOAD_IMAGE && resultCode == RESULT_OK && null != data) {
            Uri selectedImage = data.getData();
            String[] filePathColumn = { MediaStore.Images.Media.DATA };

            Cursor cursor = getContentResolver().query(selectedImage,
                    filePathColumn, null, null, null);
            cursor.moveToFirst();

            int columnIndex = cursor.getColumnIndex(filePathColumn[0]);
            String picturePath = cursor.getString(columnIndex);
            cursor.close();

            //String newPicturePath = picturePath.replace("file://","");
            try {
                Bitmap bm = MediaStore.Images.Media.getBitmap(this.getContentResolver(), selectedImage);
                ImageView imageView = (ImageView) findViewById(R.id.imageView2);
                //imageView.setImageBitmap(BitmapFactory.decodeFile(picturePath));
                imageView.setImageBitmap(bm);
                picPath = selectedImage;
            } catch (IOException e) {
                e.printStackTrace();
            }



        }


    }

    public Action getIndexApiAction() {
        Thing object = new Thing.Builder()
                .setName("Main Page") // TODO: Define a title for the content shown.
                // TODO: Make sure this auto-generated URL is correct.
                .setUrl(Uri.parse("http://[ENTER-YOUR-URL-HERE]"))
                .build();
        return new Action.Builder(Action.TYPE_VIEW)
                .setObject(object)
                .setActionStatus(Action.STATUS_TYPE_COMPLETED)
                .build();
    }

    @Override
    public void onStart() {
        super.onStart();

        client.connect();
        AppIndex.AppIndexApi.start(client, getIndexApiAction());
    }

    @Override
    public void onStop() {
        super.onStop();


        AppIndex.AppIndexApi.end(client, getIndexApiAction());
        client.disconnect();
    }




}