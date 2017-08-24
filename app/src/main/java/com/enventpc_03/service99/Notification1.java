package com.enventpc_03.service99;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enventpc_03.service99.adapters.Custom_Adapter_N;
import com.enventpc_03.service99.models.Notifications;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.List;

public class Notification1 extends BaseActivity {
    // Log tag
    private static final String TAG = Notification1.class.getSimpleName();

    private static String url = "http://www.app.service99.com/Service.asmx/GetNotifications";



    private ProgressDialog pDialog;
    private List<Notifications> noteList = new ArrayList<Notifications>();
    private ListView listView;
    private Custom_Adapter_N adapter;
    ImageView connection;
    TextView note;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_notification, frameLayout);

        listView = (ListView) findViewById(R.id.list_note);
        adapter = new Custom_Adapter_N(this, noteList);
        listView.setAdapter(adapter);

        pDialog = new ProgressDialog(this);
        // Showing progress dialog before making http request
        pDialog.setMessage("Loading...");
        pDialog.show();


        connection = (ImageView) findViewById(R.id.image_view);
        note = (TextView) findViewById(R.id.err);
        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        if (!isInternetPresent) {
            hidePDialog();
            connection.setVisibility(View.VISIBLE);
            note.setVisibility(View.VISIBLE);
        } else {

            connection.setVisibility(View.INVISIBLE);
            note.setVisibility(View.INVISIBLE);
            // Creating volley request obj
            StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            Log.d(TAG, response.toString());
                            hidePDialog();


                            try {
                                JSONArray jsonarray = new JSONArray(response);
                                for (int i = 0; i < jsonarray.length(); i++) {
                                    JSONObject obj = jsonarray.getJSONObject(i);
                                    Notifications note = new Notifications();
                                    note.setNotificationId(obj.getString("NotificationId"));
                                    note.setNotification(obj.getString("Notification"));

//                                String id = obj.getString("Exception");
//                                String message1 = obj.getString("Message");
//                                Toast.makeText(Notification1.this, id.toString(), Toast.LENGTH_LONG).show();
//                                Toast.makeText(Notification1.this, message1.toString(), Toast.LENGTH_LONG).show();


                                    // adding movie to movies array
                                    noteList.add(note);

                                }

                            } catch (JSONException e) {
                                e.printStackTrace();
                            }

                            // notifying list adapter about data changes
                            // so that it renders the list view with updated data
                            adapter.notifyDataSetChanged();
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    VolleyLog.d(TAG, "Error: " + error.getMessage());
                    hidePDialog();

                }
            });

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(stringRequest);
        }

    }
    @Override
    public void onDestroy() {
        super.onDestroy();
        hidePDialog();
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }


    @Override
    public void onBackPressed() {
        //Execute your code here

        Intent i = new Intent(Notification1.this, ServiceRequest.class);
        startActivity(i);
        // close this activity
        finish();

    }
}