package com.enventpc_03.service99;

import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.View;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.VolleyLog;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enventpc_03.service99.adapters.CustomListAdapter;
import com.enventpc_03.service99.models.Customer;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class Track_Ticket extends BaseActivity {
    // Log tag
    private static final String TAG = Track_Ticket.class.getSimpleName();

    private static String url = "http://www.app.service99.com/Service.asmx/CustomerTicketLog";
    private static String url_detail = "http://www.app.service99.com/Service.asmx/CustomerTicketView";

    private static String CUSTOMERID= "customerId";
    private static String JOBID= "JobId";

    private ProgressDialog pDialog;
    private List<Customer> customerList = new ArrayList<Customer>();
    private ListView listView;
    private CustomListAdapter adapter;
    Button refresh;
    ImageView connection;
    TextView note;

    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_track__ticket, frameLayout);

        listView = (ListView) findViewById(R.id.list);
        adapter = new CustomListAdapter(this, customerList);
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
            SharedPreferences mSharedPreference = PreferenceManager.getDefaultSharedPreferences(getBaseContext());
            final String value = (mSharedPreference.getString("customerId", "Default_Value"));

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
                                    Customer customer = new Customer();
                                    customer.setTitle(obj.getString("JobId"));
                                    customer.setSerial(obj.getString("SerialNo"));
                                    customer.setService(obj.getString("ServiceName"));
                                    customer.setStatus(obj.getString("JobStatus"));
                                    customer.setDate(obj.getString("RegistrationDate"));

                                    // adding movie to movies array
                                    customerList.add(customer);

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
            }) {
                @Override
                protected Map<String, String> getParams() {
                    Map<String, String> params = new HashMap<String, String>();
                    params.put(CUSTOMERID, value);
                    return params;
                }

            };

            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
//        // Adding request to request queue
//        AppController.getInstance().addToRequestQueue(stringRequest);

            listView.setOnItemClickListener(new AdapterView.OnItemClickListener() {

                @Override
                public void onItemClick(AdapterView<?> parent, View view, int position, long id) {

                    TextView c = (TextView) view.findViewById(R.id.job_id);
                    final String job1 = c.getText().toString();

                    StringRequest stringRequest = new StringRequest(Request.Method.POST, url_detail,
                            new Response.Listener<String>() {


                                @Override
                                public void onResponse(String response) {

                                    hidePDialog();
                                    try {
                                        //String result="";
                                        //Do it with this it will work
                                        JSONArray jsonarray = new JSONArray(response);
                                        for (int i = 0; i < jsonarray.length(); i++) {
                                            JSONObject person = jsonarray.getJSONObject(i);
                                            String ticket = person.getString("SerialNo");
                                            String service = person.getString("ServiceName");
                                            String status = person.getString("JobStatus");
                                            String house = person.getString("HouseNo");
                                            String area = person.getString("AreaName");
                                            String name = person.getString("CustomerName");
                                            String registration = person.getString("RegistrationDate");
                                            String due = person.getString("DueDate");
                                            String jobbrief = person.getString("JobBrief");
                                            String technician = person.getString("AssignTo");


                                            // if email and mb is valid than login

                                            Intent i1 = new Intent(Track_Ticket.this, Details.class);

                                            i1.putExtra("ticket", ticket);
                                            i1.putExtra("service", service);
                                            i1.putExtra("status", status);
                                            i1.putExtra("house", house);
                                            i1.putExtra("area", area);
                                            i1.putExtra("name", name);
                                            i1.putExtra("reg", registration);
                                            i1.putExtra("due", due);
                                            i1.putExtra("jobbrief", jobbrief);
                                            i1.putExtra("tech", technician);
                                            startActivity(i1);
                                            finish();

                                        }

                                    } catch (JSONException e) {
                                        e.printStackTrace();
                                    }

                                }
                            },
                            new Response.ErrorListener() {
                                @Override
                                public void onErrorResponse(VolleyError error) {
                                    Toast.makeText(Track_Ticket.this, error.toString(), Toast.LENGTH_LONG).show();
                                    connection.setVisibility(View.VISIBLE);
                                    note.setVisibility(View.VISIBLE);

                                }
                            }) {
                        @Override
                        protected Map<String, String> getParams() {
                            Map<String, String> params = new HashMap<String, String>();
                            params.put(CUSTOMERID, value);
                            params.put(JOBID, job1);

                            return params;
                        }

                    };

                    RequestQueue requestQueue = Volley.newRequestQueue(Track_Ticket.this);
                    requestQueue.add(stringRequest);

//
//                Intent intent = new Intent(SearchPeople.this, Details.class);
//                intent.putExtra(Title, name);
//                intent.putExtra(Location, location);
//                intent.putExtra(Description, description);
//                intent.putExtra("images", bitmap);
//
//
//                startActivity(intent);

                }
            });
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

        Intent i = new Intent(Track_Ticket.this, ServiceRequest.class);
        startActivity(i);
        // close this activity
        finish();

    }



}