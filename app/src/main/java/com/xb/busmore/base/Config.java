package com.xb.busmore.base;

import android.os.Environment;

public class Config {
    //正式服务器地址
    private static String IP = "http://111.230.85.238";

    //本地文件保存路径
    public static String FILE_SAVE_PATH = Environment.getExternalStorageDirectory() + "/";

    //银联卡记录
    public static final String UNION_CARD_RECORD = IP + "/bipbus/interaction/bankjourAll";
}
