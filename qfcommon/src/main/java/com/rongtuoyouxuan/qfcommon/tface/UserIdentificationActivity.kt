package com.rongtuoyouxuan.qfcommon.tface

import android.app.ProgressDialog
import android.content.Intent
import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.text.TextPaint
import android.text.TextUtils
import android.text.style.ClickableSpan
import android.util.Log
import android.view.View
import android.widget.Toast
import androidx.lifecycle.ViewModelProviders
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.BarUtils
import com.blankj.utilcode.util.ColorUtils
import com.blankj.utilcode.util.SpanUtils
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.log.upload.ULog
import com.rongtuoyouxuan.chatlive.router.constants.RouterConstant
import com.rongtuoyouxuan.chatlive.util.LaToastUtil
import com.rongtuoyouxuan.libuikit.SimpleActivity
import com.rongtuoyouxuan.qfcommon.R
import com.rongtuoyouxuan.qfcommon.viewmodel.TencentFaceViewModel
import com.rongtuoyouxuan.qfzego.KeyCenter
import com.tencent.cloud.huiyansdkface.facelight.api.WbCloudFaceContant
import com.tencent.cloud.huiyansdkface.facelight.api.WbCloudFaceVerifySdk
import com.tencent.cloud.huiyansdkface.facelight.api.WbCloudFaceVerifySdk.InputData
import com.tencent.cloud.huiyansdkface.facelight.api.listeners.WbCloudFaceVerifyLoginListener
import com.tencent.cloud.huiyansdkface.facelight.api.result.WbFaceError
import com.tencent.cloud.huiyansdkface.facelight.api.result.WbFaceVerifyResult
import com.tencent.cloud.huiyansdkface.facelight.process.FaceVerifyStatus
import kotlinx.android.synthetic.main.activity_user_identification.*

@Route(path = RouterConstant.PATH_USER_IDENTIFICATION)
class UserIdentificationActivity : SimpleActivity() {

