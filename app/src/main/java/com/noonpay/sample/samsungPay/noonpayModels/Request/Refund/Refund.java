package com.noonpay.sample.samsungPay.noonpayModels.Request.Refund;

import android.os.Parcel;
import android.os.Parcelable;

import java.sql.Ref;

public class Refund implements Parcelable {
    private String apiOperation;
    private Order order;
    private Transaction transaction;
    public Refund(){

    }

    protected Refund(Parcel in) {
        apiOperation = in.readString();
        order = in.readParcelable(Order.class.getClassLoader());
        transaction = in.readParcelable(Transaction.class.getClassLoader());
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiOperation);
        dest.writeParcelable(order, flags);
        dest.writeParcelable(transaction, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Refund> CREATOR = new Creator<Refund>() {
        @Override
        public Refund createFromParcel(Parcel in) {
            return new Refund(in);
        }

        @Override
        public Refund[] newArray(int size) {
            return new Refund[size];
        }
    };

    public String getApiOperation() {
        return apiOperation;
    }

    public void setApiOperation(String apiOperation) {
        this.apiOperation = apiOperation;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    public Transaction getTransaction() {
        return transaction;
    }

    public void setTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
