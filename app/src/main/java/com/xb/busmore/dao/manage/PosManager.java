package com.xb.busmore.dao.manage;

import android.util.Log;

import com.xb.busmore.dao.db.MacKeyEntityDao;
import com.xb.busmore.dao.db.PublicKeyEntityDao;
import com.xb.busmore.entity.AllLineInfo;
import com.xb.busmore.entity.LineInfoDown;
import com.xb.busmore.entity.LineInfoUp;
import com.xb.busmore.entity.LinePriceInfo;
import com.xb.busmore.entity.LineStation;
import com.xb.busmore.entity.car.CarConfig;
import com.xb.busmore.entity.car.CarRunInfo;
import com.xb.busmore.entity.car.UseConfig;
import com.xb.busmore.moudle.card.CardRecord;
import com.xb.busmore.moudle.qrCode.entity.FTPEntity;
import com.xb.busmore.moudle.qrCode.entity.MacKeyEntity;
import com.xb.busmore.moudle.qrCode.entity.PublicKeyEntity;
import com.xb.busmore.util.DateUtil;
import com.xb.busmore.util.Utils;
import com.xb.busmore.util.sp.CommonSharedPreferences;

import java.util.List;


/**
 * 用于获取卡机中的各种中参数
 */
public class PosManager {

    static PosManager instance;

    public static PosManager getInstance() {
        if (instance == null)
            instance = new PosManager();
        return instance;
    }

    private PosManager() {
    }

    //获取到银联支付的价格
    public static int getUnionPayPrice() {
        //TODO  获取银联的刷卡价格
        return 1;
    }

    //设置设备Sn号
    public static void setPosSn(String posSn) {
        DBManager.getInstance().updateCarConfig(DBManager.getInstance().getCarConfig().setPosSn(posSn).setTime(System.currentTimeMillis()));
        Log.w("setting", "SN号设置成功:" + posSn);
    }

    //获取设备Sn号
    public static void getPosSn() {
        DBManager.getInstance().getCarConfig().getPosSn();
    }

    public static String getmchTrxId() {
        int txid = 0;
        if (txid == 999999) {
            txid = 0;
        }
        txid++;
        setWxTx_Id(txid);
        String key = String.format("%06d", txid);
        String tx_id = DateUtil.getCurrentDate_1() + key + Utils.Random(4);
        return tx_id;
    }

    public static void setWxTx_Id(int wxTx_Id) {
        //TODO 设置微信交易序号
    }

    //设置备注信息
    public static void setMark(String mark) {
        DBManager.getInstance().setMark(mark);
    }


    //获取到用户参数
    public UseConfig getUseConfig() {
        return DBManager.getInstance().getUseConfig();
    }

    //获取到用户参数
    public UseConfig setUseConfig(UseConfig useConfig) {
        return DBManager.getInstance().updateUseConfig(useConfig);
    }


    //获取到设备参数
    public CarConfig getCarConfig() {
        return DBManager.getInstance().getCarConfig();
    }

    //更新设备参数
    public void setCarConfig(CarConfig carConfig) {
        DBManager.getInstance().updateCarConfig(carConfig);
    }

    //获取运行状态参数
    public CarRunInfo getCarRunInfo() {
        return DBManager.getInstance().getCarRunInfo();
    }

    //更新运行状态
    public CarRunInfo updateCarRunInfo(CarRunInfo carRunInfo) {
        return DBManager.getInstance().updateCarRunInfo(carRunInfo);
    }

    //设置GPS信号
    public void setGpsStatus(int gpsStatus) {
        DBManager.getInstance().setGpsStatus(gpsStatus);
    }

    //获取司机的上下班状态
    public boolean getSign() {
        return DBManager.getInstance().getCarRunInfo().getSign();
    }

    //设置司机的上下班状态
    public void setSign(boolean sign) {
        CarRunInfo carRunInfo = DBManager.getInstance().getCarRunInfo();
        carRunInfo.setSign(sign);
        updateCarRunInfo(carRunInfo);
    }

