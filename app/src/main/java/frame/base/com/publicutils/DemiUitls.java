package frame.base.com.publicutils;

import android.app.Activity;
import android.content.Context;

/**
 * 这个工具类用来说明一些尺寸通用操作
 *
 * @author liuguoyan
 * @date 2014-8-31
 */
public final class DemiUitls {
    //如果content为null肯定会崩溃，空指针异常，空余时间最好改成application获取保险。
    public static int dip2px(Context context, float dpValue) {
        if (context == null) {
            return 1;
        } else {
            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (dpValue * scale + 0.5f);
        }
    }


    public static int px2dip(Context context, float pxValue) {
        if (context == null) {
            return 1;
        } else {

            final float scale = context.getResources().getDisplayMetrics().density;
            return (int) (pxValue / scale + 0.5f);
        }
    }

    public static int getScreenWidth(Activity context) {
        if (context == null) {
            return 1;
        } else {

            return context.getWindowManager().getDefaultDisplay().getWidth();

        }
    }

    public static int getScreenHeight(Activity context) {
        if (context == null) {
            return 1;
        } else {

            return context.getWindowManager().getDefaultDisplay().getHeight();
        }
    }


}
