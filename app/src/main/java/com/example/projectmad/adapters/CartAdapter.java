package com.example.projectmad.adapters;

import android.content.Context;
import android.content.Intent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.projectmad.R;
import com.example.projectmad.activities.payment.YourCart;
import com.example.projectmad.models.Food;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.firestore.DocumentReference;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import java.util.List;

public class CartAdapter extends RecyclerView.Adapter<CartAdapter.ViewHolder> {

    private Context context;
    private List<?> resCardList;
    private Class<?> activity;
    FirebaseFirestore db = FirebaseFirestore.getInstance();


    public CartAdapter(Context context, List<?> resCardList) {
        this.context = context;
        this.resCardList = resCardList;
        this.activity = activity;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.yourcart_card, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Food food = (Food) resCardList.get(position);

        DocumentReference documentReference = db.collection("carts").document(food.getId());
        documentReference.get().addOnSuccessListener(new OnSuccessListener<DocumentSnapshot>() {
            @Override
            public void onSuccess(DocumentSnapshot documentSnapshot) {
                Food data = new Food(
                        documentSnapshot.getString("id"),
                        documentSnapshot.getString("userId"),
                        documentSnapshot.getString("foodName"),
                        documentSnapshot.getLong("totalPrice"),
                        documentSnapshot.getLong("quantity")
                );


                switch (data.getFoodName()){
                    case "Carrot Juice":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.juice1));
                        break;
                    case "Apple Juice":
                        holder.image.setImageDrawable(context.getResources().getDrawable(R.drawable.juice2));
                        break;
                }
                holder.foodName.setText(data.getFoodName());
                holder.foodPrice.setText(String.valueOf(data.getTotalPrice() + " LKR"));
                holder.quantity.setText(String.valueOf("Quantity "+data.getQuantity()));

                holder.delete.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(context, YourCart.class);
                        DocumentReference documentReference = db.collection("carts").document(data.getId());
                        documentReference.delete().addOnSuccessListener(new OnSuccessListener<Void>() {
                            @Override
                            public void onSuccess(Void unused) {
                                Toast.makeText(context, "Item Deleted Successfully.",
                                        Toast.LENGTH_SHORT).show();
                            }
                        });
                    }
                });
            }
        });
    }

    @Override
    public int getItemCount() {
        return resCardList.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        TextView foodName, foodPrice, quantity;
        Button delete;
        ImageView image;


        public ViewHolder(View itemView) {
            super(itemView);
            foodName = itemView.findViewById(R.id.food_name);
            foodPrice = itemView.findViewById(R.id.price);
            quantity = itemView.findViewById(R.id.quantity);
            delete = itemView.findViewById(R.id.delete);
            image = itemView.findViewById(R.id.type_image);

        }
    }
}
