package com.noonpay.sample.samsungPay.noonpayModels.Response.InitiateOrder;

import android.os.Parcel;
import android.os.Parcelable;

import com.noonpay.sample.samsungPay.noonpayModels.Response.GeneralResponse;

/**
 * Created by abdo on 3/4/2018.
 */

public class InitiateOrder extends GeneralResponse<InitiateOrderResponse> implements Parcelable {

    public InitiateOrder(){

    }
    protected InitiateOrder(Parcel in) {
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

    public static final Creator<InitiateOrder> CREATOR = new Creator<InitiateOrder>() {
        @Override
        public InitiateOrder createFromParcel(Parcel in) {
            return new InitiateOrder(in);
        }

        @Override
        public InitiateOrder[] newArray(int size) {
            return new InitiateOrder[size];
        }
    };
}
