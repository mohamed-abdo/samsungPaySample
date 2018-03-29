package com.noonpay.sample.samsungPay.Subscribers;

import android.app.Activity;
import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.LocalBroadcastManager;
import android.util.Log;

import com.noonpay.sample.samsungPay.APIHelper.IShowMessage;
import com.noonpay.sample.samsungPay.APIHelper.ITransformer;
import com.noonpay.sample.samsungPay.APIHelper.Identifiers;
import com.noonpay.sample.samsungPay.MainActivity;
import com.noonpay.sample.samsungPay.OrderDetails;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;

import static android.support.v4.content.ContextCompat.startActivity;

/**
 * Created by abdo on 3/5/2018.
 */

public class SaleReceived extends BroadcastReceiver implements ITransformer, IShowMessage {
    final static String TAG = "SaleReceived";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.i(TAG, "On Receive [Payment done]");
        try {
            com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.Sale saleResponse = intent.getParcelableExtra(Identifiers.PAYMENT_SUCCEED);
            if (saleResponse != null && saleResponse.getResultCode() == 0) {
                MainActivity.putBagParcelableValue(Identifiers.PAYMENT_SUCCEED_RESPONSE, saleResponse);
                showMessage("Order done successfully, authorization code: " + saleResponse.getResult().getAuthorizationCode());
                // open order details on success!
                Intent orderDetailsIntent = new Intent(context.getApplicationContext(), OrderDetails.class);
                ArrayList<String> info = new ArrayList<String>() {
                    {
                        add(String.format(Locale.ENGLISH, "Authorization code: %s", saleResponse.getResult().getAuthorizationCode()));
                        add(String.format(Locale.ENGLISH, "Captured amount: %s", saleResponse.getResult().getCapturedAmount()));
                        add(String.format(Locale.ENGLISH, "Order Id: %s", saleResponse.getResult().getOrderId()));
                        add(String.format(Locale.ENGLISH, "Order Currency: %s", saleResponse.getResult().getCurrency()));
                        add(String.format(Locale.ENGLISH, "Transaction Id: %s", saleResponse.getResult().getTransactionId()));
                        add(String.format(Locale.ENGLISH, "Order status: %s", saleResponse.getResult().getStatus()));
                    }
                };
                MainActivity.putBagArrayValue(Identifiers.PAYMENT_SUCCEED_INFO, info);
                if (context instanceof MainActivity)
                    ActivityCompat.startActivityForResult((MainActivity) context, orderDetailsIntent, 0, null);
                else
                    startActivity(context.getApplicationContext(), orderDetailsIntent, null);
            } else {
                Intent errorIntent = new Intent("com.noonpay.sample.samsungPay.ERROR_RAISED");
                intent.putExtra(Identifiers.ERROR_MSG, "Failed to capture the order" + (saleResponse == null ? "" : saleResponse.getMessage()));
                LocalBroadcastManager.getInstance(context.getApplicationContext()).sendBroadcast(errorIntent);
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