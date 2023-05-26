package com.rongtuoyouxuan.chatlive.stream.view.beauty

import android.app.Activity
import android.content.Context
import android.graphics.Bitmap
import android.view.TextureView
import android.view.View
import android.widget.ImageView
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.ImageTextureView
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.SDKManager
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.callback.FaceDetectionCallback
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.entity.MosaicType
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.entity.PreviewSize
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.util.ZegoUtil
import java.io.File
import java.util.*


/**
 * 测试接口调用是否正常
 * author: qingyingliu
 * date: 3/19/21
 */
object TestHandler : AbstractHandler() {

    private const val TAG = "TestHandler"

    private var testImagePath: String? = null

    private var pendantPath: String? = ""

    override fun onCreate(context: Context) {
        val path = context.externalCacheDir!!.path

//        if(AppUtils.getApp(context) != AppUtils.getVersionCode(context))
//        {
//            val file = File(path)
//            ZegoUtil.deleteDir(file)
//            file.mkdirs()
//            AppUtils.setApp(context,AppUtils.getVersionCode(context))
//        }



//        testImagePath = "$path/Models/test.jpg"
//        ZegoUtil.copyFileFromAssets(context, "test.jpg", "$path/Models/test.jpg")
//
//        val aiModeInfoList = copyAiModeInfoList(context)
//        val resourcesInfoList = copyResourcesInfoList(context)

        openAllBeautifyEffects()


        ULog.d(TAG, "Handler onCreate, init SDKManager")
    }

    private fun copyAiModeInfoList(context: Context): ArrayList<String>? {
        val path = context.externalCacheDir!!.path
        val faceDetection = "effect/FaceDetectionModel.model"
        val segmentation = "effect/SegmentationModel.model"
        ZegoUtil.copyFileFromAssets(context, faceDetection, path + File.separator + faceDetection)
        ZegoUtil.copyFileFromAssets(context, segmentation, path + File.separator + segmentation)
        val aiModeInfoList = ArrayList<String>()
        aiModeInfoList.add(path + File.separator + faceDetection)
        aiModeInfoList.add(path + File.separator + segmentation)
        return aiModeInfoList
    }

    private fun copyResourcesInfoList(context: Context): ArrayList<String>? {
        val path = context.externalCacheDir!!.path
        val faceWhitening = "Resources/FaceWhiteningResources.bundle"
        val pendantResources = "Resources/PendantResources.bundle"
        val rosyResources = "Resources/RosyResources.bundle"
        val teethWhiteningResources = "Resources/TeethWhiteningResources.bundle"
        val commonResources = "Resources/CommonResources.bundle"
        ZegoUtil.copyFileFromAssets(context, faceWhitening, path + File.separator + faceWhitening)
        ZegoUtil.copyFileFromAssets(
                context,
                pendantResources,
                path + File.separator + pendantResources
        )
        ZegoUtil.copyFileFromAssets(context, rosyResources, path + File.separator + rosyResources)
        ZegoUtil.copyFileFromAssets(
                context,
                teethWhiteningResources,
                path + File.separator + teethWhiteningResources
        )
        ZegoUtil.copyFileFromAssets(context, commonResources, path + File.separator + commonResources)
        val resourcesInfoList = ArrayList<String>()
        resourcesInfoList.add(path + File.separator + faceWhitening)
        resourcesInfoList.add(path + File.separator + pendantResources)
        resourcesInfoList.add(path + File.separator + rosyResources)
        resourcesInfoList.add(path + File.separator + teethWhiteningResources)
        resourcesInfoList.add(path + File.separator + commonResources)
        return resourcesInfoList
    }

    override fun polishSkin(intensity: Int) {
        ULog.d(TAG,"polishSkin with intensity=$intensity")
        SDKManager.sharedInstance().enableSmooth(true)
        SDKManager.sharedInstance().setSmoothParam(intensity)
    }

    override fun whitenSkin(intensity: Int) {
        ULog.d(TAG,"whitenSkin with intensity=$intensity")
        SDKManager.sharedInstance().enableWhiten(true)
        SDKManager.sharedInstance().setWhitenParam(intensity)
    }

    override fun rosy(intensity: Int) {
        ULog.d(TAG,"rosy with intensity=$intensity")
        SDKManager.sharedInstance().enableRosy(true)
        SDKManager.sharedInstance().setRosyParam(intensity)
    }

    override fun sharpen(intensity: Int) {
        ULog.d(TAG,"sharpen with intensity=$intensity")
        SDKManager.sharedInstance().enableSharpen(true)
        SDKManager.sharedInstance().setSharpenParam(intensity)
    }

