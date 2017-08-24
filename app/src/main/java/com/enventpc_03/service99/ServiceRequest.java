package com.enventpc_03.service99;

import android.app.AlertDialog;
import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.support.v4.app.NotificationCompat;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.ListView;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enventpc_03.service99.adapters.Custom_Adapter_N;
import com.enventpc_03.service99.adapters.SpinAdapter;
import com.enventpc_03.service99.models.Item;
import com.enventpc_03.service99.models.Notifications;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.client.HttpClient;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.impl.client.DefaultHttpClient;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.net.URLEncoder;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ServiceRequest extends BaseActivity implements OnItemClickListener {

    //for google place
    private static final String LOG_TAG = "ExampleApp";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyABlJ-d_2dVvRbA-c6DRnSHTphYogubXY0";

    private static final String LOCALITY = "locality";


    int total_time = 1000 * 60 * 60 * 24; // total one day you can change
    int peroid_time = 6 * 60 * 60 * 1000; // six hour time is assumed to make request
    private ProgressDialog pDialog;
    private List<Notifications> noteList = new ArrayList<Notifications>();
    private ListView listView;
    private Custom_Adapter_N adapter;



    private static String url = "http://service99app.emscrms.in/Service.asmx/GetNotifications";

    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.service_request, frameLayout);
        final AutoCompleteTextView autoCompView = (AutoCompleteTextView) findViewById(R.id.editText_enter_name);

        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        autoCompView.setOnItemClickListener(this);

        final EditText mob= (EditText) findViewById(R.id.service_spinner);

        Button login = (Button) findViewById(R.id.booking);

        assert login != null;
        login.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                if (autoCompView.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter Locality", Toast.LENGTH_SHORT)
                            .show();
                }
                else if (mob.toString().trim().equals("")) {
                    Toast.makeText(ServiceRequest.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();

                }
                else if (mob.getText().length() < 10) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter valid mobile number", Toast.LENGTH_SHORT)
                            .show();
                }
                else {

                    Intent i = new Intent(getApplicationContext(), Form.class);
                    String getrec=autoCompView.getText().toString();
                    String mobile=mob.getText().toString();
                    Bundle bundle = new Bundle();
                    bundle.putString("local",getrec);
                    bundle.putString("name",mobile);
                    i.putExtras(bundle);
                    i.putExtras(bundle);
                    startActivity(i);
                    finish();
                }
            }

        });

