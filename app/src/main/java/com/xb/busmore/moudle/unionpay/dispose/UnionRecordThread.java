package com.xb.busmore.moudle.unionpay.dispose;

import android.text.TextUtils;
import android.util.Log;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.szxb.mlog.SLog;
import com.xb.busmore.base.Config;
import com.xb.busmore.moudle.unionpay.entity.UnionPayEntity;
import com.xb.busmore.moudle.unionpay.unionutil.ParseUtil;
import com.xb.busmore.util.net.http.JsonRequest;
import com.yanzhenjie.nohttp.rest.Response;
import com.yanzhenjie.nohttp.rest.SyncRequestExecutor;


import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * 作者：Tangren on 2018-07-26
 * 包名：com.szxb.buspay.task.service
 * 邮箱：996489865@qq.com
 * 上传银联记录
 */

public class UnionRecordThread extends Thread {


    @Override
    public void run() {
        super.run();
        try {

            unionRecordTask();

        } catch (Exception e) {
            SLog.d("RecordThread(run.java:53)" + getName() + "任务异常>>>" + e.toString());
        }

    }


    /**
     * 银联卡
     */
    private void unionRecordTask() {
        List<UnionPayEntity> unUpList = ParseUtil.unUpList();
        Log.e("UnionRecordThread", "unionRecordTask(UnionRecordThread.java:54) 上传的银联记录" + unUpList.size());
        if (unUpList.size() == 0) {
            return;
        }
        Map<String, Object> map = new HashMap<>();
        final JSONArray array = new JSONArray();
        for (UnionPayEntity payEntity : unUpList) {
            JSONObject object = new JSONObject();
            object.put("mchId", payEntity.getMchId());
            object.put("unionPosSn", payEntity.getUnionPosSn());
            object.put("posSn", payEntity.getPosSn());
            object.put("busNo", payEntity.getBusNo());
            object.put("totalFee", payEntity.getTotalFee());
            object.put("payFee", payEntity.getPayFee());
            object.put("resCode", payEntity.getResCode());
            object.put("time", payEntity.getTime());
            object.put("tradeSeq", payEntity.getTradeSeq());
            object.put("mainCardNo", payEntity.getMainCardNo());
            object.put("batchNum", payEntity.getBatchNum());
            object.put("bus_line_name", payEntity.getBus_line_name());
            object.put("bus_line_no", payEntity.getBus_line_no());
            object.put("driverNum", payEntity.getDriverNum());
            object.put("unitno", payEntity.getUnitno());
            object.put("upStatus", payEntity.getUpStatus());
            object.put("uniqueFlag", payEntity.getUniqueFlag());
            //卡填1，二维码填2
            object.put("tranType", TextUtils.isEmpty(payEntity.getReserve_2()) ? "1" : "2");
            array.add(object);
        }
        map.put("data", array.toJSONString());
        JsonRequest request = new JsonRequest(Config.UNION_CARD_RECORD);
        request.add(map);
        Response<JSONObject> execute = SyncRequestExecutor.INSTANCE.execute(request);
        if (execute.isSucceed()) {
            try {
                JSONObject object = execute.get();
                String rescode = object.getString("rescode");
                if (TextUtils.equals(rescode, "0000")) {
                    JSONArray list = object.getJSONArray("datalist");
                    for (int i = 0; i < list.size(); i++) {
                        JSONObject ob = list.getJSONObject(i);
                        String uniqueFlag = ob.getString("uniqueFlag");
                        ParseUtil.updateUnionUpState(uniqueFlag);
                    }
                }
            } catch (Exception e) {
                e.printStackTrace();
                SLog.d("RecordThread(unionRecordTask.java:116)银联卡上传异常>>" + e.toString());
            }
        } else {
            SLog.d("RecordThread(unionRecordTask.java:187)银联卡上传网络异常" + execute.getException().toString());
        }
    }
}
