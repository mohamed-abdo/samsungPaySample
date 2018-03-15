package com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo;

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class PaymentData implements Parcelable {

    private String method;
    private Data data;
    public final static Parcelable.Creator<PaymentData> CREATOR = new Creator<PaymentData>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PaymentData createFromParcel(Parcel in) {
            return new PaymentData(in);
        }

        public PaymentData[] newArray(int size) {
            return (new PaymentData[size]);
        }

    };

    protected PaymentData(Parcel in) {
        this.method = ((String) in.readValue((String.class.getClassLoader())));
        this.data = ((Data) in.readValue((Data.class.getClassLoader())));
    }

    public PaymentData() {
    }

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

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(method);
        dest.writeValue(data);
    }

    public int describeContents() {
        return 0;
    }

}