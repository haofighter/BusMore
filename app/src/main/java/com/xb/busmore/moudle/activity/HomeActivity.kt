package com.xb.busmore.moudle.activity

import android.content.Context
import android.os.Bundle
import android.telephony.PhoneStateListener
import android.telephony.SignalStrength
import android.telephony.TelephonyManager
import com.xb.busmore.R
import com.xb.busmore.base.App
import com.xb.busmore.base.BackCall
import com.xb.busmore.base.BaseActivity
import com.xb.busmore.base.rx.RxMessage
import com.xb.busmore.dao.manage.DBManager
import com.xb.busmore.moudle.manage.PosManager
import com.xb.busmore.util.Utils
import com.xb.busmore.util.task.TimeTask
import kotlinx.android.synthetic.main.activity_home.*

class HomeActivity : BaseActivity(), BackCall {
    //填充主界面
    override fun rootView(): Int {
        return R.layout.activity_home
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        var telephonyManager = this.getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
        telephonyManager.listen(SignalListener(), PhoneStateListener.LISTEN_SIGNAL_STRENGTH or PhoneStateListener.LISTEN_SIGNAL_STRENGTHS)
    }

    override fun onResume() {
        super.onResume()
        timeTask.start()
    }


    //backCall的回调方法
    override fun call(o: Any?) {
        runOnUiThread {
            time.text = o as String
            refreshView()
        }
    }

    //计时器任务
    var timeTask: TimeTask = TimeTask(this)


    //按键接收
    override fun keyEvent(key: Int) {

    }

    //接收Rx的事件
    override fun rxMassage(message: RxMessage): Boolean {
        when (message.type) {
            RxMessage.KEY_EVENT -> keyEvent(message.data as Int)
        }
        return true
    }


    //用于界面刷新
    fun refreshView() {
        version_code.text = "[" + App.getInstance().pakageVersion + "]"
        main_back.setBackgroundResource(App.backGround)
        var carRunInfo = PosManager.getInstance().carRunInfo
        var carConfig = PosManager.getInstance().carConfig
        var useConfig = PosManager.getInstance().useConfig
        diraction_status.text = "变站模式:" + if (carRunInfo.bianStatu == 0) "自动变站" else "手动变站"
        gps_status.text = "GPS信号:" + carConfig.gps
        device_status.text = "机具状态：" + if (carRunInfo.deviceStatus == 0) "上下车机" else if (carRunInfo.deviceStatus == 1) "上车机" else "下车机"
        car_config.text = "银联商户号:" + carConfig.unionTraNo + "   银联终端号:" + carConfig.unionPos + "    车辆号:" + carConfig.busNo + "   \nSN号:" + carConfig.posSn
        line_info.text = useConfig.line + "     " + useConfig.line_chinese_name
        station_info.text = DBManager.getInstance().nowStation.name + "   " + useConfig.station
        ticket_info.text = "票价:" + Utils.fen2Yuan(DBManager.getInstance().nowSigleTicketPrice.price)
    }

    //手机信号监听
    internal inner class SignalListener : PhoneStateListener() {
        override fun onSignalStrengthsChanged(signalStrength: SignalStrength) {
            super.onSignalStrengthsChanged(signalStrength)
            var telephonyManager: TelephonyManager = getSystemService(Context.TELEPHONY_SERVICE) as TelephonyManager
            val signalInfo = signalStrength.toString()
            val params = signalInfo.split(" ".toRegex()).dropLastWhile { it.isEmpty() }.toTypedArray()
            if (telephonyManager.getNetworkType() == TelephonyManager.NETWORK_TYPE_LTE) {
                //4G网络 最佳范围   >-90dBm 越大越好
                val Itedbm = Integer.parseInt(params[9])
                net_status.setText("网络强度：$Itedbm")
            } else {
                net_status.setText("网络强度：" + "0")
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        timeTask.setFlag(false)
    }
}
