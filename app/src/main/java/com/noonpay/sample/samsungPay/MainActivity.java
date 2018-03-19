package com.noonpay.sample.samsungPay;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.content.LocalBroadcastManager;
import android.support.v7.app.AppCompatActivity;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.util.Pair;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.noonpay.sample.samsungPay.APIHelper.IShowMessage;
import com.noonpay.sample.samsungPay.APIHelper.ITransformer;
import com.noonpay.sample.samsungPay.APIHelper.Identifiers;
import com.noonpay.sample.samsungPay.Subscribers.AuthenticatedReceived;
import com.noonpay.sample.samsungPay.Subscribers.OrderInitiatedReceived;
import com.noonpay.sample.samsungPay.Subscribers.PaymentInfoReceived;
import com.noonpay.sample.samsungPay.Subscribers.SaleReceived;
import com.noonpay.sample.samsungPay.Subscribers.SamsungCardVerifiedReceived;
import com.noonpay.sample.samsungPay.noonpayModels.Request.InitiateOrder.InitiateOrder;
import com.samsung.android.sdk.samsungpay.v2.PartnerInfo;
import com.samsung.android.sdk.samsungpay.v2.SamsungPay;
import com.samsung.android.sdk.samsungpay.v2.SpaySdk;
import com.samsung.android.sdk.samsungpay.v2.StatusListener;
import com.samsung.android.sdk.samsungpay.v2.payment.CardInfo;
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentInfo;
import com.samsung.android.sdk.samsungpay.v2.payment.PaymentManager;

import java.util.ArrayList;
import java.util.List;
import java.util.Locale;
import java.util.UUID;
import java.util.function.Consumer;
import java.util.function.Function;


public class MainActivity extends AppCompatActivity implements ITransformer, IShowMessage {
    @Override
    public Context getContext() {
        return getApplicationContext();
    }

    public static final String TAG = "MAIN_ACTIVITY_SAMSUNG_PAY_APP";

    public static final String SPAY_SERVICE_ID = BuildConfig.SPAY_SERVICE_ID;

    private static Bundle bag = new Bundle();

    public synchronized static String getBagValue(String key) {
        return bag.getString(key);
    }

    public synchronized static void putBagValue(String key, String value) {
        bag.putString(key, value);
    }

    public synchronized static ArrayList<String> getBagArrayValue(String key) {
        return bag.getStringArrayList(key);
    }

    public synchronized static void putBagArrayValue(String key, ArrayList<String> value) {
        bag.putStringArrayList(key, value);
    }

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        try {
            super.onCreate(savedInstanceState);
            setContentView(R.layout.activity_main);
            registerReceivers();
            // samsung pay assert
            EnsureSamSungPayReady();
            final TextView item_no = findViewById(R.id.item_no);
            item_no.setText(String.format(Locale.ENGLISH, "%s-%s", "noon", System.currentTimeMillis()));
            final Button pay_button = findViewById(R.id.pay_button);
            pay_button.setOnClickListener(this::onPay_Click);

            final EditText priceEdit = findViewById(R.id.price);
            //default value
            priceEdit.setText("10.0");
            PrepareOrderFields(priceEdit.getText().toString());
            priceEdit.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence s, int start, int count, int after) {

                }

                @Override
                public void onTextChanged(CharSequence s, int start, int before, int count) {
                    PrepareOrderFields(s.toString());
                }

