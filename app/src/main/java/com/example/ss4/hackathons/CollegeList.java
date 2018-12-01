package com.example.ss4.hackathons;

import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONException;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class CollegeList extends AppCompatActivity {

    // List view
    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    String college[];

    List<String> list;
    String stockArr[];

    Bundle extras;

    Boolean isInternetPresent = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_college_list);

        list= new ArrayList<String>();

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {
            invokeWS();
        }
        else{
            showAlertDialog(CollegeList.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }

    }

    public void listLocation(final String[] college){

        adapter = new ArrayAdapter<String>(this, R.layout.list_college, R.id.college_name, college);
        lv.setAdapter(adapter);

        inputSearch.addTextChangedListener(new TextWatcher() {

            @Override
            public void onTextChanged(CharSequence cs, int arg1, int arg2, int arg3) {
                // When user changed the Text
                CollegeList.this.adapter.getFilter().filter(cs);
            }

            @Override
            public void beforeTextChanged(CharSequence arg0, int arg1, int arg2,
                                          int arg3) {
                // TODO Auto-generated method stub

            }

            @Override
            public void afterTextChanged(Editable arg0) {
                // TODO Auto-generated method stub
            }
        });

        lv.setOnItemClickListener(new AdapterView.OnItemClickListener()
        {
            @Override
            public void onItemClick(AdapterView<?> parent, View view,
                                    int position, long id) {
                // TODO Auto-generated method stub
                extras = getIntent().getExtras();
                String name="";
                String email="";
                String mobileNo = "";
                String gender = "";
                String specialization = "";

                    mobileNo=extras.getString("mobileNumber");
                    name=extras.getString("name");
                    email=extras.getString("email");
                    gender=extras.getString("gender");
                    specialization=extras.getString("specialization");



                Intent loginIntent = new Intent(getApplicationContext(), Register.class);
                loginIntent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                loginIntent.putExtra("mobileNumber", mobileNo);
                loginIntent.putExtra("name", name);
                loginIntent.putExtra("email", email);
                loginIntent.putExtra("gender", gender);
                loginIntent.putExtra("specialization", specialization);
                loginIntent.putExtra("college", college[position]);


                startActivity(loginIntent);
            }
        });
    }

    public void invokeWS() {
        // Show Progress Dialog
        System.out.println("Sivaraj");
        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.get("http://101.53.139.52:8080/Incubation20.0/getCollegeList.html",new AsyncHttpResponseHandler() {


            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {
                System.out.println("Sivaraj Success ");
                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");

                    JSONArray jArray = null;

                    jArray = new JSONArray(result);
                    //JSONObject parentObject = new JSONObject(new String(responseBody));
                    //parentObject.getString("city");
                    ArrayList<String> list=new ArrayList<String>();
                    System.out.println("Shine :" + jArray.length());
                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            System.out.println("City  :"+jArray.getString(0));
                            list.add(jArray.getString(0));
                        }
                        stockArr = new String[list.size()];
                        stockArr = list.toArray(stockArr);
                        System.out.println("Location  :"+list.size());
                        //location=stockArr;
                        System.out.println("Result :"+stockArr.length);
                        // Adding items to listview
                        listLocation(stockArr);

                    }


                } catch (UnsupportedEncodingException e) {
                    e.printStackTrace();
                } catch (JSONException e) {
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
}
