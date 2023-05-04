package com.rongtuoyouxuan.qfcommon.dialog;

import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.ConversationTypes.TYPE_CHATROOM;
import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.ConversationTypes.TYPE_GROUP;
import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.ConversationTypes.TYPE_PRIVATE;
import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.RequestConstants.CHATROOM;
import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.RequestConstants.GROUP;
import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.RequestConstants.USER;

import android.app.Application;
import android.text.TextUtils;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.blankj.utilcode.util.StringUtils;
import com.rongtuoyouxuan.chatlive.biz2.im.ChatIMBiz;
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.BlacklistRequest;
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.ChatRoomRequest;
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.GroupRequest;
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.MuteRequest;
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.SaveReportRequest;
import com.rongtuoyouxuan.chatlive.biz2.model.im.request.SetRoomManagerRequest;
import com.rongtuoyouxuan.chatlive.biz2.model.im.response.OperateResultModel;
import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;
import com.rongtuoyouxuan.qfcommon.R;
import com.rongtuoyouxuan.chatlive.util.LaToastUtil;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * ————————————————————————————————————————
 *
 * @Description :拉黑、举报、禁言、移除房间通用
 * @Author : jianbo
 * @Date : 2022/7/16  16:09
 * ————————————————————————————————————————
 */
public class BottomDialogViewModel extends AndroidViewModel {

    private static final String TAG = BottomDialogViewModel.class.getSimpleName();

    public MutableLiveData<Integer> permissionsLiveData = new MutableLiveData();
    public MutableLiveData<Boolean> saveReportSuccess = new MutableLiveData();

    protected String sourceId;
    protected String anchorId;
    protected List<String> targetIds;

    public BottomDialogViewModel(@NonNull Application application) {
        super(application);
    }

    public BottomDialogViewModel(@NonNull Application application, String sourceId, String anchorId, List<String> targetIds) {
        super(application);
        this.sourceId = sourceId;
        this.anchorId = anchorId;
        this.targetIds = targetIds;
    }

    private String getSource(String conversationType) {
        String source = "";
        if (conversationType.equalsIgnoreCase(TYPE_PRIVATE)) {
            source = USER;
        } else if (conversationType.equalsIgnoreCase(TYPE_GROUP)) {
            source = GROUP;
        } else if (conversationType.equalsIgnoreCase(TYPE_CHATROOM)) {
            source = CHATROOM;
        }
        return source;
    }

    /**
     * 获取用户访问权限
     */
    public void getUserAccessPermission(String conversationType) {
        Map<String, Object> map = new HashMap<>();
        map.put("access_user_id", targetIds.get(0));
        if (conversationType.equalsIgnoreCase(TYPE_GROUP)) {
            map.put("group_id", sourceId);
        } else if (conversationType.equalsIgnoreCase(TYPE_CHATROOM)) {
            //群类型 1粉丝群
            map.put("anchor_id", anchorId);
            map.put("chatroom_id", sourceId);
        }
        ChatIMBiz.INSTANCE.getUserAccessPermission(map, new RequestListener<OperateResultModel>() {
            @Override
            public void onSuccess(String reqId, OperateResultModel result) {
                if (!TextUtils.isEmpty(result.errMsg)) {
                    LaToastUtil.showShort(result.errMsg);
                }
                permissionsLiveData.setValue(result.data.permissions);
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                LaToastUtil.showShort(msg);
            }
        });
    }

    /**
     * 禁言
     */
    public void mute(String userId, String fId, String roomId, String sceneId, boolean isMute) {
        MuteRequest muteRequest = new MuteRequest(userId, fId, roomId, sceneId);

        if (isMute) {
            ChatIMBiz.INSTANCE.cancelMute(muteRequest, new RequestListener<OperateResultModel>() {
                @Override
                public void onSuccess(String reqId, OperateResultModel result) {
                    if (!TextUtils.isEmpty(result.errMsg)) {
                        LaToastUtil.showShort(result.errMsg);
                    } else {
                        LaToastUtil.showShort(StringUtils.getString(R.string.chat_operate_remove_success));
                    }
                }

                @Override
                public void onFailure(String reqId, String errCode, String msg) {
                    LaToastUtil.showShort(msg);
                }
            });
        } else {
            ChatIMBiz.INSTANCE.mute(muteRequest, new RequestListener<OperateResultModel>() {
                @Override
                public void onSuccess(String reqId, OperateResultModel result) {
                    if (!TextUtils.isEmpty(result.errMsg)) {
                        LaToastUtil.showShort(result.errMsg);
                    } else {
                        LaToastUtil.showShort(StringUtils.getString(R.string.stream_banned));
                    }
                }

                @Override
                public void onFailure(String reqId, String errCode, String msg) {
                    LaToastUtil.showShort(msg);
                }
            });
        }
    }

    /**
     * 拉黑
     */
    public void addBlacklist(String fUid, String tUId, String roomId, String fNickName,String tNickName) {
        BlacklistRequest addBlacklistRequest = new BlacklistRequest(fUid, tUId, roomId, fNickName, tNickName);
        ChatIMBiz.INSTANCE.addBlacklist(addBlacklistRequest, new RequestListener<BaseModel>() {
            @Override
            public void onSuccess(String reqId, BaseModel result) {
                if (!TextUtils.isEmpty(result.errMsg)) {
                    LaToastUtil.showShort(result.errMsg);
                } else {
                    LaToastUtil.showShort(StringUtils.getString(R.string.chat_operate_pullblack_success));
                }
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                LaToastUtil.showShort(msg);
            }
        });
    }

    /**
     * 取消拉黑
     */
    public void removeBlacklist(String fUid, String tUId, String roomId, String fNickName,String tNickName) {
        BlacklistRequest addBlacklistRequest = new BlacklistRequest(fUid, tUId, roomId, fNickName, tNickName);
        ChatIMBiz.INSTANCE.removeBlacklist(addBlacklistRequest, new RequestListener<OperateResultModel>() {
            @Override
            public void onSuccess(String reqId, OperateResultModel result) {
                if (!TextUtils.isEmpty(result.errMsg)) {
                    LaToastUtil.showShort(result.errMsg);
                } else {
                    LaToastUtil.showShort(R.string.mine_userinfo_remove_block_success);
                }
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                LaToastUtil.showShort(msg);
            }
        });
    }

    /**
     * 举报
     *
     * @param content 举报内容
     */
    public void setRoomAdmin(String userId, String roomId,String roomAdminId, String uNickName,String rNickName) {
        SetRoomManagerRequest request = new SetRoomManagerRequest(userId, roomId, roomAdminId, uNickName, rNickName);
        ChatIMBiz.INSTANCE.setRoomManager(request, new RequestListener<BaseModel>() {
            @Override
            public void onSuccess(String reqId, BaseModel result) {
                LaToastUtil.showShort(result.errMsg);
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                LaToastUtil.showShort(msg);
            }
        });
    }

    public void saveReport(String conversationType, String content) {
        SaveReportRequest reportRequest = new SaveReportRequest(getSource(conversationType), sourceId, targetIds.get(0), content);
        ChatIMBiz.INSTANCE.saveReport(reportRequest, new RequestListener<BaseModel>() {
            @Override
            public void onSuccess(String reqId, BaseModel result) {
                saveReportSuccess.setValue(true);
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                LaToastUtil.showShort(msg);
            }
        });
    }


}