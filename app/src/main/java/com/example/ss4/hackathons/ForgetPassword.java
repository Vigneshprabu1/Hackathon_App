package com.example.ss4.hackathons;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
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

public class ForgetPassword extends AppCompatActivity {

    EditText editTextEmail;

    TextView error;

    Boolean isInternetPresent = false;

    final int DEFAULT_TIMEOUT = 60 * 1000;

    ProgressDialog myPd_ring;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_forget_password);

        editTextEmail=(EditText)findViewById(R.id.editTextEmail);

        error=(TextView)findViewById(R.id.error);
    }

    public void back(View view) {

        Intent loginIntent = new Intent(getApplicationContext(), MainActivity.class);
        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);

        startActivity(loginIntent);
    }

    public void forgetPasswordSubmit(View view) {

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {

            String email=editTextEmail.getText().toString();

            if(email!=null & email.length()>0){
                if(Utility.validate(email)){
                    RequestParams params = new RequestParams();
                    params.put("email",email);

                    invokeWS(params);

                    myPd_ring= ProgressDialog.show(ForgetPassword.this, "Please wait", "Loading please wait..", true);
                    myPd_ring.setCancelable(true);
                    new Thread(new Runnable() {
                        @Override
                        public void run() {
                            // TODO Auto-generated method stub
                            try
                            {
                                Thread.sleep(15000);
                            }catch(Exception e){}
                            myPd_ring.dismiss();
                        }
                    }).start();
                }
                else{
                    error.setText("Incorrect email pattern");
                }

            }
            else{
                error.setText("Email id required");
            }

        }
        else{
            showAlertDialog(ForgetPassword.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }

    public void invokeWS(RequestParams params) {
        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://101.53.139.52:8080/Incubation20.0/forgetPassword.html",params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");

                    if (result != null) {

                        if (result.equalsIgnoreCase("IncorrectEmail")) {
                            error.setText("Incorrect email");
                        }
                        else if(result.equalsIgnoreCase("IncorrectUserName")) {
                            error.setText("Incorrect username or code");
                        }
                                               // Else display error message
                        else if(result.equalsIgnoreCase("Success")) {

                            editTextEmail.setText("");
                            error.setText("");
                            myPd_ring.dismiss();
                            showAlertDialogForSuccess(ForgetPassword.this, "Thank You",
                                    "Password has beed sent to your email.", false);
                        }


                    }


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                }

            }

            // When the response returned by REST has Http response code other than '200'
            @Override
            public void onFailure(int statusCode, Header[] headers, byte[] responseBody, Throwable error) {
                System.out.println("Faild");
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

    public void showAlertDialogForSuccess(Context context, String title, String message, Boolean status) {

        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(title)
                .setIcon(R.drawable.success)
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
