package com.xb.busmore.moudle.gps;


import android.util.Log;

import com.baidu.location.BDAbstractLocationListener;
import com.baidu.location.BDLocation;
import com.baidu.location.LocationClient;
import com.baidu.location.LocationClientOption;
import com.baidu.mapapi.model.LatLng;
import com.baidu.mapapi.utils.DistanceUtil;
import com.xb.busmore.base.App;
import com.xb.busmore.dao.manage.DBManager;
import com.xb.busmore.entity.LineStation;
import com.xb.busmore.moudle.init.LocationService;
import com.xb.busmore.dao.manage.PosManager;
import com.xb.busmore.util.ThreadScheduledExecutorUtil;
import com.xb.busmore.util.Utils;

import java.util.List;
import java.util.concurrent.TimeUnit;


/**
 * Created by Evergarden on 2018/1/14.
 */

public class GPSEvent {

    private static String TAG = "GPS";
    private static LocationService locationService;
    private static LocationClient mLocationClient;
    private static long locationTime = 0;


    //使用百度定位服务
    public static void GPSEventOpen() {
//        resetLine();
        mLocationClient = (App.getInstance()).mLocationClient;
        LocationClientOption option = new LocationClientOption();
        option.setLocationMode(LocationClientOption.LocationMode.Device_Sensors);
        //可选，设置定位模式，默认高精度
        //LocationMode.Hight_Accuracy：高精度；
        //LocationMode. Battery_Saving：低功耗；
        //LocationMode. Device_Sensors：仅使用设备；

        option.setCoorType("bd09ll");
        //可选，设置返回经纬度坐标类型，默认gcj02
        //gcj02：国测局坐标；
        //bd09ll：百度经纬度坐标；
        //bd09：百度墨卡托坐标；
        // 海外地区定位，无需设置坐标类型，统一返回wgs84类型坐标

        option.setScanSpan(2000);
        //可选，设置发起定位请求的间隔，int类型，单位ms
        //如果设置为0，则代表单次定位，即仅定位一次，默认为0
        //如果设置非0，需设置1000ms以上才有效\

        option.setOpenGps(true);
        //可选，设置是否使用gps，默认false
        //使用高精度和仅用设备两种定位模式的，参数必须设置为true

        option.setLocationNotify(false);
        //可选，设置是否当GPS有效时按照1S/1次频率输出GPS结果，默认false

        option.setIgnoreKillProcess(false);
        //可选，定位SDK内部是一个service，并放到了独立进程。
        //设置是否在stop的时候杀死这个进程，默认（建议）不杀死，即setIgnoreKillProcess(true)

        option.SetIgnoreCacheException(false);
        //可选，设置是否收集Crash信息，默认收集，即参数为false

        option.setWifiCacheTimeOut(5 * 60 * 1000);
        //可选，7.2版本新增能力
        //如果设置了该接口，首次启动定位时，会先判断当前WiFi是否超出有效期，若超出有效期，会先重新扫描WiFi，然后定位

        option.setEnableSimulateGps(false);
        //可选，设置是否需要过滤GPS仿真结果，默认需要，即参数为false

        mLocationClient.setLocOption(option);
        mLocationClient.registerLocationListener(mListener);
        mLocationClient.start();

        ThreadScheduledExecutorUtil.getInstance().getService().scheduleAtFixedRate(new Runnable() {
            @Override
            public void run() {
                Log.w("GPS定位未启用", "时间" + (System.currentTimeMillis() - locationTime));
                if (System.currentTimeMillis() - locationTime > 60000) {
                    Log.w("GPS定位未启用", "GPS模块未启用");
                    PosManager.getInstance().setGpsStatus(0);
                }
            }
        }, 0, 2, TimeUnit.SECONDS);
    }


    /*****
     *
     * 定位结果回调，重写onReceiveLocation方法
     *
     */

    private static BDAbstractLocationListener mListener = new BDAbstractLocationListener() {

        @Override
        public void onReceiveLocation(BDLocation location) {
            if (null != location && location.getLocType() != BDLocation.TypeServerError) {
                locationTime = System.currentTimeMillis();
                Log.w("GPS定位成功", "站点坐标" + location.getLatitude() + "," + location.getLongitude() + "  定位类型" + location.getLocType());
                //用于设置百度定位识别的卫星数
                PosManager.getInstance().setGpsStatus(location.getSatelliteNumber());
                List<LineStation> lineStationList =PosManager.getInstance().getStationList();
                for (int i = 0; i < lineStationList.size(); i++) {
                    LatLng nowGps = new LatLng(Utils.get6Double(location.getLatitude()), Utils.get6Double(location.getLongitude()));
                    LatLng stationGps = new LatLng(Utils.get6Double(lineStationList.get(i).getLatitude()), Utils.get6Double(lineStationList.get(i).getLongitude()));
                    double gpsDistance = DistanceUtil.getDistance(nowGps, stationGps);
                    Log.i("MI  info", "onReceiveLocation(GPSEvent.java)117)定位与站点的距离" + gpsDistance);
                    if (gpsDistance < lineStationList.get(i).getDistance()) {
                        Log.i("MI  info", "onReceiveLocation(GPSEvent.java)119)定位到了站点" + lineStationList.get(i).getName());
                        if (PosManager.getInstance().getUseConfig().getStation() != lineStationList.get(i).getNo()) {
                            Log.i("MI  info","onReceiveLocation(GPSEvent.java)121)更新定位到的站点"+ lineStationList.get(i).getName());
                            PosManager.getInstance().setNowStation(lineStationList.get(i).getNo());
                        }
                    }
                }
            } else {
                PosManager.getInstance().setGpsStatus(0);
            }
        }
    };


    //关闭GPS
    public static void GPSClose() {
        locationService.unregisterListener(mListener); //注销掉监听
        locationService.stop(); //停止定位服务
    }


}