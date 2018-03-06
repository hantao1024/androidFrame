/*
 * File Name: Connectivity.java 
 * History:
 * Created by Siyang.Miao on 2012-5-5
 */
package frame.base.com.publicutils;

import android.content.Context;
import android.database.Cursor;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.Proxy;
import android.net.Uri;
import android.net.wifi.WifiManager;
import android.text.TextUtils;


/**
 * 网络连接状态类
 * @author liuguoyan
 */
public class Connectivity {
    // ==========================================================================
    // Constants
    // ==========================================================================
    /** 移动，联通WAP代理IP */
    private final static String PROXY_CM_UNI = "10.0.0.172";
    /** 电信WAP代理IP */
    private final static String PROXY_CT = "10.0.0.200";

    public final static int CODE_NONE = 0;
    public final static int CODE_CM_UNI = 1;
    public final static int CODE_CT = 2;
    /*
     * 移动MNC编号 00 02 07
     */
    public final static String CM_MNC_1 = "00";
    public final static String CM_MNC_2 = "02";
    public final static String CM_MNC_7 = "07";
    /*
     * 电信MNC编号 00 02
     */
    public final static String CT_MNC = "03";
    /*
     * 联通MNC编号 00 02
     */
    public final static String UNI_MNC = "01";

    public final static String SIMPLE_NETTYPE_WIFI = "wifi";

    public final static String SIMPLE_NETTYPE_NET = "net";

    public final static String SIMPLE_NETTYPE_WAP = "wap";

    /*
     * 网络状态类型: 1-移动WAP，2－电信WAP，3－联通WAP，4－3G,5－WIFI，0－其它，-1－不可用
     */
    public static enum NetType {
        NONE, OTHER, CMWAP, CTWAP, UNIWAP, NET, WIFI
    }

    // ==========================================================================
    // Fields
    // ==========================================================================
    private static Connectivity sInstance;

    private Context mContext;

    private ConnectivityManager mConnManager;

    private WifiManager mWifiManager;

    // ==========================================================================
    // Constructors
    // ==========================================================================

    // ==========================================================================
    // Getters
    // ==========================================================================

    // ==========================================================================
    // Setters
    // ==========================================================================
    private Connectivity(Context context) {
        this.mContext = context;
        mWifiManager = (WifiManager) mContext.getSystemService(Context.WIFI_SERVICE);
    }

    // ==========================================================================
    // Methods
    // ==========================================================================
    public static Connectivity getInstance(Context context) {
        if (sInstance == null)
            sInstance = new Connectivity(context);
        return sInstance;
    }

    private ConnectivityManager getConnManager() {
        if (mConnManager == null) {
            Object service = mContext.getSystemService(Context.CONNECTIVITY_SERVICE);
            if (null != service) {
                mConnManager = (ConnectivityManager) service;
            }
        }
        return mConnManager;
    }

    private NetworkInfo getActiveNetworkInfo() {
        ConnectivityManager connManager = getConnManager();
        return connManager == null ? null : connManager.getActiveNetworkInfo();
    }

    public boolean isMobileAvailable() {
        NetworkInfo netInfo = getActiveNetworkInfo();
        return (netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_MOBILE);
    }

    /**
     * 判断WIFI是否可用
     */
    public boolean isWifiAvailable() {
        NetworkInfo netInfo = getActiveNetworkInfo();
        return ((netInfo != null && netInfo.getType() == ConnectivityManager.TYPE_WIFI) && mWifiManager.isWifiEnabled());
    }

    /**
     * 获取ApnInfo网络状态
     */
    public ApnInfo getCurrentApnInfo() {
        ApnInfo apnObj;
        NetworkInfo netInfo = getActiveNetworkInfo();
        if (netInfo != null) {
            if (netInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                // Wifi网络
                apnObj = new ApnInfo();
                apnObj.setNetType(NetType.WIFI);
            } else if (netInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                // 移动网络
                String apn = netInfo.getExtraInfo();
                String host = Proxy.getDefaultHost();
                int port = Proxy.getDefaultPort();
                LogUtils.i("Default proxy " + host + ", port " + port);

                Cursor cursor = null;
                Uri uri = Uri.parse("content://telephony/carriers/");
                try {
                    String where = String.format("apn='%s' AND %s ", apn,
                            host == null ? "(proxy IS NULL OR proxy = '')" : ("proxy ='" + host + "'"));
                    cursor = mContext.getContentResolver().query(uri, null, where, null, null);
                } catch (Exception e1) {
                    LogUtils.e(e1);
                }
                apnObj = makeApnParam(cursor);
                // 如果没有查询到匹配的apn，填充已有数据，并当做net网络，确保返回不为NULL
                if (apnObj == null) {
                    apnObj = new ApnInfo();
                    if (TextUtils.isEmpty(host)) {
                        // 如果用其他代理地址，当做net网络
                        apnObj.setNetType(NetType.NET);
                        apnObj.setExtraNetInfo(apn);
                    } else if (host.equals(PROXY_CM_UNI)) {
                        apnObj.setNetType(NetType.CMWAP);
                    } else if (host.equals(PROXY_CT)) {
                        apnObj.setNetType(NetType.CTWAP);
                    } else {
                        // 如果用其他代理地址，当做net网络
                        apnObj.setNetType(NetType.NET);
                        apnObj.setExtraNetInfo(apn);
                    }
                }
                // apnInfo.setPort(port);
                // apnInfo.setProxy(host);
                try {
                    if (cursor != null) {
                        cursor.close();
                    }
                } catch (Exception e) {
                    LogUtils.e(e);
                }
            } else {
                // 非Wifi、非Mobile的其他网络
                apnObj = new ApnInfo();
                apnObj.setNetType(NetType.OTHER);
                apnObj.setExtraNetInfo(netInfo.getType() + "," + netInfo.getTypeName());
            }
        } else {
            // 当前无可用网络连接
            apnObj = new ApnInfo();
            apnObj.setNetType(NetType.NONE);
        }
        return apnObj;
    }

