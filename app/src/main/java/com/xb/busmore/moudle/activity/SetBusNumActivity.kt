package com.xb.busmore.moudle.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xb.busmore.R
import com.xb.busmore.base.App
import com.xb.busmore.base.BaseActivity
import com.xb.busmore.base.PosKeyConfig
import com.xb.busmore.dao.manage.PosManager
import com.xb.busmore.util.DividerItemDecoration
import com.xb.busmore.util.Utils
import kotlinx.android.synthetic.main.activity_select_line.*
import kotlinx.android.synthetic.main.activity_set_bus_num.*
import kotlinx.android.synthetic.main.text_layout.view.*

class SetBusNumActivity : BaseActivity() {
    var white = ContextCompat.getColor(App.getInstance(), R.color.colorWhite)
    var black = ContextCompat.getColor(App.getInstance(), R.color.colorBlack)


    override fun keyEvent(key: Int) {
        when (key) {
            PosKeyConfig.key_up_left -> {

            }
            PosKeyConfig.key_up_right
            -> {

            }
            PosKeyConfig.key_down_left
            -> {

            }
            PosKeyConfig.key_down_right
            -> {
                startActivity(HomeActivity::class.java)
            }
        }
    }

    override fun rootView(): Int {
        return R.layout.activity_set_bus_num;
    }

    var numList = mutableListOf<Int>()

    init {
        initNum()
    }

    fun initNum() {
        var busNum = PosManager.getInstance().carConfig.busNo
        if (!Utils.StringIsEmpty(busNum)) {
            for (i in 1..busNum.length) {
                numList.add(busNum.get(i).toInt())
            }
        } else {
            for (i in 1..6) {
                numList.add(0)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recycle_line.layoutManager = LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false)
        recycle_line.adapter = CarNumAdapter(numList)
    }

    class CarNumAdapter(num: MutableList<Int>) : RecyclerView.Adapter<CarNumItem>() {
        var allItem = num
        var selectItem: Int = 0
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): CarNumItem {
            var view = LayoutInflater.from(App.getInstance()).inflate(R.layout.text_layout_h, null)
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            return CarNumItem(view)
        }

        override fun getItemCount(): Int {
            return allItem.size
        }

        override fun onBindViewHolder(muneItem: CarNumItem, position: Int) {
            if (position == selectItem) {
                muneItem.view.text_view.text = allItem.get(position).toString()
                muneItem.view.setBackgroundResource(R.color.gray)
                muneItem.view.text_view.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.colorWhite))
            } else {
                muneItem.view.text_view.text = allItem.get(position).toString()
                muneItem.view.setBackgroundResource(R.color.colorWhite)
                muneItem.view.text_view.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.colorBlack))
            }
        }
    }

    class CarNumItem : RecyclerView.ViewHolder {
        var view: View

        constructor(itemView: View) : super(itemView) {
            view = itemView
        }
    }

}
