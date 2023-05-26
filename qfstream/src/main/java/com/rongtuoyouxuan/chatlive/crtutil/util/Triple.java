package com.rongtuoyouxuan.chatlive.crtutil.util;

/**
 * Created by zhangdaoqiang on 2016/10/31.
 */

public class Triple<T1, T2, T3> {
    public final T1 first;
    public final T2 second;
    public final T3 third;

    public Triple(T1 first, T2 second, T3 third) {
        this.first = first;
        this.second = second;
        this.third = third;
    }

    public static <T1, T2, T3> Triple<T1, T2, T3> create(T1 first, T2 second, T3 third) {
        return new Triple<>(first, second, third);
    }
}