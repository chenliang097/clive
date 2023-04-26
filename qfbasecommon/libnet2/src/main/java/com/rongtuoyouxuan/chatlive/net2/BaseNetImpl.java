package com.rongtuoyouxuan.chatlive.net2;

import static android.os.Build.VERSION_CODES.N;

import android.app.Application;
import android.content.Context;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.os.Build;
import android.os.Handler;
import android.os.Looper;
import com.blankj.utilcode.util.DeviceUtils;
import com.blankj.utilcode.util.Utils;
import com.rongtuoyouxuan.chatlive.cookie.ClearableCookieJar;
import com.rongtuoyouxuan.chatlive.cookie.PersistentCookieJar;
import com.rongtuoyouxuan.chatlive.cookie.cache.SetCookieCache;
import com.rongtuoyouxuan.chatlive.cookie.persistence.SharedPrefsCookiePersistor;
import com.rongtuoyouxuan.chatlive.gson.GsonSafetyUtils;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.util.EnvUtils;
import com.rongtuoyouxuan.chatlive.util.NetworkUtil;
import com.chuckerteam.chucker.api.ChuckerInterceptor;
import java.io.File;
import java.util.HashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import androidx.annotation.Nullable;
import okhttp3.Cache;
import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * Created by jinqinglin on 2018/2/8.
 */

public class BaseNetImpl {
    private final static int CACHE_SIZE_BYTES = 1024 * 1024 * 10;
    public static String CACHE_DIR = "";
    private static BaseNetImpl instance;
    private static boolean isProduct;//是否是正式环境
    protected Context mContext;
    private ExecutorService executor = Executors.newScheduledThreadPool(5);
    @Nullable
    private OkHttpClient okHttpClient;
    private Map<String, String> universalParams;
    private Handler mHandler;
    private RequestNetWorkInterceptor mInterceptor;
    private AtomicInteger mSequenceGenerator = new AtomicInteger();
    private PersistentCookieJar persistentCookieJar;
    private HookNetworkError hookNetworkError;

    public BaseNetImpl() {
        mHandler = new Handler(Looper.getMainLooper());
    }

    public static BaseNetImpl getInstance() {
        if (instance == null) {
            synchronized (BaseNetImpl.class) {
                if (instance == null)
                    instance = new BaseNetImpl();
            }
        }
        return instance;
    }

    public static void init(Context mContext, boolean isProduct) {
        if (mContext == null || !(mContext instanceof Application)) {
            throw new IllegalArgumentException("context can't null or context must application");
        }
        CACHE_DIR = mContext.getCacheDir().getAbsolutePath();
        BaseNetImpl.isProduct = isProduct;
        getInstance().setContext(mContext);
//        getInstance().getCookieJar(mContext);
    }

    public HookNetworkError getHookNetworkError() {
        return hookNetworkError;
    }

    public void setHookNetworkError(HookNetworkError hookNetworkError) {
        this.hookNetworkError = hookNetworkError;
    }

    public void clearCookie() {
        if (persistentCookieJar != null) {
            persistentCookieJar.clear();
        }
    }

    public Handler getHandler() {
        return mHandler;
    }

    public void setContext(Context context) {
        mContext = context;
    }

    public List<Cookie> getCookies() {
        return ((ClearableCookieJar) getOkHttpClient(true, new HashMap<String, String>()).cookieJar()).getCookies();
    }


    public CookieJar getCookieJar(Context context) {
        if (persistentCookieJar != null) {
            return persistentCookieJar;
        }
        persistentCookieJar = new PersistentCookieJar(new SetCookieCache(), new SharedPrefsCookiePersistor(context));
        return persistentCookieJar;
    }

