package com.noonpay.sample.samsungPay.noonpayModels.Response.Refund;

import android.os.Parcel;
import android.os.Parcelable;

public class RefundResponse implements Parcelable {
    private String orderId;
    private String transactionId;
    private String status;
    private String currency;
    private double totalRefundedAmount;
    private double totalSalesAmount;
    private String authorizationCode;
    public RefundResponse(){

    }

    protected RefundResponse(Parcel in) {
        orderId = in.readString();
        transactionId = in.readString();
        status = in.readString();
        currency = in.readString();
        totalRefundedAmount = in.readDouble();
        totalSalesAmount = in.readDouble();
        authorizationCode = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(transactionId);
        dest.writeString(status);
        dest.writeString(currency);
        dest.writeDouble(totalRefundedAmount);
        dest.writeDouble(totalSalesAmount);
        dest.writeString(authorizationCode);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<RefundResponse> CREATOR = new Creator<RefundResponse>() {
        @Override
        public RefundResponse createFromParcel(Parcel in) {
            return new RefundResponse(in);
        }

        @Override
        public RefundResponse[] newArray(int size) {
            return new RefundResponse[size];
        }
    };

    public Double getTotalRefundedAmount() {
        return totalRefundedAmount;
    }

    public void setTotalRefundedAmount(Double totalRefundedAmount) {
        this.totalRefundedAmount = totalRefundedAmount;
    }

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

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public double getTotalSalesAmount() {
        return totalSalesAmount;
    }

    public void setTotalSalesAmount(double totalSalesAmount) {
        this.totalSalesAmount = totalSalesAmount;
    }

    public String getAuthorizationCode() {
        return authorizationCode;
    }

    public void setAuthorizationCode(String authorizationCode) {
        this.authorizationCode = authorizationCode;
    }
}
