package com.enventpc_03.service99.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enventpc_03.service99.R;
import com.enventpc_03.service99.models.Item;

import java.util.ArrayList;
import java.util.List;

public class SpinAdapter extends BaseAdapter

{
    private Activity activity;
    private LayoutInflater inflater;

    private List<String> customersItems;
    private List<String> customersItemSearched = null;
    private List<String> originalCustomerList;


    public SpinAdapter(Activity activity, List<String> customersItems) {
        this.activity = activity;
        this.customersItems = customersItems;
        this.originalCustomerList = new ArrayList<String>(customersItems);
        this.customersItemSearched = new ArrayList<>();
        this.customersItemSearched.addAll(customersItems);
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
            convertView = inflater.inflate(R.layout.spin_row, null);

        TextView id = (TextView) convertView.findViewById(R.id.id);
        TextView serial = (TextView) convertView.findViewById(R.id.name);

        // getting movie data for the row
        String m = customersItems.get(position);

        id.setText(m);

        serial.setText(m);

        return convertView;

    }


}

