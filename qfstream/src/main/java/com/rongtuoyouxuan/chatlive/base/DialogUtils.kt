package com.rongtuoyouxuan.chatlive.base

import android.content.Context
import com.rongtuoyouxuan.chatlive.base.view.dialog.AnchorBlockedTipDialog
import com.rongtuoyouxuan.chatlive.live.view.dialog.AudienceExitDialog
import com.rongtuoyouxuan.chatlive.stream.view.dialog.HostExitDialog
import com.rongtuoyouxuan.chatlive.stream.view.dialog.StartStreamLevelDialog

object DialogUtils {

    fun createHostExitDialog(context: Context, streamId:String?, anchorId:String?, mHostExitDialogListener: HostExitDialog.HostExitDialogListener):HostExitDialog{
        return HostExitDialog(context, streamId, anchorId, mHostExitDialogListener)
    }

    fun createAudienceExitDialog(context: Context, avatar:String?, isFollow:Boolean?, streamType: String, mAudienceExitDialogListener: AudienceExitDialog.AudienceExitDialogListener):AudienceExitDialog{
        return AudienceExitDialog(context, avatar, isFollow, streamType, mAudienceExitDialogListener)
    }

    fun createStartStreamLevelDialog(context: Context, mAudienceMicTipListener: StartStreamLevelDialog.HostLevelListener):StartStreamLevelDialog{
        return StartStreamLevelDialog(context, mAudienceMicTipListener)
    }

    fun createAnchorBlockedTipDialog(context: Context, type:Int, mAnchorBlockedTipDialogListener: AnchorBlockedTipDialog.AnchorBlockedTipDialogListener):AnchorBlockedTipDialog{
        return AnchorBlockedTipDialog(context, type, mAnchorBlockedTipDialogListener)
    }

}