package com.rongtuoyouxuan.chatlive.stream.view.beauty

import android.annotation.SuppressLint
import android.app.Dialog
import android.content.DialogInterface
import android.content.res.ColorStateList
import android.content.res.Resources
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Build
import android.os.Bundle
import android.view.*
import android.widget.*
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AlertDialog
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.Observer
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.beauty.adapter.BaseAdapterInterface
import com.rongtuoyouxuan.chatlive.stream.view.beauty.adapter.BeautifyBottomMenuItemAdapter
import com.rongtuoyouxuan.chatlive.stream.view.beauty.adapter.MakeupBottomMenuItemAdapter
import com.rongtuoyouxuan.chatlive.stream.view.beauty.listener.OnClickListener
import com.rongtuoyouxuan.chatlive.stream.view.beauty.listener.OnItemClickListener
import com.rongtuoyouxuan.chatlive.stream.view.beauty.widget.FinallyItemDecoration
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.MenuItemType
import com.rongtuoyouxuan.chatlive.stream.view.beauty.util.compareButtonListener
import com.rongtuoyouxuan.chatlive.stream.view.beauty.widget.SeekBarWithNumber
import com.rongtuoyouxuan.chatlive.stream.view.beauty.widget.TransparentImageButton
import com.rongtuoyouxuan.chatlive.crtutil.util.UIUtils
import com.rongtuoyouxuan.chatlive.crtmagicindicator.buildins.UIUtil

/**
 * 底部弹窗页面
 */
class BeautifyBottomFragment : DialogFragment() {

    private val bottomFragmentViewModel: BottomFragmentViewModel = BottomFragmentViewModel

    private val beautifyFragmentViewModel: BeautifyBottomFragmentViewModel = BeautifyBottomFragmentViewModel

    // 点击弹窗外部区域后的动作
    private var listener: OnClickListener? = null

    private lateinit var menuList: RecyclerView
    private lateinit var secondMenuList: RecyclerView
    private lateinit var seekBar: SeekBarWithNumber
    private lateinit var bottomMenuHeader: LinearLayout
    private lateinit var compareButton: TransparentImageButton
    private lateinit var bottomLayout: ConstraintLayout
    private lateinit var bottomMenuTab: RadioGroup
    private lateinit var beautifyTab: RadioButton
    private lateinit var beautifyBodyTab: RadioButton
    private lateinit var beautifyMakeupTab: RadioButton
    private lateinit var beautifyStyleTab: RadioButton
    private lateinit var beautifyAKeyTab: RadioButton
    private lateinit var bottomSecondLayout: ConstraintLayout
    private lateinit var bottomSecondMenuTab: RadioGroup
    private lateinit var returnSecond: ImageView

    override fun onCreateView(
        inflater: LayoutInflater, container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View {
        // Inflate the layout for this fragment
        dialog?.let { setDialog(it) }

        val view = inflater.inflate(R.layout.fragment_beautify_bottom, container, false);

        initView(view)
        initListener()

        seekBar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
            override fun onProgressChanged(seekBar: SeekBar?, progress: Int, fromUser: Boolean) {

                if(fromUser) {
                    seekBar?.let {
                        val seek = seekBar as? SeekBarWithNumber
                        seek?.let {
                            beautifyFragmentViewModel.seekBarValue.value = (seekBar as? SeekBarWithNumber)?.getProgress2()
                        }
                    }
                }

            }

            override fun onStartTrackingTouch(seekBar: SeekBar?) {
            }

            override fun onStopTrackingTouch(seekBar: SeekBar?) {

                seekBar?.let{
                    val seek = seekBar as? SeekBarWithNumber
                    seek?.let{
                        beautifyFragmentViewModel.seekBarValue.value = (seekBar as? SeekBarWithNumber)?.getProgress2()
                    }
                }

            }
        })

        // 点击header
        bottomMenuHeader.setOnClickListener {
            val seekBarVisible = beautifyFragmentViewModel.seekBarVisible.value

            // 如果seek bar没有显示，则收起底部弹窗，触发
            if (seekBarVisible == null || seekBarVisible == false) {
                dismiss()
                listener?.onClick()
            }
        }

