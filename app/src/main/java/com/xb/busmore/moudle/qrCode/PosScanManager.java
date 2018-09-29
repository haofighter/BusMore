package com.xb.busmore.moudle.qrCode;

import com.xb.busmore.moudle.qrCode.tensent.TenPosReportManager;
import com.xb.busmore.moudle.qrCode.xb.XBPosReportManager;

/**
 * 作者: Tangren on 2017-09-08
 * 包名：szxb.com.commonbus.util.comm
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 *
 */

public class PosScanManager {


    private static PosScanManager instance = null;

    private PosScanManager() {
    }

    public static PosScanManager getInstance() {
        if (instance == null) {
            synchronized (PosScanManager.class) {
                if (instance == null) {
                    instance = new PosScanManager();
                }
            }
        }
        return instance;
    }


    public static boolean isTenQRcode(String qrcode) {
        return qrcode != null && qrcode.indexOf("TX") == 0;
    }


    /**
     * 腾讯二维码
     * @param qrcode
     */
    public void txposScan(String qrcode) {
        TenPosReportManager.getInstance().posScan(qrcode);
    }


    public void xbposScan(String qrcode) {
        int codeType;
        if (qrcode.indexOf("szxb_ftp") == 0) {
            //如果是ftp设置
            codeType = QRCode.CONFIG_CODE_FTP;
        } else if (qrcode.indexOf("szxb_mch") == 0) {
            //商户设置
            codeType = QRCode.CONFIG_CODE_MCH;
        } else if (qrcode.indexOf("szxb_ip") == 0) {
            //服务器IP设置
            codeType = QRCode.CONFIG_CODE_IP;
        } else if (qrcode.indexOf("szxb_time") == 0) {
            //校准时间
            codeType = QRCode.TIME;
        } else if (qrcode.indexOf("szxb_dir") == 0) {
            codeType = QRCode.UpAndDown;
        } else {
            //线路
            codeType = QRCode.CONFIG_CODE_LINE;
        }
        XBPosReportManager.getInstance().posScan(codeType, qrcode);
    }


    public static boolean isMyQRcode(String qrcode) {
        return qrcode != null && qrcode.indexOf("szxb") == 0;
    }

}
