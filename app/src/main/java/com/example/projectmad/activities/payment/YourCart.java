package com.example.projectmad.activities.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.projectmad.R;
import com.example.projectmad.activities.category.CategoryMenu;
import com.example.projectmad.adapters.CartAdapter;
import com.example.projectmad.models.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class YourCart extends AppCompatActivity {
    Button btn4;
    Button btn5;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    RecyclerView recyclerView;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_your_cart);

        recyclerView = findViewById(R.id.recycler_view);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false);
        recyclerView.setLayoutManager(linearLayoutManager);
    }

    @Override
    protected void onStart() {
        super.onStart();
        getAllItems(mAuth.getCurrentUser().getUid());
    }

    private void getAllItems(String currentUser) {
        db.collection("carts")
                .whereEqualTo("userId", currentUser)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {
                        List<Food> resCardList = new ArrayList<>();
                        for (QueryDocumentSnapshot food : value) {
                            Food foodObj = new Food(
                                    food.getString("id"),
                                    food.getString("userId"),
                                    food.getString("foodName"),
                                    food.getLong("totalPrice"),
                                    food.getLong("quantity")

                            );
                            resCardList.add(foodObj);
                        }
                        CartAdapter adapter = new CartAdapter(YourCart.this, resCardList);
                        recyclerView.setAdapter(adapter);
                    }
                });
    }


    public void goToPayment(View view) {
        startActivity(new Intent(getApplicationContext(), ViewYourOrder.class));
    }

    public void goToShopping(View view) {
        startActivity(new Intent(getApplicationContext(), CategoryMenu.class));
    }
}