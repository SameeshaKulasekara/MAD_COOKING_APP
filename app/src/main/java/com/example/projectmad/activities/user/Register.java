package com.example.projectmad.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.projectmad.R;
import com.example.projectmad.activities.HomePage;
import com.example.projectmad.models.User;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.FirebaseFirestore;

public class Register extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_register);

        Button register = findViewById(R.id.button);
        register.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                EditText userName = (EditText) findViewById(R.id.editTextTextPersonName);
                EditText userEmail = (EditText) findViewById(R.id.editTextTextPersonName3);
                EditText userPassword = (EditText) findViewById(R.id.editTextTextPassword);

                if (TextUtils.isEmpty(userName.getText().toString())) {
                    Toast.makeText(Register.this, "Name cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userEmail.getText().toString())) {
                    Toast.makeText(Register.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
                } else if (TextUtils.isEmpty(userPassword.getText().toString())) {
                    Toast.makeText(Register.this, "Password cannot be empty", Toast.LENGTH_SHORT).show();
                } else {
                    createUser(userEmail.getText().toString(), userPassword.getText().toString());
                }
            }
        });
    }

    private void createUser(String email, String password) {

        FirebaseAuth mAuth;
        mAuth = FirebaseAuth.getInstance();

        mAuth.createUserWithEmailAndPassword(email, password)
                .addOnCompleteListener(this, new OnCompleteListener<AuthResult>() {
                    @Override
                    public void onComplete(@NonNull Task<AuthResult> task) {
                        if (task.isSuccessful()) {
                            // Sign in success, update UI with the signed-in user's information
                            Toast.makeText(Register.this, "Authentication Success.",
                                    Toast.LENGTH_SHORT).show();
                            saveUserDetails(email, mAuth.getCurrentUser().getUid());
                        } else {
                            // If sign in fails, display a message to the user.
                            Toast.makeText(Register.this, "Authentication failed.",
                                    Toast.LENGTH_SHORT).show();
                        }
                    }
                });
    }


    private void saveUserDetails(String email, String userId) {
        EditText firstNameEditText = (EditText) findViewById(R.id.editTextTextPersonName);

        User user = new User();
        user.setId(userId);
        user.setName(firstNameEditText.getText().toString());
        user.setEmail(email.trim());
        user.setDriver(false);

        DocumentReference documentReference = db.collection("users").document(userId);

        documentReference.set(user)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        startActivity(new Intent(getApplicationContext(), HomePage.class));
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(Register.this, "Firestore failed.",
                                Toast.LENGTH_SHORT).show();
                    }
                });
    }

    public void signIn(View view) {
        Intent i = new Intent(getApplicationContext(), LoginPage.class);
        startActivity(i);
    }
}