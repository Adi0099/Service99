package com.enventpc_03.service99;

import android.app.Activity;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.preference.PreferenceManager;
import android.provider.Settings;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

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

public class Login extends Activity implements View.OnClickListener {

	private static final String REGISTER_URL = "http://www.app.service99.com/Service.asmx/NewCustomer";

	public static final String PREFS_NAME = "MyPrefsFile";

	public static final String KEY_USERNAME = "name";
	public static final String KEY_MOB = "mobileNo";
	public static final String KEY_EMAIL = "email";
	public static final String SOURCE ="source";
	public static final String DEVICEID ="deviceId";

	// flag for Internet connection status
	Boolean isInternetPresent = false;

	// Connection detector class
	ConnectionDetector cd;

	String result="";
	private ProgressDialog pDialog;

	private EditText editTextUsername;
	private EditText editTextEmail;
	private EditText editTextmob;

	private Button buttonRegister;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_login);

		editTextUsername = (EditText) findViewById(R.id.editText_enter_name);
		editTextmob= (EditText) findViewById(R.id.editText_mobNo);
		editTextEmail= (EditText) findViewById(R.id.editText_email);

		buttonRegister = (Button) findViewById(R.id.button_register);



		buttonRegister.setOnClickListener(this);
		pDialog = new ProgressDialog(this);
		cd = new ConnectionDetector(getApplicationContext());
		// get Internet status
		isInternetPresent = cd.isConnectingToInternet();

//		getSupportActionBar().hide();


	}


