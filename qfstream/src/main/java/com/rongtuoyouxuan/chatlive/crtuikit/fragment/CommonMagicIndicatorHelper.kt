package com.rongtuoyouxuan.chatlive.crtuikit.fragment

import android.content.Context
import android.util.TypedValue
import android.view.View
import androidx.viewpager.widget.ViewPager
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.CommonNavigator
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.abs.CommonNavigatorAdapter
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.abs.IPagerIndicator
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.abs.IPagerTitleView
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.indicators.LinePagerIndicator1
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.commonnavigator.titles.SimplePagerTitleView
import com.rongtuoyouxuan.chatlive.stream.R

/**
 *  magicindicator viewpager fragment 使用的辅助工具类
 */
class CommonMagicIndicatorHelper private constructor(
    context: Context,
    private var list: List<CommonFragmentInfo>,
    private var viewPager: ViewPager,
    private var magicIndicator: com.rongtuoyouxuan.chatlive.crtmagicindicator.MagicIndicator,
    private var pageTitleViewFactory: IPagerTitleViewFactory,
    private var linePagerIndicatorFactory: ILinePagerIndicatorFactory
) {

    private val commonNavigator: CommonNavigator = CommonNavigator(context)

    class Builder constructor(val context: Context, val list: List<CommonFragmentInfo>, val viewPager: ViewPager, val magicIndicator: com.rongtuoyouxuan.chatlive.crtmagicindicator.MagicIndicator) {

        private var pageTitleViewFactory: IPagerTitleViewFactory = DefaultPagerTitleViewFactory()
        private var linePagerIndicatorFactory: ILinePagerIndicatorFactory = DefaultLinePagerIndicatorFactory()

        fun pageTitleViewFactory(pageTitleViewFactory: IPagerTitleViewFactory): Builder {
            this.pageTitleViewFactory = pageTitleViewFactory
            return this
        }

        fun linePagerIndicatorFactory(linePagerIndicatorFactory: ILinePagerIndicatorFactory): Builder {
            this.linePagerIndicatorFactory = linePagerIndicatorFactory
            return this
        }

        fun build(): CommonMagicIndicatorHelper {
            return CommonMagicIndicatorHelper(
                context,
                list,
                viewPager,
                magicIndicator,
                pageTitleViewFactory,
                linePagerIndicatorFactory
            )
        }
    }

    fun show(isAdjustMode:Boolean) {
        commonNavigator.adapter = object : CommonNavigatorAdapter() {
            override fun getCount(): Int {
                return list.size
            }

            override fun getTitleView(context: Context, index: Int): IPagerTitleView? {
                val iPagerTitleView = pageTitleViewFactory.createIPagerTitleView(context, list[index])
                (iPagerTitleView as View).setOnClickListener {
                    viewPager.currentItem = index
                }
                return iPagerTitleView
            }

            override fun getIndicator(context: Context): IPagerIndicator? {
                return linePagerIndicatorFactory.createLinePagerIndicator(context)
                return null
            }
        }
        commonNavigator.isAdjustMode = isAdjustMode
        magicIndicator.navigator = commonNavigator
        com.rongtuoyouxuan.chatlive.crtmagicindicator.ViewPagerHelper.bind(magicIndicator, viewPager)
    }

    /**
     * @return  CommonNavigator
     * 可以使用此对象设置相应的属性
     */
    fun getCommonNavigator(): CommonNavigator {
        return commonNavigator
    }

    interface IPagerTitleViewFactory {
        fun createIPagerTitleView(context: Context, fragmentInfo: CommonFragmentInfo): IPagerTitleView
    }

    class DefaultPagerTitleViewFactory : IPagerTitleViewFactory {
        override fun createIPagerTitleView(context: Context, fragmentInfo: CommonFragmentInfo): IPagerTitleView {
            val simplePagerTitleView: SimplePagerTitleView = CkSimplePagerTitleView(context)
            simplePagerTitleView.text = fragmentInfo.title
            simplePagerTitleView.normalColor = context.resources.getColor(R.color.color_magic_indicator_title_normal)
            simplePagerTitleView.setTextSize(TypedValue.COMPLEX_UNIT_SP,16f)
            simplePagerTitleView.setPadding(15,0,15,10)
            simplePagerTitleView.selectedColor = context.resources.getColor(R.color.color_magic_indicator_title_select)
            return simplePagerTitleView
        }
    }

    interface ILinePagerIndicatorFactory {
        fun createLinePagerIndicator(context: Context): IPagerIndicator
    }

    class DefaultLinePagerIndicatorFactory : ILinePagerIndicatorFactory {
        override fun createLinePagerIndicator(context: Context): IPagerIndicator {
            val indicator =
                LinePagerIndicator1(
                    context)
            indicator.mode = LinePagerIndicator1.MODE_EXACTLY
            indicator.lineHeight = com.rongtuoyouxuan.chatlive.crtutil.util.DisplayUtil.dipToPixels(context, 4.0f).toFloat()
            indicator.lineWidth = com.rongtuoyouxuan.chatlive.crtutil.util.DisplayUtil.dipToPixels(context, 15.0f).toFloat()
            indicator.roundRadius = com.rongtuoyouxuan.chatlive.crtutil.util.DisplayUtil.dipToPixels(context, 5f).toFloat()
            indicator.setColors(context.resources.getColor(R.color.qf_libutils_color_indicator_end))
            return indicator
        }
    }
}