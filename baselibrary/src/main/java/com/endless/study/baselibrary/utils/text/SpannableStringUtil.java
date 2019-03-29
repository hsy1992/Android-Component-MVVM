package com.endless.study.baselibrary.utils.text;

import android.text.SpannableString;
import android.text.Spanned;

import com.endless.study.baselibrary.common.logger.Logger;

/**
 * 设置样式
 * @author haosiyuan
 * @date 2019/3/21 9:41 AM
 */
public class SpannableStringUtil {


    /**
     * 从起始下标到终了下标，包括起始下标
     * @param builder
     * @return
     */
    public static SpannableString withInclusiveExclusive(String changeText, SpannableBuilder builder) {
        SpannableString spannableString = new SpannableString(changeText);
        try {
            setSpan(spannableString, builder, Spanned.SPAN_INCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            Logger.errorInfo("change text fail");
        }
        return spannableString;
    }

    /**
     * 从起始下标到终了下标，同时包括起始下标和终了下标
     * @param builder
     * @return
     */
    public static SpannableString withInclusiveInclusive(String changeText, SpannableBuilder builder) {
        SpannableString spannableString = new SpannableString(changeText);
        try {
            setSpan(spannableString, builder, Spanned.SPAN_INCLUSIVE_INCLUSIVE);
        } catch (Exception e) {
            Logger.errorInfo("change text fail");
        }
        return spannableString;
    }

    /**
     * 从起始下标到终了下标，但都不包括起始下标和终了下标
     * @param builder
     * @return
     */
    public static SpannableString withExclusiveExclusive(String changeText, SpannableBuilder builder) {
        SpannableString spannableString = new SpannableString(changeText);
        try {
            setSpan(spannableString, builder, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        } catch (Exception e) {
            Logger.errorInfo("change text fail");
        }
        return spannableString;
    }

    /**
     * 从起始下标到终了下标，包括终了下标
     * @param builder
     * @return
     */
    public static SpannableString withExclusiveInclusive(String changeText, SpannableBuilder builder) {
        SpannableString spannableString = new SpannableString(changeText);
        try {
            setSpan(spannableString, builder, Spanned.SPAN_EXCLUSIVE_INCLUSIVE);
        } catch (Exception e) {
            Logger.errorInfo("change text fail");
        }
        return spannableString;
    }

    /**
     * 设置样式
     */
    private static void setSpan(SpannableString spannableString, SpannableBuilder builder, int flag) {

        if (builder.getStyles() != null && builder.getStyles().size() > 0) {

            for (SpannableBuilder.ParameterModel parameterModel : builder.getStyles()) {
                spannableString.setSpan(parameterModel.getStyle(), parameterModel.getStartIndex(),
                        parameterModel.getEndIndex(), flag);
            }
        }
    }
}
