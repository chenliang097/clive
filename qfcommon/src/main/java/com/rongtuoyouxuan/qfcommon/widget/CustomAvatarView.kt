package com.rongtuoyouxuan.qfcommon.widget

import android.content.Context
import android.net.Uri
import android.util.AttributeSet
import android.widget.FrameLayout
import com.blankj.utilcode.util.AdaptScreenUtils
import com.blankj.utilcode.util.RomUtils
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.chatlive.image.util.GlideUtils
import com.rongtuoyouxuan.qfcommon.R
import kotlinx.android.synthetic.main.cell_custom_avatar.view.*

/*
*Create by {Mr秦} on 2022/9/29
*/
class CustomAvatarView(context: Context, attrs: AttributeSet?) : FrameLayout(context, attrs) {

    //头像框和头像比例
    private val result = 6f / 5f
//    private val result = 4f / 3f

    private var currentWidth: Int = 0
    private var currentHeight: Int = 0

    init {
        inflate(context, R.layout.cell_custom_avatar, this)
    }

    override fun onLayout(changed: Boolean, left: Int, top: Int, right: Int, bottom: Int) {
        super.onLayout(changed, left, top, right, bottom)
        if (currentWidth == 0) {
            currentWidth = width
        }
        if (currentHeight == 0) {
            currentHeight = height
        }
    }

    private fun setLayoutParamsView() {
        try {
            post {
                val lp = content.layoutParams
                lp.width = currentWidth
                lp.height = currentHeight
                content.layoutParams = lp

                val bgLp = this.layoutParams
                bgLp.width = (currentWidth * result).toInt()
                bgLp.height = (currentHeight * result).toInt()
                this.layoutParams = bgLp
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

//    private fun setLayoutParamsView() {
//        try {
//            post {
//                val lp = content.layoutParams
//                lp.width = (currentWidth / result).toInt()
//                lp.height = (currentHeight / result).toInt()
//                content.layoutParams = lp
//            }
//        } catch (e: Exception) {
//            e.printStackTrace()
//        }
//    }

    /**
     * url 链接(头像与头像框拼接)
     * isShowAvatarFrame 是否展示头像框 默认展示
     * */
    fun bindData(
        url: String?,
        placeholderId: Int = R.drawable.icon_default_avatar,
        isShowAvatarFrame: Boolean = true,
    ) {
        try {
            if (StringUtils.isTrimEmpty(url)) return
            //重置
            reset()
            val uri = Uri.parse(url)
            if (uri != null) {
                //头像框url
                val resultValue = uri.getQueryParameter("frame")

                if (isShowAvatarFrame
                    && !StringUtils.isTrimEmpty(resultValue)
                    && resultValue?.endsWith("webp", true) == true
                ) {
                    setLayoutParamsView()
                    loadImageWebp(resultValue)
                }

                GlideUtils.loadCircleImage(
                    context,
                    url,
                    content,
                    placeholderId
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    /**
     * avatarUrl 头像url
     * frameUrl 头像框url
     * isShowAvatarFrame 是否展示头像框 默认展示
     * */
    fun bindData(
        contentUrl: String?,
        frameUrl: String?,
        isLoadCircle: Boolean = true,
        placeholderId: Int = R.drawable.icon_default_avatar,
        isShowAvatarFrame: Boolean = true,
    ) {
        try {
            //重置
            reset()
            if (isShowAvatarFrame
                && !StringUtils.isTrimEmpty(frameUrl)
                && frameUrl?.endsWith("webp", true) == true
            ) {
                setLayoutParamsView()
                loadImageWebp(frameUrl)
            }


            if (!StringUtils.isTrimEmpty(contentUrl)) {
                if (!isLoadCircle) {
                    loadImageWebp(contentUrl)
                } else {
                    GlideUtils.loadCircleImage(
                        context,
                        contentUrl,
                        content,
                        placeholderId
                    )
                }

            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    fun bindData(
        url: String?,
        defaultWidth: Int = 0,
        defaultHeight: Int = 0,
        placeholderId: Int = R.drawable.icon_default_avatar,
        isShowAvatarFrame: Boolean = true,
    ) {
        try {
            if (StringUtils.isTrimEmpty(url)) return
            //重置
            reset()
            val uri = Uri.parse(url)
            if (uri != null) {
                //头像框url
                val resultValue = uri.getQueryParameter("frame")

                if (isShowAvatarFrame
                    && !StringUtils.isTrimEmpty(resultValue)
                    && resultValue?.endsWith("webp", true) == true
                ) {
                    if (currentWidth == 0) {
                        currentWidth = defaultWidth
                        currentHeight = defaultHeight
                    }
                    setLayoutParamsView()
                    loadImageWebp(resultValue)
                }

                GlideUtils.loadCircleImage(
                    context,
                    url,
                    content,
                    placeholderId
                )
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun loadImageWebp(resultValue: String?) {
        if (currentWidth <= AdaptScreenUtils.pt2Px(80f) && RomUtils.isOppo()) {
            GlideUtils.loadImageWebP(context, resultValue,0, bg,3,null)
        } else {
            GlideUtils.loadImageWebP(context, resultValue, bg)
        }
    }

    private fun reset() {
        content.setImageDrawable(null)
        bg.setImageDrawable(null)
    }

}