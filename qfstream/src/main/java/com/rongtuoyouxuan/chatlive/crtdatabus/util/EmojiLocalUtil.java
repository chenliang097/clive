package com.rongtuoyouxuan.chatlive.crtdatabus.util;

import android.content.Context;


import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.List;

public class EmojiLocalUtil {
//    public static EmojiMapBean getLocalEmoji(Context context){
//        List<String> emojiFile = getEmojiFile(context);
//        if (emojiFile == null || emojiFile.size()<=0) {
//            return null;
//        }
//        String type = "text";
//        String id = "0";
//        List<EmoticonBean> list = new ArrayList<>();
//        Map<String ,String> map = new HashMap<>();
//        int firstId = 0;
//        for (int i = 0; i < emojiFile.size(); i++) {
//            EmoticonBean bean = null;
//            String str = emojiFile.get(i);
//            String[] text = str.split(",");
//            String fileName = text[0].substring(0, text[0].lastIndexOf("."));
//            map.put(text[1], fileName);
//            int resID = context.getResources().getIdentifier(fileName, "drawable", context.getPackageName());
//            if (resID != 0) {
//                if (i == 12) { //因为为保持和原先一致，icon图标是笑脸
//                    firstId = resID;
//                }
//                bean = new EmoticonBean("text", "0", resID, text[1]);
//            }
//            list.add(bean);
//        }
//        EmojiMapBean itemBean = new EmojiMapBean(type,id,firstId,list,map);
//        return itemBean;
//    }

    /**
     * 读取表情配置文件
     *
     * @param context
     * @return
     */
    public static List<String> getEmojiFile(Context context) {
        try {
            List<String> list = new ArrayList<String>();
            InputStream in = context.getResources().getAssets().open("emojis");
            BufferedReader br = new BufferedReader(new InputStreamReader(in, "UTF-8"));
            String str = null;
            while ((str = br.readLine()) != null) {
                list.add(str);
            }

            return list;
        } catch (IOException e) {
            e.printStackTrace();
        }
        return null;
    }

}
