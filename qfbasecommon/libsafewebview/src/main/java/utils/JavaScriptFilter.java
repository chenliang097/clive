package utils;


/**
 * javascript脚本注入开关
 * @author wangzefeng
 *
 */
public class JavaScriptFilter {
	
    public static boolean mJsInjectOn = true; // 是否开启javascript过滤
    private static JavaScriptFilter mJavaScriptFilter; // 本类的单实例
    private JavaScriptFilter() {
    }

    /**
     * 调用此函数会返回单例
     * @param context
     * @return
     */
    public static JavaScriptFilter getInstance() {
        if (mJavaScriptFilter == null) {
            mJavaScriptFilter = new JavaScriptFilter();
        }
        return mJavaScriptFilter;
    }

    /**
     * 判断是否开启JavaScript脚本过滤
     * 
     * @return
     */
    public boolean isJsInjectOn() {
        return mJsInjectOn;
    }

    /**
     * 设置开启关闭脚本注入
     * 
     * @param jsFilterOn
     */
    public void setJsInjectOn(boolean jsFilterOn) {
        mJsInjectOn = jsFilterOn;
    }

}
