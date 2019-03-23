package com.example.het.mex;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.content.FileProvider;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.Toolbar;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class Take_Photo extends AppCompatActivity {
    static final int REQUEST_IMAGE_CAPTURE = 1;
    static final int REQUEST_TAKE_PHOTO = 1;
    String currentPhotoPath;
    ImageView mImageView;
    String Base64String;
    String desc,bType;
    String Uniname;

//    private static final String POST_URL = "http://ptsv2.com/t/dz7dh-1551415957/post";
private static final String POST_URL = "https://mex1242523-busy-ratel.eu-gb.mybluemix.net/claim";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take__photo);
        SharedPreferences sharedpref = getSharedPreferences("UniversalDetails", Context.MODE_PRIVATE);
        Uniname = sharedpref.getString("Name","");

        mainFun();
    }

    public void mainFun(){
        //        Toolbar
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        toolbar.setTitle("Take Photo");

        Bundle data = getIntent().getExtras();
        if(data == null){
            return;
        }
//        Uniname =data.getString("UniversalName");
        final TextView textView4 =(TextView) findViewById(R.id.textView4);
        textView4.setText(Uniname);

        desc = data.getString("desc_id");
        final TextView textView7 = (TextView) findViewById(R.id.textView7);
        textView7.setText(desc);

        bType = data.getString("bType");
        final TextView textView8 = (TextView) findViewById(R.id.textView8);
        textView8.setText(bType);

        mImageView = (ImageView) findViewById(R.id.imageView);
    }

    public void dispatchTakePictureIntent(View view) {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
//            startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
            // Create the File where the photo should go
            File photoFile = null;
            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
                // Error occurred while creating the File
                Toast.makeText(Take_Photo.this, "Error creating file, please change or restart your phone", Toast.LENGTH_SHORT).show();
            }
            // Continue only if the File was successfully created
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(this,
                        "com.example.android.fileprovider",
                        photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            //            Convert to base64
            Uri targetUri = Uri.fromFile(new File(currentPhotoPath));
            Bitmap bitmap;
            try {
                bitmap = BitmapFactory.decodeStream(getContentResolver().openInputStream(targetUri));
                Bitmap resizedBitmap = Bitmap.createScaledBitmap(bitmap, 500, 500, false);
                Base64String = ConvertImageToBase64(resizedBitmap);
                System.gc();
                Toast.makeText(Take_Photo.this,Base64String,Toast.LENGTH_SHORT).show();
                mImageView.setImageBitmap(resizedBitmap);
            }
            catch (FileNotFoundException e) {
                // TODO Auto-generated catch block
                e.printStackTrace();
            }

//            Display image
//            Bitmap myImg = BitmapFactory.decodeFile(currentPhotoPath);
//            mImageView.setImageBitmap(myImg);

            System.gc();

//            Display thumbnail
//            final int THUMBSIZE = 1024;
//            Bitmap ThumbImage = ThumbnailUtils.extractThumbnail(BitmapFactory.decodeFile(currentPhotoPath),
//                    THUMBSIZE, THUMBSIZE);
//            mImageView.setImageBitmap(ThumbImage);
        }
    }

    private File createImageFile() throws IOException {
        // Create an image file name
        String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
        String imageFileName = "JPEG_" + timeStamp + "_";
        File storageDir = getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,  /* prefix */
                ".jpg",         /* suffix */
                storageDir      /* directory */
        );


        // Save a file: path for use with ACTION_VIEW intents
        currentPhotoPath = image.getAbsolutePath();
        return image;

    }


    public static String ConvertImageToBase64(Bitmap bitmap){
        String encodedImage = "";

        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, byteArrayOutputStream);
//        encodedImage= Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT);
        try {
//            DO NOT FORGET TO URL DECODE THE STRING AND THEN PASS TO BASE64 DECODER
            encodedImage= URLEncoder.encode(Base64.encodeToString(byteArrayOutputStream.toByteArray(), Base64.DEFAULT), "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }

//        another method
//        String codedImage = "";
//
//        byte[] byteArray = byteArrayOutputStream .toByteArray();
//
//        encodedImage=Base64.encodeToString(byteArray, Base64.DEFAULT);
//
//        if (encodedImage == codedImage){
//            Toast.makeText(context,"Both are same",Toast.LENGTH_SHORT).show();
//        }
        return encodedImage;
    }

    public void SbmtClaimBtn(View view){
        if(mImageView.getDrawable() == null){
            Toast.makeText(Take_Photo.this,"You forgot to take a bill photo :(",Toast.LENGTH_SHORT).show();
        }
        else {
            StringRequest postRequest = new StringRequest(Request.Method.POST, POST_URL, new Response.Listener<String>() {
                @Override
                public void onResponse(String response) {
                    //response
                    Toast.makeText(Take_Photo.this,"Claim Submitted",Toast.LENGTH_SHORT).show();
                    Toast.makeText(Take_Photo.this,response.toString(),Toast.LENGTH_SHORT).show();
                    Intent i = new Intent(Take_Photo.this,HomePage_Employee.class);
//                    i.putExtra("UniversalName",Uniname);
                    startActivity(i);
                }
            }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    //error
                    Toast.makeText(Take_Photo.this,"Error",Toast.LENGTH_SHORT).show();
                }
            }){
                @Override
                protected Map<String,String> getParams(){
                    Map<String,String> params = new HashMap<String,String>();
                    params.put("name",Uniname);
                    params.put("description",desc);
                    params.put("busniesstype",bType);
                    params.put("image",Base64String);

                    return params;
                }
            };
            MySingleton.getInstance(this).addToRequestQueue(postRequest);
        }
    }
}
