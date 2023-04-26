package com.rongtuoyouxuan.qfcommon.notification;

import android.app.Activity;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;

import java.util.WeakHashMap;

/**
 * 前台Activity管理类
 */
public class ForegroundActivityManager {

    private static final String TAG = "ActivityManager";
    private static final int MAIN_SCENE = 1;
    private static final int GAME_SCENE = 2;
    private static final int CHAT_SCENE = 3;
    private static final int MINE_SCENE = 4;
    private static volatile ForegroundActivityManager instance;
    private WeakHashMap<Integer, Activity> currentActivityWeakMap = null;

    private ForegroundActivityManager() {
    }

    public static ForegroundActivityManager getInstance() {
        if (instance == null) {
            synchronized (ForegroundActivityManager.class) {
                if (instance == null) {
                    instance = new ForegroundActivityManager();
                }
            }
        }
        return instance;
    }


    public Activity getCurrentActivity() {
        Activity currentActivity = null;
        Log.d(TAG, currentActivityWeakMap.get(1).toString());
        if (currentActivityWeakMap != null) {
            currentActivity = currentActivityWeakMap.get(1);
        }
        return currentActivity;
    }

    public WeakHashMap<Integer, Activity> getCurrentActivityMap() {
        return currentActivityWeakMap;
    }

    public void setCurrentPage(Activity activity, Fragment fragment) {
        currentActivityWeakMap = new WeakHashMap<>();
        Log.d(TAG, "activity : " + activity + "  fragment : " + fragment);
        String activityName = activity.getClass().getSimpleName();
        if (activityName.equals("MainActivity")) {
            if (fragment == null) {
                return;
            }
            Log.d(TAG, "getFragments --> " + ((AppCompatActivity) activity).getSupportFragmentManager().getFragments());
            Fragment mainFragment = ((AppCompatActivity) activity).getSupportFragmentManager().findFragmentByTag("0");
            Fragment gameFragment = ((AppCompatActivity) activity).getSupportFragmentManager().findFragmentByTag("1");
            Fragment messageFragment = ((AppCompatActivity) activity).getSupportFragmentManager().findFragmentByTag("3");
            Fragment mineFragment = ((AppCompatActivity) activity).getSupportFragmentManager().findFragmentByTag("4");

            if (mainFragment != null && !mainFragment.isHidden()) {
                currentActivityWeakMap.put(MAIN_SCENE, activity);
            } else if (gameFragment != null && !gameFragment.isHidden()) {
                currentActivityWeakMap.put(GAME_SCENE, null);
            } else if (messageFragment != null && !messageFragment.isHidden()) {
                currentActivityWeakMap.put(CHAT_SCENE, activity);
            } else if (mineFragment != null && !mineFragment.isHidden()) {
                currentActivityWeakMap.put(MINE_SCENE, activity);
            }
        } else if (activityName.equals("ConversationActivity")
                || activityName.equals("SystemActivity") || activityName.equals("OfficialActivity")
                || activityName.equals("GroupInfoManageActivity") || activityName.equals("GroupMemberListActivity")) {
            currentActivityWeakMap.put(CHAT_SCENE, activity);
        }
        Log.d(TAG, "map --> " + currentActivityWeakMap);
    }
}