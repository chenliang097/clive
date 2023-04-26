package com.rongtuoyouxuan.chatlive.webview.jsinterface;


public interface WebviewListener {
    void loginSuccess(final String text);

    void registerSucc(final String text);

    //使用第三方浏览器打开链接
    void openUrl(final String url);

    void close();

    void payComplete(final String text);

    void shareBottomDialog(String url, String shareContext, String shareUrl, String shareTitle);

    void showShareIcon(boolean isVisibility, String url, String shareContext, String shareUrl, String shareTitle);

    void showPreviewBtn(String carId);

    void showQuestionIcon(String url);

    void logout();

    void showToolbar();

    void hideToolbar();

    void showOpenGuard(String uid);

    void openGiftDialog(String id);

    /**
     * h5游戏充值
     */
    void XGPay();

    /**
     * h5游戏内关闭游戏
     */
    void XGGameClose();

    /**
     * webview 中新打开一个WebViewActivity
     */
    void openAppH5(String url, boolean addCommonParam);
}
