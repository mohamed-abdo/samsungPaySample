package com.noonpay.sample.samsungPay.noonpayModels.Response.InitiateOrder;

import android.os.Parcel;
import android.os.Parcelable;

import java.util.List;

/**
 * Created by abdo on 3/4/2018.
 */

public class InitiateOrderResponse implements Parcelable {
    private String OrderId;
    private List<String> PaymentMethods;

    public InitiateOrderResponse() {

    }

    protected InitiateOrderResponse(Parcel in) {
        OrderId = in.readString();
        PaymentMethods = in.createStringArrayList();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(OrderId);
        dest.writeStringList(PaymentMethods);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<InitiateOrderResponse> CREATOR = new Creator<InitiateOrderResponse>() {
        @Override
        public InitiateOrderResponse createFromParcel(Parcel in) {
            return new InitiateOrderResponse(in);
        }

        @Override
        public InitiateOrderResponse[] newArray(int size) {
            return new InitiateOrderResponse[size];
        }
    };

    public String getOrderId() {
        return OrderId;
    }

    public void setOrderId(String orderId) {
        OrderId = orderId;
    }

    public List<String> getPaymentMethods() {
        return PaymentMethods;
    }

    public void setPaymentMethods(List<String> paymentMethods) {
        PaymentMethods = paymentMethods;
    }
}
