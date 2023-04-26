package com.rongtuoyouxuan.chatlive.stream.view.beauty.util

import android.view.MotionEvent
import android.view.View
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.beauty.TestHandler
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.*
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.MenuItemType.BottomMenuItemType

/**
 * Effects的配置以及工具类
 */


val filterNatureList = listOf(
        BottomMenuItem(
                MenuItemType.BottomMenuItemType.FilterNature.NoEffect,
                "原图",
                R.mipmap.icon_no_effect,
                0,
                100,
                false,
                resourcePath = ResourcePath(1000, "", "","原图","")
        ),
        BottomMenuItem(
                MenuItemType.BottomMenuItemType.FilterNature.Cream,
                "奶油",
                R.mipmap.icon_nature_cream,
                0,
                100,
                true,
                resourcePath = ResourcePath(1101, "Resources/ColorfulStyleResources/Creamy.bundle", "","奶油","")
        ),
        BottomMenuItem(
                MenuItemType.BottomMenuItemType.FilterNature.Youth,
                "青春",
                R.mipmap.icon_nature_youth,
                0,
                100,
                true,
                resourcePath = ResourcePath(1102, "Resources/ColorfulStyleResources/Brighten.bundle", "","青春","")
        ),
        BottomMenuItem(
                MenuItemType.BottomMenuItemType.FilterNature.Fresh,
                "清新",
                R.mipmap.icon_nature_fresh,
                0,
                100,
                true,
                resourcePath = ResourcePath(1103, "Resources/ColorfulStyleResources/Fresh.bundle", "","清新","")
        ),
        BottomMenuItem(
                MenuItemType.BottomMenuItemType.FilterNature.Akita,
                "秋田",
                R.mipmap.icon_nature_akita,
                0,
                100,
                true,
                resourcePath = ResourcePath(1104, "Resources/ColorfulStyleResources/Autumn.bundle", "","秋田","")
        )
)

val filterGrayList = listOf(
        BottomMenuItem(
                MenuItemType.BottomMenuItemType.FilterGray.NoEffect,
                "原图",
                R.mipmap.icon_no_effect,
                0,
                100,
                false,
                resourcePath = ResourcePath(1000, "", "","原图","")
        ),
        BottomMenuItem(
                MenuItemType.BottomMenuItemType.FilterGray.Monet,
                "莫奈",
                R.mipmap.icon_gray_monet,
                0,
                100,
                true,
                resourcePath = ResourcePath(1201, "Resources/ColorfulStyleResources/Cool.bundle", "","莫奈","")
        ),
        BottomMenuItem(
                BottomMenuItemType.FilterGray.Night,
                "暗夜",
                R.mipmap.icon_gray_night,
                0,
                100,
                true,
                resourcePath = ResourcePath(1202, "Resources/ColorfulStyleResources/Night.bundle", "","暗夜","")
        ),
        BottomMenuItem(
                BottomMenuItemType.FilterGray.Film,
                "胶片",
                R.mipmap.icon_gray_film,
                0,
                100,
                true,
                resourcePath = ResourcePath(1203, "Resources/ColorfulStyleResources/Film-like.bundle", "","胶片","")
        ),
)

val filterDreamList = listOf(
        BottomMenuItem(
                BottomMenuItemType.FilterDream.NoEffect,
                "原图",
                R.mipmap.icon_no_effect,
                0,
                100,
                false,
                resourcePath = ResourcePath(1000, "", "","原图","")
        ),
        BottomMenuItem(
                BottomMenuItemType.FilterDream.Sunset,
                "落日",
                R.mipmap.icon_dream_sunset,
                0,
                100,
                true,
                resourcePath = ResourcePath(1301, "Resources/ColorfulStyleResources/Sunset.bundle", "","落日","")
        ),
        BottomMenuItem(
                BottomMenuItemType.FilterDream.Glaze,
                "琉璃",
                R.mipmap.icon_dream_glaze,
                0,
                100,
                true,
                resourcePath = ResourcePath(1302, "Resources/ColorfulStyleResources/Cozily.bundle", "","琉璃","")
        ),
        BottomMenuItem(
                BottomMenuItemType.FilterDream.Nebula,
                "星云",
                R.mipmap.icon_dream_nebula,
                0,
                100,
                true,
                resourcePath = ResourcePath(1303, "Resources/ColorfulStyleResources/Sweet.bundle", "","星云","")
        ),
)

