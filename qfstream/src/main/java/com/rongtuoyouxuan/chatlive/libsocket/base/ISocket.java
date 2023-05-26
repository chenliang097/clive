package com.rongtuoyouxuan.chatlive.libsocket.base;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.im.response.IMTokenModel;

public interface ISocket {
    void initIM(String key, boolean isDebug, String naviServer, String fileServer);

    void login(String uid, String roomId, IMTokenModel model, EventCallback eventCallback);

    void signOut();

    void creatGroup(String userId, String groupId, EventCallback eventCallback);

    void joinGroup(String userId, String groupId, EventCallback eventCallback);

    void quitGroup(String groupId, EventCallback eventCallback);

    void deleteGroup(String groupId);

     void sendMessageBySocket(String peer, String msg, final ChatSendCallback chatSendCallback);
}
