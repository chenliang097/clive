package com.rongtuoyouxuan.chatlive.stream.view.beauty

import android.content.Context
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModel
import com.rongtuoyouxuan.chatlive.crtdatabus.DataBus
import com.rongtuoyouxuan.chatlive.crtlog.upload.ULog
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.BottomMenuItem
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.BottomSecondMenu
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.MenuItemType
import com.rongtuoyouxuan.chatlive.stream.view.beauty.util.*
import com.rongtuoyouxuan.chatlive.rtstream.sdkmanager.util.ZegoUtil
import java.io.File

object BeautifyBottomFragmentViewModel : ViewModel() {


    private val handler: AbstractHandler = TestHandler


    /**
     * 切换【风格妆】下所有按钮的可选状态
     */
    val enableBeautifyStyleBtns = MutableLiveData<Boolean>().also {
        it.value = true
    }

    /**
     * 当前是否选中了一个【风格妆】按钮
     */
    val isSelectBeautifyStyleBtn = MutableLiveData<Boolean>().also {
        it.value = false
    }

    // seekBar是否显示
    val seekBarVisible = MutableLiveData<Boolean>().also {
        it.value = false
    }

    // seekBar显示的数值
    val seekBarValue = MutableLiveData<Int>().also {
        it.value = 0
    }


    var seekBarOffsetValue = 0;

    var seekBarMaxValue = 100


    private val beautifySkinMap = getBeautifySkinInitialMap()
    private val beautifyBodyMap = getBeautifyBodyInitialMap()
    private val beautifyStyleMap = getBeautifyStyleInitialMap()
    private val beautifyMakeupMap = getBeautifyMakeupInitialMap()


    private val lipstickMap = getLipstickInitialMap()
    private val blusherMap = getBlusherInitialMap()
    private val eyelashesMap = getEyelashesInitialMap()
    private val eyelinerMap = getEyelinerInitialMap()
    private val eyeshadowMap = getEyeshadowInitialMap()
    private val eyesColoredMap = getEyesColoredInitialMap()

    val secondMenuMap = getSecondMenuInitialMap()



    // 保存相应的seekBar值
    private val intensityMap = getInitialMap()

    // 界面底部弹窗Tab
    val bottomMenuTabSelected =
            MutableLiveData<MenuItemType.BottomMenuItemType>().also {
                it.value = MenuItemType.BottomMenuItemType.BeautifySkin.Unselected
            }

    // 底部菜单item被选中的类型
    private val bottomMenuItemType = MutableLiveData<MenuItemType.BottomMenuItemType>().also {
        it.value = MenuItemType.BottomMenuItemType.BeautifySkin.Unselected
    }

    // 跳转二级菜单对应的一级底部菜单item被选中的类型
    var firstBottomMenuItemType : MenuItemType.BottomMenuItemType? = null

    val showSecondMenu: MutableLiveData<Boolean> =  MutableLiveData<Boolean>().also {
    }

    // 界面底部弹窗Tab
    val secondBottomMenuTabSelected =
        MutableLiveData<MenuItemType.BottomMenuItemType>(MenuItemType.BottomMenuItemType.BeautifyMakeup.Unselected)

    // 设置界面底部的菜单tab
    private fun setBottomMenuSelectedOption(option: MenuItemType.BottomMenuItemType) {
        bottomMenuTabSelected.value = option
    }

    // 当前设置的风格妆
    private var currentBeautifyStyle: MenuItemType.BottomMenuItemType.BeautifyStyle =
        MenuItemType.BottomMenuItemType.BeautifyStyle.Unselected

    // 当前设置的口红
    private var currentLipStick: MenuItemType.BottomMenuItemType.LipstickType =
        MenuItemType.BottomMenuItemType.LipstickType.Unselected

    // 当前设置的腮红
    private var currentBlusher: MenuItemType.BottomMenuItemType.BlusherType =
        MenuItemType.BottomMenuItemType.BlusherType.Unselected

    // 当前设置的眼睫毛
    private var currentEyelashes: MenuItemType.BottomMenuItemType.EyelashesType =
        MenuItemType.BottomMenuItemType.EyelashesType.Unselected

    // 当前设置的眼线
    private var currentEyeliner: MenuItemType.BottomMenuItemType.EyelinerType =
        MenuItemType.BottomMenuItemType.EyelinerType.Unselected

    // 当前设置的眼影
    private var currentEyeshadow: MenuItemType.BottomMenuItemType.EyeshadowType =
        MenuItemType.BottomMenuItemType.EyeshadowType.Unselected

    // 当前设置的美瞳
    private var currentEyesColored: MenuItemType.BottomMenuItemType.EyesColoredType =
        MenuItemType.BottomMenuItemType.EyesColoredType.Unselected

    public fun setShowSecondMenu(option: Boolean){
        showSecondMenu.value = option
    }

    // 设置界面底部的菜单tab
    fun setSecondBottomMenuSelectedOption(option: MenuItemType.BottomMenuItemType) {
        secondBottomMenuTabSelected.value = option
    }

