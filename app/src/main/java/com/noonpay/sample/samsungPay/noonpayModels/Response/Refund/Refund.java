package com.noonpay.sample.samsungPay.noonpayModels.Response.Refund;


import android.os.Parcel;
import android.os.Parcelable;

import com.noonpay.sample.samsungPay.noonpayModels.Response.GeneralResponse;

public class Refund extends GeneralResponse<RefundResponse> implements Parcelable {
    public Refund(){

    }

    protected Refund(Parcel in) {
        super(in);
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        super.writeToParcel(dest, flags);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<Refund> CREATOR = new Creator<Refund>() {
        @Override
        public Refund createFromParcel(Parcel in) {
            return new Refund(in);
        }

        @Override
        public Refund[] newArray(int size) {
            return new Refund[size];
        }
    };
}
