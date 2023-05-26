package com.rongtuoyouxuan.chatlive.crtdatabus.config;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.text.TextUtils;

import com.rongtuoyouxuan.chatlive.crtbiz2.model.config.BaseConfigModel;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.config.ClientConfModel;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.config.MsgConfigModel;
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.gift.GiftListModel;
import com.rongtuoyouxuan.chatlive.crtdatabus.language.LocaleManager;
import com.rongtuoyouxuan.chatlive.crtnet.RequestListener;
import com.rongtuoyouxuan.chatlive.crtutil.arch.LiveCallback;
import com.rongtuoyouxuan.chatlive.crtbiz2.config.ConfigBiz;
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus;
import com.rongtuoyouxuan.chatlive.crtdatabus.login.IloginListener;
import com.rongtuoyouxuan.chatlive.crtutil.download.SimpleDownload;
import java.io.File;
import java.util.HashMap;

public class ConfigManager extends ConfigManagerExt {
    private final static String baseConfigFile = "baseconf.json";
    private final static String giftlistFile = "giftlist.json";
    private final static String emojilistFile = "emojilist.json";
    private final static String screenADFile = "screen.json";
    private final static String giftPath = "gift/";
    private final static String adPath = "screen/";
    private final static int MAX_RETRY = 5;
    /* staticConf 加载成功后回调 */
    public LiveCallback<Void> staticConfLiveCallback = new LiveCallback<>();
    private SimpleDownload simpleDownload;
    private ClientConfModel clientConfModel = new ClientConfModel();
    private String appDataFilePath;
    private String cacheFilePath;
    private int retryGetRCConf = 0;
    private Handler handler = new Handler(Looper.getMainLooper());
    private Context context;
    private HashMap<String, Bitmap> medalResource = new HashMap<>();
    private GiftListModel giftListModel = new GiftListModel();
    private MsgConfigModel msgConfigModel = new MsgConfigModel();


    public ConfigManager() {

    }

    public void init(Context context) {
        this.context = context;
        appDataFilePath = context.getApplicationContext().getExternalFilesDir(null) + File.separator;
        cacheFilePath = context.getApplicationContext().getExternalCacheDir() + File.separator;

        simpleDownload = new SimpleDownload(DataBus.instance().getAppContext());
        simpleDownload.start();
        loadRCConfig();
        loadBaseConfig();
    }

    public void loadBaseConfig() {
        clientConfModel = new ClientConfModel();
        //getClientConf();
    }

    public void loadRCConfig() {
//        ConfigBiz.getInstance().getRCConfig(null, new RequestListener<RCConfigModel>() {
//            @Override
//            public void onSuccess(String reqId, RCConfigModel result) {
//                rcConfigModelMutableLiveData.setValue(result);
//            }
//
//            @Override
//            public void onFailure(String reqId, String errCode, String msg) {
//                if (retryGetRCConf < MAX_RETRY) {
//                    retryGetRCConf++;
//                    loadRCConfig();
//                } else {
//                    rcConfigModelMutableLiveData.setValue(null);
//                }
//            }
//        });
    }

    public BaseConfigModel getBaseConfig() {
        return clientConfModel.getData().getBaseconf();
    }

    public ClientConfModel getClientConfig() {
        return clientConfModel;
    }

    public boolean isDisableReceiveMsg() {
        return clientConfModel.getData().refusepmsgswitch.status == 1;
    }

    public boolean isMlOpen() {
        return clientConfModel.getData().mlswtich.status == 1;
    }

    //获取徽章资源
    public Bitmap getMedalConfig(String key) {
        return medalResource.get(key);
    }


    public void getClientConf(final IloginListener loginListener) {
        ConfigBiz.getInstance().getClientConf(null, new RequestListener<ClientConfModel>() {
            @Override
            public void onSuccess(final String reqId, ClientConfModel result) {
                if (result.errCode == 0) {
                    //0不显示，1显示
                    //isGooglePlay  true 不显示，false 显示
                    DataBus.instance().setIsGooglePlay(result.getData().getPay().getSwitchX() == 0);
                }

                clientConfModel = result;
                DataBus.instance().getMainHandler().postDelayed(new Runnable() {
                    @Override
                    public void run() {
//                        setMedalResource();
                    }
                }, 2000);

                if (loginListener != null) {
                    loginListener.onSuccess();
                }
            }

            @Override
            public void onFailure(String reqId, String errCode, String msg) {
                DataBus.instance().setIsGooglePlay(true);
                if (loginListener != null) {
                    loginListener.onSuccess();
                }
            }
        });
    }

    public ADData getScreenAD() {
        return null;
    }

