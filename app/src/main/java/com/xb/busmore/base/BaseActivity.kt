package com.xb.busmore.base

import android.graphics.Color
import android.os.Build
import android.os.Bundle
import android.os.PersistableBundle
import android.support.v7.app.AppCompatActivity
import android.view.*
import com.xb.busmore.R
import com.xb.busmore.base.rx.RxBus
import com.xb.busmore.base.rx.RxMessage
import io.reactivex.Flowable
import io.reactivex.disposables.Disposable
import io.reactivex.schedulers.Schedulers
import kotlinx.android.synthetic.main.activity_base.*

abstract class BaseActivity : AppCompatActivity() {
    //设置全屏并且隐藏虚拟按键
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_base)
        var view = LayoutInflater.from(this).inflate(rootView(), null)
        view.layoutParams = ViewGroup.LayoutParams(ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.MATCH_PARENT)
        base_layout.addView(view)
    }

    protected abstract fun rootView(): Int

    override fun onWindowFocusChanged(hasFocus: Boolean) {
        super.onWindowFocusChanged(hasFocus)
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            val window = window
            window.clearFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS or WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION)
            window.decorView.systemUiVisibility = (View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            //   window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);

            getWindow().navigationBarColor = Color.TRANSPARENT
            getWindow().statusBarColor = Color.TRANSPARENT
        }

    }

    var rx: Disposable

    init {
        rx = RxBus.getInstance().tObservable(RxMessage::class.java).filter {
            rxMassage(it)
        }.subscribeOn(Schedulers.io())
                .observeOn(Schedulers.io())
                .unsubscribeOn(Schedulers.io())
                .subscribe()
    }

    abstract fun keyEvent(key: Int)

    open fun rxMassage(message: RxMessage): Boolean {
        when (message.type) {
            RxMessage.KEY_EVENT -> keyEvent(message.data as Int)
        }
        return true
    }

    override fun onKeyDown(keyCode: Int, event: KeyEvent?): Boolean {
        when (keyCode) {
            KeyEvent.KEYCODE_BACK -> false
            KeyEvent.KEYCODE_HOME -> false
            KeyEvent.KEYCODE_MENU -> false
        }
        return super.onKeyDown(keyCode, event)
    }

    override fun onPause() {
        super.onPause()
        rx.dispose()
    }
}
