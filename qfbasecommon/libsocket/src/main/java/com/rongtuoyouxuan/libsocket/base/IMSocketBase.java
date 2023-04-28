package com.rongtuoyouxuan.libsocket.base;

import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.rongtuoyouxuan.chatlive.arch.LiveCallback;
import com.rongtuoyouxuan.chatlive.biz2.model.config.MsgConfigModel;
import com.rongtuoyouxuan.chatlive.biz2.model.im.msg.textmsg.RTTxtMsg;
import com.rongtuoyouxuan.chatlive.biz2.model.im.response.IMTokenModel;
import com.rongtuoyouxuan.chatlive.biz2.model.login.response.UserInfo;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.Message;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomBannerGift;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomGift;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomMessage;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.RoomSystemMessage;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.im.UserMessage;
import com.rongtuoyouxuan.chatlive.gson.GsonSafetyUtils;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.chatlive.util.MyLifecycleHandler;
import com.rongtuoyouxuan.libsocket.WebSocketManager;

import org.json.JSONObject;

import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.LinkedList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.atomic.AtomicBoolean;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/5/28
 */
public class IMSocketBase implements ISocket {

    protected static IMSocketBase sInstance;
    public Context mContext;

    public MutableLiveData<SocketConnectStatus> socketConnectStatus = new MutableLiveData<>();
    public String imAdmin = "";

    public Handler handler = new Handler(Looper.getMainLooper());
    public int retryCount = 0;
    public final int MAX_RETRY_COUNT = 3;

    public static final int STEP_GETTOKEN = 1;
    public static final int STEP_SOKET = 2;
    public static final int STEP_NETWORKLOST = 2;

    public static final String ERROR_TOKEN = "-1";
    public static final String ERROR_SOKET = "-2";
    public static final String ERROR_NETWORKLOST = "-3";

    private ISocket iSocket = null;

    public String uid = "";
    public boolean isEnableBackgroundRetry = false;

    final AtomicBoolean isRetrying = new AtomicBoolean(false);

    private AtomicBoolean isLogin = new AtomicBoolean(false);

    private boolean isForeground = true;

    private MyLifecycleHandler.Listener appLifecycleListener = new MyLifecycleHandler.Listener() {

        @Override
        public void onBecameForeground(Activity activity) {
            isForeground = true;
        }

        @Override
        public void onBecameBackground(Activity activity) {
            isForeground = false;
        }
    };

    public boolean isLogin() {
        return isLogin.get();
    }

    public void setLogin(boolean login) {
        isLogin.set(login);
    }

    public static IMSocketBase instance() {
        synchronized (IMSocketBase.class) {
            if (sInstance == null) {
                sInstance = new IMSocketBase();
            }
        }
        return sInstance;
    }

    public boolean isCustomSocket() {
        return iSocket != null && iSocket instanceof WebSocketManager;
    }

    public ISocket getISocket() {
        return iSocket;
    }

    public void init(Context context) {
        mContext = context;
        MyLifecycleHandler.getInstance().removeListener(appLifecycleListener);
        MyLifecycleHandler.getInstance().addListener(appLifecycleListener);
    }

    public static class SocketConnectStatus {
        public SocketConnectStatus(int step, boolean success, String code, String msg) {
            this.step = step;
            this.success = success;
            this.code = code;
            this.msg = msg;
        }

        public SocketConnectStatus() {
        }

        public int step = 0;
        public boolean success = false;
        public String code = "";
        public String msg = "";
    }

    public boolean isConnected() {
        SocketConnectStatus status = socketConnectStatus.getValue();
        return status != null && status.success;
    }

    public String getImAdmin() {
        return imAdmin;
    }

    public LiveData<SocketConnectStatus> getSocketConnectStatus() {
        return socketConnectStatus;
    }

    public interface HookDispatchPrivateMsg {
        interface Complete {
            void run(UserMessage userMessage);
        }
        void hookDispatchPrivateMsg(UserMessage msg, Complete complete);
    }

    public interface HookPrivateMsg {
        UserMessage hookPrivateMsg(UserMessage msg);
    }

    public interface HookTemplateMsg {
        MsgConfigModel.Item hookTemplateMsg(String msg);
    }

    public interface HookRoomGiftEndMsg {
        void processRoomgiftEndMsg(String channel, RoomGift msg);
    }

    public interface DisableReceiveMsg {
        boolean isDisableReceiveMsg();
    }

