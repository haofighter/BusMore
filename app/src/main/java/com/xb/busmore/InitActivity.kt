package com.xb.busmore

import android.content.Intent
import android.support.v7.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import com.xb.busmore.base.App
import com.xb.busmore.base.BaseActivity
import com.xb.busmore.base.rx.RxBus
import com.xb.busmore.base.rx.RxMessage
import com.xb.busmore.moudle.activity.HomeActivity
import com.xb.busmore.moudle.key.LoopKeyEvent
import com.xb.busmore.moudle.card.LoopCardThread
import com.xb.busmore.moudle.init.InitK21
import com.xb.busmore.moudle.qrCode.LoopScanTask
import com.xb.busmore.util.net.ftp.InitFtpDate
import com.xb.busmore.util.ThreadScheduledExecutorUtil
import com.xb.busmore.util.sp.CommonSharedPreferences
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_init.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class InitActivity : BaseActivity() {
    override fun rootView(): Int {
        return R.layout.activity_init
    }

    override fun rxMassage(message: RxMessage): Boolean {
        when (message.type) {
            RxMessage.KEY_EVENT -> keyEvent(message.data as Int)
            RxMessage.LINE -> {
                runOnUiThread { info.append("线路更新成功\n") }
                true
            }
            else -> false
        }
        return true
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info.append("初始化中\n")
        initAllLine()
        initK21()
    }


    override fun keyEvent(pair: Int) {

    }


    //启动线程线程启动
    fun start() {
        runOnUiThread {
            CommonSharedPreferences.put("v", App.getInstance().getPakageVersion())
            ThreadScheduledExecutorUtil.getInstance().service
                    .scheduleAtFixedRate(LoopCardThread(), 1000, 1000, TimeUnit.MILLISECONDS)
            runOnUiThread { info.append("刷卡已启动\n") }

            ThreadScheduledExecutorUtil.getInstance().service
                    .scheduleAtFixedRate(LoopKeyEvent(), 1000, 200, TimeUnit.MILLISECONDS)
            runOnUiThread { info.append("按键初始化成功\n") }

            startService(Intent(this, LoopScanTask::class.java))
            runOnUiThread { info.append("扫码已启动\n") }

            goToMainActivity()
        }
    }


    //K21更新
    fun initK21() {
        var v: String = CommonSharedPreferences.get("v", "0") as String;
        if (App.getInstance().getPakageVersion().equals(v)) {
            runOnUiThread { info.append("k21已更新\n") }
            start()
            return
        }
        InitK21.intit({
            if (it as Int == 0) {
                runOnUiThread { info.append("k21更新成功\n") }
                start()
            } else {
                initK21()
            }
        })
    }

    fun initAllLine() {
        //下载线路
        Executors.newScheduledThreadPool(1).schedule(object : Runnable {
            override fun run() {
                InitFtpDate.getInstance().downAllLine();
            }
        }, 1, TimeUnit.SECONDS);
    }


    fun goToMainActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
