package com.xb.busmore.moudle.card;


import android.app.Activity;
import android.util.Log;

import com.xb.busmore.base.App;
import com.xb.busmore.base.Config;
import com.xb.busmore.dao.db.CardRecordDao;
import com.xb.busmore.dao.manage.DBCore;
import com.xb.busmore.dao.manage.PosManager;
import com.xb.busmore.util.BusToast;
import com.xb.busmore.util.Utils;
import com.xb.busmore.util.net.ftp.InitFtpDate;
import com.xb.busmore.util.net.ftpUtils.FTP;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class UploadCardRecord extends Thread {

    static String startTime = "";//yyyyMMddHHmmss
    static String endTime = "";//yyyyMMddHHmmss
    static boolean isShowToast = false;//是否显示
    List<CardRecord> list = new ArrayList<>();//储存当前上传的记录

    public UploadCardRecord(String startTime, String endTime, boolean isShowToast) {
        this.startTime = startTime;
        this.endTime = endTime;
        this.isShowToast = isShowToast;
    }

    public UploadCardRecord(boolean isShowToast) {
        this("", "", isShowToast);
    }

    @Override
    public void run() {
        try {
            CardRecordDao cardRecordDao = DBCore.getDaoSession().getCardRecordDao();
            if ("".equals(startTime) || "".equals(endTime)) {
                list = cardRecordDao.queryBuilder().where(CardRecordDao.Properties.UpLoad.eq("0")).list();
            } else {
                list = cardRecordDao.queryBuilder().where(CardRecordDao.Properties.DateTime.between(startTime, endTime)).list();
            }
            if (list.size() > 0) {
                String allRecord = "";
                for (CardRecord cardRecord : list) {
                    allRecord += new FTPRecord().bulider(cardRecord).toString() + "\r\n";
                }
                String name = "record" + PosManager.getInstance().getCarConfig().getBusNo() + Utils.getStringDate() + PosManager.getInstance().getCarConfig().getPosSn() + ".txt";
                Utils.byte2File(allRecord.getBytes(), Config.FILE_SAVE_PATH, name);

                InitFtpDate.getInstance().getTestFTP()// 设置测试服务器参数
                        .uploadingSingle(Config.FILE_SAVE_PATH, name, "", new FTP.UploadProgressListener() {
                            @Override
                            public void onUploadProgress(String currentStep, long uploadSize, File file) {
                                if (currentStep == FTP.FTP_UPLOAD_SUCCESS) {
                                    for (int i = 0; i < list.size(); i++) {
                                        CardRecord cardRecord = list.get(i).setUpLoad("1");
                                        list.set(i, cardRecord);
                                    }
                                    PosManager.getInstance().updateRecord(list);
                                    Log.i("MI  info", "run(UploadRecord.java)71)  记录上传成功" + list.size());
                                } else {
                                    BusToast.showToast(App.getInstance(), "记录上传失败", false);
                                    Log.i("MI  info", "run(UploadRecord.java)71)  记录上传失败了");
                                }
                            }
                        });
            } else {
                if (isShowToast) {
                    BusToast.showToast(App.getInstance(), "记录已全部上传", false);
                }
                Log.i("MI  info", "run(UploadRecord.java)71)  未查询到未上传的记录");
            }
        } catch (Exception e) {
            Log.i("MI  info", "run(UploadRecord.java)71) 记录上传出错 " + e.getMessage());
        }
        super.run();
    }

}