    private HookDispatchPrivateMsg hookDispatchPrivateMsg;
    private HookPrivateMsg hookPrivateMsg;
    private HookTemplateMsg hookTemplateMsg;
    private HookRoomGiftEndMsg hookRoomGiftEndMsg;
    private DisableReceiveMsg disableReceiveMsg;

    public void setHookDispatchPrivateMsg(HookDispatchPrivateMsg hookDispatchPrivateMsg) {
        this.hookDispatchPrivateMsg = hookDispatchPrivateMsg;
    }

    public void setHookPrivateMsg(HookPrivateMsg hookPrivateMsg) {
        this.hookPrivateMsg = hookPrivateMsg;
    }

    public void setHookRoomGiftEndMsg(HookRoomGiftEndMsg hookRoomGiftEndMsg) {
        this.hookRoomGiftEndMsg = hookRoomGiftEndMsg;
    }

    public void setEnableReciveMsg(DisableReceiveMsg disableReceiveMsg) {
        this.disableReceiveMsg = disableReceiveMsg;
    }

    public void dispatchRoomGiftEndMsg(String channel, RoomGift roomGift) {
        if (hookRoomGiftEndMsg == null) {
            return;
        }
        hookRoomGiftEndMsg.processRoomgiftEndMsg(channel, roomGift);
    }

    public void setHookTemplateMsg(HookTemplateMsg hookTemplateMsg) {
        this.hookTemplateMsg = hookTemplateMsg;
    }

    public void dispatchTemplateMsg(String channel, String value) {
        if (hookTemplateMsg == null) {
            return;
        }
        room(channel).templatemsg.setValue(hookTemplateMsg.hookTemplateMsg(value));
    }

    public void dispatchGlobalTemplateMsg(String channel, String value) {
        if (hookTemplateMsg == null) {
            return;
        }
        globalRoom.templatemsg.setValue(hookTemplateMsg.hookTemplateMsg(value));
    }

    private void _dispatchPrivateMsg(UserMessage userMessage) {
        if (hookPrivateMsg == null) {
            user.privatemsg.setValue(userMessage);
            return;
        }
        user.privatemsg.setValue(hookPrivateMsg.hookPrivateMsg(userMessage));
    }

    private void dispatchPrivateMsg(UserMessage userMessage) {
        if (hookDispatchPrivateMsg != null) {
            hookDispatchPrivateMsg.hookDispatchPrivateMsg(userMessage, new HookDispatchPrivateMsg.Complete() {
                @Override
                public void run(UserMessage _userMessage) {
                    _dispatchPrivateMsg(_userMessage);
                }
            });
        } else {
            _dispatchPrivateMsg(userMessage);
        }
    }

    public interface SendGiftCallback {
        void onSuccess(int balance);

        void onFail(String errCode, String msg);
    }

    void lostNetWork() {
        handler.post(new Runnable() {
            @Override
            public void run() {
                socketConnectStatus.setValue(new SocketConnectStatus(STEP_NETWORKLOST, false, ERROR_NETWORKLOST, ""));
            }
        });
    }

    public void login(final EventCallback eventCallback, final String uid, final String roomId) {
        if (TextUtils.isEmpty(uid)) {
            return;
        }
        if (iSocket == null) {
            iSocket = WebSocketManager._getInstance();
            ULog.i(TAG, "webSocketImpl Instance");
        }
        iSocket.login(uid, roomId, new IMTokenModel(), eventCallback);
        isRetrying.set(false);
        ULog.i(TAG, "login");
    }

    public void retryFetchToken() {
        if (isRetrying.get() || !isNeedRetry()) {
            return;
        }
        retryCount = 0;
        isRetrying.set(true);
        doRetryFetchToken();
        ULog.i(TAG, "retryFetchToken");
    }

    private boolean isNeedRetry() {
        return isLogin.get() && isCanRetryWhileBackground();
    }

    /**
     * 判断当前条件是否可以重连
     * <p>
     * 前台 ： 可以重连
     * 后台 + 重连开关：开 ： 可以重连
     * 后台 + 重连开关：关 ： 不可以重连
     */
    private boolean isCanRetryWhileBackground() {
        return isForeground || isEnableBackgroundRetry;
    }

