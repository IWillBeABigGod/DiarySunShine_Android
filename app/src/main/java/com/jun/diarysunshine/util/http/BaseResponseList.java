package com.jun.diarysunshine.util.http;

import com.jun.diarysunshine.util.ToastUtil;
import java.io.Serializable;
import java.util.List;

/**
 * Created by jun on 2017/10/27.
 * 结果直接是一个JsonArray
 */

public class BaseResponseList <T> implements Serializable {
    public String msg;
    public String code;
    public List<T> result;

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

    public List<T> getResult() {
        return result;
    }

    public void setResult(List<T> result) {
        this.result = result;
    }
}