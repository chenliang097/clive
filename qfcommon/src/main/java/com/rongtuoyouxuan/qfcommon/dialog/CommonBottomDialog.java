package com.rongtuoyouxuan.qfcommon.dialog;

import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.ConversationTypes.TYPE_CHATROOM;
import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.ConversationTypes.TYPE_GROUP;
import static com.rongtuoyouxuan.chatlive.biz2.model.im.constants.ConversationTypes.TYPE_PRIVATE;

import android.content.Context;
import android.content.ContextWrapper;
import android.content.DialogInterface;
import android.text.TextUtils;
import android.widget.EditText;

import androidx.lifecycle.LifecycleOwner;
import androidx.lifecycle.Observer;

import com.blankj.utilcode.util.KeyboardUtils;
import com.blankj.utilcode.util.StringUtils;
import com.blankj.utilcode.util.Utils;
import com.rongtuoyouxuan.chatlive.basebizwrapper.R;
import com.rongtuoyouxuan.chatlive.log.upload.ULog;
import com.rongtuoyouxuan.libuikit.widget.ConfirmBottomSheetDialog;
import com.rongtuoyouxuan.libuikit.widget.SimpleInputDialog;
import com.rongtuoyouxuan.chatlive.util.LaToastUtil;
import com.zhihu.matisse.dialog.DiySystemDialog;
import com.rongtuoyouxuan.qfcommon.util.AccessPermission;

import java.util.List;

/**
 * @Description :拉黑、举报、禁言、移除房间通用
 * @Author : jianbo
 * @Date : 2022/8/11  20:14
 */
public class CommonBottomDialog {

    private static final String TAG = "CommonBottomDialog";

    private Context context;
    private int sceneType;

    private static BottomDialogViewModel viewModel;

    //禁言用户
    private final int OPERATE_MUTE_USER = 0;
    //移除群聊
    private final int OPERATE_REMOVE_USER = 1;
    //拉黑
    private final int OPERATE_ADD_BLACKLIST = 2;
    //取消拉黑
    private final int OPERATE_REMOVE_BLACKLIST = 3;

    //是否被禁言
    private boolean isGroupMute;
    private boolean isChatRoomMute;
    private boolean isChatRoomKick;

    /**
     * 构造器
     *
     * @param context   //     * @param sceneType 底部禁言、移出、拉黑、举报、取消Dialog弹出场景
     *                  type ----> 独立举报-1 私聊0  群聊1  直播间2
     * @param sourceId  来源id，直播间传roomId  群groupId  用户userId
     * @param anchorId  直播间时需要传 主播id
     * @param targetIds 屏蔽ID（被拉黑、被禁言、被举报人的id） 举报时size为1的list
     */
    public CommonBottomDialog(Context context, String sourceId, String anchorId, List<String> targetIds) {
        this.context = context;
        this.sceneType = sceneType;
        //        viewModel = new BottomDialogViewModel(Utils.getApp());
        viewModel = new BottomDialogViewModel(Utils.getApp(), sourceId, anchorId, targetIds);
    }

    private LifecycleOwner getLifecycleOwner(Context context) {
        while (!(context instanceof LifecycleOwner)) {
            context = ((ContextWrapper) context).getBaseContext();
        }
        return (LifecycleOwner) context;
    }


    /**
     * 私聊中显示底部对话框
     *
     * @param isBlock 是否已被拉黑
     */
    public void showPrivateDialog(boolean isBlock) {
        ConfirmBottomSheetDialog.Companion.showDialog(context,
                isBlock ? context.getString(R.string.mine_userinfo_cancel_block_tv) :
                        context.getString(R.string.chat_message_operate_pullblack),
                context.getString(R.string.chat_message_operate_report),
                null,
                null,
                StringUtils.getString(R.string.chat_message_operate_cancel),
                new ConfirmBottomSheetDialog.CallBack() {
                    @Override
                    public void callBack(int value) {
                        switch (value) {
                            case ConfirmBottomSheetDialog.centerItem0:
                                if (isBlock) {
                                    showConfirmDialog(OPERATE_REMOVE_BLACKLIST, TYPE_PRIVATE);
                                } else {
                                    showConfirmDialog(OPERATE_ADD_BLACKLIST, TYPE_PRIVATE);
                                }
                                break;
                            case ConfirmBottomSheetDialog.centerItem1:
                                showReportDialog(TYPE_PRIVATE);
                                break;
                            default:
                                LaToastUtil.showShort("取消");
                                break;
                        }
                    }

                    @Override
                    public void cancel() {
                        LaToastUtil.showShort("取消");
                    }
                });
    }

    /**
     * 直播间显示举报对话框
     */
    public void showChatRoomReportDialog() {
        showReportDialog(TYPE_CHATROOM);
    }

    /**
     * 私聊举报
     */
    public void showPrivateReportDialog() {
        showReportDialog(TYPE_PRIVATE);
    }

    /**
     * 群管理页显示举报对话框
     */
    public void showGroupReportDialog() {
        showReportDialog(TYPE_GROUP);
    }

