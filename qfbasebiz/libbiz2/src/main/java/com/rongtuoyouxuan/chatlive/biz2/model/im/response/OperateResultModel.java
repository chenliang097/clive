package com.rongtuoyouxuan.chatlive.biz2.model.im.response;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;

/**
 * ================================================
 * PackageName    com.boboo.chatlive.biz2.model.im.response
 * FileName       UserPermissionModel
 * Created on     2022/8/14 22:01
 * function       用户权限model
 *
 * @author Jason
 * Email          jianbo311@163.com
 * ================================================
 */
public class OperateResultModel extends BaseModel {

    /**
     * data
     */
    @SerializedName("data")
    public DataBean data;


    public static class DataBean {

        /**
         * value : 0
         */

        public int value;

        /**
         * permissions : 0
         */

        public int permissions;

    }
}