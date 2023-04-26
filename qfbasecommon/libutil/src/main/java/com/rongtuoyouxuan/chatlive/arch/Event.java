package com.rongtuoyouxuan.chatlive.arch;


public class Event<T> {
    private T content;
    private boolean isInit = false;
    private boolean hasBeenHandled = false;
    public Event(T value) {
        content = value;
    }
    public Event(T value, boolean init) {
        content = value;
        isInit = init;
    }

    public T peekContent() {
        return content;
    }

    boolean getHasBeenHandled() {
        return hasBeenHandled;
    }

    boolean getIsInit() {
        return isInit;
    }

    void setHasBeenHandled() {
        hasBeenHandled = true;
    }
}