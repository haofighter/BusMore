package com.xb.busmore.test

import android.os.Environment
import android.util.Log
import com.xb.busmore.dao.manage.PosManager
import com.xb.busmore.moudle.card.CardRecord
import com.xb.busmore.util.Utils
import com.xb.busmore.util.net.ftpUtils.FTP
import java.io.File
import java.util.concurrent.Executors
import java.util.concurrent.TimeUnit

class test {
    var num = 0
    fun test() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(object : Runnable {
            override fun run() {
                Log.i("tag", "文件上传")
                var ftp = FTP().setHostName("192.144.184.104").setServerPort(26).setUserName("ftp_test").setPassword("ftp1234!@#\$")
                var name = "record" + System.currentTimeMillis() + ".txt"
                var code = ""
                var list: MutableList<String> = mutableListOf()
                var rand = Math.random() * 100
                var id = 0
                while (id < rand) {
                    var cardRecord = CardRecord()
                    num++
                    id++
                    cardRecord.id = num.toLong()
                    list.add(cardRecord.toString())
                    Log.i("tag", "上传的数据" + num)
                }
                for (item: String in list) {
                    code += item + "," + "\n"
                }

                Utils.byte2File(code.toByteArray(), Environment.getExternalStorageDirectory().toString() + "/", name)
                val file = File(Environment.getExternalStorageDirectory().toString() + "/" + name)
                ftp.uploadingSingle(Environment.getExternalStorageDirectory().toString() + "/", name, "", object : FTP.UploadProgressListener {
                    override fun onUploadProgress(currentStep: String?, uploadSize: Long, file: File?) {
                        if (currentStep == FTP.FTP_UPLOAD_SUCCESS) {
                            Log.i("tag", "FTP上传成功")
                        } else {
                            Log.i("tag", "FTP上传失败")
                        }
                    }
                })
            }
        }, 1, 10, TimeUnit.SECONDS)
    }


    var index = 0
    var tag = ""
    fun testRecord() {
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate({
            Log.i("记录", "测试开始增加记录")
            var record = mutableListOf<CardRecord>()
            var recordNum = (Math.random() * 20).toInt()
            for (i in 1..recordNum) {
                index++
                for (i in 1..16) {
                    if (i < 16 - index.toString().length) {
                        tag += "0"
                    }
                }
                tag += index
                var record = CardRecord().setCardNumber(tag).setUpLoad("0")
                Log.i("记录", "测试增加记录" + tag)
                PosManager.getInstance().insertRecord(record)
                tag = ""
            }
            Log.i("记录", "测试增加记录" + recordNum + "条")
        }, 1, 10, TimeUnit.SECONDS)
    }
}