package com.noonpay.sample.samsungPay.noonpayModels.Response.Sale;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdo on 3/6/2018.
 */

public class SaleResponse implements Parcelable{
    private String orderId;
    private String transactionId;
    private String status;
    private String authorizationCode;
    private Double capturedAmount;

    public  SaleResponse(){

    }

    protected SaleResponse(Parcel in) {
        orderId = in.readString();
        transactionId = in.readString();
        status = in.readString();
        authorizationCode = in.readString();
        if (in.readByte() == 0) {
            capturedAmount = null;
        } else {
            capturedAmount = in.readDouble();
        }
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(transactionId);
        dest.writeString(status);
        dest.writeString(authorizationCode);
        if (capturedAmount == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeDouble(capturedAmount);
        }
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<SaleResponse> CREATOR = new Creator<SaleResponse>() {
        @Override
        public SaleResponse createFromParcel(Parcel in) {
            return new SaleResponse(in);
        }

        @Override
        public SaleResponse[] newArray(int size) {
            return new SaleResponse[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getTransactionId() {
        return transactionId;
    }

    public void setTransactionId(String transactionId) {
        this.transactionId = transactionId;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }

    public Double getCapturedAmount() {
        return capturedAmount;
    }

    public void setCapturedAmount(Double capturedAmount) {
        this.capturedAmount = capturedAmount;
    }
}
