package com.ved.framework.utils.glide;

import android.graphics.drawable.Drawable;
import android.text.TextUtils;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.bitmap.BitmapTransformation;
import com.bumptech.glide.load.resource.bitmap.CenterCrop;
import com.bumptech.glide.load.resource.bitmap.CenterInside;
import com.bumptech.glide.load.resource.bitmap.CircleCrop;
import com.bumptech.glide.load.resource.bitmap.FitCenter;
import com.bumptech.glide.load.resource.bitmap.RoundedCorners;
import com.bumptech.glide.request.RequestOptions;
import com.ved.framework.R;
import com.ved.framework.utils.Utils;

import java.util.HashMap;
import java.util.Map;

import androidx.annotation.DimenRes;
import androidx.annotation.DrawableRes;
import androidx.annotation.NonNull;

/**
 * Glide配置管理
 *
 * @author LSQ
 */
public class GlideManager {

    private volatile static GlideManager INSTANCE;

    private GlideManager() {
        mRequestOptionsMap = new HashMap<>();
        mTransformationMap = new HashMap<>();
    }

    private static GlideManager getInstance() {
        if(INSTANCE == null) {
            synchronized (GlideManager.class) {
                if(INSTANCE == null) {
                    INSTANCE = new GlideManager();
                }
            }
        }
        return INSTANCE;
    }

    public static void setImageUri(ImageView imageView, String url, Drawable placeholderRes) {
        if (!TextUtils.isEmpty(url)) {
            Glide.with(imageView.getContext())
                    .load(url)
                    .apply(new RequestOptions().placeholder(placeholderRes))
                    .into(imageView);
        }else {
            imageView.setImageDrawable(placeholderRes);
        }
    }

    /**
     * RequestOptions集合
     */
    private Map<String, RequestOptions> mRequestOptionsMap;
    /**
     * Transformation集合
     */
    private Map<String, BitmapTransformation> mTransformationMap;

    /**
     * 获取RequestOptions
     */
    public static RequestOptions getRequestOptions() {
        return getRequestOptions(R.drawable.cb);
    }

    /**
     * 获取RequestOptions
     */
    public static RequestOptions getRequestOptionsWithAvatar() {
        return getRequestOptions(R.drawable.cb);
    }

    /**
     * 获取RequestOptions
     */
    public static RequestOptions getRequestOptions(@DrawableRes int resId) {
        return getRequestOptionsWithRadius(resId, 0);
    }

    /**
     * 获取RequestOptions - 默认Transformation
     */
    public static RequestOptions getRequestOptionsWithRadiusRes(@DrawableRes int resId, @DimenRes int radius) {
        return getRequestOptionsWithRadius(resId, (int) Utils.getContext().getResources().getDimension(radius));
    }

    /**
     * 获取RequestOptions - 默认Transformation
     */
    public static RequestOptions getRequestOptionsWithRadius(@DrawableRes int resId, int radius) {
        return getRequestOptions(resId, GlideType.DEFAULT, radius);
    }

    /**
     * 获取RequestOptions - 无圆角
     */
    public static RequestOptions getRequestOptionsWithType(@DrawableRes int resId, GlideType type) {
        return getRequestOptions(resId, type, 0);
    }

    /**
     * 获取RequestOptions
     */
    public static RequestOptions getRequestOptions(@DrawableRes int resId, GlideType type, int radius) {
        if(type == null) {
            type = GlideType.DEFAULT;
        }
        String key = getKeyWithRequestOptions(resId, type, radius);
        RequestOptions options = getInstance().mRequestOptionsMap.get(key);
        if(options == null) {
            options = new RequestOptions().placeholder(resId).error(resId).transform(getTransformation(type, radius));
            getInstance().mRequestOptionsMap.put(key, options);
        }
        return options;
    }

    /**
     * RequestOptions Key
     *
     * @param resId  默认图片
     * @param type   类型
     * @param radius 圆角
     */
    private static String getKeyWithRequestOptions(@DrawableRes int resId, @NonNull GlideType type, int radius) {
        return resId + "_" + type.getKey() + "_" + radius;
    }

    /**
     * Transformation Key
     *
     * @param type   类型
     * @param radius 圆角
     */
    private static String getKeyWithTransformation(@NonNull GlideType type, int radius) {
        return type.getKey() + "_" + radius;
    }

    /**
     * 获取Transformation
     *
     * @param type   类型
     * @param radius 圆角
     * @return BitmapTransformation
     */
    public static BitmapTransformation getTransformation(@NonNull GlideType type, int radius) {
        String key = getKeyWithTransformation(type, radius);
        BitmapTransformation transformation = getInstance().mTransformationMap.get(key);
        if(transformation == null) {
            transformation = createBitmapTransformation(type, radius);
            getInstance().mTransformationMap.put(key, transformation);
        }
        return transformation;
    }

    /**
     * 创建Transformation
     */
    private static BitmapTransformation createBitmapTransformation(@NonNull GlideType type, int radius) {
        switch (type) {
            case CENTER_CROP:
                return new CenterCrop();
            case CENTER_INSIDE:
                return new CenterInside();
            case FIT_CENTER:
                return new FitCenter();
            case ROUNDED_CORNERS:
                return radius > 0 ? new RoundedCorners(radius) : new GlideRoundTransformCenterCrop(radius);
            case CIRCLE_CROP:
                return new CircleCrop();
            case DEFAULT:
            default:
                return new GlideRoundTransformCenterCrop(radius);
        }
    }
}
