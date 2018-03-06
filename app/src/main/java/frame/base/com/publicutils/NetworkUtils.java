package frame.base.com.publicutils;

import android.app.Activity;
import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.net.NetworkInfo.State;
import android.telephony.TelephonyManager;

public class NetworkUtils {
	
	public static final String TAG = "NetworkUtils" ;
	
    public static final int NETWORN_NONE = 0;
    public static final int NETWORN_WIFI = 1;
    public static final int NETWORN_MOBILE = 2;
    
    /**
     * wifi网络
     */
    public static final String TYPE_WIFI="wifi";
    /**
     * 3G网络
     */
    public static final String TYPE_3G="3g";
    /**
     * 无网络连接
     */
    public static final String TYPE_NONE="none";
    
    /**
     * 中国移动
     */
    public static final String CHINA_MOBILE="移动";
    /**
     * 中国联通
     */
    public static final String CHINA_UNION="联通";
    /**
     * 中国电信
     */
    public static final String CHINA_CT="电信";
    
    /**
     * 获取网络类型
     * @param context
     * @return
     */
    public static int getNetworkState(Context context){
		if(null==context){
			return NETWORN_NONE;
		}
        ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);

        NetworkInfo wifiNetworkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
        //Wifi
        if (wifiNetworkInfo != null) {
        	State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        	if(  state == State.CONNECTED||state == State.CONNECTING){
        		return NETWORN_WIFI;
        	}
		}
        
        NetworkInfo g3NetWorkInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
        //3G
        if (g3NetWorkInfo != null) {
        	State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        	if(state == State.CONNECTED||state == State.CONNECTING){
        		return NETWORN_MOBILE;
        	}
		}
        
        return NETWORN_NONE;
    }
    
    
    
    
    /***
     * 获取网络类型名称
     * @param context
     * @return
     */
    public static String getNetworkTypeName(Context context){
    	ConnectivityManager connManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
    	//Wifi
    	NetworkInfo wifiInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI);
    	if (wifiInfo!=null) {
    		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_WIFI).getState();
        	if(state == State.CONNECTED||state == State.CONNECTING){
        		return TYPE_WIFI;
        	}
		}
    	//3G
    	NetworkInfo mobileInfo = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE);
    	if (mobileInfo!=null) {
    		State state = connManager.getNetworkInfo(ConnectivityManager.TYPE_MOBILE).getState();
        	if(state == State.CONNECTED||state == State.CONNECTING){
        		return TYPE_3G;
        	}
		}
    	return TYPE_NONE;
    }
    
    /***
     * 获取网络运营商
     * @param mContext
     * @return
     */
	public static String getNetOperatorName(Activity mContext) {
		TelephonyManager telephonyManager = (TelephonyManager) mContext
				.getSystemService(Context.TELEPHONY_SERVICE);
		String operator = telephonyManager.getSimOperator();
		if (operator != null) {
			if (operator.equals("46000") || operator.equals("46002")) {
				// operatorName="中国移动";
				// Toast.makeText(this, "此卡属于(中国移动)",
				// Toast.LENGTH_SHORT).show();
				return CHINA_MOBILE;
			} else if (operator.equals("46001")) {
				// operatorName="中国联通";
				// Toast.makeText(this, "此卡属于(中国联通)",
				// Toast.LENGTH_SHORT).show();
				return CHINA_UNION;
			} else if (operator.equals("46003")) {
				// operatorName="中国电信";
				// Toast.makeText(this, "此卡属于(中国电信)",
				// Toast.LENGTH_SHORT).show();
				return CHINA_CT;
			}
		}
		return "";
	}
    
    
    
    
    
    
}
