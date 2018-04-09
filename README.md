# samsungPaySample
Payment sample using samsungPay SDK integrated with noonpay (payment gateway)
You need to add debug key to the manifest file required to integrate the Samsung Pay SDK, it's important to understand the SDK's Debug API key and API level attributes. Both are mandatory for placing your app into debug mode initially, and release mode eventually.
Debug API key
The Debug API key is used to verify a validated partner app before it is allowed to interact with the Samsung Pay app. The portal generates the key when you create/edit the service for your app.
The key is valid for three (3) months but may be revoked by Samsung if unresolved issues persist in the partner app.
Partners can request an additional Debug API key after ninety (90) days. Although an unlimited number of devices can use the key, each partner is limited to ten (10) Samsung Accounts for testing purposes.
All partners need to safeguard their debug_api_key and service ID; these values should never be included in debug logs.
Modify your project's manifest file

1.	Debug mode, each partner app running in debug mode must include debug_mode, spay_debug_api_key, and spay_sdk_api_level values as meta-data in the AndroidManifest file.
<application>
<meta-data
android:name="debug_mode"
android:value="Y" />
<meta-data
android:name="spay_debug_api_key"
android:value="xxxxxxxxxxxxxxx" />  // *same key from samsungPay developer portal
<meta-data
android:name="spay_sdk_api_level"
android:value="1.7" 
</application>
Be sure to place <meta-data> tags inside the <application> tag.
 

2.	Update the following keys from build.gradle file (module:app)
buildTypes.each {
        it.buildConfigField('String', 'SPAY_SERVICE_ID', '"xxxxxxxxxxxxxxx"')
        it.buildConfigField('String', 'NOONPAY_AUTH_SCHEME', '"KEY_TEST"')
        it.buildConfigField('String', 'NOONPAY_BUSINESS_ID', '"xxxxxxx.xxxxxxxxxxx"')
        it.buildConfigField('String', 'NOONPAY_KEY', '"xxxxxxxxxxxxxxxxxxxxx"')
        it.buildConfigField('String', 'NOONPAY_ORDER_API', '"https://api.xxxxx/order"')
    }
•	'SPAY_SERVICE_ID=> get from samsungPay developer portal, as mentioned at previous screenshot
•	OONPAY_AUTH_SCHEME => based on target environment “KEY_TEST” or “KEY_LIVE” are eligible field value.
•	NOONPAY_BUSINESS_ID => contains three parts
o	Your business identifier provided by noonpay for your organization, you can get from my profile   https://portal.noonpayments.com/Account/MyProfile
 

o	. (DOT)
o	application title, from application management
 
Ex, myBusID.AdvancedDemo
•	NOONPAY_KEY=> Your application key value, from application management.
