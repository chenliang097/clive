package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.Manifest
import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.content.DialogInterface
import android.content.Intent
import android.location.*
import android.net.Uri
import android.os.Handler
import android.os.Looper
import android.os.Message
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.AttributeSet
import android.util.Log
import android.view.*
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.lifecycleScope
import com.blankj.utilcode.util.StringUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StartPushStreamRequest
import com.rongtuoyouxuan.chatlive.biz2.model.stream.StartStreamInfoBean
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamControllerViewModel
import com.rongtuoyouxuan.chatlive.stream.viewmodel.StreamViewModel
import com.rongtuoyouxuan.chatlive.util.DirectoryUtils
import com.rongtuoyouxuan.chatlive.util.DirectoryUtils.getCacheFilesDirFile
import com.rongtuoyouxuan.chatlive.util.KeyBoardUtils
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.TransferLoadingUtil
import com.rongtuoyouxuan.libuikit.dialog.BottomDialog
import com.rongtuoyouxuan.qfcommon.dialog.ShareBottomDialog
import com.rongtuoyouxuan.qfcommon.eventbus.LiveEventData
import com.rongtuoyouxuan.qfcommon.eventbus.MLiveEventBus
import com.tbruyelle.rxpermissions2.RxPermissions
import com.yalantis.ucrop.UCrop
import com.zhihu.matisse.Matisse
import com.zhihu.matisse.MatisseUtil
import com.zhihu.matisse.MatisseUtil.onPermissionListener
import kotlinx.android.synthetic.main.qf_stream_layout_preview.view.*
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import java.io.File
import java.util.*


