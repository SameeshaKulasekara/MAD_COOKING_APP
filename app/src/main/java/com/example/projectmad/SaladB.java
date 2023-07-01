package com.example.projectmad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class SaladB extends AppCompatActivity {

    Button btnback1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_salad_b);
        btnback1 = findViewById(R.id.btn_back2);
    }
}