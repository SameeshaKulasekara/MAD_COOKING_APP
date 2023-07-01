package com.example.projectmad.adapters;

import android.annotation.SuppressLint;
import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmad.R;
import com.example.projectmad.models.Payment;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.List;

public class OrderAdapter extends RecyclerView.Adapter<OrderAdapter.ViewHolder> {

    private Context context;
    private List<?> resCardList;
    private Class<?> activity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public OrderAdapter(Context context, List<?> resCardList) {
        this.context = context;
        this.resCardList = resCardList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.order_history, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Payment payment = (Payment) resCardList.get(position);

        DocumentReference documentReference = db.collection("payments").document(payment.getId());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Payment data = new Payment(
                        documentSnapshot.getString("id"),
                        documentSnapshot.getString("userId"),
                        documentSnapshot.getString("paymentType"),
                        documentSnapshot.getString("address"),
                        documentSnapshot.getString("phone"),
                        documentSnapshot.getString("cardNumber"),
                        documentSnapshot.getDate("paymentTime"),
                        documentSnapshot.getLong("totalAmount")
                );
                @SuppressLint("SimpleDateFormat") DateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy HH:mm");
                holder.fullName.setText("Customer Address : \n\t"+data.getAddress());
                holder.ratings.setText(" ");
                holder.date.setText("Order Time : \n\t"+dateFormat.format(data.getPaymentTime()));
                holder.message.setText("Total Cost : \n\t"+data.getTotalAmount());

            }
        });
    }

    @Override
    public int getItemCount() {
        return resCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView fullName, ratings, date, message;


        public ViewHolder(View itemView) {
            super(itemView);
            fullName = itemView.findViewById(R.id.full_name);
            ratings = itemView.findViewById(R.id.ratings);
            date = itemView.findViewById(R.id.date);
            message = itemView.findViewById(R.id.message);

        }
    }
}

