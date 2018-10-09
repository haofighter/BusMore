package com.xb.busmore.util.net.ftp;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.xb.busmore.base.App;
import com.xb.busmore.base.rx.RxBus;
import com.xb.busmore.base.rx.RxMessage;
import com.xb.busmore.dao.manage.DBManager;
import com.xb.busmore.entity.AllLine;
import com.xb.busmore.entity.LineInfoDown;
import com.xb.busmore.entity.LineInfoUp;
import com.xb.busmore.entity.LinePriceInfo;
import com.xb.busmore.entity.LineStation;
import com.xb.busmore.entity.car.CarRunInfo;
import com.xb.busmore.entity.car.UseConfig;
import com.xb.busmore.dao.manage.PosManager;
import com.xb.busmore.moudle.qrCode.entity.FTPEntity;
import com.xb.busmore.util.net.ftpUtils.FTP;
import com.xb.busmore.util.net.prase.FileByte;
import com.xb.busmore.util.BusToast;
import com.xb.busmore.util.sound.SoundPoolUtil;

import java.io.File;
import java.util.List;

public class InitFtpDate {
    static FTP ftp;
    static InitFtpDate initFtpDate;

    public static InitFtpDate getInstance() {
        if (initFtpDate == null) {
            initFtpDate = new InitFtpDate();
        }
        return initFtpDate;
    }

    private InitFtpDate() {
        if (ftp == null) {
            FTPEntity ftpEntity = PosManager.getInstance().getFTP();
            ftp = new FTP().setHostName(ftpEntity.getI()).setServerPort(ftpEntity.getP()).setUserName(ftpEntity.getU()).setPassword(ftpEntity.getPsw());
        }
    }

    public void resetFtp(FTPEntity ftpEntity) {
        ftp = new FTP().setHostName(ftpEntity.getI()).setServerPort(ftpEntity.getP()).setUserName(ftpEntity.getU()).setPassword(ftpEntity.getPsw());
    }

    public FTP getFTP() {
        return ftp;
    }

    public FTP getTestFTP() {//测试服务器的地址
        return new FTP().setHostName("192.144.184.104").setServerPort(26).setUserName("ftp_test").setPassword("ftp1234!@#$");
    }


    /**
     * 下载所有文件信息
     */
    public void downAllLine() {
        ftp.downcount++;
        try {
            File file = ftp.downloadSingleFile("pram/allline.json", Environment.getExternalStorageDirectory() + "/", "allline.json");
            if (ftp.downcount < 5) {
                if (file == null) {
                    downAllLine();
                } else {
                    praseAllLine(file);
                }
            } else {
                ftp.downcount = 0;
                BusToast.showToast(App.getInstance(), "线路更新失败", false);
            }
        } catch (Exception e) {
            Log.e("InitFtpDate", "downloadDetailLine(InitFtpDate.java:110)" + e.getMessage());
        }
    }

    /**
     * 解析所有线路文件
     *
     * @param file
     * @throws Exception
     */
    public void praseAllLine(File file) throws Exception {
        byte[] PramVesion = FileByte.File2byte(file);
        String alllineStr = new String(PramVesion, "GB2312");
        AllLine allLine = new Gson().fromJson(alllineStr, AllLine.class);
        DBManager.updateAllLine(allLine.getAllline());
        RxBus.getInstance().post(new RxMessage(RxMessage.LINE));
    }


    /**
     * 下载具体线路信息
     *
     * @param lineNo
     */
    public void downloadDetailLine(String lineNo) {
        BusToast.showToast(App.getInstance(), "线路下载中", true);
        ftp.downcount++;
        int company = 0;
        int busline = 0;
        if (lineNo.length() == 6) {
            company = Integer.parseInt(lineNo.substring(0, 3));
            busline = Integer.parseInt(lineNo.substring(3, 6));
        } else if (lineNo.length() == 4) {
            company = Integer.parseInt(lineNo.substring(0, 2), 16);
            busline = Integer.parseInt(lineNo.substring(2, 4), 16);
        }

        try {
            File file = ftp.downloadSingleFile("pram/" + company + "," + busline + ".json", Environment.getExternalStorageDirectory() + "/", company + "," + busline + ".json");
            //如果重复下载5次都失败  即被视为网络异常
            if (ftp.downcount < 5) {
                if (file == null) {
                    downloadDetailLine(lineNo);
                } else {
                    ftp.downcount = 0;
                    //解析线路文件
                    praseLineInfo(file);
                }
            } else {
                ftp.downcount = 0;
                BusToast.showToast(App.getInstance(), "线路文件更新失败", false);
            }
        } catch (Exception e) {
            Log.e("InitFtpDate", "downloadDetailLine(InitFtpDate.java:110)" + e.getMessage());
        }
    }


