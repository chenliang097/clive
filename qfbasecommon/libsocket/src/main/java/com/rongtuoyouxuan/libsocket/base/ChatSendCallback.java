package com.rongtuoyouxuan.libsocket.base;

/**
 * Describe: 发送小时成功失败监听
 *
 * @author Ning
 * @date 2019/5/25
 */
public interface ChatSendCallback {
    void sendSuccess(String msg);

    void sendFail(int code, String desc);
}
