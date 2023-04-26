package com.rongtuoyouxuan.chatlive.stream.view.beauty.model

import android.content.res.ColorStateList
import androidx.core.content.ContextCompat
import com.blankj.utilcode.util.AppUtils
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.util.EnvUtils

/**
 * 底部弹窗的菜单元素
 */
data class BottomMenuItem(
    val bottomMenuItemType: MenuItemType.BottomMenuItemType,
    val text: String,
    val iconId: Int,
    var offsetValue: Int = 0,
    var maxValue: Int = 100,
    var showProcess: Boolean = false,
    var selected: Boolean = false,
    var isCircle: Boolean = true,
    var sticker: Sticker? = null,
    var resourcePath: ResourcePath? = null,
    var clickListener: (() -> Unit)? = null,

) {
    var textColor: ColorStateList?

    init {
        val unselectedColor =
            ContextCompat.getColorStateList(DataBus.instance().appContext, R.color.text_color)
        textColor = unselectedColor
    }
}

/**
 * 菜单类型
 */
open class MenuItemType {
    /**
     * 右侧菜单
     */
    open class RightMenuItemType : MenuItemType() {
        // 相机翻转
        object FlipCamera : RightMenuItemType()

        // 滤镜
        object Filter : RightMenuItemType()

        // 美颜
        object Beautify : RightMenuItemType()

        // 特效
        object MagicEffect : RightMenuItemType()

        // 背景
        object Background : RightMenuItemType();

        // 设置
        object Setting : RightMenuItemType()

        object Unselected : RightMenuItemType()
    }

    interface Reset
    interface UnSelect
    interface Makeup
    interface Filter
    /**
     * 底部菜单
     */
    open class BottomMenuItemType : MenuItemType() {
        // 美肤
        open class BeautifySkin : BottomMenuItemType() {
            object Unselected : BeautifySkin()//未选择
            object Reset : BeautifySkin()//重置
            object PolishSkin : BeautifySkin()//磨皮
            object WhitenSkin : BeautifySkin()//美白
            object Rosy : BeautifySkin()//红润
            object Sharpen : BeautifySkin()//锐化
            object WrinklesRemoving : BeautifySkin()//去法令纹
            object DarkCirclesRemoving : BeautifySkin()//去黑眼圈
        }

        // 美型
        open class BeautifyBody : BottomMenuItemType() {
            object Unselected : BeautifyBody()//未选择
            object Reset : BeautifyBody()//重置
            object ThinFace : BeautifyBody()//瘦脸
            object BigEye : BeautifyBody()//大眼
            object BrightEye : BeautifyBody()//亮眼
            object Chin : BeautifyBody()//收下巴
            object SmallMouth : BeautifyBody()//小嘴
            object WhiteTeeth : BeautifyBody()//白牙
            object NoseNarrowing : BeautifyBody()//瘦鼻
            object NoseLengthening : BeautifyBody()//长鼻
            object FaceShortening : BeautifyBody()//小脸
            object MandibleSlimming : BeautifyBody()//瘦下颌骨
            object CheekboneSlimming : BeautifyBody()//瘦颧骨
            object ForeheadShortening : BeautifyBody()//缩额头
        }

        // 美妆
        open class BeautifyMakeup : BottomMenuItemType() {
            object Unselected : BeautifyMakeup()//未选择
            object Reset : BeautifyMakeup()//重置
            object Lipstick : BeautifyMakeup()//口红
            object Blusher : BeautifyMakeup()//腮红
            object Eyelashes : BeautifyMakeup()//眼睫毛
            object Eyeliner : BeautifyMakeup()//眼线
            object Eyeshadow : BeautifyMakeup()//眼影
            object EyesColored : BeautifyMakeup()//美瞳
        }

