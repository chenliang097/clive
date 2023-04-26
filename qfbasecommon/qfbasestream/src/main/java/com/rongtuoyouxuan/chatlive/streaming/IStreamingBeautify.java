package com.rongtuoyouxuan.chatlive.streaming;

public interface IStreamingBeautify {
    void useBuiltinBeauty(boolean b);

    void setBeautyWhite(float val);

    void setBeautyRed(float val);

    void setBeautySmooth(float val);

    void setBeautyLargeEye(float val);

    void setBeautyShrinkFace(float val);

    void setBeautyShrinkJaw(float val);

    void selectFilter(int index);

    void setBeautysharpen(float val);

}
