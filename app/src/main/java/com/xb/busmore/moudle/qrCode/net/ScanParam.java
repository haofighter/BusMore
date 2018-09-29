package com.xb.busmore.moudle.qrCode.net;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.xb.busmore.base.HttpUrl;
import com.xb.busmore.moudle.qrCode.entity.MacKeyEntity;
import com.xb.busmore.util.DateUtil;
import com.xb.busmore.util.net.http.JsonRequest;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;

public class ScanParam {


//    public  void  getMacKey(){
//        JsonRequest macRequest = new JsonRequest(HttpUrl.MAC_KEY);
//        macRequest.set(ParamsUtil.getkeyMap());
//        Response<JSONObject> execute = SyncRequestExecutor.INSTANCE.execute(macRequest);
//        if (execute.isSucceed()) {
//            Log.w("InitZipActivity",
//                    "call(InitZipActivity.java:120)" + execute.get().toJSONString());
//            String macMsg = execute.get().getString("retmsg");
//            if (!TextUtils.isEmpty(macMsg) && TextUtils.equals(macMsg, "success")) {
//                final JSONArray array = execute.get().getJSONArray("mackey_list");
//                for (int i = 0; i < array.size(); i++) {
//                    JSONObject object = array.getJSONObject(i);
//                    MacKeyEntity macKeyEntity = new MacKeyEntity();
//                    macKeyEntity.setTime(DateUtil.getCurrentDate());
//                    macKeyEntity.setKey_id(object.getString("key_id"));
//                    macKeyEntity.setPubkey(object.getString("mackey"));
//                    Log.w("PosScanInit",
//                            "call(PosScanInit.java:72)" + object.getString("mackey"));
//                    getDaoSession().insertOrReplace(macKeyEntity);
//                }
//                subscriber.onNext(true);
//            } else subscriber.onNext(false);
//        } else subscriber.onError(execute.getException());
//    }
}
