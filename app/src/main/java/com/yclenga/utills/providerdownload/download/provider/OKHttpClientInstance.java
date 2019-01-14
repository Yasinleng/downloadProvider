package com.yclenga.utills.providerdownload.download.provider;

import okhttp3.OkHttpClient;

/**
 * Created by yclenga on 2017/11/24.
 * yclenga@isoftstone.com
 */

public class OKHttpClientInstance {
    private static OKHttpClientInstance sInstance;

    private OkHttpClient mOkHttpClient;

    public synchronized static OKHttpClientInstance getInstance() {
        if (sInstance == null) {
            sInstance = new OKHttpClientInstance();
        }
        return sInstance;
    }

    private OKHttpClientInstance() {
        mOkHttpClient = new OkHttpClient();
    }

    public OkHttpClient client() {
        return mOkHttpClient;
    }
}
