package com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by abdo on 3/14/2018.
 */

public class Data implements Parcelable{
    private String method;
    private QueryData queryData;
    public Data(){

    }
    protected Data(Parcel in) {
        setMethod(in.readString());
        queryData = in.readParcelable(QueryData.class.getClassLoader());
    }

    public static final Creator<Data> CREATOR = new Creator<Data>() {
        @Override
        public Data createFromParcel(Parcel in) {
            return new Data(in);
        }

        @Override
        public Data[] newArray(int size) {
            return new Data[size];
        }
    };

    public QueryData getQueryData() {
        return queryData;
    }

    public void setQueryData(QueryData queryData) {
        this.queryData = queryData;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(getMethod());
        dest.writeParcelable(queryData, flags);
    }

    public String getMethod() {
        return method;
    }

    public void setMethod(String method) {
        this.method = method;
    }
}
