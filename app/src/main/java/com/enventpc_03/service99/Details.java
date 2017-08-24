package com.enventpc_03.service99;

import android.content.Intent;
import android.os.Bundle;
import android.widget.TextView;

public class Details extends BaseActivity {

    TextView ticket,service,status1,reg,due,house,locality,landmark,problem,tech,namee;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getLayoutInflater().inflate(R.layout.activity_details, frameLayout);

        Bundle bundle = getIntent().getExtras();


        String ticketno = bundle.getString("ticket");
        String servicename = bundle.getString("service");
        String status = bundle.getString("status");
        String houseno = bundle.getString("house");
        String area = bundle.getString("area");
        String name_d = bundle.getString("name");
        String reg_no = bundle.getString("reg");
        String due_date = bundle.getString("due");
        String prob = bundle.getString("jobbrief");
        String technician = bundle.getString("tech");




        ticket = (TextView) findViewById(R.id.ticket_no);
        service= (TextView) findViewById(R.id.service_name);
        status1= (TextView) findViewById(R.id.status_d);
        reg = (TextView) findViewById(R.id.registration_date);
        due = (TextView) findViewById(R.id.due_date);
        house = (TextView) findViewById(R.id.house_no);
        locality = (TextView) findViewById(R.id.area_detail);
        namee = (TextView) findViewById(R.id.customer_name);
        problem = (TextView) findViewById(R.id.jobbrief_d);
        tech= (TextView) findViewById(R.id.technician_name);

        ticket.setText(ticketno);
        service.setText(servicename);
        reg.setText(reg_no);
        due.setText(due_date);
        house.setText(houseno);
        status1.setText(status);
        locality.setText(area);
        namee.setText(name_d);
        problem.setText(prob);
        tech.setText(technician);

    }

    @Override
    public void onBackPressed() {
        //Execute your code here

        Intent i = new Intent(Details.this, Track_Ticket.class);
        startActivity(i);
        // close this activity
        finish();

    }
}
