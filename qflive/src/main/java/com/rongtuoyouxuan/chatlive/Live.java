package com.rongtuoyouxuan.chatlive;

import android.annotation.SuppressLint;
import android.app.ActivityManager;
import android.app.Application;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.media.AudioAttributes;
import android.net.Uri;
import android.os.Build;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.Observer;

import com.alibaba.android.arouter.launcher.ARouter;
import com.blankj.utilcode.util.LogUtils;
import com.rongtuoyouxuan.chatlive.biz2.constanst.UrlConstanst;
import com.rongtuoyouxuan.chatlive.databus.DataBus;
import com.rongtuoyouxuan.chatlive.databus.storage.Storage;
import com.rongtuoyouxuan.chatlive.log.PLog;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.net2.BaseNetImpl;
import com.rongtuoyouxuan.chatlive.util.DirectoryUtils;
import com.rongtuoyouxuan.chatlive.util.EnvUtils;
import com.rongtuoyouxuan.chatlive.util.MyLifecycleHandler;
import com.rongtuoyouxuan.libuikit.UiKitConfig;
import com.rongtuoyouxuan.libuikit.loadsir.callbacks.EmptyCallback;
import com.rongtuoyouxuan.libuikit.loadsir.callbacks.ErrorCallback;
import com.rongtuoyouxuan.libuikit.loadsir.callbacks.LoadingCallback;
import com.rongtuoyouxuan.qfcommon.util.APIEnvironment;
import com.just.agentweb.AgentWebConfig;
import com.kingja.loadsir.core.LoadSir;
import com.rongtuoyouxuan.qfzego.KeyCenter;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import kotlin.jvm.functions.Function0;
import kotlin.jvm.functions.Function1;

public class Live {

    private static final String TAG = Live.class.getSimpleName();

