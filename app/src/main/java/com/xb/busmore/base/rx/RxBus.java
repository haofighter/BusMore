package com.xb.busmore.base.rx;


import io.reactivex.Flowable;
import io.reactivex.processors.FlowableProcessor;
import io.reactivex.processors.PublishProcessor;

/**
 * 作者: Tangren on 2017/7/31
 * 包名：com.szxb.utils.rx
 * 邮箱：996489865@qq.com
 * TODO:一句话描述
 */

public class RxBus {

    private static volatile RxBus instance;

    private FlowableProcessor<Object> rxbus;

    private RxBus() {
        rxbus = PublishProcessor.create().toSerialized();
    }


    /**
     * 单例RxBus
     *
     * @return RxBus
     */
    public static RxBus getInstance() {
        if (null == instance) {
            synchronized (RxBus.class) {
                if (null == instance) {
                    instance = new RxBus();
                }
            }
        }
        return instance;
    }

    private boolean hasObserVable() {
//        return RxBus.hasObservers();
        return rxbus.hasSubscribers();
    }

    //发送
    public void post(Object o) {
        if (hasObserVable()) {
            try {
                rxbus.onNext(o);
            } catch (Exception e) {

            }
        }
    }

    // 传递
    public <T> Flowable<T> tObservable(Class<T> bus) {
        return rxbus.ofType(bus).onBackpressureLatest();
    }


}
