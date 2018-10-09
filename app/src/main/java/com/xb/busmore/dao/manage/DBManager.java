package com.xb.busmore.dao.manage;


import android.text.TextUtils;
import android.util.Log;

import com.xb.busmore.BuildConfig;
import com.xb.busmore.dao.db.AllLineInfoDao;
import com.xb.busmore.dao.db.BlackListEntityDao;
import com.xb.busmore.dao.db.CarConfigDao;
import com.xb.busmore.dao.db.CarRunInfoDao;
import com.xb.busmore.dao.db.CardRecordDao;
import com.xb.busmore.dao.db.FTPEntityDao;
import com.xb.busmore.dao.db.LineInfoUpDao;
import com.xb.busmore.dao.db.LinePriceInfoDao;
import com.xb.busmore.dao.db.LineStationDao;
import com.xb.busmore.dao.db.ScanInfoEntityDao;
import com.xb.busmore.dao.db.UseConfigDao;
import com.xb.busmore.entity.AllLine;
import com.xb.busmore.entity.AllLineInfo;
import com.xb.busmore.entity.BlackListEntity;
import com.xb.busmore.entity.ScanInfoEntity;
import com.xb.busmore.entity.car.CarConfig;
import com.xb.busmore.entity.LineInfoDown;
import com.xb.busmore.entity.LineInfoUp;
import com.xb.busmore.entity.LinePriceInfo;
import com.xb.busmore.entity.LineStation;
import com.xb.busmore.entity.car.CarRunInfo;
import com.xb.busmore.entity.car.UseConfig;
import com.xb.busmore.moudle.card.CardRecord;
import com.xb.busmore.moudle.qrCode.entity.FTPEntity;
import com.xb.busmore.util.DateUtil;

import org.greenrobot.greendao.query.Query;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Evergarden on 2018/1/13.
 */

public class DBManager {
    CarConfigDao carConfigDao;
    UseConfigDao useConfigDao;
    CarRunInfoDao carRunInfoDao;
    AllLineInfoDao allLineInfoDao;
    LineInfoUpDao lineInfoDao;
    LineStationDao lineStationDao;
    LinePriceInfoDao linePriceInfoDao;
    ScanInfoEntityDao scanInfoEntityDao;
    BlackListEntityDao blackListEntityDao;
    FTPEntityDao ftpEntityDao;
    CardRecordDao cardRecordDao;

    private DBManager() {
        carConfigDao = DBCore.getDaoSession().getCarConfigDao();
        useConfigDao = DBCore.getDaoSession().getUseConfigDao();
        carRunInfoDao = DBCore.getDaoSession().getCarRunInfoDao();
        allLineInfoDao = DBCore.getDaoSession().getAllLineInfoDao();
        lineInfoDao = DBCore.getDaoSession().getLineInfoUpDao();
        lineStationDao = DBCore.getDaoSession().getLineStationDao();
        linePriceInfoDao = DBCore.getDaoSession().getLinePriceInfoDao();
        scanInfoEntityDao = DBCore.getDaoSession().getScanInfoEntityDao();
        blackListEntityDao = DBCore.getDaoSession().getBlackListEntityDao();
        ftpEntityDao = DBCore.getDaoSession().getFTPEntityDao();
        cardRecordDao = DBCore.getDaoSession().getCardRecordDao();
    }

    private static DBManager dbManager = new DBManager();

    static DBManager getInstance() {
        if (dbManager == null) {
            dbManager = new DBManager();
        }
        return dbManager;
    }

    /**
     * 获取到配置参数
     *
     * @return
     */
    CarConfig getCarConfig() {
        CarConfig carConfig = carConfigDao.queryBuilder().limit(1).unique();
        if (carConfig == null) {
            String mark = "";
            if (BuildConfig.FLAVOR.equals("ziboduofenduan")) {
                mark = "淄博分段计费";
            } else if (BuildConfig.FLAVOR.equals("rongcheng")) {
                mark = "荣成多票";
            } else if (BuildConfig.FLAVOR.equals("yiyuan")) {
                mark = "沂源多票";
            } else if (BuildConfig.FLAVOR.equals("ziboduo")) {
                mark = "淄博多票";
            } else {
                mark = "公交";
            }

            carConfig = new CarConfig(0l, 0, System.currentTimeMillis(), "未设置", "未设置", "未设置", "", mark, "000000");
        }
        return carConfig;
    }

    /**
     * 更新配置参数
     *
     * @param carConfig
     */
    void updateCarConfig(CarConfig carConfig) {
        carConfigDao.insertOrReplace(carConfig);
    }

    /**
     * 获取到运行参数
     *
     * @return
     */
    UseConfig getUseConfig() {
        UseConfig useConfig = useConfigDao.queryBuilder().limit(1).unique();
        if (useConfig == null) {
            useConfig = new UseConfig(0l, 1, "请选择线路", "000000", "0000000", 0);
        }
        return useConfig;
    }


    /**
     * 更新运行配置参数
     *
     * @param useConfig
     */
    UseConfig updateUseConfig(UseConfig useConfig) {
        useConfigDao.insertOrReplace(useConfig);
        return useConfig;
    }


