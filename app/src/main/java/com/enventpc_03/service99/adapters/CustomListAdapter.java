package com.enventpc_03.service99.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enventpc_03.service99.R;
import com.enventpc_03.service99.models.Customer;

import java.util.ArrayList;
import java.util.List;

public class CustomListAdapter extends BaseAdapter

{
	private Activity activity;
	private LayoutInflater inflater;
	private List<Customer> customersItems;
	private List<Customer> customersItemSearched = null;
	private List<Customer> originalCustomerList;


	public CustomListAdapter(Activity activity, List<Customer> customersItems) {
		this.activity = activity;
		this.customersItems = customersItems;
		this.originalCustomerList = new ArrayList<Customer>(customersItems);
		this.customersItemSearched = new ArrayList<Customer>();
		this.customersItemSearched.addAll(customersItems);
	}

	//Add Below Method
	public void reloadData() {
		this.originalCustomerList = new ArrayList<Customer>(customersItems);
		notifyDataSetChanged();
	}

	@Override
	public int getCount() {
		return customersItems.size();
	}

	@Override
	public Object getItem(int location) {
		return customersItems.get(location);
	}

	@Override
	public long getItemId(int position) {
		return position;
	}

	@Override
	public View getView(int position, View convertView, ViewGroup parent) {

		if (inflater == null)
			inflater = (LayoutInflater) activity
					.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
		if (convertView == null)
			convertView = inflater.inflate(R.layout.list_row, null);



		TextView id = (TextView) convertView.findViewById(R.id.job_id);
		TextView serial = (TextView) convertView.findViewById(R.id.serial);
		TextView service = (TextView) convertView.findViewById(R.id.service_code);
		TextView status = (TextView) convertView.findViewById(R.id.status);
		TextView date = (TextView) convertView.findViewById(R.id.date);



		// getting movie data for the row
		Customer m = customersItems.get(position);


		id.setText(m.getTitle());

		serial.setText(m.getSerial());

		service.setText(m.getService());

		status.setText(m.getStatus());

		date.setText(m.getDate());

		return convertView;
	}



}

