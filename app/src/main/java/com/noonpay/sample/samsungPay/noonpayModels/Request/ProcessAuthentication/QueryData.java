package com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication;

import android.os.Parcel;
import android.os.Parcelable;

public class QueryData implements Parcelable {
    private String status;
    private String cardBrand;
    //TODO: change to last4_pan
    private String last4Pan;
    private String data;
    private String dataType;
    private String dataVersion;
    private String merchantRef;
    public QueryData() {
    }

    protected QueryData(Parcel in) {
        status = in.readString();
        cardBrand = in.readString();
        last4Pan = in.readString();
        data = in.readString();
        dataType = in.readString();
        dataVersion = in.readString();
        merchantRef = in.readString();
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(status);
        dest.writeString(cardBrand);
        dest.writeString(last4Pan);
        dest.writeString(data);
        dest.writeString(dataType);
        dest.writeString(dataVersion);
        dest.writeString(merchantRef);
    }

    @Override
    public int describeContents() {
        return 0;
    }

    public static final Creator<QueryData> CREATOR = new Creator<QueryData>() {
        @Override
        public QueryData createFromParcel(Parcel in) {
            return new QueryData(in);
        }

        @Override
        public QueryData[] newArray(int size) {
            return new QueryData[size];
        }
    };

    public String getDataVersion() {
        return dataVersion;
    }

    public void setDataVersion(String dataVersion) {
        this.dataVersion = dataVersion;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getCardBrand() {
        return cardBrand;
    }

    public void setCardBrand(String cardBrand) {
        this.cardBrand = cardBrand;
    }

    public String getLast4Pan() {
        return last4Pan;
    }

    public void setLast4Pan(String last4Pan) {
        this.last4Pan = last4Pan;
    }

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public String getDataType() {
        return dataType;
    }

    public void setDataType(String dataType) {
        this.dataType = dataType;
    }

    public String getMerchantRef() {
        return merchantRef;
    }

    public void setMerchantRef(String merchantRef) {
        this.merchantRef = merchantRef;
    }
}