    override fun wrinklesRemoving(intensity: Int) {
        ULog.d(TAG,"wrinklesRemoving with intensity=$intensity")
        SDKManager.sharedInstance().enableWrinklesRemoving(true)
        SDKManager.sharedInstance().setWrinklesRemovingParam(intensity)
    }

    override fun darkCirclesRemoving(intensity: Int) {
        ULog.d(TAG,"darkCirclesRemoving with intensity=$intensity")
        SDKManager.sharedInstance().enableDarkCirclesRemoving(true)
        SDKManager.sharedInstance().setDarkCirclesRemovingParam(intensity)
    }

    override fun thinFace(intensity: Int) {
        ULog.d(TAG,"thinFace with intensity=$intensity")
        SDKManager.sharedInstance().enableFaceLifting(true)
        SDKManager.sharedInstance().setFaceLiftingParam(intensity)
    }

    override fun bigEye(intensity: Int) {
        ULog.d(TAG,"bigEye with intensity=$intensity")
        SDKManager.sharedInstance().enableBigEye(true)
        SDKManager.sharedInstance().setBigEyeParam(intensity)
    }

    override fun eyesBrightening(intensity: Int) {
        ULog.d(TAG,"eyesBrightening with intensity=$intensity")
        SDKManager.sharedInstance().enableEyesBrightening(true)
        SDKManager.sharedInstance().setEyesBrighteningParam(intensity)
    }

    override fun longChin(intensity: Int) {
        ULog.d(TAG,"longChin with intensity=$intensity")
        SDKManager.sharedInstance().enableLongChin(true)
        SDKManager.sharedInstance().setLongChinParam(intensity)
    }

    override fun smallMouth(intensity: Int) {
        ULog.d(TAG,"smallMouth with intensity=$intensity")
        SDKManager.sharedInstance().enableSmallMouth(true)
        SDKManager.sharedInstance().setSmallMouthParam(intensity)
    }

    override fun teethWhitening(intensity: Int) {
        ULog.d(TAG,"teethWhitening with intensity=$intensity")
        SDKManager.sharedInstance().enableTeethWhitening(true)
        SDKManager.sharedInstance().setTeethWhiteningParam(intensity)
    }

    override fun noseNarrowing(intensity: Int) {
        ULog.d(TAG,"noseNarrowing with intensity=$intensity")
        SDKManager.sharedInstance().enableNoseNarrowing(true)
        SDKManager.sharedInstance().setNoseNarrowingParam(intensity)
    }

    override fun noseLengthening(intensity: Int) {
        ULog.d(TAG,"noseLengthening with intensity=$intensity")
        SDKManager.sharedInstance().enableNoseLengthening(true)
        SDKManager.sharedInstance().setNoseLengtheningParam(intensity)
    }

    override fun faceShortening(intensity: Int) {
        ULog.d(TAG,"faceShortening with intensity=$intensity")
        SDKManager.sharedInstance().enableFaceShortening(true)
        SDKManager.sharedInstance().setFaceShorteningParam(intensity)
    }

    override fun mandibleSlimming(intensity: Int) {
        ULog.d(TAG,"mandibleSlimming with intensity=$intensity")
        SDKManager.sharedInstance().enableMandibleSlimming(true)
        SDKManager.sharedInstance().setMandibleSlimmingParam(intensity)
    }

    override fun cheekboneSlimming(intensity: Int) {
        ULog.d(TAG,"cheekboneSlimming with intensity=$intensity")
        SDKManager.sharedInstance().enableCheekboneSlimming(true)
        SDKManager.sharedInstance().setCheekboneSlimmingParam(intensity)
    }

    override fun foreheadShortening(intensity: Int) {
        ULog.d(TAG,"foreheadShortening with intensity=$intensity")
        SDKManager.sharedInstance().enableForeheadShortening(true)
        SDKManager.sharedInstance().setForeheadShorteningParam(intensity)
    }

    /**
     * 设置口红
     * @param lookupTablePath
     */
    override fun setLipstick(lookupTablePath: String) {
        SDKManager.sharedInstance().setLipstick(lookupTablePath)
    }

    /**
     * 设置口红参数
     * @param intensity
     */
    override fun setLipstickParam(intensity: Int) {
        ULog.d(TAG,"setLipstickParam with intensity=$intensity")
        SDKManager.sharedInstance().setLipstickParam(intensity)
    }

    /**
     * 设置腮红
     * @param lookupTablePath
     */
    override fun setCheek(lookupTablePath: String) {
        SDKManager.sharedInstance().setCheek(lookupTablePath)
    }

    /**
     * 设置腮红参数
     * @param intensity
     */
    override fun setCheekParam(intensity: Int) {
        ULog.d(TAG,"setCheekParam with intensity=$intensity")
        SDKManager.sharedInstance().setCheekParam(intensity)
    }

