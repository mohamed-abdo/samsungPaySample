package com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

import com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.*;

public class ProcessAuthentication implements Parcelable {

    private String apiOperation;
    private Order order;
    private PaymentData paymentData;

    public ProcessAuthentication() {
    }

    protected ProcessAuthentication(Parcel in) {
        apiOperation = in.readString();
        order = in.readParcelable(Order.class.getClassLoader());
        paymentData = in.readParcelable(PaymentData.class.getClassLoader());
    }

    public static final Creator<ProcessAuthentication> CREATOR = new Creator<ProcessAuthentication>() {
        @Override
        public ProcessAuthentication createFromParcel(Parcel in) {
            return new ProcessAuthentication(in);
        }

        @Override
        public ProcessAuthentication[] newArray(int size) {
            return new ProcessAuthentication[size];
        }
    };

    public String getApiOperation() {
        return apiOperation;
    }

    public void setApiOperation(String apiOperation) {
        this.apiOperation = apiOperation;
    }

    public PaymentData getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(PaymentData paymentData) {
        this.paymentData = paymentData;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiOperation);
        dest.writeParcelable(order, flags);
        dest.writeParcelable(paymentData, flags);
    }
}