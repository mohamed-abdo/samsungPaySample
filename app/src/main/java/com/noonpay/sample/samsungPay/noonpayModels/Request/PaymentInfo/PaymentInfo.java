/**
 * Created by abdo on 3/1/2018.
 */

package com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo;

import android.os.Parcel;
import android.os.Parcelable;

public class PaymentInfo implements Parcelable {

    private String apiOperation;
    private Order order;
    private PaymentData paymentData;
    public final static Parcelable.Creator<PaymentInfo> CREATOR = new Creator<PaymentInfo>() {


        @SuppressWarnings({
                "unchecked"
        })
        public PaymentInfo createFromParcel(Parcel in) {
            return new PaymentInfo(in);
        }

        public PaymentInfo[] newArray(int size) {
            return (new PaymentInfo[size]);
        }

    };

    protected PaymentInfo(Parcel in) {
        this.apiOperation = ((String) in.readValue((String.class.getClassLoader())));
        this.order = ((Order) in.readValue((Order.class.getClassLoader())));
        this.paymentData = ((PaymentData) in.readValue((PaymentData.class.getClassLoader())));
    }

    public PaymentInfo() {
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

    public PaymentData getPaymentData() {
        return paymentData;
    }

    public void setPaymentData(PaymentData paymentData) {
        this.paymentData = paymentData;
    }

    public void writeToParcel(Parcel dest, int flags) {
        dest.writeValue(apiOperation);
        dest.writeValue(order);
        dest.writeValue(paymentData);
    }

    public int describeContents() {
        return 0;
    }

}
