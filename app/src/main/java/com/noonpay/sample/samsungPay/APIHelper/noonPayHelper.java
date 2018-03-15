package com.noonpay.sample.samsungPay.APIHelper;

import android.content.Context;
import android.support.annotation.NonNull;
import android.util.Log;

import com.android.volley.AuthFailureError;
import com.android.volley.Cache;
import com.android.volley.Network;
import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.toolbox.BasicNetwork;
import com.android.volley.toolbox.DiskBasedCache;
import com.android.volley.toolbox.HurlStack;
import com.noonpay.sample.samsungPay.BuildConfig;
import com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.PaymentInfo;
import com.noonpay.sample.samsungPay.noonpayModels.Response.InitiateOrder.InitiateOrder;

import java.util.HashMap;
import java.util.Map;
import java.util.function.Consumer;

/**
 * Created by abdo on 2/28/2018.
 */

public class noonPayHelper implements InoonPayHttpHelper {
    private final Context _context;
    private final RequestQueue mRequestQueue;
    private final InoonPayHttpHelper Ihelper=new InoonPayHttpHelper() {
    };
    public noonPayHelper(@NonNull final Context context) {
        this._context = context;
        // Instantiate the cache
        Cache cache = new DiskBasedCache(this._context.getCacheDir(), 1024 * 1024); // 1MB cap
        // Set up the network to use HttpURLConnection as the HTTP client.
        Network network = new BasicNetwork(new HurlStack());
        // Instantiate the RequestQueue with the cache and network.
        mRequestQueue = new RequestQueue(cache, network);
        // Start the queue
        mRequestQueue.start();
    }
    public final String TAG = "noonPayHelper";

    protected Map<String, String> getAuthHeaders() throws AuthFailureError {
        Map<String, String> headers = new HashMap<>();
        headers.put("Authorization",Ihelper.getAuthorizationHeader());
        return headers;
    }

    //region using google gson
    public void InitiateOrder(com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder.InitiateOrder initiateOrder, Consumer<InitiateOrder> OnSuccess) {

        try {
            JsonRequest jsonRequest = new JsonRequest<>(Request.Method.POST
                    , BuildConfig.NOONPAY_ORDER_URL
                    , InitiateOrder.class
                    , initiateOrder
                    , getAuthHeaders()
                    , (response) -> {
                InitiateOrder data = response;
                OnSuccess.accept(data);
            }, error -> {
                Log.e("InitiateOrder", error.toString());
            });
            // Add the request to the RequestQueue.
            mRequestQueue.add(jsonRequest);
        } catch (Exception error) {
            Log.e(TAG, error.getMessage());
        } finally {
            Log.i(TAG, "request done!");
        }
    }

    public void AddPaymentInfo(com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.PaymentInfo paymentInfo, Consumer<PaymentInfo> OnSuccess) {
        try {
            JsonRequest jsonRequest = new JsonRequest<>(Request.Method.POST
                    , BuildConfig.NOONPAY_ORDER_URL
                    , PaymentInfo.class
                    , paymentInfo
                    , getAuthHeaders()
                    , (response) -> {
                PaymentInfo data = response;
                OnSuccess.accept(data);
            }, error -> {
                Log.e("InitiateOrder", error.toString());
            });
            // Add the request to the RequestQueue.
            mRequestQueue.add(jsonRequest);
        } catch (Exception error) {
            Log.e(TAG, error.getMessage());
        } finally {
            Log.i(TAG, "request done!");
        }
    }
//endregion
}
