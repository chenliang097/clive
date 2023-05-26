package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.telephony.TelephonyManager;

import com.rongtuoyouxuan.chatlive.stream.R;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class NetworkUtil {
    private static final int TYPE_WIFI = 1;
    private static final int TYPE_MOBILE = 2;
    private static final int TYPE_NOT_CONNECTED = 0;

    /**
     * 检测网络是否连接
     *
     * @return
     */
    public static boolean isNetConnected(Context context) {
        if (context == null)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo[] infos = cm.getAllNetworkInfo();
            if (infos != null) {
                for (NetworkInfo ni : infos) {
                    if (ni.isConnected()) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /**
     * 检测wifi是否连接
     *
     * @return
     */
    public static boolean isWifiConnected(Context context) {
        if (context == null)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_WIFI) {
                return true;
            }
        }
        return false;
    }

    /**
     * 检测3G是否连接
     *
     * @return
     */
    public static boolean is3gConnected(Context context) {
        if (context == null)
            return false;
        ConnectivityManager cm = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        if (cm != null) {
            NetworkInfo networkInfo = cm.getActiveNetworkInfo();
            if (networkInfo != null
                    && networkInfo.getType() == ConnectivityManager.TYPE_MOBILE) {
                return true;
            }
        }
        return false;
    }

    public static NetworkUtil.NetState getNetStateType(NetworkInfo ni) {
        NetworkUtil.NetState stateCode = NetworkUtil.NetState.NET_UNKNOWN;
        switch (ni.getType()) {
            case ConnectivityManager.TYPE_WIFI:
                stateCode = NetworkUtil.NetState.NET_WIFI;
                break;
            case ConnectivityManager.TYPE_MOBILE:
                switch (ni.getSubtype()) {
                    case TelephonyManager.NETWORK_TYPE_GPRS: //联通2g
                    case TelephonyManager.NETWORK_TYPE_CDMA: //电信2g
                    case TelephonyManager.NETWORK_TYPE_EDGE: //移动2g
                    case TelephonyManager.NETWORK_TYPE_1xRTT:
                    case TelephonyManager.NETWORK_TYPE_IDEN:
                        stateCode = NetworkUtil.NetState.NET_2G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_EVDO_A: //电信3g
                    case TelephonyManager.NETWORK_TYPE_UMTS:
                    case TelephonyManager.NETWORK_TYPE_EVDO_0:
                    case TelephonyManager.NETWORK_TYPE_HSDPA:
                    case TelephonyManager.NETWORK_TYPE_HSUPA:
                    case TelephonyManager.NETWORK_TYPE_HSPA:
                    case TelephonyManager.NETWORK_TYPE_EVDO_B:
                    case TelephonyManager.NETWORK_TYPE_EHRPD:
                    case TelephonyManager.NETWORK_TYPE_HSPAP:
                        stateCode = NetworkUtil.NetState.NET_3G;
                        break;
                    case TelephonyManager.NETWORK_TYPE_LTE:
                        stateCode = NetworkUtil.NetState.NET_4G;
                        break;
                    default:
                        stateCode = NetworkUtil.NetState.NET_UNKNOWN;
                }
                break;
            default:
                stateCode = NetworkUtil.NetState.NET_UNKNOWN;
        }
        return stateCode;
    }

    public static boolean CheckWifiNetworkState(Context context) {
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            NetworkUtil.NetState state = getNetStateType(info);
            if (state != NetworkUtil.NetState.NET_WIFI) {
                return false;
            }
        }
        return true;
    }

    public static int GetNetworkType(Context context) {
        //unknown=-1; no=0; wifi=1; 2g=2; 3g=3; 4g=4;
        int type = -1;
        ConnectivityManager connectivityManager = (ConnectivityManager) context.getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo info = connectivityManager.getActiveNetworkInfo();
        if (info != null && info.isAvailable()) {
            NetworkUtil.NetState state = getNetStateType(info);
            if (state == NetworkUtil.NetState.NET_NO)
                type = 0;
            else if (state == NetworkUtil.NetState.NET_WIFI)
                type = 1;
            else if (state == NetworkUtil.NetState.NET_2G)
                type = 2;
            else if (state == NetworkUtil.NetState.NET_3G)
                type = 3;
            else if (state == NetworkUtil.NetState.NET_4G)
                type = 4;
            else if (state == NetworkUtil.NetState.NET_UNKNOWN)
                type = -1;
        }
        return type;
    }

    public static String getPhoneImsi(Context context) {
        TelephonyManager telManager = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
        String imsi = telManager.getSubscriberId();
        if (imsi != null) {
            if (imsi.length() >= 5) {
                return imsi.substring(0, 5);
            } else {
                return imsi;
            }
        }
        return "";
    }

    private static int getConnectivityStatus(Context context) {
        ConnectivityManager cm = (ConnectivityManager) context.getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo activeNetwork = cm.getActiveNetworkInfo();
        if (null != activeNetwork) {
            if (activeNetwork.getType() == ConnectivityManager.TYPE_WIFI) return TYPE_WIFI;
            if (activeNetwork.getType() == ConnectivityManager.TYPE_MOBILE) return TYPE_MOBILE;
        }
        return TYPE_NOT_CONNECTED;
    }

    /**
     * 当前网络是否连接
     *
     * @param context Context
     * @return boolean
     */
    public static boolean netWorkIsConnected(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        return conn == NetworkUtil.TYPE_WIFI || conn == NetworkUtil.TYPE_MOBILE;
    }

    public static String getConnectivityStatusString(Context context) {
        int conn = NetworkUtil.getConnectivityStatus(context);
        String status = null;
        if (conn == NetworkUtil.TYPE_WIFI) {
            status = "";
        } else if (conn == NetworkUtil.TYPE_MOBILE) {
            status = "";
        } else if (conn == NetworkUtil.TYPE_NOT_CONNECTED) {
            status = context.getString(R.string.pl_libutil_network_not_connected);
        }
        return status;
    }

    public static String ping(String host)
    {
        String command = createSimplePingCommand(3, host);
        Process process = null;
        try
        {
            process = Runtime.getRuntime().exec(command);
            InputStream is = process.getInputStream();
            BufferedReader reader = new BufferedReader(new InputStreamReader(is));
            StringBuilder sb = new StringBuilder();
            String line;
            while (null != (line = reader.readLine()))
            {
                sb.append(line);
                sb.append("\n");
            }
            reader.close();
            is.close();
            return sb.toString();
        }
        catch (IOException e)
        {
            e.printStackTrace();
        }
        finally
        {
            if (null != process)
            {
                process.destroy();
            }
        }
        return null;
    }

    /**
     * 判断网络是否连接
     */
    public static Boolean isNetworkConnected(Context context) {
        ConnectivityManager manager = (ConnectivityManager) context
                .getApplicationContext().getSystemService(Context.CONNECTIVITY_SERVICE);
        if (manager == null) {
            return false;
        }
        NetworkInfo networkinfo = manager.getActiveNetworkInfo();
        if (networkinfo == null || !networkinfo.isAvailable()) {
            return false;
        }
        return true;
    }

    private static String createSimplePingCommand(int count, String domain) {
        return "/system/bin/ping -c " + count + " " + domain;
    }

    private enum NetState {NET_NO, NET_2G, NET_3G, NET_4G, NET_WIFI, NET_UNKNOWN}
}
