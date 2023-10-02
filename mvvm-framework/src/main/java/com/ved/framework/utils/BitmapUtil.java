package com.ved.framework.utils;

import android.app.Activity;
import android.content.res.Resources;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.Environment;
import android.util.Base64;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.Random;

/**
 * Bitmap工具类
 */
public final class BitmapUtil {

    /**
     * 计算图片的缩放值, 算法1
     */
    private static int calculateInSampleSize(BitmapFactory.Options options, int reqWidth, int
            reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;
        if (height > reqHeight || width > reqWidth) {
            final int halfHeight = height / 2;
            final int halfWidth = width / 2;
            while ((halfHeight / inSampleSize) > reqHeight && (halfWidth / inSampleSize) >
                    reqWidth) {
                inSampleSize *= 2;
            }
        }
        return inSampleSize;
    }

    /**
     * 计算图片的缩放值,算法2
     */
    public static int calculateInSampleSize2(BitmapFactory.Options options, int reqWidth, int
            reqHeight) {
        final int height = options.outHeight;
        final int width = options.outWidth;
        int inSampleSize = 1;

        if (height > reqHeight || width > reqWidth) {
            final int heightRatio = Math.round((float) height / (float) reqHeight);
            final int widthRatio = Math.round((float) width / (float) reqWidth);
            inSampleSize = heightRatio < widthRatio ? heightRatio : widthRatio;
        }
        return inSampleSize;
    }

    // 根据路径获得图片并压缩，返回bitmap用于显示
    public static Bitmap getSmallBitmap(String filePath) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(filePath, options);

        // Calculate inSampleSize
        options.inSampleSize = calculateInSampleSize(options, 480, 800);

        // Decode signBitmap with inSampleSize set
        options.inJustDecodeBounds = false;

