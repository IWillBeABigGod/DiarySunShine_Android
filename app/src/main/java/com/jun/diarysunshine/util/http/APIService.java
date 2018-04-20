package com.jun.diarysunshine.util.http;

import retrofit2.http.Field;
import retrofit2.http.FormUrlEncoded;
import retrofit2.http.POST;
import rx.Observable;

/**
 * Created by jun on 2017/10/26.
 * 网络封装类
 */

public interface APIService {

    /**
     * 获取验证码
     *
     * @param mobile 手机号码
     * @return
     */
    @FormUrlEncoded
    @POST(ApiConstant.USER_GET_CODE)
    Observable<BaseResponse<Object>> userGetCode(@Field("mobile") String mobile, @Field("token") String token);
}
