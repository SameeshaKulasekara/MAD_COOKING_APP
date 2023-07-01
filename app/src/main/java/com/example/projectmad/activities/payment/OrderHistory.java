package com.example.projectmad.activities.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import com.example.projectmad.DeleteOrder;
import com.example.projectmad.R;
import com.example.projectmad.adapters.OrderAdapter;
import com.example.projectmad.models.Payment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class OrderHistory extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_history);


        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    public void viewOrder(View view) {
        startActivity(new Intent(getApplicationContext(), DeleteOrder.class));
    }

    @Override
    protected void onStart() {
        super.onStart();
        getOrderHistory(mAuth.getCurrentUser().getUid());
    }

    private void getOrderHistory(String currentUser) {

        db.collection("payments").whereEqualTo("userId", currentUser)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Payment> resCardList = new ArrayList<>();
                        for (QueryDocumentSnapshot payment : value) {
                            Payment paymentObj = new Payment(
                                    payment.getString("id"),
                                    payment.getString("userId"),
                                    payment.getString("paymentType"),
                                    payment.getString("address"),
                                    payment.getString("phone"),
                                    payment.getString("cardNumber"),
                                    payment.getDate("paymentTime"),
                                    payment.getLong("totalAmount")

                            );
                            resCardList.add(paymentObj);
                        }
                        OrderAdapter adapter = new OrderAdapter(OrderHistory.this, resCardList);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}