        return BitmapFactory.decodeFile(filePath, options);
    }

    // 如果是放大图片，filter决定是否平滑，如果是缩小图片，filter无影响
    private static Bitmap createScaleBitmap(Bitmap src, int dstWidth, int dstHeight) {
        Bitmap dst = Bitmap.createScaledBitmap(src, dstWidth, dstHeight, false);
        if (src != dst) {   // 如果没有缩放，那么不回收
            src.recycle();  // 释放Bitmap的native像素数组
        }
        return dst;
    }

    // 从Resources中加载图片
    public static Bitmap decodeSampledBitmapFromResource(Resources res, int resId, int reqWidth,
                                                         int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeResource(res, resId, options); // 读取图片长款
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight); //
        // 计算inSampleSize
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeResource(res, resId, options); // 载入一个稍大的缩略图
        return createScaleBitmap(src, reqWidth, reqHeight); // 进一步得到目标大小的缩略图
    }

    // 从sd卡上加载图片
    public static Bitmap decodeSampledBitmapFromFd(String pathName, int reqWidth, int reqHeight) {
        final BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        BitmapFactory.decodeFile(pathName, options);
        options.inSampleSize = calculateInSampleSize(options, reqWidth, reqHeight);
        options.inJustDecodeBounds = false;
        Bitmap src = BitmapFactory.decodeFile(pathName, options);
        return createScaleBitmap(src, reqWidth, reqHeight);
    }

    //把bitmap转换成String
    public static String bitmapToString(String filePath) {

        Bitmap bm = getSmallBitmap(filePath);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bm.compress(Bitmap.CompressFormat.JPEG, 40, baos);
        byte[] b = baos.toByteArray();
        return Base64.encodeToString(b, Base64.DEFAULT);
    }

    /**
     * 修正图片大小,图片压缩后小于100k,失真不明显
     */
    public static Bitmap revisionImageSize(String path) throws IOException {
        if (path == null) {
            return null;
        }
        BufferedInputStream in = new BufferedInputStream(new FileInputStream(
                new File(path)));
        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inJustDecodeBounds = true;
        //noinspection deprecation
        options.inInputShareable = true;
        //noinspection deprecation
        options.inPurgeable = true;
        BitmapFactory.decodeStream(in, null, options);
        in.close();
        int i = 0;
        Bitmap bitmap = null;
        while (true) {
            if ((options.outWidth >> i <= 1000)
                    && (options.outHeight >> i <= 1000)) {
                in = new BufferedInputStream(
                        new FileInputStream(new File(path)));
                options.inSampleSize = (int) Math.pow(2.0D, i);
                options.inJustDecodeBounds = false;
                bitmap = BitmapFactory.decodeStream(in, null, options);
                break;
            }
            i += 1;
        }
        return bitmap;
    }

    public static Bitmap getCompressedBitmap(Activity act, String filepath) {
        Bitmap tempBitmap = null;
        float width = act.getResources().getDisplayMetrics().widthPixels;
        float height = act.getResources().getDisplayMetrics().heightPixels;
        try {
            if (width > 640) {
                tempBitmap = getSuitableBitmap(act, Uri.fromFile(new File(filepath)),
                        640, (640 / width) * height);
            } else {
                tempBitmap = getSuitableBitmap(act, Uri.fromFile(new File(filepath)),
                        (int) width, (int) height);
            }

        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }

        return tempBitmap;
    }

    public static Bitmap getSuitableBitmap(Activity act, Uri uri, float ww, float hh)
            throws FileNotFoundException {
        BitmapFactory.Options newOpts = new BitmapFactory.Options();
        newOpts.inJustDecodeBounds = true;//只读边,不读内容
        Bitmap bitmap = null;
        bitmap = BitmapFactory.decodeStream(act.getContentResolver().openInputStream(uri), null,
                newOpts);

        newOpts.inJustDecodeBounds = false;
        float w = newOpts.outWidth;
        float h = newOpts.outHeight;

        float wwh = 640f;//
        float hhh = (wwh / w) * h;//
        int be = 1;
        if (w > h && w > wwh) {
            be = (int) (newOpts.outWidth / wwh);
        } else if (w < h && h > hhh) {
            be = (int) (newOpts.outHeight / hhh);
            be += 1;
        }
        if (be <= 0)
            be = 1;
        newOpts.inSampleSize = be;//设置采样率

        newOpts.inPreferredConfig = Bitmap.Config.ARGB_8888;//该模式是默认的,可不设
        //noinspection deprecation
        newOpts.inPurgeable = true;// 同时设置才会有效
        //noinspection deprecation
        newOpts.inInputShareable = true;//。当系统内存不够时候图片自动被回收

        bitmap = BitmapFactory.decodeStream(act.getContentResolver().openInputStream(uri), null,
                newOpts);
        //      return compressBmpFromBmp(signBitmap);//原来的方法调用了这个方法企图进行二次压缩
        //其实是无效的,大家尽管尝试
        return bitmap;

        //        int maxNumOfPixels = width * height;
        //        BitmapFactory.Options opts = new BitmapFactory.Options();
        //        opts.inJustDecodeBounds = true;
        //        BitmapFactory.decodeStream(this.getContentResolver().openInputStream(uri),
        // null, opts);
        //        opts.inSampleSize = computeSampleSize(opts, -1, maxNumOfPixels);
        //        opts.inJustDecodeBounds = false;
        //        try {
        //            return BitmapFactory.decodeStream(this.getContentResolver().openInputStream
        // (uri), null,
        //                    opts);
        //        } catch (OutOfMemoryError err) {
        //        }
        //        return null;
    }

    /**
     * Bitmap对象保存为图片文件
     */
    public static String saveBitmapFile(Bitmap bitmap) {
        String fileName = EncryptUtil.Md5Util.encodeStringByMD5((System
                .currentTimeMillis() + new Random().nextLong() * 500) + "") + ".png";
        if (Environment.MEDIA_MOUNTED.equals(Environment.getExternalStorageState())) {
            String filePath = PathUtil.getCacheDir() + "/img";
            FileUtils.createDir(filePath);
            File file = new File(filePath, fileName);//将要保存图片的路径
            try {
                BufferedOutputStream bos = new BufferedOutputStream(new FileOutputStream(file));
                bitmap.compress(Bitmap.CompressFormat.PNG, 100, bos);
                bos.flush();
                bos.close();
                return file.getAbsolutePath();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        return "";
    }

    // 把Bitmap转换成Base64
    public static String bitmapToBase64(Bitmap bitmap) {
        byte[] bytes = bitmapToBytes(bitmap);
        return Base64.encodeToString(bytes, Base64.NO_WRAP);
    }

    public static byte[] bitmapToBytes(Bitmap bitmap) {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 80, baos);
        return baos.toByteArray();
    }

    // 把Base64转换成Bitmap
    public static Bitmap base64ToBitmap(String iconBase64) {
        byte[] bitmapArray = Base64.decode(iconBase64, Base64.DEFAULT);
        return BitmapFactory.decodeByteArray(bitmapArray, 0, bitmapArray.length);
    }

    public static byte[] base64ToBytes(String base64Str) {
        return Base64.decode(base64Str, Base64.DEFAULT);
    }
}
