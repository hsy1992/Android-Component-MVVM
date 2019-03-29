package com.endless.study.baselibrary.utils.text;


import android.graphics.drawable.Drawable;
import android.text.style.BackgroundColorSpan;
import android.text.style.CharacterStyle;
import android.text.style.ClickableSpan;
import android.text.style.ForegroundColorSpan;
import android.text.style.ImageSpan;
import android.text.style.RelativeSizeSpan;
import android.text.style.StrikethroughSpan;
import android.text.style.StyleSpan;
import android.text.style.SubscriptSpan;
import android.text.style.SuperscriptSpan;
import android.text.style.URLSpan;
import android.text.style.UnderlineSpan;

import java.util.ArrayList;
import java.util.List;

/**
 * 样式构建
 * @author haosiyuan
 * @date 2019/3/21 10:22 AM
 */
public class SpannableBuilder {

    private List<ParameterModel> styles;

    private SpannableBuilder(List<ParameterModel> styles) {
        this.styles = styles;
    }

    public static class Builder {

        private List<ParameterModel> styles = new ArrayList<>();

        /**
         * 文本颜色
         * @param startIndex
         * @param endIndex
         * @param color
         * @return
         */
        public Builder withForegroundColorSpan(int startIndex, int endIndex, int color) {
            styles.add(new ParameterModel(startIndex, endIndex, new ForegroundColorSpan(color)));
            return this;
        }

        /**
         * 背景色
         * @param startIndex
         * @param endIndex
         * @param color
         * @return
         */
        public Builder withBackgroundColorSpan(int startIndex, int endIndex, int color) {
            styles.add(new ParameterModel(startIndex, endIndex, new BackgroundColorSpan(color)));
            return this;
        }

        /**
         * 相对高度
         * @param startIndex
         * @param endIndex
         * @param size
         * @return
         */
        public Builder withRelativeSizeSpan(int startIndex, int endIndex, float size) {
            styles.add(new ParameterModel(startIndex, endIndex, new RelativeSizeSpan(size)));
            return this;
        }

        /**
         * 删除线
         * @param startIndex
         * @param endIndex
         * @return
         */
        public Builder withStrikethroughSpan(int startIndex, int endIndex) {
            styles.add(new ParameterModel(startIndex, endIndex, new StrikethroughSpan()));
            return this;
        }

        /**
         * 下划线
         * @param startIndex
         * @param endIndex
         * @return
         */
        public Builder withUnderlineSpan(int startIndex, int endIndex) {
            styles.add(new ParameterModel(startIndex, endIndex, new UnderlineSpan()));
            return this;
        }

        /**
         * 设置上标
         * @param startIndex
         * @param endIndex
         * @return
         */
        public Builder withSuperscriptSpan(int startIndex, int endIndex) {
            styles.add(new ParameterModel(startIndex, endIndex, new SuperscriptSpan()));
            return this;
        }

        /**
         * 设置下标
         * @param startIndex
         * @param endIndex
         * @return
         */
        public Builder withSubscriptSpan(int startIndex, int endIndex) {
            styles.add(new ParameterModel(startIndex, endIndex, new SubscriptSpan()));
            return this;
        }

        /**
         * 字体风格
         * @param startIndex
         * @param endIndex
         * @param type
         * @return
         */
        public Builder withStyleSpan(int startIndex, int endIndex, int type) {
            styles.add(new ParameterModel(startIndex, endIndex, new StyleSpan(type)));
            return this;
        }

        /**
         * 添加表情
         * @param startIndex
         * @param endIndex
         * @param drawable
         * @return
         */
        public Builder withImageSpan(int startIndex, int endIndex, Drawable drawable) {
            styles.add(new ParameterModel(startIndex, endIndex, new ImageSpan(drawable)));
            return this;
        }

        /**
         * 点击 需要设置 setMovementMethod 否则点击事件不好使
         * @param startIndex
         * @param endIndex
         * @param clickableSpan
         * @return
         */
        public Builder withClickableSpan(int startIndex, int endIndex, ClickableSpan clickableSpan) {
            styles.add(new ParameterModel(startIndex, endIndex, clickableSpan));
            return this;
        }

        /**
         * URLSpan 需要设置 setMovementMethod 否则点击事件不好使 点击跳转网页
         * @param startIndex
         * @param endIndex
         * @param url
         * @return
         */
        public Builder withClickableSpan(int startIndex, int endIndex, String url) {
            styles.add(new ParameterModel(startIndex, endIndex, new URLSpan(url)));
            return this;
        }

        public SpannableBuilder build() {
            return new SpannableBuilder(styles);
        }

    }


    public static class ParameterModel {

        private int startIndex;

        private int endIndex;

        private CharacterStyle style;

        private ParameterModel(int startIndex, int endIndex, CharacterStyle style) {
            this.startIndex = startIndex;
            this.endIndex = endIndex;
            this.style = style;
        }

        public int getStartIndex() {
            return startIndex;
        }

        public int getEndIndex() {
            return endIndex;
        }

        public CharacterStyle getStyle() {
            return style;
        }
    }

    public List<ParameterModel> getStyles() {
        return styles;
    }
}
