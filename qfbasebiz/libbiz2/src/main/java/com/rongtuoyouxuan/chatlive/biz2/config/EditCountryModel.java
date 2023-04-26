package com.rongtuoyouxuan.chatlive.biz2.config;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/6/13
 */
public class EditCountryModel extends BaseModel {

    private List<DataBean> data = new ArrayList<>();

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : Angola
         * code : AO
         */

        private String name = "";
        private String code = "";

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
