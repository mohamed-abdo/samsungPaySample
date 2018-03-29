package com.noonpay.sample.samsungPay.APIHelper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder.Order;
import com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.Data;
import com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.PaymentData;
import com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.ProcessAuthentication;
import com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.QueryData;
import com.noonpay.sample.samsungPay.noonpayModels.Request.Refund.Refund;
import com.noonpay.sample.samsungPay.noonpayModels.Request.Refund.Transaction;
import com.noonpay.sample.samsungPay.noonpayModels.Request.Sale.Sale;
import com.noonpay.sample.samsungPay.noonpayModels.Response.GeneralResponse;
import com.noonpay.sample.samsungPay.noonpayModels.Response.InitiateOrder.InitiateOrder;
import com.noonpay.sample.samsungPay.noonpayModels.Response.InitiateOrder.InitiateOrderResponse;
import com.noonpay.sample.samsungPay.noonpayModels.Response.PaymentInfo.PaymentInfo;
import com.noonpay.sample.samsungPay.noonpayModels.Response.PaymentInfo.PaymentInfoResponse;
import com.fasterxml.jackson.databind.JsonNode;
import com.noonpay.sample.samsungPay.noonpayModels.Response.ProcessAuthentication.ProcessAuthenticationResponse;
import com.noonpay.sample.samsungPay.noonpayModels.Response.Refund.RefundResponse;
import com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.SaleResponse;

import java.io.IOException;
import java.util.ArrayList;

/**
 * Created by abdo on 3/4/2018.
 */

public interface ITransformer {
    default String getTAG() {
        return "ITransformer";
    }

    default GeneralResponse transformResponse(JsonNode json) {
        GeneralResponse response = new GeneralResponse();
        response.setResultCode(json.at("/resultCode").asInt());
        response.setMessage(json.at("/message").asText());
        response.setResultClass(json.at("/resultClass").asText());
        response.setMessage(json.at("/classDescription").asText());
        response.setMessage(json.at("/actionHint").asText());
        response.setMessage(json.at("/requestReference").asText());
        return response;
    }

    default InitiateOrder transformInitiateOrder(JsonNode json) {
        InitiateOrder response = new InitiateOrder();
        response.setResultCode(json.at("/resultCode").asInt());
        response.setMessage(json.at("/message").asText());
        response.setResultClass(json.at("/resultClass").asText());
        response.setMessage(json.at("/classDescription").asText());
        response.setMessage(json.at("/actionHint").asText());
        response.setMessage(json.at("/requestReference").asText());

        InitiateOrderResponse initiateOrderResponse = new InitiateOrderResponse();
        initiateOrderResponse.setOrderId(json.at("/result/order/id").asText());
        ArrayList<JsonNode> options = new ArrayList<>();
        json.at("/result/paymentOptions").iterator().forEachRemaining(options::add);
        ArrayList<String> methods = new ArrayList<>();
        for (JsonNode o : options) {
            if (o.at("/action").asText().matches("(?i:SamsungPay)")) {
                methods.add(o.at("/method").asText());
            }
        }

        initiateOrderResponse.setPaymentMethods(methods);
        response.setResult(initiateOrderResponse);
        return response;
    }

    default PaymentInfo transformPaymentInfo(JsonNode json) {
        PaymentInfo response = new PaymentInfo();
        response.setResultCode(json.at("/resultCode").asInt());
        response.setMessage(json.at("/message").asText());
        response.setResultClass(json.at("/resultClass").asText());
        response.setMessage(json.at("/classDescription").asText());
        response.setMessage(json.at("/actionHint").asText());
        response.setMessage(json.at("/requestReference").asText());

        PaymentInfoResponse paymentInfoResponse = new PaymentInfoResponse();
        paymentInfoResponse.setOrderId(json.at("/result/order/id").asText());
        paymentInfoResponse.setMethod(json.at("/result/paymentData/method").asText());
        paymentInfoResponse.setPaymentInfoId(json.at("/result/paymentData/data/id").asText());
        paymentInfoResponse.setHref(json.at("/result/paymentData/data/href").asText());
        paymentInfoResponse.setMod(json.at("/result/paymentData/data/encInfo/mod").asText());
        paymentInfoResponse.setExp(json.at("/result/paymentData/data/href").asText());
        paymentInfoResponse.setKeyId(json.at("/result/paymentData/data/href").asText());
        paymentInfoResponse.setServiceId(json.at("/result/paymentData/data/setServiceId").asText());

        response.setResult(paymentInfoResponse);
        return response;
    }

