package com.rongtuoyouxuan.chatlive.stream.view.beauty

import androidx.lifecycle.*
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.MenuItemType


object BottomFragmentViewModel : ViewModel() {

//    var seekBarOffsetValue = 0;
//
//    var seekBarMaxValue = 100;

    // 弹出对话框的对象
    var dialogProducer: ((title: String, msg: String, positiveButtonClicked: () -> Unit, negativeButtonClicked: () -> Unit) -> Unit)? =
            null

    // 界面右侧点击的菜单
    val rightMenuSelected = MutableLiveData<MenuItemType.RightMenuItemType>(MenuItemType.RightMenuItemType.Unselected)





    // 设置界面右侧的选项
    fun setRightMenuSelectedOption(option: MenuItemType.RightMenuItemType) {
        rightMenuSelected.value = option
    }

    // 设置底部的tab

//    /**
//     * 从SharedPreference中读取intensity
//     */
//    fun readSharedPreferenceAndSet(context: Context) {
//        val sharedPreference = SharedPreferenceUtil.getInstance()
//                .withContext(context)
//                .withName("backup")
//                .build()
//        if (sharedPreference != null) {
//            intensityMap.forEach {
//                intensityMap[it.key] = sharedPreference.getInt(it.key.javaClass.name, it.value)
//                handleEffect(
//                        it.key,
//                        it.value
//                )
//            }
//
//            Logger.d("读取sharePreference")
//        }
//    }
//
//    /**
//     * 将map保存到SharedPreference
//     */
//    fun saveSharedPreference(context: Context) {
//        val sharedPreference = SharedPreferenceUtil.getInstance()
//                .withContext(context)
//                .withName("backup")
//                .build()
//        if (sharedPreference != null) {
//            val editor = sharedPreference.edit()
//            intensityMap.forEach {
//                editor.putInt(it.key.javaClass.name, it.value)
//            }
//            editor.apply()
//            Logger.d("写入sharePreference")
//        }
//    }

}