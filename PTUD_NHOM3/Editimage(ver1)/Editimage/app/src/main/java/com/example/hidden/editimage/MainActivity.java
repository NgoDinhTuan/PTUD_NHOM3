package com.example.hidden.editimage;

import android.content.Intent;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;

import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.appindexing.Thing;
import com.google.android.gms.common.api.GoogleApiClient;

public class MainActivity extends AppCompatActivity {

    Button btnbright, btnContrast;

    private GoogleApiClient client;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        btnContrast = (Button) findViewById(R.id.btnContrast);
        btnContrast.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent mh3 = new Intent(MainActivity.this, manhinh3.class);
                startActivity(mh3);
            }
        });


        btnbright = (Button) findViewById(R.id.btnbright);
        btnbright.setOnClickListener(new View.OnClickListener() {
            public void onClick(View v) {

                Intent mh2 = new Intent(MainActivity.this, ManHinh2.class);
                startActivity(mh2);
            }

            ;
        });


        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
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