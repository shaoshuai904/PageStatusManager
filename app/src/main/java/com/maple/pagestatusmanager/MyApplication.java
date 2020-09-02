package com.maple.pagestatusmanager;

import android.app.Application;

import com.maple.pagestatusmanager.utils.PageStatusManager;


/**
 * Created by zhy on 15/8/27.
 */
public class MyApplication extends Application {
    @Override
    public void onCreate() {
        super.onCreate();
        PageStatusManager.mBaseRetryLayoutId = R.layout.base_retry;
        PageStatusManager.mBaseLoadingLayoutId = R.layout.base_loading;
        PageStatusManager.mBaseEmptyLayoutId = R.layout.base_empty;
    }
}
