package com.jun.diarysunshine.util;

import android.app.Activity;
import android.app.DialogFragment;
import android.app.Fragment;
import android.app.FragmentTransaction;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.util.DisplayMetrics;
import android.view.View;
import android.view.WindowManager;

import com.google.gson.Gson;
import com.jun.diarysunshine.MainActivity;
import com.jun.diarysunshine.MainApplication;

import java.text.DecimalFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;


/**
 * Created by junjun on 2017/10/26 0025.
 */

public class CommonUtil {

    /**
     * 将.01等类似的数字转变为xx.xx的标准型数字
     */
    public static String toFormatDouble(String numbleString) {
        String ss = numbleString.replaceAll(",", "");
        DecimalFormat decimalFormat = new DecimalFormat("#0.00");
        String format = decimalFormat.format(Double.parseDouble(ss));
        return format;
    }

    /**
     * 判断list是否为空
     *
     * @param list
     * @return
     */
    public static boolean isNotEmpty(List list) {
        if (list != null && list.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    public static boolean isEmpty(List list) {
        return !isNotEmpty(list);
    }

    /**
     * 控制view3s不可点击
     *
     * @param view
     */
    public static void controlView(View view) {
        view.post(() -> view.setEnabled(false));
        view.postDelayed(() ->
        {
            if (view != null) {
                view.setEnabled(true);
            }
        }, 3000);
    }

    /**
     * 判断这个字符串是否为空
     *
     * @return
     */
    public static boolean isBlank(String str) {
        if (null != str) {
            if (str.trim().length() == 0) {
                return true;
            } else {
                if (str.equalsIgnoreCase("null")) {
                    return true;
                }
                return false;
            }
        } else {
            return true;
        }
    }

    /**
     * 判断这个字符串是否不为空
     *
     * @return
     */
    public static boolean isNotBlank(String str) {
        return !isBlank(str);
    }

    /**
     * 适配android 6.0 检查权限
     * android 6.0 的新特性，当APP需要使用一些敏感权限时，会对用户进行提示，同时代码中也要做相应处理
     */
    public static boolean checkPermission(String permission, int permissionCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MainApplication.getAppContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                ActivityCompat.requestPermissions((Activity) MainApplication.getAppContext(), new String[]{permission}, permissionCode);
            }
            return (ContextCompat.checkSelfPermission(MainApplication.getAppContext(), permission) ==
                    PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    /**
     * 适配android 6.0 检查权限
     * android 6.0 的新特性，当APP需要使用一些敏感权限时，会对用户进行提示，同时代码中也要做相应处理
     */
    public static boolean checkPermission(Fragment fragment, String permission, int permissionCode) {
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(MainApplication.getAppContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                //申请权限
                fragment.requestPermissions(new String[]{permission}, permissionCode);
            }
            return (ContextCompat.checkSelfPermission(MainApplication.getAppContext(), permission) ==
                    PackageManager.PERMISSION_GRANTED);
        }
        return true;
    }

    /**
     * 适配android 6.0 检查权限
     * android 6.0 的新特性，当APP需要使用一些敏感权限时，会对用户进行提示，同时代码中也要做相应处理
     */
    public static boolean checkPermission(Fragment fragment, String[] permissions, int permissionCode) {
        boolean result = true;
        List<String> tmpList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(MainApplication.getAppContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    tmpList.add(permission);
                    result = false;
                }
            }
            if (!result) {
                fragment.requestPermissions(tmpList.toArray(new String[tmpList.size()]), permissionCode);
            }
        }
        return result;
    }

    /**
     * 适配android 6.0 检查权限
     * android 6.0 的新特性，当APP需要使用一些敏感权限时，会对用户进行提示，同时代码中也要做相应处理
     */
    public static boolean checkPermission(String[] permissions, int permissionCode, Context context) {
        boolean result = true;
        List<String> tmpList = new ArrayList<>();
        if (Build.VERSION.SDK_INT >= 23) {
            for (String permission : permissions) {
                if (ContextCompat.checkSelfPermission(MainApplication.getAppContext(), permission) != PackageManager.PERMISSION_GRANTED) {
                    //申请权限
                    tmpList.add(permission);
                    result = false;
                }
            }
            if (!result) {
                ActivityCompat.requestPermissions((Activity) context, tmpList.toArray(new String[tmpList.size()]), permissionCode);
            }
        }
        return result;
    }

