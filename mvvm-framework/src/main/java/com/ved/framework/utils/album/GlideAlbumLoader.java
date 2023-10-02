package com.ved.framework.utils.album;

import android.graphics.drawable.Drawable;
import android.webkit.URLUtil;
import android.widget.ImageView;
import com.bumptech.glide.Glide;
import com.bumptech.glide.RequestBuilder;
import com.ved.framework.R;
import com.ved.framework.utils.glide.GlideManager;
import com.ved.framework.utils.glide.GlideType;
import com.yanzhenjie.album.AlbumFile;
import com.yanzhenjie.album.AlbumLoader;
import com.yanzhenjie.album.app.album.data.ThumbnailBuilder;

import java.io.File;

/**
 * Glide图片加载
 *
 * @author LSQ
 */
public class GlideAlbumLoader implements AlbumLoader {

    @Override
    public void load(ImageView imageView, AlbumFile albumFile) {
        String filePath = null;
        // 视频
        int mediaType = albumFile.getMediaType();
        if(mediaType == AlbumFile.TYPE_VIDEO) {
            filePath = new ThumbnailBuilder(imageView.getContext()).createThumbnailForVideo(albumFile.getPath());
        }
        // 图片或其他
        else if(mediaType == AlbumFile.TYPE_IMAGE) {
            filePath = albumFile.getPath();
        }
        RequestBuilder<Drawable> request;
        if(filePath != null) {
            request = Glide.with(imageView).load(filePath);
        } else {
            request = Glide.with(imageView).load(R.drawable.cb);
        }
        request.apply(GlideManager.getRequestOptionsWithType(R.drawable.cb, GlideType.FIT_CENTER)).into(imageView);
    }

    @Override
    public void load(ImageView imageView, String url) {
        RequestBuilder<Drawable> request;
        if(URLUtil.isNetworkUrl(url)) {
            request = Glide.with(imageView).load(url);
        } else {
            request = Glide.with(imageView).load(new File(url));
        }
        request.apply(GlideManager.getRequestOptionsWithType(R.drawable.cb, GlideType.FIT_CENTER)).into(imageView);
    }
}
