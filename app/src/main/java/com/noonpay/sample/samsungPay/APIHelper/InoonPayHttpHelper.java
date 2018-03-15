package com.noonpay.sample.samsungPay.APIHelper;

import android.util.Base64;

import com.noonpay.sample.samsungPay.BuildConfig;

/**
 * Created by abdo on 3/5/2018.
 */

public interface InoonPayHttpHelper {
    default String getAuthorizationHeader() {
        return BuildConfig.NOONPAY_AUTH_SCHEME + " " + Base64.encodeToString((BuildConfig.NOONPAY_BUSINESS_ID + ":" + BuildConfig.NOONPAY_KEY).getBytes(), Base64.DEFAULT);
    }

    default String getOrderAPIUrl() {
        return BuildConfig.NOONPAY_ORDER_URL;
    }
}
