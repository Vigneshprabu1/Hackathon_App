package com.example.ss4.hackathons;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class Home extends AppCompatActivity {

    Bundle extras;

    String email, name, collegeId;

    TextView heading, nextHackathonDate, resultHackathonDate, startHackathon, hackathonResult;

    Boolean isInternetPresent = false;

    ImageView img;

    Bitmap bitmap;
    ProgressDialog pDialog;

    String hackathonId;

    final int DEFAULT_TIMEOUT = 60 * 1000;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home);

        heading=(TextView)findViewById(R.id.textViewHeading);

        img=(ImageView)findViewById(R.id.quizAddImage);

        nextHackathonDate=(TextView)findViewById(R.id.nextHackathonDate);
        resultHackathonDate=(TextView)findViewById(R.id.hackathonResultDate);
        startHackathon=(TextView)findViewById(R.id.loginTextViewContinue);
        hackathonResult=(TextView)findViewById(R.id.viewResult);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //Getting value from session
        name=sharedpreferences.getString("name", "string value");
        email=sharedpreferences.getString("email", "string value");
        collegeId=sharedpreferences.getString("collegeId", "string value");
        heading.setText("Hi "+name+"");

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {
            pDialog = new ProgressDialog(Home.this);
            pDialog.setMessage("Page Loading ....");
            pDialog.show();

            getHackathonId();

            RequestParams params3 = new RequestParams();
            params3.put("email", email);
            getHackathonImage(params3);

            RequestParams params = new RequestParams();
            params.put("collegeId", collegeId);
            getNextHackathonDate(params);

            RequestParams params1 = new RequestParams();
            params1.put("collegeId", collegeId);
            params1.put("email", email);
            hackathon(params1);

            RequestParams params2 = new RequestParams();
            params2.put("email", email);
            hackathonResult(params2);

            pDialog.dismiss();

        }
        else{
            showAlertDialog(Home.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }



    }

    public void getNextHackathonDate(RequestParams params) {

        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://101.53.139.52:8080/Incubation20.0/getHackathonDate.html",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");
                    if(result!=null && !result.isEmpty()){
                        nextHackathonDate.setText(result);
                    }
                    } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code '200'

        });

    }

    public void getHackathonId() {

        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://incubator.shinelogics.com/getActiveHackathonId.html", new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");
                    if(result!=null && !result.isEmpty()){
                        hackathonId=result;
                    }
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code '200'

        });

    }


    public void hackathon(RequestParams params) {

        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://incubator.shinelogics.com/getActiveHackathon.html",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");
                    if(result!=null && result.equalsIgnoreCase("Start")){
                        startHackathon.setVisibility(View.VISIBLE);
                        resultHackathonDate.setVisibility(View.GONE);
                    }
                    else if(result!=null && !result.equalsIgnoreCase("Start") && !result.isEmpty()){
                        startHackathon.setVisibility(View.GONE);
                        resultHackathonDate.setVisibility(View.VISIBLE);
                        resultHackathonDate.setText("Your last hackathon result will announce on : "+result);
                    }
                    else{
                        startHackathon.setVisibility(View.GONE);
                        resultHackathonDate.setVisibility(View.GONE);
                    }
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code '200'

        });

    }

    public void getHackathonImage(RequestParams params) {

        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://incubator.shinelogics.com/getImage.html", params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");
                    if(result!=null && result.length()>0){
                        ImageFromURL url=new ImageFromURL(result, img);

                    }
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code '200'

        });

    }

    public void hackathonResult(RequestParams params) {

        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://incubator.shinelogics.com/getHackathonResult.html",params, new AsyncHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");
                    if(result!=null && result.equalsIgnoreCase("Show")){
                        hackathonResult.setVisibility(View.VISIBLE);

                    }
                    else if(result!=null && !result.equalsIgnoreCase("Show")&& !result.isEmpty()){
                        hackathonResult.setVisibility(View.GONE);
                        resultHackathonDate.setVisibility(View.VISIBLE);
                        resultHackathonDate.setText("Your last hackathon result will announce on : "+result);
                    }
                    else{
                        hackathonResult.setVisibility(View.GONE);
                    }
                } catch (UnsupportedEncodingException e1) {
                    e1.printStackTrace();
                }

            }

            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {

                // When Http response code is '404'
                if (statusCode == 404) {
                    Toast.makeText(getApplicationContext(), "Requested resource not found", Toast.LENGTH_LONG).show();
                }
                // When Http response code is '500'
                else if (statusCode == 500) {
                    Toast.makeText(getApplicationContext(), "Something went wrong at server end", Toast.LENGTH_LONG).show();
                }
                // When Http response code other than 404, 500
                else {
                    Toast.makeText(getApplicationContext(), "Unexpected Error occcured! [Most common Error: Device might not be connected to Internet or remote server is not up and running]", Toast.LENGTH_LONG).show();
                }
            }
            // When the response returned by REST has Http response code '200'

        });

    }


    public void startQuiz(View view){

        Intent loginIntent = new Intent(getApplicationContext(), QuizTest.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        loginIntent.putExtra("hackathonId", hackathonId);
        startActivity(loginIntent);
    }

    public void logout(View view){

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        editor.remove("name");
        editor.remove("email");
        editor.commit();

        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
        finish();
    }

    public void viewResult(View view){

        Intent loginIntent = new Intent(getApplicationContext(), Result.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
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


}
