package com.noonpay.sample.samsungPay.noonpayModels.Request.Refund;

import android.os.Parcel;
import android.os.Parcelable;

public class Order implements Parcelable {
    private String id;
    public Order(){

    }
    protected Order(Parcel in) {
        id = in.readString();
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

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
    }
}
