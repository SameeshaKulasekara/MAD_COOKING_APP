package com.example.projectmad.activities.feedback;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectmad.R;

public class FeedbackRating extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_rating);
    }

    public void goToSendFeedback(View view) {
        startActivity(new Intent(FeedbackRating.this, SendFeedback.class));
    }
}