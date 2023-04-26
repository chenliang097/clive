package com.rongtuoyouxuan.chatlive.biz2.model.im.constants;

/**
 * @Description : 聊天室成员状态
 * @Author : jianbo
 * @Date : 2022/8/11  09:45
 */
public class ChatRoomMemberStatus {

    public String userId;

    public MemberAction memberAction;

    public ChatRoomMemberStatus(String userId, MemberAction memberAction) {
        this.userId = userId;
        this.memberAction = memberAction;
    }

    public enum MemberAction {
        UNKNOWN(-1),
        MEMBER_JOIN(1),
        MEMBER_QUIT(0);

        int value;

        MemberAction(int value) {
            this.value = value;
        }
    }
}