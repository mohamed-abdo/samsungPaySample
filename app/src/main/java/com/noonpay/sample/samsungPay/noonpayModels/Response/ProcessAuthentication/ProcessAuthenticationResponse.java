package com.noonpay.sample.samsungPay.noonpayModels.Response.ProcessAuthentication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdo on 3/6/2018.
 */

public class ProcessAuthenticationResponse implements Parcelable {

    private String orderId;
    private String merchant;
    private String status;

    public ProcessAuthenticationResponse(){

    }

    protected ProcessAuthenticationResponse(Parcel in) {
        orderId = in.readString();
        merchant = in.readString();
        status = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(orderId);
        dest.writeString(merchant);
        dest.writeString(status);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<ProcessAuthenticationResponse> CREATOR = new Creator<ProcessAuthenticationResponse>() {
        @Override
        public ProcessAuthenticationResponse createFromParcel(Parcel in) {
            return new ProcessAuthenticationResponse(in);
        }

        @Override
        public ProcessAuthenticationResponse[] newArray(int size) {
            return new ProcessAuthenticationResponse[size];
        }
    };

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getMerchant() {
        return merchant;
    }

    public void setMerchant(String merchant) {
        this.merchant = merchant;
    }


    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
