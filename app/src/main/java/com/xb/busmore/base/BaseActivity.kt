package com.xb.busmore.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.View
import android.view.Window
import android.view.WindowManager
import com.xb.busmore.base.rx.RxBus
import com.xb.busmore.base.rx.RxMessage
import io.reactivex.schedulers.Schedulers

abstract class BaseActivity : AppCompatActivity() {


    override fun onCreate(savedInstanceState: Bundle?) {
        if (Build.VERSION.SDK_INT > 21) {
            val option = (View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                    or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN)
            window.decorView.systemUiVisibility = option
            window.statusBarColor = Color.TRANSPARENT
            window.navigationBarColor = Color.TRANSPARENT
        }
        super.onCreate(savedInstanceState)
        setContentView(rootView())
    }

    protected abstract fun rootView(): Int


    init {
        RxBus.getInstance().tObservable(RxMessage::class.java).filter {
            rxMassage(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe()
    }

    abstract fun keyEvent(key: Int)

    abstract fun rxMassage(message: RxMessage): Boolean
}
