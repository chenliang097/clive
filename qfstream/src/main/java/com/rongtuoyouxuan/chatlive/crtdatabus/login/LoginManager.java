package com.rongtuoyouxuan.chatlive.crtdatabus.login;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus;

public class LoginManager {
    /**
     * 新注册用户登录
     */
    public static int TYPE_NEW_USER = 1;
    private static LoginManager sLoginManager;

    private MutableLiveData<Integer> loginComplete = new MutableLiveData<>();
    private MutableLiveData<Void> logout = new MutableLiveData<>();

    public LoginManager() {
    }

    public static LoginManager instance() {
        if (sLoginManager == null) {
            synchronized (DataBus.class) {
                if (sLoginManager == null)
                    sLoginManager = new LoginManager();
            }
        }
        return sLoginManager;
    }

    public void logout() {
        doLogout(null);
        logout.setValue(null);
    }

    private void doLogout(final IloginListener mLoginListener) {
//        LogoutBiz.getInstance().logout(null, new RequestListener<LogoutModel>() {
//            @Override
//            public void onSuccess(String reqId, LogoutModel result) {
//            }
//
//            @Override
//            public void onFailure(String reqId, String errCode, String msg) {
//            }
//        });
    }

    public LiveData<Integer> getLoginComplete() {
        return loginComplete;
    }

    public void loginComplete() {
        loginComplete.setValue(0);
    }

    public void loginComplete(@NonNull Integer type) {
        loginComplete.setValue(type);
    }

    public LiveData<Void> getLogout() {
        return logout;
    }
}
