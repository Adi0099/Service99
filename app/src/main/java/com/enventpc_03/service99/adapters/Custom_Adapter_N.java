package com.enventpc_03.service99.adapters;

import android.app.Activity;
import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

import com.enventpc_03.service99.R;
import com.enventpc_03.service99.models.Notifications;

import java.util.ArrayList;
import java.util.List;

public class Custom_Adapter_N extends BaseAdapter

{
    private Activity activity;
    private LayoutInflater inflater;
    private List<Notifications> noteItems;
    private List<Notifications> noteItemSearched = null;
    private List<Notifications> originalnoteList;


    public Custom_Adapter_N(Activity activity, List<Notifications> noteItems) {
        this.activity = activity;
        this.noteItems = noteItems;
        this.originalnoteList = new ArrayList<Notifications>(noteItems);
        this.noteItemSearched = new ArrayList<Notifications>();
        this.noteItemSearched.addAll(noteItems);
    }

    //Add Below Method
    public void reloadData() {
        this.originalnoteList = new ArrayList<Notifications>(noteItems);
        notifyDataSetChanged();
    }

    @Override
    public int getCount() {
        return noteItems.size();
    }

    @Override
    public Object getItem(int location) {
        return noteItems.get(location);
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
            convertView = inflater.inflate(R.layout.list_row_n, null);



        TextView note_Id = (TextView) convertView.findViewById(R.id.note_id);
        TextView note = (TextView) convertView.findViewById(R.id.note);


        // getting movie data for the row
        Notifications m =noteItems.get(position);
        note_Id.setText(m.getNotificationId());
        note.setText(m.getNotification());
        return convertView;
    }
}

