package com.jun.diarysunshine.util;


import android.text.TextUtils;
import android.util.Log;

/**
 * create by jun on 2017/10/26
 * 调试信息输出
 */

public class AppLog {
    public static boolean DEBUG_ENABLE = false;// 是否调试模式
    public static final String TAG = "com.sihaiyucang";
    private static final String AUTHOR = "HARLAN -->";

    /**
     * 在application调用初始化
     */
    public static void logInit(boolean debug) {
        DEBUG_ENABLE = debug;

    }

    public static void logd(String tag, String message) {
        if (DEBUG_ENABLE) {
            Log.d(tag, message);
        }
    }

    public static void logd(String message) {
        if (DEBUG_ENABLE) {
            Log.d(TAG, message);
        }
    }

    public static void loge(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Log.e(TAG, message + "    ::  " + args);
        }
    }

    public static void logi(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Log.i(TAG, message + "    ::  " + args);
        }
    }

    public static void logv(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Log.v(TAG, message + "    ::  " + args);
        }
    }

    public static void logw(String message, Object... args) {
        if (DEBUG_ENABLE) {
            Log.w(TAG, message + "    ::  " + args);
        }
    }

    /**
     * Json格式化输出
     *
     * @param tag
     * @param message                 内容
     * @param isOutputOriginalContent 是否输入原内容
     */
    public static void iJsonFormat(String tag, String message, boolean isOutputOriginalContent) {
        if (DEBUG_ENABLE && !TextUtils.isEmpty(message)) {
            if (isOutputOriginalContent)
                Log.i(TAG, tag +"   " + message);
            Log.i(TAG,"\n" + JsonUtils.format(JsonUtils.convertUnicode(message)));
        }
    }

    public static void logxml(String message) {
        if (DEBUG_ENABLE) {
            Log.i(TAG, message);
        }
    }

}