val beautifySkinList = listOf(
        BottomMenuItem(
                BottomMenuItemType.BeautifySkin.Reset,
                "重置",
                R.mipmap.icon_reset,
                0,
                100,
                false
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifySkin.PolishSkin,
                "磨皮",
                R.mipmap.icon_mopi,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifySkin.WhitenSkin,
                "美白",
                R.mipmap.icon_white,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifySkin.Rosy,
                "红润",
                R.mipmap.icon_hongrun,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifySkin.Sharpen,
                "锐化",
                R.mipmap.icon_ruihua,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifySkin.WrinklesRemoving,
                "去法令纹",
                R.mipmap.icon_wrinkles_removing,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifySkin.DarkCirclesRemoving,
                "去黑眼圈",
                R.mipmap.icon_dark_circles_removing,
                0,
                100,
                true
        )
)

val beautifyBodyList = listOf(
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.Reset,
                "重置",
                R.mipmap.icon_reset,
                0,
                100,
                false
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.ThinFace,
                "瘦脸",
                R.mipmap.icon_face,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.BigEye,
                "大眼",
                R.mipmap.icon_eye,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.BrightEye,
                "亮眼",
                R.mipmap.icon_liangyan,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.Chin,
                "收下巴",
                R.mipmap.icon_shouxiaba,
                -100,
                200,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.SmallMouth,
                "小嘴",
                R.mipmap.icon_mouth,
                -100,
                200,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.WhiteTeeth,
                "白牙",
                R.mipmap.icon_teeth,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.NoseNarrowing,
                "瘦鼻",
                R.mipmap.icon_nose,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.NoseLengthening,
                "长鼻",
                R.mipmap.icon_nose_lengthening,
                -100,
                200,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.FaceShortening,
                "小脸",
                R.mipmap.icon_face_shortening,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.MandibleSlimming,
                "瘦下颌骨",
                R.mipmap.icon_mandible_slimming,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.CheekboneSlimming,
                "瘦颧骨",
                R.mipmap.icon_cheekbone_slimming,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyBody.ForeheadShortening,
                "收额头",
                R.mipmap.icon_forehead_slimming,
                -100,
                200,
                true
        )
)

val backgroundEffectList = listOf(
        BottomMenuItem(
                BottomMenuItemType.Background.NoEffect,
                "原图",
                R.mipmap.icon_no_effect,
                0,
                100,
                false,
                isCircle = false,
        ),
        BottomMenuItem(
                BottomMenuItemType.Background.GreenScreenSplit,
                "绿幕分割",
                R.mipmap.blue_screen_slice,
                0,
                100,
                true,
                isCircle = false
        ),
        BottomMenuItem(
                BottomMenuItemType.Background.PersonImageSplit,
                "人像分割",
                R.mipmap.person_image_slice,
                0,
                100,
                false,
                isCircle = false
        ),
        BottomMenuItem(
                BottomMenuItemType.Background.Mosaic,
                "马赛克",
                R.mipmap.icon_mosaic,
                0,
                100,
                true,
                isCircle = false
        ),
        BottomMenuItem(
                BottomMenuItemType.Background.GaussianBlur,
                "高斯模糊",
                R.mipmap.icon_gaussian_blur,
                0,
                100,
                true,
                isCircle = false
        )
)

