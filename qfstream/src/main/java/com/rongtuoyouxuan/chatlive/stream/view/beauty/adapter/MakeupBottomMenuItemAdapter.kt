package com.rongtuoyouxuan.chatlive.stream.view.beauty.adapter

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.CheckedTextView
import androidx.recyclerview.widget.RecyclerView
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.beauty.listener.OnItemClickListener
import com.rongtuoyouxuan.chatlive.stream.view.beauty.model.BottomMenuItem
import com.rongtuoyouxuan.chatlive.stream.view.beauty.widget.MenuItemButton

/**
 * the bottom menu item adapter
 */
class MakeupBottomMenuItemAdapter() :
    RecyclerView.Adapter<MakeupBottomMenuItemAdapter.ViewHolder>() , BaseAdapterInterface {
    inner class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {
        val bottomImageItem: MenuItemButton = view.findViewById(R.id.bottomImageItem)
        val bottomTextItem: CheckedTextView = view.findViewById(R.id.bottomTextItem)
    }

    private var menuList: List<BottomMenuItem> = ArrayList();

    // 菜单的item被点击之后的listener
    private var listener: OnItemClickListener? = null

    // 记录上一个被点击的值
    private var lastSelectedPosition = -1 // 默认值

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val view =
            LayoutInflater.from(parent.context).inflate(R.layout.makeup_bottom_menu_item, parent, false)
        return ViewHolder(view)
    }

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val bottomMenuItem = menuList[position]


        holder.bottomImageItem.apply {
            setImageResource(bottomMenuItem.iconId)
            neededDrawBoarder =
//                if(bottomMenuItem.bottomMenuItemType is MenuItemType.Reset) {
//                    false
//                }else{
                    bottomMenuItem.selected
//                }
            isCircle = bottomMenuItem.isCircle
        }

        holder.bottomTextItem.text = bottomMenuItem.text


        holder.bottomTextItem.setTextColor(bottomMenuItem.textColor)

        holder.bottomTextItem.isChecked = bottomMenuItem.selected



        holder.bottomImageItem.setOnClickListener {


            // 如果当前选择的position和之前选择的position一样，则不需要更新圆圈
            if (lastSelectedPosition != position) {
                // 清除点击状态
                clearSelectedState()

                bottomMenuItem.selected = true

                // 更新当前位置的UI
                notifyItemChanged(position)


                // 更新上一个位置和当前位置
                if (isValidLastPosition()) {
                    notifyItemChanged(lastSelectedPosition)
                }

                lastSelectedPosition = position
                //notifyDataSetChanged()
            }


            // 执行点击动作
            listener?.onItemClick(position)

        }
    }

    override fun getItemCount(): Int = menuList.size

    override fun setOnItemClickListener(listener: OnItemClickListener) {
        this.listener = listener
    }

    // 清空选中状态
    private fun clearSelectedState() {
        if (isValidLastPosition()) {
            // 更新前一个按钮的文字颜色
            menuList[lastSelectedPosition].selected = false
        }
    }

    // 判断lastSelectedPosition是否有效
    private fun isValidLastPosition() = lastSelectedPosition in 0..menuList.size

    override fun setData(dataList: List<BottomMenuItem>) {
        menuList = dataList
        lastSelectedPosition = -1
        var pos = -1
        dataList.onEach {
            pos ++;
            if(it.selected)
            {
                lastSelectedPosition = pos
            }
        }

        notifyDataSetChanged()
    }

}


