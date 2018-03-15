package com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder;

import android.os.Parcel;
import android.os.Parcelable;

public class InitiateOrder implements Parcelable {

    private String apiOperation;
    private Order order;
    public final static Parcelable.Creator<InitiateOrder> CREATOR = new Creator<InitiateOrder>() {


        @SuppressWarnings({
                "unchecked"
        })
        public InitiateOrder createFromParcel(Parcel in) {
            return new InitiateOrder(in);
        }

        public InitiateOrder[] newArray(int size) {
            return (new InitiateOrder[size]);
        }

    };

    protected InitiateOrder(Parcel in) {
        this.apiOperation = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((Order) in.readValue((Order.class.getClassLoader())));
    }

    public InitiateOrder() {
    }

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(apiOperation);
        dest.writeValue(order);
    }

    public int describeContents() {
        return 0;
    }

}