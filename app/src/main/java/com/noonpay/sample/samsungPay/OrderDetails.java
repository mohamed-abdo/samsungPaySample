package com.noonpay.sample.samsungPay;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.noonpay.sample.samsungPay.APIHelper.HttpClientAsync;
import com.noonpay.sample.samsungPay.APIHelper.IShowMessage;
import com.noonpay.sample.samsungPay.APIHelper.ITransformer;
import com.noonpay.sample.samsungPay.APIHelper.Identifiers;
import com.noonpay.sample.samsungPay.APIHelper.TaskRequest;
import com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.Sale;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Locale;
import java.util.stream.Collector;
import java.util.stream.Collectors;

public class OrderDetails extends Activity implements ITransformer, IShowMessage {
    private Sale saleResponse = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_order_details);
        final Button home_button = findViewById(R.id.home_button);
        home_button.setOnClickListener(this::onHomeClick);
        final Button refund_button = findViewById(R.id.refund_btn);
        refund_button.setOnClickListener(this::OnRefundClick);
        //print info
        saleResponse = (Sale) MainActivity.getBagParcelableValue(Identifiers.PAYMENT_SUCCEED_RESPONSE);
        if (saleResponse == null && saleResponse.getResultCode() == 0)
            refund_button.setEnabled(false);
        else
            refund_button.setEnabled(true);
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

    public void OnRefundClick(View view) {
        final Button refund_button = findViewById(R.id.refund_btn);
        refund_button.setEnabled(false);
        final ProgressBar progress = findViewById(R.id.progressBar);
        progress.setVisibility(View.VISIBLE);
        //call refund
        com.noonpay.sample.samsungPay.noonpayModels.Request.Refund.Refund refundInfo = BuildRefundRequest(
                saleResponse.getResult().getOrderId(),
                saleResponse.getResult().getCapturedAmount(),
                saleResponse.getResult().getCurrency(),
                saleResponse.getResult().getTransactionId());
        HttpClientAsync httpClient = new HttpClientAsync(getContext());

        TaskRequest taskRequest = new TaskRequest<>(refundInfo,
                com.noonpay.sample.samsungPay.noonpayModels.Response.Refund.Refund.class);
        httpClient.execute(taskRequest);
    }

    @Override
    public Context getContext() {
        return getApplicationContext();
    }
}
