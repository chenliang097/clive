package com.rongtuoyouxuan.chatlive.crtutil.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;

public class FloatTypeAdapter extends TypeAdapter<Number> {
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
        float a = 0;
        try {
            switch (in.peek()) {
                case NUMBER:
                case STRING:
                    String n = in.nextString();
                    if (NumberUtils.isNumeric(n))
                        a = Float.parseFloat(n);
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