//	private void registerUser(){
//
//
//
//			final String source = "Android";
//			final String username = editTextUsername.getText().toString().trim();
//			final String mobile = editTextmob.getText().toString().trim();
//			final String email = editTextEmail.getText().toString().trim();
//
//			final String id = Settings.Secure.getString(this.getContentResolver(),
//					Settings.Secure.ANDROID_ID);
//
//
//			StringRequest stringRequest = new StringRequest(Request.Method.POST, REGISTER_URL,
//					new Response.Listener<String>() {
//
//
//						@Override
//						public void onResponse(String response) {
//
//							hidePDialog();
//							try {
//								//String result="";
//								//Do it with this it will work
//								JSONArray jsonarray = new JSONArray(response);
//								for (int i = 0; i < jsonarray.length(); i++) {
//									JSONObject person = jsonarray.getJSONObject(i);
//									String excep = person.getString("Exception");
//									String message1 = person.getString("Message");
//									String custome_id = person.getString("CustomerId");
//
//									if (excep.equalsIgnoreCase("True")) {
////										Toast.makeText(Login.this, excep, Toast.LENGTH_LONG)
////												.show();
//									} else {
//
////										Toast.makeText(Login.this, excep, Toast.LENGTH_LONG)
////												.show();
//
//										// if email and mb is valid than login
//										Toast.makeText(Login.this, message1.toString(), Toast.LENGTH_LONG).show();
//										Intent i1 = new Intent(Login.this, ServiceRequest.class);
//										startActivity(i1);
//										finish();
////										Toast.makeText(Login.this, excep.toString(), Toast.LENGTH_LONG).show();
//
////										Toast.makeText(Login.this, custome_id.toString(), Toast.LENGTH_LONG).show();
//
//										SharedPreferences sp = getSharedPreferences(
//												"First_share_memory", Activity.MODE_PRIVATE);
//										// save in cache memory
//										sp.edit().putString("customerId", custome_id).commit();
//										sp.edit().putString("Mobile", mobile).commit();
//
//										SharedPreferences prefs = PreferenceManager.getDefaultSharedPreferences(getApplicationContext());
//										SharedPreferences.Editor editor1 = prefs.edit();
//										editor1.putString("customerId", custome_id);
//										editor1.commit();
//
//
//
//						/*
//						 * Intent i1 = new Intent(Login.this, Splash.class);
//						 * startActivity(i1); finish();
//						 */
//										// //////// { onFinish body}///////////////////////////////
//
//										Log.e("FINISHED_LOG", "onFinish request");
//
//										SharedPreferences settings = getSharedPreferences(
//												Login.PREFS_NAME, MODE_MULTI_PROCESS);
//										SharedPreferences.Editor editor = settings.edit();
//
//										// Set "hasLoggedIn" to true
//										editor.putBoolean("hasLoggedIn", true);
//
//										// Commit the edits!
//										editor.commit();
//
//									}
//								}
//							} catch (JSONException e) {
//								e.printStackTrace();
//							}
//
//						}
//					},
//					new Response.ErrorListener() {
//						@Override
//						public void onErrorResponse(VolleyError error) {
////							Toast.makeText(Login.this, error.toString(), Toast.LENGTH_LONG).show();
//
//						}
//					}) {
//				@Override
//				protected Map<String, String> getParams() {
//					Map<String, String> params = new HashMap<String, String>();
//					params.put(KEY_USERNAME, username);
//					params.put(KEY_MOB, mobile);
//					params.put(KEY_EMAIL, email);
//					params.put(SOURCE, source);
//					params.put(DEVICEID, id);
//					return params;
//				}
//
//			};
//
//			RequestQueue requestQueue = Volley.newRequestQueue(this);
//			requestQueue.add(stringRequest);
//
//
//	}



	@Override
	public void onClick(View v) {


		if(v == buttonRegister){

			cd = new ConnectionDetector(getApplicationContext());
			// get Internet status
			isInternetPresent = cd.isConnectingToInternet();

			if (editTextUsername.getText().toString().trim().equals("")) {
				Toast.makeText(getApplicationContext(),
						"Please enter username", Toast.LENGTH_SHORT)
						.show();
			} else if (editTextmob.getText().toString().trim().equals("")) {
				Toast.makeText(getApplicationContext(),
						"Please enter your mobile number", Toast.LENGTH_SHORT)
						.show();
			} else if (editTextmob.getText().length() < 10) {
				Toast.makeText(getApplicationContext(),
						"Please enter valid mobile number", Toast.LENGTH_SHORT)
						.show();
			}
			else if (!editTextEmail.getText().toString().trim().equals(""))
			{
				if (!android.util.Patterns.EMAIL_ADDRESS.matcher(
						editTextEmail.getText().toString().trim()).matches()) {
					Toast.makeText(getApplicationContext(),
							"Please enter valid e-mail id", Toast.LENGTH_SHORT)
							.show();
				}
				else {
					if (isInternetPresent) {
//						registerUser(); //comment for static testing
						pDialog.setMessage("Loading...");
						pDialog.show();

						Toast.makeText(Login.this, "Login Successfully0", Toast.LENGTH_LONG).show();
						Intent i1 = new Intent(Login.this, ServiceRequest.class);
						startActivity(i1);
						finish();
						pDialog.hide();

//						Toast.makeText(getApplicationContext(),
//								"connecting...", Toast.LENGTH_SHORT)
//								.show();


					} else {

						Toast.makeText(Login.this, "Unable to connect the server, please check your data settings", Toast.LENGTH_LONG)
								.show();
					}
				}
			}

			else {
				// Do your stuff


				if (isInternetPresent) {
//					registerUser();
					pDialog.setMessage("Loading...");
					pDialog.show();
					SharedPreferences settings = getSharedPreferences(
							Login.PREFS_NAME, MODE_MULTI_PROCESS);
					SharedPreferences.Editor editor = settings.edit();

					// Set "hasLoggedIn" to true
					editor.putBoolean("hasLoggedIn", true);

					// Commit the edits!
					editor.commit();
					Toast.makeText(Login.this, "Login Successfully1", Toast.LENGTH_LONG).show();
					Intent i1 = new Intent(Login.this, ServiceRequest.class);
					startActivity(i1);
					finish();
//					pDialog.hide();
//					Toast.makeText(getApplicationContext(),
//							"connecting...", Toast.LENGTH_SHORT)
//							.show();
				}

				else {

					Toast.makeText(Login.this, "Unable to connect the server, please check your data settings", Toast.LENGTH_LONG)
							.show();
				}

			}


		}
	}


	@Override
	protected void onStart() {
		// TODO Auto-generated method stub
		super.onStart();
		SharedPreferences settings = getSharedPreferences(Login.PREFS_NAME, 0);
		// Get "hasLoggedIn" value. If the value doesn't exist yet false is
		// returned
		boolean hasLoggedIn = settings.getBoolean("hasLoggedIn", false);

		if (hasLoggedIn) {
			// Go directly to main activity
			Intent i1 = new Intent(Login.this, ServiceRequest.class);
			startActivity(i1);
			finish();
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
}