    /***
     * 获取公钥
     *
     * @param keyId 公钥ID
     * @return
     */
    public static String getPublicKey(String keyId) {
        PublicKeyEntityDao dao = DBCore.getDaoSession().getPublicKeyEntityDao();
        PublicKeyEntity unique = dao.queryBuilder().where(PublicKeyEntityDao.Properties.Key_id.eq(keyId)).build().unique();
        if (unique != null)
            return unique.getPubkey();
        return "";
    }


    /**
     * 获取mac秘钥
     *
     * @param keyId
     * @return
     */
    public static String getMac(String keyId) {
        MacKeyEntityDao dao = DBCore.getDaoSession().getMacKeyEntityDao();
        MacKeyEntity unique = dao.queryBuilder().where(MacKeyEntityDao.Properties.Key_id.eq(keyId)).unique();
        if (unique != null)
            return unique.getPubkey();
        return "";

    }

    //设置车辆号
    public void setBusNo(String busNo) {
        DBManager.getInstance().setBusNo(busNo);
    }

    //设置方向
    public void setDiraction(int diraction) {
        DBManager.getInstance().updateDiraction(diraction);
    }


    //设置变站模式   1 手动变站  0自动变站
    public void setBianStatu(int bianStatu) {
        DBManager.getInstance().updateBianStatu(bianStatu);
    }

    //设置卡机的上下车机状态 1上车机器 0下车机器 2即上又下机器 第一次默认机器为上车机
    public void setDeviceStatus(int deviceStatus) {
        DBManager.getInstance().updatedDeviceStatus(deviceStatus);
    }

    //查询线路是否存在
    public boolean checkLine(String arnt, String line) {
        List<AllLineInfo> allLines = DBManager.getInstance().checkLine(arnt, line);
        if (allLines.size() > 0) {
            return true;
        } else {
            return false;
        }
    }

    //获取到分段计费站点的票价
    public static int getPrice(int i, int diraction) {
        return DBManager.getInstance().getPrice(i, diraction);
    }

    //设置当前的站点信息
    public void setNowStation(int station) {
        DBManager.getInstance().setNowStation(station);
    }

    //获取当前站点信息
    public LineStation getNowStation() {
        return DBManager.getInstance().getNowStation();
    }

    //获取当前站点的票价信息
    public LinePriceInfo getNowSigleTicketPrice() {
        return DBManager.getInstance().getNowSigleTicketPrice();
    }

    //获取当前方向的站点
    public List<LineStation> getStationList() {
        return DBManager.getInstance().getStationList();
    }

    //检查openid 是否存在且保存时间小于当前时间6S
    public boolean filterOpenID(String open_id) {
        return DBManager.getInstance().filterOpenID(open_id);
    }

    // 是否属于黑名单
    public boolean filterBlackName(String open_id) {
        return DBManager.getInstance().filterBlackName(open_id);
    }

    //获取FTP配置信息
    public FTPEntity getFTP() {
        return DBManager.getInstance().getFTP();
    }

    //更新线路信息
    public void updateLineInfo(LineInfoUp lineInfoup) {
        DBManager.getInstance().updateLineInfo(lineInfoup);
    }

    //获取所有线路信息
    public List<AllLineInfo> getAllLineInfo() {
        return DBManager.getInstance().getLineInfo();
    }

    //更新票价
    public void updateStationInfo(LineInfoDown lineInfoDown, String line) {
        DBManager.getInstance().updatePrice(lineInfoDown, line);
        DBManager.getInstance().updateStation(lineInfoDown, line);
    }


    //插入新记录
    public void insertRecord(CardRecord cardRecord) {
        DBManager.getInstance().insert(cardRecord);
    }

    //更新记录 主要用于上传
    public void updateRecord(List<CardRecord> cardRecord) {
        DBManager.getInstance().updateRecord(cardRecord);
    }

}
