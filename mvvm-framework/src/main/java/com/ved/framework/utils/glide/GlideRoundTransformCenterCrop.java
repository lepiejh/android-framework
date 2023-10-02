package com.ved.framework.utils.glide;

import android.graphics.Bitmap;
import android.graphics.BitmapShader;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.RectF;

import com.bumptech.glide.load.engine.bitmap_recycle.BitmapPool;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;

import androidx.annotation.NonNull;

/**
 * Glide .centerCrop() 和transform圆角图片不能同时存在解决方法
 *
 * @author LSQ
 */
public class GlideRoundTransformCenterCrop extends CenterCrop {

    private int mRadius;

    public GlideRoundTransformCenterCrop() {
    }

    public GlideRoundTransformCenterCrop(int radius) {
        mRadius = radius;
    }

    @Override
    protected Bitmap transform(@NonNull BitmapPool pool, @NonNull Bitmap toTransform, int outWidth, int outHeight) {
        Bitmap transform = super.transform(pool, toTransform, outWidth, outHeight);
        return roundCrop(pool, transform);
    }

    @SuppressWarnings("ConstantConditions")
    private Bitmap roundCrop(BitmapPool pool, Bitmap source) {
        if(source == null || mRadius <= 0) {
            return source;
        }
        Bitmap result = pool.get(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        if(result == null) {
            result = Bitmap.createBitmap(source.getWidth(), source.getHeight(), Bitmap.Config.ARGB_8888);
        }
        Canvas canvas = new Canvas(result);
        Paint paint = new Paint();
        paint.setShader(new BitmapShader(source, BitmapShader.TileMode.CLAMP, BitmapShader.TileMode.CLAMP));
        paint.setAntiAlias(true);
        RectF rectF = new RectF(0f, 0f, source.getWidth(), source.getHeight());
        canvas.drawRoundRect(rectF, mRadius, mRadius, paint);
        return result;
    }

    //    @Override
    //    public void updateDiskCacheKey(@NonNull MessageDigest messageDigest) {
    //
    //    }
}
