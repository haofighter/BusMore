package com.xb.busmore

import android.content.Intent
import android.os.Bundle
import android.os.Environment
import com.google.gson.Gson
import com.xb.busmore.base.App
import com.xb.busmore.base.BaseActivity
import com.xb.busmore.base.rx.RxMessage
import com.xb.busmore.dao.manage.PosManager
import com.xb.busmore.entity.OldConfig
import com.xb.busmore.moudle.activity.HomeActivity
import com.xb.busmore.moudle.card.UploadCardRecord
import com.xb.busmore.moudle.key.LoopKeyEvent
import com.xb.busmore.moudle.card.single.LoopCardThread_SingleZB
import com.xb.busmore.moudle.init.InitK21
import com.xb.busmore.moudle.qrCode.LoopScanTask
import com.xb.busmore.util.net.ftp.InitFtpDate
import com.xb.busmore.util.ThreadScheduledExecutorUtil
import com.xb.busmore.util.Utils
import com.xb.busmore.util.sp.CommonSharedPreferences
import kotlinx.android.synthetic.main.activity_init.*
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class InitActivity : BaseActivity() {
    override fun rxMassage(message: RxMessage): Boolean {
        when (message.type) {
            RxMessage.LINE -> {
                runOnUiThread { info.append("线路更新成功\n") }
                true
            }
            else -> false
        }
        return super.rxMassage(message)
    }

    override fun rootView(): Int {
        return R.layout.activity_init
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        info.append("初始化中\n")
        initAllLine()
        getOldConfig()
        initK21()
    }


    override fun keyEvent(pair: Int) {
    }


    //启动线程线程启动
    fun start() {

        CommonSharedPreferences.put("v", App.getInstance().getPakageVersion())
        ThreadScheduledExecutorUtil.getInstance().service
                .scheduleAtFixedRate(LoopCardThread_SingleZB(), 1000, 300, TimeUnit.MILLISECONDS)
        runOnUiThread { info.append("刷卡已启动\n") }

        ThreadScheduledExecutorUtil.getInstance().service
                .scheduleAtFixedRate(LoopKeyEvent(), 1000, 200, TimeUnit.MILLISECONDS)
        runOnUiThread { info.append("按键初始化成功\n") }

        startService(Intent(this, LoopScanTask::class.java))
        runOnUiThread { info.append("扫码已启动\n") }

        ThreadScheduledExecutorUtil.getInstance().service
                .scheduleAtFixedRate(UploadCardRecord(false), 2, 10, TimeUnit.MINUTES)
        runOnUiThread { info.append("定时上传刷卡记录已开启\n") }

        Executors.newScheduledThreadPool(1).schedule({ goToMainActivity() }, 2, TimeUnit.SECONDS)

    }


    //K21更新
    private fun initK21() {
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


    private fun goToMainActivity() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }


    private fun getOldConfig() {
        var old = Gson().fromJson<OldConfig>(Utils.readSaveFile(Environment.getExternalStorageDirectory().toString() + "/config.txt"), OldConfig::class.java)
        if (PosManager.getInstance().useConfig.line.equals("000000")) {
            Executors.newScheduledThreadPool(1).schedule({
                if (Utils.StringIsEmpty(old.line)) {
                    if (!old.lineName.equals("0000"))
                        InitFtpDate.getInstance().downloadDetailLine(old.lineName)

                } else {
                    if (!old.line.equals("0000"))
                        InitFtpDate.getInstance().downloadDetailLine(old.line)
                }
                val string = Gson().toJson(PosManager.getInstance().carConfig)
                Utils.byte2File(string.toByteArray(), Environment.getExternalStorageDirectory().toString(), "config.txt")
            }, 0, TimeUnit.SECONDS)
            PosManager.getInstance().carConfig = PosManager.getInstance().carConfig.setBusNo(old.busNo)
        }
    }


}
