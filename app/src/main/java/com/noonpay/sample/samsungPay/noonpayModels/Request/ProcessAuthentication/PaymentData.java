package com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentData implements Parcelable {

    private String method;
    private Data data;

    public PaymentData() {
    }

    protected PaymentData(Parcel in) {
        method = in.readString();
        data = in.readParcelable(Data.class.getClassLoader());
    }

    public static final Creator<PaymentData> CREATOR = new Creator<PaymentData>() {
        @Override
        public PaymentData createFromParcel(Parcel in) {
            return new PaymentData(in);
        }

        @Override
        public PaymentData[] newArray(int size) {
            return new PaymentData[size];
        }
    };

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }

    public Data getData() {
        return data;
    }

    public void setData(Data data) {
        this.data = data;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(method);
        dest.writeParcelable(data, flags);
    }
}