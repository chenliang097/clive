package com.rongtuoyouxuan.chatlive.crtutil.util;

import android.app.Activity;
import android.app.ActivityManager;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.text.TextUtils;
import android.widget.Toast;

import java.util.List;

import static android.content.Context.ACTIVITY_SERVICE;

/**
 * 类描述: 启动制定包名的其他应用
 * 创建人:malin.myemail@163.com
 * 创建时间:2017/11/29 17:01
 * 备注:{@link }
 * 修改人:
 * 修改时间:
 * 修改备注:
 * 版本:
 * http://blog.csdn.net/qq_28695619/article/details/53869594
 */

public class ActivitySkipUtil {

    /**
     * 判断直播是否存在
     */
    public static boolean LIVE_CAMERA_ING = false;
    public static boolean LIVE_MOBILE_GAME_ING = false;


    /**
     * 手机上是否安装了某个应用
     *
     * @param context     Context
     * @param packageName packageName
     * @return isExist
     */
    public static boolean isExist(Context context, String packageName) {
        if (TextUtils.isEmpty(packageName) || context == null) return false;
        PackageManager packageManager = context.getApplicationContext().getPackageManager();
        return checkPackInfo(packageManager, packageName);
    }

    /**
     * 启动制定包名的其他应用
     * http://www.jianshu.com/p/42ae7066f8f3
     *
     * @param packageNameStr packageName
     */
    public static boolean startThirdApp(Activity activity, String packageNameStr) {
        boolean isSuccess;
        if (activity == null || activity.isFinishing()) return false;
        try {
            PackageManager packageManager = activity.getApplicationContext().getPackageManager();
            if (checkPackInfo(packageManager, packageNameStr)) {
                Intent intent = packageManager.getLaunchIntentForPackage(packageNameStr);
                activity.startActivity(intent);
                isSuccess = true;
            } else {
                isSuccess = false;
                Toast.makeText(activity, "没有安装该游戏!", Toast.LENGTH_SHORT).show();
            }


        } catch (Throwable throwable) {
            throwable.printStackTrace();
            isSuccess = false;
        }
        return isSuccess;
    }

    /**
     * 检查包是否存在
     *
     * @param packageName packageName
     * @return is exit
     */
    private static boolean checkPackInfo(PackageManager packageManager, String packageName) {
        PackageInfo packageInfo = null;
        try {
            packageInfo = packageManager.getPackageInfo(packageName, 0);
        } catch (Throwable e) {
            e.printStackTrace();
        }
        return packageInfo != null && !TextUtils.isEmpty(packageInfo.packageName);
    }

    /**
     * 判断检测activity是否已经在任务栈里
     * @param ctx
     * @param className
     * @return
     */
    public static boolean checkActivityAlive(Context ctx,String className){
        boolean flag = false;
        try {
            Intent intent = new Intent(ctx,Class.forName(className));
            ComponentName cmpName = intent.resolveActivity(ctx.getPackageManager());
            if (cmpName != null) { // 说明系统中存在这个activity
                ActivityManager am = (ActivityManager) ctx.getSystemService(ACTIVITY_SERVICE);
                List<ActivityManager.RunningTaskInfo> taskInfoList = am.getRunningTasks(10);
                for (ActivityManager.RunningTaskInfo taskInfo : taskInfoList) {
                    if (taskInfo.topActivity.equals(cmpName)) { // 说明它已经启动了
                        flag = true;
                        break;  //跳出循环，优化效率
                    }
                }
            }

            return flag;
        }catch (Exception e){
            return false;
        }
    }

}
