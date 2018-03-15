package com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {

    private String amount;
    private String name;
    private String currency;
    private String category;
    private String channel;
    private String reference;

    public Order() {
    }

    protected Order(Parcel in) {
        amount = in.readString();
        name = in.readString();
        currency = in.readString();
        category = in.readString();
        channel = in.readString();
        reference = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(amount);
        dest.writeString(name);
        dest.writeString(currency);
        dest.writeString(category);
        dest.writeString(channel);
        dest.writeString(reference);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Order> CREATOR = new Creator<Order>() {
        @Override
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        @Override
        public Order[] newArray(int size) {
            return new Order[size];
        }
    };

    public String getAmount() {
        return amount;
    }

    public void setAmount(String amount) {
        this.amount = amount;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getChannel() {
        return channel;
    }

    public void setChannel(String channel) {
        this.channel = channel;
    }

    public String getReference() {
        return reference;
    }

    public void setReference(String reference) {
        this.reference = reference;
    }
}