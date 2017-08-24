package com.enventpc_03.service99;

import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.AsyncTask;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Filter;
import android.widget.Filterable;
import android.widget.Spinner;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;
import com.enventpc_03.service99.adapters.SpinAdapter;
import com.enventpc_03.service99.models.Item;

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

public class Form extends BaseActivity implements AdapterView.OnItemClickListener {

    private static final String REGISTER_URL = "http://www.app.service99.com/Service.asmx/GenerateTicket";

    public static final String PREFS_NAME = "MyPrefsFile";

    public static final String CUSTOMERID = "customerId";
    public static final String USERNAME = "name";
    public static final String HOUSENO = "houseNo";
    public static final String LOCALITY = "areaName";
    public static final String SERVICE = "serviceId";
    public static final String MOBILE = "mobile";
    public static final String EMAIL = "email";
    public static final String PROBLEM = "jobBrief";
    private ProgressDialog pDialog;
    Spinner spin;
    String service;
    // flag for Internet connection status
    Boolean isInternetPresent = false;

    // Connection detector class
    ConnectionDetector cd;

//    ArrayList<String> listItems = new ArrayList<>();
//    ArrayAdapter<String> adapter;
    private List<Item> customerList = new ArrayList<Item>();
    List<String> categories = new ArrayList<String>();

    private SpinAdapter adapter;
    private List<Item> items;

    AdapterView.OnItemSelectedListener listener;

    private EditText editname, houseNo, mobile, email, problem;
    Spinner service_need;
    AutoCompleteTextView autoCompView;
    String obj;

    //for google place
    private static final String LOG_TAG = "ExampleApp";

    private static final String PLACES_API_BASE = "https://maps.googleapis.com/maps/api/place";
    private static final String TYPE_AUTOCOMPLETE = "/autocomplete";
    private static final String OUT_JSON = "/json";

    //------------ make your specific key ------------
    private static final String API_KEY = "AIzaSyABlJ-d_2dVvRbA-c6DRnSHTphYogubXY0";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_form, frameLayout);
        autoCompView = (AutoCompleteTextView) findViewById(R.id.colony);

        autoCompView.setAdapter(new GooglePlacesAutocompleteAdapter(this, R.layout.list_item));
        autoCompView.setOnItemClickListener(this);

        editname = (EditText) findViewById(R.id.name);
        houseNo = (EditText) findViewById(R.id.houseNo);
//        colony_l= (EditText) findViewById(R.id.colony);
//        service_need = (Spinner) findViewById(R.id.service_need);
        mobile = (EditText) findViewById(R.id.mobile);
        email = (EditText) findViewById(R.id.email);
        problem = (EditText) findViewById(R.id.problem);
        Button submit = (Button) findViewById(R.id.submit);

        pDialog = new ProgressDialog(this);
        cd = new ConnectionDetector(getApplicationContext());
        // get Internet status
        isInternetPresent = cd.isConnectingToInternet();

        assert submit != null;
        submit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

                if (editname.getText().toString().trim().equals("")) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter your name", Toast.LENGTH_SHORT)
                            .show();
                } else if (houseNo.getText().toString().trim().equals("")) {
                    Toast.makeText(Form.this, "Please enter your house no", Toast.LENGTH_SHORT).show();

                } else if (autoCompView.getText().toString().trim().equals("")) {
                    Toast.makeText(Form.this, "Please enter your locality", Toast.LENGTH_SHORT).show();
                }

                else if (categories.get(spin.getSelectedItemPosition()).trim().equals("What Service You Need")) {
                    Toast.makeText(Form.this, "Please Select item", Toast.LENGTH_SHORT).show();

                }
                else if (mobile.getText().toString().trim().equals("")) {
                    Toast.makeText(Form.this, "Please enter your mobile number", Toast.LENGTH_SHORT).show();

                } else if (mobile.getText().length() < 10) {
                    Toast.makeText(getApplicationContext(),
                            "Please enter valid mobile number", Toast.LENGTH_SHORT)
                            .show();
                } else if (problem.getText().toString().trim().equals("")) {
                    Toast.makeText(Form.this, "Please describe your problem", Toast.LENGTH_SHORT).show();
                } else if (!email.getText().toString().trim().equals("")) {
                    if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
                            email.getText().toString().trim()).matches()) {
                        Toast.makeText(getApplicationContext(),
                                "Please enter valid e-mail id", Toast.LENGTH_SHORT)
                                .show();
                    } else {
                        if (isInternetPresent) {
//                            registerUser();
                            Intent i1 = new Intent(Form.this, Suceessful.class);
                            i1.putExtra("job_id", "ABC123467");
                            startActivity(i1);
                            finish();
                            pDialog.setMessage("Loading...");
                            pDialog.show();
//                            Toast.makeText(getApplicationContext(),
//                                    "connecting...", Toast.LENGTH_SHORT)
//                                    .show();
//

                        } else {

                            Toast.makeText(Form.this, "Unable to connect the server, please check your data settings", Toast.LENGTH_LONG)
                                    .show();
                        }
                    }
                } else {
                    // Do your stuff


                    if (isInternetPresent) {
//                        registerUser();
                        Intent i1 = new Intent(Form.this, Suceessful.class);
                        i1.putExtra("job_id", "ABC123467");
                        startActivity(i1);
                        finish();
                        pDialog.setMessage("Loading...");
                        pDialog.show();
//                        Toast.makeText(getApplicationContext(),
//                                "connecting...", Toast.LENGTH_SHORT)
//                                .show();
                    } else {

                        Toast.makeText(Form.this, "Unable to connect the server, please check your data settings", Toast.LENGTH_LONG)
                                .show();
                    }

                }

            }


        });



