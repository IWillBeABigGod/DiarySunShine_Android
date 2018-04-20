package com.jun.diarysunshine.util.http;

import android.app.Activity;
import android.content.Context;
import com.jun.diarysunshine.MainApplication;
import com.jun.diarysunshine.R;
import com.jun.diarysunshine.util.ToastUtil;

import rx.Subscriber;

/**
 * Created by jun on 2017/10/26.
 * 订阅封装
 */

public abstract class RxSubscriber<T> extends Subscriber<T> {

    private Context mContext;
    private String msg;
    private boolean showDialog = true;
    private LoadingDialogNew loadingDialog;

    /**
     * 是否显示浮动dialog
     */
    public void showDialog() {
        this.showDialog = true;
    }

    public void hideDialog() {
        this.showDialog = false;
    }

    public RxSubscriber(Context context, String msg, boolean showDialog) {
        this.mContext = context;
        this.msg = msg;
        this.showDialog = showDialog;
    }

    public RxSubscriber(Context context) {
        this(context, MainApplication.getAppContext().getString(R.string.loading), true);
    }

    public RxSubscriber(Context context, boolean showDialog) {
        this(context, MainApplication.getAppContext().getString(R.string.loading), showDialog);
    }

    @Override
    public void onCompleted() {
        if (showDialog)
            loadingDialog.cancelDialogForLoading();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (showDialog) {
            try {
                loadingDialog = new LoadingDialogNew();
                loadingDialog.showDialogForLoading((Activity) mContext, msg, true);
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
    }


    @Override
    public void onNext(T t) {
        onSucess(t);
    }

    @Override
    public void onError(Throwable e) {
        if (showDialog)
            loadingDialog.cancelDialogForLoading();
        e.printStackTrace();
        onError();
        //网络
        if (!NetWorkUtils.isNetConnected(MainApplication.getAppContext())) {
            onFail(MainApplication.getAppContext().getString(R.string.no_net));
//            ToastUtil.showToastNetError();
        }
        //服务器
        else if (e instanceof ServerException) {
            onFail(e.getMessage());
        }
        //其它
        else {
            onFail(MainApplication.getAppContext().getString(R.string.net_error));
        }
    }

    protected abstract void onSucess(T t);

    protected abstract void onError();

    protected void onFail(String message) {
        ToastUtil.showShort(message);
    }

}
