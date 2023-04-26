package com.rongtuoyouxuan.chatlive.biz2.model.login.response;

import androidx.annotation.Keep;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;

/*
 *Create by {Mr秦} on 2022/7/29
 */
@Keep
public class UploadFileResponse extends BaseModel {
    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private String key;
        private String name;
        private String url;

        public String getKey() {
            return key == null ? "" : key;
        }

        public void setKey(String key) {
            this.key = key;
        }

        public String getName() {
            return name == null ? "" : name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getUrl() {
            return url == null ? "" : url;
        }

        public void setUrl(String url) {
            this.url = url;
        }
    }
}