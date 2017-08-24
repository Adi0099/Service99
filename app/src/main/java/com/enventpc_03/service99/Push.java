package com.enventpc_03.service99;

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.os.CountDownTimer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.app.NotificationCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;

public class Push extends Activity {
    int total_time = 1000 * 60 * 60 * 24; // total one day you can change
    int peroid_time = 6 * 60 * 60 * 1000; // one hour time is assumed to make request
    private ProgressDialog pDialog;
    private static String url = "http://service99app.emscrms.in/Service.asmx/GetNotifications";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        new CountDownTimer(total_time, peroid_time) {


            public void onTick(long millisUntilFinished) {



                // make request to web and get reponse and show notification.
//                MakingWebRequest();

                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                NotificationCompat.Builder builder = new NotificationCompat.Builder(Push.this);

                Notification notify ;
                PendingIntent pending = PendingIntent.getActivity(Push.this, 0,
                        new Intent(Push.this, Notification1.class), PendingIntent.FLAG_UPDATE_CURRENT);
                notify = builder.setContentIntent(pending)
                        .setStyle(new NotificationCompat.BigTextStyle().bigText("Testing Static push notification"))
                        .setSmallIcon(R.drawable.logop).setTicker("Static push notification").setWhen(System.currentTimeMillis())
                        .setAutoCancel(true).setContentTitle("Service99 Notification/Offers")
                        .setContentText("Hi , It's me.. aditya.").build();

                notif.notify(0, notify);

//                Toast.makeText(ServiceRequest.this, " Tesitng the data", Toast.LENGTH_LONG).show();
            }

            // Method to start the service
            public void startService(Intent v) {
                startService(new Intent(getBaseContext(), MyService.class));
//                Toast.makeText(ServiceRequest.this, " Service Started..........", Toast.LENGTH_LONG).show();
            }

            public void onFinish() {

//                Toast.makeText(ServiceRequest.this, " ending............", Toast.LENGTH_LONG).show();
                //
            }
        }.start();
    }

    public void MakingWebRequest() {
        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
                new Response.Listener<String>() {

                    @Override
                    public void onResponse(String response) {

                        hidePDialog();


                        try {
                            JSONArray jsonarray = new JSONArray(response);
                            for (int i = 0; i < jsonarray.length(); i++) {
                                JSONObject obj = jsonarray.getJSONObject(i);
//                                Notifications note = new Notifications();
//                                note.setNotificationId(obj.getString("NotificationId"));
//                                note.setNotification(obj.getString("Notification"));
                                String excep = obj.getString("NotificationId");
                                String message1 = obj.getString("Notification");


                                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(Push.this);

                                Notification notify ;
                                PendingIntent pending = PendingIntent.getActivity(Push.this, 0,
                                        new Intent(Push.this, Notification1.class), PendingIntent.FLAG_UPDATE_CURRENT);
                                notify = builder.setContentIntent(pending)
                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message1))
                                        .setSmallIcon(R.drawable.logop).setTicker(excep).setWhen(System.currentTimeMillis())
                                        .setAutoCancel(true).setContentTitle("Service99 Notification/Offers")
                                        .setContentText(message1).build();

                                notif.notify(0, notify);



                            }

                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                        // notifying list adapter about data changes
                        // so that it renders the list view with updated data
//                        adapter.notifyDataSetChanged();
                    }


                    //....  you rest code
                    //       add your notification here

                }, new Response.ErrorListener() {
            @Override
            public void onErrorResponse(VolleyError error) {

                hidePDialog();
            }
        }


        ) {

            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();

                return params;
            }
        };

        RequestQueue requestQueue = Volley.newRequestQueue(Push.this);
        requestQueue.add(stringRequest);

    }
    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }
}
