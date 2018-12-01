package com.example.ss4.hackathons;


import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class MainActivity extends AppCompatActivity {

    EditText editTextUserName, editTextPassword;
    String username, password;

    TextView textError;

    Boolean isInternetPresent = false;

    SharedPreferences sharedpreferences;
    public final String MyPREFERENCES = "MyPrefs";

    ProgressDialog pDialog;

    final int DEFAULT_TIMEOUT = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        editTextUserName=(EditText)findViewById(R.id.editTextUserName);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        textError=(TextView)findViewById(R.id.error);

        sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
    }

    public void register(View view){

        Intent loginIntent = new Intent(getApplicationContext(), Register.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(loginIntent);
    }

    public void loginContinue(View view){

        username=editTextUserName.getText().toString();
        password=editTextPassword.getText().toString();

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {
            if(username!=null && username.length()>0 && password!=null && password.length()>0){
                RequestParams params = new RequestParams();
                params.put("username", username.trim());
                params.put("password", password.trim());

                pDialog = new ProgressDialog(MainActivity.this);
                pDialog.setMessage("Please wait ....");
                pDialog.show();

                invokeWS(params);

            }
            else{
                Toast.makeText(getApplicationContext(), "All fields required!", Toast.LENGTH_LONG).show();
            }
        }
        else{
            showAlertDialog(MainActivity.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }

    }

    public void invokeWS(RequestParams params) {
        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://101.53.139.52:8080/Incubation20.0/studentLogin.html", params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                try {
                    // JSON Object
                    String  result= new String(responseBody, "UTF-8");
                    //JSONObject obj = new JSONObject(str);
                    // When the JSON response has status boolean value assigned with true
                    System.out.println("Value :"+result);
                    if(result!=null && result.equalsIgnoreCase("PasswordIncorrect")){
                        textError.setText("Incorrect Password");
                        pDialog.dismiss();
                    }
                    else if(result!=null && result.equalsIgnoreCase("UserNameIncorrect")){
                        textError.setText("Incorrect Username");
                        pDialog.dismiss();
                    }
                    else{
                        SharedPreferences.Editor editor = sharedpreferences.edit();
                        editor.putString("name",result);
                        editor.putString("email", editTextUserName.getText().toString());
                        editor.commit();
                        pDialog.dismiss();

                        navigatetoHomeActivity();

                    }

                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }
            }

            // When the response returned by REST has Http response code other than '200'
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

    public void navigatetoHomeActivity(){
        Intent homeIntent = new Intent(getApplicationContext(),Home.class);

        //Set customer id to session

        homeIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
        startActivity(homeIntent);
    }


    public void forgetPassword(View view) {

        Intent loginIntent = new Intent(getApplicationContext(), ForgetPassword.class);
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
