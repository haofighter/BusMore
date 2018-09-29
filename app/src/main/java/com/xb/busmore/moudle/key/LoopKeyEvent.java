package com.xb.busmore.moudle.key;

import android.util.Log;

import com.xb.busmore.base.rx.RxBus;
import com.xb.busmore.base.rx.RxMessage;
import com.xb.busmore.util.Utils;

import com.szxb.jni.libszxb;

public class LoopKeyEvent implements Runnable {


    @Override
    public void run() {
        byte[] b = new byte[5];
        int i = libszxb.devicekey(b);
        Log.w("Dikey", i + "");
        if (i == -2) {
            libszxb.deviceReset();
        }
        if (i == -5) {
            libszxb.deviceReset();
        }
        String code = Utils.printHexBinary(b);
        Log.w("key", code);
        if (!code.equals("0000000000") && b != null) {
            if (b[0] == 0x01) {
                Log.w("keys", "run: " + code);
                RxBus.getInstance().post(new RxMessage(RxMessage.KEY_EVENT, 1));
            }
            if (b[1] == 0x01) {
                RxBus.getInstance().post(new RxMessage(RxMessage.KEY_EVENT, 2));
            }
            if (b[2] == 0x01) {
                RxBus.getInstance().post(new RxMessage(RxMessage.KEY_EVENT, 3));
            }
            if (b[3] == 0x01) {
                RxBus.getInstance().post(new RxMessage(RxMessage.KEY_EVENT, 4));
            }

        }
    }
}
