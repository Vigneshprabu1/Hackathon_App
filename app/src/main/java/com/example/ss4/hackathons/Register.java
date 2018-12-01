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
import android.widget.RadioButton;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import java.io.UnsupportedEncodingException;

import cz.msebera.android.httpclient.Header;

public class Register extends AppCompatActivity {


    String gender;

    EditText editTextName, editTextMobileNo, editTextEmail, editTextPassword, editTextConfirmPassword;

    RadioButton radioMale, radioFemale;

    Boolean isInternetPresent = false;

    Bundle extras;

    ProgressDialog pDialog;

    final int DEFAULT_TIMEOUT = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        editTextName=(EditText)findViewById(R.id.editTextFirstName);
        editTextMobileNo=(EditText)findViewById(R.id.editTextPhoneNum);
        editTextEmail=(EditText)findViewById(R.id.editTextEmail);
        editTextPassword=(EditText)findViewById(R.id.editTextPassword);
        editTextConfirmPassword=(EditText)findViewById(R.id.editTextConfirmPasswordPassword);

        radioMale=(RadioButton)findViewById(R.id.male);
        radioFemale=(RadioButton)findViewById(R.id.female);

        extras = getIntent().getExtras();
        if(extras!=null && extras.getString("college")!=null) {
            editTextMobileNo.setText(extras.getString("mobileNumber"));
            editTextName.setText(extras.getString("name"));
            editTextEmail.setText(extras.getString("email"));


        }

    }


    public void submit(View view){
        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {

            String name=editTextName.getText().toString();
            String email=editTextEmail.getText().toString().trim();
            String mobileNo=editTextMobileNo.getText().toString();
            String password=editTextPassword.getText().toString();
            String confirmPassword=editTextConfirmPassword.getText().toString();

            if(name!=null && name.length()>0 && email!=null && email.length()>0 && mobileNo!=null && mobileNo.length()>0
                    && gender!=null && gender.length()>0 && password!=null && password.length()>0
                    && confirmPassword!=null && confirmPassword.length()>0){
                if(Utility.validate(email)){
                    if(password.equals(confirmPassword)){
                        RequestParams params = new RequestParams();
                        params.put("name", name);
                        params.put("email", email);
                        params.put("mobileNumber", mobileNo);
                        params.put("gender", gender);
                        params.put("password", password);

                        pDialog = new ProgressDialog(Register.this);
                        pDialog.setMessage("Please wait ....");
                        pDialog.show();

                        invokeWS(params);
                    }
                    else{
                        Toast.makeText(getApplicationContext(), "Password and confirm password should be same", Toast.LENGTH_LONG).show();
                    }

                }
                else{
                    Toast.makeText(getApplicationContext(), "Incorrect email id", Toast.LENGTH_LONG).show();
                }

            }
            else{
                Toast.makeText(getApplicationContext(), "All fields required", Toast.LENGTH_LONG).show();
            }

        }
        else{
            showAlertDialog(Register.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }

    public void invokeWS(RequestParams params) {
        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://101.53.139.52:8080/Incubation20.0/sregister.html",params, new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("Sivaraj Success ");
                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");

                    if(result!=null && result.equalsIgnoreCase("EmailExist")){
                        pDialog.dismiss();
                        showAlertDialog(Register.this, "This email already registered with us",
                                "Kindly login..!!", false);
                    }
                    else if(result!=null && !result.equalsIgnoreCase("EmailExist")){
                        Intent loginIntent = new Intent(getApplicationContext(), AddProfileImage.class);
                        loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                        loginIntent.putExtra("id", result);
                        pDialog.dismiss();
                        startActivity(loginIntent);
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

    public void onRadioButtonClicked(View view) {
        // Is the button now checked?
        boolean checked = ((RadioButton) view).isChecked();

        // Check which radio button was clicked
        switch (view.getId()) {
            case R.id.male:

                if (checked)
                    System.out.println("Male Yes");
                gender = "Male";
                break;
            case R.id.female:
                if (checked)
                    gender = "Female";
                break;

        }
    }
}
