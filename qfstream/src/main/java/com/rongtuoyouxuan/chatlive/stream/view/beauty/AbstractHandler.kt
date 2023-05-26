package com.rongtuoyouxuan.chatlive.stream.view.beauty

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.TextureView
import android.widget.ImageView
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.entity.MosaicType
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.entity.PreviewSize

/**
 * 美颜、特效等的处理抽象类
 * author: qingyingliu
 * date: 3/18/21
 */
abstract class AbstractHandler {

    /**
     * 在App启动时调用，用于初始化引擎等
     */
    abstract fun onCreate(context: Context)

    /**
     * 翻转相机
     */
    abstract fun flipCamera()

    /**
     * 磨皮功能
     * @param intensity 磨皮的强度
     */
    abstract fun polishSkin(intensity: Int)

    /**
     * 美白功能
     * @param intensity 美白的强度
     */
    abstract fun whitenSkin(intensity: Int)

    /**
     * 红润功能
     * @param intensity 红润的强度
     */
    abstract fun rosy(intensity: Int)

    /**
     * 锐化功能
     * @param intensity 锐化的强度
     */
    abstract fun sharpen(intensity: Int)

    /**
     * 去法令纹功能
     * @param intensity 去法令纹的强度
     */
    abstract fun wrinklesRemoving(intensity: Int)

    /**
     * 去黑眼圈功能
     * @param intensity 去黑眼圈的强度
     */
    abstract fun darkCirclesRemoving(intensity: Int)

    /**
     * 瘦脸功能
     * @param intensity 瘦脸的强度
     */
    abstract fun thinFace(intensity: Int)

    /**
     * 大眼功能
     * @param intensity 大眼的强度
     */
    abstract fun bigEye(intensity: Int)


    /**
     * 亮眼功能
     * @param intensity 亮眼的强度
     */
    abstract fun eyesBrightening(intensity: Int)

    /**
     * 长下巴功能
     * @param intensity 长下巴的强度
     */
    abstract fun longChin(intensity: Int)

    /**
     * 小嘴功能
     * @param intensity 小嘴的强度
     */
    abstract fun smallMouth(intensity: Int)

    /**
     * 白牙功能
     * @param intensity 白牙的强度
     */
    abstract fun teethWhitening(intensity: Int)


    /**
     * 瘦鼻功能
     * @param intensity 瘦鼻的强度
     */
    abstract fun noseNarrowing(intensity: Int)

    /**
     * 长鼻功能
     * @param intensity 长鼻的强度
     */
    abstract fun noseLengthening(intensity: Int)

    /**
     * 小脸功能
     * @param intensity 小脸的强度
     */
    abstract fun faceShortening(intensity: Int)

    /**
     * 瘦下颌骨功能
     * @param intensity 瘦下颌骨的强度
     */
    abstract fun mandibleSlimming(intensity: Int)

    /**
     * 瘦颧骨功能
     * @param intensity 瘦颧骨的强度
     */
    abstract fun cheekboneSlimming(intensity: Int)

    /**
     * 缩额头功能
     * @param intensity 缩额头的强度
     */
    abstract fun foreheadShortening(intensity: Int)

    /**
     * 设置口红
     * @param lookupTablePath
     */
    abstract fun setLipstick(lookupTablePath: String)

    /**
     * 设置口红参数
     * @param lipstickEffectStrength
     */
    abstract fun setLipstickParam(intensity: Int)

    /**
     * 设置腮红
     * @param lookupTablePath
     */
    abstract fun setCheek(lookupTablePath: String)

    /**
     * 设置腮红参数
     * @param cheekEffectStrength
     */
    abstract fun setCheekParam(intensity: Int)

    /**
     * 设置眼线
     * @param lookupTablePath
     */
    abstract fun setEyeliner(lookupTablePath: String)

    /**
     * 设置眼线参数
     * @param eyelinerEffectStrength
     */
    abstract fun setEyelinerParam(intensity: Int)

    /**
     * 设置眼影
     * @param lookupTablePath
     */
    abstract fun setEyeshadow(lookupTablePath: String)

