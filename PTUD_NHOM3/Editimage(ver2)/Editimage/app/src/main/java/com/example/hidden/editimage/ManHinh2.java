package com.example.hidden.editimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

import java.io.IOException;

public class ManHinh2 extends AppCompatActivity {
    Button btnBack1;
    private static Uri picPathMH2 = null;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man_hinh2);

        btnBack1 = (Button) findViewById(R.id.btnBack1);
        btnBack1.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent mh = new Intent(ManHinh2.this, MainActivity.class);
                if (picPathMH2 != null)
                {
                    DataPassing dataPassing = new DataPassing();
                    dataPassing.setPathFile(picPathMH2);
                    mh.putExtra("PICTURE_PATH", dataPassing);
                }
                startActivity(mh);
            }
        });


        Bundle bl = getIntent().getExtras();
        DataPassing data = (DataPassing)bl.getParcelable("PICTURE_PATH");
        picPathMH2 = data.getPathFile();
        Uri picturePath = data.getPathFile();

        try {
            Bitmap bitmapData = MediaStore.Images.Media.getBitmap(this.getContentResolver(), picturePath);
            ImageView view = (ImageView)findViewById(R.id.imageView);
            view.setImageBitmap(bitmapData);
        } catch (IOException e) {
            e.printStackTrace();
        }

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(-30);
        seekBar.setMax(30);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {
                ImageView view = (ImageView)findViewById(R.id.imageView);
              //  Bitmap bitmapData = BitmapFactory.decodeResource(getResources(), R.mipmap.img_vietnamese);
                Bitmap bitmapData = ((BitmapDrawable)view.getDrawable()).getBitmap();
                Bitmap newBitMapData = changeBitmapContrastBrightness(bitmapData, progress);
                view.setImageBitmap(newBitMapData);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {

            }
        });
    }

    public static Bitmap changeBitmapContrastBrightness(Bitmap bmp, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        1, 0, 0, 0, brightness,
                        0, 1, 0, 0, brightness,
                        0, 0, 1, 0, brightness,
                        0, 0, 0,1, 0,
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }
}
