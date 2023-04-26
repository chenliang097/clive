package com.rongtuoyouxuan.chatlive.gson;

import android.util.Log;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by jinqinglin on 2018/2/6.
 */

public class IntegerTypeAdapter extends TypeAdapter<Number> {
    @Override
    public void write(JsonWriter jsonWriter, Number number) throws IOException {
        jsonWriter.value(number);
    }

    @Override
    public Number read(JsonReader in) throws IOException {
        if (in.peek() == JsonToken.NULL) {
            in.nextNull();
            return 0;
        }
        int a = 0;
        try {
            switch (in.peek()) {
                case NUMBER:
                    double i = in.nextDouble();//当成double来读取
                    a = (int) i;
                    break;
                case STRING:
                    String n = in.nextString();
                    if (NumberUtils.isNumeric(n))
                        a = (int)Double.parseDouble(n);
                    break;
                case BOOLEAN:
                    boolean b = in.nextBoolean();
                    a = b ? 1 : 0;
                    break;
                default:
                    in.skipValue();
                    break;
            }
        } catch (NumberFormatException e) {
            Log.e("jql", e.getMessage());
        }
        return a;
    }
}
