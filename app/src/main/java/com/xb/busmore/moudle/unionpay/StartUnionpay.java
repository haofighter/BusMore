package com.xb.busmore.moudle.unionpay;

import com.xb.busmore.base.App;
import com.xb.busmore.entity.MifareGetCard;
import com.xb.busmore.moudle.manage.PosManager;
import com.xb.busmore.moudle.unionpay.dispose.BankCardParse;
import com.xb.busmore.moudle.unionpay.dispose.BankQRParse;
import com.xb.busmore.moudle.unionpay.dispose.BankResponse;
import com.xb.busmore.util.AppUtil;
import com.xb.busmore.util.BusToast;

public class StartUnionpay {

    //银联刷卡
    public void unionCardPay(MifareGetCard mifareGetCard) {
        BankResponse bankICResponse = new BankResponse();
        //银联卡
        try {
            if (!AppUtil.checkNetStatus()) {
                BusToast.showToast(App.getInstance(), "网络异常\n请选择其他方式乘车", false);
                return;
            }
            BusToast.showToast(App.getInstance(), "银联支付中", true);
            boolean isNull = bankICResponse.getResCode() == -999;
            BankCardParse cardParse = new BankCardParse();
            bankICResponse = cardParse.parseResponse(bankICResponse,
                    isNull ? "0" : bankICResponse.getMainCardNo(),
                    isNull ? 0 : bankICResponse.getLastTime(), PosManager.getUnionPayPrice(), mifareGetCard.getUnipayCard());
            if (bankICResponse.getResCode() > 0) {
                BusToast.showToast(App.getInstance(), bankICResponse.getMsg(), true);
            } else {
                BusToast.showToast(App.getInstance(), bankICResponse.getMsg() + "[" + bankICResponse.getResCode() + "]", false);
            }

        } catch (Exception e) {
            e.printStackTrace();
            BusToast.showToast(App.getInstance(), "", false);
        }
    }

    //银联二维码
    public void unionScanPay(String codeResult) {
        BusToast.showToast(App.getInstance(), "扫码成功，正在发起支付。。。", true);

        BankQRParse qrParse = new BankQRParse();
        BankResponse response = qrParse.parseResponse(PosManager.getUnionPayPrice(), codeResult);

        if (response.getResCode() > 0) {
            BusToast.showToast(App.getInstance(), response.getMsg(), true);
        } else {
            BusToast.showToast(App.getInstance(), response.getMsg() + "[" + response.getResCode() + "]", false);
        }
    }

}