    fun getSecondMenuData(type: MenuItemType.BottomMenuItemType): List<BottomSecondMenu> {
        return when(type){
            MenuItemType.BottomMenuItemType.BeautifyMakeup.Lipstick ->{
                lipstickMenuList
            }
            MenuItemType.BottomMenuItemType.BeautifyMakeup.Blusher ->{
                blusherMenuList
            }
            MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyelashes ->{
                eyelashesMenuList
            }
            MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeliner ->{
                eyelinerMenuList
            }
            MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeshadow ->{
                eyeShadowMenuList
            }
            MenuItemType.BottomMenuItemType.BeautifyMakeup.EyesColored ->{
                eyesColoredMenuList
            }
            else -> {
                listOf()
            }
        }
    }

    /**
     * 当底部bottomMenuType发生变化时的更新
     */
    fun addBottomMenuItemListener(owner: LifecycleOwner) {
        bottomMenuItemType.observe(owner, Observer { bottomMenuItem ->
            run {

                when (bottomMenuItem) {

                    is MenuItemType.Makeup -> {

                        when (bottomMenuItem) {
                            is MenuItemType.Reset ->{
                                seekBarVisible.value = false
                            }
                            else -> {
                                seekBarVisible.value = true
                            }
                        }

                        when (bottomMenuItem) {
                            is MenuItemType.BottomMenuItemType.LipstickType -> {

                                currentLipStick = bottomMenuItem

                            }
                            is MenuItemType.BottomMenuItemType.BlusherType -> {

                                currentBlusher = bottomMenuItem

                            }
                            is MenuItemType.BottomMenuItemType.EyelashesType -> {

                                currentEyelashes = bottomMenuItem

                            }
                            is MenuItemType.BottomMenuItemType.EyelinerType -> {

                                currentEyeliner = bottomMenuItem

                            }
                            is MenuItemType.BottomMenuItemType.EyeshadowType -> {

                                currentEyeshadow = bottomMenuItem

                            }
                            is MenuItemType.BottomMenuItemType.EyesColoredType -> {

                                currentEyesColored = bottomMenuItem

                            }
                        }
                    }

                    else -> {

                        when (bottomMenuItem) {
                            MenuItemType.BottomMenuItemType.BeautifyBody.Unselected -> {
                            }
                            MenuItemType.BottomMenuItemType.BeautifySkin.Unselected -> {
                            }
                            MenuItemType.BottomMenuItemType.BeautifyMakeup.Unselected -> {
                            }

                            MenuItemType.BottomMenuItemType.BeautifyBody.Reset -> {
                                resetBeautifyBody()
                            }
                            MenuItemType.BottomMenuItemType.BeautifySkin.Reset -> {
                                resetBeautifySkin()
                            }
                            MenuItemType.BottomMenuItemType.BeautifyMakeup.Reset -> {
                                resetBeautifyMakeup()
                            }
                            is MenuItemType.BottomMenuItemType.BeautifyStyle -> {
                                when (bottomMenuItem) {
                                    MenuItemType.BottomMenuItemType.BeautifyStyle.NoEffect -> {
                                        currentBeautifyStyle = bottomMenuItem
                                        resetBeautifyStyle()
                                        isSelectBeautifyStyleBtn.value = false
                                    }
                                    MenuItemType.BottomMenuItemType.BeautifyStyle.EyelidDownToMakeup,
                                    MenuItemType.BottomMenuItemType.BeautifyStyle.Look,
                                    MenuItemType.BottomMenuItemType.BeautifyStyle.MilkKiller,
                                    MenuItemType.BottomMenuItemType.BeautifyStyle.PureDesire,
                                    MenuItemType.BottomMenuItemType.BeautifyStyle.Youth -> {
                                        // 选中风格妆效果后，需要重置掉当前所有美妆效果
                                        resetBeautifyMakeupInner()
                                        currentBeautifyStyle = bottomMenuItem
                                        isSelectBeautifyStyleBtn.value = true
                                        seekBarVisible.value = true
                                    }
                                }
                            }
                            is MenuItemType.BottomMenuItemType.BeautifyMakeup -> {
                                seekBarVisible.value = false
                            }

                            else -> {
                                // 其它情况都显示拖动条
                                seekBarVisible.value = true
                            }
                        }


                    }
                }

                seekBarValue.value = intensityMap[getMenuItem(bottomMenuItem)]

            }
        })
    }

    /**
     * 添加特效监听接口
     */
    fun addSeekBarListener(owner: LifecycleOwner) {
        seekBarValue.observe(owner,
                Observer {
                    var itemType = getMenuItem(bottomMenuItemType.value)

                    if (itemType != null && it != null) {
                        handleEffect(itemType, it)
                        intensityMap[itemType] = it
                    }
                })
    }

