package com.xb.busmore.util.net.ftpUtils;

import android.text.TextUtils;
import android.util.Log;

import com.xb.busmore.base.App;
import com.xb.busmore.util.BusToast;

import org.apache.commons.net.ftp.FTPClient;
import org.apache.commons.net.ftp.FTPClientConfig;
import org.apache.commons.net.ftp.FTPFile;
import org.apache.commons.net.ftp.FTPReply;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.util.LinkedList;

public class FTP {
    public static final String FTP_CONNECT_SUCCESSS = "ftp连接成功";
    public static final String FTP_CONNECT_FAIL = "ftp连接失败";
    public static final String FTP_DISCONNECT_SUCCESS = "ftp断开连接";
    public static final String FTP_FILE_NOTEXISTS = "ftp上文件不存在";

    public static final String FTP_UPLOAD_SUCCESS = "ftp文件上传成功";
    public static final String FTP_UPLOAD_FAIL = "ftp文件上传失败";
    public static final String FTP_UPLOAD_LOADING = "ftp文件正在上传";

    public static final String FTP_DOWN_LOADING = "ftp文件正在下载";
    public static final String FTP_DOWN_SUCCESS = "ftp文件下载成功";
    public static final String FTP_DOWN_FAIL = "ftp文件下载失败";

    public static final String FTP_DELETEFILE_SUCCESS = "ftp文件删除成功";
    public static final String FTP_DELETEFILE_FAIL = "ftp文件删除失败";

    public static int downcount = 0;

    /**
     * 服务器名.
     */
    private String hostName;

    /**
     * 端口号
     */
    private int serverPort;

    /**
     * 用户名.
     */
    private String userName;

    /**
     * 密码.
     */
    private String password;

    public FTP() {
        if (ftpClient == null) {
            this.ftpClient = new FTPClient();
        }
    }

    public FTP setHostName(String hostName) {
        this.hostName = hostName;
        return this;
    }

    public FTP setServerPort(int serverPort) {
        this.serverPort = serverPort;
        return this;
    }


    public FTP setUserName(String userName) {
        this.userName = userName;
        return this;
    }


    public FTP setPassword(String password) {
        this.password = password;
        return this;
    }

