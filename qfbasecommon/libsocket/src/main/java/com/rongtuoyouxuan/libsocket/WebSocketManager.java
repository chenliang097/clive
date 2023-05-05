package com.rongtuoyouxuan.libsocket;

import android.app.Activity;
import android.text.TextUtils;

import androidx.annotation.Nullable;

import com.rongtuoyouxuan.chatlive.biz2.model.im.response.IMTokenModel;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.IMUserInfo;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.util.EnvUtils;
import com.rongtuoyouxuan.chatlive.util.MyLifecycleHandler;
import com.rongtuoyouxuan.chatlive.util.NumUtils;
import com.rongtuoyouxuan.libsocket.base.BruteForceCoding;
import com.rongtuoyouxuan.libsocket.base.ChatSendCallback;
import com.rongtuoyouxuan.libsocket.base.EventCallback;
import com.rongtuoyouxuan.libsocket.base.GuardWebSocketThread;
import com.rongtuoyouxuan.libsocket.base.IMSocketBase;
import com.rongtuoyouxuan.libsocket.base.ISocket;
import com.rongtuoyouxuan.libsocket.base.IWebSocketListener;
import com.rongtuoyouxuan.libsocket.base.WebSocketImpl;

import okhttp3.Response;
import okhttp3.WebSocket;
import okio.ByteString;

/**
 * 描述：
 *
 * @time 2019/10/25
 */
public class WebSocketManager implements ISocket, IWebSocketListener {
    private String TAG = "WebSocketManager";
    private WebSocketImpl.Builder builder;
    private WebSocketImpl webSocketImpl;

    private static final String enterRoomString = "{\"type\":\"up\",\"action\":\"intoroom\",\"body\":\"%s\"}";
    private static final String leaveRoomString = "{\"type\":\"up\",\"action\":\"leaveroom\",\"body\":\"%s\"}";

    private static final String pingString = "{\"type\":\"up\",\"action\":\"ping\",\"body\":\"1\"}";

    protected static ISocket sInstance;

    private MyLifecycleHandler.Listener appLifecycleListener = new MyLifecycleHandler.Listener() {

        @Override
        public void onBecameForeground(Activity activity) {
            if (webSocketImpl != null) {
                webSocketImpl.sendMsg(pingString);
            }
        }

        @Override
        public void onBecameBackground(Activity activity) {

        }
    };

    public static ISocket _getInstance() {
        synchronized (WebSocketManager.class) {
            if (sInstance == null) {
                sInstance = new WebSocketManager();
            }
        }
        return sInstance;
    }


    private WebSocketManager() {

    }


    @Override
    public void initIM(String key, boolean isDebug, String naviServer, String fileServer) {
        builder = new WebSocketImpl.Builder();
        ULog.i(TAG, "initIM");
    }

    @Override
    public void login(String uid, String roomId, IMTokenModel result, EventCallback eventCallback) {
        if (IMSocketBase.instance().isConnected()) {
            return;
        }
        IMSocketBase.instance().uid = uid;
        IMSocketBase.instance().imAdmin = result.data.admin;
        IMUserInfo imUserInfo = new IMUserInfo();
        imUserInfo.deviceid = EnvUtils.getDeviceId(IMSocketBase.instance().mContext);
        imUserInfo.userid = uid;
        imUserInfo.roomId = roomId;
        if (webSocketImpl != null) {
            webSocketImpl.release();
        }
        webSocketImpl = builder.conncect("ws://goim.rongtuolive.com:3102/sub")
                .setInterval(result.data.period)
                .setEventCallback(eventCallback)
                .setInfo(imUserInfo)
                .build();
        webSocketImpl.setWebSocketListener(WebSocketManager.this);

        MyLifecycleHandler.getInstance().removeListener(appLifecycleListener);
        MyLifecycleHandler.getInstance().addListener(appLifecycleListener);
        ULog.i(TAG, "login");
    }

    private void cancelCheckPong() {

    }

