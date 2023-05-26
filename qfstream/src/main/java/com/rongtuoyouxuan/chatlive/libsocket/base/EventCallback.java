package com.rongtuoyouxuan.chatlive.libsocket.base;

/**
 * Describe: imsdk操作成功失败监听
 *
 * @author Ning
 * @date 2019/5/25
 */
public interface EventCallback {
    void Success();

    void Error(String code, String desc);
}
