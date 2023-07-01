package com.example.projectmad.activities.feedback;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.widget.TextView;

import com.example.projectmad.R;
import com.example.projectmad.adapters.FeedbackAdapter;
import com.example.projectmad.models.Feedback;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class AllFeedbacks extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;
    TextView allRatingsInput, ratingsMeanInput;
    float ratingsCal;
    float ratingsMean;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_all_feedbacks);

        allRatingsInput = findViewById(R.id.all_ratings);
        ratingsMeanInput = findViewById(R.id.rating_mean);
        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllFeedbacks(mAuth.getCurrentUser().getUid());
        ratingsCal = 0;
        ratingsMean = 0;
    }

    private void getAllFeedbacks(String currentUser) {
        db.collection("feedbacks")
                .whereEqualTo("userId", currentUser)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Feedback> resCardList = new ArrayList<>();
                        for (QueryDocumentSnapshot feedback : value) {
                            Feedback eventObj = new Feedback(
                                    feedback.getString("id"),
                                    feedback.getString("userId"),
                                    feedback.getString("fullName"),
                                    feedback.getString("email"),
                                    feedback.getString("phone"),
                                    feedback.getDouble("ratings"),
                                    feedback.getDate("sendTime"),
                                    feedback.getString("description")
                            );
                            //calculation
                            ratingsCal += feedback.getDouble("ratings");
                            resCardList.add(eventObj);
                        }

                        allRatingsInput.setText(resCardList.size() + " Ratings");
                        //calculation
                        ratingsMean = ratingsCal/resCardList.size();

                        ratingsMeanInput.setText(String.valueOf(ratingsMean));
                        FeedbackAdapter adapter = new FeedbackAdapter(AllFeedbacks.this, resCardList, FeedbackView.class);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}