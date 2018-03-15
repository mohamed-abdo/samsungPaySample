package com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo;

/**
 * Created by abdo on 3/1/2018.
 */

import android.os.Parcel;
import android.os.Parcelable;
import android.os.Parcelable.Creator;

public class Data implements Parcelable {

    private String returnUrl;
    public final static Parcelable.Creator<Data> CREATOR = new Creator<Data>() {


        @SuppressWarnings({
                "unchecked"
        })
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        public Data[] newArray(int size) {
            return (new Data[size]);
        }

    };

    protected Data(Parcel in) {
        this.returnUrl = ((String) in.readValue((String.class.getClassLoader())));
    }

    public Data() {
    }

    public String getReturnUrl() {
        return returnUrl;
    }

    public void setReturnUrl(String returnUrl) {
        this.returnUrl = returnUrl;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(returnUrl);
    }

    public int describeContents() {
        return 0;
    }

}