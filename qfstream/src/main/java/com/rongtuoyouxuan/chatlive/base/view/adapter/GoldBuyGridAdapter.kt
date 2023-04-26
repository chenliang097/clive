package ccom.rongtuoyouxuan.chatlive.base.view.adapter

import android.content.Context
import android.widget.RelativeLayout
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.rongtuoyouxuan.chatlive.base.view.model.RechargeInfoBean
import com.rongtuoyouxuan.chatlive.stream.R

class GoldBuyGridAdapter:BaseQuickAdapter<RechargeInfoBean, BaseViewHolder> {
    private var buyGoldClickListener: BuyGoldClickListener? = null
    private var position = 0
    private var mContext: Context? = null
    constructor(context: Context, data:MutableList<RechargeInfoBean>):super(R.layout.rt_adapter_grid_buy_gold, data){
        this.mContext = context
    }

    override fun convert(holder: BaseViewHolder, item: RechargeInfoBean) {
        holder.setText(R.id.buyGoldAdapterGoldTxt, item?.coin.toString())
        holder.setText(R.id.buyGoldAdapterPayTxt, context.resources.getString(R.string.rt_recharge_dialog_rmb, item?.rmp))
        holder.getView<RelativeLayout>(R.id.buyGoldAdapterLayout).setOnClickListener {
            if(null != buyGoldClickListener){
                buyGoldClickListener!!.onBuyGoldClickListener(holder.layoutPosition)
            }
        }

        var layout = holder.getView<RelativeLayout>(R.id.buyGoldAdapterLayout)
        if (position == holder.layoutPosition){
            layout.setBackgroundResource(R.drawable.rt_bg_item_recharge_dialog_select)
        }else{
            layout.setBackgroundResource(R.drawable.rt_bg_item_recharge_dialog)
        }
        layout.setOnClickListener {
            if(null != buyGoldClickListener)
                buyGoldClickListener!!.onBuyGoldClickListener(holder.layoutPosition)
        }
    }

    fun updateView(position: Int){
        this.position = position
        notifyDataSetChanged()
    }

    fun setBuyGoldClickListener(buyGoldClickListener: BuyGoldClickListener){
        this.buyGoldClickListener = buyGoldClickListener
    }

    interface BuyGoldClickListener{
        fun onBuyGoldClickListener(position:Int)
    }
}