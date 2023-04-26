package utils;

import java.net.MalformedURLException;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.concurrent.ConcurrentLinkedQueue;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.preference.PreferenceManager;
import android.text.TextUtils;
import android.webkit.WebView;

public class AdPattern {

    public static final String KEY_IS_ADBLOCK_OPEN = "is_adblock_open";
    public static final String KEY_IS_ADBLOCK_TIPS_OPEN = "is_adblock_tips_open";
    public static final String KEY_CURRRUNT_FILTER_COUNT = "cur_filter_count";
    public static Object syncObject = new Object();
    public static String mCurKey = "";
    private static String AD_PATTERN_FILENAME = ""; // 定义广告url及白名单url的正则串的文件（json格式）
    // private static String AD_PATTERN_FILENAME = "ad_filters.json";
    private static AdPattern sAdPattern; // 本类的单实例
    private int mAdFilter_total = 0;
    private boolean mAdBlockOn = true; // 是否屏蔽广告的开关
    private boolean mAdFilterTipOn = true;
    private ConcurrentLinkedQueue<RuleReg> mArrAdPattern; // 识别广告的正则pattern
    private ConcurrentLinkedQueue<Pattern> mArrIgnoreUrlPattern; // 白名单的正则pattern
    private Context mAppContext;
    /**
     * 构造函数：根据给定的正则模式串json文件，构造正则模式，生成本类的单实例
     *
     * @param appContext
     * @param patternFilePath
     *            ：定义广告url及白名单url的正则串的文件（json格式）
     */
    private AdPattern(Context appContext) {
        this.mAppContext = appContext;
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mAppContext);
        mAdBlockOn = sharedPreferences.getBoolean(KEY_IS_ADBLOCK_OPEN, true);
        mAdFilterTipOn = sharedPreferences.getBoolean(KEY_IS_ADBLOCK_TIPS_OPEN, true);
        mCurKey = getCurDateKey();
        initAdPatterns();
    }

    // public static final String KEY_ADBLOCK_FILE_PATH = "adblock_file_path";

    /**
     * 调用此函数会返回AdPattern的单实例。第一次调用时，构建单实例并返回；以后调用，会返回此单实例的引用
     *
     * @param context
     *            ：上下文
     * @return：返回对AdPattern单实例的引用
     */
    public static AdPattern get(Context context) {
        if (sAdPattern == null) {
            sAdPattern = new AdPattern(context.getApplicationContext());
        }
        return sAdPattern;
    }

    public void initAdPatterns() {
        synchronized (syncObject) {
            /**
             * 针对云控时,将规则文件置空动作时,需要及时将规则更新
             */
            mArrAdPattern = new ConcurrentLinkedQueue<RuleReg>();
            mArrIgnoreUrlPattern = new ConcurrentLinkedQueue<Pattern>();

            // Json file -> Json Object
            AdFilterJsonObject jsonObj = AdFilterJsonObject
                    .getAdFilterJsonObject(AD_PATTERN_FILENAME);

            // 调试用
            // AssetManager am = mAppContext.getAssets();
            // AdFilterJsonObject jsonObj;
            // try {
            // InputStream isAdPattern = am.open(AD_PATTERN_FILENAME);
            // jsonObj = AdFilterJsonObject.getAdFilterJsonObject(isAdPattern);
            // } catch (IOException e) { return; }

            // 将广告url的正则串编译成正则pattern
            if (jsonObj == null)
                return;

            if (jsonObj.block_patterns != null) {
                Pattern resPat, hostPat;
                boolean checkForThirdParty;

                for (AdFilterJsonObject.AdUrlPattern urlPat : jsonObj.block_patterns) {
                    if (urlPat.resPattern == null) {
                        resPat = null;
                    } else {
                        resPat = Pattern.compile(urlPat.resPattern, Pattern.CASE_INSENSITIVE);
                    }
                    if (urlPat.thirdParty == null) {
                        checkForThirdParty = false;
                    } else {
                        checkForThirdParty = true;
                    }
                    if (urlPat.hostPattern == null) {
                        hostPat = null;
                    } else {
                        hostPat = Pattern.compile(urlPat.hostPattern, Pattern.CASE_INSENSITIVE);
                    }
                    mArrAdPattern.add(new RuleReg(resPat, checkForThirdParty, hostPat));
                }
            }

            // 将白名单的正则串编译成正则pattern
            if (jsonObj.ignore_patterns != null) {
                for (String ignore_url : jsonObj.ignore_patterns) {
                    mArrIgnoreUrlPattern.add(Pattern.compile(ignore_url, Pattern.CASE_INSENSITIVE));
                }
            }
        }
    }

    /**
     * 判断是否做广告屏蔽检查
     *
     * @return
     */
    public boolean isAdBlockOn() {
        return mAdBlockOn;
    }

    /**
     * 设置是否做广告屏蔽检查
     *
     * @param adBlockOn
     */
    public void setAdBlockOn(boolean adBlockOn) {
        if (mAdBlockOn == adBlockOn) {
            return;
        }
        mAdBlockOn = adBlockOn;
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mAppContext);
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(KEY_IS_ADBLOCK_OPEN, adBlockOn);
        edit.commit();
    }

    public void InitAdFilter() {
        mAdFilter_total = 0;

    }

    public int GetTotalCount() {

        if (IsShowTip()) {
            int mRet = mAdFilter_total;
            mAdFilter_total = 0;
            return mRet;
        }
        return 0;

    }

    public boolean GetAdBlockOn() {
        return mAdBlockOn;
    }

    public boolean GetAdFilterTipOn() {
        return mAdFilterTipOn;
    }

    public boolean IsShowTip() {
        return mAdBlockOn && mAdFilterTipOn;
    }

    public void SetAdFilterTipsOn(boolean IsOn) {
        if (mAdFilterTipOn == IsOn) {
            return;
        }
        mAdFilterTipOn = IsOn;
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mAppContext);
        Editor edit = sharedPreferences.edit();
        edit.putBoolean(KEY_IS_ADBLOCK_TIPS_OPEN, IsOn);
        edit.commit();
    }

    private void AddMonthFilter(final WebView view) {
        if (TextUtils.isEmpty(mCurKey)) {
            return;
        }
        if (IsShowTip()) {
            mAdFilter_total++;
        }
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mAppContext);
        int num = sharedPreferences.getInt(mCurKey, 0);
        num++;
        Editor edit = sharedPreferences.edit();
        edit.putInt(mCurKey, num);
        num = sharedPreferences.getInt(KEY_CURRRUNT_FILTER_COUNT, 0);
        num++;
        edit.putInt(KEY_CURRRUNT_FILTER_COUNT, num);
        /*
         * int nFirst=sharedPreferences.getInt("IsFristTriggerAd",0);
         * if(nFirst==0){ edit.putInt("IsFristTriggerAd", 1); Handler thandl=new
         * Handler(Looper.getMainLooper()); thandl.postDelayed(new Runnable() {
         *
         * @Override public void run() { Toast sToast =
         * Toast.makeText(view.getContext(), "已开启智能广告过滤", Toast.LENGTH_SHORT);
         * sToast.show(); } }, 1000);
         *
         * }
         */
        edit.commit();
    }

    public int GetThisMonthFilterCount() {
        if (TextUtils.isEmpty(mCurKey)) {
            return 0;
        }
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mAppContext);
        return sharedPreferences.getInt(mCurKey, 0);
    }

    public String getCurDateKey() {
        long time = System.currentTimeMillis();
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM", Locale.getDefault());
        Date d1 = new Date(time);
        String curKey = "this_month_filter_total_" + format.format(d1);
        return curKey;
    }

    public int GetTotalCountAndClear() {
        SharedPreferences sharedPreferences = PreferenceManager
                .getDefaultSharedPreferences(mAppContext);
        int num = sharedPreferences.getInt(KEY_CURRRUNT_FILTER_COUNT, 0);
        Editor edit = sharedPreferences.edit();
        edit.putInt(KEY_CURRRUNT_FILTER_COUNT, 0);
        edit.commit();
        return num;
    }

    /**
     * 检查当前待加载资源是否是要屏蔽的广告
     *
     * @param resourceUrl
     *            : 待加载资源的url
     * @param hostpageUrl
     *            : 已加载页面的url
     * @return 符合屏蔽规则，则返回true，否则返回false. 注意，此函数应与isAdBlockOn()同时使用。
     */
    public boolean matches(final String resourceUrl, final String hostpageUrl, WebView view) {
        synchronized (syncObject) {
            // 当屏蔽广告开关关闭，或者，匹配广告模式的ArrayList为空时，返回false（不屏蔽广告）
            if (mAdBlockOn == false || mArrAdPattern == null || resourceUrl == null
                    || hostpageUrl == null)
                return false;

            // hostpageUrl符合白名单时，返回false（不屏蔽广告）
            if (mArrIgnoreUrlPattern != null) {
                for (Pattern ignorePat : mArrIgnoreUrlPattern) {
                    Matcher ignoreMatch = ignorePat.matcher(hostpageUrl);
                    if (ignoreMatch.find())
                        return false;
                }
            }
            // 检查resourceUrl与hostpageUrl的host是否一致？某些拦截pattern只对第三方host生效
            boolean sameHost;
            try {
                String resourceHost = new URL(resourceUrl).getHost();
                String hostpageHost = new URL(hostpageUrl).getHost();
                if (resourceHost.equals(hostpageHost)) {
                    sameHost = true;
                } else {
                    sameHost = false;
                }
            } catch (MalformedURLException e) {
                sameHost = false;
            }
            // 检查待加载资源是否满足定义的广告url模式
            boolean bIsMatch = false;
            for (RuleReg rr : mArrAdPattern) {
                if (rr.resourceReg == null)
                    continue;
                Matcher resMatch = rr.resourceReg.matcher(resourceUrl);
                if (!resMatch.find()) {
                    continue;
                }

                // 与当前模式匹配（一）
                // 先检查是否规定了thirdParty域？ 如规定了，检查sameHost：在相同host时不拦截（返回false）
                if (rr.checkForThirdParty && sameHost) {
                    return false;
                }

                // 与当前模式匹配（二）
                // 检查hostpageUrl是否属于网开一面的url（如taobao的推广嵌入在tmall的页面时不拦截）
                if (rr.hostpageReg == null) {
                    bIsMatch = true;
                    break;
                } else {
                    Matcher hostMatch = rr.hostpageReg.matcher(hostpageUrl);
                    bIsMatch = !hostMatch.find();
                    break;
                }
            }
            if (bIsMatch) {
                AddMonthFilter(view);
            }
            return bIsMatch;
        }
    }

    class RuleReg {
        public Pattern resourceReg; // 匹配待拦截资源url的正则模式
        public boolean checkForThirdParty; // true:
                                           // 表示待拦截资源的url只对第三方host有效；false，表示对全部host生效
        public Pattern hostpageReg; // 检测到待拦截资源后，网开一面的host的正则模式

        public RuleReg(Pattern resourceReg, boolean checkForThirdParty, Pattern hostpageReg) {
            super();
            this.resourceReg = resourceReg;
            this.checkForThirdParty = checkForThirdParty;
            this.hostpageReg = hostpageReg;
        }
    }
}
