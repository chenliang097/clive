package com.rongtuoyouxuan.chatlive.stream.view.beauty.util

import android.content.Context
import androidx.appcompat.app.AppCompatActivity

/**
 * author: qingyingliu
 * date: 3/26/21
 */

object AppKeys{
    const val VERSION = "version"
}

object AppUtils{




    public fun getApp(context : Context) : Int {

        val sharedPreferences = context.getSharedPreferences("app", AppCompatActivity.MODE_PRIVATE)
        val app =  sharedPreferences?.getInt(AppKeys.VERSION,0)
        return app?:0
    }

    public fun getVersionCode(context:Context) :Int{
        try {
            val packageManager = context.packageManager;
            val packageInfo = packageManager.getPackageInfo(context.packageName, 0);
            return packageInfo.versionCode;
        } catch (e  : Exception) {
            e.printStackTrace();
        }
        return 0
    }


    fun setApp(context : Context,value : Int) {

        val sharedPreferences = context.getSharedPreferences("app", AppCompatActivity.MODE_PRIVATE)
        val editor = sharedPreferences.edit()
        editor.putInt(AppKeys.VERSION,value)
        editor.apply()

    }
}