    public Bitmap getScreenADBitmap(String imgUrl) {
        File file = new File(cacheFilePath + adPath + SimpleDownload.getFileNameWithoutExtFromURL(imgUrl));
        Bitmap bitmap = BitmapFactory.decodeFile(file.getAbsolutePath());
        if (bitmap == null) {
            file.delete();
        }
        return bitmap;
    }

    public void loadClientConfig() {
        ConfigLoader.INSTANCE.loadConfig();
    }

    public ClientConfModel getAppConfig() {
        return ConfigLoader.INSTANCE.getClientModel();
    }

    public static class ADData {
        public String action;
        public String actionVal;
        public String imgUrl;
        public int time;
    }

    public GiftListModel getGiftList() {
        return giftListModel;
    }
    public GiftListModel.DataBean getGiftItem(String giftId) {
        if (giftListModel == null || giftListModel.data == null) {
            return null;
        }
        if (giftListModel.data.isEmpty()) {
            return null;
        }

        for (GiftListModel.DataBean item : giftListModel.data) {
            if (item.id.equalsIgnoreCase(giftId)) {
                return item;
            }
        }
        return null;
    }

    public GiftLocalInfo getGiftDesc(GiftListModel.DataBean item) {
        String desc = "";
        String name = "";
        if (item != null) {
            switch (DataBus.instance().getLocaleManager().getLanguage().getValue()) {
                case LocaleManager.LANGUAGE_ENGLISH:
                    desc = item.lang.desc.en;
                    name = item.lang.name.en;
                    break;

                case LocaleManager.LANGUAGE_SIMPLIFIED_CHINESE:
                    desc = item.lang.desc.zh;
                    name = item.lang.name.zh;
                    break;

                case LocaleManager.LANGUAGE_ZH_TW:
                    desc = item.lang.desc.zhtw;
                    name = item.lang.name.zhtw;
                    break;

                case LocaleManager.LANGUAGE_TH:
                    desc = item.lang.desc.th;
                    name = item.lang.name.th;
                    break;

            }
            if (TextUtils.isEmpty(desc)) {
                desc = item.lang.desc.en;
            }
            if (TextUtils.isEmpty(name)) {
                name = item.lang.name.en;
            }
        }
        return new GiftLocalInfo(name, desc);
    }

    public GiftLocalInfo getGiftDesc(String giftId) {
        GiftListModel.DataBean item = getGiftItem(giftId);
        return getGiftDesc(item);
    }
    public String getGiftSmallImagesFolder(String giftId) {
        GiftListModel.DataBean item = getGiftItem(giftId);
        if (item == null) {
            return "";
        }
        if (TextUtils.isEmpty(item.smallres)) {
            return "";
        }
        String path = cacheFilePath + giftPath + SimpleDownload.getFileNameWithoutExtFromURL(item.smallres);
        if (SimpleDownload.isFileExist(path + "/001")) {
            return path;
        }
        return "";
    }

    public String getGiftBigImagesFolder(String giftId) {
        GiftListModel.DataBean item = getGiftItem(giftId);
        if (item == null) {
            return "";
        }
        if (TextUtils.isEmpty(item.bigres)) {
            return "";
        }
        String path = cacheFilePath + giftPath + SimpleDownload.getFileNameWithoutExtFromURL(item.bigres);
        if (SimpleDownload.isFileExist(path + "/001")) {
            return path;
        }
        return "";
    }

    public String getGiftBigSvgaFolder(String giftId) {
        GiftListModel.DataBean item = getGiftItem(giftId);
        if (item == null) {
            return "";
        }
        if (TextUtils.isEmpty(item.svgabigres)) {
            return "";
        }
        String path = cacheFilePath + giftPath + SimpleDownload.getFileNameWithoutExtFromURL(item.svgabigres);
        if (SimpleDownload.isFileExist(path + "/001.svga")) {
            return path;
        }
        return "";
    }

    public String getGiftSmallSvgaFolder(String giftId) {
        GiftListModel.DataBean item = getGiftItem(giftId);
        if (item == null) {
            return "";
        }
        if (TextUtils.isEmpty(item.svgasmallres)) {
            return "";
        }
        String path = cacheFilePath + giftPath + SimpleDownload.getFileNameWithoutExtFromURL(item.svgasmallres);
        if (SimpleDownload.isFileExist(path + "/001.svga")) {
            return path;
        }
        return "";
    }

    public MsgConfigModel getTemplatemsgConf() {
        return msgConfigModel;
    }

    public String getGiftIcon(String giftId) {
        GiftListModel.DataBean item = getGiftItem(giftId);
        if (item != null) {
            return item.icon;
        }
        return "";
    }

    public static class GiftLocalInfo {
        public String name;
        public String desc;

        public GiftLocalInfo(String name, String desc) {
            this.name = name;
            this.desc = desc;
        }
    }
}
