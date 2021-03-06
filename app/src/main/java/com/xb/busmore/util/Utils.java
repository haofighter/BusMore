package com.xb.busmore.util;

import android.content.Context;
import android.net.ConnectivityManager;
import android.net.NetworkInfo;
import android.util.Log;


import com.xb.busmore.base.App;

import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.text.DecimalFormat;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 作者: Tangren on 2017/7/8
 * 包名：com.szxb.onlinbus.util
 * 邮箱：996489865@qq.com
 * TODO:通用的工具类
 */

public class Utils {
    static final String REG = "s[a-zA-z_0-9]{5,7}z[0-9]{2,3}x[/:+*_%&#]b[a-zA-z_0-9.-]{2,4}";


    public static boolean checkQR(long currentTime, long lastTime) {
        return currentTime - lastTime > 2000;
    }

    /**
     * 合并byte数组
     */
    public static byte[] unitByteArray(byte[] byte1, byte[] byte2) {

        byte[] unitByte = new byte[byte1.length + byte2.length];
        System.arraycopy(byte1, 0, unitByte, 0, byte1.length);
        System.arraycopy(byte2, 0, unitByte, byte1.length, byte2.length);
        return unitByte;

    }

    public static byte[] MergeArray(byte[] byte1, byte[]... bytes) {
        byte[] Merage = byte1;
        for (int i = 0; i < bytes.length; i++) {
            byte[] newInt = unitByteArray(Merage, bytes[i]);
            Merage = newInt;
        }

        return Merage;

    }

    public static byte[] hexStringToByte(String hex) {
        hex = hex.toUpperCase();
        int len = (hex.length() / 2);
        byte[] result = new byte[len];
        char[] achar = hex.toCharArray();
        for (int i = 0; i < len; i++) {
            int pos = i * 2;
            result[i] = (byte) (toByte(achar[pos]) << 4 | toByte(achar[pos + 1]));
        }
        return result;
    }

    private static byte toByte(char c) {
        byte b = (byte) "0123456789ABCDEF".indexOf(c);
        return b;
    }

    /**
     * 随机字符串
     *
     * @param length
     * @return
     */
    public static String Random(int length) {
        char[] ss = new char[length];
        int i = 0;
        while (i < length) {
            int f = (int) (Math.random() * 5);
            if (f == 0)
                ss[i] = (char) ('A' + Math.random() * 26);
            else if (f == 1)
                ss[i] = (char) ('a' + Math.random() * 26);
            else
                ss[i] = (char) ('0' + Math.random() * 10);
            i++;
        }
        String is = new String(ss);
        return is;
    }

