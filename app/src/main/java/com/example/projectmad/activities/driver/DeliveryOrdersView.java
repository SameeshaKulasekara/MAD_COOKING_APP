package com.example.projectmad.activities.driver;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.projectmad.R;
import com.example.projectmad.adapters.DeliveryAdapter;
import com.example.projectmad.models.Payment;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class DeliveryOrdersView extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_delevery_orders_view);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllDelivery(mAuth.getCurrentUser().getUid());
    }

    private void getAllDelivery(String currentUser) {

        db.collection("payments").whereEqualTo("paymentType", "CARD")
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
                        DeliveryAdapter adapter = new DeliveryAdapter(DeliveryOrdersView.this, resCardList, DeliveryDetails.class);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }
}