package com.rongtuoyouxuan.chatlive.crtutil.gson;

import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSyntaxException;
import com.google.gson.TypeAdapter;
import com.google.gson.TypeAdapterFactory;
import com.google.gson.internal.$Gson$Types;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.JsonReaderInternalAccess;
import com.google.gson.internal.ObjectConstructor;
import com.google.gson.internal.Streams;
import com.google.gson.internal.bind.TypeAdapters;
import com.google.gson.reflect.TypeToken;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public final class MapTypeAdapterFactory implements TypeAdapterFactory {
    final boolean complexMapKeySerialization;
    private final ConstructorConstructor constructorConstructor;

    public MapTypeAdapterFactory(ConstructorConstructor constructorConstructor,
                                 boolean complexMapKeySerialization) {
        this.constructorConstructor = constructorConstructor;
        this.complexMapKeySerialization = complexMapKeySerialization;
    }

    @Override
    public <T> TypeAdapter<T> create(Gson gson, TypeToken<T> typeToken) {
        Type type = typeToken.getType();

        Class<? super T> rawType = typeToken.getRawType();
        if (!Map.class.isAssignableFrom(rawType)) {
            return null;
        }

        Class<?> rawTypeOfSrc = $Gson$Types.getRawType(type);
        Type[] keyAndValueTypes = $Gson$Types.getMapKeyAndValueTypes(type, rawTypeOfSrc);
        TypeAdapter<?> keyAdapter = getKeyAdapter(gson, keyAndValueTypes[0]);
        TypeAdapter<?> valueAdapter = gson.getAdapter(TypeToken.get(keyAndValueTypes[1]));
        ObjectConstructor<T> constructor = constructorConstructor.get(typeToken);

        @SuppressWarnings({"unchecked", "rawtypes"})
        // we don't define a type parameter for the key or value types
                TypeAdapter<T> result = new Adapter(gson, keyAndValueTypes[0], keyAdapter,
                keyAndValueTypes[1], valueAdapter, constructor);
        return result;
    }

    /**
     * Returns a type adapter that writes the value as a string.
     */
    private TypeAdapter<?> getKeyAdapter(Gson context, Type keyType) {
        return (keyType == boolean.class || keyType == Boolean.class)
                ? TypeAdapters.BOOLEAN_AS_STRING
                : context.getAdapter(TypeToken.get(keyType));
    }

    private final class Adapter<K, V> extends TypeAdapter<Map<K, V>> {
        private final TypeAdapter<K> keyTypeAdapter;
        private final TypeAdapter<V> valueTypeAdapter;
        private final ObjectConstructor<? extends Map<K, V>> constructor;

        public Adapter(Gson context, Type keyType, TypeAdapter<K> keyTypeAdapter,
                       Type valueType, TypeAdapter<V> valueTypeAdapter,
                       ObjectConstructor<? extends Map<K, V>> constructor) {
            this.keyTypeAdapter =
                    new TypeAdapterRuntimeTypeWrapper<K>(context, keyTypeAdapter, keyType);
            this.valueTypeAdapter =
                    new TypeAdapterRuntimeTypeWrapper<V>(context, valueTypeAdapter, valueType);
            this.constructor = constructor;
        }

        @Override
        public Map<K, V> read(JsonReader in) throws IOException {
            Map<K, V> map = constructor.construct();
            JsonToken peek = in.peek();
            switch (peek) {
                case NULL:
                case STRING:
                case NUMBER:
                case BOOLEAN:
                    in.skipValue();
                    return map;
            }

            if (peek == JsonToken.BEGIN_ARRAY) {
                in.beginArray();
                while (in.hasNext()) {
                    in.beginArray(); // entry array
                    K key = keyTypeAdapter.read(in);
                    V value = valueTypeAdapter.read(in);
                    V replaced = map.put(key, value);
                    if (replaced != null) {
                        throw new JsonSyntaxException("duplicate key: " + key);
                    }
                    in.endArray();
                }
                in.endArray();
            } else {
                in.beginObject();
                while (in.hasNext()) {
                    JsonReaderInternalAccess.INSTANCE.promoteNameToValue(in);
                    K key = keyTypeAdapter.read(in);
                    V value = valueTypeAdapter.read(in);
                    V replaced = map.put(key, value);
                    if (replaced != null) {
                        throw new JsonSyntaxException("duplicate key: " + key);
                    }
                }
                in.endObject();
            }
            return map;
        }

        @Override
        public void write(JsonWriter out, Map<K, V> map) throws IOException {
            if (map == null) {
                out.nullValue();
                return;
            }

            if (!complexMapKeySerialization) {
                out.beginObject();
                for (Map.Entry<K, V> entry : map.entrySet()) {
                    out.name(String.valueOf(entry.getKey()));
                    valueTypeAdapter.write(out, entry.getValue());
                }
                out.endObject();
                return;
            }

            boolean hasComplexKeys = false;
            List<JsonElement> keys = new ArrayList<JsonElement>(map.size());

            List<V> values = new ArrayList<V>(map.size());
            for (Map.Entry<K, V> entry : map.entrySet()) {
                JsonElement keyElement = keyTypeAdapter.toJsonTree(entry.getKey());
                keys.add(keyElement);
                values.add(entry.getValue());
                hasComplexKeys |= keyElement.isJsonArray() || keyElement.isJsonObject();
            }

            if (hasComplexKeys) {
                out.beginArray();
                for (int i = 0, size = keys.size(); i < size; i++) {
                    out.beginArray(); // entry array
                    Streams.write(keys.get(i), out);
                    valueTypeAdapter.write(out, values.get(i));
                    out.endArray();
                }
                out.endArray();
            } else {
                out.beginObject();
                for (int i = 0, size = keys.size(); i < size; i++) {
                    JsonElement keyElement = keys.get(i);
                    out.name(keyToString(keyElement));
                    valueTypeAdapter.write(out, values.get(i));
                }
                out.endObject();
            }
        }

        private String keyToString(JsonElement keyElement) {
            if (keyElement.isJsonPrimitive()) {
                JsonPrimitive primitive = keyElement.getAsJsonPrimitive();
                if (primitive.isNumber()) {
                    return String.valueOf(primitive.getAsNumber());
                } else if (primitive.isBoolean()) {
                    return Boolean.toString(primitive.getAsBoolean());
                } else if (primitive.isString()) {
                    return primitive.getAsString();
                } else {
                    throw new AssertionError();
                }
            } else if (keyElement.isJsonNull()) {
                return "null";
            } else {
                throw new AssertionError();
            }
        }
    }
}
