package com.noonpay.sample.samsungPay.APIHelper;

import android.os.Parcel;
import android.os.Parcelable;
import android.util.JsonToken;

import java.util.function.Function;


/**
 * Created by abdo on 3/3/2018.
 */

public class TaskRequest<T> implements Parcelable {
    private T model;
    private Class outType;

    public TaskRequest(T model,Class outType) {
        this.model = model;
        this.outType = outType;
    }

    protected TaskRequest(Parcel in) {
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<TaskRequest> CREATOR = new Creator<TaskRequest>() {
        @Override
        public TaskRequest createFromParcel(Parcel in) {
            return new TaskRequest(in);
        }

        @Override
        public TaskRequest[] newArray(int size) {
            return new TaskRequest[size];
        }
    };

    public T getModel() {
        return model;
    }

    public void setModel(T model) {
        this.model = model;
    }

    public Class getOutType() {
        return outType;
    }

    public void setOutType(Class outType) {
        this.outType = outType;
    }
}