    static {
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                layout.setEnableLoadMore(false);
                return null;
            }
        });
    }

    private static void initNet(final Context context) {
//        //初始化参数
//        Map<String, String> params = new HashMap<>();
//        params.put("x-cv", EnvUtils.getAppVersion(context)); //app版本
//        params.put("x-pt", EnvUtils.getAppPlat());//软件平台
//        params.put("x-cn", EnvUtils.getApiChannel());//当前渠道
//        params.put("x-cl", DataBus.instance().getLocaleManager().getLanguage().getValue());//app语言
//        params.put("x-os", EnvUtils.getApiChannel());//操作系统和版本
//        params.put("x-nt", NetworkUtil.GetNetworkType(context) + "");//网络类型
//        params.put("x-dn", EnvUtils.getApiChannel());//设备名 base64编码值
//        params.put("x-pn", context.getPackageName());//应用标识
//        params.put("x-cd", "rtlive");//app code
//        params.put("x-dt", DeviceUtils.getAndroidID());//device token
//        params.put("x-dn", EnvUtils.getDeviceName(context));//设备名 base64编码值
//        params.put("x-df", DeviceUtils.getAndroidID());
//        if (DataBus.instance().getUserInfo().getValue() != null
//                && !StringUtils.isTrimEmpty(DataBus.instance().getUserInfo().getValue().getToken())) {
//            BaseNetImpl.getInstance().addToken(DataBus.instance().getUserInfo().getValue().getToken());
//        }
//
//        BaseNetImpl.init(context, APIEnvironment.INSTANCE.isProductEnvironment());
//        BaseNetImpl.getInstance().addCommonQueryParam(params);
//
//        //上一次没有正常登录，清除cookie
//        if (!Storage.Companion.getInstance().getBoolean(SPConstants.BooleanConstants.LAST_LOGIN)) {
//            BaseNetImpl.getInstance().clearCookie();
//        }
        BaseNetImpl.setCertificate(context, "Digicert-OV-DV-root.cer");
    }

    private static void initLogConfig(Context context) {
        PLog.init(context, EnvUtils.getAppChannel());
        ULog.init(context, EnvUtils.getAppChannel(), EnvUtils.getProductName());
    }

    private static void initRouter(Application app) {
        if (PLog.isDebugMode()) {
            ARouter.openLog();     // 打印日志
            ARouter.openDebug();   // 开启调试模式(如果在InstantRun模式下运行, 必须开启调试模式. 线上版本需要关闭, 否则有安全风险)
        }
        ARouter.init(app); // 尽可能早, 推荐在Application中初始化
    }

    private static void initPermission(Application app) {

    }

    private static void initClientConfig(Application app) {
        DataBus.instance().getConfigMananger().init(app);
    }


    private static void initDataBus(Context context) {
        DataBus.init(context);
        Storage.Companion.getInstance().init(context);
    }

    public static void init(Application app) {
        //log工具类初始化
        LogUtils.getConfig()
                .setLogSwitch(!APIEnvironment.INSTANCE.isProductEnvironment())
                .setLogHeadSwitch(false)
                .setBorderSwitch(false);

        DirectoryUtils.init(app);
        //初始化版本渠道信息
        EnvUtils.initEnv(app);

        if (!PLog.isDebugMode()) {
            //写dump文件
            CrashHandler crashHandler = CrashHandler.getInstance();
            crashHandler.init(app.getApplicationContext());
        }

        Context context = app.getApplicationContext();
        app.registerActivityLifecycleCallbacks(MyLifecycleHandler.getInstance());


        int pid = android.os.Process.myPid();
        ActivityManager mActivityManager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
        if (mActivityManager == null) return;
        for (ActivityManager.RunningAppProcessInfo appProcess : mActivityManager.getRunningAppProcesses()) {
            if (appProcess.pid != pid || appProcess.processName.compareToIgnoreCase(context.getPackageName()) != 0)
                continue;

            //初始化日志模块
            initLogConfig(context);
            //初始化路由模块
            initRouter(app);
            //初始化网络模块
            initNet(context);

            initPermission(app);

            initClientConfig(app);

            initUiKit();

            initUserTags();

            initLoadSir();

            initAgentWeb();

            initUmeng(app);

        }
    }

    private static void initAgentWeb() {
        if (ULog.isDebugMode()) {
            AgentWebConfig.debug();
        }
    }

    private static void initLoadSir() {
        LoadSir.beginBuilder()
                .addCallback(new ErrorCallback())
                .addCallback(new EmptyCallback())
                .addCallback(new LoadingCallback())
                .setDefaultCallback(LoadingCallback.class)
                .commit();
    }

    private static void initUiKit() {
        UiKitConfig.INSTANCE.setGetLanguageContext(new Function1<Context, Context>() {
            @Override
            public Context invoke(Context context) {
                return DataBus.instance().getLocaleManager().setLocale(context);
            }
        });
        UiKitConfig.INSTANCE.setArLanguage(new Function0<Boolean>() {
            @Override
            public Boolean invoke() {
                return DataBus.instance().getLocaleManager().getLanguage().getValue().equals("ar");
            }
        });
        UiKitConfig.setBaseUrl(UrlConstanst.BASE_URL_LIVE_API_BOBOO_COM);
    }

    private static void initShare() {
//        ShareConfig.setToSelectContactsActivity(new Function2<String, String, Unit>() {
//            @Override
//            public Unit invoke(String anchorId, String imageUrl) {
//                Router.toSelectContactsActivity(anchorId, imageUrl);
//                return Unit.INSTANCE;
//            }
//        });
    }

    @SuppressLint("NewApi")
    private static void createNotificationChannelCall(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = EnvUtils.getProductName() + "_chat_call.caf";
            String channelName = EnvUtils.getProductName() + "_chat_call.caf";
            Uri NOTIFICATION_SOUND_URI = Uri.parse("android.resource://" + context.getPackageName() + "/raw/chat_call");
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            AudioAttributes attributes = new AudioAttributes.Builder()
                    .setUsage(AudioAttributes.USAGE_NOTIFICATION)
                    .build();
            channel.setSound(NOTIFICATION_SOUND_URI, attributes);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    @SuppressLint("NewApi")
    private static void createNotificationChannelDefault(@NonNull Context context) {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            String channelId = EnvUtils.getProductName() + "_default";
            String channelName = EnvUtils.getProductName() + "_default";
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_HIGH);
            NotificationManager notificationManager = (NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE);
            notificationManager.createNotificationChannel(channel);
        }
    }

    public static void onConfigurationChanged(Context context) {
        DataBus.instance().getLocaleManager().setLocale(context);
    }

    public static Context getLocalContext(Context base, Application app) {
        initDataBus(base);
        return DataBus.instance().getLocaleManager().setLocale(base);
    }

    private static void initUserTags() {
        DataBus.instance().getMainPageOnCreate().observe(new Observer<FragmentActivity>() {
            @Override
            public void onChanged(FragmentActivity fragmentActivity) {
            }
        });
    }


    //友盟分享
    private static void initUmeng(Application app) {
        /**
         * 初始化common库
         * 参数1:上下文，不能为空
         * 参数2:【友盟+】 AppKey
         * 参数3:【友盟+】 Channel，Channel命名请详见Channel渠道命名规范。
         * 参数4:设备类型，UMConfigure.DEVICE_TYPE_PHONE为手机、UMConfigure.DEVICE_TYPE_BOX为盒子，默认为手机
         * 参数5:Push推送业务的secret  需要集成Push功能时必须传入Push的secret，否则传空。
         */
        UMConfigure.init(app, KeyCenter.getInstance().UM_APPKEY, "umeng",
                UMConfigure.DEVICE_TYPE_PHONE, "");
        PlatformConfig.setWeixin(KeyCenter.getInstance().UM_WX_APP_ID, KeyCenter.getInstance().UM_WX_APP_SECRET);
        PlatformConfig.setWXFileProvider(app.getPackageName() + ".fileprovider");
        PlatformConfig.setSinaWeibo(KeyCenter.getInstance().UM_SINA_APP_ID, KeyCenter.getInstance().UM_SINA_APP_KEY,
                "http://sns.whalecloud.com");
        PlatformConfig.setSinaFileProvider(app.getPackageName() + ".fileprovider");
        PlatformConfig.setQQZone(KeyCenter.getInstance().UM_QQ_APP_ID, KeyCenter.getInstance().UM_QQ_APP_KEY);
        PlatformConfig.setQQFileProvider(app.getPackageName() + ".fileprovider");
        /**
         * 设置组件化的Log开关
         * 参数: boolean 默认为false，如需查看LOG设置为true
         */
        UMConfigure.setLogEnabled(true);
    }

    public interface InitApp {
    }
}
