package com.noonpay.sample.samsungPay.APIHelper;

import android.support.annotation.NonNull;
import android.util.Log;

import com.fasterxml.jackson.core.JsonParseException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.BufferedReader;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.io.OutputStream;
import java.net.HttpURLConnection;
import java.net.URL;


/**
 * Created by abdo on 3/3/2018.
 */

public interface IPostHttpClient extends IShowMessage {

    ITransformer ITransformResult();

    String getAuthorizationHeader();

    String getHttpUrl();

    default Object PostData(@NonNull final Object inModel, @NonNull final Class outType) throws Exception {
        final String TAG = "PostHttpClientHelper";
        final ITransformer transformer = ITransformResult();
        final ObjectMapper objectMapper = new ObjectMapper();
        HttpURLConnection urlConnection = null;
        try {
            final URL _url = new URL(getHttpUrl());
            urlConnection = (HttpURLConnection) _url.openConnection();
            urlConnection.setDoInput(true);
            urlConnection.setDoOutput(true);
            urlConnection.setUseCaches(false);
            urlConnection.setChunkedStreamingMode(0);
            urlConnection.setRequestMethod("POST");
            urlConnection.setRequestProperty("Authorization", getAuthorizationHeader());
            urlConnection.setRequestProperty("Content-Type", "application/json; charset=utf-8");
            String serialized = objectMapper.writeValueAsString(inModel);
            byte[] postData = serialized.getBytes("UTF-8");
            urlConnection.setRequestProperty("Content-Length", "" + postData.length);
            OutputStream out = new BufferedOutputStream(urlConnection.getOutputStream());
            out.write(postData);
            out.flush();
            //int responseCode = urlConnection.getResponseCode();
            InputStream in = new BufferedInputStream(urlConnection.getInputStream());
            BufferedReader reader = new BufferedReader(new InputStreamReader(in));
            StringBuilder result = new StringBuilder();
            String line;
            while ((line = reader.readLine()) != null) {
                result.append(line);
            }
            in.close();
            String jsonResult = result.toString();
            JsonNode jsonNode = objectMapper.readTree(jsonResult);
            switch (outType.getName()) {
                case "com.noonpay.sample.samsungPay.noonpayModels.Response.InitiateOrder.InitiateOrder": {
                    return transformer.transformInitiateOrder(jsonNode);
                }
                case "com.noonpay.sample.samsungPay.noonpayModels.Response.PaymentInfo.PaymentInfo": {
                    return transformer.transformPaymentInfo(jsonNode);
                }
                case "com.noonpay.sample.samsungPay.noonpayModels.Response.ProcessAuthentication.ProcessAuthentication": {
                    return transformer.transformAuthenticationInfo(jsonNode);
                }
                case "com.noonpay.sample.samsungPay.noonpayModels.Response.Sale.Sale": {
                    return transformer.transformSaleInfo(jsonNode);
                }
                case "com.noonpay.sample.samsungPay.noonpayModels.Response.Refund.Refund": {
                    return transformer.transformRefundInfo(jsonNode);
                }
                default: {
                    Log.d(TAG, "Unexpected outType");
                    return transformer.transformResponse(jsonNode);
                }
            }
        } catch (Exception error) {
            error.printStackTrace(System.err);
            Log.e(TAG, error.getMessage());
            throw new Exception(String.format("Error while post data to the api: %s", error.getMessage()));
        } finally {
            if (urlConnection != null)
                urlConnection.disconnect();
        }
    }
}
