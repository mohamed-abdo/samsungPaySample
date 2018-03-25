package com.noonpay.sample.samsungPay.Subscribers;
/**
 * Created by abdo on 3/5/2018.
 */

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
import com.noonpay.sample.samsungPay.noonpayModels.Request.Sale.Sale;
import com.noonpay.sample.samsungPay.noonpayModels.Response.ProcessAuthentication.ProcessAuthentication;

public class AuthenticatedReceived extends BroadcastReceiver implements ITransformer, IShowMessage {
    final static String TAG = "AuthenticatedReceived";
    Context context;

    @Override
    public void onReceive(Context context, Intent intent) {
        this.context = context;
        Log.i(TAG, "On Receive [Payment Authentication]");
        try {
            ProcessAuthentication authenticator = intent.getParcelableExtra(Identifiers.ORDER_AUTHENTICATED);
            if (authenticator != null && authenticator.getResultCode() == 0) {
                showMessage("Order authenticated successfully, for merchant " + authenticator.getResult().getMerchant());
                //preparing for sale (authorize & capture)
                Sale saleRequest = BuildSaleRequest(authenticator.getResult().getOrderId());

                HttpClientAsync httpClient = new HttpClientAsync(context);

                TaskRequest taskRequest = new TaskRequest<>(saleRequest,
                        com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.Sale.class);

                httpClient.execute(taskRequest);
            } else {
                Intent errorIntent = new Intent("com.noonpay.sample.samsungPay.ERROR_RAISED");
                intent.putExtra(Identifiers.ERROR_MSG, "Failed to process authentication info!");
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
