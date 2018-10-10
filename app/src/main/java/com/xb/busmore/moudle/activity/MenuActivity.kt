package com.xb.busmore.moudle.activity

import android.os.Bundle
import android.support.v4.content.ContextCompat
import android.support.v7.widget.LinearLayoutManager
import android.support.v7.widget.RecyclerView
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import com.xb.busmore.BuildConfig
import com.xb.busmore.R
import com.xb.busmore.base.App
import com.xb.busmore.base.BaseActivity
import com.xb.busmore.base.PosKeyConfig
import com.xb.busmore.base.rx.RxMessage
import com.xb.busmore.dao.manage.PosManager
import com.xb.busmore.util.BusToast
import com.xb.busmore.util.DividerItemDecoration
import com.xb.busmore.util.net.ftp.InitFtpDate
import kotlinx.android.synthetic.main.activity_init.*
import kotlinx.android.synthetic.main.activity_menu.*
import kotlinx.android.synthetic.main.text_layout.view.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit


class MenuActivity : BaseActivity() {

    private var nowTag: Int = 0  //0主界面控制  1 变站控制  2上下行控制 3上下车机控制
    private val bianStatuTag = 3 //变站模式的条目所对应的位置
    private val diractionTag = 4 //上下行模式的条目所对应的位置
    private val deviceStatusTag = 5 //上下车机模式的条目所对应的位置
    private var bianStatu = PosManager.getInstance().carRunInfo.bianStatu //临时存储的变站模式
    private var diraction = PosManager.getInstance().carRunInfo.diraction //临时存储的变站模式上下行
    private var deviceStatus = PosManager.getInstance().carRunInfo.deviceStatus //临时存储的上下车机

    override fun rootView(): Int {
        return R.layout.activity_menu
    }

    var mune: MutableList<String> = mutableListOf()
    fun initMuneItem() {
        mune.clear()
        mune.add("下载配置文件")
        mune.add("线路选择")
        mune.add("设置车号")
        mune.add("变站模式")
        mune.add("上下行设置")
        mune.add("上下车机设置")
        mune.add("上传记录")
        mune.add("上传所有记录")
    }

    //按键接收
    override fun keyEvent(key: Int) {
        when (key) {
            PosKeyConfig.key_up_left -> {//上
                if (nowTag == 0) {
                    var changeItem = (recycle.adapter as MuneAdapter).selectItem - 1
                    changeItem = if (changeItem < 0) mune.size else changeItem
                    (recycle.adapter as MuneAdapter).selectItem = changeItem
                    (recycle.adapter as MuneAdapter).notifyDataSetChanged()
                    recycle.scrollToPosition(changeItem)
                } else if (nowTag == 1) {
                    bianStatu++
                    mune.set(bianStatuTag, if (bianStatu == 0) "手动变站" else "自动变站")
                    showRcycleChange()
                } else if (nowTag == 2) {
                    diraction++
                    mune.set(diractionTag, if (diraction == 1) "上行" else "下行")
                    showRcycleChange()
                }
            }
            PosKeyConfig.key_up_right//下
            -> {
                if (nowTag == 0) {
                    var changeItem = (recycle.adapter as MuneAdapter).selectItem + 1
                    (recycle.adapter as MuneAdapter).selectItem = if (changeItem >= mune.size) 0 else changeItem
                    (recycle.adapter as MuneAdapter).notifyDataSetChanged()
                    recycle.scrollToPosition(changeItem)
                } else if (nowTag == 1) {
                    bianStatu = Math.abs(bianStatu - 1) % 2
                    mune.set(bianStatuTag, if (bianStatu == 0) "自动变站" else "手动变站")
                    showRcycleChange()
                } else if (nowTag == 2) {
                    diraction = Math.abs(diraction - 1) % 2
                    mune.set(diractionTag, if (diraction == 0) "上行" else "下行")
                    showRcycleChange()
                } else if (nowTag == 2) {
                    deviceStatus = Math.abs(deviceStatus - 1) % 3
                    mune.set(deviceStatusTag, if (deviceStatus == 0) "下车机" else if (deviceStatus == 1) "下车机" else "上下车机")
                    showRcycleChange()
                }
            }
            PosKeyConfig.key_down_left//确认
            -> {
                if (nowTag == 0) {
                    muneActionConfrim((recycle.adapter as MuneAdapter).selectItem)
                } else if (nowTag == 1) {
                    nowTag = 0
                    PosManager.getInstance().updateCarRunInfo(PosManager.getInstance().carRunInfo.setBianStatu(bianStatu))
                    BusToast.showToast(App.getInstance(), "设置当前变站模式\n" + mune.get(bianStatuTag), true)
                    initMuneItem()
                    showRcycleChange()
                } else if (nowTag == 2) {
                    nowTag = 0
                    PosManager.getInstance().updateCarRunInfo(PosManager.getInstance().carRunInfo.setDiraction(diraction))
                    BusToast.showToast(App.getInstance(), "设置当前上下行\n" + mune.get(diractionTag), true)
                    initMuneItem()
                    showRcycleChange()
                } else if (nowTag == 3) {
                    nowTag = 0
                    PosManager.getInstance().updateCarRunInfo(PosManager.getInstance().carRunInfo.setDeviceStatus(deviceStatus))
                    BusToast.showToast(App.getInstance(), "设置当前上下车机\n" + mune.get(deviceStatus), true)
                    initMuneItem()
                    showRcycleChange()
                }
            }
            PosKeyConfig.key_down_right//取消
            -> {
                if (nowTag == 0) {
                    startActivity(HomeActivity::class.java)
                } else if (nowTag == 1) {
                    nowTag = 0
                    showRcycleChange()
                } else if (nowTag == 2) {
                    nowTag = 0
                    showRcycleChange()
                } else if (nowTag == 3) {
                    nowTag = 0
                    showRcycleChange()
                }
            }
        }
    }

