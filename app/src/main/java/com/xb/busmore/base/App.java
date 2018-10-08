package com.xb.busmore.base;

import android.app.Application;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.ServiceConnection;
import android.content.pm.PackageManager;
import android.os.IBinder;
import android.text.TextUtils;
import android.util.Log;

import com.baidu.location.LocationClient;
import com.lilei.tool.tool.IToolInterface;
import com.xb.busmore.BuildConfig;
import com.xb.busmore.R;
import com.xb.busmore.dao.manage.DBCore;
import com.xb.busmore.moudle.init.LocationService;
import com.xb.busmore.dao.manage.PosManager;
import com.xb.busmore.util.Utils;

import com.example.zhoukai.modemtooltest.ModemToolTest;


public class App extends Application {

    //单例
    private static volatile App instance = null;

    public static Boolean k21IsInit = false;
    public static int backGround = R.mipmap.bzb;


    //百度定位服务相关
    public LocationService locationService;
    public LocationClient mLocationClient;


    @Override
    public void onCreate() {
        super.onCreate();
        instance = this;
        {//定位服务
            locationService = new LocationService(instance);
            mLocationClient = new LocationClient(getApplicationContext());
        }
        //初始化数据库
        DBCore.init(this);

        //初始化AIDL
        initAIDL();

        //存储SN号
        if (Utils.StringIsEmpty(PosManager.getInstance().getCarConfig().getPosSn())) {
            initSn();
        }
    }

    public void initBack() {
        if (BuildConfig.FLAVOR.equals("yiyuan")) {

        } else if (BuildConfig.FLAVOR.equals("rongcheng")) {
            backGround = R.mipmap.rc_bg;
        } else if (BuildConfig.FLAVOR.contains("zibo")) {
            backGround = R.mipmap.bzb;
        }

    }

    /**
     * 获取Application的单例
     *
     * @return
     */
    public static App getInstance() {
        if (instance == null) {
            synchronized (App.class) {
                if (instance == null) {
                    instance = new App();
                }
            }
        }
        return instance;
    }

    /*********************************底层通讯相关连接*************************************/
    //K21通讯相关


    public IToolInterface mService;

    public IToolInterface getmService() {
        return mService;
    }

    ServiceConnection connection = new ServiceConnection() {
        @Override
        public void onServiceConnected(ComponentName name, IBinder service) {
            mService = IToolInterface.Stub.asInterface(service);
        }

        @Override
        public void onServiceDisconnected(ComponentName name) {
            mService = null;
        }
    };

    //初始化AIDL
    private void initAIDL() {
        Intent i = new Intent();
        i.setAction("com.lypeer.aidl");
        i.setPackage("com.lilei.tool.tool");
        boolean ret = bindService(i, connection, Context.BIND_AUTO_CREATE);
        Log.d("App",
                "initAIDL(App.java:124)AIDL初始化是否成功:" + ret);
    }


    /**********************************************************************************/


    /**
     * 获取到版本号
     *
     * @return
     */
    public String getPakageVersion() {
        try {
            return getPackageManager().getPackageInfo(
                    "com.xb.busmore", 0).versionName;
        } catch (PackageManager.NameNotFoundException e) {
            e.printStackTrace();
        }

        return "";
    }

    //初始化SN号
    private void initSn() {
        String item;
        try {
            item = ModemToolTest.getItem(7);
        } catch (Exception e) {
            item = "";
        }
        if (TextUtils.isEmpty(item)) {
            initSn();
        }
        PosManager.setPosSn(item);
    }


    //是否存在SD卡,默认不存在
    private boolean isExistSD = false;

    public void setExistSD(boolean existSD) {
        this.isExistSD = existSD;
    }

    public boolean isExistSD() {
        return isExistSD;
    }

}
