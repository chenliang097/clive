package com.rongtuoyouxuan.chatlive.crtbiz2.model.user.event;

public class PayEvent {
    public String payType = "";
    public boolean payStatus = false;
    public String payPurchaseId = "";

    public PayEvent(String payType, boolean payStatus) {
        this.payType = payType;
        this.payStatus = payStatus;
    }

    public PayEvent(String payType, boolean payStatus, String payPurchaseId) {
        this.payType = payType;
        this.payStatus = payStatus;
        this.payPurchaseId = payPurchaseId;
    }
}
