package com.rongtuoyouxuan.qfcommon.util;

/**
 * @Description : 用户访问权限
 * @Author : jianbo
 * @Date : 2022/8/19  14:44
 */
public class AccessPermission {

    //1 拉黑了对方 1 << 0
    public static int ADDBLACK_OTHER = 1 << 0;     //1*2的0次方 的二进制 0001

    //2 被对方拉黑 1 << 1
    public static int BLOCKED = 1 << 1;           //1*2的1次方 的二进制 0010

    //4 群组禁言 1 << 2
    public static int GROUP_MUTE = 1 << 2; //1*2的2次方 的二进制 0100

    //8 聊天室禁言 1 << 3
    public static int CHATROOM_MUTE = 1 << 3; //1*2的3次方 的二进制 1000

    //16 被聊天室踢出 1 << 4
    public static int CHATROOM_KICK = 1 << 4; //1*2的4次方 的二进制 1 0000

    /**
     * 该用户在群组 是否禁言
     *
     * @param currentStatus 当前状态
     * @return
     */
    public static boolean isGroupMute(int currentStatus) {
        return (currentStatus & GROUP_MUTE) == GROUP_MUTE;
    }

    /**
     * 该用户在直播间 是否禁言
     *
     * @param currentStatus 当前状态
     * @return
     */
    public static boolean isChatRoomMute(int currentStatus) {
        return (currentStatus & CHATROOM_MUTE) == CHATROOM_MUTE;
    }

    /**
     * 该用户在直播间 是否被踢出
     *
     * @param currentStatus 当前状态
     * @return
     */
    public static boolean isChatRoomKick(int currentStatus) {
        return (currentStatus & CHATROOM_KICK) == CHATROOM_KICK;
    }


    /**
     * 添加某个操作权限 通过或操作实现
     *
     * @param currentStatus 当前状态
     * @param permission
     * @return
     */
    private int append(int currentStatus, int permission) {
        return currentStatus | permission;
    }

    /**
     * 除去某个操作权限 通过非操作 和 与操作共同实现
     *
     * @param currentStatus 当前状态
     * @param permission
     * @return
     */
    private int delete(int currentStatus, int permission) {
        // 如果非操作不好理解，可以理解为 减（－）操作也是可以的
        // currentStatus = currentStatus - more;
        return currentStatus & ~permission;
    }

    /**
     * 是否拥有某个权限 通过与运算判断
     *
     * @param currentStatus 当前状态
     * @param permission
     * @return
     */
    private static boolean hasPermission(int currentStatus, int permission) {
        return (currentStatus & permission) == permission;
    }


}