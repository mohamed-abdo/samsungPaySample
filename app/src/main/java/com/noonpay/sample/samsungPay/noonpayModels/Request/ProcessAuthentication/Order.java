package com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdo on 3/6/2018.
 */

public class Order implements Parcelable {

    private String id;
    public final static Parcelable.Creator<Order> CREATOR = new Creator<Order>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Order createFromParcel(Parcel in) {
            return new Order(in);
        }

        public Order[] newArray(int size) {
            return (new Order[size]);
        }

    };

    protected Order(Parcel in) {
        this.id = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Order() {
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(id);
    }

    public int describeContents() {
        return 0;
    }

}