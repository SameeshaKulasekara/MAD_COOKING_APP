package com.example.projectmad.activities.driver;

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
import com.example.projectmad.models.Driver;
import com.example.projectmad.models.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class DriverRegisterPage extends AppCompatActivity {
    FirebaseFirestore db = FirebaseFirestore.getInstance();
    FirebaseAuth mAuth = FirebaseAuth.getInstance();

    EditText firstName, lastName, email, nic, address, phoneNumber, licenseNumber;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_driver_register_page);

        firstName = findViewById(R.id.first_name);
        lastName = findViewById(R.id.last_name);
        email = findViewById(R.id.email);
        nic = findViewById(R.id.nic);
        address = findViewById(R.id.address);
        phoneNumber = findViewById(R.id.phone_number);
        licenseNumber = findViewById(R.id.license_number);

        Button registerDriverButton = (Button) findViewById(R.id.create_account);
        registerDriverButton.setOnClickListener(new View.OnClickListener(){
            @Override
            public void onClick(View v) {
                registerAsDriver();
            }
        });


    }

    private void registerAsDriver() {

        //validation check
        if (TextUtils.isEmpty(firstName.getText().toString())) {
            Toast.makeText(DriverRegisterPage.this, "First name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(lastName.getText().toString())) {
            Toast.makeText(DriverRegisterPage.this, "Last name cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(email.getText().toString())) {
            Toast.makeText(DriverRegisterPage.this, "Email cannot be empty", Toast.LENGTH_SHORT).show();
        }  else if (TextUtils.isEmpty(nic.getText().toString())) {
            Toast.makeText(DriverRegisterPage.this, "NIC cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(address.getText().toString())) {
            Toast.makeText(DriverRegisterPage.this, "Address cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(phoneNumber.getText().toString())) {
            Toast.makeText(DriverRegisterPage.this, "Phone Number cannot be empty", Toast.LENGTH_SHORT).show();
        } else if (TextUtils.isEmpty(licenseNumber.getText().toString())) {
            Toast.makeText(DriverRegisterPage.this, "Driver's license cannot be empty", Toast.LENGTH_SHORT).show();
        } else {

            //driver create
            createDriver();
        }
    }

    private void createDriver() {

        User user = new User();
        user.setDriver(true);

        Map<String, Object> data = new HashMap<>();
        data.put("isDriver", user.isDriver());

        //user update (isDriver = true)
        DocumentReference documentReference1 = db.collection("users").document(mAuth.getCurrentUser().getUid());
        documentReference1.update(data);


        //create driver object
        Driver driver = new Driver();

        //assigns value to driver object
        driver.setId(UUID.randomUUID().toString());
        driver.setUserId(mAuth.getCurrentUser().getUid());
        driver.setFirstName(firstName.getText().toString());
        driver.setLastName(lastName.getText().toString());
        driver.setEmail(email.getText().toString());
        driver.setNic(nic.getText().toString());
        driver.setAddress(address.getText().toString());
        driver.setPhoneNumber(phoneNumber.getText().toString());
        driver.setLicenseNumber(licenseNumber.getText().toString());

        DocumentReference documentReference = db.collection("drivers").document(driver.getId());
        documentReference.set(driver)
                .addOnSuccessListener(new OnSuccessListener<Void>() {
                    @Override
                    public void onSuccess(Void unused) {
                        Toast.makeText(DriverRegisterPage.this, "Driver Registration Successfully!",
                                Toast.LENGTH_SHORT).show();
                        startActivity(new Intent(getApplicationContext(), DeliveryOrdersView.class));
                    }
                })

                .addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        Toast.makeText(DriverRegisterPage.this, "Driver Registration Failed!",
                                Toast.LENGTH_SHORT).show();
                    }
                });


    }
}