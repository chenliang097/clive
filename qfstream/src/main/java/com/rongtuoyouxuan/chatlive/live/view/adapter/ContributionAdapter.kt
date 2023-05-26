package com.rongtuoyouxuan.chatlive.live.view.adapter

import android.annotation.SuppressLint
import android.content.Context
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.databinding.DataBindingUtil
import androidx.recyclerview.widget.RecyclerView
import com.blankj.utilcode.util.StringUtils
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.CmMultiEntity
import com.rongtuoyouxuan.chatlive.crtbiz2.model.stream.LiveAudienceRankEntity
import com.rongtuoyouxuan.chatlive.crtimage.util.GlideUtils
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.databinding.ItemDialogContributionBinding
import com.rongtuoyouxuan.chatlive.stream.databinding.ItemDialogContributionTop0Binding
import com.rongtuoyouxuan.chatlive.crtutil.util.BigDecimalUtil

/**
 *
 * date:2022/8/11-10:19
 * des: 贡献榜
 */
class ContributionAdapter(
    val context: Context,
    val bindCallBack: (data: LiveAudienceRankEntity?) -> Unit = { },
    val followCallBack: (data: LiveAudienceRankEntity?, position: Int) -> Unit = { _, _ -> }
) : RecyclerView.Adapter<RecyclerView.ViewHolder>() {
    private val inflater: LayoutInflater = LayoutInflater.from(context)

    val data = ArrayList<CmMultiEntity>()

    var loginUID = 0L
    var clickTop: IClickTop? = null

    override fun getItemViewType(position: Int): Int {
        return when (data[position].itemType) {
            CmMultiEntity.item_top -> 0
            else -> 1
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): RecyclerView.ViewHolder {
        val holder: RecyclerView.ViewHolder
        when (viewType) {
            0 -> {
                holder = TopHolder0(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_dialog_contribution_top_0, parent, false
                    )
                )
            }
            else -> {
                holder = ContentHolder(
                    DataBindingUtil.inflate(
                        inflater,
                        R.layout.item_dialog_contribution, parent, false
                    )
                )
            }
        }
        return holder
    }

    override fun onBindViewHolder(holder: RecyclerView.ViewHolder, position: Int) {
        when (getItemViewType(position)) {
            0 -> {
                if (holder is TopHolder0) {
                    bindTop0(holder, data[position].topList)
                }
            }
            else -> {
                if (holder is ContentHolder) {
                    bindContent(holder, data[position].liveItemEntity)
                }
            }
        }
    }

    override fun onBindViewHolder(
        holder: RecyclerView.ViewHolder,
        position: Int,
        payloads: MutableList<Any>
    ) {
        if (payloads.isNotEmpty()) {
            when (getItemViewType(position)) {
                0 -> {
                    if (holder is TopHolder0) {
                        when ((payloads[0] as? Bundle)?.getInt("ranking")) {
                            1 -> {
                                holder.itemBind.ivFollow.visibility = View.INVISIBLE
                            }
                            2 -> {
                                holder.itemBind.ivFollow1.visibility = View.INVISIBLE
                            }
                            3 -> {
                                holder.itemBind.ivFollow2.visibility = View.INVISIBLE
                            }
                        }
                    }
                }
            }
        } else {
            this.onBindViewHolder(holder, position)
        }
    }

    override fun getItemId(position: Int): Long {
        return position.toLong()
    }

    override fun getItemCount(): Int {
        return data.size
    }

    inner class TopHolder0(val itemBind: ItemDialogContributionTop0Binding) :
        RecyclerView.ViewHolder(itemBind.root)

    private fun bindTop0(holder: TopHolder0, list: ArrayList<LiveAudienceRankEntity>) {
        if (list.size > 0) {
            holder.itemBind.llTop.visibility = View.VISIBLE
            val item = list[0]
            if (item.userAvatar?.isNotEmpty() == true) {
                GlideUtils.loadCircleImage(
                    context, item.userAvatar, holder.itemBind.ivHeader,
                    R.drawable.icon_default_avatar
                )
                val uri = Uri.parse(item.userAvatar)
                val resultValue = uri.getQueryParameter("frame")
                if (!StringUtils.isTrimEmpty(resultValue)
                    && resultValue?.endsWith("webp", true) == true
                ) {
                    GlideUtils.loadImageWebP(
                        context,
                        resultValue,
                        holder.itemBind.ivHeaderFrame
                    )
                }
            }

            holder.itemBind.tvName.text = item.nickname
            holder.itemBind.tvDiamond.text = BigDecimalUtil.decimalFormat3(item.diamondAmount ?: 0f)
            if (item.userId == loginUID) {
                holder.itemBind.ivFollow.visibility = View.INVISIBLE
            } else if (item.isFollow == true) {
                holder.itemBind.ivFollow.visibility = View.INVISIBLE
            } else {
                holder.itemBind.ivFollow.visibility = View.VISIBLE

                holder.itemBind.ivFollow.setOnClickListener {
                    clickTop?.clickFollow(0, 1, item)
                }
            }

            holder.itemBind.llTop.setOnClickListener {
                clickTop?.click(item.userId ?: 0L)
            }
        } else {
            holder.itemBind.llTop.visibility = View.INVISIBLE
        }

        if (list.size > 1) {
            holder.itemBind.llTop1.visibility = View.VISIBLE

            val item = list[1]
            if (item.userAvatar?.isNotEmpty() == true) {
                GlideUtils.loadCircleImage(
                    context, item.userAvatar, holder.itemBind.ivHeader1,
                    R.drawable.icon_default_avatar
                )
                val uri = Uri.parse(item.userAvatar)
                val resultValue = uri.getQueryParameter("frame")
                if (!StringUtils.isTrimEmpty(resultValue)
                    && resultValue?.endsWith("webp", true) == true
                ) {
                    GlideUtils.loadImageWebP(
                        context,
                        resultValue,
                        holder.itemBind.ivHeaderFrame1
                    )
                }
            }

            holder.itemBind.tvName1.text = item.nickname
            holder.itemBind.tvDiamond1.text = BigDecimalUtil.decimalFormat3(item.diamondAmount ?: 0f)
            if (item.userId == loginUID) {
                holder.itemBind.ivFollow1.visibility = View.INVISIBLE
            } else if (item.isFollow == true) {
                holder.itemBind.ivFollow1.visibility = View.INVISIBLE
            } else {
                holder.itemBind.ivFollow1.visibility = View.VISIBLE
                holder.itemBind.ivFollow1.setOnClickListener {
                    clickTop?.clickFollow(0, 2, item)
                }
            }

            holder.itemBind.llTop1.setOnClickListener {
                clickTop?.click(item.userId ?: 0L)
            }
        } else {
            holder.itemBind.llTop1.visibility = View.INVISIBLE
        }

        if (list.size > 2) {
            holder.itemBind.llTop2.visibility = View.VISIBLE

            val item = list[2]
            if (item.userAvatar?.isNotEmpty() == true) {
                GlideUtils.loadCircleImage(
                    context, item.userAvatar, holder.itemBind.ivHeader2,
                    R.drawable.icon_default_avatar
                )
                val uri = Uri.parse(item.userAvatar)
                val resultValue = uri.getQueryParameter("frame")
                if (!StringUtils.isTrimEmpty(resultValue)
                    && resultValue?.endsWith("webp", true) == true
                ) {
                    GlideUtils.loadImageWebP(
                        context,
                        resultValue,
                        holder.itemBind.ivHeaderFrame2
                    )
                }
            }

            holder.itemBind.tvName2.text = item.nickname
            holder.itemBind.tvDiamond2.text = BigDecimalUtil.decimalFormat3(item.diamondAmount ?: 0f)
            if (item.userId == loginUID) {
                holder.itemBind.ivFollow2.visibility = View.INVISIBLE
            } else if (item.isFollow == true) {
                holder.itemBind.ivFollow2.visibility = View.INVISIBLE
            } else {
                holder.itemBind.ivFollow2.visibility = View.VISIBLE
                holder.itemBind.ivFollow2.setOnClickListener {
                    clickTop?.clickFollow(0, 3, item)
                }
            }

            holder.itemBind.llTop2.setOnClickListener {
                clickTop?.click(item.userId ?: 0L)
            }
        } else {
            holder.itemBind.llTop2.visibility = View.INVISIBLE
        }
    }

    inner class ContentHolder(val itemBind: ItemDialogContributionBinding) :
        RecyclerView.ViewHolder(itemBind.root)

    private fun bindContent(holder: ContentHolder, item: LiveAudienceRankEntity?) {
        val position = holder.bindingAdapterPosition
        holder.itemBind.tvPosition.text = "${position + 3}"
        if (item?.userAvatar?.isNotEmpty() == true) {
            GlideUtils.loadCircleImage(
                context, item.userAvatar, holder.itemBind.ivHeader,
                R.drawable.icon_default_avatar
            )
            val uri = Uri.parse(item.userAvatar)
            val resultValue = uri.getQueryParameter("frame")
            if (!StringUtils.isTrimEmpty(resultValue)
                && resultValue?.endsWith("webp", true) == true
            ) {
                GlideUtils.loadImageWebP(
                    context,
                    resultValue,
                    holder.itemBind.ivHeaderFrame
                )
            }
        }
        holder.itemBind.tvName.text = item?.nickname
        holder.itemBind.tvDiamond.text = BigDecimalUtil.decimalFormat3(item?.diamondAmount ?: 0f)
        if (item?.userId == loginUID) {
            holder.itemBind.ivFollow.visibility = View.GONE
        } else if (item?.isFollow == true) {
            holder.itemBind.ivFollow.visibility = View.GONE
        } else {
            holder.itemBind.ivFollow.visibility = View.VISIBLE
        }

        holder.itemBind.ivFollow.setOnClickListener {
            followCallBack(item, position)
        }

        holder.itemBind.spaceClick.setOnClickListener {
            bindCallBack(item)
        }
    }

    @SuppressLint("NotifyDataSetChanged")
    fun setList(list: List<CmMultiEntity>) {
        this.data.clear()
        this.data.addAll(list)
        notifyDataSetChanged()
    }

    fun addData(item: CmMultiEntity) {
        this.data.add(item)
        notifyItemInserted(this.data.size)
    }

    fun addData(index: Int, item: CmMultiEntity) {
        this.data.add(index, item)
        notifyItemInserted(this.data.size)
    }

    fun addData(newData: List<CmMultiEntity>) {
        this.data.addAll(newData)
//        notifyItemInserted(this.data.size)
        notifyItemRangeInserted(this.data.size - newData.size, newData.size)
    }

    interface IClickTop {
        fun click(uid: Long)

        fun clickFollow(position: Int, ranking: Int, item: LiveAudienceRankEntity)
    }
}