    private void doRetryFetchToken() {
        if (retryCount < MAX_RETRY_COUNT && isNeedRetry()) {
            retryCount++;
            handler.postDelayed(new Runnable() {
                @Override
                public void run() {
                    if (isNeedRetry()) {
                        login(null, IMSocketBase.instance().uid, "");
                    } else {
                        isRetrying.set(false);
                        ULog.i(TAG, "doRetryFetchToken finish count:%s", String.valueOf(retryCount));
                    }
                }
            }, 1000 * retryCount);
            ULog.i(TAG, "doRetryFetchToken count:%s", String.valueOf(retryCount));
        } else {
            isRetrying.set(false);
            ULog.i(TAG, "doRetryFetchToken finish count:%s", String.valueOf(retryCount));
        }
    }

    private int extraId = 1000;

    private int generateExtraId() {
        return extraId++;
    }

    private Message generateSendMsg(String type, String peerUid, String fid, String tid,
                                    String text, String emoji, String imgPath, String imgUrl,
                                    String giftId, int giftNum, String videoPath, String videoUrl,
                                    String audioPath, String audioUrl, int duration) {
        int sendId = generateExtraId();
        Message msg = new Message();
        msg.setUid(peerUid);
        msg.setFid(fid);
        msg.setTid(tid);
        msg.setType(type);
        if (type.equals(Message.TYPE_TEXT)) {
            msg.setText(text);
            //使用emoji来标记isHello
            msg.setIsHello(emoji);
        } else if (type.equals(Message.TYPE_EMOJI)) {
            msg.setEmoji(emoji);
        } else if (type.equals(Message.TYPE_IMAGE)) {
            msg.setImgPath(imgPath);
            msg.setImgurl(imgUrl);
        } else if (type.equals(Message.TYPE_GIFT)) {
            msg.setGiftid(giftId);
            msg.setGiftnum(giftNum);
        } else if (type.equals(Message.TYPE_VIDEO)) {
            msg.setVideoPath(videoPath);
            msg.setVideoUrl(videoUrl);
            msg.setImgurl(videoUrl);
            msg.setImgPath(videoPath);
        } else if (type.equals(Message.TYPE_AUDIO)) {
            msg.setAudioPath(audioPath);
            msg.setAudioUrl(audioUrl);
            msg.setDuration(duration);
        }
        msg.setExtraId(sendId);
        msg.setStatus(Message.MSG_STATUS_SENDING);
        return msg;
    }

    //使用emoji来标记isHello
    private Message generateTextSendMsg(String myUid, String peerUid, String text, String isHello) {
        return generateSendMsg(Message.TYPE_TEXT, peerUid, myUid, peerUid, text, isHello, "", "", "", 0, "", "", "", "", 0);
    }

    private Message generateEmojiSendMsg(String myUid, String peerUid, String emoji) {
        return generateSendMsg(Message.TYPE_EMOJI, peerUid, myUid, peerUid, "", emoji, "", "", "", 0, "", "", "", "", 0);
    }

    private Message generateImgSendMsg(String myUid, String peerUid, String imgPath, String imgUrl) {
        return generateSendMsg(Message.TYPE_IMAGE, peerUid, myUid, peerUid, "", "", imgPath, imgUrl, "", 0, "", "", "", "", 0);
    }

    private Message generateGiftSendMsg(String myUid, String peerUid, String giftId, int giftNum) {
        return generateSendMsg(Message.TYPE_GIFT, peerUid, myUid, peerUid, "", "", "", "", giftId, giftNum, "", "", "", "", 0);
    }

    private Message generateVideoSendMsg(String myUid, String peerUid, String videoPath, String videoUrl) {
        return generateSendMsg(Message.TYPE_VIDEO, peerUid, myUid, peerUid, "", "", "", "", "", 0, videoPath, videoUrl, "", "", 0);
    }

    public void SendUserMessage(UserInfo peerUser, Message msg, String translang, String isHello) {
        if (msg.getType().equals(Message.TYPE_TEXT)) {
            _SendUserTextMessage(peerUser, msg.getText(), translang, msg, Message.MSG_STATUS_RESENDING, isHello);
        } else if (msg.getType().equals(Message.TYPE_EMOJI)) {
            _SendUserEmojiMessage(peerUser, msg.getEmoji(), msg, Message.MSG_STATUS_RESENDING);
        } else if (msg.getType().equals(Message.TYPE_GIFT)) {
            _SendUserGiftMessage(peerUser, msg.getGiftid(), msg.getGiftnum(), msg, null, Message.MSG_STATUS_RESENDING);
        }
    }

