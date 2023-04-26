package com.rongtuoyouxuan.chatlive.biz2.model.stream.gift;

import com.rongtuoyouxuan.chatlive.net2.BaseModel;
import com.google.gson.annotations.SerializedName;
import java.util.ArrayList;
import java.util.List;

public class GiftListModel extends BaseModel {
    @SerializedName("data")
    public List<DataBean> data = new ArrayList<>();

    public static class DataBean {
        public static String TYPE_GIF = "imgframe";
        public static String TYPE_SVGA = "svga";

        @SerializedName("id")
        public String id = "";
        @SerializedName("name")
        public String name = "";
        @SerializedName("lang")
        public LangBean lang = new LangBean();
        @SerializedName("icon")
        public String icon = "";
        @SerializedName("price")
        public String price = "";
        @SerializedName("vip")
        public String vip = "";
        @SerializedName("smallres")
        public String smallres = "";
        @SerializedName("bigres")
        public String bigres = "";
        @SerializedName("super")
        public String supergift = "";
        @SerializedName("comboexpire")
        public int comboexpire = 0;
        @SerializedName("tag")
        public String tag = "";
        @SerializedName("tagicon")
        public String tagicon = "";
        @SerializedName("location")
        public int location = 2;
        @SerializedName("taginfo")
        public TagInfo taginfo;
        @SerializedName("sp")
        public String sp;
        @SerializedName("numoption")
        public List<Integer> numoption = new ArrayList<>();
        @SerializedName(value = "imgframe", alternate = "gtype")
        public String gType = TYPE_GIF;
        @SerializedName("svgasmallres")
        public String svgasmallres = "";
        @SerializedName("svgabigres")
        public String svgabigres = "";

        public static class LangBean {
            /**
             * name : {"zh":"","en":"","ja":"","ko":"","ru":"","th":"","vi":"","ar":""}
             * desc : {"zh":"","en":"","ja":"","ko":"","ru":"","th":"","vi":"","ar":""}
             */

            @SerializedName("name")
            public NameBean name = new NameBean();
            @SerializedName("desc")
            public DescBean desc = new DescBean();
            @SerializedName("title")
            public TitleBean title = new TitleBean();

            public static class NameBean {
                /**
                 * zh :
                 * en :
                 * ja :
                 * ko :
                 * ru :
                 * th :
                 * vi :
                 * ar :
                 */

                @SerializedName("zh")
                public String zh = "";
                @SerializedName("en")
                public String en = "";
                @SerializedName("ja")
                public String ja = "";
                @SerializedName("ko")
                public String ko = "";
                @SerializedName("ru")
                public String ru = "";
                @SerializedName("th")
                public String th = "";
                @SerializedName("vi")
                public String vi = "";
                @SerializedName("ar")
                public String ar = "";
                @SerializedName("zhtw")
                public String zhtw = "";
                @SerializedName("de")
                public String de = "";
                @SerializedName("fr")
                public String fr = "";
                @SerializedName("pt")
                public String pt = "";
                @SerializedName("es")
                public String es = "";
                @SerializedName("ms")
                public String ms = "";
                @SerializedName("id")
                public String id = "";
            }

            public static class DescBean {
                /**
                 * zh :
                 * en :
                 * ja :
                 * ko :
                 * ru :
                 * th :
                 * vi :
                 * ar :
                 */

                @SerializedName("zh")
                public String zh = "";
                @SerializedName("en")
                public String en = "";
                @SerializedName("ja")
                public String ja = "";
                @SerializedName("ko")
                public String ko = "";
                @SerializedName("ru")
                public String ru = "";
                @SerializedName("th")
                public String th = "";
                @SerializedName("vi")
                public String vi = "";
                @SerializedName("ar")
                public String ar = "";
                @SerializedName("zhtw")
                public String zhtw = "";
                @SerializedName("de")
                public String de = "";
                @SerializedName("fr")
                public String fr = "";
                @SerializedName("pt")
                public String pt = "";
                @SerializedName("es")
                public String es = "";
                @SerializedName("ms")
                public String ms = "";
                @SerializedName("id")
                public String id = "";
            }

            public static class TitleBean {
                /**
                 * zh :
                 * en :
                 * ja :
                 * ko :
                 * ru :
                 * th :
                 * vi :
                 * ar :
                 */

                @SerializedName("zh")
                public String zh = "";
                @SerializedName("en")
                public String en = "";
                @SerializedName("ja")
                public String ja = "";
                @SerializedName("ko")
                public String ko = "";
                @SerializedName("ru")
                public String ru = "";
                @SerializedName("th")
                public String th = "";
                @SerializedName("vi")
                public String vi = "";
                @SerializedName("ar")
                public String ar = "";
                @SerializedName("zhtw")
                public String zhtw = "";
                @SerializedName("de")
                public String de = "";
                @SerializedName("fr")
                public String fr = "";
                @SerializedName("pt")
                public String pt = "";
                @SerializedName("es")
                public String es = "";
                @SerializedName("ms")
                public String ms = "";
                @SerializedName("id")
                public String id = "";
            }
        }

        public static class TagInfo {
            @SerializedName("act")
            public String act;
            @SerializedName("act_val")
            public String act_val;
            @SerializedName("img")
            public String img;
            @SerializedName("avatar")
            public String avatar;
            // 以下字段弹幕使用
            @SerializedName("bgcolor")
            public String bgcolor;
            @SerializedName("icon")
            public String icon;
        }
    }
}
