package com.xb.busmore.util.logcat;

import android.util.Log;

/**
 * Created by Evergarden on 2018/3/24.
 */

public class LogcatEvent {
    private static LogcatEvent logcat=new LogcatEvent();
    public static LogcatEvent getInstance() {
        return logcat;
    }

    public void WritingCom(){
        Log.d("LogcatEvent","WritingCom");
     //   new WriteLogcat("SERIALPORT",100).start();
    }

    public void WritingCardPay(){
        Log.d("LogcatEvent","WritingCardPay");
     //   new WriteLogcat("TIME",20).start();
    }

}