//        new CountDownTimer(total_time, peroid_time) {
//
//
//            public void onTick(long millisUntilFinished) {
//
//
//
//                // make request to web and get reponse and show notification.
//                MakingWebRequest();
//
////                Toast.makeText(ServiceRequest.this, " Tesitng the data", Toast.LENGTH_LONG).show();
//            }
//
//            // Method to start the service
//            public void startService(Intent v) {
//                startService(new Intent(getBaseContext(), MyService.class));
////                Toast.makeText(ServiceRequest.this, " Service Started..........", Toast.LENGTH_LONG).show();
//            }
//
//            public void onFinish() {
//
////                Toast.makeText(ServiceRequest.this, " ending............", Toast.LENGTH_LONG).show();
//                //
//            }
//        }.start();

    }

    //for google place
    public void onItemClick(AdapterView<?> adapterView, View view, int position, long id) {
        String str = (String) adapterView.getItemAtPosition(position);
        Toast.makeText(this, str, Toast.LENGTH_SHORT).show();
    }

    public static ArrayList<String> autocomplete(String input) {
        ArrayList<String> resultList = null;

        HttpURLConnection conn = null;
        StringBuilder jsonResults = new StringBuilder();
        try {
            StringBuilder sb = new StringBuilder(PLACES_API_BASE + TYPE_AUTOCOMPLETE + OUT_JSON);
            sb.append("?key=" + API_KEY);
            sb.append("&components=country:in");
            sb.append("&components=administrative_area_level_3:lucknow");
            sb.append("&input=" + URLEncoder.encode(input, "utf8"));

            URL url = new URL(sb.toString());

            System.out.println("URL: "+url);
            conn = (HttpURLConnection) url.openConnection();
            InputStreamReader in = new InputStreamReader(conn.getInputStream());

            // Load the results into a StringBuilder
            int read;
            char[] buff = new char[1024];
            while ((read = in.read(buff)) != -1) {
                jsonResults.append(buff, 0, read);
            }
        } catch (MalformedURLException e) {
            Log.e(LOG_TAG, "Error processing Places API URL", e);
            return resultList;
        } catch (IOException e) {
            Log.e(LOG_TAG, "Error connecting to Places API", e);
            return resultList;
        } finally {
            if (conn != null) {
                conn.disconnect();
            }
        }

        try {

            // Create a JSON object hierarchy from the results
            JSONObject jsonObj = new JSONObject(jsonResults.toString());
            JSONArray predsJsonArray = jsonObj.getJSONArray("predictions");

            // Extract the Place descriptions from the results
            resultList = new ArrayList<String>(predsJsonArray.length());
            for (int i = 0; i < predsJsonArray.length(); i++) {
                System.out.println(predsJsonArray.getJSONObject(i).getString("description"));
                System.out.println("============================================================");
                resultList.add(predsJsonArray.getJSONObject(i).getString("description"));
            }
        } catch (JSONException e) {
            Log.e(LOG_TAG, "Cannot process JSON results", e);
        }

        return resultList;
    }

    class GooglePlacesAutocompleteAdapter extends ArrayAdapter<String> implements Filterable {
        private ArrayList<String> resultList;

        public GooglePlacesAutocompleteAdapter(Context context, int textViewResourceId) {
            super(context, textViewResourceId);
        }

        @Override
        public int getCount() {
            return resultList.size();
        }

        @Override
        public String getItem(int index) {
            return resultList.get(index);
        }

        @Override
        public Filter getFilter() {
            Filter filter = new Filter() {
                @Override
                protected FilterResults performFiltering(CharSequence constraint) {
                    FilterResults filterResults = new FilterResults();
                    if (constraint != null) {
                        // Retrieve the autocomplete results.
                        resultList = autocomplete(constraint.toString());

                        // Assign the data to the FilterResults
                        filterResults.values = resultList;
                        filterResults.count = resultList.size();
                    }
                    return filterResults;
                }

                @Override
                protected void publishResults(CharSequence constraint, FilterResults results) {
                    if (results != null && results.count > 0) {
                        notifyDataSetChanged();
                    } else {
                        notifyDataSetInvalidated();
                    }
                }
            };
            return filter;
        }
    }
    //////////////////////////////////////////////////////////////////////GPS Activity Close//////////////////////////////////////


    @Override
    public void onBackPressed() {
        // mIsBackButtonPressed = true;
        // super.onBackPressed();
        AlertDialog.Builder alertDialogBuilder = new AlertDialog.Builder(this);
        alertDialogBuilder.setTitle("Exit Application?");
        alertDialogBuilder
                .setMessage("Click yes to exit!")
                .setCancelable(true)
                .setPositiveButton("Yes",
                        new DialogInterface.OnClickListener() {
                            public void onClick(DialogInterface dialog, int id) {
                                // Write your code here to execute after dialog

                                Intent startMain = new Intent(Intent.ACTION_MAIN);
                                startMain.addCategory(Intent.CATEGORY_HOME);
                                startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                                startMain.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP);
                                startActivity(startMain);
                                finish();
                                ServiceRequest.isQuit = true;

                                Toast.makeText(getApplicationContext(), "Thank You",
                                        Toast.LENGTH_LONG).show();
                            }
                        })

                .setNegativeButton("No", new DialogInterface.OnClickListener() {
                    public void onClick(DialogInterface dialog, int id) {

                        dialog.cancel();

                    }
                });

        AlertDialog alertDialog = alertDialogBuilder.create();
        alertDialog.show();
        return;
    }
    public static boolean isQuit = false;

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
//         Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.refresh);
        item.setVisible(false);
        return true;
    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
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
                                NotificationCompat.Builder builder = new NotificationCompat.Builder(ServiceRequest.this);

                                Notification notify ;
                                PendingIntent pending = PendingIntent.getActivity(ServiceRequest.this, 0,
                                        new Intent(ServiceRequest.this, Notification1.class), PendingIntent.FLAG_UPDATE_CURRENT);
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

        RequestQueue requestQueue = Volley.newRequestQueue(ServiceRequest.this);
        requestQueue.add(stringRequest);

    }
    @Override
    public void onPause() {
        super.onPause();  // Always call the superclass method first

       finish();
        }

}

