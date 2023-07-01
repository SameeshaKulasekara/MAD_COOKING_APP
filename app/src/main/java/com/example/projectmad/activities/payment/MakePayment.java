package com.example.projectmad.activities.payment;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.Toast;

import com.example.projectmad.R;
import com.example.projectmad.activities.HomePage;
import com.example.projectmad.models.Payment;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.EventListener;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.FirebaseFirestoreException;
import com.google.firebase.firestore.QueryDocumentSnapshot;
import com.google.firebase.firestore.QuerySnapshot;

import java.util.Date;
import java.util.UUID;

public class MakePayment extends AppCompatActivity {
    EditText et1;
    EditText et2;
    EditText et3;
    EditText et4;
    EditText et5;
    Button bttn1;
    Button bttn2;
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_make_payment);
        et1 = findViewById(R.id.address);
        et2 = findViewById(R.id.cardNo);
        et3 = findViewById(R.id.phone);
        et4 = findViewById(R.id.expDate);
        et5= findViewById(R.id.cvc);

        bttn1 = findViewById(R.id.payNow);
        bttn2 = findViewById(R.id.cancel1);


    }

    public void completePaymenet(View view) {

        if (TextUtils.isEmpty(et1.getText().toString())) {
            Toast.makeText(MakePayment.this, "Address cannot be empty!", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(et2.getText().toString())){
            Toast.makeText(MakePayment.this, "Card Number cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(et3.getText().toString())){
            Toast.makeText(MakePayment.this, "Phone cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(et4.getText().toString())){
            Toast.makeText(MakePayment.this, "Expire date cannot be empty!", Toast.LENGTH_SHORT).show();
        }else if (TextUtils.isEmpty(et5.getText().toString())){
            Toast.makeText(MakePayment.this, "CVV cannot be empty!", Toast.LENGTH_SHORT).show();
        } else {

            //create object payment
            Payment payment = new Payment();
            Date date = new Date();

            //assign data to payment object
            payment.setId(UUID.randomUUID().toString());
            payment.setUserId(mAuth.getCurrentUser().getUid());
            payment.setPaymentType("CARD");
            payment.setAddress(et1.getText().toString());
            payment.setCardNumber(et2.getText().toString());
            payment.setPhone(et3.getText().toString());
            payment.setPaymentTime(date);
            payment.setTotalAmount(getIntent().getLongExtra("totalAmount", 0));

            //payment create
            DocumentReference documentReference = db.collection("payments").document(payment.getId());
            documentReference.set(payment)
                    .addOnSuccessListener(new OnSuccessListener<Void>() {
                        @Override
                        public void onSuccess(Void unused) {
                            Toast.makeText(MakePayment.this, "Payment Successfully!",
                                    Toast.LENGTH_SHORT).show();
                            cartClear();
                            startActivity(new Intent(getApplicationContext(), HomePage.class));
                        }
                    })

                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            Toast.makeText(MakePayment.this, "Payment Failed!!",
                                    Toast.LENGTH_SHORT).show();
                        }
                    });
        }

    }

    private void cartClear() {


        db.collection("carts")
                .whereEqualTo("userId", mAuth.getCurrentUser().getUid())
                .addSnapshotListener(new EventListener<QuerySnapshot>() {
                    @Override
                    public void onEvent(@Nullable QuerySnapshot value, @Nullable FirebaseFirestoreException error) {

                        for (QueryDocumentSnapshot food : value) {
                            DocumentReference documentReference = db.collection("carts").document(food.getString("id"));
                            documentReference.delete();
                        }
                    }

                });
    }
}