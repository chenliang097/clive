package com.rongtuoyouxuan.libsocket.base;

import android.annotation.SuppressLint;
import android.os.Build;
import android.os.Handler;
import android.os.Message;
import android.util.Log;

import androidx.annotation.NonNull;

import com.blankj.utilcode.util.ConvertUtils;
import com.google.gson.Gson;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.MsgHeaderBean;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.IMUserInfo;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.net2.BaseNetImpl;
import com.rongtuoyouxuan.chatlive.util.GsonUtils;
import com.rongtuoyouxuan.libsocket.WebSocketManager;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.Socket;
import java.util.Base64;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;

import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import okhttp3.WebSocket;
import okhttp3.WebSocketListener;
import okio.ByteString;

/**
 * 描述：
 *
 * @time 2019/10/25
 */
public class WebSocketImpl {
    private WebSocket mWebSocket;
    private IWebSocketListener mWebSocketListener;
    private Builder mBuilder;

    private EventCallback mEventCallback;
    private ChatSendCallback chatSendCallback;
    private boolean isReceiveHeat = false;

    private WebSocketImpl(Builder builder, EventCallback eventCallback) {
        mBuilder = builder;
        mEventCallback = eventCallback;
        init(builder);
    }

    public void init(Builder builder) {
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .readTimeout(60, TimeUnit.SECONDS)
                .writeTimeout(60, TimeUnit.SECONDS)
                .connectTimeout(60, TimeUnit.SECONDS)
                .pingInterval(builder.heartbeat_tiem, TimeUnit.SECONDS)
                .build();

        Request build = new Request.Builder()
                .url(builder.mUrl)
                .build();

        okHttpClient.newWebSocket(build, new SocketListener());
        okHttpClient.dispatcher().executorService().shutdown();
        ULog.i(getTAG(), "init");
    }

    public void release() {
        if (mWebSocket == null) {
            return;
        }
        if(hbThread != null) {
            hbThread.interrupt();
        }
        hbThread = null;
        mWebSocket.close(1000, null);
        mWebSocket = null;
        ULog.i(getTAG(), "release");
    }

    public String getLoginUserInfo() {
        HashMap<String, Object> map = new HashMap<String, Object>();
//        map.put("type", "up");
//        map.put("action", "auth");
        Gson gson = new Gson();
//        String body = gson.toJson(mBuilder.imUserInfo);
//        map.put("body", body);
        String s = gson.toJson(map);
        return s;
    }

    public void sendMsg(String msg) {
        if (mWebSocket == null) {
            return;
        }
        mWebSocket.send(msg);
    }

    private class SocketListener extends WebSocketListener {

