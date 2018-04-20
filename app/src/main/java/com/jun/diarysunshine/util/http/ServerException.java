package com.jun.diarysunshine.util.http;

/**
 * Created by jun on 2017/10/26.
 * 服务器请求异常
 */

public class ServerException extends Exception{

    public ServerException(String message) {
        super(message);
    }
}
