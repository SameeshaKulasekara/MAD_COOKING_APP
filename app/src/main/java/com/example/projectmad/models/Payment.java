package com.example.projectmad.models;

import java.util.Date;

public class Payment {

    private String id;
    private String userId;
    private String paymentType;
    private String address;
    private String phone;
    private String cardNumber;
    private Date paymentTime;
    private long totalAmount;

    public Payment() {
    }

    public Payment(String id, String userId, String paymentType, String address, String phone, String cardNumber, Date paymentTime, long totalAmount) {
        this.id = id;
        this.userId = userId;
        this.paymentType = paymentType;
        this.address = address;
        this.phone = phone;
        this.cardNumber = cardNumber;
        this.paymentTime = paymentTime;
        this.totalAmount = totalAmount;
    }

    public String getPaymentType() {
        return paymentType;
    }

    public void setPaymentType(String paymentType) {
        this.paymentType = paymentType;
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

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getCardNumber() {
        return cardNumber;
    }

    public void setCardNumber(String cardNumber) {
        this.cardNumber = cardNumber;
    }

    public Date getPaymentTime() {
        return paymentTime;
    }

    public void setPaymentTime(Date paymentTime) {
        this.paymentTime = paymentTime;
    }

    public long getTotalAmount() {
        return totalAmount;
    }

    public void setTotalAmount(long totalAmount) {
        this.totalAmount = totalAmount;
    }
}