    @Override
    public void signOut() {
        if (webSocketImpl != null) {
            webSocketImpl.release();
        }
        ULog.i(TAG, "signOut");
    }

    @Override
    public void creatGroup(String userId, String groupId, EventCallback eventCallback) {

    }

    @Override
    public void joinGroup(String userId, String groupId, EventCallback eventCallback) {
        if (webSocketImpl == null) {
            return;
        }
        if (!TextUtils.isEmpty(groupId)) {
            IMSocketBase.instance().setJoinGroup(groupId);
        }
        String enterString = String.format(enterRoomString, groupId);
        ULog.e("websocket", enterString);
        webSocketImpl.sendMsg(enterString);
    }

    @Override
    public void quitGroup(String groupId, EventCallback eventCallback) {
        IMSocketBase.instance().setQuitGroup(groupId);

        if (webSocketImpl == null) {
            return;
        }
        String leave = String.format(leaveRoomString, groupId);
        ULog.e("websocket", leave);
        webSocketImpl.sendMsg(leave);
    }

    @Override
    public void deleteGroup(String groupId) {
        quitGroup(groupId,null);
    }

    @Override
    public void sendMessageBySocket(String peer, String msg, ChatSendCallback chatSendCallback) {
         webSocketImpl.sendCommonMessage(Integer.parseInt(peer), msg, chatSendCallback);
    }

    @Override
    public void onOpen(WebSocket webSocket, Response response) {
        IMSocketBase.instance().handler.post(new Runnable() {
            @Override
            public void run() {
                cancelCheckPong();
                ULog.e("websocket", "onOpen");
                IMSocketBase.instance().socketConnectStatus.setValue(new IMSocketBase.SocketConnectStatus(IMSocketBase.STEP_SOKET, true, "", ""));
                String lastGroup = IMSocketBase.instance().getLastGroup();
                if (!TextUtils.isEmpty(lastGroup)) {
                    joinGroup(IMSocketBase.instance().uid, lastGroup,null);
                }
            }
        });
//        GuardWebSocketThread.instance().start();
    }

    @Override
    public void onMessage(WebSocket webSocket, final String text, long op) {
        IMSocketBase.instance().handler.post(new Runnable() {
            @Override
            public void run() {
                cancelCheckPong();
                IMSocketBase.instance().parseMsg(text, false, op);
            }
        });
    }

    @Override
    public void onMessage(WebSocket webSocket, ByteString bytes, long op) {
        IMSocketBase.instance().handler.post(new Runnable() {
            @Override
            public void run() {
                cancelCheckPong();
            }
        });
    }

    @Override
    public void onClosing(WebSocket webSocket, int code, String reason) {
        IMSocketBase.instance().handler.post(new Runnable() {
            @Override
            public void run() {
                cancelCheckPong();
            }
        });
    }

    @Override
    public void onClosed(WebSocket webSocket, final int code, final String reason) {
        IMSocketBase.instance().handler.post(new Runnable() {
            @Override
            public void run() {
                cancelCheckPong();
                IMSocketBase.instance().socketConnectStatus.setValue(new IMSocketBase.SocketConnectStatus(IMSocketBase.STEP_SOKET, false, code+"", reason));
//                IMSocketBase.instance().retryFetchToken();
                ULog.e("websocket", "onClosed");
                if (webSocketImpl != null) {
                    webSocketImpl.release();
                }
            }
        });
    }

    @Override
    public void onFailure(WebSocket webSocket, final Throwable t, @Nullable Response response) {
        IMSocketBase.instance().handler.post(new Runnable() {
            @Override
            public void run() {
                cancelCheckPong();
                IMSocketBase.instance().socketConnectStatus.setValue(new IMSocketBase.SocketConnectStatus(IMSocketBase.STEP_SOKET, false, IMSocketBase.ERROR_SOKET, t.getMessage()));
//                IMSocketBase.instance().retryFetchToken();
                ULog.e("websocket", "onFailure");
                if (webSocketImpl != null) {
                    webSocketImpl.release();
                }
            }
        });
    }

}
