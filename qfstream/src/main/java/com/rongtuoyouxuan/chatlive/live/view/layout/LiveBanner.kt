package com.rongtuoyouxuan.chatlive.live.view.layout

import android.content.Context
import android.net.Uri
import android.text.TextUtils
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.LifecycleOwner
import com.rongtuoyouxuan.chatlive.base.utils.ViewModelUtils
import com.rongtuoyouxuan.chatlive.base.viewmodel.IMLiveViewModel
import com.rongtuoyouxuan.chatlive.biz2.model.stream.AdsBean
import com.rongtuoyouxuan.chatlive.router.Router
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.activity.StreamActivity
import com.rongtuoyouxuan.chatlive.webview.WebViewWrapper
import com.rongtuoyouxuan.libuikit.widget.banner.BannerView
import com.rongtuoyouxuan.libuikit.widget.banner.holder.BannerHolderCreator
import com.rongtuoyouxuan.libuikit.widget.banner.holder.Holder
import com.bumptech.glide.Glide
import com.bumptech.glide.request.RequestOptions
import kotlinx.android.synthetic.main.qf_stream_view_banner.view.*
import java.net.URLDecoder

class LiveBanner : BannerView<AdsBean?> {
    var bannerHolderCreator: BannerHolderCreator<*> =
        BannerHolderCreator<Any?> { LiveBannerHolder() }
    private var mIMLiveViewModel: IMLiveViewModel? = null
    private var activityList: List<AdsBean>? = null

    @JvmOverloads
    constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0) : super(
        context,
        attrs,
        defStyleAttr
    ) {
        initViewModel(context)
        initData(context)
    }

    override fun getResLayout(): Int {
        return R.layout.qf_stream_view_banner
    }

    private fun initData(context: Context) {
        mIMLiveViewModel?.adsLiveEvent?.observe((context as LifecycleOwner)) { adsBeans ->
            if (adsBeans?.isNotEmpty() == true) {
                activityList = adsBeans
                isCanLoop = adsBeans.size > 1
                setPages(bannerHolderCreator, adsBeans)
                setPoint(
                    R.drawable.shape_item_banner_oval_white,
                    R.drawable.shape_item_banner_oval
                )
                if (adsBeans.size == 1) {
                    ll_point?.visibility = INVISIBLE
                } else {
                    ll_point?.visibility = VISIBLE
                }
                start(2500)
            }
        }
        setOnItemClickListener { position ->
            if (activityList != null && activityList!!.isNotEmpty()) {
                var requesturl = Uri.encode(activityList!![position].scheme, "/:?&=");
                var uri = Uri.parse(requesturl)
                var scheme = uri.scheme
//                var host = uri.host
                var type = uri.getQueryParameter("type")
                var url = URLDecoder.decode(uri.getQueryParameter("arg"), "UTF-8")
                var gameId = uri.getQueryParameter("id")
                var gameVerson = uri.getQueryParameter("version")
                if (scheme != "rt") return@setOnItemClickListener
                if (activityList!![position].target == 0) {
                    if (type == "1") {//跳转直播间游戏链接
                        gameId?.toInt()?.let {
                            if (gameVerson != null) {
                                if (url != null) {
                                }
                            }
                        }
                    } else {
                        val list = requesturl.split("arg=")
                        if (list.size > 1) {
                            val newUrl = URLDecoder.decode(list[1], "UTF-8")
                            if (!TextUtils.isEmpty(newUrl)) {
                                Router.toWebDialogActivity(context, newUrl)
                            }
                        }
                    }
                } else {
                    val list = requesturl.split("arg=")
                    if (list.size > 1) {
                        val newUrl = URLDecoder.decode(list[1], "UTF-8")
                        if (!TextUtils.isEmpty(newUrl)) {
                            Router.toWebDialogActivity(context, newUrl)
                        }
                    }
                }
            }
        }
    }
    private fun initViewModel(context: Context) {
        if (getContext() as FragmentActivity is StreamActivity) {
            mIMLiveViewModel = ViewModelUtils.get(
                getContext() as FragmentActivity,
                IMLiveViewModel::class.java
            )
        } else {
            mIMLiveViewModel = ViewModelUtils.getLive(IMLiveViewModel::class.java)
        }
    }

    inner class LiveBannerHolder : Holder<AdsBean?> {
        private var imageView: ImageView? = null
        private var webViewWrapper: WebViewWrapper? = null
        override fun createView(context: Context): View {
            var convertView = LayoutInflater.from(context).inflate(R.layout.qf_stream_layout_live_banner, null)
//            if (imageView == null) {
//                imageView = ImageView(context)
//                val layoutParams = ViewGroup.LayoutParams(
//                    ViewGroup.LayoutParams.MATCH_PARENT,
//                    ViewGroup.LayoutParams.MATCH_PARENT
//                )
//                imageView!!.layoutParams = layoutParams
//            }
            imageView = convertView.findViewById(R.id.liveBannerImg)
            webViewWrapper = convertView.findViewById(R.id.liveBannerWebView)
            return convertView!!
        }

        override fun convert(context: Context?, position: Int, item: AdsBean?) {
            if (item == null) {
                return
            }
            if (context != null) {
                if(!TextUtils.isEmpty(item.widget_url)){
                    webViewWrapper?.visibility = View.VISIBLE
                    imageView?.visibility = View.GONE
                    webViewWrapper?.loadurl(item.widget_url)
                }else{
                    webViewWrapper?.visibility = View.GONE
                    imageView?.visibility = View.VISIBLE
                    Glide.with(context).load(item.pic).apply(
                        RequestOptions()
                            .dontAnimate()
                            .placeholder(R.drawable.icon_default_dynamic)
                            .error(R.drawable.icon_default_dynamic))
                        .into(imageView!!)
                }

            }
        }
    }
}