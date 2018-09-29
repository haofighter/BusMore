package com.xb.busmore.base.rx;

/**
 * Rx消息容器
 */

public class RxMessage {
    public final static int KEY_EVENT = 10001;//按键事件
    public final static int LINE = 10002;//线路更新
    public final static int REFRESH_QR_CODE = 10003;//刷新二维码
    public final static int QR_ERROR = 10004;//二维码有误
    public final static int SOFTWARE_EXCEPTION = 10005;//软件异常
    public static int SettingFangxiang = 10006;//设置方向
    public final static int OK = 0;//成功
    public final static int FAIL = -1;//失败


    private int type;
    private Object Data;
    private Object Data2;

    public RxMessage() {
    }

    public RxMessage(int type) {
        this.type = type;
    }


    public RxMessage(int type, Object data) {
        this.type = type;
        Data = data;
    }

    public RxMessage(int type, Object data, Object data2) {
        this.type = type;
        Data = data;
        Data2 = data2;
    }

    public int getType() {
        return type;
    }

    public Object getData() {
        return Data;
    }

    public Object getData2() {
        return Data2;
    }

    public void setType(int type) {
        this.type = type;
    }

    public void setData(Object data) {
        Data = data;
    }
}
