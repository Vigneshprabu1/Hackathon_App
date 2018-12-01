package com.example.ss4.hackathons;

import android.content.Context;
import android.content.DialogInterface;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.support.v7.app.AlertDialog;
import android.support.v7.app.AppCompatActivity;
import android.widget.ArrayAdapter;
import android.widget.EditText;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.loopj.android.http.AsyncHttpClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

import cz.msebera.android.httpclient.Header;

public class Result extends AppCompatActivity {

    private ListView lv;

    // Listview Adapter
    ArrayAdapter<String> adapter;

    // Search EditText
    EditText inputSearch;

    String college[];

    List<String> list;
    String stockArr[];

    Boolean isInternetPresent = false;

    String name, email, collegeId;

    TextView txtWinner;

    final int DEFAULT_TIMEOUT = 60 * 1000;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_result);

        list= new ArrayList<String>();

        lv = (ListView) findViewById(R.id.list_view);
        inputSearch = (EditText) findViewById(R.id.inputSearch);

        txtWinner=(TextView)findViewById(R.id.winner);

        SharedPreferences sharedpreferences = getSharedPreferences("MyPREFERENCES", Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedpreferences.edit();

        //Getting value from session
        name=sharedpreferences.getString("name", "string value");
        email=sharedpreferences.getString("email", "string value");
        collegeId=sharedpreferences.getString("collegeId", "string value");

        ConnectionDetector cd = new ConnectionDetector(getApplicationContext());
        isInternetPresent=cd.checkNetwork();
        if(isInternetPresent) {
            RequestParams params = new RequestParams();
            params.put("email", email);
            invokeWS(params);
        }
        else{
            showAlertDialog(Result.this, "No Internet Connection",
                    "You don't have internet connection.", false);
        }
    }

    public void listResult(final String[] result){

        adapter = new ArrayAdapter<String>(this, R.layout.list_college, R.id.college_name, result);
        lv.setAdapter(adapter);
    }

    public void invokeWS(RequestParams params) {
        // Show Progress Dialog

        // Make RESTful webservice call using AsyncHttpClient object
        AsyncHttpClient client = new AsyncHttpClient();
        client.setTimeout(DEFAULT_TIMEOUT);
        client.get("http://101.53.139.52:8080/Incubation20.0/getResult.html",params, new AsyncHttpResponseHandler() {

            @Override
            public void onSuccess(int statusCode, Header[] headers, byte[] responseBody) {

                try {
                    // JSON Object
                    String result= new String(responseBody, "UTF-8");

                    JSONArray jArray = null;

                    jArray = new JSONArray(result);
                    //JSONObject parentObject = new JSONObject(new String(responseBody));
                    //parentObject.getString("city");
                    ArrayList<String> list=new ArrayList<String>();

                    if (jArray != null) {
                        for (int i = 0; i < jArray.length(); i++) {
                            JSONObject t = jArray.getJSONObject(i);

                            String name=t.getString("winnerName");
                            String email=t.getString("winnerEmail");
                            String result1=name+" ("+email+")";

                            if(t.getString("award")!=null && t.getString("award").equalsIgnoreCase("Winner")){
                                txtWinner.setText(result1);
                                txtWinner.setText(result1);
                            }
                           else {
                                list.add(result1);
                            }
                        }
                        stockArr = new String[list.size()];
                        stockArr = list.toArray(stockArr);
                        System.out.println("Location  :"+list.size());
                        //location=stockArr;
                        System.out.println("Result :"+stockArr.length);
                        // Adding items to listview
                        listResult(stockArr);

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