val beautifyStyleList = listOf(
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.NoEffect,
                "原图",
                R.mipmap.icon_no_effect,
                0,
                100,
                false,
                isCircle = false,
                resourcePath = ResourcePath(
                        1504,
                        "",
                        "",
                        "原图",
                        ""
                )
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.EyelidDownToMakeup,
                "眼睑下至妆",
                R.mipmap.icon_beautify_style_eyelid_down_to_makeup,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1505,
                        "Resources/MakeupResources/makeupdir/makeupdir_vulnerable_and_innocenteyes.bundle/",
                        "",
                        "眼睑下至妆",
                        ""
                )

        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.Youth,
                "银河眼妆",
                R.mipmap.icon_beautify_style_youth,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1506,
                        "Resources/MakeupResources/makeupdir/makeupdir_milky_eyes.bundle/",
                        "",
                        "银河眼妆",
                        ""
                )

        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.MilkKiller,
                "奶凶",
                R.mipmap.icon_beautify_style_milk_killer,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1507,
                        "Resources/MakeupResources/makeupdir/makeupdir_cutie_and_cool.bundle/",
                        "",
                        "奶凶",
                        ""
                )

        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.PureDesire,
                "纯欲",
                R.mipmap.icon_beautify_style_pure_desire,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1508,
                        "Resources/MakeupResources/makeupdir/makeupdir_pure_and_sexy.bundle/",
                        "",
                        "纯欲",
                        ""
                )

        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.Look,
                "神颜",
                R.mipmap.icon_beautify_style_look,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1509,
                        "Resources/MakeupResources/makeupdir/makeupdir_flawless.bundle/",
                        "",
                        "神颜",
                        ""
                )
        ),
)

val beautifyAKeyList = listOf(
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.NoEffect,
                "抖音风",
                R.mipmap.icon_beautify_style_look,
                0,
                100,
                false,
                isCircle = false,
                resourcePath = ResourcePath(
                        1601,
                        "",
                        "",
                        "抖音风",
                        ""
                )
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.Look,
                "B612风",
                R.mipmap.icon_beautify_style_look,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1602,
                        "Resources/FilterType_Beautiful_Makeup/makeupdir/makeupdir_flawless.bundle/",
                        "",
                        "B612风",
                        ""
                )
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.Look,
                "美颜相机风",
                R.mipmap.icon_beautify_style_look,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1603,
                        "Resources/FilterType_Beautiful_Makeup/makeupdir/makeupdir_flawless.bundle/",
                        "",
                        "美颜相机风",
                        ""
                )
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.Look,
                "B站风",
                R.mipmap.icon_beautify_style_look,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1604,
                        "Resources/FilterType_Beautiful_Makeup/makeupdir/makeupdir_flawless.bundle/",
                        "",
                        "B站风",
                        ""
                )
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyStyle.Look,
                "轻颜风",
                R.mipmap.icon_beautify_style_look,
                0,
                100,
                true,
                isCircle = false,
                resourcePath = ResourcePath(
                        1605,
                        "Resources/FilterType_Beautiful_Makeup/makeupdir/makeupdir_flawless.bundle/",
                        "",
                        "轻颜风",
                        ""
                )
        ),
)

val beautifyMakeupList = listOf(
        BottomMenuItem(
                BottomMenuItemType.BeautifyMakeup.Reset,
                "重置",
                R.mipmap.icon_reset,
                0,
                100,
                false
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyMakeup.Lipstick,
                "口红",
                R.mipmap.icon_lipstick,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyMakeup.Blusher,
                "腮红",
                R.mipmap.icon_blusher,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyMakeup.Eyelashes,
                "眼睫毛",
                R.mipmap.icon_eyelashes,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyMakeup.Eyeliner,
                "眼线",
                R.mipmap.icon_eyeliner,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyMakeup.Eyeshadow,
                "眼影",
                R.mipmap.icon_eyeshadow,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.BeautifyMakeup.EyesColored,
                "美瞳",
                R.mipmap.icon_beautify_eyes,
                0,
                100,
                true
        )
)

val magicEffectList = listOf(
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.icon_no_effect,
                sticker = Sticker(0, "", "","原图","")
        ),
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.pic_thereeanmials,
                sticker = Sticker(1, "Pendants/pendantAnimal.bundle", "","三只动物","点头切换动物")
        ),
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.qianshui,
                sticker = Sticker(2, "Pendants/pendantDive.bundle", "","潜水镜","")
        ),
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.pic_maotou,
                sticker = Sticker(3 ,"Pendants/pendantCat.bundle", "","猫头","")
        ),
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.pic_watermelon,
                sticker = Sticker(4,"Pendants/pendantWatermelon.bundle", "", "西瓜","张嘴开始吃瓜")
        ),
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.pic_fawn,
                sticker = Sticker(5, "Pendants/pendantDeer.bundle","", "小鹿","")
        ),
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.pic_lianmo,
                sticker = Sticker(6, "Pendants/pendantFacefilm.bundle", "","炫酷脸膜","")
        ),
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.pic_xiaochou,
                sticker = Sticker(7,"Pendants/pendantClown.bundle", "","小丑","摇头试试～")
        ),
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.pic_wawaji,
                sticker = Sticker(8, "Pendants/pendantBaby.bundle","", "娃娃机","")
        )
        ,
        BottomMenuItem(
                BottomMenuItemType.TDMagicEffect.Sticker,
                "",
                iconId = R.mipmap.pic_meishaoniu,
                sticker = Sticker(9, "Pendants/pendantGirl.bundle", "","美少女战士","张嘴开启变身")
        )

)

