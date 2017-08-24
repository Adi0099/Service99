package com.enventpc_03.service99;

/**
 * Created by eNventPC-03 on 2/4/2016.
 */

import android.app.Activity;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.net.Uri;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.os.Handler;
import android.support.v4.app.NotificationCompat;
import android.support.v4.content.ContextCompat;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.google.android.gms.appindexing.Action;
import com.google.android.gms.appindexing.AppIndex;
import com.google.android.gms.common.api.GoogleApiClient;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.HashMap;
import java.util.Map;


public class SplashScreen extends Activity {
    // Splash screen timer
    private static int SPLASH_TIME_OUT = 3000;
    int total_time = 1000 * 60 * 60 * 24; // total one day you can change
    int period_time = 6 * 60 * 60 * 1000; // six hour time is assumed to make request
    private static String url = "http://www.app.service99.com/Service.asmx/GetNotifications";
    /**
     * ATTENTION: This was auto-generated to implement the App Indexing API.
     * See https://g.co/AppIndexing/AndroidStudio for more information.
     */
    private GoogleApiClient client;

    public static final String PREFS_NAME = "MyPrefsFile";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        // TODO Auto-generated method stub
        super.onCreate(savedInstanceState);
        setContentView(R.layout.splash);


        // ATTENTION: This was auto-generated to implement the App Indexing API.
        // See https://g.co/AppIndexing/AndroidStudio for more information.
        client = new GoogleApiClient.Builder(this).addApi(AppIndex.API).build();
    }

//    public void MakingWebRequest() {
//        StringRequest stringRequest = new StringRequest(Request.Method.POST, url,
//                new Response.Listener<String>() {
//
//                    @Override
//                    public void onResponse(String response) {
//
//
//                        try {
//                            JSONArray jsonarray = new JSONArray(response);
//                            for (int i = 0; i < jsonarray.length(); i++) {
//                                JSONObject obj = jsonarray.getJSONObject(i);
////                                Notifications note = new Notifications();
////                                note.setNotificationId(obj.getString("NotificationId"));
////                                note.setNotification(obj.getString("Notification"));
//                                String excep = obj.getString("NotificationId");
//                                String message1 = obj.getString("Notification");
////                                int color = 0xff123456;
////                                color = getResources().getColor(R.color.colorPrimary);
//////                                color = ContextCompat.getColor(context, R.color.color_preloader_center);
//
//                                NotificationManager notif = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
//                                NotificationCompat.Builder builder = new NotificationCompat.Builder(SplashScreen.this);
//                                Notification notify;
//
//                                PendingIntent pending = PendingIntent.getActivity(SplashScreen.this, 0,
//                                        new Intent(SplashScreen.this, Notification1.class), PendingIntent.FLAG_UPDATE_CURRENT);
//                                notify = builder.setContentIntent(pending)
//                                        .setStyle(new NotificationCompat.BigTextStyle().bigText(message1))
//                                        .setSmallIcon(R.drawable.logop).setTicker(excep).setWhen(System.currentTimeMillis())
//                                        .setAutoCancel(true).setContentTitle("Service99 Notification/Offers")
//                                        .setContentText(message1).build();
//
//
//                                notif.notify(0, notify);
//
//
//                            }
//
//                        } catch (JSONException e) {
//                            e.printStackTrace();
//                        }
//
//                        // notifying list adapter about data changes
//                        // so that it renders the list view with updated data
////                        adapter.notifyDataSetChanged();
//                    }
//
//
//                    //....  you rest code
//                    //       add your notification here
//
//                }, new Response.ErrorListener() {
//            @Override
//            public void onErrorResponse(VolleyError error) {
//
//
//            }
//        }
//
//
//        ) {
//
//            @Override
//            protected Map<String, String> getParams() {
//                Map<String, String> params = new HashMap<String, String>();
//
//                return params;
//            }
//        };
//
//        RequestQueue requestQueue = Volley.newRequestQueue(SplashScreen.this);
//        requestQueue.add(stringRequest);
//
//    }

    @Override
    protected void onStart() {
        // TODO Auto-generated method stub
        super.onStart();
        SharedPreferences settings = getSharedPreferences(SplashScreen.PREFS_NAME, 0);
        // Get "hasLoggedIn" value. If the value doesn't exist yet false is
        // returned
        boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

        if (hasLoggedIn) {
            // Go directly to main activity
            new Handler().postDelayed(new Runnable() {

            /*
             * Showing splash screen with a timer. This will be useful when you
             * want to show case your app logo / company
             */

                @Override
                public void run() {
                    // This method will be executed once the timer is over
                    // Start your app main activity
                    Intent i = new Intent(SplashScreen.this, Login.class);
                    startActivity(i);
                    // close this activity
                    finish();

                    new CountDownTimer(total_time, period_time) {


                        public void onTick(long millisUntilFinished) {


                            // make request to web and get reponse and show notification.
//                        MakingWebRequest();

//                Toast.makeText(ServiceRequest.this, " Tesitng the data", Toast.LENGTH_LONG).show();
                        }
                        // Method to start the service
                        public void startService(Intent v) {

                            startService(new Intent(getBaseContext(), MyService.class));
//                         Toast.makeText(ServiceRequest.this, " Service Started..........", Toast.LENGTH_LONG).show();
                        }

                        public void onFinish() {

//                Toast.makeText(ServiceRequest.this, " ending............", Toast.LENGTH_LONG).show();
                            //
                        }
                    }.start();
                }
            }, SPLASH_TIME_OUT);
        }
    }
    //handle back button press
    @Override
    public void onBackPressed() {
        //  mIsBackButtonPressed = true;
        super.onBackPressed();
    }
    @Override
    protected void onPause() {
        // TODO Auto-generated method stub
        super.onPause();
        finish();
    }
}