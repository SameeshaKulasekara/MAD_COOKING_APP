package com.example.projectmad.activities.driver;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projectmad.R;

public class DeliveryDetails extends AppCompatActivity {

    TextView address, price, driverCost;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delivery_details);

        address = findViewById(R.id.address);
        price = findViewById(R.id.price);
        driverCost = findViewById(R.id.driver_cost);

        address.setText("Address : "+String.valueOf(getIntent().getStringExtra("address")));
        price.setText("Price : "+String.valueOf(getIntent().getLongExtra("totalAmount", 0)));

        //calculation
        int costOfDriver = (int) (getIntent().getLongExtra("totalAmount", 0)) * 20/100;
        driverCost.setText("Price : "+ costOfDriver);
    }
}