        // 风格妆
        open class BeautifyStyle : BottomMenuItemType() {
            object Unselected : BeautifyStyle()//未选择
            object NoEffect : BeautifyStyle()//原图
            object EyelidDownToMakeup : BeautifyStyle()//眼睑下至妆
            object Look : BeautifyStyle()//神颜
            object MilkKiller : BeautifyStyle()//奶凶
            object PureDesire : BeautifyStyle()//纯欲
            object Youth : BeautifyStyle()//银河眼妆
        }
        //一键美颜
        open class BeautifyAKey : BottomMenuItemType() {
            object Unselected : BeautifyAKey()//未选择
            object Reset : BeautifyAKey()//重置
            object Douyin : BeautifyAKey()//抖音风
            object B : BeautifyAKey()//b612风
            object Bli : BeautifyAKey()//B站风
            object MeiYan : BeautifyAKey()//美颜相机风
            object Qingyan : BeautifyAKey()//轻颜相机风
        }

        //背景
        open class Background : BottomMenuItemType(){
            object Unselected : Background()//未选择
            object NoEffect : Background()//原图
            object GreenScreenSplit : Background()//绿幕分割
            object PersonImageSplit : Background()//人像分割
            object Mosaic : Background()//马赛克
            object GaussianBlur : Background()//高斯模糊
        }

        // 特效
        open class TDMagicEffect : BottomMenuItemType() {
            object Unselected : TDMagicEffect()//未选择
            object Sticker : TDMagicEffect()//贴纸
        }

        // 马赛克形状
        open class MosaicShape : BottomMenuItemType() {
            object Square : MosaicShape()//四边形
            object Triangle : MosaicShape()//三角形
            object Hexagon : MosaicShape()//六边形
        }

        //口红
        open class LipstickType : BottomMenuItemType(),Makeup{
            object Unselected : LipstickType(),UnSelect//未选择
            object NoEffect : LipstickType(),Reset//原图
        }

        //腮红
        open class BlusherType: BottomMenuItemType(),Makeup{
            object Unselected : BlusherType(),UnSelect//未选择
            object NoEffect : BlusherType(),Reset//原图
        }

        //眼睫毛
        open class EyelashesType: BottomMenuItemType(),Makeup{
            object Unselected : EyelashesType(),UnSelect//未选择
            object NoEffect : EyelashesType(),Reset//原图
        }

        //眼线
        open class EyelinerType: BottomMenuItemType(),Makeup{
            object Unselected : EyelinerType(),UnSelect//未选择
            object NoEffect : EyelinerType(),Reset//原图
        }

        //眼影
        open class EyeshadowType: BottomMenuItemType(),Makeup{
            object Unselected : EyeshadowType(),UnSelect//未选择
            object NoEffect : EyeshadowType(),Reset//原图
        }

        //美瞳
        open class EyesColoredType: BottomMenuItemType(),Makeup{
            object Unselected : EyesColoredType(),UnSelect//未选择
            object NoEffect : EyesColoredType(),Reset//原图
        }

        //自然
        open class FilterNature: BottomMenuItemType(){
            object Unselected : FilterNature(),UnSelect,Filter//未选择
            object NoEffect : FilterNature(),Reset,Filter//原图
            object Cream : FilterNature(),Filter//奶油
            object Youth : FilterNature(),Filter//青春
            object Fresh : FilterNature(),Filter//清新
            object Akita : FilterNature(),Filter//秋田
        }

        //灰调
        open class FilterGray: BottomMenuItemType(){
            object Unselected : FilterGray(),UnSelect,Filter//未选择
            object NoEffect : FilterGray(),Reset,Filter//原图
            object Monet : FilterGray(),Filter//莫奈
            object Night : FilterGray(),Filter//暗夜
            object Film : FilterGray(),Filter//胶片
        }

        //梦境
        open class FilterDream: BottomMenuItemType(){
            object Unselected : FilterDream(),UnSelect,Filter//未选择
            object NoEffect : FilterGray(),Reset,Filter//原图
            object Sunset : FilterDream(),Filter//落日
            object Glaze : FilterDream(),Filter//琉璃
            object Nebula : FilterDream(),Filter//星云
        }
    }
}