        @Override
        public void onOpen(WebSocket webSocket, Response response) {
            super.onOpen(webSocket, response);
            if (webSocket == null) {
                return;
            }
            mWebSocket = webSocket;
            try {
                mWebSocket.send(ByteString.of(authWrite()));
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            if (mWebSocketListener != null) {
                mWebSocketListener.onOpen(webSocket, response);
            }
            Log.e(getTAG(), "WebSocketImpl----onOpen: ");
        }

        @Override
        public void onMessage(WebSocket webSocket, String text) {
            super.onMessage(webSocket, text);
//            if (mWebSocketListener != null) {
//                mWebSocketListener.onMessage(webSocket, text);
//            }
            Log.e(getTAG(), "onMessage: " + text);
        }

        @Override
        public void onMessage(WebSocket webSocket, ByteString bytes) {
            super.onMessage(webSocket, bytes);
            byte[] inBuffer = bytes.toByteArray();
            Long operation = BruteForceCoding.decodeIntBigEndian(inBuffer, 8, 4);

            Log.e(getTAG(), "onMessage: " + inBuffer.length + "--operation:" + operation);
            if (3 == operation) {
                Log.e(getTAG(), "onMessage: " + "heartBeatReceived---");
                isReceiveHeat = true;
            } else if (8 == operation) {
                Log.e(getTAG(), "onMessage: " + "authSuccess---");
                if (mEventCallback != null) {
                    mEventCallback.Success();
                }
                heartBeat(webSocket);
            } else if (5 == operation) {
                byte[] result = BruteForceCoding.tail(inBuffer, inBuffer.length);
                String resultInfo = new String(result).trim();
                ULog.e(getTAG(), "onMessage: " + "5---" + resultInfo);
//                if (mWebSocketListener != null) {
//                    mWebSocketListener.onMessage(webSocket, bytes, op);
//                }
            }
            else if (9 == operation) {//自定义消息类型 服务端下发
                Long packageLength = BruteForceCoding.decodeIntBigEndian(inBuffer, 0, 4);
                Long headLength = BruteForceCoding.decodeIntBigEndian(inBuffer, 4, 2);
                Long version = BruteForceCoding.decodeIntBigEndian(inBuffer, 6, 2);
                Long sequenceId = BruteForceCoding.decodeIntBigEndian(inBuffer, 12, 4);
                ULog.e(getTAG(), "onMessage: " + "packageLength:" + packageLength + "--headLength:" + headLength + "--version:" + version + "--sequenceId:" + sequenceId  + "--operation:" + operation + "--inBuffer:" + inBuffer.length);

                for(int offset = headLength.intValue(); offset < inBuffer.length; offset+=packageLength){
                    Long packetLen1 = BruteForceCoding.decodeIntBigEndian(inBuffer, offset, 4);
                    Long headLength1 = BruteForceCoding.decodeIntBigEndian(inBuffer, offset + 4, 2);
                    Long version1 = BruteForceCoding.decodeIntBigEndian(inBuffer, offset + 6, 2);
                    Long operation1 = BruteForceCoding.decodeIntBigEndian(inBuffer, offset + 8, 4);
                    Long sequenceId1 = BruteForceCoding.decodeIntBigEndian(inBuffer, offset + 12, 4);
                    byte[] result = BruteForceCoding.tail(inBuffer,(int)(offset + headLength1), (int)(packetLen1 -headLength1));
                    ULog.e(getTAG(), "onMessage: " + "packetLen1:" + packetLen1 + "--headLength1:" + headLength1 + "--version1:" + version1 + "--sequenceId1:" + sequenceId1  + "--operation1:" + operation1);
                    String resultInfo = new String(result).trim();
                    ULog.e(getTAG(), "onMessage: " + "9---" + resultInfo);
                    if (mWebSocketListener != null) {
                        mWebSocketListener.onMessage(webSocket, resultInfo, operation1);
                    }
                }

            }
        }

        @Override
        public void onClosing(WebSocket webSocket, int code, String reason) {
            if (mWebSocketListener != null) {
                mWebSocketListener.onClosing(webSocket, code, reason);
            }
            super.onClosing(webSocket, code, reason);
            ULog.i(getTAG(), "onClosing code:%s reason:%s", String.valueOf(code), reason);
        }

        @Override
        public void onClosed(WebSocket webSocket, int code, String reason) {
            if (mWebSocketListener != null) {
                mWebSocketListener.onClosed(webSocket, code, reason);
            }
            super.onClosed(webSocket, code, reason);
            ULog.i(getTAG(), "onClosed code:%s reason:%s", String.valueOf(code), reason);
        }

        @Override
        public void onFailure(WebSocket webSocket, Throwable t, Response response) {
            super.onFailure(webSocket, t, response);
            if (mWebSocketListener != null) {
                mWebSocketListener.onFailure(webSocket, t, response);
            }
            ULog.e(getTAG(), "onFailure %s", t);
        }
    }

    private Thread hbThread;
    private void heartBeat(WebSocket webSocket) {
        hbThread = new Thread(new HeartbeatTask(webSocket));
        hbThread.start();
    }