val mosaicShapeList = listOf(
        BottomMenuItem(
                BottomMenuItemType.MosaicShape.Triangle,
                "三角形",
                R.mipmap.icon_triangle,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.MosaicShape.Square,
                "四边形",
                R.mipmap.icon_square,
                0,
                100,
                true
        ),
        BottomMenuItem(
                BottomMenuItemType.MosaicShape.Hexagon,
                "六边形",
                R.mipmap.icon_hexagon,
                0,
                100,
                true
        )
)

val mosaicMenuList = listOf(
        BottomSecondMenu("马赛克形状",BottomMenuItemType.MosaicShape.Square,false)
)
val lipstickMenuList = listOf(
        BottomSecondMenu("口红",BottomMenuItemType.LipstickType.Unselected,false)
)

val blusherMenuList = listOf(
        BottomSecondMenu("腮红",BottomMenuItemType.BlusherType.Unselected,false)
)

val eyelashesMenuList = listOf(
        BottomSecondMenu("眼睫毛",BottomMenuItemType.EyelashesType.Unselected,false)
)

val eyelinerMenuList = listOf(
        BottomSecondMenu("眼线",BottomMenuItemType.EyelinerType.Unselected,false)
)

val eyeShadowMenuList = listOf(
        BottomSecondMenu("眼影",BottomMenuItemType.EyeshadowType.Unselected,false)
)

val eyesColoredMenuList = listOf(
        BottomSecondMenu("美瞳",BottomMenuItemType.EyesColoredType.Unselected,false)
)

val lipStickTypeList = getLipStickTypeLists()


fun getLipStickTypeLists(): MutableList<BottomMenuItemType.LipstickType> {
        val typeList = mutableListOf<BottomMenuItemType.LipstickType>()
        for(i in 1..5) {
                typeList.add(object : BottomMenuItemType.LipstickType(){})
        }
        return typeList
}

val lipStickList = listOf(
                BottomMenuItem(
                        BottomMenuItemType.LipstickType.NoEffect,
                        "原图",
                        R.mipmap.icon_original,
                        0,
                        100,
                        false,
                        resourcePath = ResourcePath(100, "", "","原图","")
                ),
                BottomMenuItem(
                        lipStickTypeList[0],
                        "豆沙粉",
                        R.mipmap.icon_lipstick_bean_pink,
                        0,
                        100,
                        true,
                        resourcePath = ResourcePath(101, "Resources/MakeupResources/lipstickdir/lipstickdir_bean_paste_pink.bundle/", "","豆沙粉","")
                ),
                BottomMenuItem(
                        lipStickTypeList[1],
                        "甜橘色",
                        R.mipmap.icon_lipstick_sweet_orange,
                        0,
                        100,
                        true,
                        resourcePath = ResourcePath(102, "Resources/MakeupResources/lipstickdir/lipstickdir_sweet_orange.bundle/", "","甜橘色","")
                ),
                BottomMenuItem(
                        lipStickTypeList[2],
                        "铁锈红",
                        R.mipmap.icon_lipstick_rusty_red,
                        0,
                        100,
                        true,
                        resourcePath = ResourcePath(103, "Resources/MakeupResources/lipstickdir/lipstickdir_rust_red.bundle/", "","铁锈红","")
                ),
                BottomMenuItem(
                        lipStickTypeList[3],
                        "珊瑚色",
                        R.mipmap.icon_lipstick_coral,
                        0,
                        100,
                        true,
                        resourcePath = ResourcePath(104, "Resources/MakeupResources/lipstickdir/lipstickdir_coral.bundle/", "","珊瑚色","")
                ),
                BottomMenuItem(
                        lipStickTypeList[4],
                        "丝绒红",
                        R.mipmap.icon_lipstick_velvet_red,
                        0,
                        100,
                        true,
                        resourcePath = ResourcePath(105, "Resources/MakeupResources/lipstickdir/lipstickdir_velvet_red.bundle/", "","丝绒红","")
                )
        )