    default com.noonpay.sample.samsungPay.noonpayModels.Response.ProcessAuthentication.ProcessAuthentication transformAuthenticationInfo(JsonNode json) {
        com.noonpay.sample.samsungPay.noonpayModels.Response.ProcessAuthentication.ProcessAuthentication response
                = new com.noonpay.sample.samsungPay.noonpayModels.Response.ProcessAuthentication.ProcessAuthentication();
        response.setResultCode(json.at("/resultCode").asInt());
        response.setMessage(json.at("/message").asText());
        response.setResultClass(json.at("/resultClass").asText());
        response.setMessage(json.at("/classDescription").asText());
        response.setMessage(json.at("/actionHint").asText());
        response.setMessage(json.at("/requestReference").asText());

        ProcessAuthenticationResponse authResponse = new ProcessAuthenticationResponse();
        authResponse.setOrderId(json.at("/result/order/id").asText());
        authResponse.setStatus(json.at("/result/order/status").asText());
        authResponse.setMerchant(json.at("/result/merchant/id").asText());

        response.setResult(authResponse);
        return response;
    }

    default com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.Sale transformSaleInfo(JsonNode json) {
        com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.Sale response
                = new com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.Sale();
        response.setResultCode(json.at("/resultCode").asInt());
        response.setMessage(json.at("/message").asText());
        response.setResultClass(json.at("/resultClass").asText());
        response.setMessage(json.at("/classDescription").asText());
        response.setMessage(json.at("/actionHint").asText());
        response.setMessage(json.at("/requestReference").asText());

        SaleResponse saleResponse = new SaleResponse();
        saleResponse.setOrderId(json.at("/result/order/id").asText());
        saleResponse.setStatus(json.at("/result/order/status").asText());
        saleResponse.setCurrency(json.at("/result/order/currency").asText());
        saleResponse.setCapturedAmount(json.at("/result/order/totalCapturedAmount").asDouble());
        saleResponse.setTransactionId(json.at("/result/transaction/id").asText());
        saleResponse.setAuthorizationCode(json.at("/result/transaction/authorizationCode").asText());

        response.setResult(saleResponse);
        return response;
    }

    default com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder.InitiateOrder BuildInitiateOrder(com.samsung.android.sdk.samsungpay.v2.payment.PaymentInfo paymentInfo, String merchantRef) {

        com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder.InitiateOrder initiateOrder = new com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder.InitiateOrder();
        initiateOrder.setApiOperation("INITIATE");
        Order order = new Order();
        order.setAmount(paymentInfo.getAmount().getTotalPrice());
        order.setCurrency(paymentInfo.getAmount().getCurrencyCode());
        order.setName(paymentInfo.getOrderNumber());
        order.setReference(merchantRef);
        order.setChannel("mobile");
        order.setCategory("samsungPay");
        initiateOrder.setOrder(order);
        return initiateOrder;
    }

    default com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.PaymentInfo BuildPaymentInfo(String orderId, String paymentMethod) {
        com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.PaymentInfo paymentInfo = new com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.PaymentInfo();
        paymentInfo.setApiOperation("ADD_PAYMENT_INFO");
        com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.Order order =
                new com.noonpay.sample.samsungPay.noonpayModels.Request.PaymentInfo.Order();
        order.setId(orderId);
        PaymentData paymentData = new PaymentData();
        paymentData.setMethod(paymentMethod);
        Data data = new Data();
        data.setReturnUrl("");
        paymentData.setData(data);
        paymentInfo.setOrder(order);
        paymentInfo.setPaymentData(paymentData);

        return paymentInfo;
    }

