package com.rongtuoyouxuan.chatlive.base.viewmodel;

import androidx.lifecycle.MutableLiveData;
import android.content.Intent;

import com.rongtuoyouxuan.chatlive.arch.LiveEvent;
import com.rongtuoyouxuan.chatlive.base.utils.LiveStreamInfo;
import com.rongtuoyouxuan.chatlive.base.view.model.SendEvent;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AnchorInfo;
import com.rongtuoyouxuan.chatlive.biz2.model.stream.UserCardModel;
import com.rongtuoyouxuan.chatlive.biz2.model.translate.RoomTranslateResponse;
import com.rongtuoyouxuan.chatlive.biz2.translate.TranslateBiz;
import com.rongtuoyouxuan.chatlive.databus.DataBus;
import com.rongtuoyouxuan.chatlive.net2.RequestListener;

/**
 * 推流与直播间公用
 */
public class ControllerViewModel extends LiveBaseViewModel {

    public LiveEvent<String> showToast = new LiveEvent<>();
    public LiveEvent<RoomTranslateResponse> translateResponseMutableLiveData = new LiveEvent<>();//翻译
    public LiveEvent<SendEvent> mMessageButton = new LiveEvent<>();//弹出消息发送控件
    public UserCardModel userCardModel = new UserCardModel();
    public MutableLiveData<AnchorInfo> mHostInfo = new MutableLiveData<>();//主播信息


    public ControllerViewModel(LiveStreamInfo liveStreamInfo) {
        super(liveStreamInfo);
    }

    public void onActivityResultEvent(int requestCode, int resultCode, Intent data) {
    }

    public void translateContent(final String content) {
        RoomTranslateResponse translateResponse = new RoomTranslateResponse();
        translateResponse.data.original = content;
        translateResponse.data.event = RoomTranslateResponse.ROOM_TYPE_START;
        translateResponseMutableLiveData.setValue(translateResponse);
        String translang = DataBus.instance().getLocaleManager().getDefaultTransLanguage();
        TranslateBiz.getInstance().translateRoomContent(null, content, translang, "room", new RequestListener<RoomTranslateResponse>() {
            @Override
            public void onSuccess(String reqId, RoomTranslateResponse result) {
                result.data.original = content;
                result.data.event = RoomTranslateResponse.ROOM_TYPE_END;
                translateResponseMutableLiveData.setValue(result);
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                RoomTranslateResponse translateResponse = new RoomTranslateResponse();
                translateResponse.data.original = content;
                translateResponse.data.event = RoomTranslateResponse.ROOM_TYPE_END;
                try {
                    translateResponse.errCode = Integer.valueOf(errCode);
                } catch (Exception ex) {
                    translateResponse.errCode = -1;
                }
                translateResponseMutableLiveData.setValue(translateResponse);
            }
        });
    }

    public void upUserInfo( String uid){
//        UserCardBiz.INSTANCE.getUserCardInfo(null, uid, new RequestListener<UserCardModel>() {
//            @Override
//            public void onSuccess(String reqId, UserCardModel result) {
//                userCardModel = result;
//            }
//
//            @Override
//            public void onFailure(String reqId, String errCode, String msg) {
//            }
//        });
    }
}
