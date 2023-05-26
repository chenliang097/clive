package com.rongtuoyouxuan.chatlive.crtbiz2.model.im.constants;

/**
 * @Description : 聊天室状态监听
 * @Author : jianbo
 * @Date : 2022/8/11  09:45
 */
public class ChatRoomStatus {

    //正在加入聊天室时。
    public static final int STATUS_JOINING = 1;

    //加入聊天室成功时。
    public static final int STATUS_JOINED = 2;

    //聊天室被重置时。
    public static final int STATUS_RESET = 3;

    //退出聊天室时。
    public static final int STATUS_QUITED = 4;

    //聊天室被销毁时。
    public static final int STATUS_DESTROYED = 5;

    //聊天室操作异常时。
    public static final int STATUS_ERROR = 6;

    public int status;
    public DestroyType destroyType;
    public int errorCode;

    public ChatRoomStatus(int status) {
        this.status = status;
    }

    public ChatRoomStatus(int status, DestroyType destroyType) {
        this.status = status;
        this.destroyType = destroyType;
    }

    public ChatRoomStatus(int status, int errorCode) {
        this.status = status;
        this.errorCode = errorCode;
    }

    public enum DestroyType {
        MANUAL,         //用户调用 IM Server API 手动销毁聊天室。
        AUTO,           //IM Server 自动销毁聊天室。
        UNKNOWN;
    }
}