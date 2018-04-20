package com.jun.diarysunshine.util.http;

import com.jun.diarysunshine.util.ToastUtil;
import java.io.Serializable;

/**
 * Created by jun on 2017/10/26.
 * 网络请求返回基类
 */

public class BaseResponse<T> implements Serializable {
    public String msg;
    public String code;
    public T result;

    public boolean success() {
        if (code != null) {
            if ("100".equals(code)) {
                return true;
            } else if ("0".equals(code)) {
                ToastUtil.showShort("网络数据加载失败！");
                return false;
            }
        }
        return true;
    }

    @Override
    public String toString() {
        return "BaseRespose{" +
                "msg='" + msg + '\'' +
                ", code='" + code + '\'' +
                ", result=" + result +
                '}';
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }

    public T getResult() {
        return result;
    }

    public void setResult(T result) {
        this.result = result;
    }
}
