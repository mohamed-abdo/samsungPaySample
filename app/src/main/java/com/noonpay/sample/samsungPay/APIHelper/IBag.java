package com.noonpay.sample.samsungPay.APIHelper;

import android.os.Bundle;

/**
 * Created by abdo on 3/7/2018.
 */
@FunctionalInterface
public interface IBag {
    Bundle getBag();

    default String getBagValue(String key) {
        synchronized (this) {
            return getBag().getString(key);
        }
    }

    default void putBagValue(String key, String value) {
        synchronized (this) {
            getBag().putString(key, value);
        }
    }
}
