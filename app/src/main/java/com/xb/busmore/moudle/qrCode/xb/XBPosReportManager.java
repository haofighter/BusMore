package com.xb.busmore.moudle.qrCode.xb;

import android.util.Log;

import com.alibaba.fastjson.JSONObject;
import com.google.gson.Gson;
import com.xb.busmore.base.App;
import com.xb.busmore.base.rx.RxBus;
import com.xb.busmore.base.rx.RxMessage;
import com.xb.busmore.dao.manage.PosManager;
import com.xb.busmore.moudle.qrCode.QRCode;
import com.xb.busmore.moudle.qrCode.entity.FTPEntity;
import com.xb.busmore.moudle.qrCode.entity.MCHEntity;
import com.xb.busmore.moudle.qrCode.entity.XbScanInfo;
import com.xb.busmore.util.BusToast;
import com.xb.busmore.util.DateUtil;
import com.xb.busmore.util.Utils;
import com.xb.busmore.util.net.ftp.InitFtpDate;
import com.xb.busmore.util.sound.SoundPoolUtil;

/**
 * 作者: Tangren on 2017-09-08
 * 包名：szxb.com.commonbus.util.report
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class XBPosReportManager {

    private static XBPosReportManager instance = null;

    private XBPosReportManager() {

    }

    public static XBPosReportManager getInstance() {
        if (instance == null) {
            synchronized (XBPosReportManager.class) {
                if (instance == null) {
                    instance = new XBPosReportManager();
                }
            }
        }
        return instance;
    }


    public void posScan(int codeType, String qrcode) {
        try {
            switch (codeType) {
                case QRCode.CONFIG_CODE_FTP:
                    String resultFTP = qrcode.substring(8, qrcode.length());
                    FTPEntity ftpEntity = new Gson().fromJson(resultFTP, FTPEntity.class);
                    if (ftpEntity != null && Utils.verify(ftpEntity.getK())) {
                        if (Utils.isIP(ftpEntity.getI())) {
                            InitFtpDate.getInstance().resetFtp(ftpEntity);
                            BusToast.showToast(App.getInstance(), "FTP参数设置成功[114]\n重启生效", true);
                        } else {
                            BusToast.showToast(App.getInstance(), "IP地址无效[404]", true);
                        }
                    }
                    break;

                case QRCode.CONFIG_CODE_MCH:
                    String resultMCH = qrcode.substring(8, qrcode.length());
                    MCHEntity mchEntity = new Gson().fromJson(resultMCH, MCHEntity.class);
                    if (mchEntity != null && Utils.verify(mchEntity.getK())) {
                        Log.w("jsonObject", mchEntity.toString());
                        PosManager.setMark(mchEntity.getO());
                        BusToast.showToast(App.getInstance(), "备注修改成功[115]", true);
                    }
                    break;
                case QRCode.TIME:
                    BusToast.showToast(App.getInstance(), "校准时间", true);
                    JSONObject resultTime = JSONObject.parseObject(qrcode.substring(9, qrcode.length()));
                    String k = resultTime.getString("k");
                    String date = resultTime.getString("t");
                    if (date != null && date.length() > 0 && Utils.verify(k)) {
                        DateUtil.setTime(date);
                    } else {
                        BusToast.showToast(App.getInstance(), "参数不符", false);
                    }
                    break;
                case QRCode.UpAndDown:
                    JSONObject UpAndDown = JSONObject.parseObject(qrcode.substring(8, qrcode.length()));
                    String t = UpAndDown.getString("t");
                    String keys = UpAndDown.getString("k");
                    Log.w("LoopScan", Utils.verify(keys) + t);
                    if (t != null && t.length() > 0 && Utils.verify(keys)) {
                        if (t.equals("0")) {
                            SoundPoolUtil.play(22);
                            PosManager.getInstance().setDiraction(0);
                            BusToast.showToast(App.getInstance(), "设置上行方向成功", true);
                        } else if (t.equals("1")) {
                            SoundPoolUtil.play(22);
//                            UseChangeSharedPreferences.getInstance().used("put", "Direction", "1");
                            PosManager.getInstance().setDiraction(0);
                            BusToast.showToast(App.getInstance(), "设置下行方向成功", true);
                        }
                        RxBus.getInstance().post(new RxMessage(RxMessage.SettingFangxiang, t));
                    }
                    break;

                case QRCode.CONFIG_CODE_LINE:
                    XbScanInfo xbScanInfo = new Gson().fromJson(qrcode.substring(4, qrcode.length()), XbScanInfo.class);
                    if (xbScanInfo.getL() != null && xbScanInfo.getL().length() > 0 && Utils.verify(xbScanInfo.getK())) {
                        Log.e("XBPosReportManager", "posScan(XBPosReportManager.java:113)" + xbScanInfo.toString());
                        PosManager.getInstance().setBianStatu(xbScanInfo.getT2());
                        PosManager.getInstance().setDeviceStatus(xbScanInfo.getT());
                        PosManager.getInstance().setBusNo(xbScanInfo.getN());

                        String ant = Integer.parseInt(xbScanInfo.getL().substring(0, 2), 16) + "";
                        String line = Integer.parseInt(xbScanInfo.getL().substring(2, 4), 16) + "";

                        if (PosManager.getInstance().checkLine(ant, line)) {
                            BusToast.showToast(App.getInstance(), "", true);
                            InitFtpDate.getInstance().downloadDetailLine(xbScanInfo.getL());
                        } else {
                            BusToast.showToast(App.getInstance(), "未查询到此线路", true);
                        }
                    }
                    break;
                default:
                    if (!Utils.checkNetStatus()) {
                        BusToast.showToast(App.getInstance(), "请检查网络", false);
                        return;
                    } else {
                        BusToast.showToast(App.getInstance(), "二维码有误", false);
                    }
                    break;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}