    public FTPClient getFtpClient() {
        if (ftpClient.isConnected()) {
            try {
                openConnect();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return ftpClient;
    }

    /**
     * FTP连接.
     */
    private FTPClient ftpClient;
//          .builder("60.210.101.86")
//                        .setPort(8586)
//                        .setLogin("gongjiaosaoma", "Modern87")


    // -------------------------------------------------------文件上传方法------------------------------------------------

    /**
     * 上传单个文件.
     *
     * @return true上传成功, false上传失败
     * @throws IOException
     */
    public boolean uploadingSingle(String filePath, String fileName, String ftpPath,
                                   UploadProgressListener listener) throws IOException {
        boolean flag = false;
        // 不带进度的方式
        // // 创建输入流
        // InputStream inputStream = new FileInputStream(localFile);
        // // 上传单个文件
        // flag = ftpClient.storeFile(localFile.getName(), inputStream);
        // // 关闭文件流
        // inputStream.close();
        this.uploadBeforeOperate(ftpPath, listener);
        File file;
        try {
            file = new File(filePath + fileName);
            // 带有进度的方式
            BufferedInputStream buffIn = new BufferedInputStream(
                    new FileInputStream(file));
            ProgressInputStream progressInput = new ProgressInputStream(buffIn,
                    listener, file);
            flag = ftpClient.storeFile(file.getName(), progressInput);
            buffIn.close();
        } catch (Exception e) {
            return false;
        }
        try {
            if (ftpClient.listFiles(fileName)[0].getSize() == file.length()) {

                Log.i("MI  info", "uploadingSingle(FTP.java)181)  校验服务器文件成功");
                listener.onUploadProgress(FTP.FTP_UPLOAD_SUCCESS, file.length(), file);
                return true;
            } else {

                Log.i("MI  info", "uploadingSingle(FTP.java)181)  校验服务器文件失败");
                listener.onUploadProgress(FTP.FTP_UPLOAD_FAIL, ftpClient.listFiles(fileName)[0].getSize(), null);
                return false;
            }
        } catch (Exception e) {
            Log.i("MI  info", "uploadingSingle(FTP.java)182) 校验服务器文件错误");
            listener.onUploadProgress(FTP.FTP_UPLOAD_FAIL, ftpClient.listFiles(fileName)[0].getSize(), null);
            return false;
        }
    }

    /**
     * 上传文件之前初始化相关参数
     *
     * @param remotePath FTP目录
     * @param listener   监听器
     * @throws IOException
     */
    private void uploadBeforeOperate(String remotePath,
                                     UploadProgressListener listener) throws IOException {

        // 打开FTP服务
        try {
            this.openConnect();
        } catch (IOException e1) {
            e1.printStackTrace();
            listener.onUploadProgress(FTP.FTP_UPLOAD_FAIL, 0, null);
            Log.e("FTP", "uploadBeforeOperate(FTP.java:178)FTP连接失败");
            return;
        }

        // 设置模式
        ftpClient.setFileTransferMode(org.apache.commons.net.ftp.FTP.STREAM_TRANSFER_MODE);
        // FTP下创建文件夹
        ftpClient.makeDirectory(remotePath);
        // 改变FTP目录
        ftpClient.changeWorkingDirectory(remotePath);
        // 上传单个文件

    }

    /**
     * 上传完成之后关闭连接
     *
     * @param listener
     * @throws IOException
     */
    private void uploadAfterOperate(UploadProgressListener listener)
            throws IOException {
        this.closeConnect();
        //	listener.onUploadProgress(FTP_DISCONNECT_SUCCESS, 0, null);
    }

    // -------------------------------------------------------文件下载方法------------------------------------------------

    public File downloadSingleFile(String serverPath, String localPath,
                                   String fileName) throws Exception {
        return downloadSingleFile(serverPath, localPath, fileName, new DownLoadProgressListener() {
            @Override
            public void onDownLoadProgress(String currentStep, long downProcess, File file) {

            }
        });
    }


    /**
     * 下载单个文件，可实现断点下载.
     *
     * @param serverPath Ftp目录及文件路径
     * @param localPath  本地目录
     * @param fileName   下载之后的文件名称
     * @param listener   监听器
     * @throws IOException
     */
    //String localpath="/mnt/sdcard";
    public File downloadSingleFile(String serverPath, String localPath,
                                   String fileName, DownLoadProgressListener listener)
            throws Exception {
        // 打开FTP服务
        try {
            this.openConnect();
            listener.onDownLoadProgress(FTP_CONNECT_SUCCESSS, 0,
                    null);
        } catch (IOException e1) {
            e1.printStackTrace();
            listener.onDownLoadProgress(FTP_CONNECT_FAIL, 0, null);
            return null;
        }

        // 先判断服务器文件是否存在
        FTPFile[] files = ftpClient.listFiles(serverPath);
        if (files.length == 0) {
            listener.onDownLoadProgress(FTP_FILE_NOTEXISTS, 0, null);
            return null;
        }

        // 创建本地文件夹
        File mkFile = new File(localPath);
        if (!mkFile.exists()) {
            mkFile.mkdirs();
        }

        //本地文件路径
        localPath = localPath + fileName;

        // 接着判断下载的文件是否能断点下载
        File localFile = new File(localPath);
        long localSize = 0;
        if (localFile.exists()) {
            localFile.delete();
        }
        localFile.createNewFile();


        long currentSize = 0;
        // 开始准备下载文件
        OutputStream out = new FileOutputStream(localFile, true);
        ftpClient.setRestartOffset(localSize);
        InputStream input = ftpClient.retrieveFileStream(serverPath);
        byte[] b = new byte[1024];
        int length = 0;
        while ((length = input.read(b)) != -1) {
            out.write(b, 0, length);
            currentSize = currentSize + length;
        }
        out.flush();
        out.close();
        input.close();

        // 此方法是来确保流处理完毕，如果没有此方法，可能会造成现程序死掉
        if (ftpClient.completePendingCommand()) {
            listener.onDownLoadProgress(FTP_DOWN_SUCCESS, 0,
                    new File(localPath));
        } else {

            listener.onDownLoadProgress(FTP_DOWN_FAIL, 0, null);
        }

        Log.e("FTP", "downloadSingleFile(FTP.java:296)" + localFile.length() + "         " + ftpClient.listFiles(serverPath)[0].getSize());
        if (localFile.length() != ftpClient.listFiles(serverPath)[0].getSize()) {
            return null;
        }


        // 下载完成之后关闭连接
        this.closeConnect();
        listener.onDownLoadProgress(FTP_DISCONNECT_SUCCESS, 0,
                null);

        return localFile;
    }


    // -------------------------------------------------------文件删除方法------------------------------------------------

    /**
     * 删除Ftp下的文件.
     *
     * @param serverPath Ftp目录及文件路径
     * @param listener   监听器
     * @throws IOException
     */
    public void deleteSingleFile(String serverPath,
                                 DeleteFileProgressListener listener) throws Exception {

        // 打开FTP服务
        try {
            this.openConnect();
            listener.onDeleteProgress(FTP_CONNECT_SUCCESSS);
        } catch (IOException e1) {
            e1.printStackTrace();
            listener.onDeleteProgress(FTP_CONNECT_FAIL);
            return;
        }

        // 先判断服务器文件是否存在
        FTPFile[] files = ftpClient.listFiles(serverPath);
        if (files.length == 0) {
            listener.onDeleteProgress(FTP_FILE_NOTEXISTS);
            return;
        }

        // 进行删除操作
        boolean flag = true;
        flag = ftpClient.deleteFile(serverPath);
        if (flag) {
            listener.onDeleteProgress(FTP_DELETEFILE_SUCCESS);
        } else {
            listener.onDeleteProgress(FTP_DELETEFILE_FAIL);
        }

        // 删除完成之后关闭连接
        this.closeConnect();
        listener.onDeleteProgress(FTP_DISCONNECT_SUCCESS);

        return;
    }

    // -------------------------------------------------------打开关闭连接------------------------------------------------

    /**
     * 打开FTP服务.
     *
     * @throws IOException
     */
    public void openConnect() throws IOException {

        if (TextUtils.isEmpty(hostName)) {
            BusToast.showToast(App.getInstance(), "FTP地址未设置！", false);
            return;
        } else if (serverPort == 0) {
            BusToast.showToast(App.getInstance(), "FTP端口号未设置！", false);
            return;
        } else if (TextUtils.isEmpty(userName)) {
            BusToast.showToast(App.getInstance(), "FTP用户名未设置！", false);
            return;
        } else if (TextUtils.isEmpty(password)) {
            BusToast.showToast(App.getInstance(), "FTP密码未设置!", false);
            return;
        }

        ftpClient = new FTPClient();
        // 中文转码
        ftpClient.setControlEncoding("UTF-8");
        int reply; // 服务器响应值
        // 连接至服务器
        ftpClient.connect(hostName, serverPort);

        // 获取响应值
        reply = ftpClient.getReplyCode();
        System.out.println(reply + "reply");
        if (!FTPReply.isPositiveCompletion(reply)) {
            // 断开连接
            ftpClient.disconnect();
            throw new IOException("connect fail: " + reply);
        }
        // 登录到服务器
        ftpClient.login(userName, password);
        // 获取响应值
        reply = ftpClient.getReplyCode();


        if (!FTPReply.isPositiveCompletion(reply)) {
            // 断开连接
            ftpClient.disconnect();
            throw new IOException("connect fail: " + reply);
        } else {
            // 获取登录信息
            FTPClientConfig config = new FTPClientConfig(ftpClient
                    .getSystemType().split(" ")[0]);
            config.setServerLanguageCode("zh");
            ftpClient.configure(config);
            // 使用被动模式设为默认
            ftpClient.enterLocalPassiveMode();
            // 二进制文件支持
            ftpClient
                    .setFileType(org.apache.commons.net.ftp.FTP.BINARY_FILE_TYPE);
        }
    }

    /**
     * 关闭FTP服务.
     *
     * @throws IOException
     */
    public void closeConnect() throws IOException {
        if (ftpClient != null) {
            // 退出FTP
            ftpClient.logout();
            // 断开连接
            ftpClient.disconnect();
        }
    }

    public FTP conect() {
        try {
            openConnect();
        } catch (Exception e) {
            BusToast.showToast(App.getInstance(), "FTP连接失败", false);
        }
        return this;
    }

    // ---------------------------------------------------上传、下载、删除监听---------------------------------------------

    /*
     * 上传进度监听
     */
    public interface UploadProgressListener {
        public void onUploadProgress(String currentStep, long uploadSize,
                                     File file);
    }

    /*
     * 下载进度监听
     */
    public interface DownLoadProgressListener {
        public void onDownLoadProgress(String currentStep, long downProcess,
                                       File file);
    }

    /*
     * 文件删除监听
     */
    public interface DeleteFileProgressListener {
        public void onDeleteProgress(String currentStep);
    }


}