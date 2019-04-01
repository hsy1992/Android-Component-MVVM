package com.endless.study.baselibrary.common.glide;

import android.widget.ImageView;

import androidx.annotation.DrawableRes;

/**
 * 图片配置信息
 * @author haosiyuan
 * @date 2019/4/1 2:18 PM
 */
public class ImageConfig {

    /**
     * 图片路径
     */
    private String imageUrl;
    /**
     * ImageView
     */
    private ImageView imageView;
    /**
     * 占位符
     */
    private @DrawableRes int placeHolder;
    /**
     * 错误图片
     */
    private @DrawableRes int errorPic;

    public String getImageUrl() {
        return imageUrl;
    }

    public ImageView getImageView() {
        return imageView;
    }

    public int getPlaceHolder() {
        return placeHolder;
    }

    public int getErrorPic() {
        return errorPic;
    }
}