    //更新UI
    fun showRcycleChange() {
        (recycle.adapter as MuneAdapter).allItem = mune
        (recycle.adapter as MuneAdapter).notifyDataSetChanged()
    }

    override fun rxMassage(message: RxMessage): Boolean {
        when (message.type) {
            RxMessage.LINE -> {
                BusToast.showToast(App.getInstance(), "参数更新成功", true)
            }
        }
        return super.rxMassage(message)
    }

    //菜单确认操作
    fun muneActionConfrim(position: Int) {
        when (position) {
            0 -> {
                BusToast.showToast(App.getInstance(), "正在更新参数", true)
                //下载线路
                Executors.newScheduledThreadPool(1).schedule(object : Runnable {
                    override fun run() {
                        InitFtpDate.getInstance().downAllLine();
                    }
                }, 1, TimeUnit.SECONDS);
            }
            1 -> {
                startActivity(SelectLineActivity::class.java)
            }
            2 -> {
                startActivity(SetBusNumActivity::class.java)
            }
            3 -> {
                nowTag = 1
                mune.set(bianStatuTag, if (bianStatu == 0) "手动变站" else "自动变站")
                showRcycleChange()
            }

            4 -> {
                nowTag = 2
                mune.set(diractionTag, if (diraction == 1) "上行" else "下行")
                showRcycleChange()
            }
            5 -> {//设置上下车机
                //TODO 分段计费不支持上下车机 暂不处理
            }
            6 -> {

            }
        }

    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initMuneItem()
        recycle.layoutManager = LinearLayoutManager(this, LinearLayoutManager.VERTICAL, false)
        recycle.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recycle.adapter = MuneAdapter(mune)
    }


    class MuneAdapter(mune: MutableList<String>) : RecyclerView.Adapter<MuneItem>() {
        var allItem = mune
        var selectItem: Int = 0
        override fun onCreateViewHolder(p0: ViewGroup, p1: Int): MuneItem {
            var view = LayoutInflater.from(App.getInstance()).inflate(R.layout.text_layout, null)
            view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT)
            return MuneItem(view)
        }

        override fun getItemCount(): Int {
            return allItem.size
        }

        override fun onBindViewHolder(muneItem: MuneItem, position: Int) {
            if (position == selectItem) {
                muneItem.view.text_view.text = allItem.get(position)
                muneItem.view.setBackgroundResource(R.color.gray)
                muneItem.view.text_view.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.colorWhite))
            } else {
                muneItem.view.text_view.text = allItem.get(position)
                muneItem.view.setBackgroundResource(R.color.colorWhite)
                muneItem.view.text_view.setTextColor(ContextCompat.getColor(App.getInstance(), R.color.colorBlack))
            }
        }

    }

    class MuneItem : RecyclerView.ViewHolder {
        var view: View

        constructor(itemView: View) : super(itemView) {
            view = itemView
        }
    }
}