    private var tencentFaceViewModel:TencentFaceViewModel? = null
    private var isAgree = false
    private var progressDlg: ProgressDialog? = null
    private val isShowSuccess = false
    private var isFaceVerifyInService = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initObserver()
    }

    override fun getLayoutResId(): Int {
        return R.layout.activity_user_identification
    }

    override fun initData() {
        mTitleBar.setTitleText(resources.getString(R.string.rt_identification_title))
        SpanUtils.with(identificationAgreementTxt)
            .append(StringUtils.getString(R.string.identification_agreement))
            .appendSpace(3)
            .append(StringUtils.getString(R.string.identification_agreement_1))
            .setClickSpan(object : ClickableSpan() {
                override fun onClick(widget: View) {
                    LaToastUtil.showShort("跳转协议")
                }

                override fun updateDrawState(ds: TextPaint) {
                    ds.color = ColorUtils.getColor(R.color.rt_c_FF2434)
                    ds.isUnderlineText = false
                    identificationAgreementTxt.highlightColor = Color.TRANSPARENT
                }
            })
            .create()
    }

    override fun initListener() {
        identificationBtn.isEnabled = false
        identificationBtn.setOnClickListener{
            var name = identificationNameEdit.text.toString().trim()
            var idNum = identificationIdEdit.text.toString().trim()
            if(TextUtils.isEmpty(name)){
                return@setOnClickListener
            }
            if(TextUtils.isEmpty(idNum)){
                return@setOnClickListener
            }
            tencentFaceViewModel?.getFaceData(name, idNum, DataBus.instance().USER_ID)
        }
        identificationRadioBtn.setOnClickListener {
            if(!isAgree){
                isAgree = true
                identificationBtn.isEnabled = true
                identificationBtn.setBackgroundResource(R.drawable.rt_common_btn)
                identificationRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_pressed)
            }else{
                isAgree = false
                identificationBtn.isEnabled = false
                identificationBtn.setBackgroundResource(R.drawable.rt_common_no_pressed_btn)
                identificationRadioBtn.setImageResource(R.drawable.rt_icon_pay_type_radio_normal1)

            }
        }
    }


    private fun initObserver(){
        tencentFaceViewModel = ViewModelProviders.of(this).get(TencentFaceViewModel::class.java)
        tencentFaceViewModel?.faceDataLiveData?.observe(this
        ) { t ->
            if (t?.errCode == 0) {
                t.data?.face_id?.let { t.data?.order_no?.let { it1 ->
                    t.data?.nonce?.let { it2 ->
                        t.data?.sign?.let { it3 ->
                            initWBCloudFaceVerifySdk(it,
                                it1, it2, it3
                            )
                        }
                    }
                } }
            } else {
                LaToastUtil.showShort(t?.errMsg)
            }
        }

        tencentFaceViewModel?.faceResultLiveData?.observe(this
        ) { t ->
            if (t?.errCode == 0) {
                finish()
            } else {
                LaToastUtil.showShort(t?.errMsg)
            }
        }
    }

    private fun initWBCloudFaceVerifySdk(faceId:String, orderNo:String, nonce:String, sign:String){
        //先填好数据
        val data = Bundle()
        val inputData = InputData(
            faceId,
            orderNo,
            KeyCenter.getInstance().TENCENT_WB_APP_ID,
            KeyCenter.getInstance().TENCENT_WB_VERSION,
            nonce,
            DataBus.instance().USER_ID,
            sign,
            FaceVerifyStatus.Mode.GRADE,
            KeyCenter.getInstance().TENCENT_WB_KEY_LICENCE
        )
        data.putSerializable(WbCloudFaceContant.INPUT_DATA, inputData)
        Log.d("clll","faceId:" + inputData.faceId)
        //使用国际化设置，默认简体中文
        data.putString(WbCloudFaceContant.LANGUAGE, WbCloudFaceContant.LANGUAGE_ZH_CN)
        //颜色设置,sdk内置黑色和白色两种模式，默认白色
        //如果客户想定制自己的皮肤，可以传入WbCloudFaceContant.CUSTOM模式,此时可以配置ui里各种元素的色值
        //定制详情参考app/res/colors.xml文件里各个参数
        data.putString(WbCloudFaceContant.COLOR_MODE, WbCloudFaceContant.WHITE)
        //是否需要录制上传视频 默认不需要
        data.putBoolean(WbCloudFaceContant.VIDEO_UPLOAD, false)
        //是否播放提示音，默认不播放
        data.putBoolean(WbCloudFaceContant.PLAY_VOICE, false)
        //识别阶段合作方定制提示语,可不传，此处为demo演示
        data.putString(WbCloudFaceContant.CUSTOMER_TIPS_LIVE, "仅供体验使用 请勿用于投产!")
        //上传阶段合作方定制提示语,可不传，此处为demo演示
        data.putString(WbCloudFaceContant.CUSTOMER_TIPS_UPLOAD, "仅供体验使用 请勿用于投产!")
        //合作方长定制提示语，可不传，此处为demo演示
        //如果需要展示长提示语，需要邮件申请
        data.putString(
            WbCloudFaceContant.CUSTOMER_LONG_TIP,
            "本demo提供的appId仅用于体验，实际生产请使用控制台给您分配的appId！"
        )
        //设置选择的比对类型  默认为公安网纹图片对比
        //公安网纹图片比对 WbCloudFaceContant.ID_CRAD
        //默认公安网纹图片比对
        data.putString(WbCloudFaceContant.COMPARE_TYPE, WbCloudFaceContant.ID_CARD)

        //sdk log开关，默认关闭，debug调试sdk问题的时候可以打开,本地写日志需要外部存储权限
        //【特别注意】上线前请务必关闭sdk log开关！！！
        data.putBoolean(WbCloudFaceContant.IS_ENABLE_LOG, true);
        Log.d("clll","WbCloudFaceVerifySdk initAdvSdk")
        isFaceVerifyInService = true

        //初始化 SDK，得到是否登录 SDK 成功的结果,由 WbCloudFaceVerifyLoginListener  返回登录结果
        //【特别注意】建议对拉起人脸识别按钮做防止重复点击的操作
        //避免用户快速点击导致二次登录，二次拉起刷脸等操作引起问题
        WbCloudFaceVerifySdk.getInstance().initAdvSdk(this, data, object:
            WbCloudFaceVerifyLoginListener {

            override fun onLoginSuccess() {

                //拉起刷脸页面
                //【特别注意】请使用activity context拉起sdk
                //【特别注意】请在主线程拉起sdk！
                WbCloudFaceVerifySdk.getInstance().startWbFaceVerifySdk(
                    this@UserIdentificationActivity
                ) { result ->
                    isFaceVerifyInService = false
                    //得到刷脸结果
                    if (result != null) {
                        goToResultPage(result)
                        if (result.isSuccess) {
                            ULog.d("clll", "刷脸成功! Sign=" + result.sign + "; liveRate=" + result.liveRate +
                                        "; similarity=" + result.similarity + "riskInfo=" + result.riskInfo.toString())
                            if (!isShowSuccess) {
                                Toast.makeText(this@UserIdentificationActivity, "刷脸成功", Toast.LENGTH_SHORT).show()
                            }
                            // todo  刷脸成功可以去后台查询刷脸结果
                        } else {
                            val error = result.error
                            if (error != null) {
                                Log.d("clll", "刷脸失败！domain=" + error.domain + " ;code= " + error.code + " ;desc=" + error.desc + ";reason=" + error.reason)
                                if (error.domain == WbFaceError.WBFaceErrorDomainCompareServer) {
                                    Log.d("clll", "对比失败，liveRate=" + result.liveRate + "; similarity=" + result.similarity)
                                    // todo 虽然对比失败，但这个domain也可以去后台查询刷脸结果
                                }
                                if (!isShowSuccess) {
                                    Toast.makeText(this@UserIdentificationActivity, "刷脸失败!" + error.desc, Toast.LENGTH_LONG).show()
                                }
                            } else {
                                Log.e("clll", "sdk返回error为空！")
                            }
                        }
                    } else {
                        Log.e("clll", "sdk返回结果为空！")
                    }
                    //更新userId
                    Log.d("clll", "更新userId")
//                    userId = "WbFaceVerifyREF" + System.currentTimeMillis()
                    //刷脸结束后，释放资源
                    WbCloudFaceVerifySdk.getInstance().release()
                }
            }

            override fun onLoginFailed(error: WbFaceError?) {
                //登录失败
                Log.d("clll", "登录失败！");

                isFaceVerifyInService = false
                hideLoading()
                if (error != null) {
                    Log.d("clll","登录失败！domain=" + error.getDomain() + " ;code= " + error.getCode()
                                + " ;desc=" + error.getDesc() + ";reason=" + error.getReason()
                    )
                    if (error.getDomain() == WbFaceError.WBFaceErrorDomainParams) {
                        Toast.makeText(this@UserIdentificationActivity, "传入参数有误！" + error.getDesc(), Toast.LENGTH_SHORT).show()
                    } else {
                        Toast.makeText(this@UserIdentificationActivity, "登录刷脸sdk失败！" + error.getDesc(), Toast.LENGTH_SHORT).show()
                    }
                } else {
                    Log.e("clll","sdk返回error为空！")
                }
            }
        });

    }

    private fun initProgress() {
        progressDlg?.dismiss()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.ICE_CREAM_SANDWICH) {
            progressDlg = ProgressDialog(this)
        } else {
            progressDlg = ProgressDialog(this)
            progressDlg?.setInverseBackgroundForced(true)
        }
        progressDlg?.setMessage("加载中...")
        progressDlg?.setIndeterminate(true)
        //需要loading框无法外部取消
        progressDlg?.setCancelable(false)
        progressDlg?.setCanceledOnTouchOutside(false)
        progressDlg?.setProgressStyle(ProgressDialog.STYLE_SPINNER)
    }

    fun hideLoading() {
        if (progressDlg != null) {
            progressDlg!!.dismiss()
        }
    }

    private fun goToResultPage(result: WbFaceVerifyResult) {
        val isSuccess = result.isSuccess
        if (isSuccess) {
            if (!isShowSuccess) {
                tencentFaceViewModel?.sendTencentFaceResult(result.similarity, result.orderNo, DataBus.instance().USER_ID)
                return
            }
        } else {
        }
    }

    override fun statusBarSetting() {
        BarUtils.transparentStatusBar(this)
        BarUtils.setStatusBarLightMode(this, true)
    }

}