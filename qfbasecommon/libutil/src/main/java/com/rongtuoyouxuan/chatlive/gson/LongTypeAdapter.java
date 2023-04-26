package com.rongtuoyouxuan.chatlive.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

/**
 * Created by jinqinglin on 2018/2/6.
 */

public class LongTypeAdapter extends TypeAdapter<Number> {
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
        long a = 0;
        try {
            switch (in.peek()) {
                case NUMBER:
                    a = (long)in.nextDouble();
                    break;
                case STRING:
                    String n = in.nextString();
                    if (NumberUtils.isNumeric(n))
                        a = (long)Double.parseDouble(n);
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
            e.printStackTrace();
        }
        return a;
    }
}