    class HeartbeatTask implements Runnable {
        WebSocket webSocket;
        HeartbeatTask(WebSocket webSocket){
           this.webSocket = webSocket;
        }
        @Override
        public void run() {
            while (true) {
                if(hbThread == null){
                    break;
                }
                if(hbThread!= null && hbThread.isInterrupted()){
                    break;
                }
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                try {
                    heartBeatWrite(webSocket);
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
    }

    public synchronized byte[] authWrite() throws IOException {
        int[] codes = {1000,1001,1002,2001,2002,2004,2005,2006,2007,2008,2010,2013,2020,3001,3002,3003,3004,3006,4001};
        String contentStr = GsonUtils.toJson(new MsgHeaderBean(Integer.parseInt(mBuilder.imUserInfo.userid), "live://" + mBuilder.imUserInfo.roomId, "android",codes));
        int packLength = contentStr.getBytes().length + 16;
        byte[] message = new byte[4 + 2 + 2 + 4 + 4];
        int offset = BruteForceCoding.encodeIntBigEndian(message, packLength, 0, 4 * BruteForceCoding.BSIZE);
        offset = BruteForceCoding.encodeIntBigEndian(message, 16, offset, 2 * BruteForceCoding.BSIZE);
        offset = BruteForceCoding.encodeIntBigEndian(message, 1, offset, 2 * BruteForceCoding.BSIZE);
        offset = BruteForceCoding.encodeIntBigEndian(message, 7, offset, 4 * BruteForceCoding.BSIZE);
        offset = BruteForceCoding.encodeIntBigEndian(message, 1, offset, 4 * BruteForceCoding.BSIZE);
        return BruteForceCoding.add(message, contentStr.getBytes());

    }

    public synchronized void heartBeatWrite(WebSocket webSocket) throws IOException {
        int[] codes = {1000,1001,1002,2001,2002,2004,2005,2006,2007,2008,2010,2013,2020,3001,3002,3003,3004,3006,4001};
        String contentStr = GsonUtils.toJson(new MsgHeaderBean(Integer.parseInt(mBuilder.imUserInfo.userid), "live://" + mBuilder.imUserInfo.roomId, "android",codes));
        int packLength = contentStr.getBytes().length + 16;
        byte[] message = new byte[4 + 2 + 2 + 4 + 4];

        // package length
        int offset = BruteForceCoding.encodeIntBigEndian(message, packLength, 0, 4 * BruteForceCoding.BSIZE);
        // header lenght
        offset = BruteForceCoding.encodeIntBigEndian(message, 16, offset, 2 * BruteForceCoding.BSIZE);
        // ver
        offset = BruteForceCoding.encodeIntBigEndian(message, 1, offset, 2 * BruteForceCoding.BSIZE);
        // operation
        offset = BruteForceCoding.encodeIntBigEndian(message, 2, offset, 4 * BruteForceCoding.BSIZE);
        // jsonp callback
        offset = BruteForceCoding.encodeIntBigEndian(message, 1, offset, 4 * BruteForceCoding.BSIZE);
        ULog.e(getTAG(), "heartBeatWrite---" + contentStr);
        webSocket.send(ByteString.of(BruteForceCoding.add(message, contentStr.getBytes())));
        isReceiveHeat = false;
        handler.sendEmptyMessageDelayed(0, 3000);
    }

    private static final int MAX_RETRY = 3;
    private int retryConnectCount = 0;
    @SuppressLint("HandlerLeak")
    private Handler handler = new Handler(){
        @Override
        public void handleMessage(@NonNull Message msg) {
            super.handleMessage(msg);
            if (retryConnectCount < MAX_RETRY) {
                retryConnectCount++;
                if (!isReceiveHeat) {
                    if (mWebSocket != null) {
                        heartBeat(mWebSocket);
                    }
                }
            }
        }
    };


    public void setWebSocketListener(IWebSocketListener webSocketListener) {
        mWebSocketListener = webSocketListener;
    }

    public static class Builder {

        public String mUrl;
        public long heartbeat_tiem = 5;
        IMUserInfo imUserInfo = new IMUserInfo();

        private EventCallback mEventCallback;

        public Builder conncect(String url) {
            mUrl = url;
            return this;
        }

        public Builder setInterval(long time) {
            heartbeat_tiem = time;
            return this;
        }

        public Builder setInfo(IMUserInfo info) {
            imUserInfo = info;
            return this;
        }

        public Builder setEventCallback(EventCallback mEventCallback) {
            this.mEventCallback = mEventCallback;
            return this;
        }

        public WebSocketImpl build() {
            WebSocketImpl webSocket = new WebSocketImpl(this, mEventCallback);
            return webSocket;
        }

    }

    private String getTAG() {
        String tag = "WebSocketImpl";
        try {
            return tag + "@" + Integer.toHexString(this.hashCode());
        } catch (Exception e) {
            ULog.e(e);
            return tag;
        }
    }

    public void sendCommonMessage(int op, String msg, ChatSendCallback chatSendCallback) {
        WebSocket webSocket;
        this.chatSendCallback = chatSendCallback;
//        synchronized (WebSocketManager.class) {
            webSocket = mWebSocket;
//        }
        if (webSocket != null) {
            sendMessage(op, webSocket, msg);
        }
    }

    private static void sendMessage(int op, WebSocket webSocket, String msg) {
        int packLength = msg.getBytes().length + 16;
        byte[] message = new byte[16];
        ULog.e("clll", "op:" + op + "--sendMessage:" + msg);
        int offset = BruteForceCoding.encodeIntBigEndian(message, packLength, 0, 4 * BruteForceCoding.BSIZE);
        offset = BruteForceCoding.encodeIntBigEndian(message, 16, offset, 2 * BruteForceCoding.BSIZE);
        offset = BruteForceCoding.encodeIntBigEndian(message, 1, offset, 2 * BruteForceCoding.BSIZE);
        offset = BruteForceCoding.encodeIntBigEndian(message, op, offset, 4 * BruteForceCoding.BSIZE);
        offset = BruteForceCoding.encodeIntBigEndian(message, 1, offset, 4 * BruteForceCoding.BSIZE);

        ByteString aa = ByteString.of(BruteForceCoding.add(message, msg.getBytes()));

        webSocket.send(aa);
    }
}
