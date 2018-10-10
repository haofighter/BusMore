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
import com.xb.busmore.base.rx.RxMessage
import com.xb.busmore.dao.manage.PosManager
import com.xb.busmore.util.BusToast
import com.xb.busmore.util.DividerItemDecoration
import com.xb.busmore.util.Utils
import com.xb.busmore.util.net.ftp.InitFtpDate
import kotlinx.android.synthetic.main.activity_select_line.*
import kotlinx.android.synthetic.main.text_layout.view.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class SelectLineActivity : BaseActivity() {


    override fun rootView(): Int {
        return R.layout.activity_select_line
    }

    override fun keyEvent(key: Int) {
        when (key) {
            PosKeyConfig.key_up_left -> {
                var changeItem = (recycle_line.adapter as LineAdapter).selectItem - 1
                changeItem = if (changeItem < 0) (recycle_line.adapter as LineAdapter).allItem.size-1 else changeItem
                (recycle_line.adapter as LineAdapter).selectItem = changeItem
                (recycle_line.adapter as LineAdapter).notifyDataSetChanged()
                recycle_line.scrollToPosition(changeItem)
            }
            PosKeyConfig.key_up_right
            -> {
                var changeItem = (recycle_line.adapter as LineAdapter).selectItem + 1
                (recycle_line.adapter as LineAdapter).selectItem = if (changeItem >= (recycle_line.adapter as LineAdapter).allItem.size) 0 else changeItem
                (recycle_line.adapter as LineAdapter).notifyDataSetChanged()
                recycle_line.scrollToPosition(changeItem)
            }
            PosKeyConfig.key_down_left
            -> {
                Executors.newScheduledThreadPool(1).schedule({
                    var allLineInfo = (recycle_line.adapter as LineAdapter).allItem.get((recycle_line.adapter as LineAdapter).selectItem)
                    if (!InitFtpDate.getInstance().downloadDetailLine(Utils.str2three(allLineInfo.acnt) + Utils.str2three(allLineInfo.routeno))) {
                        BusToast.showToast(App.getInstance(), "线路下载失败\n稍后重试", false)
                    }
                }, 0, TimeUnit.SECONDS)

            }
            PosKeyConfig.key_down_right
            -> {
                startActivity(HomeActivity::class.java)
            }
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        recycle_line.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycle_line.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recycle_line.adapter = LineAdapter()
    }

    class LineAdapter() : RecyclerView.Adapter<LineItem>() {
        var allItem = PosManager.getInstance().allLineInfo
        var selectItem: Int = 0
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): LineItem {
            var view = LayoutInflater.from(App.getInstance()).inflate(R.layout.text_layout, null)
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            return LineItem(view)
        }

        override fun getItemCount(): Int {
            return allItem.size
        }

        override fun onBindViewHolder(muneItem: LineItem, position: Int) {
            if (position == selectItem) {
                muneItem.view.text_view.text = allItem.get(position).routename
                muneItem.view.setBackgroundResource(R.color.gray)
                muneItem.view.text_view.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.colorWhite))
            } else {
                muneItem.view.text_view.text = allItem.get(position).routename
                muneItem.view.setBackgroundResource(R.color.colorWhite)
                muneItem.view.text_view.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.colorBlack))
            }
        }

    }

    class LineItem : RecyclerView.ViewHolder {
        var view: View

        constructor(itemView: View) : super(itemView) {
            view = itemView
        }
    }
}
