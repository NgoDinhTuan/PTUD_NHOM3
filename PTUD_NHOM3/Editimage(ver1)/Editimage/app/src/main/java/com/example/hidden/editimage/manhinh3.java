package com.example.hidden.editimage;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.ColorMatrix;
import android.graphics.ColorMatrixColorFilter;
import android.graphics.Paint;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.SeekBar;

public class manhinh3 extends AppCompatActivity {
    Button btnBack2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_manhinh3);

        btnBack2 = (Button) findViewById(R.id.btnBack2);
        btnBack2.setOnClickListener(new View.OnClickListener() {

            public void onClick(View v) {
                Intent mh = new Intent(manhinh3.this, MainActivity.class);
                startActivity(mh);
            }
        });

        SeekBar seekBar = (SeekBar)findViewById(R.id.seekBar);
        seekBar.setProgress(-10);
        seekBar.setMax(10);
        seekBar.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener(){

            @Override
            public void onProgressChanged(SeekBar seekBar1, int progress, boolean fromUser) {
                Bitmap bitmapData = BitmapFactory.decodeResource(getResources(), R.mipmap.img_vietnamese);
                Bitmap newBitMapData = changeBitmapContrast(bitmapData, progress, progress);
                ImageView view = (ImageView)findViewById(R.id.imageView4);
                view.setImageBitmap(newBitMapData);
            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar1) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar1) {

            }
        });
    }

    public static Bitmap changeBitmapContrast(Bitmap bmp, float contrast, float brightness)
    {
        ColorMatrix cm = new ColorMatrix(new float[]
                {
                        contrast, 0, 0, 0,0,
                        0, contrast, 0, 0,0,
                        0, 0, contrast, 0,0,
                        0, 0, 0,contrast, 0,
                        0, 0, 0,1, 0
                });

        Bitmap ret = Bitmap.createBitmap(bmp.getWidth(), bmp.getHeight(), bmp.getConfig());

        Canvas canvas = new Canvas(ret);

        Paint paint = new Paint();
        paint.setColorFilter(new ColorMatrixColorFilter(cm));
        canvas.drawBitmap(bmp, 0, 0, paint);

        return ret;
    }

}
