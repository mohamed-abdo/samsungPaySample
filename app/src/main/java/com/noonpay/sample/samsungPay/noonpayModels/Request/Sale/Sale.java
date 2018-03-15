package com.noonpay.sample.samsungPay.noonpayModels.Request.Sale;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdo on 3/6/2018.
 */

public class Sale implements Parcelable {
    private String apiOperation;
    private Order order;

    public Sale() {

    }

    protected Sale(Parcel in) {
        apiOperation = in.readString();
        order = in.readParcelable(Order.class.getClassLoader());
    }

    public static final Creator<Sale> CREATOR = new Creator<Sale>() {
        @Override
        public Sale createFromParcel(Parcel in) {
            return new Sale(in);
        }

        @Override
        public Sale[] newArray(int size) {
            return new Sale[size];
        }
    };

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(apiOperation);
        dest.writeParcelable(order, flags);
    }

    public String getApiOperation() {
        return apiOperation;
    }

    public void setApiOperation(String apiOperation) {
        this.apiOperation = apiOperation;
    }

    public Order getOrder() {
        return order;
    }

    public void setOrder(Order order) {
        this.order = order;
    }
}
