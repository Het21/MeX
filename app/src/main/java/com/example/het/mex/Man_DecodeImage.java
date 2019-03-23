package com.example.het.mex;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.widget.ImageView;
import android.widget.TextView;

public class Man_DecodeImage extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_man__decode_image);

        mainFun();
    }

    public void mainFun(){
        //        Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Bill Image");
        setSupportActionBar(toolbar);

        Bundle data = getIntent().getExtras();
        if(data == null){
            return;
        }

        String name =data.getString("name");
        final TextView textView3 =(TextView) findViewById(R.id.textView3);
        textView3.setText(name);

        String busniessType =data.getString("busniessType");
        final TextView textView9 =(TextView) findViewById(R.id.textView9);
        textView9.setText(busniessType);

        String description =data.getString("description");
        final TextView textView10 =(TextView) findViewById(R.id.textView10);
        textView10.setText(description);



        byte[] byteArray = getIntent().getByteArrayExtra("image");
        Bitmap bmp = BitmapFactory.decodeByteArray(byteArray, 0, byteArray.length);
        ImageView imgvw = (ImageView)findViewById(R.id.imageView);
        imgvw.setImageBitmap(bmp);
    }
}