    /**
     * @param peerUser  对方用户信息
     * @param text      发送的文本
     * @param translang 需要翻译成的语言
     */
    public void SendUserTextMessage(String myUid, UserInfo peerUser, String text, String translang, String ishello) {
//        Message msg = generateTextSendMsg(myUid, peerUser.uid, text, ishello);
//        _SendUserTextMessage(peerUser, text, translang, msg, Message.MSG_STATUS_SENDING, ishello);
    }

    private void _SendUserTextMessage(UserInfo peerUser, String text, String translang, Message msg, int status, String ishello) {
//        dispatchPrivateMsg(new UserMessage(peerUser, msg, status, msg.getExtraId()));
//        ChatIMBiz.getInstance().sendText(null, peerUser.uid, text, translang, ishello, new SendMsgRequestListener(peerUser, msg.getExtraId()));
    }

    /**
     * @param peerUser 对方用户信息
     * @param emoji    发送的Emoji
     */
    public void SendUserEmojiMessage(String myUid, UserInfo peerUser, String emoji) {
//        Message msg = generateEmojiSendMsg(myUid, peerUser.uid, emoji);
//        _SendUserEmojiMessage(peerUser, emoji, msg, Message.MSG_STATUS_SENDING);
    }

    private void _SendUserEmojiMessage(UserInfo peerUser, String emoji, Message msg, int status) {
//        dispatchPrivateMsg(new UserMessage(peerUser, msg, status, msg.getExtraId()));
//        ChatIMBiz.getInstance().sendEmoji(null, peerUser.uid, emoji, new SendMsgRequestListener(peerUser, msg.getExtraId()));
    }

    /**
     * @param peerUser 对方用户信息
     * @param giftId   礼物Id
     * @param giftNum  礼物数量
     */
    public void SendUserGiftMessage(String myUid, UserInfo peerUser, String giftId, int giftNum, SendGiftCallback callback) {
//        Message msg = generateGiftSendMsg(myUid, peerUser.uid, giftId, giftNum);
//        _SendUserGiftMessage(peerUser, giftId, giftNum, msg, callback, Message.MSG_STATUS_SENDING);
    }

    private void _SendUserGiftMessage(UserInfo peerUser, String giftId, int giftNum, Message msg, SendGiftCallback callback, int status) {
//        dispatchPrivateMsg(new UserMessage(peerUser, msg, status, msg.getExtraId()));
//        ChatIMBiz.getInstance().sendGift(null, peerUser.uid, giftId, giftNum, new SendGiftRequestListener(peerUser, msg.getExtraId(), callback));

    }

    private void dispatch(String rawMsg, String roomId, int channel, boolean isOffline) {
        if (disableReceiveMsg != null && disableReceiveMsg.isDisableReceiveMsg()) {
            return;
        }
        switch (channel) {
            case 1001:
                room(roomId).chmsg.setValue(GsonSafetyUtils.getInstance().fromJson(rawMsg, RTTxtMsg.class));
                break;

            default:
                break;
        }
    }

