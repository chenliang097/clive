package com.rongtuoyouxuan.chatlive.stream.view.beauty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.beauty.BeautifyBottomFragmentViewModel
import com.rongtuoyouxuan.chatlive.stream.view.beauty.listener.OnItemClickListener
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.BottomMenuItem
import com.rongtuoyouxuan.chatlive.stream.view.beauty.widget.MenuItemButton
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.MenuItemType
import com.rongtuoyouxuan.chatlive.util.LaToastUtil

/**
 * the bottom menu item adapter
 */
class BeautifyBottomMenuItemAdapter() :
    RecyclerView.Adapter<BeautifyBottomMenuItemAdapter.ViewHolder>() {

    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bottomItemRootView: View = view.findViewById(R.id.bottomItemRootView)
        val bottomImageItem: MenuItemButton = view.findViewById(R.id.bottomImageItem)
        val bottomTextItem: CheckedTextView = view.findViewById(R.id.bottomTextItem)
        val hint: View = view.findViewById(R.id.hint)
    }

    private var isEnableBeautifyStyleBtns: Boolean = true
    private var isEnableMakeupBtns: Boolean = true

    private val beautifyViewModel = BeautifyBottomFragmentViewModel

    private var menuList: List<BottomMenuItem> = ArrayList();

    // 菜单的item被点击之后的listener
    private var listener: OnItemClickListener? = null

    // 记录上一个被点击的值
    private var lastSelectedPosition = -1 // 默认值

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.beautify_bottom_menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bottomMenuItem = menuList[position]
        holder.bottomItemRootView.alpha = 1f
        if (bottomMenuItem.bottomMenuItemType is MenuItemType.BottomMenuItemType.BeautifyStyle) {
            if (!isEnableBeautifyStyleBtns) {
                // 如果是【风格妆】禁用状态，单独处理样式
                holder.bottomItemRootView.alpha = 0.5f

                holder.bottomImageItem.apply {
                    setImageResource(bottomMenuItem.iconId)
                    neededDrawBoarder = false
                    isCircle = bottomMenuItem.isCircle

                    setOnClickListener {
                        LaToastUtil.showShort("请先取消贴纸效果后再使用")
                    }
                }
                holder.bottomTextItem.text = bottomMenuItem.text
                holder.bottomTextItem.setTextColor(bottomMenuItem.textColor)
                holder.bottomTextItem.isChecked = false
                holder.hint.visibility = View.INVISIBLE

                return
            }
        } else if (bottomMenuItem.bottomMenuItemType is MenuItemType.BottomMenuItemType.BeautifyMakeup) {
            if (!isEnableMakeupBtns) {
                // 如果是【风格妆】禁用状态，单独处理样式
                holder.bottomItemRootView.alpha = 0.5f

                holder.bottomImageItem.apply {
                    setImageResource(bottomMenuItem.iconId)
                    neededDrawBoarder = false
                    isCircle = bottomMenuItem.isCircle

                    setOnClickListener {
                        LaToastUtil.showShort("请先取消风格妆效果后再使用")
                    }
                }
                holder.bottomTextItem.text = bottomMenuItem.text
                holder.bottomTextItem.setTextColor(bottomMenuItem.textColor)
                holder.bottomTextItem.isChecked = false

                return
            }
        }

        holder.bottomImageItem.apply {
            setImageResource(bottomMenuItem.iconId)
            neededDrawBoarder =
                if(bottomMenuItem.bottomMenuItemType is MenuItemType.BottomMenuItemType.BeautifyMakeup) {
                    false
                }else{
                    bottomMenuItem.selected
                }

            isCircle = bottomMenuItem.isCircle
        }

        holder.bottomTextItem.text = bottomMenuItem.text
        holder.bottomTextItem.setTextColor(bottomMenuItem.textColor)
        holder.bottomTextItem.isChecked = bottomMenuItem.selected

        holder.hint.visibility = if(
            bottomMenuItem.bottomMenuItemType is MenuItemType.BottomMenuItemType.BeautifyMakeup &&
            bottomMenuItem.bottomMenuItemType != MenuItemType.BottomMenuItemType.BeautifyMakeup.Reset &&
            !BeautifyBottomFragmentViewModel.isDefaultBeautifyMakeupType(bottomMenuItem.bottomMenuItemType)
        ){
            View.VISIBLE
        }else{
            View.INVISIBLE
        }



        holder.bottomImageItem.setOnClickListener {


            // 如果当前选择的position和之前选择的position一样，则不需要更新圆圈（美颜）
            if (lastSelectedPosition != position) {
                // 清除点击状态
                clearSelectedState()

                // 设置新的选择状态
                bottomMenuItem.selected =
                    !(bottomMenuItem.bottomMenuItemType == MenuItemType.BottomMenuItemType.BeautifySkin.Reset
                            || bottomMenuItem.bottomMenuItemType == MenuItemType.BottomMenuItemType.BeautifyBody.Reset)

                if(bottomMenuItem.bottomMenuItemType is MenuItemType.BottomMenuItemType.BeautifyMakeup)
                {
                    bottomMenuItem.selected = false
                }

                // 更新当前位置的UI
                notifyItemChanged(position)


                // 更新上一个位置和当前位置
                if (isValidLastPosition()) {
                    notifyItemChanged(lastSelectedPosition)
                }

                lastSelectedPosition = position
            }


            // 执行点击动作
            listener?.onItemClick(position)

        }
    }

    override fun getItemCount(): Int = menuList.size

    fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // 清空选中状态
    private fun clearSelectedState() {
        if (isValidLastPosition()) {
            menuList[lastSelectedPosition].selected = false
        }
    }

    // 判断lastSelectedPosition是否有效
    private fun isValidLastPosition() = lastSelectedPosition in 0..menuList.size

    fun setData(dataList: List<BottomMenuItem>) {
        menuList = dataList
        lastSelectedPosition = -1
        dataList.forEachIndexed { index, bottomMenuItem ->
            if (bottomMenuItem.selected) {
                lastSelectedPosition = index
            }
        }
        notifyDataSetChanged()
    }

    fun setBeautifyStyleBtns(enable: Boolean) {
        isEnableBeautifyStyleBtns = enable
    }

    fun setMakeupBtns(enable: Boolean) {
        isEnableMakeupBtns = enable
    }
}


