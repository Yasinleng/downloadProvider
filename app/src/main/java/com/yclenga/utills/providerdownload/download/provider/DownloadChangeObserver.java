package com.yclenga.utills.providerdownload.download.provider;

import android.database.ContentObserver;
import android.os.Handler;
import android.util.Log;

/**
 * Created by yclenga on 2017/11/24.
 * yclenga@isoftstone.com
 */

public class DownloadChangeObserver extends ContentObserver {

    private static final String TAG = "DownloadChangeObserver";
    /**
     * Creates a content observer.
     *
     * @param handler The handler to run {@link #onChange} on, or null if none.
     */
    public DownloadChangeObserver(Handler handler) {
        super(handler);
    }

    @Override
    public void onChange(boolean selfChange) {
        super.onChange(selfChange);

//        Log.i(TAG, "onChange: 实时监听"+Thread.currentThread().getName());

    }
}
