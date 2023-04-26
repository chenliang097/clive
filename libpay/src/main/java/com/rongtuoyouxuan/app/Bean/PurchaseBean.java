package com.rongtuoyouxuan.app.Bean;

/**
 * Created by Ning on 2020/11/19
 * Describe: 谷歌支付订单信息
 */
public class PurchaseBean {

    /**
     * orderId : GPA.3354-3552-2504-63519
     * packageName : com.haiwaizj.chatlive
     * productId : com.bothlive.newgold.30
     * purchaseTime : 1605770946273
     * purchaseState : 0
     * purchaseToken : kgmpeiiemhfdgalpciaijean.AO-J1Ow8SGFzAvbIcM50lJfuezed6cso1kG32cyywmCFzJwcnWJYfHpDEaA3tGM4fQefZPOCRiVJTHwXa5DbHrQcww5mnuAumyActxFr9nm_HHLPhXXTa28
     * acknowledged : false
     * heheda_id : 5fb61ec70640d951
     */

    private String orderId;
    private String packageName;
    private String productId;
    private long purchaseTime;
    private int purchaseState;
    private String purchaseToken;
    private boolean acknowledged;
    private String heheda_id;
    private String error_code;

    public String getError_code() {
        return error_code;
    }

    public void setError_code(String error_code) {
        this.error_code = error_code;
    }

    public String getOrderId() {
        return orderId;
    }

    public void setOrderId(String orderId) {
        this.orderId = orderId;
    }

    public String getPackageName() {
        return packageName;
    }

    public void setPackageName(String packageName) {
        this.packageName = packageName;
    }

    public String getProductId() {
        return productId;
    }

    public void setProductId(String productId) {
        this.productId = productId;
    }

    public long getPurchaseTime() {
        return purchaseTime;
    }

    public void setPurchaseTime(long purchaseTime) {
        this.purchaseTime = purchaseTime;
    }

    public int getPurchaseState() {
        return purchaseState;
    }

    public void setPurchaseState(int purchaseState) {
        this.purchaseState = purchaseState;
    }

    public String getPurchaseToken() {
        return purchaseToken;
    }

    public void setPurchaseToken(String purchaseToken) {
        this.purchaseToken = purchaseToken;
    }

    public boolean isAcknowledged() {
        return acknowledged;
    }

    public void setAcknowledged(boolean acknowledged) {
        this.acknowledged = acknowledged;
    }

    public String getHeheda_id() {
        return heheda_id;
    }

    public void setHeheda_id(String heheda_id) {
        this.heheda_id = heheda_id;
    }
}
