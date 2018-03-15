package com.noonpay.sample.samsungPay.APIHelper;

import android.app.Application;
import android.content.Context;
import android.os.Looper;
import android.os.Message;
import android.util.Log;
import android.widget.Toast;

import java.util.logging.Handler;
import java.util.logging.LogRecord;

/**
 * Created by abdo on 3/7/2018.
 */
@FunctionalInterface
public interface IShowMessage {
    Context getContext();

    default void showMessage(String message) {
        final Thread mThread = new Thread() {
            @Override
            public void run() {
                try {
                    Looper.prepare();
                    Toast.makeText(getContext(), message, Toast.LENGTH_SHORT).show();
                    Looper.loop();
                } catch (Exception error) {
                    error.printStackTrace();
                    Log.e("IShowMessage", error.getMessage());
                }
            }
        };
        mThread.start();
    }
}
