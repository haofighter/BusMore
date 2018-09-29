package com.xb.busmore.moudle.qrCode.tensent;

import android.text.TextUtils;
import android.util.Log;

import com.tencent.wlxsdk.WlxSdk;
import com.xb.busmore.base.App;
import com.xb.busmore.base.rx.RxBus;
import com.xb.busmore.base.rx.RxMessage;
import com.xb.busmore.dao.manage.DBManager;
import com.xb.busmore.entity.PosRecord;
import com.xb.busmore.moudle.manage.PosManager;
import com.xb.busmore.util.BusToast;


/**
 * 作者: Tangren on 2017-09-08
 * 包名：szxb.com.commonbus.util.report
 * 邮箱：996489865@qq.com
 * TODO:腾讯
 */

public class TenPosReportManager {

    private static TenPosReportManager instance = null;
    private WlxSdk wxSdk;

    private TenPosReportManager() {
        wxSdk = new WlxSdk();
    }

    public static TenPosReportManager getInstance() {
        if (instance == null) {
            synchronized (TenPosReportManager.class) {
                if (instance == null) {
                    instance = new TenPosReportManager();
                }
            }
        }
        return instance;
    }

    public void posScan(String qrcode) {
        if (PosManager.getInstance().getCarRunInfo().equals("1") && PosManager.getInstance().getCarConfig().getGPS() == 0) {
            BusToast.showToast(App.getInstance(), "GPS未启用", false);
            return;
        }

        if (wxSdk == null) wxSdk = new WlxSdk();
        int init = wxSdk.init(qrcode);
        int key_id = wxSdk.get_key_id();
        String open_id = wxSdk.get_open_id();
        String mac_root_id = wxSdk.get_mac_root_id();
        Log.d("TenPosReportManager",
                "posScan(TenPosReportManager.java:37)init=" + init + "key_id=" + key_id + "open_id=" + open_id + "mac_root_id=" + mac_root_id);
        int verify = 0;
        if (!TextUtils.isEmpty(open_id)) {
            if (DBManager.getInstance().filterOpenID(open_id)) {
                BusToast.showToast(App.getInstance(), "禁止频繁刷码", false);
            } else if (DBManager.getInstance().filterBlackName(open_id)) {
                //是黑名单
                RxBus.getInstance().post(new RxMessage(RxMessage.QR_ERROR));
            } else {
                if (init == 0 && key_id > 0) {
//                    //String open_id, String pub_key, int payfee, byte scene, byte scantype, String pos_id, String pos_trx_id, String aes_mac_root
//                    verify = wxSdk.verify(open_id
//                            , PosManager.getPublicKey(String.valueOf(key_id))
//                            , PosManager.getInstance().getMarkedPrice()//1//金额,上线修改为PosManager.getInstance().getMarkedPrice()
//                            , (byte) 1
//                            , (byte) 1
//                            , PosManager.getInstance().getPosSn()
//                            , PosManager.getmchTrxId()
//                            , PosManager.getInstance().getMac(mac_root_id));
//                    String record = wxSdk.get_record();
//                    PosRecord posRecord = new PosRecord();
//                    posRecord.setOpen_id(open_id);
//                    posRecord.setMch_trx_id(PosManager.getInstance().getmchTrxId());
//                    posRecord.setOrder_time(PosManager.getInstance().getOrderTime());
//                    posRecord.setTotal_fee(PosManager.getInstance().getMarkedPrice());//金额，上线修改为posRecord.setTotal_fee(PosManager.getInstance().getMarkedPrice());
//                    posRecord.setPay_fee(PosManager.getInstance().getMarkedPrice() * PosManager.getInstance().getDiscount() / 100);//实际扣款金额，上线修改为posRecord.setTotal_fee(PosManager.getInstance().getPayMarkedPrice());
//                    posRecord.setCity_code(PosManager.getInstance().geCityCode());
//                    posRecord.setOrder_desc(PosManager.getInstance().getChinese_name());
//                    posRecord.setIn_station_id(PosManager.getInstance().getInStationId());
//                    posRecord.setIn_station_name(PosManager.getInstance().getInStationName());
//                    posRecord.setRecord(record);
//                    posRecord.setBus_no(FetchAppConfig.busNo());
//                    posRecord.setBus_line_name(PosManager.getInstance().getLineName());
//                    posRecord.setPos_no(PosManager.getInstance().getDriverNo());
//                    posRecord.setUnitno(PosManager.getInstance().getUnitno());
//                    posRecord.setDriveno(PosManager.getInstance().getDriveno());
//                    PosRequest.getInstance().request(new QRScanMessage(posRecord, verify));
                }
            }
        }
    }


}