    CarRunInfo getCarRunInfo() {
        CarRunInfo carRunInfo = carRunInfoDao.queryBuilder().limit(1).unique();
        if (carRunInfo == null) {
            carRunInfo = new CarRunInfo(0l, 0, 2, 0, 0, false, "100100100100100100100100100100100100100100100100100100100100");
        }
        return carRunInfo;
    }

    CarRunInfo updateCarRunInfo(CarRunInfo carRunInfo) {
        carRunInfoDao.insertOrReplace(carRunInfo);
        return carRunInfo;
    }


    /**
     * 获取到所有的配置线路
     *
     * @return
     */
    List<AllLineInfo> getLineInfo() {
        return allLineInfoDao.queryBuilder().list();
    }

    /**
     * 获取到所有的配置线路
     *
     * @return
     */
    AllLineInfo getLineInfo(String lineNo) {
        String ant = Integer.parseInt(lineNo.substring(0, 2), 16) + "";
        String line = Integer.parseInt(lineNo.substring(2, 4), 16) + "";
        return allLineInfoDao.queryBuilder().where(AllLineInfoDao.Properties.Acnt.eq(ant), AllLineInfoDao.Properties.Routeno.eq(line)).unique();
    }


    /**
     * 更新所有线路
     *
     * @param allLine
     */
    public static void updateAllLine(List<AllLineInfo> allLine) {
        AllLineInfoDao allLineDao = DBCore.getDaoSession().getAllLineInfoDao();
        allLineDao.deleteAll();
        allLineDao.insertInTx(allLine);
        Log.w("setting", "更新了all   Line");
    }

    /**
     * 更新线路文件
     *
     * @param lineInfo
     */
    void updateLineInfo(LineInfoUp lineInfo) {
        try {
            LineInfoUp lineInfoOld = lineInfoDao.queryBuilder().where(LineInfoUpDao.Properties.Line.eq(lineInfo.getLine())).unique();
            if (lineInfoOld != null) {
                lineInfoDao.delete(lineInfoOld);
            }
        } catch (Exception e) {

        }
        lineInfoDao.insertOrReplace(lineInfo);
        Log.w("setting", "站点上半部分信息更新：  线路：" + lineInfo.getLine());
    }


    /**
     * 更新站点信息
     *
     * @param lineInfo
     * @param line
     */
    void updateStation(LineInfoDown lineInfo, String line) {
        try {
            List<LineStation> lineStations = lineStationDao.queryBuilder().where(LineStationDao.Properties.Line.eq(line)).list();
            if (lineStations.size() != 0) {
                lineStationDao.deleteInTx(lineStations);
            }
        } catch (Exception e) {

        }
        List<LineStation> allStation = new ArrayList<>();
        allStation.addAll(lineInfo.getDown_position());
        allStation.addAll(lineInfo.getUp_position());
        lineStationDao.insertInTx(allStation);
        Log.w("setting", "站点更新：  线路：" + line + "    站点数量：" + allStation.size());
    }

    /**
     * 更新票价信息
     *
     * @param lineInfo
     * @param line
     */
    void updatePrice(LineInfoDown lineInfo, String line) {
        try {
            List<LinePriceInfo> linePrices = linePriceInfoDao.queryBuilder().where(LinePriceInfoDao.Properties.Line.eq(line)).list();
            if (linePrices.size() != 0) {
                linePriceInfoDao.deleteInTx(linePrices);
            }
        } catch (Exception e) {
            Log.e("DBManager", "updatePrice(DBManager.java:105)" + e.getMessage());
        }
        List<LinePriceInfo> prices = new ArrayList<>();
        prices.addAll(lineInfo.getDown_price());
        prices.addAll(lineInfo.getUp_price());
        linePriceInfoDao.insertInTx(prices);

        Log.w("setting", "线路票价更新：线路：" + line);
    }


    /**
     * 设置GPS的信号强度
     *
     * @param gpsStatus
     */
    void setGpsStatus(int gpsStatus) {
        CarConfig carConfig = getCarConfig();
        carConfig.setGPS(gpsStatus);
        carConfig.setTime(System.currentTimeMillis());
        updateCarConfig(carConfig);
        Log.w("setting", "GPS强度更新：" + gpsStatus);
    }

    /**
     * 设置SN号
     *
     * @param posSn
     */
    void setPosSn(String posSn) {
        CarConfig carConfig = getCarConfig();
        carConfig.setPosSn(posSn);
        carConfig.setTime(System.currentTimeMillis());
        updateCarConfig(carConfig);
        Log.w("setting", "SN号设置成功:" + posSn);
    }

    /**
     * 检查openid 是否存在且保存时间小于当前时间6S
     * 即在1分钟内是否有记录
     *
     * @param openID
     * @return >0说明存在则不发起支付
     */
    boolean filterOpenID(String openID) {
        ScanInfoEntity unique = scanInfoEntityDao.
                queryBuilder().limit(1).orderDesc(ScanInfoEntityDao.Properties.Id).build().unique();
        if (unique != null) {
            if (!TextUtils.isEmpty(unique.getRemark_1()))
                if (unique.getRemark_1().equals(openID)) {
                    return DateUtil.getMILLISECOND(DateUtil.getCurrentDate(), unique.getTime()) <= 6;
                }
        }
        return false;
    }

