package com.rongtuoyouxuan.chatlive.qfcommon.notification

import android.app.Activity
import android.app.Application
import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.fragment.app.Fragment
import androidx.fragment.app.FragmentActivity
import androidx.fragment.app.FragmentManager

class AppLifecycleCallback : Application.ActivityLifecycleCallbacks {

    companion object {
        val TAG = "AppLifecycleCallback"
    }

    private val mFragmentLifecycleCallbacks: FragmentManager.FragmentLifecycleCallbacks =
        object : FragmentManager.FragmentLifecycleCallbacks() {
            override fun onFragmentPreAttached(fm: FragmentManager, f: Fragment, context: Context) {
                super.onFragmentPreAttached(fm, f, context)
                Log.i(TAG, "onFragmentPreAttached: " + f.javaClass.simpleName)
            }

            override fun onFragmentActivityCreated(
                fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?,
            ) {
                super.onFragmentActivityCreated(fm, f, savedInstanceState)
                Log.i(TAG, "onFragmentActivityCreated: " + f.javaClass.simpleName)
            }

            override fun onFragmentAttached(fm: FragmentManager, f: Fragment, context: Context) {
                super.onFragmentAttached(fm, f, context)
                Log.i(TAG, "onFragmentAttached: " + f.javaClass.simpleName)
            }

            override fun onFragmentPreCreated(
                fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?,
            ) {
                super.onFragmentPreCreated(fm, f, savedInstanceState)
                Log.i(TAG, "onFragmentPreCreated: " + f.javaClass.simpleName)
            }

            override fun onFragmentCreated(
                fm: FragmentManager, f: Fragment, savedInstanceState: Bundle?,
            ) {
                super.onFragmentCreated(fm, f, savedInstanceState)
                Log.i(TAG, "onFragmentCreated: " + f.javaClass.simpleName)
            }

            override fun onFragmentViewCreated(
                fm: FragmentManager, f: Fragment, v: View, savedInstanceState: Bundle?,
            ) {
                super.onFragmentViewCreated(fm, f, v, savedInstanceState)
                Log.i(TAG, "onFragmentViewCreated: " + f.javaClass.simpleName)
            }

            override fun onFragmentStarted(fm: FragmentManager, f: Fragment) {
                super.onFragmentStarted(fm, f)
                Log.i(TAG, "onFragmentStarted: " + f.javaClass.simpleName)
            }

            override fun onFragmentResumed(fm: FragmentManager, f: Fragment) {
                super.onFragmentResumed(fm, f)
                Log.i(TAG, "onFragmentResumed: " + f.javaClass.simpleName)
                //获取Activity弱引用
                com.rongtuoyouxuan.chatlive.qfcommon.notification.ForegroundActivityManager.getInstance().setCurrentPage(f.activity, f)
            }

            override fun onFragmentPaused(fm: FragmentManager, f: Fragment) {
                super.onFragmentPaused(fm, f)
                Log.i(TAG, "onFragmentPaused: " + f.javaClass.simpleName)
                //获取Activity弱引用
                com.rongtuoyouxuan.chatlive.qfcommon.notification.ForegroundActivityManager.getInstance().currentActivityMap.clear()
            }

            override fun onFragmentStopped(fm: FragmentManager, f: Fragment) {
                super.onFragmentStopped(fm, f)
                Log.i(TAG, "onFragmentStopped: " + f.javaClass.simpleName)
            }

            override fun onFragmentSaveInstanceState(
                fm: FragmentManager, f: Fragment, outState: Bundle,
            ) {
                super.onFragmentSaveInstanceState(fm, f, outState)
                Log.i(TAG, "onFragmentSaveInstanceState: " + f.javaClass.simpleName)
            }

            override fun onFragmentViewDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentViewDestroyed(fm, f)
                Log.i(TAG, "onFragmentViewDestroyed: " + f.javaClass.simpleName)
            }

            override fun onFragmentDestroyed(fm: FragmentManager, f: Fragment) {
                super.onFragmentDestroyed(fm, f)
                Log.i(TAG, "onFragmentDestroyed: " + f.javaClass.simpleName)
            }

            override fun onFragmentDetached(fm: FragmentManager, f: Fragment) {
                super.onFragmentDetached(fm, f)
                Log.i(TAG, "onFragmentDetached: " + f.javaClass.simpleName)
            }
        }

    override fun onActivityCreated(activity: Activity, savedInstanceState: Bundle?) {
//        //获取Activity弱引用
//        ForegroundActivityManager1.getInstance().setCurrentActivity(activity)
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.registerFragmentLifecycleCallbacks(
                mFragmentLifecycleCallbacks, true)
        }
    }

    override fun onActivityStarted(activity: Activity) {
    }

    override fun onActivityResumed(activity: Activity) {
        //获取Activity弱引用
        com.rongtuoyouxuan.chatlive.qfcommon.notification.ForegroundActivityManager.getInstance().setCurrentPage(activity, null)
    }

    override fun onActivityPaused(activity: Activity) {
    }

    override fun onActivityStopped(activity: Activity) {
    }

    override fun onActivitySaveInstanceState(activity: Activity, outState: Bundle) {
    }

    override fun onActivityDestroyed(activity: Activity) {
        if (activity is FragmentActivity) {
            activity.supportFragmentManager.unregisterFragmentLifecycleCallbacks(
                mFragmentLifecycleCallbacks);
            NotificationControlManager.getInstance()?.dismissDialog()
        }
    }

}