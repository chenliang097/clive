package com.rongtuoyouxuan.libuikit.loadsir;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.annotation.DrawableRes;
import androidx.annotation.StringRes;

import com.blankj.utilcode.util.ThreadUtils;
import com.rongtuoyouxuan.libuikit.R;
import com.rongtuoyouxuan.libuikit.loadsir.callbacks.EmptyCallback;
import com.rongtuoyouxuan.libuikit.loadsir.callbacks.ErrorCallback;
import com.rongtuoyouxuan.libuikit.loadsir.callbacks.LoadingCallback;
import com.kingja.loadsir.callback.Callback;
import com.kingja.loadsir.core.Convertor;
import com.kingja.loadsir.core.LoadService;
import com.kingja.loadsir.core.LoadSir;
import com.kingja.loadsir.core.Transport;

/*
 *Creaete by {Mr秦} on 2020/10/27
 */
public class BaseLoadService {

    //************************************注册 初始化**************************************************v
    private LoadService mLoadService;

    private BaseLoadService(LoadService loadService) {
        mLoadService = loadService;
    }

    public static BaseLoadService register(Object target, BaseOnReloadListener baseOnReloadListener) {
        return new BaseLoadService(realRegister(LoadSir.getDefault(), target, baseOnReloadListener));
    }

    public static <T> BaseLoadService register(Object target, BaseOnReloadListener baseOnReloadListener, Convertor<T> convector) {
        return new BaseLoadService(realRegister(LoadSir.getDefault(), target, baseOnReloadListener, convector));
    }

    public static BaseLoadService register(LoadSir loadSir, Object target, BaseOnReloadListener baseOnReloadListener) {
        return new BaseLoadService(realRegister(loadSir, target, baseOnReloadListener));
    }

    public static <T> BaseLoadService register(LoadSir loadSir, Object target, BaseOnReloadListener baseOnReloadListener, Convertor<T> convector) {
        return new BaseLoadService(realRegister(loadSir, target, baseOnReloadListener, convector));
    }

    private static LoadService realRegister(LoadSir loadSir, Object target, BaseOnReloadListener baseOnReloadListener) {
        return loadSir.register(target, baseOnReloadListener);
    }

    private static <T> LoadService realRegister(LoadSir loadSir, Object target, Callback.OnReloadListener onReloadListener, Convertor<T> convector) {
        return loadSir.register(target, onReloadListener, convector);
    }

    public void setCallBack(final Class<? extends Callback> clazz, Transport transport) {
        mLoadService.setCallBack(clazz, transport);
    }

    public void setEmptyCallBack(@StringRes int title, @DrawableRes int imgResId, @StringRes int btn_tv) {
        setCallBack(EmptyCallback.class, (context, view) -> {
            if (title != 0) {
                ((TextView) view.findViewById(R.id.tv_empty)).setText(title);
            }
            if (imgResId != 0) {
                ((ImageView) view.findViewById(R.id.empty_im)).setImageResource(imgResId);
            }
            TextView btn = (TextView) view.findViewById(R.id.empty_btn);
            if (btn_tv != 0) {
                btn.setVisibility(View.VISIBLE);
                btn.setText(btn_tv);
            }else{
                btn.setVisibility(View.GONE);
            }
        });
    }


    public void setLoadingCallBack(Transport transport) {
        setCallBack(LoadingCallback.class, transport);
    }

    public void setErrorCallBack(Transport transport) {
        setCallBack(ErrorCallback.class, transport);
    }

    //************************************手动调用**************************************************v

    public void postSuccess() {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadService.showSuccess();
            }
        });
    }

    public void postError() {
        postCallback(ErrorCallback.class);
    }

    public void postError(final Class<? extends Callback> clazz) {
        postCallback(clazz);
    }


    public void postEmpty() {
        postCallback(EmptyCallback.class);
    }

    public void postEmpty(final Class<? extends Callback> clazz) {
        postCallback(clazz);
    }

    public void reset() {
        postCallback(LoadingCallback.class);
    }

    public void reset(final Class<? extends Callback> clazz) {
        postCallback(clazz);
    }


    private void postCallback(final Class<? extends Callback> clazz) {
        ThreadUtils.runOnUiThread(new Runnable() {
            @Override
            public void run() {
                mLoadService.showCallback(clazz);
            }
        });
    }

    //************************************其他方法如:Convertor**************************************************v
    public void showWithConverter(Object t) {
        mLoadService.showWithConvertor(t);
    }
}