    /**
     * 是否属于黑名单
     *
     * @param openID
     * @return
     */
    boolean filterBlackName(String openID) {
        Query<BlackListEntity> build = blackListEntityDao.queryBuilder().where(BlackListEntityDao.Properties.Open_id.eq(openID),
                BlackListEntityDao.Properties.Time.ge(DateUtil.currentLong())).build();
        BlackListEntity blackEntity = build.unique();
        return blackEntity != null;
    }


    void setBusNo(String busNo) {
        updateCarConfig(getCarConfig().setBusNo(busNo));
    }

    //设置FTP
    void setFTP(FTPEntity ftp) {
        if (ftp == null) {
            ftpEntityDao.insertOrReplace(new FTPEntity(0l, BuildConfig.FTPURI, BuildConfig.FTPPORT, BuildConfig.FTPNAME, BuildConfig.FTPPSW, ""));
        } else {
            ftp.setId(0l);
            ftpEntityDao.insertOrReplace(ftp);
        }
    }

    //获取FTP地址
    FTPEntity getFTP() {
        FTPEntity ftpEntity = ftpEntityDao.queryBuilder().limit(1).unique();
        if (ftpEntity == null) {
            ftpEntity = new FTPEntity(0l, BuildConfig.FTPURI, BuildConfig.FTPPORT, BuildConfig.FTPNAME, BuildConfig.FTPPSW, "");
        }
        return ftpEntity;
    }

    //设置备注信息
    void setMark(String mark) {
        updateCarConfig(getCarConfig().setMark(mark));
    }

    //设置方向 0上行  1下行
    public void updateDiraction(int diraction) {
        updateCarRunInfo(getCarRunInfo().setDiraction(diraction));
    }

    //设置变站模式
    void updateBianStatu(int bianStatu) {
        updateCarRunInfo(getCarRunInfo().setBianStatu(bianStatu));
    }

    //设置卡机上下机状态
    void updatedDeviceStatus(int deviceStatus) {
        updateCarRunInfo(getCarRunInfo().setDeviceStatus(deviceStatus));
    }

    //查询线路是否存在
    List<AllLineInfo> checkLine(String arnt, String line) {
        AllLineInfoDao allLineInfoDao = DBCore.getDaoSession().getAllLineInfoDao();
        return allLineInfoDao.queryBuilder().where(AllLineInfoDao.Properties.Acnt.eq(arnt), AllLineInfoDao.Properties.Routeno.eq(line)).list();
    }

    //获取到票价
    int getPrice(int station, int diraction) {
        LinePriceInfo linePriceInfo = linePriceInfoDao.queryBuilder().where(LinePriceInfoDao.Properties.Diraction.eq(diraction), LinePriceInfoDao.Properties.No.eq(station)).limit(1).unique();
        if (linePriceInfo == null) {
            return 0;
        }
        return linePriceInfo.getPrice();
    }


    //获取到当前站点信息  如果不存在则设置一个默认值 防止空指针问题
    LineStation getNowStation() {
        CarRunInfo carRunInfo = getCarRunInfo();
        UseConfig useConfig = getUseConfig();
        LineStation lineStation = lineStationDao.queryBuilder().where(LineStationDao.Properties.Diraction.eq(carRunInfo.getDiraction()), LineStationDao.Properties.No.eq(useConfig.getStation())).limit(1).unique();
        if (lineStation == null) {
            return new LineStation(0l, 0, "", "未设置", 0, 0, 0, System.currentTimeMillis(), 0);
        }
        return lineStation;
    }

    //获取到单个站点一个票价的票价(分段计费)
    LinePriceInfo getNowSigleTicketPrice() {
        CarRunInfo carRunInfo = getCarRunInfo();
        UseConfig useConfig = getUseConfig();
        LinePriceInfo linePriceInfo = linePriceInfoDao.queryBuilder().where(LinePriceInfoDao.Properties.Diraction.eq(carRunInfo.getDiraction()), LinePriceInfoDao.Properties.No.eq(useConfig.getStation())).limit(1).unique();
        if (linePriceInfo == null) {
            return new LinePriceInfo(0l, 0, 0, "", 0, System.currentTimeMillis());
        }
        return linePriceInfo;
    }


    //通过方向获取到所有站点
    List<LineStation> getStationList() {
        CarRunInfo carRunInfo = getCarRunInfo();
        return lineStationDao.queryBuilder().where(LineStationDao.Properties.Diraction.eq(carRunInfo.getDiraction())).list();
    }

    //设置当前的站点
    void setNowStation(int station) {
        useConfigDao.update(getUseConfig().setStation(station));
    }

    //增加刷卡记录
    void insert(CardRecord cardRecord) {
        cardRecordDao.insertOrReplace(cardRecord);
    }

    //更新刷卡记录  主要用于记录上传后更新状态
    void updateRecord(List<CardRecord> cardRecord) {
        cardRecordDao.insertOrReplaceInTx(cardRecord);
    }
}