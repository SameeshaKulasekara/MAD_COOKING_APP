package com.example.projectmad;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

public class JuiceRecipe1 extends AppCompatActivity {
    Button btn1;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_juice_recipe1);
        btn1 = findViewById(R.id.btn_r04);
    }

    public void goToBack(View view) {
        startActivity(new Intent(getApplicationContext(), CategoryPage.class));
    }
}