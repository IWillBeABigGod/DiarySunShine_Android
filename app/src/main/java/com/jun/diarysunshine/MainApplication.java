package com.jun.diarysunshine;

import android.app.Application;
import android.content.Context;
import android.content.res.Resources;

/**
 * Created by jun on 2018/4/18.
 */

public class MainApplication extends Application {

    private static MainApplication mainApplication;

    @Override
    public void onCreate() {
        super.onCreate();
        mainApplication = this;
    }

    /**
     * 获取全局context
     * @return
     */
    public static Context getAppContext() {
        return mainApplication;
    }

    /**
     * 获取资源
     * @return
     */
    public static Resources getAppResources() {
        return mainApplication.getResources();
    }
}
