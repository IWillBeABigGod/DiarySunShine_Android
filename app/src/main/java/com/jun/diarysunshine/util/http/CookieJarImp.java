package com.jun.diarysunshine.util.http;

import android.support.v4.util.ArrayMap;

import com.jun.diarysunshine.util.CommonUtil;
import com.jun.diarysunshine.util.ShareUtil;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.util.ArrayList;
import java.util.List;
import java.util.Locale;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;

/**
 * Created by jun on 2017/10/26
 * 实现cookie的获取与保存
 */

public class CookieJarImp implements CookieJar{

    private final ArrayMap<String, List<Cookie>> cookieStore = new ArrayMap<>();

    @Override
    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
        if(CommonUtil.isNotEmpty(cookies)){
//            ACache.get(MainApplication.mContext).put("cookie",encodeCookie(new OkHttpCookies(cookies.get(0))));
            ShareUtil.setPreferStr("cookie",encodeCookie(new OkHttpCookies(cookies.get(0))));
//            Constant.COOKIE = encodeCookie(new OkHttpCookies(cookies.get(0)));
        }
    }

    @Override
    public List<Cookie> loadForRequest(HttpUrl url) {
        List<Cookie> cookies = new ArrayList<>();
//        String cookieString = ACache.get(MainApplication.mContext).getAsString("cookie");
        String cookieString = ShareUtil.getPreferStr("cookie");
        if(CommonUtil.isNotBlank(cookieString)){
            Cookie cookie = decodeCookie(cookieString);
            cookies.add(cookie);
            return cookies;
        }else {
            return new ArrayList<>();
        }
    }


    /**
     * cookies 序列化成 string
     *
     * @param cookie 要序列化的cookie
     * @return 序列化之后的string
     */
    protected String encodeCookie(OkHttpCookies cookie) {
        if (cookie == null)
            return null;
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        try {
            ObjectOutputStream outputStream = new ObjectOutputStream(os);
            outputStream.writeObject(cookie);
        } catch (IOException e) {
            return null;
        }

        return byteArrayToHexString(os.toByteArray());
    }

    /**
     * 将字符串反序列化成cookies
     *
     * @param cookieString cookies string
     * @return cookie object
     */
    protected Cookie decodeCookie(String cookieString) {
        byte[] bytes = hexStringToByteArray(cookieString);
        ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(bytes);
        Cookie cookie = null;
        try {
            ObjectInputStream objectInputStream = new ObjectInputStream(byteArrayInputStream);
            cookie = ((OkHttpCookies) objectInputStream.readObject()).getCookies();
        } catch (IOException e) {
        } catch (ClassNotFoundException e) {
        }

        return cookie;
    }

    /**
     * 二进制数组转十六进制字符串
     *
     * @param bytes byte array to be converted
     * @return string containing hex values
     */
    protected String byteArrayToHexString(byte[] bytes) {
        StringBuilder sb = new StringBuilder(bytes.length * 2);
        for (byte element : bytes) {
            int v = element & 0xff;
            if (v < 16) {
                sb.append('0');
            }
            sb.append(Integer.toHexString(v));
        }
        return sb.toString().toUpperCase(Locale.US);
    }

    /**
     * 十六进制字符串转二进制数组
     *
     * @param hexString string of hex-encoded values
     * @return decoded byte array
     */
    protected byte[] hexStringToByteArray(String hexString) {
        int len = hexString.length();
        byte[] data = new byte[len / 2];
        for (int i = 0; i < len; i += 2) {
            data[i / 2] = (byte) ((Character.digit(hexString.charAt(i), 16) << 4) + Character.digit(hexString.charAt(i + 1), 16));
        }
        return data;
    }
}
