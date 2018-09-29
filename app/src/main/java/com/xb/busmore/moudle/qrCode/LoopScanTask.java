package com.xb.busmore.moudle.qrCode;

import android.app.Service;
import android.content.Intent;
import android.os.IBinder;
import android.support.annotation.Nullable;
import android.text.TextUtils;
import android.util.Log;

import com.szxb.jni.libszxb;
import com.xb.busmore.base.App;
import com.xb.busmore.base.rx.RxBus;
import com.xb.busmore.base.rx.RxMessage;
import com.xb.busmore.moudle.manage.PosManager;
import com.xb.busmore.moudle.unionpay.dispose.BankQRParse;
import com.xb.busmore.moudle.unionpay.dispose.BankResponse;
import com.xb.busmore.util.BusToast;
import com.xb.busmore.util.Utils;
import com.xb.busmore.util.logcat.LogcatEvent;

import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;


/**
 * 作者: Tangren on 2017/7/31
 * 包名：com.szxb.task
 * 邮箱：996489865@qq.com
 * TODO:轮训扫码
 */

public class LoopScanTask extends Service {
    @Nullable
    @Override
    public IBinder onBind(Intent intent) {
        return null;
    }

    //临时变量存储上次刷卡记录,为了防止重复刷卡
    private String tem = "0";
    //每次扫码后的时间
    private long lastTime = 0;

    private BankResponse response = new BankResponse();

    @Override
    public void onCreate() {
        super.onCreate();
        Executors.newScheduledThreadPool(1).scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                try {
                    //循环扫码
                    byte[] recv = new byte[1024];
                    int barcode = libszxb.getBarcode(recv);
                    if (barcode < 0) {
                        LogcatEvent.getInstance().WritingCom();
                    }
                    Log.e("LoopScanTask", "run(LoopScanTask.java:59) 二维码 barcode=" + barcode);
                    if (barcode > 0) {
                        String result = new String(recv, 0, barcode);
                        Log.e("LoopScanTask", "run(LoopScanTask.java:62) 二维码 原串" + result);
                        if (PosScanManager.isTenQRcode(result) && PosManager.getInstance().getSign()) {//腾讯二维码
                            if (!Utils.checkQR(System.currentTimeMillis(), lastTime)) return;
                            if (TextUtils.equals(result, tem)) {
                                RxBus.getInstance().post(new RxMessage(RxMessage.REFRESH_QR_CODE));
                                lastTime = System.currentTimeMillis();
                                return;
                            }
                            PosScanManager.getInstance().txposScan(result);
                        } else if (PosScanManager.isMyQRcode(result)) {//小兵二维码
                            if (!Utils.checkQR(System.currentTimeMillis(), lastTime)) return;
                            PosScanManager.getInstance().xbposScan(result);
                        } else if (Utils.isAllNum(result)) {
                            if (filterCheck(result)) {
                                return;
                            }
                            BusToast.showToast(App.getInstance(), "扫码成功，正在发起支付", true);

                            BankQRParse qrParse = new BankQRParse();
                            //TODO 银联卡目前无价格信息后续处理
//                            response = qrParse.parseResponse(App.getPosManager().getUnionPayPrice(), result);
//
//                            if (response.getResCode() > 0) {
//                                BusToast.showToast(App.getInstance(), response.getMsg(), true);
//                            } else {
//                                BusToast.showToast(App.getInstance(), response.getMsg() + "[" + response.getResCode() + "]", false);
//                            }
//                            tem = result;
//                            lastTime = System.currentTimeMillis();
                        } else {
                            RxBus.getInstance().post(new RxMessage(RxMessage.QR_ERROR));
                        }
                        tem = result;
                        lastTime = System.currentTimeMillis();
                    }
                } catch (Exception e) {
                    RxBus.getInstance().post(new RxMessage(RxMessage.SOFTWARE_EXCEPTION));
                    e.printStackTrace();
                    Log.e("LoopScanTask", "run(LoopScanTask.java:99)扫码出现异常,异常原因" + e.toString());
                }

            }
        }, 500, 200, TimeUnit.MILLISECONDS);

    }


    private boolean filterCheck(String result) {
        if (!checkQR(System.currentTimeMillis(), lastTime)) {
            return true;
        }
//        if (TextUtils.equals(result, tem)) {
//            RxBus.getInstance().send(new QRScanMessage(new PosRecord(), QRCode.REFRESH_QR_CODE));
//            lastTime = SystemClock.elapsedRealtime();
//            return true;
//        }
        return false;
    }

    /**
     * 两次扫码间隔
     *
     * @param currentTime
     * @param lastTime
     * @return
     */
    public static boolean checkQR(long currentTime, long lastTime) {
        return currentTime - lastTime > 1500;
    }

}
