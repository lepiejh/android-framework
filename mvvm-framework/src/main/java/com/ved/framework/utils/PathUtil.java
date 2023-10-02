package com.ved.framework.utils;

import android.content.Context;
import android.os.Environment;

/**
 * 路径工具类
 * Created by Administrator on 2017/5/7.
 */
public class PathUtil {

    private static String mCacheDir;
    private static String mFileDir;
    private static String mStorageDir;
    private static String mStorageDCIMDir;

    public static void init(Context context) {
        mCacheDir = context.getCacheDir().getAbsolutePath();
        mFileDir = context.getFilesDir().getAbsolutePath();
        mStorageDir = Environment.getExternalStorageDirectory().getAbsolutePath();
        mStorageDCIMDir = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DCIM).getAbsolutePath();
    }

    /**
     * 内部储存，App下缓存文件夹
     */
    public static String getCacheDir() {
        return mCacheDir;
    }

    /**
     * 内部储存，App下文件存储文件夹
     */
    public static String getFileDir() {
        return mFileDir;
    }

    /**
     * 外部存储，根目录
     */
    public static String getStorageDir() {
        return mStorageDir;
    }

    /**
     * 外部存储，图片文件夹
     */
    public static String getStorageDCIMDir() {
        return mStorageDCIMDir;
    }
}
