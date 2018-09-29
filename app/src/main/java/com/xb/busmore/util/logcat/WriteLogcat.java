package com.xb.busmore.util.logcat;

import android.os.Environment;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileOutputStream;
import java.io.InputStreamReader;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;

/**
 * Created by Evergarden on 2018/3/11.
 */

public class WriteLogcat extends Thread {

    private FileOutputStream fos;
    private boolean captureLogThreadOpen=true;



    private int len;//读取条数
    private String filter_str;//过滤字段
    public WriteLogcat(String filter_str, int len){
            this.len=len;
            this.filter_str=filter_str;
    }


    @Override
    public void run() {
        Start();

    }

    void Start(){
        while(captureLogThreadOpen) {
            try {
                /*命令的准备*/
                ArrayList<String> getLog = new ArrayList<String>();
                getLog.add("logcat");
                getLog.add("-d");
                getLog.add("-v");
                getLog.add("time");

                ArrayList<String> clearLog = new ArrayList<String>();
                clearLog.add("logcat");
                clearLog.add("-c");
                Process process = Runtime.getRuntime().exec(getLog.toArray(new String[getLog.size()]));//抓取当前的缓存日志
                BufferedReader buffRead = new BufferedReader(new InputStreamReader(process.getInputStream()));//获取输入流
                Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));//清除是为了下次抓取不会从头抓取
                String str = null;
                File logFile = new File(Environment.getExternalStorageDirectory() + "/" + "log.txt");//打开文件
                fos = new FileOutputStream(logFile, true);//true表示在写的时候在文件末尾追加
                String newline = System.getProperty("line.separator");//换行的字符串
                int logCount = 0;

                while ((str = buffRead.readLine()) != null) {//循环读取每一行
                    if (str.indexOf(filter_str)!=-1) {
                        SimpleDateFormat format = new SimpleDateFormat("yyyyMMddHHmmss");
                        Date date = new Date(System.currentTimeMillis());
                        String time = format.format(date);
                        fos.write((time + str).getBytes());//加上年
                        fos.write(newline.getBytes());//换行
                        logCount++;
                        if (logCount > len) {//大于10000行就退出
                            captureLogThreadOpen = false;
                            fos.close();
                            break;
                        }
                    }
                }
                    fos.close();
                    fos = null;
                    Runtime.getRuntime().exec(clearLog.toArray(new String[clearLog.size()]));
                } catch(Exception e){

                }


        }
    }
}