    // 获取每个美颜的强度初始值
    private fun getInitialMap(): HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            map.putAll(beautifySkinMap)
            map.putAll(beautifyBodyMap)
            map.putAll(beautifyStyleMap)
            map.putAll(beautifyMakeupMap)
            map.putAll(lipstickMap)
            map.putAll(blusherMap)
            map.putAll(eyelashesMap)
            map.putAll(eyelinerMap)
            map.putAll(eyeshadowMap)
            map.putAll(eyesColoredMap)
        }
    }

    private fun getSecondMenuInitialMap(): HashMap<MenuItemType.BottomMenuItemType,MenuItemType.BottomMenuItemType>{
        return HashMap<MenuItemType.BottomMenuItemType, MenuItemType.BottomMenuItemType>().also { map ->
            run {

                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Lipstick] = MenuItemType.BottomMenuItemType.LipstickType.Unselected
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Blusher] = MenuItemType.BottomMenuItemType.BlusherType.Unselected
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyelashes] = MenuItemType.BottomMenuItemType.EyelashesType.Unselected
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeliner] = MenuItemType.BottomMenuItemType.EyelinerType.Unselected
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeshadow] = MenuItemType.BottomMenuItemType.EyeshadowType.Unselected
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.EyesColored] = MenuItemType.BottomMenuItemType.EyesColoredType.Unselected

            }
        }
    }

    private fun getBeautifySkinInitialMap():HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                // 美颜
                map[MenuItemType.BottomMenuItemType.BeautifySkin.WhitenSkin] = 50
                map[MenuItemType.BottomMenuItemType.BeautifySkin.PolishSkin] = 50
                map[MenuItemType.BottomMenuItemType.BeautifySkin.Rosy] = 50
                map[MenuItemType.BottomMenuItemType.BeautifySkin.Sharpen] = 50
                map[MenuItemType.BottomMenuItemType.BeautifySkin.WrinklesRemoving] = 50
                map[MenuItemType.BottomMenuItemType.BeautifySkin.DarkCirclesRemoving] = 50

            }
        }
    }

    private fun getBeautifyBodyInitialMap(): HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                // 美型
                map[MenuItemType.BottomMenuItemType.BeautifyBody.ThinFace] = 0
                map[MenuItemType.BottomMenuItemType.BeautifyBody.BigEye] = 0
                map[MenuItemType.BottomMenuItemType.BeautifyBody.BrightEye] = 50
                map[MenuItemType.BottomMenuItemType.BeautifyBody.Chin] = 0
                map[MenuItemType.BottomMenuItemType.BeautifyBody.SmallMouth] = 0
                map[MenuItemType.BottomMenuItemType.BeautifyBody.WhiteTeeth] = 50
                map[MenuItemType.BottomMenuItemType.BeautifyBody.NoseNarrowing] = 0
                map[MenuItemType.BottomMenuItemType.BeautifyBody.NoseLengthening] = 50
                map[MenuItemType.BottomMenuItemType.BeautifyBody.FaceShortening] = 50
                map[MenuItemType.BottomMenuItemType.BeautifyBody.MandibleSlimming] = 50
                map[MenuItemType.BottomMenuItemType.BeautifyBody.CheekboneSlimming] = 50
                map[MenuItemType.BottomMenuItemType.BeautifyBody.ForeheadShortening] = 50


            }
        }
    }

    private fun getBeautifyStyleInitialMap(): HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            // 风格妆
            map[MenuItemType.BottomMenuItemType.BeautifyStyle.EyelidDownToMakeup] = 50
            map[MenuItemType.BottomMenuItemType.BeautifyStyle.Look] = 50
            map[MenuItemType.BottomMenuItemType.BeautifyStyle.MilkKiller] = 50
            map[MenuItemType.BottomMenuItemType.BeautifyStyle.PureDesire] = 50
            map[MenuItemType.BottomMenuItemType.BeautifyStyle.Youth] = 50
        }
    }

    private fun getBeautifyMakeupInitialMap():HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                // 美妆
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Lipstick] = 60
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Blusher] = 80
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyelashes] = 100
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeliner] = 60
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeshadow] = 80
                map[MenuItemType.BottomMenuItemType.BeautifyMakeup.EyesColored] = 80

            }
        }
    }

    /**
     * 口红
     */
    private fun getLipstickInitialMap():HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                lipStickTypeList.forEach { type ->
                    map[type] =  beautifyMakeupMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Lipstick]!!.toInt()
                }

            }
        }
    }

    /**
     * 腮红
     */
    private fun getBlusherInitialMap():HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                blusherTypeList.forEach { type ->
                    map[type] =  beautifyMakeupMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Blusher]!!.toInt()
                }

            }
        }
    }

    /**
     * 眼睫毛
     */
    private fun getEyelashesInitialMap():HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                eyelashesTypeList.forEach { type ->
                    map[type] =  beautifyMakeupMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyelashes]!!.toInt()
                }

            }
        }
    }

    /**
     * 眼线
     */
    private fun getEyelinerInitialMap():HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                eyelinerTypeList.forEach { type ->
                    map[type] =  beautifyMakeupMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeliner]!!.toInt()
                }

            }
        }
    }

    /**
     * 眼影
     */
    private fun getEyeshadowInitialMap():HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                eyeshadowTypeList.forEach { type ->
                    map[type] =  beautifyMakeupMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeshadow]!!.toInt()
                }

            }
        }
    }

    /**
     * 美瞳
     */
    private fun getEyesColoredInitialMap(): HashMap<MenuItemType.BottomMenuItemType, Int> {
        return HashMap<MenuItemType.BottomMenuItemType, Int>().also { map ->
            run {
                eyesColoredTypeList.forEach { type ->
                    map[type] =
                        beautifyMakeupMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.EyesColored]!!.toInt()
                }
            }
        }
    }

    /**
     * 重置美肤
     */
    private fun resetBeautifySkin() {
        seekBarVisible.value = false


        if(isDefaultBeautifySkin())
        {
            return
        }

        val positiveButtonClicked = {

            beautifySkinMap.forEach { item ->
                intensityMap[item.key] = item.value
            }

            beautifySkinList.forEach {
                intensityMap[it.bottomMenuItemType]?.let { it1 ->
                    handleEffect(
                            it.bottomMenuItemType,
                            it1
                    )
                }
            }
        }
        val negativeButtonClicked = {
            ULog.d("clll", "按了取消键")
        }
        BottomFragmentViewModel.dialogProducer?.let {
            it(
                    "提示",
                    "确认重置当前美肤效果吗？",
                    positiveButtonClicked,
                    negativeButtonClicked
            )
        }


        ULog.d("clll", "resetBeautifySkin")
    }

    /**
     * 重置美型
     */
    private fun resetBeautifyBody() {
        seekBarVisible.value = false

        if(isDefaultBeautifyBody())
        {
            return
        }

        val positiveButtonClicked = {

            beautifyBodyMap.forEach { item ->
                intensityMap[item.key] = item.value
            }

            beautifyBodyList.forEach {
                intensityMap[it.bottomMenuItemType]?.let { it1 ->
                    handleEffect(
                            it.bottomMenuItemType,
                            it1
                    )
                }
            }
        }

        val negativeButtonClicked = {
            ULog.d("clll", "按了取消键")
        }

        BottomFragmentViewModel.dialogProducer?.let {
            it(
                    "提示",
                    "确认重置当前美型效果吗？",
                    positiveButtonClicked,
                    negativeButtonClicked
            )
        }


        ULog.d("clll", "resetBeautifyBody");
    }

    /**
     * 重置风格妆
     */
    private fun resetBeautifyStyle() {
        seekBarVisible.value = false

        if(isDefaultBeautifyStyle())
        {
            return
        }

        val positiveButtonClicked = {

            beautifyStyleMap.forEach { item ->
                intensityMap[item.key] = item.value
            }

            beautifyStyleList.forEach {
                intensityMap[it.bottomMenuItemType]?.let { it1 ->
                    handleEffect(
                            it.bottomMenuItemType,
                            it1
                    )
                }
            }
        }

        val negativeButtonClicked = {
            ULog.d("clll", "按了取消键")
        }

        BottomFragmentViewModel.dialogProducer?.let {
            it(
                    "提示",
                    "确认重置当前风格妆效果吗？",
                    positiveButtonClicked,
                    negativeButtonClicked
            )
        }


        ULog.d("clll", "resetBeautifyStyle")
    }

    /**
     * 重置美妆
     */
    private fun resetBeautifyMakeup() {
        seekBarVisible.value = false


        if(isDefaultBeautifyMakeup())
        {
            return
        }

        val positiveButtonClicked = {
            resetBeautifyMakeupInner()
        }
        val negativeButtonClicked = {
            ULog.d("clll", "按了取消键")
        }
        BottomFragmentViewModel.dialogProducer?.let {
            it(
                "提示",
                "确认重置当前美妆效果吗？",
                positiveButtonClicked,
                negativeButtonClicked
            )
        }
    }

    private fun resetBeautifyMakeupInner() {
        lipstickMap.forEach { item ->
            intensityMap[item.key] = item.value
        }

        blusherMap.forEach { item ->
            intensityMap[item.key] = item.value
        }

        eyelashesMap.forEach { item ->
            intensityMap[item.key] = item.value
        }

        eyelinerMap.forEach { item ->
            intensityMap[item.key] = item.value
        }

        eyeshadowMap.forEach { item ->
            intensityMap[item.key] = item.value
        }

        eyesColoredMap.forEach { item ->
            intensityMap[item.key] = item.value
        }


        currentLipStick =
            secondMenuMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Lipstick] as MenuItemType.BottomMenuItemType.LipstickType
        currentBlusher =
            secondMenuMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Blusher] as MenuItemType.BottomMenuItemType.BlusherType
        currentEyelashes =
            secondMenuMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyelashes] as MenuItemType.BottomMenuItemType.EyelashesType
        currentEyeliner =
            secondMenuMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeliner] as MenuItemType.BottomMenuItemType.EyelinerType
        currentEyeshadow =
            secondMenuMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.Eyeshadow] as MenuItemType.BottomMenuItemType.EyeshadowType
        currentEyesColored =
            secondMenuMap[MenuItemType.BottomMenuItemType.BeautifyMakeup.EyesColored] as MenuItemType.BottomMenuItemType.EyesColoredType

        handler.setLipstick("")
        handler.setCheek("")
        handler.setEyelash("")
        handler.setEyeliner("")
        handler.setEyeshadow("")
        handler.setEyesColored("")

        beautifyMakeupList.forEach {
            intensityMap[it.bottomMenuItemType]?.let { it1 ->
                handleEffect(
                    it.bottomMenuItemType,
                    it1
                )
            }
        }

        showSecondMenu.value = false
    }

    // 切换到美型
    fun setBeautifyBodyTab() {
        seekBarVisible.value = false
        setBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.BeautifyBody.Unselected)
    }

    // 切换到美肤
    fun setBeautifySkinTab() {
        seekBarVisible.value = false
        setBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.BeautifySkin.Unselected)
    }

    // 切换到美妆
    fun setBeautifyMakeupTab() {
        seekBarVisible.value = false
        setBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.BeautifyMakeup.Unselected)
    }

    // 切换到风格妆
    fun setBeautifyStyleTab() {
        seekBarVisible.value = false
        setBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.BeautifyStyle.Unselected)
    }

    //切换到一键美颜
    fun setBeautifyAKeyTab() {
        seekBarVisible.value = false
        setBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.BeautifyStyle.Unselected)
    }

    // 切换到口红
    private fun setLipstickTab() {
//        seekBarVisible.value = false
        setSecondBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.LipstickType.Unselected)
    }

    // 切换到腮红
    private fun setBlusherTab() {
//        seekBarVisible.value = false
        setSecondBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.BlusherType.Unselected)
    }

    // 切换到眼睫毛
    private fun setEyelashesTab() {
//        seekBarVisible.value = false
        setSecondBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.EyelashesType.Unselected)
    }

    // 切换到眼线
    private fun setEyelinerTab() {
//        seekBarVisible.value = false
        setSecondBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.EyelinerType.Unselected)
    }

    // 切换到眼影
    private fun setEyeshadowTab() {
//        seekBarVisible.value = false
        setSecondBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.EyeshadowType.Unselected)
    }

    private fun setEyesColoredTab() {
//        seekBarVisible.value = false
        setSecondBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.EyesColoredType.Unselected)
    }

    fun switchSecondTab(type: MenuItemType.BottomMenuItemType){
        when(type){
            is MenuItemType.BottomMenuItemType.LipstickType ->{
                setLipstickTab()
            }
            is MenuItemType.BottomMenuItemType.BlusherType ->{
                setBlusherTab()
            }
            is MenuItemType.BottomMenuItemType.EyelashesType ->{
                setEyelashesTab()
            }
            is MenuItemType.BottomMenuItemType.EyelinerType ->{
                setEyelinerTab()
            }
            is MenuItemType.BottomMenuItemType.EyeshadowType ->{
                setEyeshadowTab()
            }
            is MenuItemType.BottomMenuItemType.EyesColoredType ->{
                setEyesColoredTab()
            }
            else -> { }
        }
    }

    /**
     * 是否是上次选择的风格妆
     * bottomMenuItem ： 当前选择的按钮
     */
    private fun isSelectLastBeautifyStyle(bottomMenuItem: BottomMenuItem): Boolean {
        return currentBeautifyStyle == bottomMenuItem.bottomMenuItemType ||
                (currentBeautifyStyle is MenuItemType.BottomMenuItemType.BeautifyStyle.NoEffect
                        && bottomMenuItem.bottomMenuItemType is MenuItemType.BottomMenuItemType.BeautifyStyle.NoEffect)
    }

    private fun isDefaultBeautifyBody():Boolean
    {
        beautifyBodyMap.forEach { item ->
            if(intensityMap[item.key] != item.value)
            {
                return false
            }

        }

        return true
    }

    private fun isDefaultBeautifyStyle():Boolean
    {
        beautifyStyleMap.forEach { item ->
            if(intensityMap[item.key] != item.value)
            {
                return false
            }

        }

        return true
    }

    private fun isDefaultBeautifySkin():Boolean
    {
        beautifySkinMap.forEach { item ->
            if(intensityMap[item.key] != item.value)
            {
                return false
            }

        }

        return true
    }

    private fun isDefaultBeautifyMakeup():Boolean
    {
        beautifyMakeupMap.forEach { item ->
            if(!isDefaultBeautifyMakeupValue(item.key))
            {
                return false
            }
        }

        return true
    }

    fun isDefaultBeautifyMakeupType(type :MenuItemType.BottomMenuItemType):Boolean
    {
        var item :MenuItemType.BottomMenuItemType?= type

        if(item is MenuItemType.BottomMenuItemType.BeautifyMakeup)
        {
            item = secondMenuMap[item]
        }

        when(item){
            is MenuItemType.BottomMenuItemType.LipstickType ->{
                return (currentLipStick is MenuItemType.Reset || currentLipStick is MenuItemType.UnSelect)
            }
            is MenuItemType.BottomMenuItemType.BlusherType ->{
                return (currentBlusher is MenuItemType.Reset || currentBlusher is MenuItemType.UnSelect)
            }
            is MenuItemType.BottomMenuItemType.EyelashesType ->{
                return (currentEyelashes is MenuItemType.Reset || currentEyelashes is MenuItemType.UnSelect)
            }
            is MenuItemType.BottomMenuItemType.EyelinerType ->{
                return (currentEyeliner is MenuItemType.Reset || currentEyeliner is MenuItemType.UnSelect)
            }
            is MenuItemType.BottomMenuItemType.EyeshadowType ->{
                return (currentEyeshadow is MenuItemType.Reset || currentEyeshadow is MenuItemType.UnSelect)
            }
            is MenuItemType.BottomMenuItemType.EyesColoredType ->{
                return (currentEyesColored is MenuItemType.Reset || currentEyesColored is MenuItemType.UnSelect)
            }
            else ->{
                return false
            }
        }
    }

    fun isDefaultBeautifyMakeupValue(type :MenuItemType.BottomMenuItemType):Boolean
    {
        var item :MenuItemType.BottomMenuItemType?= type

        if(item is MenuItemType.BottomMenuItemType.BeautifyMakeup)
        {
            item = secondMenuMap[item]
        }

        when(item){
            is MenuItemType.BottomMenuItemType.LipstickType ->{
                if (currentLipStick is MenuItemType.Reset || currentLipStick is MenuItemType.UnSelect){
                    lipstickMap.forEach { lipstick ->
                        if(lipstick.value != intensityMap[lipstick.key])
                        {
                            return false
                        }
                    }
                }else{
                    return false
                }
            }
            is MenuItemType.BottomMenuItemType.BlusherType ->{
                if (currentBlusher is MenuItemType.Reset || currentBlusher is MenuItemType.UnSelect){
                    blusherMap.forEach { blusher ->
                        if(blusher.value != intensityMap[blusher.key])
                        {
                            return false
                        }
                    }
                }else{
                    return false
                }
            }
            is MenuItemType.BottomMenuItemType.EyelashesType ->{
                if (currentEyelashes is MenuItemType.Reset || currentEyelashes is MenuItemType.UnSelect){
                    eyelashesMap.forEach { eyelashes ->
                        if(eyelashes.value != intensityMap[eyelashes.key])
                        {
                            return false
                        }
                    }
                }else{
                    return false
                }
            }
            is MenuItemType.BottomMenuItemType.EyelinerType ->{
                if (currentEyeliner is MenuItemType.Reset || currentEyeliner is MenuItemType.UnSelect){
                    eyelinerMap.forEach { eyeliner ->
                        if(eyeliner.value != intensityMap[eyeliner.key])
                        {
                            return false
                        }
                    }
                }else{
                    return false
                }
            }
            is MenuItemType.BottomMenuItemType.EyeshadowType ->{
                if (currentEyeshadow is MenuItemType.Reset || currentEyeshadow is MenuItemType.UnSelect){
                    eyeshadowMap.forEach { eyeshadow ->
                        if(eyeshadow.value != intensityMap[eyeshadow.key])
                        {
                            return false
                        }
                    }
                }else{
                    return false
                }
            }
            is MenuItemType.BottomMenuItemType.EyesColoredType ->{
                if (currentEyesColored is MenuItemType.Reset || currentEyesColored is MenuItemType.UnSelect){
                    eyesColoredMap.forEach { eyeshadow ->
                        if(eyeshadow.value != intensityMap[eyeshadow.key])
                        {
                            return false
                        }
                    }
                }else{
                    return false
                }
            }
            else ->{
                return false
            }
        }
        return true
    }

     /**
     * 设置特效强度
     */
    private fun handleEffect(item: MenuItemType.BottomMenuItemType, intensity: Int) {
        when (item) {
            // 美肤
            MenuItemType.BottomMenuItemType.BeautifySkin.PolishSkin -> handler.polishSkin(intensity)
            MenuItemType.BottomMenuItemType.BeautifySkin.WhitenSkin -> handler.whitenSkin(intensity)
            MenuItemType.BottomMenuItemType.BeautifySkin.Rosy -> handler.rosy(intensity)
            MenuItemType.BottomMenuItemType.BeautifySkin.Sharpen -> handler.sharpen(intensity)
            MenuItemType.BottomMenuItemType.BeautifySkin.WrinklesRemoving -> handler.wrinklesRemoving(intensity)
            MenuItemType.BottomMenuItemType.BeautifySkin.DarkCirclesRemoving -> handler.darkCirclesRemoving(intensity)

            MenuItemType.BottomMenuItemType.BeautifySkin.Unselected -> {
            }
            MenuItemType.BottomMenuItemType.BeautifySkin.Reset -> resetBeautifySkin()

            // 美型
            MenuItemType.BottomMenuItemType.BeautifyBody.ThinFace -> handler.thinFace(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.BigEye -> handler.bigEye(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.BrightEye -> handler.eyesBrightening(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.Chin -> handler.longChin(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.SmallMouth -> handler.smallMouth(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.WhiteTeeth -> handler.teethWhitening(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.NoseNarrowing -> handler.noseNarrowing(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.NoseLengthening -> handler.noseLengthening(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.FaceShortening -> handler.faceShortening(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.MandibleSlimming -> handler.mandibleSlimming(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.CheekboneSlimming -> handler.cheekboneSlimming(intensity)
            MenuItemType.BottomMenuItemType.BeautifyBody.ForeheadShortening -> handler.foreheadShortening(intensity)

            MenuItemType.BottomMenuItemType.BeautifyBody.Unselected -> {
            }
            MenuItemType.BottomMenuItemType.BeautifyBody.Reset -> resetBeautifyBody()

            MenuItemType.BottomMenuItemType.BeautifyStyle.NoEffect -> resetBeautifyStyle()

            MenuItemType.BottomMenuItemType.BeautifyStyle.EyelidDownToMakeup,
            MenuItemType.BottomMenuItemType.BeautifyStyle.Look,
            MenuItemType.BottomMenuItemType.BeautifyStyle.MilkKiller,
            MenuItemType.BottomMenuItemType.BeautifyStyle.PureDesire,
            MenuItemType.BottomMenuItemType.BeautifyStyle.Youth, -> {
                handler.setBeautifyStyleParam(intensity)
            }

            is MenuItemType.UnSelect ->{

            }
            is MenuItemType.Reset->{
                when(item){
                    is MenuItemType.BottomMenuItemType.LipstickType ->{
                        handler.setLipstick("")
                    }
                    is MenuItemType.BottomMenuItemType.BlusherType ->{
                        handler.setCheek("")
                    }
                    is MenuItemType.BottomMenuItemType.EyelashesType ->{
                        handler.setEyelash("")
                    }
                    is MenuItemType.BottomMenuItemType.EyelinerType ->{
                        handler.setEyeliner("")
                    }
                    is MenuItemType.BottomMenuItemType.EyeshadowType ->{
                        handler.setEyeshadow("")
                    }
                    is MenuItemType.BottomMenuItemType.EyesColoredType ->{
                        handler.setEyesColored("")
                    }
                    is MenuItemType.Filter ->{
                        handler.setFilter("")
                    }
                }
            }
            is MenuItemType.BottomMenuItemType.LipstickType ->{
                handler.setLipstickParam(intensity)
            }
            is MenuItemType.BottomMenuItemType.BlusherType ->{
                handler.setCheekParam(intensity)
            }
            is MenuItemType.BottomMenuItemType.EyelashesType ->{
                handler.setEyelashParam(intensity)
            }
            is MenuItemType.BottomMenuItemType.EyelinerType ->{
                handler.setEyelinerParam(intensity)
            }
            is MenuItemType.BottomMenuItemType.EyeshadowType ->{
                handler.setEyeshadowParam(intensity)
            }
            is MenuItemType.BottomMenuItemType.EyesColoredType ->{
                handler.setEyesColoredParam(intensity)
            }

        }
    }

    fun getBottomMenuList(): List<BottomMenuItem> {

        when (bottomMenuTabSelected.value) {
            is MenuItemType.BottomMenuItemType.BeautifySkin -> {

                return beautifySkinList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                    }
                    item.selected = false
                }
            }

            is MenuItemType.BottomMenuItemType.BeautifyBody -> {

                return beautifyBodyList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                    }

                    item.selected = false
                }
            }

            is MenuItemType.BottomMenuItemType.BeautifyMakeup -> {

                return beautifyMakeupList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                        if(item.bottomMenuItemType != MenuItemType.BottomMenuItemType.BeautifyMakeup.Reset &&
                            item.bottomMenuItemType != MenuItemType.BottomMenuItemType.BeautifyMakeup.Unselected) {

                            firstBottomMenuItemType = item.bottomMenuItemType
                            setShowSecondMenu(true)

                        }
                    }

                    item.selected = false
                }
            }

            is MenuItemType.BottomMenuItemType.BeautifyStyle -> {
                return beautifyStyleList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        item.resourcePath?.let {
                            val path = getResourcePath(it.path)
                            handler.setBeautifyStyle(path)
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                    }

                    item.selected = false

                    //恢复上次选择按钮的状态
                    if (isSelectLastBeautifyStyle(item)) {
                        item.selected = true
                        bottomMenuItemType.value = item.bottomMenuItemType
                    }
                }
            }
            is MenuItemType.BottomMenuItemType.BeautifyAKey -> {
                return beautifyAKeyList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess) {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        item.resourcePath?.let {
                            val path = getResourcePath(it.path)
                            handler.setBeautifyStyle(path)
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                    }

                    item.selected = false

                    //恢复上次选择按钮的状态
                    if (isSelectLastBeautifyStyle(item)) {
                        item.selected = true
                        bottomMenuItemType.value = item.bottomMenuItemType
                    }
                }

            }
        }
        return listOf()
    }

    fun getSecondBottomMenuList(): List<BottomMenuItem> {
        when (secondBottomMenuTabSelected.value) {

            is MenuItemType.BottomMenuItemType.LipstickType -> {

                return lipStickList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType

                        item.resourcePath?.let {
                            val path = getResourcePath(it.path)
                            handler.setLipstick(path)
                        }
                    }

                    item.selected = false

                    //恢复上次选择按钮的状态
                    if (isSelectLastLipStick(item)) {
                        item.selected = true
                        bottomMenuItemType.value =
                            currentLipStick
                    }
                }
            }

            is MenuItemType.BottomMenuItemType.BlusherType -> {

                return blusherList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                        item.resourcePath?.let {
                            val path = getResourcePath(it.path)
                            handler.setCheek(path)
                        }
                    }

                    item.selected = false

                    //恢复上次选择按钮的状态
                    if (isSelectLastBlusher(item)) {
                        item.selected = true
                        bottomMenuItemType.value =
                            currentBlusher
                    }
                }
            }
            is MenuItemType.BottomMenuItemType.EyelashesType -> {

                return eyelashesList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                        item.resourcePath?.let {
                            val path = getResourcePath(it.path)
                            handler.setEyelash(path)
                        }
                    }

                    item.selected = false

                    //恢复上次选择按钮的状态
                    if (isSelectLastEyelashes(item)) {
                        item.selected = true
                        bottomMenuItemType.value =
                            currentEyelashes
                    }
                }
            }
            is MenuItemType.BottomMenuItemType.EyelinerType -> {

                return eyelinerList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                        item.resourcePath?.let {
                            val path = getResourcePath(it.path)
                            handler.setEyeliner(path)
                        }
                    }

                    item.selected = false

                    //恢复上次选择按钮的状态
                    if (isSelectLastEyeliner(item)) {
                        item.selected = true
                        bottomMenuItemType.value =
                            currentEyeliner
                    }
                }
            }

            is MenuItemType.BottomMenuItemType.EyeshadowType -> {

                return eyeshadowList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                        item.resourcePath?.let {
                            val path = getResourcePath(it.path)
                            handler.setEyeshadow(path)
                        }
                    }

                    item.selected = false

                    //恢复上次选择按钮的状态
                    if (isSelectLastEyeshadow(item)) {
                        item.selected = true
                        bottomMenuItemType.value =
                            currentEyeshadow
                    }
                }
            }

            is MenuItemType.BottomMenuItemType.EyesColoredType -> {

                return eyesColoredList.onEach { item ->
                    item.clickListener = {

                        if(item.showProcess)
                        {
                            seekBarOffsetValue = item.offsetValue
                            seekBarMaxValue = item.maxValue
                        }

                        item.resourcePath?.let {
                            val path = getResourcePath(it.path)
                            handler.setEyesColored(path)
                        }

                        bottomMenuItemType.value = item.bottomMenuItemType
                    }

                    item.selected = false

                    //恢复上次选择按钮的状态
                    if (isSelectLastEyesColored(item)) {
                        item.selected = true
                        bottomMenuItemType.value =
                            currentEyesColored
                    }
                }
            }

            else -> {}

        }
        return listOf()
    }


    /**
     * 用于二级菜单映射一级菜单的seekBar值
     */
    private fun getMenuItem(itemType : MenuItemType.BottomMenuItemType?): MenuItemType.BottomMenuItemType?{


        return itemType
    }

    /**
     * 是否是上次选择的口红
     * bottomMenuItem ： 当前选择的按钮
     */
    private fun isSelectLastLipStick(bottomMenuItem: BottomMenuItem): Boolean {
        return currentLipStick == bottomMenuItem.bottomMenuItemType
    }


    /**
     * 是否是上次选择的腮红
     * bottomMenuItem ： 当前选择的按钮
     */
    private fun isSelectLastBlusher(bottomMenuItem: BottomMenuItem): Boolean {
        return currentBlusher == bottomMenuItem.bottomMenuItemType
    }


    /**
     * 是否是上次选择的眼睫毛
     * bottomMenuItem ： 当前选择的按钮
     */
    private fun isSelectLastEyelashes(bottomMenuItem: BottomMenuItem): Boolean {
        return currentEyelashes == bottomMenuItem.bottomMenuItemType
    }

    /**
     * 是否是上次选择的眼线
     * bottomMenuItem ： 当前选择的按钮
     */
    private fun isSelectLastEyeliner(bottomMenuItem: BottomMenuItem): Boolean {
        return currentEyeliner == bottomMenuItem.bottomMenuItemType
    }

    /**
     * 是否是上次选择的眼影
     * bottomMenuItem ： 当前选择的按钮
     */
    private fun isSelectLastEyeshadow(bottomMenuItem: BottomMenuItem): Boolean {
        return currentEyeshadow == bottomMenuItem.bottomMenuItemType
    }

    private fun isSelectLastEyesColored(bottomMenuItem: BottomMenuItem): Boolean {
        return currentEyesColored == bottomMenuItem.bottomMenuItemType
    }

    /**
     * 从SharedPreference中读取intensity
     */
    fun readSharedPreferenceAndSet(context: Context) {
        val sharedPreference = SharedPreferenceUtil.getInstance()
                .withContext(context)
                .withName("backup")
                .build()
        if (sharedPreference != null) {
            intensityMap.forEach {
                intensityMap[it.key] = sharedPreference.getInt(it.key.javaClass.name, it.value)
                handleEffect(
                        it.key,
                        it.value
                )
            }

            ULog.d("clll", "读取sharePreference")
        }
    }

    /**
     * 将map保存到SharedPreference
     */
    fun saveSharedPreference(context: Context) {
        val sharedPreference = SharedPreferenceUtil.getInstance()
                .withContext(context)
                .withName("backup")
                .build()
        if (sharedPreference != null) {
            val editor = sharedPreference.edit()
            intensityMap.forEach {
                editor.putInt(it.key.javaClass.name, it.value)
            }
            editor.apply()
            ULog.d("clll", "写入sharePreference")
        }
    }


    fun cleanData()
    {
        seekBarVisible.value = false
        bottomMenuItemType.value = MenuItemType.BottomMenuItemType.BeautifySkin.Unselected
    }

    fun getResourcePath(path: String?):String{

        if(path.isNullOrBlank())
        {
            return ""
        }

        val basePath: String = DataBus.instance().appContext.externalCacheDir!!.getPath()
        ZegoUtil.copyFileFromAssets(
            DataBus.instance().appContext,path, basePath + File.separator + path)

        return basePath + File.separator + path
    }

}