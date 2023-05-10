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
import com.rongtuoyouxuan.libuikit.dialog.BottomDialog;
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
    private boolean isMute;
    private boolean isSuperManager;
    private boolean isRoomManager;

    private boolean isAnchor;
    private String roomId;
    private String sceneId;
    private String anchorId;
    private String tUserId;
    private String tNickName;

    private String fUserId;
    private String fNickName;

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

    public CommonBottomDialog(Context context, String roomId, String sceneId, String anchorId, String fUserId, String fNickName,String tUserId,String tNickName,boolean isSpeak, boolean isRoomManager, boolean isSuperManager) {
        this.context = context;
        this.anchorId = anchorId;
        this.roomId = roomId;
        this.sceneId = sceneId;
        this.fUserId = fUserId;
        this.fNickName = fNickName;
        this.tUserId = tUserId;
        this.tNickName = tNickName;
        this.isMute = isSpeak;
        this.isRoomManager = isRoomManager;
        this.isSuperManager = isSuperManager;
        this.isAnchor = anchorId==fUserId;
        viewModel = new BottomDialogViewModel(Utils.getApp());
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
                                    showConfirmDialog(OPERATE_REMOVE_BLACKLIST);
                                } else {
                                    showConfirmDialog(OPERATE_ADD_BLACKLIST);
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
     * 对外显示底部对话框
     *
     * @param isShowReport 是否显示举报
     */
    public void showManagerDialog(String conversationType, boolean isShowReport) {
        showBottomDialog(conversationType, isShowReport);
    }

    /**
     * 显示底部对话框
     */
    private void showBottomDialog(String conversationType, boolean isShowReport) {
        String muteText = "";
        String movedText = "";
        String roomManagerText = "";
        muteText = isMute ? StringUtils.getString(R.string.chat_message_operate_unmute)
                : StringUtils.getString(R.string.chat_message_operate_mute);
        movedText = StringUtils.getString(R.string.chat_message_operate_moveout_room);
        roomManagerText = isRoomManager?StringUtils.getString(R.string.chat_message_operate_set_manager):StringUtils.getString(R.string.chat_message_operate_cancel_manager);
        BottomDialog.Builder builder = new BottomDialog.Builder(context);
        if(isAnchor || isSuperManager){
            builder.setPositiveButton(roomManagerText,
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            viewModel.setRoomAdmin(fUserId, roomId, tUserId, fNickName, tNickName);
                            dialogInterface.dismiss();

                        }
                    }, R.color.rt_c_3478F6, R.drawable.bg_page_more_top);
        }else{
            builder.setPositiveButton(StringUtils.getString(R.string.chat_message_operate_pullblack),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            showConfirmDialog(OPERATE_REMOVE_USER);
                            dialogInterface.dismiss();
                        }
                    }, R.color.rt_c_3478F6, R.drawable.bg_page_more_top);
        }

        if(isAnchor || isSuperManager) {
            builder.setPositiveButtonThree(StringUtils.getString(R.string.chat_message_operate_pullblack),
                    new DialogInterface.OnClickListener() {
                        @Override
                        public void onClick(DialogInterface dialogInterface, int i) {
                            showConfirmDialog(OPERATE_REMOVE_USER);
                            dialogInterface.dismiss();
                        }
                    }, R.color.rt_c_3478F6);
        }

        builder.setPositiveButtonFour(muteText,
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        viewModel.mute(fUserId, tUserId, roomId, sceneId, isMute);
                        dialogInterface.dismiss();
                    }
                }, R.color.rt_c_3478F6);

        builder.setPositiveButtonFive(StringUtils.getString(R.string.chat_message_operate_report),
                new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialogInterface, int i) {
                        showReportDialog(conversationType);
                        dialogInterface.dismiss();
                    }
                }, R.color.rt_c_3478F6);
        builder.setNegativeButton(R.string.cancel, new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialogInterface, int i) {

            }
        });
        builder.create().show();

    }


    /**
     * 二次确认Dialog
     */
    private void showConfirmDialog(int operateType) {
        String title = "";
        String content = "";
        if (operateType == OPERATE_MUTE_USER) {
            title = context.getString(R.string.chat_message_operate_mute);
            content = context.getString(R.string.operate_room_mute_hint);
        } else if (operateType == OPERATE_REMOVE_USER) {
            title = context.getString(R.string.dialog_note);
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
                        if (operateType == OPERATE_ADD_BLACKLIST) {
                            viewModel.addBlacklist(fUserId, tUserId, roomId, fNickName, tNickName);
                        } else if (operateType == OPERATE_REMOVE_BLACKLIST) {
                            viewModel.removeBlacklist(fUserId, tUserId, roomId, fNickName, tNickName);
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