    private void addULog(String rawMsg) {
        try {
            ULog.i(TAG, "socket msg: " + rawMsg);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    //全局直播间消息
    public static class GlobalRoom {
        public LiveCallback<RoomBannerGift> bannergit = new LiveCallback<>();//横幅
        public LiveCallback<MsgConfigModel.Item> templatemsg = new LiveCallback<>(); //通用模板消息
    }

    public static class PK {
    }

    /**
     * 社交
     */
    public static class User {
        public LiveCallback<UserMessage> privatemsg = new LiveCallback<>();//私信消息

        public static class Call {
            public LiveCallback<RoomGift> miChatGift = new LiveCallback<>();
            public LiveCallback<RoomGift> miChatGiftEnd = new LiveCallback<>();
        }

        public Call call = new Call();
    }

    private LinkedHashMap<String, Room> roomMap = new LinkedHashMap<>();
    public User user = new User();
    public GlobalRoom globalRoom = new GlobalRoom();

    static final String TAG = "IMSocketBase";

    public Room room(String roomId) {
        if (roomMap.containsKey(roomId)) {
            return roomMap.get(roomId);
        }
        Room room = new Room();
        roomMap.put(roomId, room);
        return room;
    }

    public void setJoinGroup(String roomId) {
        room(roomId).isJoinGroup = true;
    }

    public void setQuitGroup(String roomId) {
        roomMap.remove(roomId);
    }

    public String getLastGroup() {
        Map.Entry<String, Room> entry = getTail(roomMap);
        if (entry != null && entry.getValue().isJoinGroup) {
            return entry.getKey();
        }
        return "";
    }

    public <K, V> Map.Entry<K, V> getTail(LinkedHashMap<K, V> map) {
        Iterator<Map.Entry<K, V>> iterator = map.entrySet().iterator();
        Map.Entry<K, V> tail = null;
        while (iterator.hasNext()) {
            tail = iterator.next();
        }
        return tail;
    }

    public void parseMsg(String msg, boolean isOffline, long op) {
        if (TextUtils.isEmpty(msg)) {
            return;
        }
        ULog.d(TAG, msg);

        JSONObject jsonObj = null;
        try {
            jsonObj = new JSONObject(msg);
        } catch (Exception e) {
            ULog.e(TAG, "receivedMessage json error");
        }

        if (jsonObj == null) {
            return;
        }
        try {
            String roomId = jsonObj.getString("room_id");
            int operation = (int) op;
            if (operation > 0) {
                dispatch(msg, roomId, operation, isOffline);
            }
        }catch (Exception e){
            e.printStackTrace();
        }

    }

    /**
     * 直播间
     */
    public static class Room {
        private boolean isJoinGroup = false;
        public LiveCallback<RTTxtMsg> chmsg = new LiveCallback<>();//弹幕
        public LiveCallback<RoomGift> roomgift = new LiveCallback<>();//送礼物
        public LiveCallback<RoomGift> roomgiftend = new LiveCallback<>();//送礼物结束
        public LiveCallback<String> forbidroom = new LiveCallback<>();//直播间封禁
        public LiveCallback<MsgConfigModel.Item> templatemsg = new LiveCallback<>(); //通用模板消息
    }

    private RoomSystemMessage createCustomRoomSystemMessage(String msg) {
        return new RoomSystemMessage("", "", msg, "#ffffff", "", "");
    }

    private <T> T fromJson(String json, Class<T> classOfT, String rawMsg) {
        T obj = GsonSafetyUtils.getInstance().fromJson(json, classOfT);

        return obj;
    }

    private LinkedList<String> recentMsgs = new LinkedList<>();

    public List<String> getRecentMsgs() {
        return recentMsgs;
    }

    public void addRecentMsg(String msg) {
        if (!TextUtils.isEmpty(msg)) {
            if (recentMsgs.size() > 5) {
                recentMsgs.removeFirst();
            }
            recentMsgs.add(System.currentTimeMillis() + "|" + msg + "\n");
        }
    }

    @Override
    public void initIM(String key, boolean isDebug, String naviServer, String fileServer) {
        if (iSocket == null) {
            return;
        }
        iSocket.initIM(key, isDebug, naviServer, fileServer);
        ULog.i(TAG, "initIM");
    }

    @Override
    public void login(String uid, String roomId, IMTokenModel model, EventCallback eventCallback) {
        if (iSocket == null) {
            return;
        }
        IMSocketBase.instance().uid = uid;
        iSocket.login(uid, roomId, model, eventCallback);
        ULog.i(TAG, "login override");
    }



    @Override
    public void signOut() {
        if (iSocket == null) {
            return;
        }
        iSocket.signOut();
        ULog.i(TAG, "signOut");
    }

    @Override
    public void creatGroup(String userId, String groupId, EventCallback eventCallback) {
        if (iSocket == null) {
            return;
        }
        iSocket.creatGroup(userId, groupId, eventCallback);
    }

    @Override
    public void joinGroup(String userId, String groupId, EventCallback eventCallback) {
        if (iSocket == null) {
            return;
        }
        iSocket.joinGroup(userId, groupId, eventCallback);
    }

    @Override
    public void quitGroup(String groupId, EventCallback eventCallback) {
        if (iSocket == null) {
            return;
        }

        iSocket.quitGroup(groupId, eventCallback);
    }

    @Override
    public void deleteGroup(String groupId) {
        if (iSocket == null) {
            return;
        }

        iSocket.deleteGroup(groupId);
    }

    @Override
    public void sendMessageBySocket(String peer, String msg, ChatSendCallback chatSendCallback) {
        if (iSocket == null) {
            return;
        }

        iSocket.sendMessageBySocket(peer, msg, chatSendCallback);
    }
}
