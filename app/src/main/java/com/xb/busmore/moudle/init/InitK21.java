package com.xb.busmore.moudle.init;

import android.content.res.AssetManager;
import android.os.RemoteException;


import com.xb.busmore.base.App;
import com.xb.busmore.base.BackCall;
import com.xb.busmore.util.DateUtil;

import java.util.Calendar;

import com.szxb.jni.libszxb;

/**
 * K21更新
 */
public class InitK21 {

    //固件和K21
    public static void intit(final BackCall backCall) {
        new Thread(new Runnable() {
            @Override
            public void run() {
                AssetManager ass = App.getInstance().getAssets();
                int tag = libszxb.ymodemUpdate(ass, "zibofenduan.bin");
                Calendar calendar = libszxb.deviceGettime();
                try {
                    if (calendar != null) {
                        //如果系统时间是准确的
                        if (DateUtil.getCurrentYear() >= 2017) {
                            SetK21Time();
                        } else {
                            //如果系统时间错误,则使用k21时间
                            if ((calendar.get(Calendar.YEAR)) >= 2017)
                                App.getInstance()
                                        .mService
                                        .setDateTime(calendar.get(Calendar.YEAR)
                                                , calendar.get(Calendar.MONTH)
                                                , calendar.get(Calendar.DAY_OF_MONTH)
                                                , calendar.get(Calendar.HOUR_OF_DAY)
                                                , calendar.get(Calendar.MINUTE));
                        }
                    }
                } catch (RemoteException e) {
                    e.printStackTrace();
                }
                backCall.call(tag);
            }
        }).start();
    }

    //初始化单片机时间
    public static void SetK21Time() {
        Calendar now = Calendar.getInstance();
        int Year = now.get(Calendar.YEAR);
        int Month = now.get(Calendar.MONTH) + 1;
        int Day = now.get(Calendar.DAY_OF_MONTH);
        int hour = now.get(Calendar.HOUR_OF_DAY);
        int Min = now.get(Calendar.MINUTE);
        int Sec = now.get(Calendar.SECOND);
        libszxb.deviceSettime(Year, Month, Day, hour, Min, Sec);
    }
}
