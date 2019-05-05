package com.ads.xinfa.base;

import android.os.Environment;

import java.io.File;

/**
 * 常量
 */
public class Constant {
    //是否是单元测试
    public static boolean isUnitTest = false;

    //URL
    public static final String URL = "http://192.168.0.2:16888/interface/interface1.aspx?rt=snapshot&es=131";
    public static final String URL_PREFIX = "http://";
    public static final String URL_PATH = "/interface/interface1.aspx?";

    //UDP收到的消息
    public static final int UPDATE_XML_FILE = 0X10;  //更新xml文件
    public static final int GET_SNAPSHOOT = 0x11;   //快照
    public static final int UPDATE_APK = 0X12;   //下载apk 更新
    public static final int INSTALL_APK = 0x13;  //安装apk

    //显示样式
    public static final String TYPE_SHOW_MARQUEE = "跑马灯";
    public static final String TYPE_SHOW_IMAGE_AND_VIDEO = "视图混播";
    public static final String TYPE_SHOW_IMAGE = "图片轮播";
    public static final String TYPE_SHOW_VIDEO = "视频轮播";

    //跑马灯运动轨迹
    public static final String MARQUEE_LEFT = "left";
    public static final String MARQUEE_RIGHT = "right";

    //UDP 发布过来的操作
    public static final String METHOD_UPDATE_XML = "更新";
    public static final String METHOD_GET_SNAPSHOOT = "快照";
    public static final String METHOD_UPDATE_APK = "升级";

    //下载apk存放路径
    public static final String NAME_DOWNLOAD_APK = "myxinfa.apk";
    public static final String PATH_DOWNLOAD_APK = Environment
            .getExternalStorageDirectory().getPath()+
            File.separator+
            "SZTY" +
            File.separator+
            "download";



    //指令集
    public static final String COMMAND_UPDATE = "更新";
    public static final String COMMAND_UPGRADE = "升级";
    public static final String COMMAND_SETTING = "配置";


}
