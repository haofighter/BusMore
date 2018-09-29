package com.xb.busmore.moudle.manage.task;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;

import com.xb.busmore.base.App;

/**
 * 作者: Tangren on 2017/8/2
 * 包名：com.szxb.buspay.task
 * 邮箱：996489865@qq.com
 * TODO:监听sd卡插入动作插入时数据同步到sd卡中
 */

public class TaskSDBroadcast extends BroadcastReceiver {
    @Override
    public void onReceive(Context context, Intent intent) {
        String action = intent.getAction();
        if (action.equals(Intent.ACTION_MEDIA_EJECT)) {//拔出动作
            App.getInstance().setExistSD(false);

        } else if (action.equals(Intent.ACTION_MEDIA_MOUNTED)) {//插入动作
            App.getInstance().setExistSD(true);
        }
    }
}
