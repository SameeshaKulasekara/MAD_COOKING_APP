package com.example.projectmad.activities.payment;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.example.projectmad.R;
import com.example.projectmad.models.Food;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.ArrayList;
import java.util.List;

public class ViewYourOrder extends AppCompatActivity {

    TextView dishName;
    TextView quantity;
    TextView price;
    TextView totalAmount;

    String column1 = "";
    String column2 = "";
    String column3 = "";
    long totalPrice;

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    List<Food> list = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_your_order);

        dishName = findViewById(R.id.dish_name);
        quantity = findViewById(R.id.quantity);
        price = findViewById(R.id.price);
        totalAmount = findViewById(R.id.total_amount);

    }

    @Override
    protected void onStart() {
        super.onStart();
        column1 = "";
        column2 = "";
        column3 = "";
        totalPrice = 0;
        getAllItems(mAuth.getCurrentUser().getUid());
    }


    private void getAllItems(String currentUser) {
        db.collection("carts")
                .whereEqualTo("userId", currentUser)
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot food : value) {
                            Food foodObj = new Food(
                                    food.getString("id"),
                                    food.getString("userId"),
                                    food.getString("foodName"),
                                    food.getLong("totalPrice"),
                                    food.getLong("quantity")

                            );
                            column1 += foodObj.getFoodName() + "\n";
                            column2 += foodObj.getQuantity() + "\n";
                            column3 += foodObj.getTotalPrice() + "\n";

                            //calculation
                            totalPrice += foodObj.getTotalPrice();
                            dishName.setText(column1);
                            quantity.setText(column2);
                            price.setText(column3);
                            totalAmount.setText(totalPrice + " LKR");
                            list.add(foodObj);
                        }
                    }

                });


    }

    public void goToPayment(View view) {
        Intent intent = new Intent(ViewYourOrder.this, MakePayment.class);
        intent.putExtra("totalAmount", totalPrice);
        startActivity(intent);

    }
}