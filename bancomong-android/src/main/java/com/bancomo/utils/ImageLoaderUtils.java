package com.bancomo.utils;

import android.content.Context;
import android.graphics.Bitmap;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.model.GlideUrl;
import com.bumptech.glide.load.model.LazyHeaders;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bancomo.api.BancoMoInterceptor;
import com.bancomo.bancomodroid.R;

/**
 * Created by Rajan Maurya on 05/02/17.
 */
public class ImageLoaderUtils {

    public static String buildImageUrl(int clientId) {
        return PrefManager.getInstanceUrl()
                + "clients/"
                + clientId
                + "/images?maxHeight=120&maxWidth=120";
    }

    public static GlideUrl buildGlideUrl(int clientId) {
        return new GlideUrl(buildImageUrl(clientId), new LazyHeaders.Builder()
                .addHeader(BancoMoInterceptor.HEADER_TENANT, PrefManager.getTenant())
                .addHeader(BancoMoInterceptor.HEADER_AUTH, PrefManager.getToken())
                .addHeader("Accept", "application/octet-stream")
                .build());
    }

    public static void loadImage(Context context, int clientId, final ImageView imageView) {
        Glide.with(context)
                .load(buildGlideUrl(clientId))
                .asBitmap()
                .placeholder(R.drawable.ic_dp_placeholder)
                .error(R.drawable.ic_dp_placeholder)
                .into(new BitmapImageViewTarget(imageView) {
                    @Override
                    protected void setResource(Bitmap result) {
                        // check a valid bitmap is downloaded
                        if (result == null || result.getWidth() == 0)
                            return;
                        // set to image view
                        imageView.setImageBitmap(result);
                    }
                });
    }
}
