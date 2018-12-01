package com.example.ss4.hackathons;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.AsyncTask;
import android.os.Bundle;
import android.provider.MediaStore;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.util.ArrayList;
import java.util.List;

public class AddProfileImage extends AppCompatActivity {

    private static final int CAMERA_CODE = 101, GALLERY_CODE = 201, CROPING_CODE = 301;

    private Button btn_select_image;
    private ImageView imageView;
    private Uri mImageCaptureUri;
    private File outPutFile = null;

    Bundle extras;

    Bitmap photo;

    ProgressDialog pDialog;

    String id;

    Boolean isInternetPresent = false;

    private MenuItem item;

    final int DEFAULT_TIMEOUT = 30 * 1000;

    private String url = "http://101.53.139.52:8080/Incubation20.0/UploadProfileImage.html";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_profile_image);

        outPutFile = new File(android.os.Environment.getExternalStorageDirectory(), "temp.jpg");
        imageView=(ImageView)findViewById(R.id.imageViewLogo);

        extras = getIntent().getExtras();
        id=extras.getString("id");

    }

    public void uploadButton(View view){

       selectImageOption();
    }

    private void selectImageOption() {
        final CharSequence[] items = { "Capture Photo", "Choose from Gallery", "Cancel" };

        AlertDialog.Builder builder = new AlertDialog.Builder(AddProfileImage.this);
        builder.setTitle("Add Photo!");
        builder.setItems(items, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int item) {

                if (items[item].equals("Capture Photo")) {

                    Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                    File f = new File(android.os.Environment.getExternalStorageDirectory(), "temp1.jpg");
                    mImageCaptureUri = Uri.fromFile(f);
                    intent.putExtra(MediaStore.EXTRA_OUTPUT, mImageCaptureUri);
                    startActivityForResult(intent, CAMERA_CODE);

                } else if (items[item].equals("Choose from Gallery")) {

                    Intent i = new Intent(Intent.ACTION_PICK, android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
                    startActivityForResult(i, GALLERY_CODE);

                } else if (items[item].equals("Cancel")) {
                    dialog.dismiss();
                }
            }
        });
        builder.show();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        super.onActivityResult(requestCode, resultCode, data);

        if (requestCode == GALLERY_CODE && resultCode == RESULT_OK && null != data) {

            mImageCaptureUri = data.getData();
            System.out.println("Gallery Image URI : "+mImageCaptureUri);
            CropingIMG();

        } else if (requestCode == CAMERA_CODE && resultCode == Activity.RESULT_OK) {

            System.out.println("Camera Image URI : "+mImageCaptureUri);
            CropingIMG();
        } else if (requestCode == CROPING_CODE) {

            try {
                if(outPutFile.exists()){
                    photo = decodeFile(outPutFile);
                    if(photo!=null) {
                        Bitmap proImage = getRoundedShape1(photo);
                        imageView.setImageBitmap(proImage);
                    }
                }
                else {
                    Toast.makeText(getApplicationContext(), "Error while save image", Toast.LENGTH_SHORT).show();
                }
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }

    private void CropingIMG() {

        final ArrayList<CropingOption> cropOptions = new ArrayList();

        Intent intent = new Intent("com.android.camera.action.CROP");
        intent.setType("image/*");

        List<ResolveInfo> list = getPackageManager().queryIntentActivities( intent, 0 );
        int size = list.size();
        if (size == 0) {
            Toast.makeText(this, "Cann't find image croping app", Toast.LENGTH_SHORT).show();
            return;
        } else {
            intent.setData(mImageCaptureUri);
            intent.putExtra("outputX", 512);
            intent.putExtra("outputY", 512);
            intent.putExtra("aspectX", 1);
            intent.putExtra("aspectY", 1);
            intent.putExtra("scale", true);

            //TODO: don't use return-data tag because it's not return large image data and crash not given any message
            //intent.putExtra("return-data", true);

            //Create output file here
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(outPutFile));

            if (size == 1) {
                Intent i   = new Intent(intent);
                ResolveInfo res = (ResolveInfo) list.get(0);

                i.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));

                startActivityForResult(i, CROPING_CODE);
                return;
            } else {
                for (ResolveInfo res : list) {

                    final CropingOption co = new CropingOption();

                    co.title  = getPackageManager().getApplicationLabel(res.activityInfo.applicationInfo);
                    co.icon  = getPackageManager().getApplicationIcon(res.activityInfo.applicationInfo);
                    co.appIntent= new Intent(intent);
                    co.appIntent.setComponent( new ComponentName(res.activityInfo.packageName, res.activityInfo.name));
                    cropOptions.add(co);
                }

                CropingOptionAdapter adapter = new CropingOptionAdapter(getApplicationContext(), cropOptions);

                AlertDialog.Builder builder = new AlertDialog.Builder(this);
                builder.setTitle("Choose Croping App");
                builder.setCancelable(false);
                builder.setAdapter( adapter, new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int item ) {
                        startActivityForResult(cropOptions.get(item).appIntent, CROPING_CODE);
                    }
                });

                builder.setOnCancelListener( new DialogInterface.OnCancelListener() {
                    @Override
                    public void onCancel( DialogInterface dialog ) {

                        if (mImageCaptureUri != null ) {
                            getContentResolver().delete(mImageCaptureUri, null, null );
                            mImageCaptureUri = null;
                        }
                    }
                } );

                AlertDialog alert = builder.create();
                alert.show();
            }
        }
    }

    private Bitmap decodeFile(File f) {
        try {
            // decode image size
            BitmapFactory.Options o = new BitmapFactory.Options();
            o.inJustDecodeBounds = true;
            BitmapFactory.decodeStream(new FileInputStream(f), null, o);

            // Find the correct scale value. It should be the power of 2.
            final int REQUIRED_SIZE = 212;
            int width_tmp = o.outWidth, height_tmp = o.outHeight;
            int scale = 1;
            while (true) {
                if (width_tmp / 2 < REQUIRED_SIZE || height_tmp / 2 < REQUIRED_SIZE)
                    break;
                width_tmp /= 2;
                height_tmp /= 2;
                scale *= 2;
            }

            // decode with inSampleSize
            BitmapFactory.Options o2 = new BitmapFactory.Options();
            o2.inSampleSize = scale;
            return BitmapFactory.decodeStream(new FileInputStream(f), null, o2);
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return null;
    }

    public Bitmap getRoundedShape1(Bitmap scaleBitmapImage) {
        int targetWidth = 125;
        int targetHeight = 125;
        Bitmap targetBitmap = Bitmap.createBitmap(targetWidth,
                targetHeight, Bitmap.Config.ARGB_8888);

        Canvas canvas = new Canvas(targetBitmap);
        Path path = new Path();
        path.addCircle(((float) targetWidth - 1) / 2,
                ((float) targetHeight - 1) / 2,
                (Math.min(((float) targetWidth),
                        ((float) targetHeight)) / 2),
                Path.Direction.CCW);

        canvas.clipPath(path);
        Bitmap sourceBitmap = scaleBitmapImage;
        canvas.drawBitmap(sourceBitmap,
                new Rect(0, 0, sourceBitmap.getWidth(),
                        sourceBitmap.getHeight()),
                new Rect(0, 0, targetWidth,
                        targetHeight), null);
        return targetBitmap;
    }




    public void showAlertDialog(Context context, String title, String message, Boolean status) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setIcon(R.drawable.internet_faild)
                .setMessage(message)
                .setCancelable(false)
                .setNegativeButton("Close",new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {
                        dialog.cancel();
                    }
                });
        AlertDialog alert = builder.create();
        alert.show();
    }

    private class SendHttpRequestTask extends AsyncTask<String, Void, String> {

        @Override
        protected String doInBackground(String... params) {
            String url = params[0];
            String param1 = params[1];
            //String param2 = params[2];
            Bitmap b =photo; //BitmapFactory.decodeResource(AddProfileImage.this.getResources(), R.drawable.pro);

            ByteArrayOutputStream baos = new ByteArrayOutputStream();
            b.compress(Bitmap.CompressFormat.PNG, 0, baos);

            try {
                HttpClient1 client = new HttpClient1(url);
                client.connectForMultipart();
                client.addFormPart("id", param1);
                //client.addFormPart("param2", param2);
                client.addFilePart("file", ""+id+".png", baos.toByteArray());
                client.finishMultipart();
                String data = client.getResponse();
            }
            catch(Throwable t) {
                t.printStackTrace();
            }

            return null;
        }

        @Override
        protected void onPostExecute(String data) {
            pDialog.dismiss();
        }



    }

    public void profileImageUploadContinue(View view){

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {
            if(photo!=null) {
                System.out.println("Photo Uploaded ");
                SendHttpRequestTask t = new SendHttpRequestTask();

                String param1 = id;
                String param2 = "";

                String[] params = new String[]{url, param1, param2};

                pDialog = new ProgressDialog(AddProfileImage.this);
                pDialog.setMessage("Please wait ....");
                pDialog.show();

                t.execute(params);
            }

            Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
            loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
            startActivity(loginIntent);

        }
        else{
            showAlertDialog(AddProfileImage.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }

    }

    public void skipUploadImage(View view) {

        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(loginIntent);
    }


}
