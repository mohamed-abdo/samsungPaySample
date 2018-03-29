package com.noonpay.sample.samsungPay;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.noonpay.sample.samsungPay.APIHelper.Identifiers;
import com.noonpay.sample.samsungPay.noonpayModels.Response.Refund.Refund;
import com.noonpay.sample.samsungPay.noonpayModels.Response.Refund.RefundResponse;

import java.util.ArrayList;
import java.util.stream.Collectors;

public class RefundDetails extends Activity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_refund_details);
        final Button home_button = findViewById(R.id.home_button);
        home_button.setOnClickListener(this::onHomeClick);
        //print info
        ArrayList<String> info = MainActivity.getBagArrayValue(Identifiers.REFUND_SUCCEED_INFO);
        TextView textView = findViewById(R.id.info_view);
        if (info != null) {
            String data = info.stream()
                    .collect(Collectors.joining("\n"));
            textView.setText(data);
        } else {
            textView.setText("Refund done!");
        }
    }

    public void onHomeClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
