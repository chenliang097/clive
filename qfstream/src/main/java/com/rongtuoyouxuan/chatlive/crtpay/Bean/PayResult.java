package com.rongtuoyouxuan.chatlive.crtpay.Bean;

import android.text.TextUtils;

import java.util.Map;

/**
 * @author ASnow
 * @date 2019/1/12
 * 文件   ChatLive
 * 描述  支付宝支付PayResult
 */
public class PayResult {
    private String resultStatus;
    private String result;
    private String memo;

    public PayResult(Map<String, String> rawResult) {
        if (rawResult == null) {
            return;
        }

        for (String key : rawResult.keySet()) {
            if (TextUtils.equals(key, "resultStatus")) {
                resultStatus = rawResult.get(key);
            } else if (TextUtils.equals(key, "result")) {
                result = rawResult.get(key);
            } else if (TextUtils.equals(key, "memo")) {
                memo = rawResult.get(key);
            }
        }
    }

    @Override
    public String toString() {
        return "resultStatus={" + resultStatus + "};memo={" + memo
                + "};result={" + result + "}";
    }

    /**
     * @return the resultStatus
     */
    public String getResultStatus() {
        return resultStatus;
    }

    /**
     * @return the memo
     */
    public String getMemo() {
        return memo;
    }

    /**
     * @return the result
     */
    public String getResult() {
        return result;
    }
}