val blusherTypeList = getBlusherTypeLists()


fun getBlusherTypeLists(): MutableList<BottomMenuItemType.BlusherType> {
        val typeList = mutableListOf<BottomMenuItemType.BlusherType>()
        for(i in 1..5) {
                typeList.add(object : BottomMenuItemType.BlusherType(){})
        }
        return typeList
}

val blusherList = listOf(
        BottomMenuItem(
                BottomMenuItemType.BlusherType.NoEffect,
                "原图",
                R.mipmap.icon_original,
                0,
                100,
                false,
                resourcePath = ResourcePath(200, "", "","原图","")
        ),
        BottomMenuItem(
                blusherTypeList[0],
                "微醺",
                R.mipmap.icon_blush_drunk,
                0,
                100,
                true,
                resourcePath = ResourcePath(201, "Resources/MakeupResources/blusherdir/blusherdir_slightly_drunk.bundle/", "","微醺","")
        ),
        BottomMenuItem(
                blusherTypeList[1],
                "蜜桃",
                R.mipmap.icon_blush_peach,
                0,
                100,
                true,
                resourcePath = ResourcePath(202, "Resources/MakeupResources/blusherdir/blusherdir_peach.bundle/", "","蜜桃","")
        ),
        BottomMenuItem(
                blusherTypeList[2],
                "奶橘",
                R.mipmap.icon_blush_milk_orange,
                0,
                100,
                true,
                resourcePath = ResourcePath(203, "Resources/MakeupResources/blusherdir/blusherdir_milk_orange.bundle/", "","奶橘","")
        ),
        BottomMenuItem(
                blusherTypeList[3],
                "杏粉",
                R.mipmap.icon_blush_apricot_pink,
                0,
                100,
                true,
                resourcePath = ResourcePath(204, "Resources/MakeupResources/blusherdir/blusherdir_apricot_pink.bundle/", "","杏粉","")
        ),
        BottomMenuItem(
                blusherTypeList[4],
                "甜橙",
                R.mipmap.icon_blush_sweet_orange,
                0,
                100,
                true,
                resourcePath = ResourcePath(205, "Resources/MakeupResources/blusherdir/blusherdir_sweet_orange.bundle/", "","甜橙","")
        )
)

val eyelashesTypeList = getEyelashesTypeLists()


fun getEyelashesTypeLists(): MutableList<BottomMenuItemType.EyelashesType> {
        val typeList = mutableListOf<BottomMenuItemType.EyelashesType>()
        for(i in 1..5) {
                typeList.add(object : BottomMenuItemType.EyelashesType(){})
        }
        return typeList
}

val eyelashesList = listOf(
        BottomMenuItem(
                BottomMenuItemType.EyelashesType.NoEffect,
                "原图",
                R.mipmap.icon_original,
                0,
                100,
                false,
                resourcePath = ResourcePath(300, "", "","原图","")
        ),
        BottomMenuItem(
                eyelashesTypeList[0],
                "自然",
                R.mipmap.icon_eyelash_natural,
                0,
                100,
                true,
                resourcePath = ResourcePath(301, "Resources/MakeupResources/eyelashesdir/eyelashesdir_natural.bundle/", "","自然","")
        ),
        BottomMenuItem(
                eyelashesTypeList[1],
                "温柔",
                R.mipmap.icon_eyelash_gentle,
                0,
                100,
                true,
                resourcePath = ResourcePath(302, "Resources/MakeupResources/eyelashesdir/eyelashesdir_tender.bundle/", "","温柔","")
        ),
        BottomMenuItem(
                eyelashesTypeList[2],
                "卷翘",
                R.mipmap.icon_eyelash_curly,
                0,
                100,
                true,
                resourcePath = ResourcePath(303, "Resources/MakeupResources/eyelashesdir/eyelashesdir_curl.bundle/", "","卷翘","")
        ),
        BottomMenuItem(
                eyelashesTypeList[3],
                "纤长",
                R.mipmap.icon_eyelash_slender,
                0,
                100,
                true,
                resourcePath = ResourcePath(304, "Resources/MakeupResources/eyelashesdir/eyelashesdir_slender.bundle/", "","纤长","")
        ),
        BottomMenuItem(
                eyelashesTypeList[4],
                "浓密",
                R.mipmap.icon_eyelash_dense,
                0,
                100,
                true,
                resourcePath = ResourcePath(305, "Resources/MakeupResources/eyelashesdir/eyelashesdir_bushy.bundle/", "","浓密","")
        )
)