    /**
     * 对外显示底部对话框
     *
     * @param isShowReport 是否显示举报
     */
    public void showManagerDialog(String conversationType, boolean isShowReport) {
        viewModel.getUserAccessPermission(conversationType);
        viewModel.permissionsLiveData.observe(getLifecycleOwner(context), new Observer<Integer>() {
            @Override
            public void onChanged(Integer integer) {
                isGroupMute = AccessPermission.isGroupMute(integer);
                isChatRoomMute = AccessPermission.isChatRoomMute(integer);
                isChatRoomKick = AccessPermission.isChatRoomKick(integer);
                showBottomDialog(conversationType, isShowReport);
            }
        });
    }

    /**
     * 显示底部对话框
     */
    private void showBottomDialog(String conversationType, boolean isShowReport) {
        String muteText = "";
        String movedText = "";
        if (conversationType.equalsIgnoreCase(TYPE_CHATROOM)) {
            muteText = isChatRoomMute ? StringUtils.getString(R.string.chat_message_operate_unmute)
                    : StringUtils.getString(R.string.chat_message_operate_mute);
            movedText = StringUtils.getString(R.string.chat_message_operate_moveout_room);
        }

        ConfirmBottomSheetDialog.Companion.showDialog(context,
                muteText, movedText,
                StringUtils.getString(R.string.chat_message_operate_pullblack),
                isShowReport ? StringUtils.getString(R.string.chat_message_operate_report) : null,
                StringUtils.getString(R.string.chat_message_operate_cancel),
                new ConfirmBottomSheetDialog.CallBack() {
                    @Override
                    public void callBack(int value) {
                        switch (value) {
                            case ConfirmBottomSheetDialog.centerItem0:
//                                showConfirmDialog(OPERATE_MUTE_USER, conversationType);
                                viewModel.mute(isGroupMute, isChatRoomMute, conversationType);
                                break;
                            case ConfirmBottomSheetDialog.centerItem1:
                                showConfirmDialog(OPERATE_REMOVE_USER, conversationType);
                                break;
                            case ConfirmBottomSheetDialog.centerItem2:
                                showConfirmDialog(OPERATE_ADD_BLACKLIST, conversationType);
                                break;
                            case ConfirmBottomSheetDialog.centerItem3:
                                showReportDialog(conversationType);
                                break;
                            default:
//                                LaToastUtil.showShort("取消");
                                break;
                        }
                    }

                    @Override
                    public void cancel() {
//                        LaToastUtil.showShort("取消");
                    }
                });
    }


    /**
     * 二次确认Dialog
     */
    private void showConfirmDialog(int operateType, String conversationType) {
        String title = "";
        String content = "";
        if (operateType == OPERATE_MUTE_USER) {
            if (conversationType.equalsIgnoreCase(TYPE_GROUP)) {
                title = context.getString(R.string.chat_message_operate_mute);
                content = context.getString(R.string.operate_group_mute_hint);
            } else if (conversationType.equalsIgnoreCase(TYPE_CHATROOM)) {
                title = context.getString(R.string.chat_message_operate_mute);
                content = context.getString(R.string.operate_room_mute_hint);
            }
        } else if (operateType == OPERATE_ADD_BLACKLIST) {
            title = context.getString(R.string.chat_message_operate_pullblack);
            content = context.getString(R.string.chat_message_operate_pullblack_hint);
        } else if (operateType == OPERATE_REMOVE_BLACKLIST) {
//            title = context.getString(R.string.chat_message_operate_pullblack);
            title = null;
            content = context.getString(R.string.mine_userinfo_add_block_tv);
        }
        new DiySystemDialog.Builder(context)
                .setTitle(title)
                .setMessage(content)
                .setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dialog.dismiss();
                    }
                }).setPositiveButton(StringUtils.getString(R.string.login_ok), new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        if (operateType == OPERATE_REMOVE_USER) {
                            viewModel.removeUser(conversationType);
                        } else if (operateType == OPERATE_ADD_BLACKLIST) {
                            viewModel.addBlacklist(conversationType);
                        } else if (operateType == OPERATE_REMOVE_BLACKLIST) {
                            viewModel.removeBlacklist(conversationType);
                        }
                        dialog.dismiss();
                    }
                })
                .create().show();
    }

    /**
     * 显示举报对话框
     */
    private void showReportDialog(String conversationType) {
        SimpleInputDialog dialog = new SimpleInputDialog(context);
        dialog.setTitleText(StringUtils.getString(R.string.chat_message_operate_report));
        dialog.setInputHint(StringUtils.getString(R.string.chat_message_operate_report_input_hint));
        dialog.setCancelText(StringUtils.getString(R.string.chat_message_operate_cancel));
        dialog.setConfirmText(StringUtils.getString(R.string.chat_message_operate_submit));
        dialog.setShowCountLimit(true);
        dialog.setInputDialogListener(
                new SimpleInputDialog.InputDialogListener() {
                    @Override
                    public boolean onConfirmClicked(EditText input) {
                        String reportContent = input.getText().toString();
                        if (!TextUtils.isEmpty(reportContent)) {
                            viewModel.saveReport(conversationType, reportContent);
                            viewModel.saveReportSuccess.observe(getLifecycleOwner(context), new Observer<Boolean>() {
                                @Override
                                public void onChanged(Boolean aBoolean) {
                                    ULog.d(TAG, "saveReportSuccess ---> " + aBoolean);
                                    KeyboardUtils.hideSoftInput(input);
                                }
                            });
                        } else {
                            LaToastUtil.showShort(StringUtils.getString(R.string.toast_report_content_empty));
                        }
                        return true;
                    }
                });
        dialog.show();
    }

}
