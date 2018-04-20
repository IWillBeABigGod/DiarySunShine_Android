package com.jun.diarysunshine.util.http;

import rx.Observable;

/**
 * Created by jun on 2017/10/26.
 * 网络封装类
 */

public class ApiWrapper extends RetrofitUtil {

    /**
     * 获取验证码
     *
     * @param mobile 手机号码
     * @return
     */
    public Observable<Object> userGetCode(String mobile, String token) {
        return getService().userGetCode(mobile, token)
                .compose(this.<Object>applySchedulers());
    }
}
