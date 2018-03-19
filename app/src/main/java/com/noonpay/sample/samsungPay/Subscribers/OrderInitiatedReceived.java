package com.noonpay.sample.samsungPay.Subscribers;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;
import android.widget.Toast;

import com.noonpay.sample.samsungPay.APIHelper.HttpClientAsync;
import com.noonpay.sample.samsungPay.APIHelper.IShowMessage;
import com.noonpay.sample.samsungPay.APIHelper.Identifiers;
import com.noonpay.sample.samsungPay.APIHelper.TaskRequest;
import com.noonpay.sample.samsungPay.APIHelper.ITransformer;
import com.noonpay.sample.samsungPay.MainActivity;
import com.noonpay.sample.samsungPay.noonpayModels.Response.InitiateOrder.InitiateOrder;

import java.util.Optional;

/**
 * Created by abdo on 3/4/2018.
 */

public class OrderInitiatedReceived extends BroadcastReceiver implements ITransformer, IShowMessage {
    final static String TAG = "OrderInitiatedReceived";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.i(TAG, "On Receive [Order Initiated]");
        try {

            InitiateOrder order = intent.getParcelableExtra(Identifiers.ORDER_INITIATED);
            String orderId, paymentMethod = "SamsungPay";//default value for testing
            //TODO: filter should be revised.
            if (order != null && order.getResultCode() == 0) {
                showMessage("OrderId: " + order.getResult().getOrderId());
                orderId = order.getResult().getOrderId();
                MainActivity.putBagValue(Identifiers.ORDER_ID, orderId);
                Optional<String> option = order
                        .getResult()
                        .getPaymentMethods()
                        .stream()
                        .findFirst();
                if (option.isPresent())
                    paymentMethod = option.get();
                com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.PaymentInfo paymentInfo = BuildPaymentInfo(orderId, paymentMethod);
                HttpClientAsync httpClient = new HttpClientAsync(context);

                TaskRequest taskRequest = new TaskRequest<>(paymentInfo,
                        com.noonpay.sample.samsungPay.noonpayModels.Response.PaymentInfo.PaymentInfo.class);
                httpClient.execute(taskRequest);
            } else {
                showMessage("Failed to initialize payment order! ");
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