        compareButton.setOnTouchListener(compareButtonListener)


        return view
    }

    private fun initView(view: View){
        this.menuList = view.findViewById(R.id.menuList)
        this.secondMenuList = view.findViewById(R.id.secondMenuList)
        this.seekBar = view.findViewById(R.id.seekBar)
        this.bottomMenuHeader = view.findViewById(R.id.bottomMenuHeader)
        this.compareButton = view.findViewById(R.id.compareButton)
        this.bottomLayout = view.findViewById(R.id.bottomLayout)
        this.bottomMenuTab = view.findViewById(R.id.bottomMenuTab)
        this.beautifyTab = view.findViewById(R.id.beautifyTab)
        this.beautifyBodyTab = view.findViewById(R.id.beautifyBodyTab)
        this.beautifyMakeupTab = view.findViewById(R.id.beautifyMakeupTab)
        this.beautifyStyleTab = view.findViewById(R.id.beautifyStyleTab)
        this.beautifyAKeyTab = view.findViewById(R.id.beautifyAKeyTab)
        this.bottomSecondLayout = view.findViewById(R.id.bottomSecondLayout)
        this.bottomSecondMenuTab = view.findViewById(R.id.bottomSecondMenuTab)
        this.returnSecond = view.findViewById(R.id.returnSecond)

        this.menuList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)
            adapter = BeautifyBottomMenuItemAdapter()
            addItemDecoration(FinallyItemDecoration(UIUtil.dip2px(context, 16.0)))
        }
    }

    @RequiresApi(Build.VERSION_CODES.N)
    private fun initListener(){
        activity?.let { beautifyFragmentViewModel.addSeekBarListener(it) }
        activity?.let { beautifyFragmentViewModel.addBottomMenuItemListener(it) }
        beautifyFragmentViewModel.seekBarValue.observe(viewLifecycleOwner, Observer { seekBarValue ->
            seekBarValue?.let {
                seekBar.progress = seekBarValue
            }
        })

        beautifyFragmentViewModel.bottomMenuTabSelected.observe(viewLifecycleOwner
        ) { _ ->
            val adapter = menuList.adapter as? BeautifyBottomMenuItemAdapter
            adapter?.apply {

                beautifyFragmentViewModel.getBottomMenuList().let {
                    setData(it)
                    setOnItemClickListener(object : OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val item = it[position]
                            item.clickListener?.invoke()
                        }
                    })
                }

            }
        }

        beautifyFragmentViewModel.seekBarVisible.observe(viewLifecycleOwner) { seekBarVisible ->

            if (seekBarVisible) {
                seekBar.setOffsetValue(beautifyFragmentViewModel.seekBarOffsetValue)
                seekBar.max = beautifyFragmentViewModel.seekBarMaxValue
            }

            seekBar.visibility = if (seekBarVisible) View.VISIBLE else View.INVISIBLE
        }

        beautifyFragmentViewModel.enableBeautifyStyleBtns.observe(viewLifecycleOwner) { enable ->
            val adapter = menuList.adapter as? BeautifyBottomMenuItemAdapter
            adapter?.setBeautifyStyleBtns(enable)
        }

        beautifyFragmentViewModel.isSelectBeautifyStyleBtn.observe(viewLifecycleOwner) { isSelect ->
            // 如果选了风格妆，【美妆】就不可选
            val adapter = menuList.adapter as? BeautifyBottomMenuItemAdapter
            adapter?.setMakeupBtns(!isSelect)
        }

        beautifyFragmentViewModel.showSecondMenu.observe(viewLifecycleOwner) { showSecondMenu ->

            val type = beautifyFragmentViewModel.firstBottomMenuItemType
            if (showSecondMenu && type != null) {
                initSecondMenu(type)
                bottomSecondLayout.visibility = View.VISIBLE
                bottomLayout.visibility = View.GONE
            } else {
                beautifyFragmentViewModel.seekBarVisible.value = false
                bottomSecondLayout.visibility = View.GONE
                bottomLayout.visibility = View.VISIBLE
                menuList.adapter?.notifyDataSetChanged()
            }

        }

        bottomMenuTab.setOnCheckedChangeListener(RadioGroup.OnCheckedChangeListener() { _, checkedId: Int ->


            when (checkedId) {
                R.id.beautifyTab -> {
                    if(beautifyTab.isChecked) {
                        beautifyFragmentViewModel.setBeautifySkinTab()
                    }
                }
                R.id.beautifyBodyTab -> {
                    if(beautifyBodyTab.isChecked) {
                        beautifyFragmentViewModel.setBeautifyBodyTab()
                    }
                }
                R.id.beautifyMakeupTab ->{
                    if(beautifyMakeupTab.isChecked){
                        beautifyFragmentViewModel.setBeautifyMakeupTab()
                    }
                }
                R.id.beautifyStyleTab ->{
                    if(beautifyStyleTab.isChecked){
                        beautifyFragmentViewModel.setBeautifyStyleTab()
                    }
                }
                R.id.beautifyAKeyTab  ->{
                    if(beautifyAKeyTab.isChecked){
                        beautifyFragmentViewModel.setBeautifyStyleTab()
                    }
                }

            }
        })

        returnSecond.setOnClickListener {
            beautifyFragmentViewModel.setShowSecondMenu(false)
        }

        bottomFragmentViewModel.dialogProducer =
            { title: String, msg: String, positiveButtonClicked: () -> Unit, negativeButtonClicked: () -> Unit ->
                // 弹出dialog
                val mAlertDialog = AlertDialog.Builder(context as FragmentActivity)
                    .setTitle(title)
                    .setMessage(msg)
                    .setPositiveButton("确定") { _: DialogInterface, _: Int ->
                        positiveButtonClicked()
                    }.setNegativeButton("取消") { _, _ ->
                        negativeButtonClicked()
                    }
                    .show()

                if (mAlertDialog.getWindow() != null) {
                    val lp = mAlertDialog.getWindow()!!.getAttributes();
                    lp.width = (context as FragmentActivity).getWindowManager().getDefaultDisplay().getWidth() / 10 * 9; // 宽度，可根据屏幕宽度进行计算
                    lp.gravity = Gravity.CENTER;
                    mAlertDialog.getWindow()!!.setAttributes(lp);
                }
            }

    }


    @RequiresApi(Build.VERSION_CODES.N)
    @SuppressLint("ResourceType", "UseCompatLoadingForColorStateLists")
    private fun initSecondMenu(type: MenuItemType.BottomMenuItemType)
    {
        bottomSecondMenuTab.removeAllViews()

        val dataList = beautifyFragmentViewModel.getSecondMenuData(type)

        var check = true
        dataList.forEach { menuItem ->
            menuItem.id = View.generateViewId()
            val button = RadioButton(context)
            val params = RadioGroup.LayoutParams(
                RadioGroup.LayoutParams.WRAP_CONTENT,
                RadioGroup.LayoutParams.MATCH_PARENT
            )
            params.leftMargin = UIUtils.dip2px(context, 32f)
            button.layoutParams = params
            button.buttonDrawable = null
            button.gravity = Gravity.CENTER
            button.textSize = 13f
            button.text = menuItem.title
            button.id = menuItem.id!!
            if(menuItem.supportCheck) {
                button.setBackgroundResource(R.drawable.btn_title_bg_selector)
            }

            if(check)
            {
                button.isChecked = check
                check = false
            }else{
                button.isChecked = check
            }



            val resource: Resources? = context?.resources
            val colorStateList: ColorStateList =
                resource!!.getColorStateList(R.drawable.btn_title_text_selector)
            button.setTextColor(colorStateList)

            bottomSecondMenuTab.addView(button)
        }


        bottomSecondMenuTab.setOnCheckedChangeListener { _, checkedId: Int ->

            dataList.forEach { menu ->
                menu.id?.let { id ->
                    if (id == checkedId) {
                        beautifyFragmentViewModel.switchSecondTab(menu.menuItemType)
                        initRecycleView()
                    }
                }

            }

        }
        beautifyFragmentViewModel.secondMenuMap[beautifyFragmentViewModel.firstBottomMenuItemType]?.let {
            beautifyFragmentViewModel.switchSecondTab(
                it
            )
        }?: run {
            beautifyFragmentViewModel.setSecondBottomMenuSelectedOption(MenuItemType.BottomMenuItemType.BeautifyMakeup.Unselected)
        }
        initRecycleView()


    }

    private fun initRecycleView()
    {
        this.secondMenuList.apply {
            layoutManager = LinearLayoutManager(context, LinearLayoutManager.HORIZONTAL, false)


            when(beautifyFragmentViewModel.secondBottomMenuTabSelected.value)
            {
                is MenuItemType.BottomMenuItemType.LipstickType ,
                is MenuItemType.BottomMenuItemType.BlusherType ,
                is MenuItemType.BottomMenuItemType.EyelashesType ,
                is MenuItemType.BottomMenuItemType.EyelinerType ,
                is MenuItemType.BottomMenuItemType.EyeshadowType ,
                is MenuItemType.BottomMenuItemType.EyesColoredType ->{
                    adapter = MakeupBottomMenuItemAdapter()
                }
                else -> {}
            }

            val adapter = secondMenuList.adapter as? BaseAdapterInterface
            adapter?.apply {
                beautifyFragmentViewModel.getSecondBottomMenuList().let {
                    setData(it)
                    setOnItemClickListener(object : OnItemClickListener {
                        override fun onItemClick(position: Int) {
                            val item = it[position]
                            item.clickListener?.invoke()
                        }
                    })
                }
            }
        }
    }

    /**
     * 创建底部弹窗
     */
    private fun setDialog(dialog: Dialog) {
        // 去掉dialog的标题
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE)

        // 设置dialog的大小和位置
        val window = dialog.window?.apply {
            // 去掉默认的padding
            decorView.setPadding(0, 0, 0, 0)

            val parameters = attributes
            parameters.gravity = Gravity.BOTTOM
            parameters.width = ViewGroup.LayoutParams.MATCH_PARENT
            parameters.height = ViewGroup.LayoutParams.WRAP_CONTENT
            parameters.dimAmount = 0f

            setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
            attributes = parameters

            addFlags(WindowManager.LayoutParams.FLAG_WATCH_OUTSIDE_TOUCH)
            addFlags(WindowManager.LayoutParams.FLAG_NOT_TOUCH_MODAL)

            dialog.setOnKeyListener(object : DialogInterface.OnKeyListener {
                override fun onKey(arg0: DialogInterface?, keyCode: Int, arg2: KeyEvent?): Boolean {
                    // TODO Auto-generated method stub
                    if (keyCode == KeyEvent.KEYCODE_BACK) {
                        dismiss()
                        return true
                    } else if (keyCode == KeyEvent.KEYCODE_MENU) {
                        return true
                    }
                    return false
                }
            })
        }


        // 设置回调
        window?.decorView?.setOnTouchListener { v, event ->
            if (event.action == MotionEvent.ACTION_OUTSIDE) {
                listener?.onClick()
                dismiss()
            } else {
                v.performClick()
            }
            true
        }
    }

    /**
     * 设置点击弹窗之外的监听动作
     */
    fun setPopUpWindowClickListener(listener: OnClickListener) {
        this.listener = listener
    }

    /**
     * 设置悬浮按钮（对比）高度
     */
    fun setFloatButtonHeight(view: View) {
        view.y = bottomLayout.y - 10
    }

    override fun dismiss() {
//        bottomMenuTab.check(R.id.beautifyTab)
        beautifyFragmentViewModel.cleanData()
        bottomFragmentViewModel.rightMenuSelected.value = MenuItemType.RightMenuItemType.Unselected
        super.dismiss()
    }
}