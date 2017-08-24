package com.enventpc_03.service99;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

public class Suceessful extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
//        overridePendingTransition(R.anim.in, R.anim.out);
        setContentView(R.layout.activity_suceessful);

        Bundle bundle = getIntent().getExtras();
        String message = bundle.getString("job_id");

        Button home = (Button) findViewById(R.id.redirect);
        TextView job = (TextView) findViewById(R.id.job_no);
        job.setText(message);
        home.setOnClickListener(new View.OnClickListener()
        {
            @Override
            public void onClick(View v) {

                Intent i = new Intent(Suceessful.this, ServiceRequest.class);
                startActivity(i);
                // close this activity
                finish();
            }
        });
    }

    @Override
    public void onBackPressed() {
        //Execute your code here

        Intent i = new Intent(Suceessful.this, ServiceRequest.class);
        startActivity(i);
        // close this activity
        finish();

    }
}