    private OkHttpClient getOkHttpClient(boolean takeDefaultParams, HashMap<String, String> universalParams) {
        if (null != okHttpClient) return okHttpClient;

        OkHttpClient.Builder builder = new OkHttpClient.Builder();
        builder.connectTimeout(Constants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.readTimeout(Constants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        builder.writeTimeout(Constants.DEFAULT_MILLISECONDS, TimeUnit.MILLISECONDS);
        if (universalParams == null) {
            universalParams = new HashMap<>();
        }
        if (takeDefaultParams) {
            universalParams.putAll(getUniversalParams());
        }

        if (mInterceptor == null) {
            ULog.e("clll", "mInterceptor == null");
            mInterceptor = new RequestNetWorkInterceptor(mContext, universalParams);
        } else {
            ULog.e("clll", "mInterceptor != null");
            mInterceptor.addHeaderParam(universalParams);
        }
        builder.addInterceptor(mInterceptor);
//        builder.cookieJar(getCookieJar(mContext));
        builder.cache(new Cache(new File(CACHE_DIR), CACHE_SIZE_BYTES));

        if (ULog.isDebugMode()) {
            HttpLoggingInterceptor logging = new HttpLoggingInterceptor();
            logging.setLevel(HttpLoggingInterceptor.Level.BODY);
            builder.addInterceptor(logging);
        }


        if (!isProduct) {
            builder.addInterceptor(new ChuckerInterceptor(Utils.getApp()));
        }
        if (!isProduct) {
            builder.addInterceptor(new LogDataInterceptor());
        }
        okHttpClient = builder.build();

        return okHttpClient;
    }

    public OkHttpClient getOkHttpClient() {
        if (null != okHttpClient) {
            return okHttpClient;
        } else {
            return new OkHttpClient.Builder().build();
        }
    }

    public Retrofit getRetrofit(String baseUrl, boolean takeDefaultParams, HashMap<String, String> universalParams) {
        Retrofit.Builder builder = new Retrofit.Builder();
        builder.baseUrl(baseUrl == null ? "" : baseUrl);
        builder.callbackExecutor(executor);
        builder.client(getOkHttpClient(takeDefaultParams, universalParams));
        builder.addConverterFactory(GsonConverterFactory.create(GsonSafetyUtils.getInstance().mGson));
//        builder.addConverterFactory(GsonConverterFactory.create());
        return builder.build();
    }

    public Map<String, String> getUniversalParams() {
        if (null != universalParams) return universalParams;
        universalParams = new HashMap<>();
//        universalParams.putAll(getHeaderData());
        return universalParams;
    }

    public void addCommonQueryParam(Map<String, String> params) {
        getUniversalParams().putAll(params);
        if (mInterceptor == null) {
            mInterceptor = new RequestNetWorkInterceptor(mContext, universalParams);
        }
        mInterceptor.addHeaderParam(universalParams);
    }

    public void addToken(String token) {
        if (mInterceptor == null) {
            mInterceptor = new RequestNetWorkInterceptor(mContext, universalParams);
        }
        mInterceptor.addToken(token);
    }

    public String getAtomHeaderData() {
        if (mInterceptor != null) {
            return mInterceptor.getHeaderData();
        }
        return null;
    }

    public interface HookNetworkError {
        //返回true继续执行，返回false中断执行
        boolean hookError(NetInfo netInfo, String code, String msg);
    }

    public static class NetInfo {
        public String url = "";
        public String throwMsg = "";
    }

    private Map<String, String> getHeaderData() {
        Map<String, String> params = new HashMap<>();
        params.put("x-pt", EnvUtils.getAppPlat());//软件平台
        params.put("x-cn", EnvUtils.getApiChannel());//当前渠道
        params.put("x-os", EnvUtils.getApiChannel());//操作系统和版本
        params.put("x-cd", "rtlive");//app code
        params.put("x-dt", DeviceUtils.getAndroidID());//device token
        if (mContext != null) {
            params.put("x-pn", mContext.getPackageName());//应用标识
            params.put("x-cl", getLocale(mContext.getResources()).getLanguage());//app语言
            params.put("x-cv", EnvUtils.getAppVersion(mContext)); //app版本
            params.put("x-nt", NetworkUtil.GetNetworkType(mContext) + "");//网络类型
            params.put("x-df", DeviceUtils.getAndroidID());
            params.put("x-dn", EnvUtils.getDeviceName(mContext));//设备名 base64编码值
        }

        return params;
    }

    public Locale getLocale(Resources res) {
        Configuration config = res.getConfiguration();
        return Build.VERSION.SDK_INT >= N ? config.getLocales().get(0) : config.locale;
    }
}