    /**
     * 设置眼线
     * @param lookupTablePath
     */
    override fun setEyeliner(lookupTablePath: String) {
        SDKManager.sharedInstance().setEyeliner(lookupTablePath)
    }

    /**
     * 设置眼线参数
     * @param intensity
     */
    override fun setEyelinerParam(intensity: Int) {
        ULog.d(TAG,"setEyelinerParam with intensity=$intensity")
        SDKManager.sharedInstance().setEyelinerParam(intensity)
    }

    /**
     * 设置眼影
     * @param lookupTablePath
     */
    override fun setEyeshadow(lookupTablePath: String) {
        SDKManager.sharedInstance().setEyeshadow(lookupTablePath)
    }

    /**
     * 设置眼影参数
     * @param intensity
     */
    override fun setEyeshadowParam(intensity: Int) {
        ULog.d(TAG,"setEyeshadowParam with intensity=$intensity")
        SDKManager.sharedInstance().setEyeshadowParam(intensity)
    }

    /**
     * 设置眼睫毛
     * @param lookupTablePath
     */
    override fun setEyelash(lookupTablePath: String) {
        SDKManager.sharedInstance().setEyelash(lookupTablePath)
    }

    /**
     * 设置眼睫毛参数
     * @param intensity
     */
    override fun setEyelashParam(intensity: Int) {
        ULog.d(TAG,"setEyelashParam with intensity=$intensity")
        SDKManager.sharedInstance().setEyelashParam(intensity)
    }

    override fun setEyesColored(path: String) {
        SDKManager.sharedInstance().setEyesColored(path)
    }

    override fun setEyesColoredParam(intensity: Int) {
        SDKManager.sharedInstance().setEyesColoredParam(intensity)
    }

    override fun setBeautifyStyle(path: String) {
        SDKManager.sharedInstance().setBeautifyStyle(path)
    }

    override fun setBeautifyStyleParam(intensity: Int) {
        SDKManager.sharedInstance().setBeautifyStyleParam(intensity)
    }

    /**
     * 设置风格滤镜
     * @param lookupTablePath
     */
    override fun setFilter(lookupTablePath: String) {
        SDKManager.sharedInstance().setFilter(lookupTablePath)
    }

    /**
     * 设置风格滤镜参数
     * @param intensity
     */
    override fun setFilterParam(intensity: Int) {
        ULog.d(TAG,"setFilterParam with intensity=$intensity")
        SDKManager.sharedInstance().setFilterParam(intensity)
    }

    override fun enableGreenSplit() {
        ULog.d(TAG,"enable greenSplit")
//        setPendant("")
        SDKManager.sharedInstance().enableAISegment(false)
        SDKManager.sharedInstance().enableChromaKey(true)
        SDKManager.sharedInstance().setChromaKeyBackgroundPath(testImagePath)
    }

    override fun greenScreenSplit(param: Int) {
        ULog.d(TAG,"greenScreenSplit with param=$param")
        SDKManager.sharedInstance().greenScreenSplit(param)
    }

    override fun personImageSplit() {
        ULog.d(TAG,"personImageSplit")
//        setPendant("")
        SDKManager.sharedInstance().enableChromaKey(false)
        SDKManager.sharedInstance().enableAISegment(true)
        SDKManager.sharedInstance().setPortraitSegmentationBackgroundPath(testImagePath)

    }

    override fun enableAISegment(enable: Boolean){
        SDKManager.sharedInstance().enableAISegment(enable)
    }

    override fun setPortraitSegmentationBackgroundPath(){
        SDKManager.sharedInstance().setPortraitSegmentationBackgroundPath(testImagePath)
    }

    override fun enablePortraitSegmentationBackgroundBlur(enable: Boolean){
        SDKManager.sharedInstance().enablePortraitSegmentationBackgroundBlur(true)
    }

    override fun setPortraitSegmentationBackgroundBlurParam(backgroundBlurEffectStrength: Int){
        SDKManager.sharedInstance().setPortraitSegmentationBackgroundBlurParam(
                backgroundBlurEffectStrength
        )
    }

    override fun enablePortraitSegmentationBackgroundMosaic(enable: Boolean){
        SDKManager.sharedInstance().enablePortraitSegmentationBackgroundMosaic(enable);
    }

    override fun setPortraitSegmentationBackgroundMosaicParam(
            mosaicEffectStrength: Int,
            type: MosaicType?
    ){
        SDKManager.sharedInstance().setPortraitSegmentationBackgroundMosaicParam(
                mosaicEffectStrength,
                type
        )
    }

