package com.rongtuoyouxuan.chatlive.crtbiz2.model.language;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;

import java.util.ArrayList;
import java.util.List;

public class LanguageCodeModel extends BaseModel {

    /**
     * name : 中文简体
     * code : zh
     */

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        private String name = "";
        private String code = "";
        private boolean isChecked = false;

        public boolean isChecked() {
            return isChecked;
        }

        public void setChecked(boolean checked) {
            isChecked = checked;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getCode() {
            return code;
        }

        public void setCode(String code) {
            this.code = code;
        }
    }
}
