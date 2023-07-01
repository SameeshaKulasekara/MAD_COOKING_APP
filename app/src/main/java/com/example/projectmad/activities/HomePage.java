package com.example.projectmad.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectmad.activities.category.CategoryMenu;
import com.example.projectmad.activities.user.LoginPage;
import com.example.projectmad.activities.payment.OrderHistory;
import com.example.projectmad.R;
import com.example.projectmad.activities.payment.YourCart;
import com.example.projectmad.activities.driver.DeliveryOrdersView;
import com.example.projectmad.activities.driver.DriverRegisterPage;
import com.example.projectmad.activities.feedback.FeedbackHome;
import com.example.projectmad.utils.UserUtils;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

public class HomePage extends AppCompatActivity {
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    UserUtils userUtils = new UserUtils();
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_home_page);
    }

    @Override
    protected void onStart() {
        super.onStart();
        userUtils.getAuthUser();
    }
    @Override
    public void onBackPressed() {
        Intent startMain = new Intent(Intent.ACTION_MAIN);
        startMain.addCategory(Intent.CATEGORY_HOME);
        startMain.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(startMain);
    }

    public void profileIconButton(View view) {
        mAuth.signOut();
        startActivity(new Intent(getApplicationContext(), LoginPage.class));
    }

    public void goToCategory(View view) {
        startActivity(new Intent(getApplicationContext(), CategoryMenu.class));
    }

    public void goToCart(View view) {
        startActivity(new Intent(getApplicationContext(), YourCart.class));
    }

    public void goToOrders(View view) {
        startActivity(new Intent(getApplicationContext(), OrderHistory.class));
    }

    public void goToDriver(View view) {

        //user ID
        String userId = mAuth.getCurrentUser().getUid();
        DocumentReference documentReference = db.collection("users").document(userId);
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                if (documentSnapshot.getBoolean("isDriver")){
                    startActivity(new Intent(getApplicationContext(), DeliveryOrdersView.class));
                }else {
                    startActivity(new Intent(getApplicationContext(), DriverRegisterPage.class));
                }
            }
        });


    }


    public void goToFeedback(View view) {
        startActivity(new Intent(getApplicationContext(), FeedbackHome.class));
    }


}