package com.noonpay.sample.samsungPay.Subscribers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.noonpay.sample.samsungPay.APIHelper.HttpClientAsync;
import com.noonpay.sample.samsungPay.APIHelper.IShowMessage;
import com.noonpay.sample.samsungPay.APIHelper.ITransformer;
import com.noonpay.sample.samsungPay.APIHelper.Identifiers;
import com.noonpay.sample.samsungPay.APIHelper.TaskRequest;
import com.noonpay.sample.samsungPay.MainActivity;
import com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.ProcessAuthentication;
import com.noonpay.sample.samsungPay.noonpayModels.Response.PaymentInfo.PaymentInfo;

/**
 * Created by abdo on 3/4/2018.
 */

public class PaymentInfoReceived extends BroadcastReceiver implements ITransformer, IShowMessage {
    final static String TAG = "PaymentInfoReceived";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.i(TAG, "On Receive [Payment Info]");
        try {
            PaymentInfo paymentInfo = intent.getParcelableExtra(Identifiers.PAYMENT_INFO);
            if (paymentInfo != null && paymentInfo.getResultCode() == 0) {
                showMessage("Payment info added with Id: " + paymentInfo.getResult().getPaymentInfoId());
                //preparing for authenticator
                ProcessAuthentication authenticator = BuildAuthenticator(MainActivity.getBagValue(Identifiers.SPAY_PAYMENT_VERIFICATION_DATA),
                        paymentInfo.getResult().getOrderId(),
                        MainActivity.getBagValue(Identifiers.PAYMENT_METHOD),
                        null);
                HttpClientAsync httpClient = new HttpClientAsync(context);

                TaskRequest taskRequest = new TaskRequest<>(authenticator,
                        com.noonpay.sample.samsungPay.noonpayModels.Response.ProcessAuthentication.ProcessAuthentication.class);

                httpClient.execute(taskRequest);
            } else {
                Intent errorIntent = new Intent("com.noonpay.sample.samsungPay.ERROR_RAISED");
                intent.putExtra(Identifiers.ERROR_MSG, "Failed to add payment info!");
                LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(intent);
            }
        } finally {
            LocalBroadcastManager.getInstance(context.getApplicationContext()).unregisterReceiver(this);
        }
    }

    @Override
    public Context getContext() {
        return this.context;
    }
}
