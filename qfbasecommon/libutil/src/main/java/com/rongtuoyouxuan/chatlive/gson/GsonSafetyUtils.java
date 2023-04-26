package com.rongtuoyouxuan.chatlive.gson;

import com.google.gson.FieldNamingPolicy;
import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.InstanceCreator;
import com.google.gson.internal.ConstructorConstructor;
import com.google.gson.internal.Excluder;
import com.google.gson.stream.JsonReader;

import java.io.FileReader;
import java.io.FileWriter;
import java.lang.reflect.Field;
import java.lang.reflect.Type;
import java.util.Map;

/**
 * Created by jinqinglin on 2018/2/6.
 */

public class GsonSafetyUtils {
    private static GsonSafetyUtils instance;
    public Gson mGson;
    @SuppressWarnings("unchecked")
    private GsonSafetyUtils(){
        GsonBuilder gsonBuilder = new GsonBuilder();
        try {
            Class builder = gsonBuilder.getClass();
            Field f = builder.getDeclaredField("instanceCreators");
            f.setAccessible(true);
            Map<Type, InstanceCreator<?>> val = (Map<Type, InstanceCreator<?>>) f.get(gsonBuilder);//得到此属性的值
            //注册数组的处理器
            ConstructorConstructor constructorConstructor = new ConstructorConstructor(val);
            gsonBuilder.registerTypeAdapterFactory(new ReflectiveTypeAdapterFactory(constructorConstructor, FieldNamingPolicy.IDENTITY,
                    Excluder.DEFAULT,new JsonAdapterAnnotationTypeAdapterFactory(constructorConstructor)));
            gsonBuilder.registerTypeAdapterFactory(new MapTypeAdapterFactory(constructorConstructor, false));
            gsonBuilder.registerTypeAdapterFactory(new CollectionTypeAdapterFactory(constructorConstructor));
            gsonBuilder.registerTypeAdapter(String.class,new StringTypeAdapter());
            gsonBuilder.registerTypeAdapter(int.class,new IntegerTypeAdapter());
            gsonBuilder.registerTypeAdapter(Integer.class,new IntegerTypeAdapter());
            gsonBuilder.registerTypeAdapter(long.class,new LongTypeAdapter());
            gsonBuilder.registerTypeAdapter(Long.class,new LongTypeAdapter());
            gsonBuilder.registerTypeAdapter(double.class,new DoubleTypeAdapter());
            gsonBuilder.registerTypeAdapter(Double.class,new DoubleTypeAdapter());
            gsonBuilder.registerTypeAdapter(float.class,new FloatTypeAdapter());
            gsonBuilder.registerTypeAdapter(Float.class,new FloatTypeAdapter());
            gsonBuilder.registerTypeAdapter(boolean.class,new BooleanTypeAdapter());
            gsonBuilder.registerTypeAdapter(Boolean.class,new BooleanTypeAdapter());

        } catch (NoSuchFieldException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }
        mGson = gsonBuilder.create();
    }

    public static GsonSafetyUtils getInstance(){
        if(instance == null){
            synchronized (GsonSafetyUtils.class){
                if(instance == null)
                    instance = new GsonSafetyUtils();
            }
        }
        return instance;
    }

    public <T> T fromJson(String json,Class<T> classOfT){
        try {
            return mGson.fromJson(json,classOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T fromJson(String json,Type typeOfT){
        try {
            return mGson.fromJson(json,typeOfT);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public <T> T fromFile(String filePath, Class<T> classOfT) {
        try {
            JsonReader reader = new JsonReader(new FileReader(filePath));
            return mGson.fromJson(reader, classOfT);
        } catch (Exception e) {
        }
        return null;
    }

    public void toFile(Object obj, String filePath) {
        try {
            mGson.toJson(obj, new FileWriter(filePath));
        } catch (Exception e) {

        }
    }

    public String toJson(Object obj) {
        String json = "";
        try {
            json = mGson.toJson(obj);
        } catch (Exception e) {

        }
        return json;
    }
}