    //读取文件类容转成string
    public static String readSaveFile(String filename) {
        FileInputStream inputStream;
        StringBuilder sb = new StringBuilder("");
        try {
            inputStream = new FileInputStream(filename);
            byte temp[] = new byte[1024];

            int len = 0;
            while ((len = inputStream.read(temp)) > 0) {
                sb.append(new String(temp, 0, len));
            }
            Log.d("msg", "readSaveFile: \n" + sb.toString());
            inputStream.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 根据byte数组，生成文件
     *
     * @param bfile    文件数组
     * @param filePath 文件存放路径
     * @param fileName 文件名称
     */
    public static void byte2File(byte[] bfile, String filePath, String fileName) {

        BufferedOutputStream bos = null;
        FileOutputStream fos = null;
        File file = null;
        try {
            File dir = new File(filePath);
            if (!dir.exists() && !dir.isDirectory()) {//判断文件目录是否存在
                dir.mkdirs();
            }
            file = new File(filePath + fileName);
            fos = new FileOutputStream(file);
            bos = new BufferedOutputStream(fos);
            bos.write(bfile);
        } catch (Exception e) {
            System.out.println(e.getMessage());
            e.printStackTrace();
        } finally {
            try {
                if (bos != null) {
                    bos.close();
                }
                if (fos != null) {
                    fos.close();
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
    }

    private static final char[] hexCode = "0123456789ABCDEF".toCharArray();

    /**
     * 将byte转成hex的字符串
     *
     * @param data
     * @return
     */
    public static String printHexBinary(byte[] data) {
        StringBuilder r = new StringBuilder(data.length * 2);
        for (byte b : data) {
            r.append(hexCode[(b >> 4) & 0xF]);
            r.append(hexCode[(b & 0xF)]);
        }
        return r.toString();
    }

    //打开目录文件
    public static byte[] File2byte(String filePath) {
        byte[] buffer = null;
        try {

            File file = new File(filePath);
            FileInputStream fis = new FileInputStream(file);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            byte[] b = new byte[1024];
            int n;
            while ((n = fis.read(b)) != -1) {
                bos.write(b, 0, n);
            }
            fis.close();

            bos.close();
            buffer = bos.toByteArray();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return buffer;
    }


    /**
     * 拼接两个数组
     *
     * @param byte_1
     * @param byte_2
     * @return
     */
    public static byte[] byteMerger(byte[] byte_1, byte[] byte_2) {
        byte[] byte_3 = new byte[byte_1.length + byte_2.length];
        System.arraycopy(byte_1, 0, byte_3, 0, byte_1.length);
        System.arraycopy(byte_2, 0, byte_3, byte_1.length, byte_2.length);
        return byte_3;
    }


    /**
     * @param value
     * @param len
     * @return
     */
    public static byte[] int2Bytes(int value, int len) {
        byte[] b = new byte[len];
        for (int i = 0; i < len; i++) {
            b[len - i - 1] = (byte) ((value >> 8 * i) & 0xff);
        }
        return b;
    }

    /**
     * 获取到yyyyMMddHHmmss时间的字符
     *
     * @return
     */
    public static String getStringDate() {
        Date currentTime = new Date();
        SimpleDateFormat formatter = new SimpleDateFormat("yyyyMMddHHmmss");
        String dateString = formatter.format(currentTime);
        return dateString;
    }


    /**
     * 将分转换城元
     *
     * @param prices
     * @return
     */
    public static String fen2Yuan(int prices) {
        DecimalFormat format = new DecimalFormat("0.00");
        return format.format((float) prices / (float) 100);
    }


    /**
     * 获取版本信息
     *
     * @param context
     * @return
     */
    public static String version(Context context) {
        try {
            String pkName = context.getPackageName();
            String versionName = context.getPackageManager().getPackageInfo(
                    pkName, 0).versionName;
            int versionCode = context.getPackageManager()
                    .getPackageInfo(pkName, 0).versionCode;
            return "V:" + versionName;
        } catch (Exception e) {
        }
        return "V:1.1";
    }


    //获取小数点后6位
    public static double get6Double(double dou) {
        DecimalFormat df = new DecimalFormat("#.000000");
        String str = df.format(dou);
        return Double.parseDouble(str);
    }


    /**
     * 判断字符串是否为空
     *
     * @param string
     * @return
     */
    public static boolean StringIsEmpty(String string) {
        if (string == null)
            return true;
        if ("".equals(string))
            return true;
        return false;
    }

    /**
     * @param var .
     * @return 是否是全数字
     */
    public static boolean isAllNum(String var) {
        Pattern pattern = Pattern.compile("[0-9]*");
        Matcher matcher = pattern.matcher(var);
        return matcher.matches();
    }


    /**
     * 校验秘钥
     *
     * @param var .
     * @return .
     */
    public static boolean verify(String var) {
        return Pattern.matches(REG, var);
    }

    /**
     * 效验IP
     *
     * @param var
     * @return
     */
    public static boolean isIP(String var) {
        String reg = "([1-9]|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])(\\.(\\d|[1-9]\\d|1\\d{2}|2[0-4]\\d|25[0-5])){3}";
        return Pattern.matches(reg, var);
    }


    public static final String AL_APPID = "24640563-1";

    public static final String AL_APPSECRET = "fbaebeb6aeccca7244150d892cbd0183";

    public static final String AL_RSASECRET = "MIIEvAIBADANBgkqhkiG9w0BAQEFAASCBKYwggSiAgEAAoIBAQCFZ6AE8OrBy76/H44Hll7gSTUH+Y5jO8jXBvn0ztD1DN7VuIHU16pFVMkIxdIV5U5puqsRyjzG7NmNHQ/5ugXcaMStRSrmxgkhV+4mLF4AFIG6Q4SGqKBWbdeY2biG9Pr/Kho8fQkfn4bVbShcDjDlb7+3EiBBmo0EBvM5Cx5+d3iOmsMkDp08/7mvlsi2RZB6BE0E3vXxxWqNpd9vcNA9MF2BUv3rWSFTvKFFhJMPowO5esNkDucCJDujByGeYR3nfHVJsToGMgyH1naLbR84iFDElJD4e/MGE1p9RahHAvZC2rIsJuCjOjKRkRlinrUtJ46B4voRFBSHTD80+rb7AgMBAAECggEAF9WczGUyvC8nxEPh/08JYm8R73vRyb77shdH/0oYIUAMbAjMAXgKrtpoFI4ra8gNS2yTddk5JJ1afRDNMuPeejOpt/9cm+CT7wYjwWQ54y/Gwb2gZuKnyulTFLcJdes9EOu3xkjcoW7CC022YUQTzlo52X5+jtoLCcYoHG/1Icj6iL1K/Vy5hzQV4W9iaw6slyojfmb+jNqzn/Cj55txTb3tRf8h3J5c7x4+iW5Z0DLnV7pihJx4I7qRtEING4HMXJGgeRw6Fh8Oey1uJ2g06a0/JHRfXdImqfv66kZZSbR3wnEIXtoKkDj4ABbwurk7OWIPYqVhd6ou+jCXo04DoQKBgQC/YR65knT7ygisu2Zc+4c8bOpq+uaA7pg4p2EAY65NBT9OuMC+wpKIpd5Vg4K5qsDHSm+kA5jxaj9/W1JlEGdndI4C8miw0Gk816mJrMStQQsU7Sm3AIANfgAO3irUm93nCImxPbiaUCMHf3EsYOd/tQgZ4U/S9szEOK6vj+1oCwKBgQCycyrXmKV7q6TMVfT/vGGXSPgnry65L6nOeNYXmfl1ImapS2/9WwNjA/wgFOTwtd9WlzDah1WbVhzOUj/jzyyt756OcPrDXAVFyjHsvfxqffJF7VXSmLEA4T4bO2EmRItqEp3m2chX5abk6NNoTW4N/fys/3IdToo+6tyTV4gS0QKBgCIcTF6lFQa/1tX6vN770fnaK9oPiEAYxrbqVma8XZfKxKLiWTBm+7kxXzR8sTMolR2wTb+9CGiXGGQ5wbDTwKp24szxqelB7E/03Hn2ekOrIS+Eqfe9NhWxjI8Z2CZ29ZE1O12E879FY0j11RUk3H4tpRj/yLuAU7TxqWkPTTBLAoGAPHK2uIGEzPe/w1f6vgZaKJ0XMpotAZ4br01PeH83+W3FyOM89F24L/pRlax+QOva08IzUlM1tIDfESTbb5fmUqUw2J980mCBiLqNXtycfJZdU0KqJ8SBcQLbvu3GYuglMbFMu75aFyw79J7JvjM8bJD03sVJKpRIUDfOsDqeZYECgYAYN7L2qhGi97zEYGaA7Ar9bQWABfQ3LQZEcjOUFbUqFPaaQSBg1uSQQ2TnmU9TG0QB42ueYkAwSj05z4WL3wNk+7EOQKgjakTWyhcphuf+jnOK2HChtwF1JOGTu8ZGF5hVLLUtGtz/8pn7dm6tdZKANnbMcSeZc3N8MJEgj6OM7Q==";

    /**
     * 是否有网络
     *
     * @return boolean
     */
    public static boolean checkNetStatus() {
        ConnectivityManager cm = (ConnectivityManager) App.getInstance().getSystemService(Context.CONNECTIVITY_SERVICE);
        NetworkInfo current = cm.getActiveNetworkInfo();
        return current != null && current.isAvailable();
    }


    public static String str2three(String str) {
        String endStr = "";
        if (str.length() < 3) {
            for (int i = 0; i < 3; i++) {
                if (str.length() - i <= 0) {
                    endStr += "0";
                }
            }
            endStr += str;
            return endStr;
        } else {
            return str.substring(0, 3);
        }
    }
}
