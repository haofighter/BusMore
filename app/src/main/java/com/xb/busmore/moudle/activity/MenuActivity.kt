package com.xb.busmore.moudle.activity

import android.util.Log
import com.xb.busmore.R
import com.xb.busmore.base.BaseActivity
import com.xb.busmore.base.PosKeyConfig
class MenuActivity : BaseActivity() {
    override fun rootView(): Int {
        return R.layout.activity_menu
    }

    //按键接收
    override fun keyEvent(key: Int) {
        Log.i("MI  info", "(HomeActivity.java62)" + key)
        when (key) {
            PosKeyConfig.key_up_left -> {
            }
            PosKeyConfig.key_up_right
            -> {
            }
            PosKeyConfig.key_down_left
            -> {
                finish()
            }
            PosKeyConfig.key_down_right
            -> {

            }
        }
    }

}
