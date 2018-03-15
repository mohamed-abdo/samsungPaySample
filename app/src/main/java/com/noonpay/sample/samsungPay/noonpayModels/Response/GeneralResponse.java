package com.noonpay.sample.samsungPay.noonpayModels.Response;


import android.os.Parcel;
import android.os.Parcelable;


/**
 * Created by abdo on 3/4/2018.
 */

public class GeneralResponse<T> implements Parcelable {

    public GeneralResponse() {
    }

    private Integer resultCode;
    private String resultClass;
    private String message;
    private String classDescription;
    private String actionHint;
    private String requestReference;
    private T result;


    protected GeneralResponse(Parcel in) {
        if (in.readByte() == 0) {
            resultCode = null;
        } else {
            resultCode = in.readInt();
        }
        resultClass = in.readString();
        message = in.readString();
        classDescription = in.readString();
        actionHint = in.readString();
        requestReference = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        if (resultCode == null) {
            dest.writeByte((byte) 0);
        } else {
            dest.writeByte((byte) 1);
            dest.writeInt(resultCode);
        }
        dest.writeString(resultClass);
        dest.writeString(message);
        dest.writeString(classDescription);
        dest.writeString(actionHint);
        dest.writeString(requestReference);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<GeneralResponse> CREATOR = new Creator<GeneralResponse>() {
        @Override
        public GeneralResponse createFromParcel(Parcel in) {
            return new GeneralResponse(in);
        }

        @Override
        public GeneralResponse[] newArray(int size) {
            return new GeneralResponse[size];
        }
    };

    public Integer getResultCode() {
        return resultCode;
    }

    public void setResultCode(Integer resultCode) {
        this.resultCode = resultCode;
    }

    public String getResultClass() {
        return resultClass;
    }

    public void setResultClass(String resultClass) {
        this.resultClass = resultClass;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getClassDescription() {
        return classDescription;
    }

    public void setClassDescription(String classDescription) {
        this.classDescription = classDescription;
    }

    public String getActionHint() {
        return actionHint;
    }

    public void setActionHint(String actionHint) {
        this.actionHint = actionHint;
    }

    public String getRequestReference() {
        return requestReference;
    }

    public void setRequestReference(String requestReference) {
        this.requestReference = requestReference;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
