package com.jun.diarysunshine.util.http;


import com.jun.diarysunshine.MainApplication;
import com.jun.diarysunshine.util.AppLog;

import java.io.File;

import okhttp3.Cache;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.RequestBody;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava.RxJavaCallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;
import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by jun on 2017/10/26.
 */

public class RetrofitUtil {

    /**
     * 服务器地址
     */
    public static String API_HOST = "";

    private static APIService service;
    private static Retrofit retrofit;

    public static APIService getService() {
        if (service == null) {
            service = getRetrofit().create(APIService.class);
        }
        return service;
    }

    protected static Retrofit getRetrofit() {
        if (retrofit == null) {
            HttpLoggingInterceptor interceptor = new HttpLoggingInterceptor(new HttpLoggingInterceptor.Logger() {
                @Override
                public void log(String message) {
                    AppLog.logi(message);
                }
            });
            interceptor.setLevel(HttpLoggingInterceptor.Level.BASIC);
            //网络缓存路径文件
            File httpCacheDirectory = new File(MainApplication.getAppContext().getExternalCacheDir(), "http");
            //通过拦截器设置缓存，暂未实现
            //CacheInterceptor cacheInterceptor = new CacheInterceptor();
            OkHttpClient client = new OkHttpClient.Builder()
                    //设置cook的保存与提交 暂时没有
                    .cookieJar(new CookieJarImp())
                    //设置缓存
                    .cache(new Cache(httpCacheDirectory, 10 * 1024 * 1024))
                    //log请求参数
                    .addInterceptor(new RequestInterceptor())
                    .addInterceptor(interceptor)
                    //网络请求缓存，未实现
                    //    .addInterceptor(cacheInterceptor)
                    .build();
            retrofit = new Retrofit.Builder()
                    .client(client)
                    .baseUrl(API_HOST)
                    .addConverterFactory(GsonConverterFactory.create())
                    .addCallAdapterFactory(RxJavaCallAdapterFactory.create())
                    .build();
        }
        return retrofit;
    }

    /**
     * 对网络接口返回的Response进行分割操作
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponse(final BaseResponse<T> response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {

                AppLog.logd("msg = " + response.getMsg() + "    code = " + response.getCode());
                if (response.success()) {
//                    AppLog.iJsonFormat("", JSON.toJSONString(response),true);
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onNext(response.getResult());
                    }
                } else {
                    if (!subscriber.isUnsubscribed()) {
                        subscriber.onError(new ServerException(response.getMsg()));
                    }
                    return;
                }

                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }

            }
        });
    }

    /**
     * 用于处理result为数组的数据结构
     *
     * @param response
     * @param <T>
     * @return
     */
    public <T> Observable<T> flatResponseList(final T response) {
        return Observable.create(new Observable.OnSubscribe<T>() {

            @Override
            public void call(Subscriber<? super T> subscriber) {
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onNext(response);
                }
                if (!subscriber.isUnsubscribed()) {
                    subscriber.onCompleted();
                }
            }
        });
    }


    protected <T> Observable.Transformer<BaseResponse<T>, T> applySchedulers() {
        return (Observable.Transformer<BaseResponse<T>, T>) transformer;
    }


    final Observable.Transformer transformer = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponse((BaseResponse<Object>) response);
                        }
                    })
                    ;
        }
    };

    protected <T> Observable.Transformer<T, T> applySchedulersList() {
        return (Observable.Transformer<T, T>) transformerList;
    }


    final Observable.Transformer transformerList = new Observable.Transformer() {
        @Override
        public Object call(Object observable) {
            return ((Observable) observable).subscribeOn(Schedulers.io())
                    .observeOn(AndroidSchedulers.mainThread())
                    .flatMap(new Func1() {
                        @Override
                        public Object call(Object response) {
                            return flatResponseList(response);
                        }
                    })
                    ;
        }
    };

    /**
     * 当{@link APIService}中接口的注解为{@link retrofit2.http.Multipart}时，参数为{@link RequestBody}
     * 生成对应的RequestBody
     *
     * @param param
     * @return
     */
    protected RequestBody createRequestBody(int param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(long param) {
        return RequestBody.create(MediaType.parse("text/plain"), String.valueOf(param));
    }

    protected RequestBody createRequestBody(String param) {
        return RequestBody.create(MediaType.parse("text/plain"), param);
    }

    protected RequestBody createRequestBody(File param) {
        return RequestBody.create(MediaType.parse("image/*"), param);
    }

}
