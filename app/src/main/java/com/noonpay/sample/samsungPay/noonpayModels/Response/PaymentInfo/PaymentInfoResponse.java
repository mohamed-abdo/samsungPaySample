package com.noonpay.sample.samsungPay.noonpayModels.Response.PaymentInfo;


import android.os.Parcel;
import android.os.Parcelable;

import com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.SaleResponse;

import java.util.stream.Stream;

/**
 * Created by abdo on 3/4/2018.
 */

public class PaymentInfoResponse implements Parcelable{
    private String method;
    private String PaymentInfoId;
    private String href;
    private String serviceId;
    private String mod;
    private String exp;
    private String keyId;
    private String orderId;
    public PaymentInfoResponse() {

    }

    protected PaymentInfoResponse(Parcel in) {
        method = in.readString();
        PaymentInfoId = in.readString();
        href = in.readString();
        serviceId = in.readString();
        mod = in.readString();
        exp = in.readString();
        keyId = in.readString();
        orderId = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(method);
        dest.writeString(PaymentInfoId);
        dest.writeString(href);
        dest.writeString(serviceId);
        dest.writeString(mod);
        dest.writeString(exp);
        dest.writeString(keyId);
        dest.writeString(orderId);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<PaymentInfoResponse> CREATOR = new Creator<PaymentInfoResponse>() {
        @Override
        public PaymentInfoResponse createFromParcel(Parcel in) {
            return new PaymentInfoResponse(in);
        }

        @Override
        public PaymentInfoResponse[] newArray(int size) {
            return new PaymentInfoResponse[size];
        }
    };

    public String getPaymentInfoId() {
        return PaymentInfoId;
    }

    public void setPaymentInfoId(String paymentInfoId) {
        PaymentInfoId = paymentInfoId;
    }

    public String getHref() {
        return href;
    }

    public void setHref(String href) {
        this.href = href;
    }

    public String getServiceId() {
        return serviceId;
    }

    public void setServiceId(String serviceId) {
        this.serviceId = serviceId;
    }

    public String getMod() {
        return mod;
    }

    public void setMod(String mod) {
        this.mod = mod;
    }

    public String getExp() {
        return exp;
    }

    public void setExp(String exp) {
        this.exp = exp;
    }

    public String getKeyId() {
        return keyId;
    }

    public void setKeyId(String keyId) {
        this.keyId = keyId;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
