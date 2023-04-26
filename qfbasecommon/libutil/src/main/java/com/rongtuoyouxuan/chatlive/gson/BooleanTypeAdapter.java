package com.rongtuoyouxuan.chatlive.gson;

import android.text.TextUtils;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by jinqinglin on 2018/2/6.
 */

public class BooleanTypeAdapter extends TypeAdapter<Boolean> {
    @Override
    public void write(JsonWriter jsonWriter, Boolean aBoolean) throws IOException {
        jsonWriter.value(aBoolean);
    }

    @Override
    public Boolean read(JsonReader jsonReader) throws IOException {
        boolean a = false;
        try {
            if(jsonReader.peek() == JsonToken.NULL){
                jsonReader.nextNull();
                return a;
            }else if(jsonReader.peek() == JsonToken.NUMBER){
                double b = jsonReader.nextDouble();
                if(b == 1)
                    a = true;
            }else if(jsonReader.peek() == JsonToken.STRING){
                String b = jsonReader.nextString();
                if(TextUtils.equals(b,"true")){
                    a = true;
                }else if(NumberUtils.isNumeric(b)){
                    Double d = Double.parseDouble(b);
                    a = Math.abs(d) == 1;
                }
            }else if(jsonReader.peek() == JsonToken.BOOLEAN){
                a = jsonReader.nextBoolean();
            }else {
                jsonReader.skipValue();
            }
        }catch (Exception e){}
        return a;
    }
}