    default ProcessAuthentication BuildAuthenticator(@NonNull final String payLoad, @NonNull final String orderId, @NonNull final String method, final String extraData) {
        final ObjectMapper objectMapper = new ObjectMapper();
        JsonNode jsonPayLoad = null;
        try {
            jsonPayLoad = objectMapper.readTree(payLoad);
        } catch (IOException e) {
            e.printStackTrace(System.err);
            Log.e(getTAG(), e.getMessage());
        }
        ProcessAuthentication authenticator = new ProcessAuthentication();
        authenticator.setApiOperation("PROCESS_AUTHENTICATION");
        com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.Order order =
                new com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.Order();
        order.setId(orderId);
        com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.PaymentData paymentData =
                new com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.PaymentData();
        paymentData.setMethod(method);
        com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.Data data =
                new com.noonpay.sample.samsungPay.noonpayModels.Request.ProcessAuthentication.Data();
        data.setMethod(jsonPayLoad.at("/method").asText());
        QueryData queryData =
                new QueryData();
        queryData.setStatus("processed");
        queryData.setData(jsonPayLoad.at("/3DS/data").asText());
        queryData.setDataType(jsonPayLoad.at("/3DS/type").asText());
        queryData.setDataVersion(jsonPayLoad.at("/3DS/version").asText());
        queryData.setCardBrand(jsonPayLoad.at("/payment_card_brand").asText());
        queryData.setLast4Pan(jsonPayLoad.at("/payment_last4_fpan").asText());
        queryData.setMerchantRef(jsonPayLoad.at("/merchant_ref").asText());
        data.setQueryData(queryData);

        paymentData.setData(data);
        authenticator.setOrder(order);
        authenticator.setPaymentData(paymentData);

        return authenticator;
    }

    default Sale BuildSaleRequest(@NonNull final String orderId) {
        Sale saleRequest = new Sale();
        saleRequest.setApiOperation("SALE");
        com.noonpay.sample.samsungPay.noonpayModels.Request.Sale.Order order =
                new com.noonpay.sample.samsungPay.noonpayModels.Request.Sale.Order();
        order.setId(orderId);

        saleRequest.setOrder(order);
        return saleRequest;
    }

    default Refund BuildRefundRequest(
            @NonNull final String orderId,
            @NonNull final double amount,
            @NonNull final String currency,
            @NonNull final String targetTransactionId
    ) {
        Refund refundRequest = new Refund();
        refundRequest.setApiOperation("REFUND");
        com.noonpay.sample.samsungPay.noonpayModels.Request.Refund.Order order =
                new com.noonpay.sample.samsungPay.noonpayModels.Request.Refund.Order();
        order.setId(orderId);

        Transaction transaction=new Transaction();
        transaction.setAmount(amount);
        transaction.setCurrency(currency);
        transaction.setTargetTransactionId(targetTransactionId);

        refundRequest.setTransaction(transaction);
        refundRequest.setOrder(order);
        return refundRequest;
    }

    default com.noonpay.sample.samsungPay.noonpayModels.Response.Refund.Refund transformRefundInfo(JsonNode json){
        com.noonpay.sample.samsungPay.noonpayModels.Response.Refund.Refund response
                = new com.noonpay.sample.samsungPay.noonpayModels.Response.Refund.Refund();
        response.setResultCode(json.at("/resultCode").asInt());
        response.setMessage(json.at("/message").asText());
        response.setResultClass(json.at("/resultClass").asText());
        response.setMessage(json.at("/classDescription").asText());
        response.setMessage(json.at("/actionHint").asText());
        response.setMessage(json.at("/requestReference").asText());

        RefundResponse refundResponse = new RefundResponse();
        refundResponse.setOrderId(json.at("/result/order/id").asText());
        refundResponse.setAuthorizationCode(json.at("/result/transaction/authorizationCode").asText());
        refundResponse.setStatus(json.at("/result/order/status").asText());
        refundResponse.setCurrency(json.at("/result/order/currency").asText());
        refundResponse.setTransactionId(json.at("/result/transaction/id").asText());
        refundResponse.setTotalRefundedAmount(json.at("/result/order/totalRefundedAmount").asDouble());
        refundResponse.setTotalSalesAmount(json.at("/result/order/totalSalesAmount").asDouble());
        response.setResult(refundResponse);
        return response;
    }
}