class StreamPreviewLayout @JvmOverloads constructor(
    mContext: Context,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = 0
) : RelativeLayout(mContext, attrs, defStyleAttr), View.OnClickListener {

    private var countAnimationImg: ImageView? = null
    private var titleEdit: EditText? = null
    private var popupWindow: PopupWindow? = null
    private var mPhotoPath: String? = null
    private var viewbutton : View? = null//记录点击的view用于弹出popupwindow
    private var inputTxt:String? = ""
    private var inputTxtBoolean:Boolean? = false

    private var mTips = 0
    private var startLiveListener: StartLiveListener? = null

    private val controllerViewModel: StreamControllerViewModel
    private val mStreamViewModel: StreamViewModel

    protected var locationManager: LocationManager? = null
    private var latitude:Double? = 0.00;
    private var longitude:Double? = 0.00;
    private var addPhotoPath:String? = "";
    private var curIndex: Int = 0
    private var startStreamInfoBean:StartStreamInfoBean? = null
    private var locationSwitch = false
    private var isFrontCamera = false

    @SuppressLint("HandlerLeak")
    private var handler = object :Handler(){
        override fun handleMessage(msg: Message) {
            initLoacation()
        }
    }

    companion object {
        const val TYPE_LIVE = "live"
        private const val PICK_IMAGE_REQUEST_CODE = 0x111
        private const val CAMERA_REQ_CODE = 0x112
        const val STREAM_TITLE = "stream_title"
        const val STREAM_COVER = "stream_cover"
    }

    init {
        initView()
        controllerViewModel = ViewModelUtils.get(mContext as FragmentActivity, StreamControllerViewModel::class.java)
        mStreamViewModel = ViewModelUtils.get(mContext, StreamViewModel::class.java)
        initListener(mContext as LifecycleOwner)
        mStreamViewModel.getStreamRoomInfo(DataBus.instance().USER_ID, DataBus.instance().USER_NAME)
    }

    private fun initView() {
        inflate(context, R.layout.qf_stream_layout_preview, this)
        titleEdit = findViewById(R.id.streamPreviewEditTitle)
    }

    private fun initListener(lifecycleOwner: LifecycleOwner) {
        streamPreviewBeautyTxt?.setOnClickListener(this)
        streamPreviewBtnStart?.setOnClickListener(this)
        streamPreviewTurnCameraTxt?.setOnClickListener(this)
        streamPreviewCloseImg?.setOnClickListener(this)
        streamPreviewImgCoverLayout?.setOnClickListener(this)
        streamPreviewLocationTxt?.setOnClickListener(this)
        streamPreviewKeijianTxt?.setOnClickListener(this)
        streamPreviewShareTxt?.setOnClickListener(this)
        mStreamViewModel.startStreamEvent.observe(lifecycleOwner) {
            streamPreviewBtnStart?.isEnabled = true
            visibility = GONE
        }
        mStreamViewModel.showReconnectEvent.observe(lifecycleOwner) { s ->
            streamPreviewBtnStart!!.isEnabled = true
            streamPreviewLayout.visibility = VISIBLE
            LaToastUtil.showShort(s)
        }

        controllerViewModel.mControllerVisibility.observe(lifecycleOwner) { aBoolean ->
            if (popupWindow != null && !aBoolean!!) {
                popupWindow!!.dismiss()
            } else {
                if (viewbutton != null && mTips != 0) {
                    showDeletePop()
                }
            }
        }
        mStreamViewModel.setCover.observe(lifecycleOwner) { streamPreviewImgCoverLayout!!.performClick() }

        controllerViewModel.uploadLiveData.observe(lifecycleOwner) { t ->
            TransferLoadingUtil.dismissDialogLoading(context)
            setPhoto(t)
        }

        controllerViewModel.ActivityResultEvent.observe(lifecycleOwner) { activityResult ->
            onActivityResult(activityResult!!.requestCode, activityResult.resultCode,
                activityResult.data)
        }

        mStreamViewModel.startPushStreamInfoLiveData.observe(lifecycleOwner){
            startStreamInfoBean = it
            if(TextUtils.isEmpty(it.data.last_cover_image)){
                setPhoto(it.data.anchor_avatar)
            }else{
                setPhoto(it.data.last_cover_image)
            }
            if(!TextUtils.isEmpty(it.data.scene_title)) {
                getLocalHostInfo(it.data.scene_title)
            }
            if(it.data.back_video) {
                isFrontCamera = false
                mStreamViewModel.onSwitchCameraEnd(StreamViewModel.CAMERA_BACK)
            }else{
                isFrontCamera = true
                mStreamViewModel.onSwitchCameraEnd(StreamViewModel.CAMERA_FRONT)
            }
            mStreamViewModel.switchCamera()
            if(it.data.position_switch){
                locationSwitch = true
                handler.sendEmptyMessageDelayed(0, 500)
            }else{
                locationSwitch = false
                streamPreviewLocationTxt?.text = StringUtils.getString(R.string.stream_unknown_location)
            }

        }

        MLiveEventBus.get(LiveEventData.LIVE_ALLOW_RANGE).observe(lifecycleOwner){
            when(it){
                "1"->{
                    streamPreviewKeijianTxt.text = context.getString(R.string.stream_allow_ranage).plus(">")
                }
                "2"->{
                    streamPreviewKeijianTxt.text = context.getString(R.string.stream_allow_ranage_no).plus(">")
                }
            }
        }
        titleEdit?.setOnTouchListener { view, motionEvent ->
            streamPreviewEditIcon.visibility = View.GONE
            titleEdit?.isCursorVisible = true
            false
        }
    }

    fun setParams(w: Int, h: Int) {
        val layoutParams = streamPreviewLayout!!.layoutParams
        layoutParams.width = w
        layoutParams.height = h
        streamPreviewLayout!!.layoutParams = layoutParams
    }

    fun setStartLiveListener(startLiveListener: StartLiveListener?) {
        this.startLiveListener = startLiveListener
    }

    private fun setPhoto(url: String?) {
        addPhotoPath = url
        streamPreviewChangeCoverImg.visibility = View.GONE
        GlideUtils.loadImage(context, url, streamPreviewImgCover, R.drawable.rt_default_avatar)
        ULog.e("clll", "setPhotofullPath:$url")
    }

    override fun onClick(v: View) {
        when (v.id) {
            R.id.streamPreviewBeautyTxt -> {
                controllerViewModel.setBeautySettingVisibility(true)
//                controllerViewModel.mControllerVisibility.setValue(false)
            }
            R.id.streamPreviewBtnStart -> {
                Log.d("clll", "11111---")
                isCanStartStream()
            }
            R.id.streamPreviewImgCoverLayout -> {
                showBottomDialog()
            }
            R.id.streamPreviewCloseImg -> {
                mStreamViewModel?.finishActivity()
            }
            R.id.streamPreviewTurnCameraTxt -> {
//                mStreamViewModel.setUseFrontCamera(isUserFrontCamera)
                if(mStreamViewModel.cameraId.value == StreamViewModel.CAMERA_FRONT){
                    mStreamViewModel.onSwitchCameraEnd(StreamViewModel.CAMERA_BACK)
                }else{
                    mStreamViewModel.onSwitchCameraEnd(StreamViewModel.CAMERA_FRONT)
                }
                mStreamViewModel?.switchCamera()
            }
            R.id.streamPreviewLocationTxt ->{
                showLocationPermissonDialog()
            }
            R.id.streamPreviewKeijianTxt ->{
                Router.toStartLiveVisibleRangeDialog(startStreamInfoBean?.data?.scene_id_str, startStreamInfoBean?.data?.room_id_str)
            }
            R.id.streamPreviewShareTxt ->{
                showShareDialog()
            }
        }
    }

    private fun isCanStartStream(){
        (context as FragmentActivity).lifecycleScope.launch(Dispatchers.Main) {
            startLiving()
//            var userInfo = DataBus.instance().userInfo.value?.user_info as UserInfo.UserInfoBean
//            if(userInfo.user_level >= 10 || userInfo.realcert_status == 1){
//                streamPreviewBtnStart!!.isEnabled = false
//                playCountAnimation()
//            }else{
//                DialogUtils.createStartStreamLevelDialog(context, object :StartStreamLevelDialog.HostLevelListener{
//                    override fun onApplyClick() {
//                        val token = DataBus.instance().userInfo.value?.token
//                        val url = if (APIEnvironment.isDevEnvironment()) {
//                            LiveRoomHelper.dev_apply_live_url
//                        } else if (APIEnvironment.isTestEnvironment()) {
//                            LiveRoomHelper.test_apply_live_url
//                        } else {
//                            LiveRoomHelper.online_apply_live_url
//                        }
//                        Router.toUrl(context, url.plus(token))
//                    }
//
//                    override fun onUnionClick() {
//                        val token = DataBus.instance().userInfo.value?.token
//                        val url = if (APIEnvironment.isDevEnvironment()) {
//                            LiveRoomHelper.dev_group_url
//                        } else if (APIEnvironment.isTestEnvironment()) {
//                            LiveRoomHelper.test_group_url
//                        } else {
//                            LiveRoomHelper.online_group_url
//                        }
//                        Router.toUrl(context, url.plus(token))
//                    }
//
//                }).show()
//            }
        }

    }

    fun startLive(livingType: Int?) {
        if (popupWindow != null && popupWindow!!.isShowing) {
            mTips = 0
            viewbutton = null
            popupWindow!!.dismiss()
        }
        var title = titleEdit?.text.toString();
        if(TextUtils.isEmpty(title)){
            title = DataBus.instance().USER_NAME
        }
        isFrontCamera = mStreamViewModel.cameraId.value == StreamViewModel.CAMERA_BACK
        if(startStreamInfoBean != null && !TextUtils.isEmpty(startStreamInfoBean?.data?.token)
            && !TextUtils.isEmpty(startStreamInfoBean?.data?.stream_id)
            && !TextUtils.isEmpty(startStreamInfoBean?.data?.room_id_str)){

            var request = StartPushStreamRequest(addPhotoPath, title, streamPreviewLocationTxt.text.toString(), longitude, latitude, locationSwitch, isFrontCamera)
            mStreamViewModel.uploadAnchorInfo(startStreamInfoBean!!, request)
        }else {
            mStreamViewModel.startRequestPullUrl(
                title, addPhotoPath, longitude, latitude,
                streamPreviewLocationTxt.text.toString(),
                DataBus.instance().USER_ID, DataBus.instance().USER_NAME, locationSwitch, isFrontCamera
            )
        }
    }

    private fun showLocationPermissonDialog() {
        val builder = BottomDialog.Builder(context)
        builder.setTitle(R.string.rt_stream_location_dialog_ins)
        builder.setPositiveButton(StringUtils.getString(R.string.rt_stream_show_loacation),
            { dialog, p1 ->
                locationSwitch = true
                handler.sendEmptyMessageDelayed(0, 500)
                dialog.dismiss()
            }, R.color.rt_c_3478F6)
        builder.setPositiveButtonTwo(StringUtils.getString(R.string.rt_stream_hide_loacation),
            { dialog, p1 ->
                locationSwitch = false
                streamPreviewLocationTxt?.text = StringUtils.getString(R.string.stream_unknown_location)
                dialog.dismiss()
            }, R.color.rt_c_3478F6)
        builder.setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
        builder.create().show()
    }

    private fun showBottomDialog() {
        val builder = BottomDialog.Builder(context)
        builder.setPositiveButton(context.getString(R.string.album),
            { dialog, p1 ->
                requestPermission(MatisseUtil.ALBUM)
                dialog?.dismiss()
            }, context.resources.getColor(R.color.qf_libutils_color_333333), R.drawable.bg_page_more_top)
        builder.setPositiveButtonTwo(R.string.camera) { dialog, which ->
            requestPermission(MatisseUtil.CAMERA)
            dialog.dismiss()
        }
        builder.setNegativeButton(R.string.cancel) { dialog, which -> dialog.dismiss() }
        builder.create().show()
    }

    /**
     * 申请权限
     *
     * @param photoType 拍照或者相册
     */
    private fun requestPermission(photoType: Int) {
        MatisseUtil.requestPermission(
            context as FragmentActivity, StreamActivity.DEFAULT_SETTINGS_REQ_CODE,
            object : onPermissionListener {
                override fun onSuccess() {
                    if (MatisseUtil.ALBUM == photoType) {
                        MatisseUtil.navigation2Album(
                            context as Activity, 1,
                            PICK_IMAGE_REQUEST_CODE
                        )
                    } else if (MatisseUtil.CAMERA == photoType) {
                        mPhotoPath = MatisseUtil.openCamera(
                            context as Activity,
                            CAMERA_REQ_CODE
                        )
                    }
                }

                override fun onCancel() {}
            })
    }

    private fun cropImage(sourceUri: String?) {
        val destinationUri = Uri.fromFile(
            File(getCacheFilesDirFile(DirectoryUtils.DIRECTORY_IMAGES), System.currentTimeMillis().toString() + ".jpg")
        )
        UCrop.of(Uri.fromFile(File(sourceUri)), destinationUri)
            .withAspectRatio(6f, 6f)
            .start((context as StreamActivity))
    }

    //开始直播
    public fun startLiving() {
        startLive(curIndex)
    }

    override fun setVisibility(visibility: Int) {
        mStreamViewModel.isPreviewVisible.value = visibility == VISIBLE
        super.setVisibility(visibility)
    }

    interface StartLiveListener {
        fun onVoiceRoomStartLive()
    }

    private fun showDeletePop() {
        if (viewbutton != null && mTips != 0) {
            showDeletePop(viewbutton, mTips)
        }
    }

    private fun showDeletePop(v: View?, tips: Int) {
        val contentView = LayoutInflater.from(context).inflate(R.layout.qf_stream_item_activity_pop, null)
        val tvActivityText = contentView.findViewById<TextView>(R.id.tv_share_content)
        tvActivityText.setText(tips)
        popupWindow = PopupWindow(contentView)
        popupWindow!!.width = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow!!.height = ViewGroup.LayoutParams.WRAP_CONTENT
        popupWindow!!.contentView = contentView
        popupWindow!!.setOnDismissListener { popupWindow = null }
        contentView.measure(MeasureSpec.UNSPECIFIED, MeasureSpec.UNSPECIFIED)
        val popupHeight = contentView.measuredHeight
        val popupWidth = contentView.measuredWidth
        //获取需要在其上方显示的控件的位置信息
        val location = IntArray(2)
        v!!.getLocationOnScreen(location)
        viewbutton = v
        mTips = tips
        //在控件上方显示    向上移动y轴是负数
        popupWindow!!.showAtLocation(
            v, Gravity.NO_GRAVITY,
            location[0] + v.width / 2 - popupWidth / 2,
            location[1] + v.height + 20)
    }

    private fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        when (requestCode) {
            PICK_IMAGE_REQUEST_CODE -> if (resultCode == Activity.RESULT_OK) {
                val uriList = Matisse.obtainResult(data)
                val pathList = Matisse.obtainPathResult(data)
                if (uriList != null && uriList.size > 0 && pathList != null && pathList.size == uriList.size) {
                    for (url in pathList) {
                        cropImage(url)
                    }
                }
            }
            CAMERA_REQ_CODE -> if (requestCode == CAMERA_REQ_CODE && resultCode == Activity.RESULT_OK) {
                val pathList = ArrayList<String?>()
                pathList.add(mPhotoPath)
                if (pathList != null && pathList.size > 0) {
                    cropImage(mPhotoPath)
                }
            }
            1001 -> startLiving()
            UCrop.REQUEST_CROP -> if (resultCode == Activity.RESULT_OK) {
                val croppedFileUri = UCrop.getOutput(data!!)
                if (croppedFileUri != null) {
                    TransferLoadingUtil.showDialogLoading(context)
                    controllerViewModel.uploadIcon(croppedFileUri.path)
                }
            } else if (resultCode == UCrop.RESULT_ERROR) {
                Toast.makeText(context, R.string.stream_crop_error, Toast.LENGTH_SHORT).show()
            }
        }
    }

    @SuppressLint("CheckResult")
    fun initLoacation(){
        var rxPermissions = RxPermissions(context as FragmentActivity)
        if (rxPermissions != null && rxPermissions?.isGranted(Manifest.permission.ACCESS_COARSE_LOCATION)){
            ULog.e("clll", "initLoacation---isGranted")
            try{
                getLocation()
            }catch (e:Exception){
                e.printStackTrace()
            }
        }else{
            ULog.e("clll", "initLoacation---requestEach")
            rxPermissions?.requestEach(Manifest.permission.ACCESS_COARSE_LOCATION)?.subscribe(){
                if (it.granted) {
                    getLocation()
                }  else{
                    longitude  = 0.00
                    latitude = 0.00
                }
            }
        }

    }

    private var location:Location? = null
    @SuppressLint("MissingPermission", "CheckResult")
    fun getLocation(){
        try{
            locationManager = context.getSystemService(Context.LOCATION_SERVICE) as LocationManager
            if(location == null) {
                location = locationManager?.getLastKnownLocation(LocationManager.NETWORK_PROVIDER)
            }
            if(location == null){
                location = locationManager?.getLastKnownLocation(LocationManager.GPS_PROVIDER)
            }
            longitude  = location?.longitude
            latitude = location?.latitude
            if(getAddress(context, location) != null && getAddress(context, location)?.size!! > 0 && !TextUtils.isEmpty(getAddress(context, location)?.get(0)?.adminArea)){
                streamPreviewLocationTxt?.text = getAddress(context, location)?.get(0)?.adminArea.plus(" >")
            }else{
                longitude = 0.00
                latitude = 0.00
                streamPreviewLocationTxt?.text = StringUtils.getString(R.string.stream_unknown_location)
            }
        }catch (e:Exception){
            e.printStackTrace()
        }
    }

    private fun getAddress(context: Context, location: Location?): List<Address>? {
        var result: List<Address>? = null
        try {
            if (location != null) {
                val gc = Geocoder(context, Locale.getDefault())
                result = gc.getFromLocation(location.latitude, location.longitude, 1)
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return result
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev?.action === MotionEvent.ACTION_DOWN) {  //把操作放在用户点击的时候
            if (KeyBoardUtils.isShouldHideKeyboard(titleEdit, ev)) { //判断用户点击的是否是输入框以外的区域
                KeyBoardUtils.hiddenSoftInput(titleEdit)
                streamPreviewEditIcon.visibility = View.VISIBLE
                titleEdit?.isCursorVisible = false
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    fun setUserInfo(userId:String?, userName:String?){
        if (!TextUtils.isEmpty(userId)) {
            DataBus.instance().USER_ID = userId!!
        }
        if (!TextUtils.isEmpty(userName)) {
            DataBus.instance().USER_NAME = userName!!
        }
        getLocalHostInfo("")
    }

    private fun getLocalHostInfo(title:String){
        if(TextUtils.isEmpty(title)) {
            var title =
                DataBus.instance().USER_NAME + StringUtils.getString(R.string.stream_prepare_edit_title_default)
            titleEdit?.setText(title)
        }else{
            titleEdit?.setText(title)
        }

    }

    private fun showShareDialog(){
        var title = titleEdit?.text.toString()
       var shareBottomDialog:ShareBottomDialog.Builder = ShareBottomDialog.Builder(context)
        shareBottomDialog.setEventListener(object :ShareBottomDialog.Builder.EventListener{
            override fun onSuccess(type: String?) {
            }

            override fun onError() {
            }

        })
        var shareContent = context.resources.getString(R.string.share_to_content, startStreamInfoBean?.data?.anchor_name)
        shareBottomDialog.create(true, "",shareContent, "https://www.baidu.com/", title).show()
    }

}