package com.noonpay.sample.samsungPay;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.noonpay.sample.samsungPay.APIHelper.Identifiers;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OrderDetails extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        final Button home_button = findViewById(R.id.home_button);
        home_button.setOnClickListener(this::onHomeClick);
        //print info
        ArrayList<String> info = MainActivity.getBagArrayValue(Identifiers.PAYMENT_SUCCEED_INFO);
        TextView textView = findViewById(R.id.info_view);
        if (info != null) {
            String data = info.stream()
                    .collect(Collectors.joining("\n"));
            textView.setText(data);
        } else {
            textView.setText("Payment done!");
        }
    }

    public void onHomeClick(View view) {
        Intent intent = new Intent(getApplicationContext(), MainActivity.class);
        startActivity(intent);
    }
}
