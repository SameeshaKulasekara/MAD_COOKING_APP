package com.example.projectmad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

public class DeleteOrder extends AppCompatActivity {
    ImageView ev;
    TextView tv01;
    TextView tv02;
    TextView tv03;
    Button btn01;
    Button btn02;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delete_order);
        ev = findViewById(R.id.imageView_od);
        tv01 = findViewById(R.id.tv_od02);
        tv02 = findViewById(R.id.tv_od03);
        tv03 = findViewById(R.id.tv_od05);
        btn01 = findViewById(R.id.btn_od01);
        btn02 = findViewById(R.id.btn_od02);
    }
}