    /**
     * 解析具体线路文件
     *
     * @param file
     * @throws Exception
     */
    public void praseLineInfo(File file) throws Exception {
        byte[] PramVesion = FileByte.File2byte(file);
        String alllineStr = new String(PramVesion, "GB2312");
        Log.e("InitFtpDate", "praseLineInfo(InitFtpDate.java:124)" + alllineStr);
        LineInfoUp lineInfoup = new Gson().fromJson(alllineStr, LineInfoUp.class);
        LineInfoDown lineInfoDown = new Gson().fromJson(alllineStr, LineInfoDown.class);
        PosManager.getInstance().updateLineInfo(lineInfoup);//保存线路文件相关

        /******************************解析站点********************************/
        List<LineStation> uplineStationList = lineInfoDown.getUp_position();
        for (int i = 0; i < uplineStationList.size(); i++) {
            LineStation upStation = uplineStationList.get(i);
            upStation.setDiraction(0);
            upStation.setTime(System.currentTimeMillis());
            upStation.setLine(lineInfoup.getLine());
            uplineStationList.set(i, upStation);
        }
        lineInfoDown.setUp_position(uplineStationList);

        List<LineStation> downlineStationList = lineInfoDown.getDown_position();
        for (int i = 0; i < downlineStationList.size(); i++) {
            LineStation downStation = downlineStationList.get(i);
            downStation.setDiraction(1);
            downStation.setLine(lineInfoup.getLine());
            downStation.setTime(System.currentTimeMillis());
            downlineStationList.set(i, downStation);
        }
        lineInfoDown.setDown_position(downlineStationList);
        /****************************解析票价**********************************/
        List<LinePriceInfo> upLinePrice = lineInfoDown.getUp_price();
        for (int i = 0; i < upLinePrice.size(); i++) {
            LinePriceInfo upPrice = upLinePrice.get(i);
            upPrice.setDiraction(0);
            upPrice.setLine(lineInfoup.getLine());
            upPrice.setTime(System.currentTimeMillis());
            upLinePrice.set(i, upPrice);
        }
        lineInfoDown.setUp_price(upLinePrice);


        List<LinePriceInfo> downLinePrice = lineInfoDown.getDown_price();
        for (int i = 0; i < downLinePrice.size(); i++) {
            LinePriceInfo downPrice = downLinePrice.get(i);
            downPrice.setDiraction(0);
            downPrice.setLine(lineInfoup.getLine());
            downPrice.setTime(System.currentTimeMillis());
            downLinePrice.set(i, downPrice);
        }
        lineInfoDown.setDown_price(downLinePrice);
        /*************************解析完成*************************************/
        //解析线路文件成功 对参数进行数据库保存
        PosManager.getInstance().updateStationInfo(lineInfoDown, lineInfoup.getLine());
        //设置线路信息
        setLine(lineInfoup, lineInfoDown);
    }


    //设置线路信息
    public void setLine(LineInfoUp lineInfoUp, LineInfoDown lineInfoDown) {
        UseConfig useConfig = PosManager.getInstance().setUseConfig(
                PosManager.getInstance().getUseConfig()
                        .setLine(lineInfoUp.getLine())
                        .setLine_chinese_name(lineInfoUp.getChinese_name()).
                        setStation(1));

        CarRunInfo carRunInfo = PosManager.getInstance().setCarRunInfo(PosManager.getInstance().getCarRunInfo()
                .setPrice(PosManager.getPrice(1, PosManager.getInstance().getCarRunInfo().getDiraction()))
                .setCoefficient(lineInfoUp.getCoefficient()));

        PosManager.getInstance().setCarConfig(PosManager.getInstance().getCarConfig().setLine(lineInfoUp.getLine()));

        SoundPoolUtil.play(28);
        BusToast.showToast(App.getInstance(), "线路号:" + useConfig.getLine() + "\n线路名:" + useConfig.getLine_chinese_name()
                + "\n机具状态:" + (carRunInfo.getDeviceStatus() == 0 ? "上车机" : (carRunInfo.getDeviceStatus() == 1 ? "上下车机" : "下车机"))
                + "\n变站模式:" + (carRunInfo.getBianStatu() == 0 ? "手动变站" : "自动变站")
                + "\n车辆号:" + PosManager.getInstance().getCarConfig().getBusNo(), true);
    }

}
