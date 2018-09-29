package com.xb.busmore.util.task;

import com.xb.busmore.base.BackCall;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * 作者：Evergarden on 2017-09-01 17:14
 * QQ：1941042402
 */

public class TimeTask extends Thread {

    private BackCall backCall;

    public TimeTask(BackCall backCall) {
        this.backCall = backCall;
    }

    private boolean flag = true;

    public void setFlag(boolean flag) {
        this.flag = flag;
    }

    @Override
    public void run() {

        while (flag) {
            try {
                Thread.sleep(1000);
            } catch (Exception e) {

            }
            SimpleDateFormat df = new SimpleDateFormat("yyyy年MM月dd日 HH :mm :ss");//设置日期格式
            String time = df.format(new Date());
            backCall.call(time);

            super.run();
        }
    }
}
