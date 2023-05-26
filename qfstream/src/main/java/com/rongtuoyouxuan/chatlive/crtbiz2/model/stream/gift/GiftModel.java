package com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.gift;

import com.rongtuoyouxuan.chatlive.crtnet.BaseModel;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

/**
 * Describe:
 *
 * @author Ning
 * @date 2019/6/5
 */
public class GiftModel extends BaseModel {


    /**
     * data : {"items":[{"id":"1226","price":"66","float":"1","vip":"vip"}]}
     */

    private DataBean data = new DataBean();

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        private List<ItemsBean> items = new ArrayList<>();

        public List<ItemsBean> getItems() {
            return items;
        }

        public void setItems(List<ItemsBean> items) {
            this.items = items;
        }

        public static class ItemsBean {
            /**
             * id : 1226
             * price : 66
             * float : 1
             * vip : vip
             */
        // package
//            {
//                "id": "35",
//                    "price": "100",
//                    "float": "0",
//                    "vip": "",
//                    "super": "0",
//                    "bid": "c4bc20e31e3",      //包裹ID
//                    "num": 38,                 //数量
//                    "type": "gift",            //物品类型
//                    "mark": "七彩玫瑰",         //备注
//                    "endtime": "1600000000",   //有效期
//                    "addincome": 1             //是否参与收益
//            }

            private String id = "";
            private String price = "";
            @SerializedName("float")
            private String floatX = "";
            private String vip = "";
            @SerializedName("super")
            private String superX = "";

            // 下面字段在 背包中使用
            @SerializedName("bid")
            private String bid = "";
            @SerializedName("num")
            private int num = 0;
            @SerializedName("endtime")
            private long endtime = 0;
            @SerializedName("mark")
            private String mark = "";
            @SerializedName("type")
            private String type = "";
            /**
             * 是否被选中
             */
            private boolean isChecked = false;

            private int pageIndex = 0;

            public boolean isChecked() {
                return isChecked;
            }

            public void setChecked(boolean checked) {
                isChecked = checked;
            }

            public String getSuperX() {
                return superX;
            }

            public void setSuperX(String superX) {
                this.superX = superX;
            }

            public String getId() {
                return id;
            }

            public void setId(String id) {
                this.id = id;
            }

            public String getPrice() {
                return price;
            }

            public void setPrice(String price) {
                this.price = price;
            }

            public String getFloatX() {
                return floatX;
            }

            public void setFloatX(String floatX) {
                this.floatX = floatX;
            }

            public String getVip() {
                return vip;
            }

            public void setVip(String vip) {
                this.vip = vip;
            }

            public String getBid() {
                return bid;
            }

            public void setBid(String bid) {
                this.bid = bid;
            }

            public int getNum() {
                if(num > 999){
                    num = 999;
                }
                return num;
            }

            public void setNum(int num) {
                this.num = num;
            }

            public long getEndtime() {
                return endtime;
            }

            public void setEndtime(long endtime) {
                this.endtime = endtime;
            }

            public String getMark() {
                return mark;
            }

            public void setMark(String mark) {
                this.mark = mark;
            }

            public boolean isPackageGift(){
                return "gift".equals(type);
            }

            public int getPageIndex() {
                return pageIndex;
            }

            public void setPageIndex(int pageIndex) {
                this.pageIndex = pageIndex;
            }
        }
    }


}
