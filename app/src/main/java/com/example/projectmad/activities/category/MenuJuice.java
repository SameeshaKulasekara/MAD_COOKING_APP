package com.example.projectmad.activities.category;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.Toast;

import com.example.projectmad.JuiceRecipe1;
import com.example.projectmad.JuiceRecipe2;
import com.example.projectmad.R;
import com.example.projectmad.models.Food;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.material.textfield.TextInputEditText;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.UUID;

public class MenuJuice extends AppCompatActivity {

    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    LinearLayout food1;
    LinearLayout food2;
    String foodName;
    int foodPrice;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_menu_juice);

        food1 = (LinearLayout) findViewById(R.id.food_1);
        food2 = (LinearLayout) findViewById(R.id.food_2);
    }

    public void selectCarrotJuice(View view) {
        food1.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        food2.setBackground(getResources().getDrawable(R.drawable.background_radius));
        foodName = "Carrot Juice";
        foodPrice = 500;
    }

    public void selectAppleJuice(View view) {
        food1.setBackground(getResources().getDrawable(R.drawable.background_radius));
        food2.setBackground(getResources().getDrawable(R.drawable.background_color_radius));
        foodName = "Apple Juice";
        foodPrice = 350;
    }

    public void addToCart(View view) {

        TextInputEditText quantity = (TextInputEditText) findViewById(R.id.quantity);

        //validation
        if (TextUtils.isEmpty(foodName)) {
            Toast.makeText(MenuJuice.this, "Food name cannot be empty!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(quantity.getText().toString())) {
            Toast.makeText(MenuJuice.this, "Quantity cannot be empty!", Toast.LENGTH_SHORT).show();
        } else{


            //calculation
            int totalPrice = foodPrice * Integer.parseInt(quantity.getText().toString());


            //create food object
            Food food = new Food();

            //assigns value to food object
            food.setId(UUID.randomUUID().toString());
            food.setUserId(mAuth.getCurrentUser().getUid());
            food.setFoodName(foodName);
            food.setTotalPrice(totalPrice);
            food.setQuantity(Integer.parseInt(quantity.getText().toString()));

            //database and document create
            DocumentReference documentReference = db.collection("carts").document(food.getId());
            documentReference.set(food)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MenuJuice.this, "Foods add to cart Successfully!",
                                    Toast.LENGTH_SHORT).show();
                            startActivity(new Intent(MenuJuice.this, CategoryMenu.class));
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MenuJuice.this, "Foods add to cart Failed!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });

        }
    }

    public void carrotJuiceInfo(View view) {
        startActivity(new Intent(MenuJuice.this, JuiceRecipe1.class));
    }

    public void appleJuiceInfo(View view) {
        startActivity(new Intent(MenuJuice.this, JuiceRecipe2.class));
    }
}