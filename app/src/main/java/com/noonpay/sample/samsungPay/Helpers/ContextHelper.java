package com.noonpay.sample.samsungPay.Helpers;

import android.content.Context;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;

/**
 * Created by abdo on 2/28/2018.
 */

public class ContextHelper {
    private final Context _context;
    public ContextHelper(final Context context) {
        this._context=context;
    }
    public String GetAppKey(String name) throws PackageManager.NameNotFoundException {
        final ApplicationInfo appInfo = _context.getPackageManager().getApplicationInfo(_context.getPackageName(), PackageManager.GET_META_DATA);
        if (appInfo.metaData != null) {
            return appInfo.metaData.getString(name);
        }
        throw new PackageManager.NameNotFoundException("meta-data not found");
    }
}
