package com.rongtuoyouxuan.chatlive.stream.view.layout

import android.content.Context
import android.util.AttributeSet
import android.view.LayoutInflater
import android.view.View
import android.widget.*
import androidx.fragment.app.FragmentActivity
import androidx.lifecycle.ViewModelProvider
import com.rongtuoyouxuan.chatlive.base.DialogUtils
import com.rongtuoyouxuan.chatlive.base.view.model.SendEvent
import com.rongtuoyouxuan.chatlive.databus.DataBus
import com.rongtuoyouxuan.chatlive.stream.R
import com.rongtuoyouxuan.chatlive.stream.view.layout.RoomSetMaskInputLayout.OnSetMaskWordListener
import com.rongtuoyouxuan.chatlive.stream.viewmodel.AnchorManagerViewModel
import kotlinx.android.synthetic.main.qf_stream_layout_set_mask_word.view.*

open class SetMaskWordsView @JvmOverloads constructor(context: Context, attrs: AttributeSet? = null, defStyleAttr: Int = 0)
    :LinearLayout (context, attrs, defStyleAttr){

    private var anchorManagerViewModel:AnchorManagerViewModel? = null
    private var roomId = ""

    init {
        LayoutInflater.from(context).inflate(R.layout.qf_stream_layout_set_mask_word, this)
        initView()
        initObserver()
    }

    fun initView(){
    }

    fun initObserver(){
        anchorManagerViewModel = ViewModelProvider(context as FragmentActivity).get(AnchorManagerViewModel::class.java)
        anchorManagerViewModel?.setRoomMaskWordsLiveData?.observe(context as FragmentActivity){
            anchorManagerViewModel?.getRoomMaskWords(roomId)
        }
        anchorManagerViewModel?.roomMaskWordsLiveData?.observe(context as FragmentActivity){
            anchorManagerMaskWordNumTxt.text = resources.getString(R.string.stream_anchor_manager_mask_work_num, it.data?.words?.size, 10)
            it.data?.words?.let { it1 -> updateWordsLayout(it1) }
        }
    }

    fun setData(roomId:String){
        this.roomId = roomId
        anchorManagerViewModel?.getRoomMaskWords(roomId)
    }

    private fun updateWordsLayout(list:List<String>){
        if(list != null && list.isNotEmpty()) {
            setMaskWordAddLayout.visibility = View.GONE
            setMaskWordFlowLayout.visibility = View.VISIBLE
            setMaskWordFlowLayout.removeAllViews()

            list.forEachIndexed() {index, data->
                var convertView =
                    View.inflate(context, R.layout.qf_stream_adapter_set_mask_word, null)
                var addMaskWordsLayout = convertView.findViewById<LinearLayout>(R.id.addMaskWordsLayout)
                var showMaskWordsLayout = convertView.findViewById<LinearLayout>(R.id.showMaskWordsLayout)
                var txt = convertView.findViewById<TextView>(R.id.setMaskWordTxt)
                var deleteImg = convertView.findViewById<ImageView>(R.id.setMaskWordDeleteImg)

                if(index == 0){
                    if(list.size == 10){
                        addMaskWordsLayout.visibility = View.GONE
                        showMaskWordsLayout.visibility = View.VISIBLE
                    }else {
                        addMaskWordsLayout.visibility = View.VISIBLE
                        showMaskWordsLayout.visibility = View.VISIBLE
                    }
                }else{
                    addMaskWordsLayout.visibility = View.GONE
                    showMaskWordsLayout.visibility = View.VISIBLE
                }
                txt.text = data

                addMaskWordsLayout.setOnClickListener { view ->
                    setMaskWord()
                }

                deleteImg.setOnClickListener { view ->
                    anchorManagerViewModel?.deleteRoomMaskWords(roomId,DataBus.instance().USER_ID, data)
                }
                setMaskWordFlowLayout.addView(convertView)
            }
        }else{
            setMaskWordAddLayout.visibility = View.VISIBLE
            setMaskWordFlowLayout.visibility = View.GONE
            setMaskWordAddLayout.setOnClickListener { view ->
                setMaskWord()
            }
        }
    }

    fun setMaskWord(){
        DialogUtils.createRoomSetMaskWordInputDialog(context
        ) {
            anchorManagerViewModel?.setRoomMaskWords(roomId, DataBus.instance().USER_ID, it)
        }.show()
    }

}