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
import com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder.InitiateOrder;
import com.samsung.android.sdk.samsungpay.v2.card.ISAddCardListener;

/**
 * Created by abdo on 3/5/2018.
 */

public class SamsungCardVerifiedReceived extends BroadcastReceiver implements ITransformer, IShowMessage {
    final static String TAG = "SamsungCardVerifiedReceived";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.i(TAG, "On Receive [Samsung verification]");
        try {
            InitiateOrder initiateOrder = intent.getParcelableExtra(Identifiers.ORDER_SUBMITTED);
            HttpClientAsync httpClient = new HttpClientAsync(context);

            TaskRequest taskRequest = new TaskRequest<>(initiateOrder,
                    com.noonpay.sample.samsungPay.noonpayModels.Response.InitiateOrder.InitiateOrder.class);
            httpClient.execute(taskRequest);

            showMessage("Order submitted to noonPay! successfully/");
        } finally {
            LocalBroadcastManager.getInstance(context.getApplicationContext()).unregisterReceiver(this);
        }
    }

    @Override
    public Context getContext() {
        return this.context;
    }
}
