/*
 * File Name: ApnInfo.java 
 * History:
 * Created by Siyang.Miao on 2012-5-5
 */
package frame.base.com.publicutils;


/**
 * APN参数封装类
 * 
 * @author Siyang.Miao
 * @version 1
 */
public class ApnInfo {
    // ==========================================================================
    // Constants
    // ==========================================================================
	
    // ==========================================================================
    // Fields
    // ==========================================================================
    /** APN 名称 */
    private String apn;
    /** 代理IP */
    private String proxy;
    /** 端口号 */
    private int port;
    /** 网络类型 */
    private Connectivity.NetType netType;
    /** 网络类型补充信息 */
    private String extraNetInfo;
    /** MNC号 */
    private String mnc;
    /** MCC号 */
    private String mcc;
    
    // ==========================================================================
    // Constructors
    // ==========================================================================
    
    // ==========================================================================
    // Getters
    // ==========================================================================
    public String getApn() {
        return apn;
    }
    
    public String getProxy() {
        return proxy;
    }
    
    public int getPort() {
        return port;
    }
    
    public Connectivity.NetType getNetType() {
        return netType;
    }

    public String getExtraNetInfo() {
        if (null == extraNetInfo) {
            if (null != netType) {
                return netType.name();
            }
        }
        return extraNetInfo;
    }

    public String getMnc() {
        return mnc;
    }

    public String getMcc() {
        return mcc;
    }

    // ==========================================================================
    // Setters
    // ==========================================================================
    public void setApn(String apn) {
        this.apn = apn;
    }

    public void setProxy(String proxy) {
        this.proxy = proxy;
    }

    public void setPort(int port) {
        this.port = port;
    }

    public void setNetType(Connectivity.NetType netType) {
        this.netType = netType;
    }

    public void setExtraNetInfo(String extraNetInfo) {
        this.extraNetInfo = extraNetInfo;
    }

    public void setMnc(String mnc) {
        this.mnc = mnc;
    }

    public void setMcc(String mcc) {
        this.mcc = mcc;
    }

    // ==========================================================================
    // Methods
    // ==========================================================================

    // ==========================================================================
    // Inner/Nested Classes
    // ==========================================================================
}