    /**
     * 取得当前连接类型
     * 
     * @return
     */
    public NetType getCurrentNetType() {
        return getCurrentApnInfo().getNetType();
    }

    /**
     * 取得当前连接类型名称
     * 
     * @return
     */
    public String getCurrentNetTypeName() {
        return getCurrentApnInfo().getExtraNetInfo();
    }

    /**
     * 将Apn数据库记录解析为ApnInfo对象
     */
    private static ApnInfo makeApnParam(Cursor cur) {
        if (cur == null) {
            return null;
        }
        if (!cur.moveToFirst()) {
            return null;
        }
        ApnInfo apnObj = new ApnInfo();
        int colId;
        String proxy = null;
        int port = -1;
        String apn = null;
        String mnc = null;

        colId = cur.getColumnIndex("proxy");
        if (-1 != colId) {
            proxy = cur.getString(colId);
            proxy = convertProxy(proxy);
            apnObj.setProxy(proxy);
        } else {
            LogUtils.e("\"proxy\" column is not found in cursor!");
        }
        colId = cur.getColumnIndex("apn");
        if (-1 != colId) {
            apn = cur.getString(colId);
            apnObj.setApn(apn);
        } else {
            LogUtils.e("\"apn\" column is not found in cursor!");
        }

        colId = cur.getColumnIndex("mnc");
        if (-1 != colId) {
            mnc = cur.getString(colId);
            colId = cur.getColumnIndex("mcc");
            if (-1 != colId) {
                String mcc = cur.getString(colId);
                apnObj.setMcc(mcc + mnc);
            } else {
                LogUtils.e("\"mcc\" column is not found in cursor!");
            }
        } else {
            LogUtils.e("\"mnc\" column is not found in cursor!");
        }

        if (proxy != null) {
            colId = cur.getColumnIndex("port");
            if (-1 != colId) {
                port = cur.getInt(colId);
                apnObj.setPort(port);
            } else {
                LogUtils.e("\"port\" column is not found in cursor!");
            }

            if (proxy.equals(PROXY_CM_UNI)) {
                apnObj.setMnc(mnc);
                if (mnc != null) {
                    if ((mnc.equals(CM_MNC_1) || mnc.equals(CM_MNC_2) || mnc.equals(CM_MNC_7))) {
                        apnObj.setNetType(NetType.CMWAP);
                    } else {
                        apnObj.setNetType(NetType.UNIWAP);
                    }
                }
            } else if (proxy.equals(PROXY_CT)) {
                apnObj.setNetType(NetType.CTWAP);
            } else {
                // 如果用其他代理地址，当做net网络
                apnObj.setNetType(NetType.NET);
                apnObj.setExtraNetInfo(apn + ", " + proxy + ", " + port);
            }
        } else {
            // 无代理，可能是net网络
            apnObj.setNetType(NetType.NET);
            apnObj.setExtraNetInfo(apn);
        }
        return apnObj;
    }

    private static String convertProxy(final String proxy) {
        int[] mSeg;
        String proxyIP = proxy;
        try {
            if (proxyIP != null && proxyIP.length() > 10) {// 代理IP地址位数过长,进行格式转化
                mSeg = new int[4];
                String[] strSeg = proxyIP.split("\\.");
                if (4 == strSeg.length) {
                    for (int i = 0; i < 4; i++) {
                        mSeg[i] = Integer.parseInt(strSeg[i]);
                    }
                    proxyIP = new StringBuilder(16).append(mSeg[0]).append('.').append(mSeg[1]).append('.')
                            .append(mSeg[2]).append('.').append(mSeg[3]).toString();
                }
            }
        } catch (NumberFormatException e) {
            LogUtils.i("Proxy IP FormatException " + e.getMessage());
            proxyIP = proxy;
        }
        return proxyIP;
    }

    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================
}
