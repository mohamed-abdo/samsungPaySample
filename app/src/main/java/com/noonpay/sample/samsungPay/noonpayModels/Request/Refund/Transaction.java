package com.noonpay.sample.samsungPay.noonpayModels.Request.Refund;

import android.os.Parcel;
import android.os.Parcelable;

public class Transaction implements Parcelable {
    private double amount;
    private String currency;
    private String targetTransactionId;

    public Transaction() {
    }

    protected Transaction(Parcel in) {
        amount = in.readDouble();
        currency = in.readString();
        targetTransactionId = in.readString();
    }

    public static final Creator<Transaction> CREATOR = new Creator<Transaction>() {
        @Override
        public Transaction createFromParcel(Parcel in) {
            return new Transaction(in);
        }

        @Override
        public Transaction[] newArray(int size) {
            return new Transaction[size];
        }
    };

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public String getTargetTransactionId() {
        return targetTransactionId;
    }

    public void setTargetTransactionId(String targetTransactionId) {
        this.targetTransactionId = targetTransactionId;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeDouble(amount);
        dest.writeString(currency);
        dest.writeString(targetTransactionId);
    }
}
