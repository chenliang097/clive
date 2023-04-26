package com.rongtuoyouxuan.chatlive.databus;

import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.SPUtils;
import com.rongtuoyouxuan.chatlive.databus.storage.Storage;
import com.rongtuoyouxuan.chatlive.sp.SPConstants;
import com.google.gson.JsonSyntaxException;
import com.google.gson.reflect.TypeToken;
import com.rongtuoyouxuan.chatlive.arch.LiveCallback;
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.UserInfo;
import com.rongtuoyouxuan.chatlive.databus.config.ConfigManager;
import com.rongtuoyouxuan.chatlive.databus.language.LocaleManager;
import com.rongtuoyouxuan.chatlive.databus.login.LoginManager;
import com.rongtuoyouxuan.chatlive.util.GsonUtils;

import org.jetbrains.annotations.NotNull;

import java.lang.reflect.Type;

public class DataBus {

    private static final int MAX_LOG_RETRY = 3;
    public final String USER_ID = "13652";
    private static volatile DataBus sDataBus;
    private static Context context;
    private final String TAG = DataBus.class.getSimpleName();
    private MutableLiveData<UserInfo> userInfo = new MutableLiveData<>();
    private MutableLiveData<Void> gotBasePermission = new MutableLiveData<>();
    private LocaleManager localeManager;
    private ConfigManager configManager = new ConfigManager();
    private String lastSchemeUri = null;
    private String lastSchemeUriAct = "";
    private boolean appHasStarted = false;
    private boolean forceUpdate = false;
    private boolean isLogined = false;
    private boolean hasLoadConfig = false;
    private String lastAction = "";
    private String lastActionVal = "";
    private Handler handler;
    private String fcmToken = "";
    private String curLiveRoomFansClub = "";//当前用户进入该直播间的粉丝团名称

    private boolean FLOAT_IS_RUN = false;
    private LiveCallback<FragmentActivity> mainPageOnCreate = new LiveCallback<>();
    /**
     * isGooglePlay  true 不显示，false 显示
     */
    private boolean isGooglePlay = true;


    private DataBus() {

    }

    public static DataBus instance() {
        if (sDataBus == null) {
            synchronized (DataBus.class) {
                if (sDataBus == null)
                    sDataBus = new DataBus();
                sDataBus._init();
            }
        }
        return sDataBus;
    }

    public static void init(Context context) {
        DataBus.context = context;
    }

    private void clearData() {
        cleanUserInfo();
        lastSchemeUri = null;
        appHasStarted = false;
    }

    private void _init() {
        localeManager = new LocaleManager();
        localeManager.init();

        initUserInfo();
        LoginManager.instance().getLoginComplete().observeForever(new Observer<Integer>() {
            @Override
            public void onChanged(@NonNull Integer type) {
                Storage.Companion.getInstance().set(SPConstants.BooleanConstants.LAST_LOGIN, true);
                isLogined = true;
                DataBus.instance().initConfig();
                init(context);
                initIM();
                appInitComplete();

            }
        });

        LoginManager.instance().getLogout().observeForever(new Observer<Void>() {
            @Override
            public void onChanged(@Nullable Void aVoid) {
                Storage.Companion.getInstance().set(SPConstants.BooleanConstants.LAST_LOGIN, false);
                isLogined = false;
                unInitIM();
                clearData();
            }
        });
    }

    public Handler getMainHandler() {
        if (handler == null) {
            handler = new Handler(Looper.getMainLooper());
        }
        return handler;
    }

    public void initConfig() {
        if (hasLoadConfig) {
            return;
        }
        hasLoadConfig = true;
        configManager.loadClientConfig();
        configManager.loadCountryCode();
    }
    public Context getAppContext() {
        return context;
    }

    public LocaleManager getLocaleManager() {
        return localeManager;
    }

    public ConfigManager getConfigMananger() {
        return configManager;
    }

    private void initUserInfo() {
        UserInfo wrapper = null;
        String usrString = Storage.Companion.getInstance().getString(SPConstants.StringConstants.APP_USER);
        if (!TextUtils.isEmpty(usrString)) {
            Type type = new TypeToken<UserInfo>() {
            }.getType();
            try {
                wrapper = GsonUtils.fromJson(usrString, type);
            } catch (JsonSyntaxException e) {
                e.printStackTrace();
            }
        }
        userInfo.setValue(wrapper == null ? new UserInfo() : wrapper);
    }

    public boolean isLogin() {
        return isLogined;
    }

    public void refreshUserInfo(UserInfo info) {
        if(userInfo==null)return;
        this.userInfo.setValue(info);
        String user = GsonUtils.toJson(info);
        SPUtils.getInstance().put(SPConstants.StringConstants.USER_ID, info.getUser_info().getUserId());
        Storage.Companion.getInstance().set(SPConstants.StringConstants.APP_USER, user);
    }

    public LiveData<UserInfo> getUserInfo() {
        return userInfo;
    }

    public String getUid() {
        return String.valueOf(getUserInfo().getValue().getUser_info().getUserId());
    }

    //是否是游客
    public boolean isVisitor() {
        return getUserInfo().getValue().getUser_info().isVisitor();
    }

    public String getToken() {
        return getUserInfo().getValue().getToken();
    }

    public void cleanUserInfo() {
        userInfo.setValue(new UserInfo());
        Storage.Companion.getInstance().set(SPConstants.StringConstants.APP_USER, "");
    }

    public boolean isAppHasStarted() {
        return appHasStarted;
    }

    public void setAppHasStarted(boolean appHasStarted) {
        this.appHasStarted = appHasStarted;
    }

    public String getLastSchemeUri() {
        return lastSchemeUri;
    }

    public String getLastSchemeAct() {
        return lastSchemeUriAct;
    }

    public void setLastSchemeUri(String act, String lastSchemeUri) {
        this.lastSchemeUriAct = act;
        this.lastSchemeUri = lastSchemeUri;
    }

    public void setLastAction(String action, String actionVal) {
        this.lastAction = action;
        this.lastActionVal = actionVal;
    }

    public String getLastAction() {
        return lastAction;
    }

    public String getLastActionVal() {
        return lastActionVal;
    }

    public boolean isForceUpdate() {
        return forceUpdate;
    }

    public void setForceUpdate(boolean forceUpdate) {
        this.forceUpdate = forceUpdate;
    }

    private void initIM() {
    }

    private void appInitComplete() {
        DataBus.instance().setAppHasStarted(true);
    }

    private void unInitIM() {
        DataBus.instance().setAppHasStarted(false);
    }

    public MutableLiveData<Void> getGotBasePermission() {
        return gotBasePermission;
    }

    public boolean getIsGooglePlay() {
        return isGooglePlay;
    }

    public void setIsGooglePlay(boolean isGooglePlay) {
        this.isGooglePlay = isGooglePlay;
    }


    public String getFCMToken() {
        return fcmToken;
    }

    public LiveCallback<FragmentActivity> getMainPageOnCreate() {
        return mainPageOnCreate;
    }

    public boolean getFLOAT_IS_RUN() {
        return this.FLOAT_IS_RUN;
    }

    public void setFLOAT_IS_RUN(boolean FLOAT_IS_RUN) {
        this.FLOAT_IS_RUN = FLOAT_IS_RUN;
    }

    public String getCurLiveRoomFansClub() {
        return this.curLiveRoomFansClub;
    }

    public void setCurLiveRoomFansClub(String curLiveRoomFansClub) {
        this.curLiveRoomFansClub = curLiveRoomFansClub;
    }

}