val eyelinerTypeList = getEyelinerTypeLists()

fun getEyelinerTypeLists(): MutableList<BottomMenuItemType.EyelinerType> {
        val typeList = mutableListOf<BottomMenuItemType.EyelinerType>()
        for(i in 1..5) {
                typeList.add(object : BottomMenuItemType.EyelinerType(){})
        }
        return typeList
}

val eyelinerList = listOf(
        BottomMenuItem(
                BottomMenuItemType.EyelinerType.NoEffect,
                "原图",
                R.mipmap.icon_original,
                0,
                100,
                false,
                resourcePath = ResourcePath(400, "", "","原图","")
        ),
        BottomMenuItem(
                eyelinerTypeList[0],
                "自然",
                R.mipmap.icon_eyeliner_natural,
                0,
                100,
                true,
                resourcePath = ResourcePath(401, "Resources/MakeupResources/eyelinerdir/eyelinerdir_natural.bundle/", "","自然","")
        ),
        BottomMenuItem(
                eyelinerTypeList[1],
                "野猫",
                R.mipmap.icon_eyeliner_wild_cat,
                0,
                100,
                true,
                resourcePath = ResourcePath(402, "Resources/MakeupResources/eyelinerdir/eyelinerdir_wildcat.bundle/", "","野猫","")
        ),
        BottomMenuItem(
                eyelinerTypeList[2],
                "俏皮",
                R.mipmap.icon_eyeliner_naughty,
                0,
                100,
                true,
                resourcePath = ResourcePath(403, "Resources/MakeupResources/eyelinerdir/eyelinerdir_pretty.bundle/", "","俏皮","")
        ),
        BottomMenuItem(
                eyelinerTypeList[3],
                "心机",
                R.mipmap.icon_eyeliner_scheming,
                0,
                100,
                true,
                resourcePath = ResourcePath(404, "Resources/MakeupResources/eyelinerdir/eyelinerdir_scheming.bundle/", "","心机","")
        ),
        BottomMenuItem(
                eyelinerTypeList[4],
                "气质",
                R.mipmap.icon_eyeliner_classy,
                0,
                100,
                true,
                resourcePath = ResourcePath(405, "Resources/MakeupResources/eyelinerdir/eyelinerdir_temperament.bundle/", "","气质","")
        )
)

val eyeshadowTypeList = getEyeshadowTypeLists()

fun getEyeshadowTypeLists(): MutableList<BottomMenuItemType.EyeshadowType> {
        val typeList = mutableListOf<BottomMenuItemType.EyeshadowType>()
        for(i in 1..5) {
                typeList.add(object : BottomMenuItemType.EyeshadowType(){})
        }
        return typeList
}