                @Override
                public void afterTextChanged(Editable s) {

                }
            });
        } catch (Exception error) {
            error.printStackTrace(System.err);
            showMessage(error.getMessage());
        }

    }

    @Override
    protected void onDestroy() {
        unRegisterReceivers();
        super.onDestroy();
    }

    public void onPay_Click(View view) {
        try {
            runOnUiThread(this::onPayClickStart);
            final String item_no = ((TextView) findViewById(R.id.item_no)).getText().toString();
            final Double item_price = Double.parseDouble(((TextView) findViewById(R.id.price)).getText().toString());
            try {
                if (item_price < 1) {
                    showMessage("Please type a price greater than or equals to 1.0");
                    return;
                }

                InitializeCheckout(cardsDetails -> {
                            showMessage("One or more supported card(S) found!, details: " + cardsDetails);
                            final PaymentInfo paymentInfo = BuildPaymentInfo(item_no, item_price);
                            startInAppPay(paymentInfo, (SPayPair) -> {
                                //SPay first contains paymentCredential in json format ex, 3DS data
                                putBagValue(Identifiers.SPAY_PAYMENT_VERIFICATION_DATA, SPayPair.first);
                                // You can use PaymentInfo, paymentCredential and extraPaymentData.
                                // Calling noonpay_logo API!
                                ArrayList paymentEvents = MainActivity.getBagArrayValue(Identifiers.PAYMENT_EVENTS);
                                if (paymentEvents == null)
                                    paymentEvents = new ArrayList<String>();
                                paymentEvents.add(Identifiers.ORDER_SUBMITTED);
                                MainActivity.putBagArrayValue(Identifiers.PAYMENT_EVENTS, paymentEvents);
                                InitiateOrder initiateOrder = BuildInitiateOrder(BuildPaymentInfo(item_no, item_price), UUID.randomUUID().toString());

                                Intent intent = new Intent("com.noonpay.sample.samsungPay.SAMSUNG_CARD_VERIFIED");
                                intent.setPackage("com.noonpay.sample.samsungPay");
                                intent.putExtra(Identifiers.ORDER_SUBMITTED, initiateOrder);
                                LocalBroadcastManager.getInstance(getApplicationContext()).sendBroadcast(intent);
                                //done samsung verification!
                                //runOnUiThread(this::onPayClickDone);
                            });
                        }
                        , () -> showMessage("No supported card(S) exists!.")
                        , () -> showMessage("Samsung Pay is responding!")
                );
            } catch (Exception ex) {
                showMessage(ex.getLocalizedMessage());
                Log.e(TAG, ex.getMessage());
                runOnUiThread(this::onPayClickDone);
            }
        } catch (Exception error) {
            error.printStackTrace(System.err);
            Log.e(TAG, error.getMessage());
            runOnUiThread(this::onPayClickDone);
        } finally {
            Log.e(TAG, "On Pay click, finalized.");
        }
    }

    public void onPayClickDone() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button pay_button = findViewById(R.id.pay_button);
        runOnUiThread(() -> {
            progressBar.setVisibility(View.INVISIBLE);
            pay_button.setEnabled(true);
        });
    }

    private void onPayClickStart() {
        final ProgressBar progressBar = findViewById(R.id.progressBar);
        final Button pay_button = findViewById(R.id.pay_button);
        runOnUiThread(() -> {
            pay_button.setEnabled(false);
            progressBar.setVisibility(View.VISIBLE);
        });
    }

    private void registerReceivers() {

        //region Register receiver
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                new OrderInitiatedReceived(),
                new IntentFilter("com.noonpay.sample.samsungPay.ORDER_INITIATED"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                new PaymentInfoReceived(),
                new IntentFilter("com.noonpay.sample.samsungPay.PAYMENT_INFO"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                new SamsungCardVerifiedReceived(),
                new IntentFilter("com.noonpay.sample.samsungPay.SAMSUNG_CARD_VERIFIED"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                new AuthenticatedReceived(),
                new IntentFilter("com.noonpay.sample.samsungPay.ORDER_AUTHENTICATED"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                new SaleReceived(),
                new IntentFilter("com.noonpay.sample.samsungPay.PAYMENT_SUCCEED"));
        LocalBroadcastManager.getInstance(getApplicationContext()).registerReceiver(
                new OnErrorReceived(),
                new IntentFilter("com.noonpay.sample.samsungPay.ERROR_RAISED"));
        //endregion

    }

    private void unRegisterReceivers() {

        //region Register receiver
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                new OrderInitiatedReceived());
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                new PaymentInfoReceived());
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                new SamsungCardVerifiedReceived());
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                new AuthenticatedReceived());
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                new SaleReceived());
        LocalBroadcastManager.getInstance(getApplicationContext()).unregisterReceiver(
                new OnErrorReceived());
        //endregion

    }

    // region OnError
    public class OnErrorReceived extends BroadcastReceiver implements IShowMessage {
        final static String TAG = "OnErrorReceived";
        Context context;

        @Override
        public void onReceive(Context context, Intent intent) {
            this.context = context;
            try {
                String msg = intent.getStringExtra(Identifiers.ERROR_MSG);
                msg = msg == null ? "Unexpected error occurred." : msg;
                Log.i(TAG, "On Receive [On Error Received]: " + msg);
                showMessage(msg);
                onPayClickDone();
            } finally {
                LocalBroadcastManager.getInstance(context.getApplicationContext()).unregisterReceiver(this);
            }
        }

        @Override
        public Context getContext() {
            return context;
        }
    }

    //endregion
    //region Samsung Pay Helper
    private void InitializeCheckout(final Consumer<String> onCardSupport, final Runnable onNoCardSupport, final Runnable onFailed) {
        Bundle bundle = new Bundle();
        bundle.putString(SamsungPay.PARTNER_SERVICE_TYPE, SamsungPay.ServiceType.INAPP_PAYMENT.toString());
        PartnerInfo partnerInfo = new PartnerInfo(SPAY_SERVICE_ID, bundle);
        PaymentManager paymentManager = new PaymentManager(this, partnerInfo);

        paymentManager.requestCardInfo(new Bundle(), new PaymentManager.CardInfoListener() {
            /*
            * This callback is received when the card information is received successfully.
            */
            @Override
            public void onResult(List<CardInfo> cardResponse) {
                int visaCount = 0, mcCount = 0, amexCount = 0, dsCount = 0;
                String brandStrings = "- Card Info : ";
                if (cardResponse != null) {
                    PaymentManager.Brand brand;
                    //instead of loop just add a predicate to check if supported cards are listed in.
                    for (int i = 0; i < cardResponse.size(); i++) {
                        brand = cardResponse.get(i).getBrand();
                        switch (brand) {
                            case AMERICANEXPRESS:
                                amexCount++;
                                break;
                            case MASTERCARD:
                                mcCount++;
                                break;
                            case VISA:
                                visaCount++;
                                break;
                            case DISCOVER:
                                dsCount++;
                                break;
                            default:
                                break;
                        }
                    }
                }
                brandStrings += " VISA=" + visaCount + ", MASTERCARD=" + mcCount + ", AMERICANEXPRESS=" + amexCount + ", DISCOVER=" + dsCount;
                //As per documentation AMERICANEXPRESS is not supported in SDK 1.8
                if (visaCount == 0 && mcCount == 0) {
                    onNoCardSupport.run();
                }
                onCardSupport.accept(brandStrings);
            }

            /*
            /*
            * This callback is received when the card information cannot be retrieved.
            * For example, when SDK service in the Samsung Pay app dies abnormally.
            */
            @Override
            public void onFailure(int errorCode, Bundle errorData) {
                // Called when an error occurs during in-app cryptogram generation.
                onFailed.run();
                runOnUiThread(MainActivity.this::onPayClickDone);
            }
        }); // get Card Brand List
    }

    private void startInAppPay(final PaymentInfo inPaymentInfo, Consumer<Pair<String, Bundle>> OnSPaySuccess) {
        // PaymentManager.startInAppPay method to show normal payment sheet.
        try {
            Bundle bundle = new Bundle();
            bundle.putString(SamsungPay.PARTNER_SERVICE_TYPE, SamsungPay.ServiceType.INAPP_PAYMENT.toString());
            PartnerInfo partnerInfo = new PartnerInfo(SPAY_SERVICE_ID, bundle);
            PaymentManager paymentManager = new PaymentManager(getApplicationContext(), partnerInfo);
            paymentManager.startInAppPay(inPaymentInfo, new PaymentManager.TransactionInfoListener() {
                @Override
                public void onAddressUpdated(PaymentInfo paymentInfo) {
                    //TODO implement the logic
                    paymentManager.updateAmount(inPaymentInfo.getAmount());
                }

                @Override
                public void onCardInfoUpdated(CardInfo cardInfo) {
                    //TODO implement the logic
                    paymentManager.updateAmount(inPaymentInfo.getAmount());
                }

                @Override
                public void onSuccess(PaymentInfo response, String paymentCredential, Bundle extraPaymentData) {
                    showMessage("Samsung Pay, successfully respond!");
                    OnSPaySuccess.accept(new Pair<>(paymentCredential, extraPaymentData));
                }

                @Override
                public void onFailure(int errorCode, Bundle errorData) {
                    showMessage("Samsung Pay, failed to respond!");
                    runOnUiThread(MainActivity.this::onPayClickDone);
                }
            });
        } catch (NullPointerException e) {
            e.printStackTrace();
            showMessage("All mandatory fields cannot be null.");
            runOnUiThread(MainActivity.this::onPayClickDone);

        } catch (IllegalStateException e) {
            e.printStackTrace();
            showMessage("IllegalStateException");
            runOnUiThread(MainActivity.this::onPayClickDone);

        } catch (NumberFormatException e) {
            e.printStackTrace();
            showMessage("Amount values is not valid");
            runOnUiThread(MainActivity.this::onPayClickDone);
        } catch (IllegalArgumentException e) {
            e.printStackTrace();
            showMessage("PaymentInfo values are not valid or all mandatory fields not set.");
            runOnUiThread(MainActivity.this::onPayClickDone);
        }
    }

    //endregion

    //region Lambda Helpers

    private Function<Double, Double> calcVAT = (price) -> price * .05;

    private Function<Double, Double> calcShippingCost = (price) -> price < 10 ? 10.0 : 0;

    private Function<Double, Double> calcTotalPrice = (price) -> price + calcVAT.apply(price) + calcShippingCost.apply(price);

    //endregion

    //region Helpers

    private void EnsureSamSungPayReady() {
        final Button checkoutButton = (Button) findViewById(R.id.pay_button);

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            checkoutButton.setEnabled(false);
            showMessage("You need to update your Operating system to able to use Samsung Pay!");
            return;
        }
        Bundle bundle = new Bundle();
        bundle.putString(SamsungPay.PARTNER_SERVICE_TYPE, SamsungPay.ServiceType.INAPP_PAYMENT.toString());
        PartnerInfo partnerInfo = new PartnerInfo(SPAY_SERVICE_ID, bundle);

        final SamsungPay samsungPay = new SamsungPay(this, partnerInfo);

        samsungPay.getSamsungPayStatus(new StatusListener() {
            @Override
            public void onSuccess(int status, Bundle bundle) {
                switch (status) {
                    case SamsungPay.SPAY_NOT_SUPPORTED: // Samsung Pay is not supported
                        checkoutButton.setEnabled(false);
                        samsungPay.goToUpdatePage();
                        showMessage("SPAY_NOT_SUPPORTED");
                        break;
                    case SamsungPay.SPAY_NOT_READY: // Activate Samsung Pay or update Samsung Pay, if needed
                        int extra_reason = bundle.getInt(SamsungPay.EXTRA_ERROR_REASON);
                        switch (extra_reason) {
                            case SamsungPay.ERROR_SPAY_APP_NEED_TO_UPDATE:
                                samsungPay.goToUpdatePage();
                                showMessage("ERROR_SPAY_APP_NEED_TO_UPDATE");
                                break;
                            case SamsungPay.ERROR_SPAY_SETUP_NOT_COMPLETED:
                                samsungPay.activateSamsungPay();
                                break;
                            default:
                                showMessage("Samsung PAY is not ready, extra reason: " + extra_reason);
                                Log.e(TAG, "Samsung PAY is not ready, extra reason: " + extra_reason);
                        }
                        checkoutButton.setEnabled(false);
                        break;
                    case SamsungPay.SPAY_READY: // Samsung Pay is ready
                        checkoutButton.setEnabled(true);
                        break;
                    default:// Not expected result
                        checkoutButton.setEnabled(false);
                        showMessage("Not expected result");
                        break;
                }
            }

            @Override
            public void onFail(int errorCode, Bundle bundle) {
                checkoutButton.setEnabled(false);
                Log.d(TAG, "Failed to initialize Samsung Pay service." + errorCode);
            }
        });
    }


    private void PrepareOrderFields(String item_price) {
        try {
            if (item_price.contentEquals("")) {
                showMessage("price is require!");
                return;
            }
        } catch (NumberFormatException ex) {
            showMessage("price has invalid format!");
        }

        final TextView item_shippingCost = (TextView) findViewById(R.id.shipping_cost);
        final TextView item_VAT = (TextView) findViewById(R.id.tax);
        final TextView item_totalCost = (TextView) findViewById(R.id.amount);

        item_shippingCost.setText(String.format(Locale.ENGLISH, "%.2f", calcShippingCost.apply(Double.parseDouble((item_price)))));
        item_VAT.setText(String.format(Locale.ENGLISH, "%.2f", calcVAT.apply(Double.parseDouble((item_price)))));
        item_totalCost.setText(String.format(Locale.ENGLISH, "%.2f", calcTotalPrice.apply(Double.parseDouble((item_price
        )))));
    }

    private PaymentInfo BuildPaymentInfo(final String item_no, final Double item_price) {
        ArrayList<SpaySdk.Brand> brandList = new ArrayList<>();
        brandList.add(PaymentManager.Brand.MASTERCARD);
        brandList.add(PaymentManager.Brand.VISA);

        PaymentInfo.Address shippingAddress =
                new PaymentInfo.Address.Builder()
                        .setAddressee("Greens")
                        .setAddressLine1("Emmar Business Park")
                        .setAddressLine2("Building 3")
                        .setCity("dubai")
                        .setState("dubai")
                        .setCountryCode("United Arab Emirates")
                        .setPostalCode("17711")
                        .build();

        PaymentInfo.Amount amount =
                new PaymentInfo.Amount.Builder()
                        .setCurrencyCode("AED")
                        .setItemTotalPrice(String.format(Locale.ENGLISH, "%.2f", item_price))
                        .setShippingPrice(String.format(Locale.ENGLISH, "%.2f", calcShippingCost.apply(item_price)))
                        .setTax(String.format(Locale.ENGLISH, "%.2f", calcVAT.apply(item_price)))
                        .setTotalPrice(String.format(Locale.ENGLISH, "%.2f", calcTotalPrice.apply(item_price)))
                        .build();

        PaymentInfo.Builder paymentInfoBuilder =
                new PaymentInfo.Builder();

        return paymentInfoBuilder
                .setMerchantId("noonPay")
                .setMerchantName("noon")
                .setOrderNumber(item_no)
                //TODO" review payment protocol
                .setPaymentProtocol(PaymentInfo.PaymentProtocol.PROTOCOL_3DS)
                /* Include NEED_BILLING_SEND_SHIPPING option for AddressInPaymentSheet if merchant needs
                * the billing address from Samsung Pay but wants to send the shipping address to Samsung Pay.
                * Both billing and shipping address will be shown on the payment sheet.
                */
                .setAddressInPaymentSheet(PaymentInfo.AddressInPaymentSheet.NEED_BILLING_SEND_SHIPPING)
                .setShippingAddress(shippingAddress)
                .setAllowedCardBrands(brandList)
                .setCardHolderNameEnabled(true)
                .setRecurringEnabled(false)
                .setAmount(amount)
                .build();
    }
    //endregion
}
