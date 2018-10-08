package com.xb.busmore.moudle.unionpay.dispose;

import com.szxb.java8583.core.Iso8583Message;
import com.szxb.java8583.module.BankScanPay;
import com.szxb.java8583.module.manager.BusllPosManage;
import com.xb.busmore.entity.car.CarConfig;
import com.xb.busmore.entity.car.UseConfig;
import com.xb.busmore.dao.manage.PosManager;
import com.xb.busmore.moudle.unionpay.config.UnionConfig;
import com.xb.busmore.moudle.unionpay.entity.UnionPayEntity;

import static com.xb.busmore.dao.manage.DBCore.getDaoSession;
import static com.xb.busmore.util.DateUtil.getCurrentDate;

/**
 * 作者：Tangren on 2018-09-11
 * 包名：com.szxb.unionpay.dispose
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class BankQRParse {
    synchronized public BankResponse parseResponse(int amount, String qrCode) {
        BusllPosManage.getPosManager().setTradeSeq();
        //同步保存记录
        saveQRUnionPayEntity(amount, qrCode);
        SyncSSLRequest syncSSLRequest = new SyncSSLRequest();
        Iso8583Message iso8583Message = BankScanPay.getInstance()
                .qrPayMessage(qrCode, amount, BusllPosManage.getPosManager().getTradeSeq(), BusllPosManage.getPosManager().getMacKey());
        BankResponse response = syncSSLRequest.request(UnionConfig.PAY_TYPE_BANK_QR, iso8583Message.getBytes());
        return response;
    }

    private void saveQRUnionPayEntity(int amount, String qrCode) {
        UnionPayEntity payEntity = new UnionPayEntity();

        /*************************************************************/
        UseConfig useConfig = PosManager.getInstance().getUseConfig();
        CarConfig carConfig = PosManager.getInstance().getCarConfig();

        payEntity.setPosSn(carConfig.getPosSn());
        payEntity.setBusNo(carConfig.getBusNo());
        payEntity.setBus_line_name(useConfig.getLine_chinese_name());
        payEntity.setBus_line_no(useConfig.getLine());
        payEntity.setDriverNum(useConfig.getDriverNo());
        payEntity.setUnitno(carConfig.getUnionTraNo());
        /*************************************************************/
        payEntity.setMchId(BusllPosManage.getPosManager().getMchId());
        payEntity.setUnionPosSn(BusllPosManage.getPosManager().getPosSn());

        payEntity.setTotalFee(String.valueOf(amount));
        //注:支付金额记录存储需根据交易返回为准,未防止交易失败导致金额错误
        payEntity.setPayFee("0");
        payEntity.setTime(getCurrentDate());
        payEntity.setTradeSeq(String.format("%06d", BusllPosManage.getPosManager().getTradeSeq()));
        payEntity.setMainCardNo(qrCode);
        payEntity.setBatchNum(BusllPosManage.getPosManager().getBatchNum());

        payEntity.setUpStatus(1);//0已支付、1未支付
        payEntity.setUniqueFlag(String.format("%06d", BusllPosManage.getPosManager().getTradeSeq()) + BusllPosManage.getPosManager().getBatchNum());
        payEntity.setReserve_2("QR");
        //记录也同步保存
        getDaoSession().getUnionPayEntityDao().insertOrReplaceInTx(payEntity);
    }
}