val eyeshadowList = listOf(
        BottomMenuItem(
                BottomMenuItemType.EyeshadowType.NoEffect,
                "原图",
                R.mipmap.icon_original,
                0,
                100,
                false,
                resourcePath = ResourcePath(500, "", "","原图","")
        ),
        BottomMenuItem(
                eyeshadowTypeList[0],
                "粉雾海",
                R.mipmap.icon_eyeshadow_misty_pink,
                0,
                100,
                true,
                resourcePath = ResourcePath(501, "Resources/MakeupResources/eyeshadowdir/eyeshadowdir_mist_pink.bundle/", "","粉雾海","")
        ),
        BottomMenuItem(
                eyeshadowTypeList[1],
                "微光蜜",
                R.mipmap.icon_eyeshadow_honey_pink,
                0,
                100,
                true,
                resourcePath = ResourcePath(502, "Resources/MakeupResources/eyeshadowdir/eyeshadowdir_shimmer_pink.bundle/", "","微光蜜","")
        ),
        BottomMenuItem(
                eyeshadowTypeList[2],
                "暖茶棕",
                R.mipmap.icon_eyeshadow_warm_brown,
                0,
                100,
                true,
                resourcePath = ResourcePath(503, "Resources/MakeupResources/eyeshadowdir/eyeshadowdir_tea_brown.bundle/", "","暖茶棕","")
        ),
        BottomMenuItem(
                eyeshadowTypeList[3],
                "元气橙",
                R.mipmap.icon_eyeshadow_vital_orange,
                0,
                100,
                true,
                resourcePath = ResourcePath(504, "Resources/MakeupResources/eyeshadowdir/eyeshadowdir_vitality_orange.bundle/", "","元气橙","")
        ),
        BottomMenuItem(
                eyeshadowTypeList[4],
                "摩卡棕",
                R.mipmap.icon_eyeshadow_brown,
                0,
                100,
                true,
                resourcePath = ResourcePath(505, "Resources/MakeupResources/eyeshadowdir/eyeshadowdir_mocha_brown.bundle/", "","摩卡棕","")
        )
)

val eyesColoredTypeList = getEyesColoredLists()

fun getEyesColoredLists(): MutableList<BottomMenuItemType.EyesColoredType> {
        val typeList = mutableListOf<BottomMenuItemType.EyesColoredType>()
        for(i in 1..5) {
                typeList.add(object : BottomMenuItemType.EyesColoredType(){})
        }
        return typeList
}

val eyesColoredList = listOf(
        BottomMenuItem(
                BottomMenuItemType.EyesColoredType.NoEffect,
                "原图",
                R.mipmap.icon_original,
                0,
                100,
                false,
                resourcePath = ResourcePath(500, "", "","原图","")
        ),
        BottomMenuItem(
                eyesColoredTypeList[0],
                "水光黑",
                R.mipmap.icon_beautify_eyes_water_black,
                0,
                100,
                true,
                resourcePath = ResourcePath(501, "Resources/MakeupResources/coloredcontactsdir/coloredcontactsdir_darknight_black.bundle/", "","水光黑","")
        ),
        BottomMenuItem(
                eyesColoredTypeList[1],
                "星空蓝",
                R.mipmap.icon_beautify_eyes_starry_blue,
                0,
                100,
                true,
                resourcePath = ResourcePath(502, "Resources/MakeupResources/coloredcontactsdir/coloredcontactsdir_starry_blue.bundle/", "","星空蓝","")
        ),
        BottomMenuItem(
                eyesColoredTypeList[2],
                "仙棕绿",
                R.mipmap.icon_beautify_eyes_fairy_brown_green,
                0,
                100,
                true,
                resourcePath = ResourcePath(503, "Resources/MakeupResources/coloredcontactsdir/coloredcontactsdir_mystery_brown_green.bundle/", "","仙棕绿","")
        ),
        BottomMenuItem(
                eyesColoredTypeList[3],
                "异瞳棕",
                R.mipmap.icon_beautify_eyes_different_pupil_brown,
                0,
                100,
                true,
                resourcePath = ResourcePath(504, "Resources/MakeupResources/coloredcontactsdir/coloredcontactsdir_polar_lights_brown.bundle/", "","异瞳棕","")
        ),
        BottomMenuItem(
                eyesColoredTypeList[4],
                "可可棕",
                R.mipmap.icon_beautify_eyes_cocoa_brown,
                0,
                100,
                true,
                resourcePath = ResourcePath(505, "Resources/MakeupResources/coloredcontactsdir/coloredcontactsdir_chocolate_brown.bundle/", "","可可棕","")
        )
)

val compareButtonListener = View.OnTouchListener() { view: View, motionEvent: MotionEvent ->
    view.performClick()
    when (motionEvent.action) {
        MotionEvent.ACTION_DOWN -> TestHandler.onCompareButtonPress()
        MotionEvent.ACTION_UP -> TestHandler.onCompareButtonRelease()
    }
    true
}