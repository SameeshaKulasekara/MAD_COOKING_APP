package com.example.projectmad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectmad.activities.HomePage;

public class CategoryPage extends AppCompatActivity {

    Button carrotJuice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_category_page);

    }

    public void goToCarratJuce(View view) {
        startActivity(new Intent(getApplicationContext(), JuiceRecipe1.class));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
    }
}