package com.rongtuoyouxuan.chatlive.gson;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by jinqinglin on 2018/2/5.
 */

public class
StringTypeAdapter extends TypeAdapter<String> {

    @Override
    public String read(JsonReader reader) throws IOException {
        String a = "";
        try {
            if(reader.peek() == JsonToken.NUMBER){
                a = String.valueOf(reader.nextInt());
            }else if(reader.peek() == JsonToken.BOOLEAN){
                a = String.valueOf(reader.nextBoolean());
            }else if(reader.peek() == JsonToken.STRING) {
                a = reader.nextString();
            }else {
                reader.skipValue();
            }
        } catch (Exception e) {
            Log.e("jql", e.getMessage());
        }
        return a;
    }

    @Override
    public void write(JsonWriter writer, String value) throws IOException {
        try {
            if (value == null) {
                writer.nullValue();
                return;
            }
            writer.value(value);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
