package com.example.pedaars.lister;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class Home extends AppCompatActivity implements View.OnClickListener {

    private Button NewCust;
    private Button Cust;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        setContentView(R.layout.activity_home);
        NewCust = (Button)findViewById(R.id.New_Cust_button);
        Cust = (Button)findViewById(R.id.Ex_Cust_button);
        NewCust.setOnClickListener(this);
        Cust.setOnClickListener(this);

    }


    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.New_Cust_button:
                NewC();
                break;
            case R.id.Ex_Cust_button:
                ExC();
                break;
        }

    }

    private void NewC() {
        Intent intent = new Intent(Home.this, NewCustomer.class);
        startActivity(intent);
    }

    private void ExC() {
        Intent intent = new Intent(Home.this, Customers.class);
        startActivity(intent);
    }


}
