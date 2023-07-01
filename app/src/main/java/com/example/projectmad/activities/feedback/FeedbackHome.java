package com.example.projectmad.activities.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectmad.R;
import com.example.projectmad.activities.HomePage;

public class FeedbackHome extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback);
    }


    public void goToMyRatings(View view) {
        startActivity(new Intent(FeedbackHome.this, AllFeedbacks.class));
    }

    public void goToAddFeedback(View view) {
        startActivity(new Intent(FeedbackHome.this, FeedbackRating.class));
    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
    }
}