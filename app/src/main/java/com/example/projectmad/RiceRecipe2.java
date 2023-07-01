package com.example.projectmad;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.widget.Button;

public class RiceRecipe2 extends AppCompatActivity {

    Button rice_btn2;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_rice_recipe2);
        rice_btn2 = findViewById(R.id.btn_r04);
    }
}