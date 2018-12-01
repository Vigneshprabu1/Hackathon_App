package com.example.ss4.hackathons;

import android.app.ProgressDialog;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.widget.ImageView;

import java.io.InputStream;
import java.net.URL;

/**
 * Created by shinelogics on 8/25/2016.
 */
public class ImageFromURL{

    ImageView img;
    Bitmap bitmap;
    ProgressDialog pDialog;


   String image_url = "http://101.53.139.52:8080/Incubation20.0/HackathonImages/";

    public ImageFromURL(String result, ImageView img){
        this.img=img;
        new LoadImage().execute(image_url+result);

    }


    private class LoadImage extends AsyncTask<String, String, Bitmap> {
        @Override
        protected void onPreExecute() {
            super.onPreExecute();
//            pDialog = new ProgressDialog(Home.this);
//            pDialog.setMessage("Loading Image ....");
//            pDialog.show();

        }
        protected Bitmap doInBackground(String... args) {
            try {
                bitmap = BitmapFactory.decodeStream((InputStream)new URL(args[0]).getContent());

            } catch (Exception e) {
                e.printStackTrace();
            }
            return bitmap;
        }

        protected void onPostExecute(Bitmap image) {

            if(image != null){
                img.setImageBitmap(image);

                //pDialog.dismiss();

            }else{

                //pDialog.dismiss();
                //Toast.makeText(Home.this, "Image Does Not exist or Network Error", Toast.LENGTH_SHORT).show();

            }
        }
    }
}