    /**
     * 根据检查Fragment是否存在
     *
     * @return
     */
    public static boolean checkFragmentExist(String tag, Activity activity) {
        boolean isExist;
        Fragment prev = activity.getFragmentManager().findFragmentByTag(tag);
        if (prev == null) {
            isExist = false;
        } else {
            if (prev.getActivity() != activity) {
                isExist = false;
            } else {
                isExist = true;
            }
        }
        return isExist;
    }

    /**
     * 显示DialogFragment
     *
     * @param dialogFragment
     * @param tag
     */
    public static void initDialogFragment(DialogFragment dialogFragment, String tag, Activity activity) {
        try {
            FragmentTransaction transaction = activity.getFragmentManager().beginTransaction();
            dialogFragment.show(transaction, tag);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static boolean isNumber(String string) {
        Pattern pattern = Pattern.compile("^[0-9]+(.[0-9]+)?$");
        Matcher isNum = pattern.matcher(string);
        if (isNum.matches()) {
            return true;
        }
        return false;
    }

    /**
     * 将08月01日转换为08-01
     *
     * @param date
     * @return
     */
    public static String changeDate(String date) {
        String replace = date.replace("月", "-").replace("日", "");
        return replace;
    }

    /**
     * @param json
     * @param clazz
     * @return
     * @author cgg
     */
    public static <T> List<T> jsonToList(String json, Class<T[]> clazz) {
        Gson gson = new Gson();
        T[] array = gson.fromJson(json, clazz);
        return Arrays.asList(array);
    }


//    /**
//     * 加载网络正常图片
//     *
//     * @param imageView
//     * @param url
//     */
//    public static void loadFromWeb(ImageView imageView, Object url) {
//
//        /**
//         * 加载网络图片
//         */
//        Glide.with(MainApplication.getAppContext()).load(ApiConstant.BASE_URL_IMG + url).diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.mipmap.default_logo2)
//                .error(R.mipmap.default_logo2)
//                .into(imageView);
//
//    }
//
//    /**
//     * 加载网络正常图片 可设置默认图片
//     *
//     * @param imageView
//     * @param url
//     */
//    public static void loadFromWeb(ImageView imageView, Object url, int resId) {
//
//        /**
//         * 加载网络图片
//         */
//        Glide.with(MainApplication.getAppContext()).load(ApiConstant.BASE_URL_IMG + url).diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(resId)
//                .error(resId)
//                .into(imageView);
//
//    }
//
//    /**
//     * 加载网络图片，处理成圆角图片
//     *
//     * @param imageView
//     * @param url
//     */
//    public static void loadFromWebWithRound(ImageView imageView, Object url, int radius) {
//
//        /**
//         * 加载网络图片
//         */
//        Glide.with(MainApplication.getAppContext()).load(ApiConstant.BASE_URL_IMG + url).diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .bitmapTransform(new RoundTransformation(MainApplication.getAppContext(),radius))
//                .placeholder(R.mipmap.default_logo2)
//                .error(R.mipmap.default_logo2)
//                .into(imageView);
//
//    }
//
//    /**
//     * 加载网络图片，处理成圆角图片
//     *
//     * @param imageView
//     * @param url
//     */
//    public static void loadFromWebWithRoundAndRes(ImageView imageView, Object url, int radius, int resId) {
//
//        /**
//         * 加载网络图片
//         */
//        Glide.with(MainApplication.getAppContext()).load(ApiConstant.BASE_URL_IMG + url).diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .bitmapTransform(new RoundTransformation(MainApplication.getAppContext(),radius))
//                .placeholder(resId)
//                .error(resId)
//                .into(imageView);
//
//    }
//
//
//    /**
//     * 加载网络正常图片 默认图片为长图
//     *
//     * @param imageView
//     * @param url
//     */
//    public static void loadFromWeb125(ImageView imageView, Object url) {
//
//        /**
//         * 加载网络图片
//         */
//        Glide.with(MainApplication.getAppContext()).load(ApiConstant.BASE_URL_IMG + url).diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(R.mipmap.default_long)
//                .error(R.mipmap.default_long)
//                .into(imageView);
//
//    }
//
//    /**
//     * 加载网络正常图片可变默认图
//     *
//     * @param imageView
//     * @param url
//     */
//    public static void loadFromWebPx(ImageView imageView, Object url, int resId) {
//
//        /**
//         * 加载网络图片
//         */
//        Glide.with(MainApplication.getAppContext()).load(ApiConstant.BASE_URL_IMG + url).diskCacheStrategy(DiskCacheStrategy.SOURCE)
//                .placeholder(resId)
//                .error(resId)
//                .into(imageView);
//
//    }
//
//    public static void loadImageWidth(Activity activity, ImageView imageView, String url) {
//        Glide.with(activity).load(ApiConstant.BASE_URL_IMG + url).asBitmap().diskCacheStrategy(DiskCacheStrategy.SOURCE).into(new SimpleTarget<Bitmap>(Target.SIZE_ORIGINAL, Target.SIZE_ORIGINAL) {
//            @Override
//            public void onResourceReady(Bitmap resource, GlideAnimation<? super Bitmap> glideAnimation) {
//                int imageWidth = resource.getWidth();
//                int imageHeight = resource.getHeight();
////                BitmapFactory.Options options = new BitmapFactory.Options();
////                options.inJustDecodeBounds = true;
////                int ratio = 1;
////                if (w > h && w > ww) {
////                    be = (int) (newOpts.outWidth / ww);
////                } else if (w < h && h > hh) {
////                    be = (int) (newOpts.outHeight / hh);
////                }
////                if (be <= 0) be = 1;
////                options.inJustDecodeBounds = false;
//                int height = getScreenWidth(activity) * imageHeight / imageWidth;
//                ViewGroup.LayoutParams para = imageView.getLayoutParams();
//                para.height = height;
//                para.width = getScreenWidth(activity);
//                imageView.setImageBitmap(resource);
//            }
//        });
//    }
//
//    /**
//     * 加载本地图片
//     *
//     * @param imageView
//     * @param path
//     */
//    public static void loadFromLocal(ImageView imageView, String path) {
//
//        /**
//         * 加载本地图片
//         */
//        File file = new File(path);
//        Glide.with(MainApplication.getAppContext()).load(file)
////                .error(R.drawable.img_default_page)
//                .into(imageView);
//
//    }


    /**
     * 根据手机的分辨率从 dp 的单位 转成为 px(像素)
     */
    public static int dip2px(float dpValue) {
        final float scale = MainApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }


    /**
     * 根据手机的分辨率从 px(像素) 的单位 转成为 dp
     */
    public static int px2dip(float pxValue) {
        final float scale = MainApplication.getAppContext().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    public static int getScreenWidth(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);

        return metric.widthPixels;  // 屏幕宽度（像素）
    }

    public static int getScreenHeight(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.heightPixels;  // 屏幕高度（像素）

    }

    public static float getScreenDensity(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.density;  // 屏幕密度（像素比例 0.75 / 1.0 / 1.5 / 2.0 / 2.5 / 3.0）
    }

    public static int getScreenDensityDpi(Activity activity) {
        DisplayMetrics metric = new DisplayMetrics();
        activity.getWindowManager().getDefaultDisplay().getMetrics(metric);
        return metric.densityDpi;  // 屏幕密度DPI（每寸像素 120 / 160 / 240 / 320 / 480 /640）
    }

    /**
     * 设置页面的透明度
     *
     * @param bgAlpha 1表示不透明
     */
    public static void setBackgroundAlpha(Activity activity, float bgAlpha) {
        WindowManager.LayoutParams lp = activity.getWindow().getAttributes();
        lp.alpha = bgAlpha;
//        if (bgAlpha == 1) {
//            activity.getWindow().clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//不移除该Flag的话,在有视频的页面上的视频会出现黑屏的bug
//        } else {
//            activity.getWindow().addFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND);//此行代码主要是解决在华为手机上半透明效果无效的bug
//        }
        activity.getWindow().setAttributes(lp);
    }

    /**
     * 将日期改为xx月xx日
     * @param date 2017-11-17
     */
    public static String changeDateToYD(String date){
        if(CommonUtil.isBlank(date)){
            return null;
        }
        String[] split = date.split("-");
        if(split.length == 3){
            return split[1] + "月" + split[2] + "日";
        }else {
            return null;
        }
    }

    public static void goToMainCart(Context context){
        Intent intent = new Intent(context, MainActivity.class);
        intent.putExtra("index","1");
        context.startActivity(intent);
        ((Activity)context).finish();
    }

//    /**
//     * 拨打电话
//     *
//     * @param phoneNum
//     */
//    public static void callPhone(Context context, String phoneNum) {
//        if (StrUtil.isBlank(phoneNum)) {
//            ToastUtil.showShort("电话号码不能为空");
//            return;
//        }
//        if (checkPermission(Manifest.permission.CALL_PHONE, Constant.REQUEST_SYSTEM_CALL_PHONE_CODE)) {
//            Intent intent = new Intent(Intent.ACTION_CALL);
//            intent.setData(Uri.parse("tel:" + phoneNum));
//            ((Activity)context).startActivity(intent);
//        }
//    }

    /**
     * get App versionName
     * @param context
     * @return
     */
    public static String getVersionName(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        String versionName="";
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionName=packageInfo.versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionName;
    }

    /**
     * get App versionCode
     * @param context
     * @return
     */
    public static Integer getVersionCode(Context context){
        PackageManager packageManager=context.getPackageManager();
        PackageInfo packageInfo;
        Integer versionCode= null;
        try {
            packageInfo=packageManager.getPackageInfo(context.getPackageName(),0);
            versionCode=packageInfo.versionCode;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }
        return versionCode;
    }

    /**
     * 判断当前日期是星期几
     *
     * @param  pTime     设置的需要判断的时间 //格式如2012-09-08
     *
     * @return dayForWeek 判断结果
     * @Exception 发生异常
     */
    public static String getWeek(String pTime) {

        String Week = "周";
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        Calendar c = Calendar.getInstance();
        try {
            c.setTime(format.parse(pTime));
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SUNDAY) {
            Week += "日";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.MONDAY) {
            Week += "一";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.TUESDAY) {
            Week += "二";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.WEDNESDAY) {
            Week += "三";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.THURSDAY) {
            Week += "四";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.FRIDAY) {
            Week += "五";
        }
        if (c.get(Calendar.DAY_OF_WEEK) == Calendar.SATURDAY) {
            Week += "六";
        }

        return Week;
    }

//    public static void goToMainActivity(Context context){
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        context.startActivity(intent);
//    }
//
//    /**
//     * 其他页面跳转到购物车，此时购物车需要有返回键
//     * @param context
//     */
//    public static void goToMainCart(Context context){
//        Intent intent = new Intent(context, MainActivity.class);
//        intent.setFlags(Intent.FLAG_ACTIVITY_REORDER_TO_FRONT);
//        intent.putExtra("to_cart","1");
//        intent.putExtra("from_goods","1");
//        intent.putExtra("from_go","1");
//        intent.putExtra("index", "1");
//        context.startActivity(intent);
//    }
//
//    /**
//     * 处理点击返回键
//     */
//    public static void clickBack(Context context){
//        if(AppManager.getAppManager().getStackSize() == 1){
//            CommonUtil.goToMainActivity(context);
//            ((Activity)context).finish();
//        }else {
//            ((Activity)context).finish();
//        }
//    }
}