//        for (int i = 0; i < 1; i++) {
//
//            Item customer = new Item();
//            customer.setiD(""+i);
//            customer.setsText("What Service You Need");
//
//            // adding movie to movies array
//            customerList.add(customer);
//        }


        categories.add("Automobile");
        categories.add("Business Services");
        categories.add("Computers");
        categories.add("Education");
        categories.add("Personal");
        categories.add("Travel");

        spin = (Spinner) findViewById(R.id.service_need);
        adapter = new SpinAdapter(this, categories);
        spin.setAdapter(adapter);




        Bundle bundle = getIntent().getExtras();
        String stuff1 = bundle.getString("local");
        autoCompView.setText(stuff1);
        String stuff2=(bundle.getString("name"));
        mobile.setText(stuff2);

//        adapter.notifyDataSetChanged();

//        String name = bundle.getString("name");
//        adapter.add(name);


    }

    private void registerUser() {

        final SharedPreferences mSharedPreference= PreferenceManager.getDefaultSharedPreferences(getBaseContext());
        final String value=(mSharedPreference.getString("customerId", "Default_Value"));


        final String customer_id =value.toString().trim();
        final String username = editname.getText().toString().trim();
        final String house = houseNo.getText().toString().trim();
        final String local_area = autoCompView.getText().toString().trim();
        final String service=customerList.get(spin.getSelectedItemPosition()).getiD().toString().trim();
        final String mobile_no = mobile.getText().toString().trim();
        final String email_id = email.getText().toString().trim();
        final String prob = problem.getText().toString().trim();

        StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
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
                                String excep = person.getString("Exception");
                                String message1 = person.getString("Message");
                                String job = person.getString("JobNo");

                                if (excep.equalsIgnoreCase("True")) {

                                } else {

//                                    Toast.makeText(Form.this, excep, Toast.LENGTH_LONG)
//                                            .show();
                                    editname.setText("");
                                    // if email and mb is valid than login

                                    Intent i1 = new Intent(Form.this, Suceessful.class);
                                    i1.putExtra("job_id", job);
                                    startActivity(i1);
                                    finish();

                                }
                            }
                        } catch (JSONException e) {
                            e.printStackTrace();
                        }

                    }
                },
                new Response.ErrorListener() {
                    @Override
                    public void onErrorResponse(VolleyError error) {
//                        Toast.makeText(Form.this, error.toString(), Toast.LENGTH_LONG).show();

                    }
                }) {
            @Override
            protected Map<String, String> getParams() {
                Map<String, String> params = new HashMap<String, String>();
                params.put(CUSTOMERID,customer_id);
                params.put(USERNAME, username);
                params.put(HOUSENO, house);
                params.put(LOCALITY, local_area);
                params.put(SERVICE,service);
                params.put(MOBILE, mobile_no);
                params.put(EMAIL, email_id);
                params.put(PROBLEM, prob);
                return params;
            }

        };

        RequestQueue requestQueue = Volley.newRequestQueue(this);
        requestQueue.add(stringRequest);


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

            System.out.println("URL: " + url);
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

    public void onStart() {
        super.onStart();
        BackTask bt = new BackTask();
        bt.execute();
    }

    private class BackTask extends AsyncTask<Void, Void, Void> {
        ArrayList<String> list;

        protected void onPreExecute() {
            super.onPreExecute();
            list = new ArrayList<>();

        }

        protected Void doInBackground(Void... params) {
            InputStream is = null;
            String result = "";
            try {
                HttpClient httpclient = new DefaultHttpClient();
                HttpPost httppost = new HttpPost("http://www.app.service99.com/Service.asmx/GetServiceList");
                HttpResponse response = httpclient.execute(httppost);
                HttpEntity entity = response.getEntity();
                // Get our response as a String.
                is = entity.getContent();
            } catch (IOException e) {
                e.printStackTrace();
            }

            //convert response to string
            try {
                BufferedReader reader = new BufferedReader(new InputStreamReader(is, "utf-8"));
                String line = null;
                while ((line = reader.readLine()) != null) {
                    result += line;
                }
                is.close();
                //result=sb.toString();
            } catch (Exception e) {
                e.printStackTrace();
            }
            // parse json data
            try {
                JSONArray jArray = new JSONArray(result);
                for (int i = 0; i < jArray.length(); i++) {
                    JSONObject obj = jArray.getJSONObject(i);
                    Item customer = new Item();
                    customer.setiD(obj.getString("ServiceId"));
                    customer.setsText(obj.getString("ServiceName"));



//                    adapter.notifyDataSetChanged();

                    // adding movie to movies array
                    customerList.add(customer);



                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
//            adapter.notifyDataSetChanged();
            return null;
        }

        protected void onPostExecute(Void result) {
//            listItems.addAll(list);



            Bundle bundle = getIntent().getExtras();

            position = customerList.indexOf(bundle.getString("name"));
            spin.setSelection(position,false);
            adapter.notifyDataSetChanged();
        }

    }

    private void hidePDialog() {
        if (pDialog != null) {
            pDialog.dismiss();
            pDialog = null;
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {

        MenuInflater inflater = getMenuInflater();
//         Inflate menu to add items to action bar if it is present.
        inflater.inflate(R.menu.main, menu);
        MenuItem item = menu.findItem(R.id.refresh);
        item.setVisible(false);
        return true;
    }


}