    /**
     * 设置眼影参数
     * @param eyeshadowEffectStrength
     */
    abstract fun setEyeshadowParam(intensity: Int)

    /**
     * 设置眼睫毛
     * @param lookupTablePath
     */
    abstract fun setEyelash(lookupTablePath: String)

    /**
     * 设置眼睫毛参数
     * @param eyelashEffectStrength
     */
    abstract fun setEyelashParam(intensity: Int)

    /**
     * 设置美瞳
     * @param path
     */
    abstract fun setEyesColored(path: String)

    /**
     * 设置美瞳参数
     * @param intensity
     */
    abstract fun setEyesColoredParam(intensity: Int)

    /**
     * 设置风格妆
     * @param path
     */
    abstract fun setBeautifyStyle(path: String)

    /**
     * 设置风格妆参数
     * @param intensity
     */
    abstract fun setBeautifyStyleParam(intensity: Int)

    /**
     * 设置风格滤镜
     * @param lookupTablePath
     */
    abstract fun setFilter(lookupTablePath: String)

    /**
     * 设置风格滤镜参数
     * @param colorfulStyleEffectStrength
     */
    abstract fun setFilterParam(intensity: Int)

    /**
     * 开启绿幕分割
     */
    abstract fun enableGreenSplit()
    /**
     * 绿幕分割调整参数
     */
    abstract fun greenScreenSplit(param: Int)

    /**
     * 人像分割功能
     */
    abstract fun personImageSplit()

    /**
     * 是否开启人像分割功能
     * @param enable
     */
    abstract fun enableAISegment(enable: Boolean)

    /**
     * 设置人像分割背景
     * @param imageUrl
     */
    abstract fun setPortraitSegmentationBackgroundPath()

    /**
     * 开启人像分割背景模糊
     * @param enable
     */
    abstract fun enablePortraitSegmentationBackgroundBlur(enable: Boolean)

    /**
     * 设置人像分割背景模糊强度
     * @param enable
     */
    abstract fun setPortraitSegmentationBackgroundBlurParam(backgroundBlurEffectStrength: Int);


    /**
     * 开启人像分割背景马赛克
     * @param enable
     */
    abstract fun enablePortraitSegmentationBackgroundMosaic(enable: Boolean)

    /**
     * 设置人像分割背景马赛克参数
     * @param mosaicEffectStrength
     * @param type
     */
    abstract fun setPortraitSegmentationBackgroundMosaicParam(
        mosaicEffectStrength: Int,
        type: MosaicType?
    )


    /**
     * 设置贴纸挂件
     * @param path 绝对路径，注意权限
     */
    abstract fun setPendant(path: String)


    /**
     * 设置贴纸挂件开启
     * @param enable
     */
    abstract fun enablePendant(enable: Boolean)

    /**
     * 关闭特效
     */
    abstract fun noEffect()

    /**
     * 按下对比按钮
     */
    abstract fun onCompareButtonPress()

    /**
     * 释放对比按钮
     */
    abstract fun onCompareButtonRelease()

    /**
     * 点击拍照
     */
    abstract fun shoot(textureView: TextureView, imageView: ImageView)

    /**
     * 启动相机
     */
    abstract fun startCamera()

    /**
     * 关闭相机
     */
    abstract fun stopCamera()

    /**
     * 保存照片
     */
    abstract fun savePicture(bitmap: Bitmap, activity: Activity, callback: (() -> Unit)? = null)


    /**
     * 保存照片
     */
    abstract fun savePicture2(bitmap: Bitmap, activity: Activity, callback: (() -> Unit)? = null)
    /**
     * 设置预览surface
     */
    abstract fun setPreviewSurface(textureView: TextureView)


    /**
     * 返回所有可用分辨率
     */
    abstract fun getAllPreviewResolutionSize():List<PreviewSize>

    /**
     * 设置分辨率
     */
    abstract fun setResolution(width: Int, height: Int)

    /**
     * 返回默认分辨率
     */
    abstract fun getDefaultResolution(): PreviewSize

}