package frame.base.com.publicutils;

import android.content.Context;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;

/**
 * app配置
 *
 * @author hantao
 */
public final class AppConfig {

    /**
     * (调试|正式)版本(正式为true)
     */
    public static boolean RELEASE = false;

    /**
     * 添加Bridge广告
     */
    public static boolean HAS_BRIDGE_AD = true;

    /**
     * 当前应用版本号
     */
    public static String APP_VERSION(Context context) {
        PackageInfo pinfo = null;
        try {
            pinfo = context.getPackageManager().getPackageInfo(context.getPackageName(), PackageManager.GET_CONFIGURATIONS);
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return pinfo == null ? "1.0.001" : pinfo.versionName;
    }
}
