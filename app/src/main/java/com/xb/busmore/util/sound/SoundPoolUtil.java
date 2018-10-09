package com.xb.busmore.util.sound;

import android.content.Context;
import android.media.AudioManager;
import android.media.MediaPlayer;
import android.media.SoundPool;

import com.xb.busmore.R;
import com.xb.busmore.base.App;


/**
 * 作者: Tangren on 2017-09-05
 * 包名：szxb.com.commonbus.util.sound
 * 邮箱：996489865@qq.com
 * TODO:音源管理
 */

public class SoundPoolUtil {

    private static SoundPool mSoundPlayer = new SoundPool(1,
            AudioManager.STREAM_MUSIC, 5);

    private static MediaPlayer mediaPlayer;

    public static SoundPoolUtil soundPlayUtils;

    static Context mContext;

    private static int[] musics = new int[]{
            R.raw.ec_many_success, // 1,刷码成功,提示请您上车
            R.raw.qr_error, // 2,二维码有误
            R.raw.verify_fail, // 3,效验失败
            R.raw.e, // 4,软件异常
            R.raw.ec_user_public_key, // 5,卡证书公钥错误，提示卡证书数据错误
            R.raw.ec_format, // 6,二维码格式错误
            R.raw.ec_user_sign, // 7,二维码签名错误
            R.raw.ec_card_public_key, // 8,二维码签名错误
            R.raw.ec_fee, // 9,超出最大金额
            R.raw.ec_balance, // 10,余额不足
            R.raw.ec_param_err, // 11,参数错误
            R.raw.ec_fail, //12,系统错误
            R.raw.ec_re_qr_code, //13,请刷新二维码
            R.raw.ec_card_cert_time, //14,卡证书过期，提示用户联网刷新二维码
            R.raw.w01,//15 学生卡
            R.raw.w02,//16 老年卡
            R.raw.w03,//17 免费卡
            R.raw.w05,//18 员工卡
            R.raw.w06,//19 管理卡
            R.raw.w07,//20 请投币
            R.raw.w08,//21 非法卡
            R.raw.w11,//22 请充值
            R.raw.w12,//23 错误
            R.raw.w13,//24 重新刷卡
            R.raw.w14,//25 上班
            R.raw.w15,//26 下班
            R.raw.w16,//27 请买票
            R.raw.w18,//28 叮
            R.raw.w19,//29 补票
            R.raw.w20,//30 请上车
            R.raw.w21,//31 请下车
            R.raw.w23,//32 爱心卡
            R.raw.w24,//33 优惠卡
            R.raw.w25,//34  叮..
            R.raw.w26,//35  请X检
            R.raw.w27,//36  卡失效
            R.raw.w28,//37  荣军卡
            R.raw.w29,//38  叮叮
            R.raw.w30,//39  无偿献血卡
            R.raw.xiaban,//40  成功下班
            R.raw.yinlian,//41  银联卡
            R.raw.yuangong,//42  员工卡
            R.raw.axkx,//43 爱心卡请下车
            R.raw.axks,//44 爱心卡请上车
            R.raw.lnkx,//45 老李卡 请下车
            R.raw.lnks,//46  老李卡 请上车
            R.raw.didididi,//47 滴滴滴滴
    };


    /**
     * 初始化
     *
     * @param context
     */
    public static SoundPoolUtil init(Context context) {
        if (soundPlayUtils == null) {
            soundPlayUtils = new SoundPoolUtil();
        }
        mContext = context;
        for (int music : musics) {
            mSoundPlayer.load(mContext, music, 1);
        }
        return soundPlayUtils;
    }

    /**
     * 播放声音
     *
     * @param soundID .
     */
    public static void play(int soundID) {
        int play = mSoundPlayer.play(soundID, 1, 1, 0, 0, 1);
        if (play == 0) {
            if (soundID > 0) {
                playMedia(musics[soundID - 1]);
            }
        }
    }

    private static void playMedia(int soundID) {
        try {
            if (mediaPlayer != null && mediaPlayer.isPlaying()) {
                mediaPlayer.stop();
                mediaPlayer.release();
                mediaPlayer = null;
            }
            mediaPlayer = MediaPlayer.create(App.getInstance(), soundID);
            mediaPlayer.setAudioStreamType(AudioManager.STREAM_MUSIC);
            mediaPlayer.setLooping(false);
            mediaPlayer.start();
        } catch (Exception e) {
            e.printStackTrace();
        }

    }

    public static void release() {
        if (mSoundPlayer != null)
            mSoundPlayer.release();
    }
}
