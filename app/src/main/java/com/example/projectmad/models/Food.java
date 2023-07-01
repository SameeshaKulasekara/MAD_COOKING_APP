package com.example.projectmad.models;

public class Food {

    private String id;
    private String userId;
    private String foodName;
    private long totalPrice;
    private long quantity;

    public Food() {
    }

    public Food(String id, String userId, String foodName, long totalPrice, long quantity) {
        this.id = id;
        this.userId = userId;
        this.foodName = foodName;
        this.totalPrice = totalPrice;
        this.quantity = quantity;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getFoodName() {
        return foodName;
    }

    public void setFoodName(String foodName) {
        this.foodName = foodName;
    }

    public long getTotalPrice() {
        return totalPrice;
    }

    public void setTotalPrice(long totalPrice) {
        this.totalPrice = totalPrice;
    }

    public long getQuantity() {
        return quantity;
    }

    public void setQuantity(long quantity) {
        this.quantity = quantity;
    }
}
