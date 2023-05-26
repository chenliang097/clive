package com.rongtuoyouxuan.chatlive.rtstream.streaming;


public interface Sdk {
    interface StreamApi extends IStreamingLifecycle, IBaseStreaming, IStreamingOperation, IStreamingBeautify {
    }



    interface StreamInitData {
        String getSdkName();

        InitParams getInitParams();
    }

    class InitParams {
        public String sdkName;
        public int gop;
        public boolean useHardEncode;
        public boolean verticalStream;
        public boolean useFrontCamera;
        public boolean frontCameraMirror;
        public boolean needWatermark;
        public int extParam;
    }
}