    override fun setPendant(path: String) {
        SDKManager.sharedInstance().apply {
//            enableChromaKey(false)
//            enableAISegment(false)
//            enablePortraitSegmentationBackgroundBlur(false)
//            enablePortraitSegmentationBackgroundMosaic(false)
            setPendant(path)
            pendantPath = path
        }
    }

    override fun enablePendant(enable: Boolean){
        if(enable)
        {
            SDKManager.sharedInstance().setPendant(pendantPath ?: "")
        }else{
            SDKManager.sharedInstance().setPendant("")
        }
    }

    override fun noEffect() {
        ULog.d(TAG,"no effect")
        SDKManager.sharedInstance().apply {
            enableChromaKey(false)
            enableAISegment(false)
            enablePortraitSegmentationBackgroundBlur(false)
            enablePortraitSegmentationBackgroundMosaic(false)
//            setPendant("")
        }
    }

    override fun onCompareButtonPress() {
        ULog.d(TAG,"onCompareButtonPress")
        SDKManager.sharedInstance().stopRenderAllEffects()
        ImageTextureView.setShowEffects(false)
        //closeAllEffects()
    }

    override fun onCompareButtonRelease() {
        ULog.d(TAG,"onCompareButtonRelease")
        SDKManager.sharedInstance().startRenderAllEffects()
        ImageTextureView.setShowEffects(true)
        //openAllBeautifyEffects()
    }

    override fun flipCamera() {
        ULog.d(TAG,"flip camera")
        // 翻转相机，函数名有点怪
        SDKManager.sharedInstance().setFrontCam()
    }

    override fun shoot(textureView: TextureView, imageView: ImageView) {
//        textureView.visibility = View.INVISIBLE
        val bitmap = textureView.bitmap
        imageView.visibility = View.VISIBLE
        imageView.setImageBitmap(bitmap)
        ULog.d(TAG,"shoot")

    }

    override fun startCamera() {
        ULog.d(TAG,"start camera")
        SDKManager.sharedInstance().startCamera()
    }

    override fun stopCamera() {
        ULog.d(TAG,"stop camera")
        SDKManager.sharedInstance().stopCamera()
    }

    override fun savePicture(bitmap: Bitmap, activity: Activity, callback: (() -> Unit)?) {
        ULog.d(TAG,"save picture")
//        savePhoto(bitmap, activity) { s: String, uri: Uri ->
//            ULog.d(TAG,"图片保存到: s=$s,uri=$uri")
//            callback?.invoke()
//        }
    }

    override fun savePicture2(bitmap: Bitmap, activity: Activity, callback: (() -> Unit)?) {
        ULog.d(TAG,"save picture")
//        savePhoto2(bitmap, activity) { s: String, uri: Uri ->
//            Logger.d("图片保存到: s=$s,uri=$uri")
//            callback?.invoke()
//        }
    }

    override fun setPreviewSurface(textureView: TextureView) {
        ULog.d(TAG,"set preview view")
        SDKManager.sharedInstance().setView(textureView)
    }

    /**
     * 开启人脸检测
     * @param enable
     */
    fun enableFaceDetection(enable: Boolean) {
        ULog.d(TAG, "enableFaceDetection :enable$enable")
        SDKManager.sharedInstance().enableFaceDetection(enable)
    }

    /**
     * 人脸检测回调
     */
    fun setFaceDetectionCallback(faceDetectionCallback: FaceDetectionCallback?) {
        SDKManager.sharedInstance().setFaceDetectionCallback(faceDetectionCallback)
    }

    /**
     * 一键开启所有美白功能
     */
    private fun openAllBeautifyEffects() {
        ULog.d(TAG,"开启特效")
        SDKManager.sharedInstance().apply {

            enableSmooth(true)
            enableWhiten(true)
            enableRosy(true)
            enableSharpen(true)

            enableBigEye(true)
            enableFaceLifting(true)
            enableEyesBrightening(true)
            enableLongChin(true)
            enableSmallMouth(true)
            enableTeethWhitening(true)
            enableNoseNarrowing(true)
        }
    }


    /**
     * 返回所有可用分辨率
     */
    override fun getAllPreviewResolutionSize(): List<PreviewSize> {
        return SDKManager.sharedInstance().supportedPreviewSizes ?: emptyList<PreviewSize>().also {
            ULog.d(TAG,"分辨率: $it")
        }
    }

    override fun setResolution(width: Int, height: Int) {
        ULog.d(TAG,"setResolution with $width × $height")
        SDKManager.sharedInstance().setPreviewSize(
                PreviewSize()
                    .also {
                    it.width = width
                    it.height = height
                })
    }

    override fun getDefaultResolution(): PreviewSize {
        return SDKManager.sharedInstance().previewDefault.also {
            ULog.d(TAG,"默认分辨率为${it.width}×${it.height}")
        }
    }

}