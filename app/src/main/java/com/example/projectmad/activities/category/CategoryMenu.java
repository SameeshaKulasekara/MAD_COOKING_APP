package com.example.projectmad.activities.category;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectmad.R;
import com.example.projectmad.activities.HomePage;

public class CategoryMenu extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_menu);
    }

    public void juice(View view) {
        startActivity(new Intent(getApplicationContext(), MenuJuice.class));
    }

    public void dessert(View view) {
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
    }
}