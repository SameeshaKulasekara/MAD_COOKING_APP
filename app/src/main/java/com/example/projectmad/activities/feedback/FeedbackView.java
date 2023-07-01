package com.example.projectmad.activities.feedback;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.EditText;
import android.widget.RatingBar;
import android.widget.Toast;

import com.example.projectmad.activities.HomePage;
import com.example.projectmad.R;
import com.example.projectmad.models.Feedback;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

public class FeedbackView extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    EditText fullName;
    EditText email;
    EditText phone;
    RatingBar ratings;
    EditText description;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_feedback_view);

        fullName = (EditText) findViewById(R.id.full_name);
        email = (EditText) findViewById(R.id.email);
        phone = (EditText) findViewById(R.id.phone);
        ratings = findViewById(R.id.stars_bar);
        description = (EditText) findViewById(R.id.description);

        fullName.setText(getIntent().getStringExtra("fullName"));
        email.setText(getIntent().getStringExtra("email"));
        phone.setText(getIntent().getStringExtra("phone"));
        ratings.setRating(Float.parseFloat(getIntent().getStringExtra("ratings")));
        description.setText(getIntent().getStringExtra("description"));

    }


    public void feedbackDelete(View view) {

        //feedback delete
        DocumentReference documentReference = db.collection("feedbacks").document(getIntent().getStringExtra("id"));
        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
            @Override
            public void onSuccess(Void unused) {
                Toast.makeText(FeedbackView.this, "Room Reservation Deleted Successfully.",
                        Toast.LENGTH_SHORT).show();
                startActivity(new Intent(getApplicationContext(), FeedbackHome.class));
            }
        });
    }

    public void feedbackUpdate(View view) {

        //validation
        if (TextUtils.isEmpty(fullName.getText().toString())) {
            Toast.makeText(FeedbackView.this, "full name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(FeedbackView.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phone.getText().toString())) {
            Toast.makeText(FeedbackView.this, "Phone number cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            Feedback feedback = new Feedback();
            Date date = new Date();

            feedback.setId(getIntent().getStringExtra("id"));
            feedback.setUserId(mAuth.getCurrentUser().getUid());
            feedback.setFullName(fullName.getText().toString());
            feedback.setEmail(email.getText().toString());
            feedback.setPhone(phone.getText().toString());
            feedback.setRatings( ratings.getRating());
            feedback.setSendTime(date);
            feedback.setDescription(description.getText().toString());


            Map<String, Object> data = new HashMap<>();
            data.put("fullName", feedback.getFullName());
            data.put("email", feedback.getEmail());
            data.put("phone", feedback.getPhone());
            data.put("ratings", feedback.getRatings());
            data.put("sendTime", feedback.getSendTime());
            data.put("description", feedback.getDescription());
            DocumentReference documentReference = db.collection("feedbacks").document(feedback.getId());
            documentReference.update(data)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(FeedbackView.this, "Feedback saved Successfully!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(FeedbackView.this, AllFeedbacks.class));
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(FeedbackView.this, "Feedback Failed!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }

    }

    @Override
    public void onBackPressed() {
        Intent i = new Intent(getApplicationContext(), HomePage.class);
        startActivity(i);
    }
}