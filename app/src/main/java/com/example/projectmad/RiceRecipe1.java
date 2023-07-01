package com.example.projectmad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class RiceRecipe1 extends AppCompatActivity {
    Button rice_btn;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice_recipe1);
        rice_btn = findViewById(R.id.btn_r04);
    }
}